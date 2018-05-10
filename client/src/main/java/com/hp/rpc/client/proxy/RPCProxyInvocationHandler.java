/**
 * 
 */
package com.hp.rpc.client.proxy;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.apache.curator.x.discovery.ServiceInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.common.beans.BaseBean;
import com.hp.core.common.utils.SpringContextUtil;
import com.hp.core.netty.bean.NettyRequest;
import com.hp.core.netty.bean.NettyResponse;
import com.hp.core.netty.client.Client;
import com.hp.core.zookeeper.bean.RegisterInstanceDetail;
import com.hp.core.zookeeper.discovery.ServiceDiscoveryFactory;
import com.hp.rpc.client.factory.NettyClientFactory;
import com.hp.rpc.common.exceptions.ServiceNoFoundException;
import com.hp.rpc.model.RPCRequestBean;

/**
 * @author ping.huang
 * 2016年12月21日
 */
public class RPCProxyInvocationHandler implements InvocationHandler, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 295866342274805408L;
	
	static Logger log = LoggerFactory.getLogger(RPCProxyInvocationHandler.class);
	
	private static ServiceDiscoveryFactory discovery = SpringContextUtil.getBean(ServiceDiscoveryFactory.class);
	private static NettyClientFactory nettyClientFactory = NettyClientFactory.getInstance();
	
	private Class<?> clazz;
	
	public RPCProxyInvocationHandler(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		String serverName = method.getDeclaringClass().getName() + "." + method.getName();
		ServiceInstance<RegisterInstanceDetail> serviceInstance = discovery.discoveryService(serverName);
		
		//找不到服务
		if (serviceInstance == null) {
			log.warn("find service from zk error. with serverName={}", serverName);
			throw new ServiceNoFoundException("find service from zk error. with serverName=" + serverName);
		}
		
		RegisterInstanceDetail detail = serviceInstance.getPayload();
		Client client = nettyClientFactory.getNettyClient(detail.getLinstenAddress(), detail.getLinstenPort());
		
		if (client == null) {
			log.warn("find service from zk error. with serverName={}", serverName);
			throw new ServiceNoFoundException("find service from zk error. with serverName=" + serverName);
		}
		
		//组装请求对象
		RPCRequestBean request = new RPCRequestBean();
		request.setClassName(method.getDeclaringClass());
		request.setMethodName(method.getName());
		request.setParameters(args);
		
		NettyResponse response = client.send(new NettyRequest(request, RPCRequestBean.class));
		return response.getData();
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
