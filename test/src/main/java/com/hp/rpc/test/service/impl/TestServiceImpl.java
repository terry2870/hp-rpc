/**
 * 
 */
package com.hp.rpc.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.rpc.test.bean.UserBean;
import com.hp.rpc.test.service.IDemoService;
import com.hp.rpc.test.service.IDemoService2;
import com.hp.rpc.test.service.IDemoService3;
import com.hp.rpc.test.service.ITestService;

/**
 * @author ping.huang
 * 2016年12月12日
 */
@Service
public class TestServiceImpl implements ITestService {

	public TestServiceImpl() {
		System.out.println("TestServiceImpl init");
	}
	
	@Override
	public String test1(UserBean user) {
		return "收到了" + user.toString();
	}
	
/*	@Autowired
	IDemoService demoService;
	
	@Autowired
	IDemoService2 demoService2;
	
	@Autowired
	IDemoService3 demoService3;*/

	@Override
	public UserBean test2(UserBean user) {
		UserBean u = new UserBean();
//		System.out.println("demoService= " + demoService.get("1234"));
//		System.out.println("demoService= " + demoService2.get2("456"));
//		System.out.println("demoService= " + demoService3.get3("789"));
		
		u.setMobile(user.getMobile());
		u.setUserId(u.getUserId());
		u.setUserName("收到了" + user.getUserName());
		return u;
	}

}
