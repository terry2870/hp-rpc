/**
 * 
 */
package com.hp.rpc.test.p2.impl;

import org.springframework.stereotype.Component;

import com.hp.rpc.test.p2.ITest1;

/**
 * @author ping.huang
 * 2016年12月16日
 */
@Component
public class Test1Impl implements ITest1 {

	/* (non-Javadoc)
	 * @see com.hp.rpc.test.p2.ITest1#str()
	 */
	@Override
	public String str() {
		// TODO Auto-generated method stub
		return "sdfasdf";
	}

}
