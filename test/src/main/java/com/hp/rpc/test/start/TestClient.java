/**
 * 
 */
package com.hp.rpc.test.start;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hp.rpc.test.p2.ITest1;
import com.hp.rpc.test.p2.pp.ITest5;

/**
 * @author ping.huang
 * 2016年12月14日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/hp-rpc-common.xml"})
//@ContextConfiguration(locations = {"classpath*:META-INF/spring/hp-rpc-common.xml"})
public class TestClient {

	public TestClient() {
		System.out.println("TestClient init ");
	}
	
	//@Autowired
	ITest5 t5;
	
	@Value("${zk.basePath}")
	private String zkBasePath;
	
	@Test
	public void t() {
		
		try {
			System.in.read();
			System.out.println("t5= " + t5.str5().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		System.out.println("t4= " + t4.str4().toString());
		System.out.println("111111111111111111111111111111111111111111111");
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*UserBean u = testService.test2(new UserBean("name1", 1, "139"));
		System.out.println(u);
		System.out.println("fffffffffffffffffffffffff");*/
		//System.out.println("aaaa= " + t1.str());
	}
	
	public static void main(String[] args) {
		
	}
}
