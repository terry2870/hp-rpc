/**
 * 
 */
package com.hp.rpc.test;

import java.io.IOException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;

import com.hp.core.common.utils.SpringContextUtil;

/**
 * 扫描所有的需要调用远端接口的服务，并自动生成代理实现类
 * @author ping.huang
 * 2016年12月15日
 */
public class Sacn extends ClassPathBeanDefinitionScanner {

	static Logger log = LoggerFactory.getLogger(Sacn.class);
	
	private BeanDefinitionRegistry registry;
		
	/**
	 * @param registry
	 */
	public Sacn(BeanDefinitionRegistry registry) {
		super(registry);
		this.registry = registry;
		System.out.println("start Sacn");
		
		try {
			//SpringContextUtil.loadBean("TestServiceImpl", TestServiceImpl.class, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpringContextUtil.getBean(DefaultListableBeanFactory.class);
		//Map<String, BeanDefinitionRegistry> map = SpringContextUtil.getBeansOfType(BeanDefinitionRegistry.class);
		
		//log.info("map= {}", map);
		//scan("com.hp.rpc.test.service.impl");
		
		Set<BeanDefinitionHolder> set = doScan("com.hp.rpc.test.service.impl");
		String[] arr = registry.getBeanDefinitionNames();
		for (String str : arr) {
			System.out.println("beanName= " + str);
		}
		System.out.println(set);
		
		System.out.println("end Sacn");
	}
	
	/*public Set<BeanDefinitionHolder> doScan(String... basePackages) {
		Assert.notEmpty(basePackages, "At least one base package must be specified");
		Set<BeanDefinitionHolder> beanDefinitions = new LinkedHashSet<BeanDefinitionHolder>();
		for (String basePackage : basePackages) {
			Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
			System.out.println("candidates= " + candidates.size());
			for (BeanDefinition candidate : candidates) {
				ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(candidate);
				candidate.setScope(scopeMetadata.getScopeName());
				String beanName = this.beanNameGenerator.generateBeanName(candidate, this.registry);
				if (candidate instanceof AbstractBeanDefinition) {
					postProcessBeanDefinition((AbstractBeanDefinition) candidate, beanName);
				}
				if (candidate instanceof AnnotatedBeanDefinition) {
					AnnotationConfigUtils.processCommonDefinitionAnnotations((AnnotatedBeanDefinition) candidate);
				}
				if (checkCandidate(beanName, candidate)) {
					BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(candidate, beanName);
					definitionHolder = AnnotationConfigUtils.applyScopedProxyMode(scopeMetadata, definitionHolder, this.registry);
					beanDefinitions.add(definitionHolder);
					registerBeanDefinition(definitionHolder, this.registry);
				}
			}
		}
		return beanDefinitions;
	}*/
	
	protected boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {
		log.info("metadataReader= " + metadataReader);
		return true;
	}

	protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
		return true;
	}
}
