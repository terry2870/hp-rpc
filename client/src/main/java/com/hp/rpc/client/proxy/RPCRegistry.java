/**
 * 
 */
package com.hp.rpc.client.proxy;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.rpc.common.exceptions.BindingException;

/**
 * RPC实现类的构造工厂
 * @author ping.huang 2016年12月22日
 */
public class RPCRegistry {

	static Logger log = LoggerFactory.getLogger(RPCRegistry.class);

	private static Map<Class<?>, RPCProxyFactory<?>> knownRPCs = new HashMap<Class<?>, RPCProxyFactory<?>>();

	@SuppressWarnings("unchecked")
	public <T> T getRPC(Class<T> type) throws BindingException {
		final RPCProxyFactory<T> rpcProxyFactory = (RPCProxyFactory<T>) knownRPCs.get(type);
		if (rpcProxyFactory == null) {
			throw new BindingException("Type " + type + " is not known to the RPCRegistry.");
		}
		try {
			return rpcProxyFactory.newInstance();
		} catch (BindingException e) {
			throw new BindingException("Error getting mapper instance. Cause: " + e, e);
		}
	}

	public <T> boolean hasRPC(Class<T> type) {
		return knownRPCs.containsKey(type);
	}

	public <T> void addRPC(Class<T> type) {
		if (!type.isInterface()) {
			return;
		}
		if (hasRPC(type)) {
			throw new BindingException("Type " + type + " is already known to the RPCRegistry.");
		}
		knownRPCs.put(type, new RPCProxyFactory<T>(type));
	}
}
