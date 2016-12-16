/**
 * 
 */
package com.hp.rpc.test.service;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;


/**
 * @author ping.huang
 * 2016年12月14日
 */
public class BeanProxyCreator {

	
	@SuppressWarnings("unchecked")
	public <T> T create(Class<T> interfaceClass) {
		return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[] { interfaceClass }, new ProxyInvocationHandler());
	}
	
	public class ProxyInvocationHandler implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			/*RpcRequest request = new RpcRequest(); // 创建并初始化 RPC 请求
			request.setRequestId(UUID.randomUUID().toString());
			request.setClassName(method.getDeclaringClass().getName());
			request.setMethodName(method.getName());
			request.setParameterTypes(method.getParameterTypes());
			request.setParameters(args);

			if (serviceDiscovery != null) {
				serverAddress = serviceDiscovery.discover(); // 发现服务
			}

			String[] array = serverAddress.split(":");
			String host = array[0];
			int port = Integer.parseInt(array[1]);

			RpcClient client = new RpcClient(host, port); // 初始化 RPC 客户端
			RpcResponse response = client.send(request); // 通过 RPC 客户端发送 RPC
															// 请求并获取 RPC 响应

			if (response == null || response.getError() != null) {
				throw response.getError();
			} else {
				return response.getResult();
			}*/
			return new Object();
		}
		
	}
}
