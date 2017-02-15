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

import com.hp.rpc.client.proxy.RPCRegistry;

/**
 * @author ping.huang
 * 2016年12月22日
 */
public class RPCScannerConfigurer implements BeanDefinitionRegistryPostProcessor {
	
	static Logger log = LoggerFactory.getLogger(RPCScannerConfigurer.class);
	
	private String basePackages;
	private RPCRegistry rpcRegistry;
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		log.info("start to scan package with basePackages={}", basePackages);
		RPCClassPathScanner scan = new RPCClassPathScanner(registry);
		scan.setRpcRegistry(rpcRegistry);
		scan.scan(basePackages.split(","));
	}

	public String getBasePackages() {
		return basePackages;
	}

	public void setBasePackages(String basePackages) {
		this.basePackages = basePackages;
	}

	public RPCRegistry getRpcRegistry() {
		return rpcRegistry;
	}

	public void setRpcRegistry(RPCRegistry rpcRegistry) {
		this.rpcRegistry = rpcRegistry;
	}

}
