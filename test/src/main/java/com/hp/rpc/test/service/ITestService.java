/**
 * 
 */
package com.hp.rpc.test.service;

import com.hp.rpc.test.bean.UserBean;

/**
 * @author ping.huang
 * 2016年12月12日
 */
public interface ITestService {

	public String test1(UserBean user);
	
	public UserBean test2(UserBean user);
}
