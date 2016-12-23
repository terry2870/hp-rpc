/**
 * 
 */
package com.hp.rpc.test;

import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import com.hp.rpc.test.service.impl.TestServiceImpl;

/**
 * @author ping.huang
 * 2016年12月19日
 */
public class Load implements ApplicationContextAware {

	static Logger log = LoggerFactory.getLogger(Load.class);
	
	ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public Load() {
		log.info("Load");
		log.info("applicationContext= " + applicationContext);
		log.info("Load end");
	}
	
	public void init() {
		log.info("init");
		log.info("applicationContext= " + applicationContext);
		try {
			loadBean("TestServiceImpl", TestServiceImpl.class, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("init end");
	}
	
	/**
	 * 动态注册bean到spring容器中
	 * @param beanName
	 * @param className
	 * @param property
	 */
	public void loadBean(String beanName, Class<?> className, Map<String, Object> property) throws Exception {
		ConfigurableApplicationContext ctx = (ConfigurableApplicationContext) applicationContext;
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) ctx.getBeanFactory();
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(className);
		if (property != null && !property.isEmpty()) {
			for (Entry<String, Object> entry : property.entrySet()) {
				builder.addPropertyValue(entry.getKey(), entry.getValue());
			}
		}
		registry.registerBeanDefinition(beanName, builder.getBeanDefinition());
	}

}
