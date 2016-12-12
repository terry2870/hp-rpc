/**
 * 
 */
package com.hp.rpc.common.discovery;

import java.io.Closeable;
import java.io.IOException;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.hp.rpc.model.RegisterInstanceDetail;

/**
 * @author ping.huang 2016年12月12日
 */
public class ServiceDiscoveryFactoryBean implements FactoryBean<ServiceDiscovery<RegisterInstanceDetail>>, InitializingBean, Closeable {

	private ServiceDiscovery<RegisterInstanceDetail> serviceDiscovery;
	
	private CuratorFramework curator;
	private String basePath;

	@Override
	public void afterPropertiesSet() throws Exception {
		JsonInstanceSerializer<RegisterInstanceDetail> serializer = new JsonInstanceSerializer<RegisterInstanceDetail>(RegisterInstanceDetail.class);
		serviceDiscovery = ServiceDiscoveryBuilder.builder(RegisterInstanceDetail.class)
				.client(curator)
				.serializer(serializer)
				.basePath(basePath)
				.build();
		serviceDiscovery.start();
	}

	@Override
	public ServiceDiscovery<RegisterInstanceDetail> getObject() throws Exception {
		return serviceDiscovery;
	}

	@Override
	public Class<?> getObjectType() {
		return ServiceDiscovery.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void close() throws IOException {
		if (serviceDiscovery != null) {
			serviceDiscovery.close();
		}
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

}
