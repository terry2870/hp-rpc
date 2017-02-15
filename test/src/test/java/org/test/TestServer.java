package org.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hp.rpc.server.ServiceRegistry;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/spring*.xml"})
public class TestServer {

	public static void main(String[] args) {
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:META-INF/spring/spring*.xml");
		ServiceRegistry s = ctx.getBean(ServiceRegistry.class);
		System.out.println("ServiceRegistry= " + s);
		try {
//			Thread.sleep(10000);
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ctx.start();
		ctx.close();
	}
	
	@Test
	public void testServer() {
		try {
//			Thread.sleep(10000);
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
