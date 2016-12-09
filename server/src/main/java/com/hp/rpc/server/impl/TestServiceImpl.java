/**
 * 
 */
package com.hp.rpc.server.impl;

import org.springframework.stereotype.Service;

import com.hp.rpc.server.ITestService;

/**
 * @author ping.huang
 * 2016年12月5日
 */
@Service
public class TestServiceImpl implements ITestService {

	@Override
	public String str(String str) {
		return "123 " + str;
	}

	@Override
	public String str(int a) {
		
		return "收到了+" + a;
	}

}
