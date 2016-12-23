/**
 * 
 */
package com.hp.rpc.client.discovery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * @author ping.huang
 * 2016年12月22日
 */
public class RPCScannerConfigurer implements BeanDefinitionRegistryPostProcessor {
	
	static Logger log = LoggerFactory.getLogger(RPCScannerConfigurer.class);
	
	private String basePackages;
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		ClassPathRPCScanner scan = new ClassPathRPCScanner(registry);
		scan.scan();
	}

	public String getBasePackages() {
		return basePackages;
	}

	public void setBasePackages(String basePackages) {
		this.basePackages = basePackages;
	}

}
