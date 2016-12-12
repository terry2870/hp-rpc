/**
 * 
 */
package com.hp.rpc.server;

import java.io.IOException;
import java.util.UUID;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.junit.Test;

import com.hp.tools.common.beans.BaseBean;

/**
 * @author ping.huang
 * 2016年11月8日
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath*:META-INF/spring/spring*.xml"})
public class TestStart {

	private static ServiceDiscovery<InstanceDetails> serviceDiscovery;
	
	@Test
	public void test() {
		
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.102.205:2181", 2000, 30000, new ExponentialBackoffRetry(2000, 3));
        client.start();
        
        JsonInstanceSerializer<InstanceDetails> serializer = new JsonInstanceSerializer<InstanceDetails>(InstanceDetails.class);
        serviceDiscovery = ServiceDiscoveryBuilder.builder(InstanceDetails.class)
                .client(client)
                .serializer(serializer)
                .basePath("/hp/test")
                .build();
        try {
			serviceDiscovery.start();
			
			ServiceInstance<InstanceDetails> instance1 = ServiceInstance.<InstanceDetails>builder()
	                .name("service1")
	                .port(12345)
	                //.address()   //address不写的话，会取本地ip
	                .payload(new InstanceDetails(UUID.randomUUID().toString(),"192.168.1.100",12345,"Test.Service1"))
//	                .uriSpec(new UriSpec("{scheme}://{address}:{port}"))
	                .build();
			serviceDiscovery.registerService(instance1);
			
			ServiceInstance<InstanceDetails> instance2 = ServiceInstance.<InstanceDetails>builder()
	                .name("service1")
	                .port(8888)
	                //.address()   //address不写的话，会取本地ip
	                .payload(new InstanceDetails(UUID.randomUUID().toString(),"192.168.1.1001",12345,"Test.Service12"))
//	                .uriSpec(new UriSpec("{scheme}://{address}:{port}"))
	                .build();
			serviceDiscovery.registerService(instance2);
			
			
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@JsonRootName("details")
	public static class InstanceDetails extends BaseBean {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3796065342542290080L;

		private String id;

	    private String listenAddress;

	    private int listenPort;

	    private String interfaceName;

	    public InstanceDetails() {}
	    
		/**
		 * @param id
		 * @param listenAddress
		 * @param listenPort
		 * @param interfaceName
		 */
		public InstanceDetails(String id, String listenAddress, int listenPort, String interfaceName) {
			super();
			this.id = id;
			this.listenAddress = listenAddress;
			this.listenPort = listenPort;
			this.interfaceName = interfaceName;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getListenAddress() {
			return listenAddress;
		}

		public void setListenAddress(String listenAddress) {
			this.listenAddress = listenAddress;
		}

		public int getListenPort() {
			return listenPort;
		}

		public void setListenPort(int listenPort) {
			this.listenPort = listenPort;
		}

		public String getInterfaceName() {
			return interfaceName;
		}

		public void setInterfaceName(String interfaceName) {
			this.interfaceName = interfaceName;
		}
	}
}
