/**
 * 
 */
package com.hp.rpc.client.proxy;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author ping.huang
 * 2016年12月22日
 */
public class RPCFactoryBean<T> implements FactoryBean<T> {

	private Class<T> mapperInterface;
	
	private RPCRegistry RPCRegistry;
	
	@Override
	public T getObject() throws Exception {
		return RPCRegistry.getRPC(mapperInterface);
	}

	@Override
	public Class<?> getObjectType() {
		return this.mapperInterface;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public Class<T> getMapperInterface() {
		return mapperInterface;
	}

	public void setMapperInterface(Class<T> mapperInterface) {
		this.mapperInterface = mapperInterface;
	}

	public RPCRegistry getRPCRegistry() {
		return RPCRegistry;
	}

	public void setRPCRegistry(RPCRegistry rPCRegistry) {
		RPCRegistry = rPCRegistry;
	}

}
