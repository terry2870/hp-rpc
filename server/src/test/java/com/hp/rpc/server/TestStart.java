/**
 * 
 */
package com.hp.rpc.server;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author ping.huang
 * 2016年11月8日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/spring*.xml"})
public class TestStart {

	
	@Test
	public void test() {
		
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
