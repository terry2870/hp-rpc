/**
 * 
 */
package org.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hp.rpc.test.p2.pp.ITest5;

/**
 * @author ping.huang
 * 2016年12月14日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/spring-rpc-client.xml"})
public class TestClient {

	public TestClient() {
		System.out.println("TestClient init ");
	}
	
	/*@Autowired
	ITestService testService;*/
	
	@Autowired
	ITest5 t5;
	
	@Test
	public void t() {
		
		System.out.println("t5= " + t5.str5());
		
		
		
		/*UserBean u = testService.test2(new UserBean("name1", 1, "139"));
		System.out.println(u);
		System.out.println("fffffffffffffffffffffffff");*/
		//System.out.println("aaaa= " + t1.str());
	}
	
	public static void main(String[] args) {
		
	}
}
