/**
 * 
 */
package com.hp.rpc.server;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.hp.core.common.utils.StringUtil;
import com.hp.core.zookeeper.bean.RegisterInstanceDetail;
import com.hp.core.zookeeper.discovery.ServiceDiscoveryFactory;
import com.hp.rpc.common.constants.RPCConstant;
import com.hp.rpc.model.RPCServerConfigBean;

/**
 * 服务注册
 * @author ping.huang
 * 2016年12月7日
 */
public class ServiceRegistry implements BeanPostProcessor, Closeable {

	static Logger log = LoggerFactory.getLogger(ServiceRegistry.class);
	
	private RPCServerConfigBean serverConfigBean;
	
	private ServiceDiscoveryFactory serviceDiscoveryFactory;

	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		log.info("postProcessAfterInitialization with beanName={}", beanName);
		if (serverConfigBean == null) {
			//没有设置配置文件
			log.warn("ServiceRegistry error. with config is null");
			return bean;
		}
		if (!serverConfigBean.allowConfig()) {
			//没有设置允许的值
			log.warn("ServiceRegistry error. with allowConfig is false");
			return bean;
		}
		Class<?> clazz = bean.getClass();
		
		//只暴露有接口的方法
		Class<?>[] clazzes = clazz.getInterfaces();
		if (ArrayUtils.isEmpty(clazzes)) {
			//该类没有实现接口
			log.warn("ServiceRegistry error. with bean have not interface. with class={}", bean.getClass());
			return bean;
		}
		
		//只取该实现类的第一个接口进行暴露
		clazz = clazzes[0];

		//检查该接口与是否被禁止暴露给远程调用
		if (!checkClassAndPackage(clazz)) {
			log.debug("ServiceRegistry error. with interface is forbidden or not allow. with class={}", bean.getClass());
			return bean;
		}
		
		//注册服务
		registry(clazz, beanName);
		
		return bean;
	}
	
	/**
	 * 检查该接口是否可以暴露
	 * @param clazz
	 * @return
	 */
	private boolean checkClassAndPackage(Class<?> clazz) {
		if (clazz == null) {
			return false;
		}
		String packageName = clazz.getPackage().getName();
		String className = clazz.getName();
		//检查是否在禁止的包下面
		if (StringUtils.isNotEmpty(serverConfigBean.getForbidPackages())) {
			for (String str : serverConfigBean.getForbidPackages().split(RPCConstant.SERVICE_REGISTRY_CLASS_SPLIT)) {
				if (packageName.startsWith(str)) {
					log.warn("checkClassAndPackage error. with interface is in forbidden package. with class={}", clazz);
					return false;
				}
			}
		}
		
		//检查是否是禁止的类
		if (StringUtils.isNotEmpty(serverConfigBean.getForbidClasses()) && serverConfigBean.getForbidClasses().contains(className)) {
			log.warn("checkClassAndPackage error. with interface is forbidden. with class={}", clazz);
			return false;
		}
		
		//检查是否在允许的包内
		if (StringUtils.isNotEmpty(serverConfigBean.getAllowPackages())) {
			for (String str : serverConfigBean.getAllowPackages().split(RPCConstant.SERVICE_REGISTRY_CLASS_SPLIT)) {
				if (packageName.startsWith(str)) {
					return true;
				}
			}
		}
		
		//检查是否是允许的类
		if (StringUtils.isNotEmpty(serverConfigBean.getAllowClasses()) && serverConfigBean.getAllowClasses().contains(className)) {
			return true;
		}
		
		log.warn("checkClassAndPackage error. with interface is not in allow. with class={}", clazz);
		return false;
	}
	
	/**
	 * 注册服务
	 * @param clazz
	 * @param beanName
	 */
	private void registry(Class<?> clazz, String beanName) {
		if (clazz == null) {
			return;
		}
		Method[] methods = clazz.getDeclaredMethods();
		if (ArrayUtils.isEmpty(methods)) {
			return;
		}
		for (Method m : methods) {
			if (!checkMethod(clazz.getName(), m.getName())) {
				//方法不允许注册
				continue;
			}
			RegisterInstanceDetail detail = new RegisterInstanceDetail();
			detail.setClassName(clazz.getName());
			detail.setBeanName(beanName);
			detail.setLinstenAddress(StringUtil.fetchLocalIP());
			detail.setLinstenPort(serverConfigBean.getPort());
			detail.setMethodName(m.getName());
			detail.setServiceName(detail.getClassName() + "." + detail.getMethodName());
			detail.setServiceType("netty");
			
			//注册服务
			serviceDiscoveryFactory.registerService(detail);
			log.info("registry success with beanName={}, clazz={}, detail={}", beanName, clazz, detail);
		}
	}
	
	/**
	 * 检查方法是否可以注册
	 * @param className
	 * @param methodName
	 * @return
	 */
	private boolean checkMethod(String className, String methodName) {
		if (StringUtils.isEmpty(className) || StringUtils.isEmpty(className)) {
			log.warn("checkMethod error. with className is empty or methodName is empty. with className={}, methodName={}", className, methodName);
			return false;
		}
		
		//判断该方法是否在被禁止的范围内
		if (StringUtils.isNotEmpty(serverConfigBean.getForbidClassMethods()) && serverConfigBean.getForbidClassMethods().contains(className + "." + methodName)) {
			log.warn("checkMethod error. with method is forbidden. with className={}, methodName={}", className, methodName);
			return false;
		}
		
		//判断该方法是否在允许的范围内
		if (StringUtils.isEmpty(serverConfigBean.getAllowClassMethods())) {
			return true;
		}
		if (serverConfigBean.getAllowClassMethods().contains(className + "." + methodName)) {
			return true;
		}
		log.warn("checkMethod error. with method is not in allow. with className={}, methodName={}", className, methodName);
		return false;
	}
	
	@Override
	public void close() throws IOException {
		log.info("close ServiceRegistry");
	}


	public RPCServerConfigBean getServerConfigBean() {
		return serverConfigBean;
	}

	public void setServerConfigBean(RPCServerConfigBean serverConfigBean) {
		this.serverConfigBean = serverConfigBean;
	}

	public ServiceDiscoveryFactory getServiceDiscoveryFactory() {
		return serviceDiscoveryFactory;
	}

	public void setServiceDiscoveryFactory(ServiceDiscoveryFactory serviceDiscoveryFactory) {
		this.serviceDiscoveryFactory = serviceDiscoveryFactory;
	}

}
