package com.hp.rpc.test.start;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/spring*.xml"})
public class TestServer {

	
	
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
