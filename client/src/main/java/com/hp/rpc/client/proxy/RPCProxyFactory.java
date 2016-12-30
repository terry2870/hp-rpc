/**
 * 
 */
package com.hp.rpc.client.proxy;

import java.lang.reflect.Proxy;


/**
 * @author ping.huang
 * 2016年12月20日
 */
public class RPCProxyFactory<T> {
	
	private Class<T> interfaceClass;
	
	public RPCProxyFactory(Class<T> interfaceClass) {
		this.interfaceClass = interfaceClass;
	}
	
	@SuppressWarnings("unchecked")
	public T newInstance() {
		Object obj = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[] { interfaceClass }, new RPCProxyInvocationHandler(interfaceClass));
		return (T) obj;
	}

	public Class<T> getInterfaceClass() {
		return interfaceClass;
	}

	public void setInterfaceClass(Class<T> interfaceClass) {
		this.interfaceClass = interfaceClass;
	}
}
