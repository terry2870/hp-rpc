/**
 * 
 */
package com.hp.rpc.test;

import java.util.Map.Entry;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.Assert;

/**
 * @author ping.huang
 * 2016年12月15日
 */
public class MyBeanDefinitionRegistry extends DefaultListableBeanFactory implements ApplicationContextAware {

	private ApplicationContext applicationContext;
	
	@Override
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition)
			throws BeanDefinitionStoreException {
		super.registerBeanDefinition(beanName, beanDefinition);
		System.out.println("beanName=" + beanName);
		/*Assert.hasText(beanName, "'beanName' must not be empty");
		Assert.notNull(beanDefinition, "BeanDefinition must not be null");
		try {
			loadBean(beanName, beanDefinition);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	/*@Override
	public void removeBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
		if (this.beanDefinitionMap.remove(beanName) == null) {
			throw new NoSuchBeanDefinitionException(beanName);
		}
	}

	@Override
	public BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
		BeanDefinition bd = this.beanDefinitionMap.get(beanName);
		if (bd == null) {
			throw new NoSuchBeanDefinitionException(beanName);
		}
		return bd;
	}

	@Override
	public boolean containsBeanDefinition(String beanName) {
		return this.beanDefinitionMap.containsKey(beanName);
	}

	@Override
	public String[] getBeanDefinitionNames() {
		return StringUtils.toStringArray(this.beanDefinitionMap.keySet());
	}

	@Override
	public int getBeanDefinitionCount() {
		return this.beanDefinitionMap.size();
	}

	@Override
	public boolean isBeanNameInUse(String beanName) {
		return isAlias(beanName) || containsBeanDefinition(beanName);
	}*/

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	/**
	 * 动态注册bean到spring容器中
	 * @param beanName
	 * @param className
	 * @param property
	 */
	private void loadBean(String beanName, BeanDefinition beanDefinition) throws Exception {
		if (isBeanExists(beanName)) {
			throw new Exception("【"+ beanName +"】，spring bean名称重复");
		}
//		Object obj = Proxy.newProxyInstance(className.getClassLoader(), new Class<?>[] {className}, this);
		ConfigurableApplicationContext ctx = (ConfigurableApplicationContext) applicationContext;
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) ctx.getBeanFactory();
		registry.registerBeanDefinition(beanName, beanDefinition);
		
		
		
		/*ConfigurableApplicationContext ctx = (ConfigurableApplicationContext) applicationContext;
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) ctx.getBeanFactory();
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(className);
		if (property != null && !property.isEmpty()) {
			for (Entry<String, Object> entry : property.entrySet()) {
				builder.addPropertyValue(entry.getKey(), entry.getValue());
			}
		}
		registry.registerBeanDefinition(beanName, builder.getBeanDefinition());*/
	}
	
	/**
	 * 查询spring工厂中是否包含该名称的bean对象
	 * @param beanName bean名称
	 * @return spring工厂中是否包含该名称的bean对象
	 */
	public boolean isBeanExists(String beanName) {
		return applicationContext.containsBean(beanName);
	}

}
