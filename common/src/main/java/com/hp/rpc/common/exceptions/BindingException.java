/**
 * 
 */
package com.hp.rpc.common.exceptions;

/**
 * @author ping.huang 2016年12月22日
 */
public class BindingException extends RuntimeException {

	private static final long serialVersionUID = 4300802238789381562L;

	public BindingException() {
		super();
	}

	public BindingException(String message) {
		super(message);
	}

	public BindingException(String message, Throwable cause) {
		super(message, cause);
	}

	public BindingException(Throwable cause) {
		super(cause);
	}
}
