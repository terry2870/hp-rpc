/**
 * 
 */
package com.hp.rpc.client.proxy;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author ping.huang
 * 2016年12月20日
 */
public class RPCProxyFactory<T> {
	
	private Class<T> interfaceClass;
	
	public RPCProxyFactory(Class<T> interfaceClass) {
		this.interfaceClass = interfaceClass;
	}

	//代理类的实现类缓存
	private static Map<Class<?>, Object> proxyInstance = new ConcurrentHashMap<>();
	
	@SuppressWarnings("unchecked")
	public T newInstance() {
		Object obj = proxyInstance.get(interfaceClass);
		if (obj == null) {
			obj = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[] { interfaceClass }, new RPCProxyInvocationHandler(interfaceClass));
			proxyInstance.put(interfaceClass, obj);
		}
		return (T) obj;
	}

	public Class<T> getInterfaceClass() {
		return interfaceClass;
	}

	public void setInterfaceClass(Class<T> interfaceClass) {
		this.interfaceClass = interfaceClass;
	}
}
