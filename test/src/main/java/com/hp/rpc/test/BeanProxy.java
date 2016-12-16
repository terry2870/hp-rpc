/**
 * 
 */
package com.hp.rpc.test;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import com.hp.rpc.test.service.IDemoService;
import com.hp.rpc.test.service.IDemoService2;
import com.hp.rpc.test.service.IDemoService3;

/**
 * @author ping.huang
 * 2016年12月14日
 */
public class BeanProxy implements ApplicationContextAware, BeanPostProcessor, InvocationHandler {
	
	private ApplicationContext applicationContext;
	
	public BeanProxy() {
		System.out.println("BeanProxy start");
		/*ConfigurableApplicationContext ctx = (ConfigurableApplicationContext) applicationContext;
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) ctx.getBeanFactory();
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DemoServiceImpl.class);
		registry.registerBeanDefinition("demoService", builder.getBeanDefinition());*/
	}
	
	public void init() throws Exception {
		System.out.println("BeanProxy init");
		ConfigurableApplicationContext ctx = (ConfigurableApplicationContext) applicationContext;
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) ctx.getBeanFactory();
		
		/*Object d1 = Proxy.newProxyInstance(IDemoService.class.getClassLoader(), new Class<?>[] {IDemoService.class}, this);
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(d1.getClass());
		registry.registerBeanDefinition("demoService", builder.getBeanDefinition());
		
		Object d2 = Proxy.newProxyInstance(IDemoService2.class.getClassLoader(), new Class<?>[] {IDemoService2.class}, this);
		BeanDefinitionBuilder builder2 = BeanDefinitionBuilder.genericBeanDefinition(d2.getClass());
		registry.registerBeanDefinition("demoService2", builder2.getBeanDefinition());
		
		Object d3 = Proxy.newProxyInstance(IDemoService3.class.getClassLoader(), new Class<?>[] {IDemoService3.class}, this);
		BeanDefinitionBuilder builder3 = BeanDefinitionBuilder.genericBeanDefinition(d3.getClass());
		registry.registerBeanDefinition("demoService3", builder3.getBeanDefinition());*/
		loadBean("demoService", IDemoService.class, null);
		loadBean("demoService2", IDemoService2.class, null);
		loadBean("demoService3", IDemoService3.class, null);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		System.out.println("BeanProxy setApplicationContext");
		this.applicationContext = applicationContext;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("postProcessBeforeInitialization. beanName=" + beanName);
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("postProcessAfterInitialization. beanName=" + beanName);
		return bean;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("abcdefg");
		System.out.println("class= " + method.getDeclaringClass().getName());
		System.out.println("method= " + method.getName());
		return new Object();
	}
	
	/**
	 * 动态注册bean到spring容器中
	 * @param beanName
	 * @param className
	 * @param property
	 */
	private void loadBean(String beanName, Class<?> className, Map<String, Object> property) throws Exception {
		if (containsBean(beanName)) {
			throw new Exception("【"+ beanName +"】，spring bean名称重复");
		}
		Object obj = Proxy.newProxyInstance(className.getClassLoader(), new Class<?>[] {className}, this);
		ConfigurableApplicationContext ctx = (ConfigurableApplicationContext) applicationContext;
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) ctx.getBeanFactory();
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(obj.getClass());
		if (MapUtils.isNotEmpty(property)) {
			for (Entry<String, Object> entry : property.entrySet()) {
				builder.addPropertyValue(entry.getKey(), entry.getValue());
			}
		}
		registry.registerBeanDefinition(beanName, builder.getBeanDefinition());
	}
	
	/**
	 * 查询spring工厂中是否包含该名称的bean对象
	 * @param beanName bean名称
	 * @return spring工厂中是否包含该名称的bean对象
	 */
	private boolean containsBean(String beanName) {
		return applicationContext.containsBean(beanName);
	}
}
