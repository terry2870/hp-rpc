/**
 * 
 */
package com.hp.rpc.client.proxy;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.apache.curator.x.discovery.ServiceInstance;

import com.hp.core.zookeeper.bean.RegisterInstanceDetail;
import com.hp.core.zookeeper.discovery.ServiceDiscoveryFactory;
import com.hp.core.zookeeper.discovery.ServiceDiscoveryFactoryBean;
import com.hp.tools.common.beans.BaseBean;
import com.hp.tools.common.utils.SpringContextUtil;

/**
 * @author ping.huang
 * 2016年12月21日
 */
public class RPCProxyInvocationHandler implements InvocationHandler, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 295866342274805408L;
	
	private static ServiceDiscoveryFactory discovery = SpringContextUtil.getBean(ServiceDiscoveryFactory.class);
	
	private Class<?> clazz;
	
	public RPCProxyInvocationHandler(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		ServiceInstance<RegisterInstanceDetail> serviceInstance = discovery.discoveryService(method.getDeclaringClass().getName() + "." + method.getName());
		System.out.println("serviceInstance= " + serviceInstance.getPayload().toString());
		return new T(method.getDeclaringClass(), method.getName()).toString();
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	public static class T extends BaseBean {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2068612398293353018L;
		private Class<?> c;
		private String method;
		/**
		 * @param c
		 * @param method
		 */
		public T(Class<?> c, String method) {
			super();
			this.c = c;
			this.method = method;
		}
		public Class<?> getC() {
			return c;
		}
		public void setC(Class<?> c) {
			this.c = c;
		}
		public String getMethod() {
			return method;
		}
		public void setMethod(String method) {
			this.method = method;
		}
	}

}
