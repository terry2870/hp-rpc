/**
 * 
 */
package com.hp.rpc.client.proxy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import com.hp.rpc.common.exceptions.BindingException;

/**
 * RPC实现类的构造工厂
 * @author ping.huang 2016年12月22日
 */
public class RPCRegistry implements BeanDefinitionRegistry {

	static Logger log = LoggerFactory.getLogger(RPCRegistry.class);

	private static Map<Class<?>, RPCProxyFactory<?>> knownRPCs = new ConcurrentHashMap<Class<?>, RPCProxyFactory<?>>();

	@SuppressWarnings("unchecked")
	public <T> T getRPCService(Class<T> type) throws BindingException {
		final RPCProxyFactory<T> rpcProxyFactory = (RPCProxyFactory<T>) knownRPCs.get(type);
		if (rpcProxyFactory == null) {
			throw new BindingException("Type " + type + " is not known to the RPCRegistry.");
		}
		try {
			return rpcProxyFactory.newInstance();
		} catch (BindingException e) {
			throw new BindingException("Error getting mapper instance. Cause: " + e.getMessage(), e);
		}
	}

	public <T> boolean hasRPC(Class<T> type) {
		return knownRPCs.containsKey(type);
	}

	public <T> void addRPCService(Class<T> type) {
		if (!type.isInterface()) {
			return;
		}
		if (hasRPC(type)) {
			throw new BindingException("Type " + type + " is already known to the RPCRegistry.");
		}
		knownRPCs.put(type, new RPCProxyFactory<T>(type));
	}

	/* (non-Javadoc)
	 * @see org.springframework.core.AliasRegistry#registerAlias(java.lang.String, java.lang.String)
	 */
	@Override
	public void registerAlias(String name, String alias) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.springframework.core.AliasRegistry#removeAlias(java.lang.String)
	 */
	@Override
	public void removeAlias(String alias) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.springframework.core.AliasRegistry#isAlias(java.lang.String)
	 */
	@Override
	public boolean isAlias(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.springframework.core.AliasRegistry#getAliases(java.lang.String)
	 */
	@Override
	public String[] getAliases(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.support.BeanDefinitionRegistry#registerBeanDefinition(java.lang.String, org.springframework.beans.factory.config.BeanDefinition)
	 */
	@Override
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeanDefinitionStoreException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.support.BeanDefinitionRegistry#removeBeanDefinition(java.lang.String)
	 */
	@Override
	public void removeBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.support.BeanDefinitionRegistry#getBeanDefinition(java.lang.String)
	 */
	@Override
	public BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.support.BeanDefinitionRegistry#containsBeanDefinition(java.lang.String)
	 */
	@Override
	public boolean containsBeanDefinition(String beanName) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.support.BeanDefinitionRegistry#getBeanDefinitionNames()
	 */
	@Override
	public String[] getBeanDefinitionNames() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.support.BeanDefinitionRegistry#getBeanDefinitionCount()
	 */
	@Override
	public int getBeanDefinitionCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.support.BeanDefinitionRegistry#isBeanNameInUse(java.lang.String)
	 */
	@Override
	public boolean isBeanNameInUse(String beanName) {
		// TODO Auto-generated method stub
		return false;
	}
}
