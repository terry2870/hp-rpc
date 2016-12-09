package com.hp.rpc.common.exceptions;

import org.springframework.beans.BeansException;

/**
 * bean为空异常
 * @author ping.huang
 * @date 2014-09-15
 */
public class BeanNoFoundException extends BeansException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BeanNoFoundException(String msg) {
		super(msg);
	}


}
