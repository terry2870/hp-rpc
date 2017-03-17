package com.hp.rpc.common.exceptions;

import org.springframework.beans.BeansException;

/**
 * 找不到服务空异常
 * @author ping.huang
 * @date 2014-09-15
 */
public class ServiceNoFoundException extends BeansException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServiceNoFoundException(String msg) {
		super(msg);
	}


}
