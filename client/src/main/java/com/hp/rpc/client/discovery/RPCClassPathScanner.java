/**
 * 
 */
package com.hp.rpc.client.discovery;

import java.io.IOException;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;

import com.alibaba.fastjson.JSON;
import com.hp.rpc.client.proxy.RPCFactoryBean;
import com.hp.rpc.client.proxy.RPCRegistry;

/**
 * @author ping.huang 2016年12月20日
 */
public class RPCClassPathScanner extends ClassPathBeanDefinitionScanner {

	private RPCRegistry rpcRegistry;

	/**
	 * @param registry
	 */
	public RPCClassPathScanner(BeanDefinitionRegistry registry) {
		super(registry);
	}

	static Logger log = LoggerFactory.getLogger(RPCClassPathScanner.class);

	@Override
	public Set<BeanDefinitionHolder> doScan(String... basePackages) {
		Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
		if (CollectionUtils.isEmpty(beanDefinitions)) {
			log.warn("ClassPathRPCScanner scan error. with beans is empty . with basePackages={}", JSON.toJSONString(basePackages));
			return null;
		}
		for (BeanDefinitionHolder holder : beanDefinitions) {
			GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();

			log.debug("Creating MapperFactoryBean with name '{}' and '{}' mapperInterface", holder.getBeanName(), definition.getBeanClassName());

			try {
				rpcRegistry.addRPCService(Class.forName(definition.getBeanClassName()));
				definition.getPropertyValues().add("rpcRegistry", rpcRegistry);

				definition.getPropertyValues().add("mapperInterface", definition.getBeanClassName());
				definition.setBeanClass(RPCFactoryBean.class);
			} catch (ClassNotFoundException e) {
				log.error("Registry beanDefinition error. with class={}", definition.getBeanClassName());
			}
		}

		return beanDefinitions;
	}

	protected boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {
		return true;
	}

	protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
		return (beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent());
	}

	public void setRpcRegistry(RPCRegistry rpcRegistry) {
		this.rpcRegistry = rpcRegistry;
	}

}
