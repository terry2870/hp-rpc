/**
 * 
 */
package com.hp.rpc.server;

import java.io.Closeable;
import java.util.List;
import java.util.Map;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceProvider;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.apache.curator.x.discovery.strategies.RandomStrategy;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hp.rpc.server.TestStart.InstanceDetails;

/**
 * @author ping.huang
 * 2016年12月11日
 */
public class TestDis {

	private static ServiceDiscovery<InstanceDetails> serviceDiscovery;
	private Map<String, ServiceProvider<InstanceDetails>> providers = Maps.newHashMap();
    private List<Closeable> closeableList = Lists.newArrayList();
    private Object lock = new Object();
	
    public TestDis() {
    	CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.102.205:2181", new ExponentialBackoffRetry(2000, 3));
        client.start();
        
        JsonInstanceSerializer<InstanceDetails> serializer = new JsonInstanceSerializer<InstanceDetails>(InstanceDetails.class);
        serviceDiscovery = ServiceDiscoveryBuilder.builder(InstanceDetails.class)
                .client(client)
                .serializer(serializer)
                .basePath("/hp/test")
                .build();
        try {
			serviceDiscovery.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
        try {
        	TestDis t = new TestDis();
        	
        	while (true) {
        		ServiceInstance<InstanceDetails> instance1 = t.getInstanceByName("service1");
    	        System.out.println(instance1.getPayload().getId());
    	        Thread.sleep(1000);
        	}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ServiceInstance<InstanceDetails> getInstanceByName(String serviceName) throws Exception {
        ServiceProvider<InstanceDetails> provider = providers.get(serviceName);
        if (provider == null) {
            synchronized (lock) {
                provider = providers.get(serviceName);
                if (provider == null) {
                    provider = serviceDiscovery.serviceProviderBuilder().
                            serviceName(serviceName).
                            providerStrategy(new RandomStrategy<InstanceDetails>())
                            .build();
                    provider.start();
                    closeableList.add(provider);
                    providers.put(serviceName, provider);
                }
            }
        }


        return provider.getInstance();
    }
}
