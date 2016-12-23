/**
 * 
 */
package com.hp.rpc.client.discovery;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.hp.rpc.client.proxy.RPCProxyFactory;
import com.hp.tools.common.utils.SpringContextUtil;

/**
 * @author ping.huang 2016年12月20日
 */
public class ClassPathRPCScanner extends ClassPathBeanDefinitionScanner {

	/**
	 * @param registry
	 */
	public ClassPathRPCScanner(BeanDefinitionRegistry registry) {
		super(registry);

	}

	static Logger log = LoggerFactory.getLogger(ClassPathRPCScanner.class);

	/*
	 * public void init() throws Exception { Assert.hasText(basePackages,
	 * "basePackages must not empty.");
	 * 
	 * //获取多有需要调用远程的接口 Set<BeanDefinition> beanDefinitionSet =
	 * doScan(basePackages); if (CollectionUtils.isEmpty(beanDefinitionSet)) {
	 * log.warn("can not scan service with basePackages={}", basePackages);
	 * return; }
	 * 
	 * //生成代理类 for (BeanDefinition bean : beanDefinitionSet) { Class<?> clazz =
	 * Class.forName(bean.getBeanClassName()); Object obj = new
	 * RPCProxyFactory(clazz).create(); //动态注册到spring工场
	 * SpringContextUtil.loadBean(BeanDefinitionReaderUtils.generateBeanName(
	 * bean, SpringContextUtil.getBeanDefinitionRegistry()), obj.getClass()); }
	 * }
	 */

	@Override
	public Set<BeanDefinitionHolder> doScan(String... basePackages) {
		Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
		if (CollectionUtils.isEmpty(beanDefinitions)) {
			log.warn("ClassPathRPCScanner scan error. with beans is empty . with basePackages={}", JSON.toJSONString(basePackages));
			return null;
		}
		for (BeanDefinitionHolder holder : beanDefinitions) {
			GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();

			log.debug("Creating MapperFactoryBean with name '{}' and '{}' mapperInterface", holder.getBeanName(), definition.getBeanClassName() );

			// the mapper interface is the original class of the bean
			// but, the actual class of the bean is MapperFactoryBean
			definition.getPropertyValues().add("mapperInterface", definition.getBeanClassName());
			definition.setBeanClass(MapperFactoryBean.class);

			definition.getPropertyValues().add("addToConfig", this.addToConfig);

			boolean explicitFactoryUsed = false;
			if (StringUtils.hasText(this.sqlSessionFactoryBeanName)) {
				definition.getPropertyValues().add("sqlSessionFactory", new RuntimeBeanReference(this.sqlSessionFactoryBeanName));
				explicitFactoryUsed = true;
			} else if (this.sqlSessionFactory != null) {
				definition.getPropertyValues().add("sqlSessionFactory", this.sqlSessionFactory);
				explicitFactoryUsed = true;
			}

			if (StringUtils.hasText(this.sqlSessionTemplateBeanName)) {
				if (explicitFactoryUsed) {
					logger.warn("Cannot use both: sqlSessionTemplate and sqlSessionFactory together. sqlSessionFactory is ignored.");
				}
				definition.getPropertyValues().add("sqlSessionTemplate", new RuntimeBeanReference(this.sqlSessionTemplateBeanName));
				explicitFactoryUsed = true;
			} else if (this.sqlSessionTemplate != null) {
				if (explicitFactoryUsed) {
					logger.warn("Cannot use both: sqlSessionTemplate and sqlSessionFactory together. sqlSessionFactory is ignored.");
				}
				definition.getPropertyValues().add("sqlSessionTemplate", this.sqlSessionTemplate);
				explicitFactoryUsed = true;
			}

			if (!explicitFactoryUsed) {
				if (logger.isDebugEnabled()) {
					logger.debug("Enabling autowire by type for MapperFactoryBean with name '" + holder.getBeanName() + "'.");
				}
				definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
			}
		}

		return beanDefinitions;
	}

	/**
	 * 扫描所有需要远程调用的接口
	 * 
	 * @param basePackages
	 * @return
	 * @throws Exception
	 */
	public Set<BeanDefinition> doScan2(String... basePackages) throws Exception {
		Assert.notEmpty(basePackages, "At least one base package must be specified");
		Set<BeanDefinition> beanDefinitions = new LinkedHashSet<>();
		for (String basePackage : basePackages) {
			Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
			if (CollectionUtils.isEmpty(candidates)) {
				// 没有扫码到，跳过
				continue;
			}
			for (BeanDefinition bean : candidates) {
				bean.setParentName(parentName);
				Class<?> clazz = Class.forName(bean.getBeanClassName());
				if (!clazz.isInterface()) {
					// 不是接口的，跳过
					continue;
				}
				beanDefinitions.add(bean);
			}
		}
		return beanDefinitions;
	}

	protected boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {
		log.info("metadataReader= " + metadataReader);
		return true;
	}

	protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
		return true;
	}

	public void setBasePackages(String basePackages) {
		this.basePackages = basePackages;
	}

}
