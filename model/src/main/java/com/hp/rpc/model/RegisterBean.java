/**
 * 
 */
package com.hp.rpc.model;

import java.util.List;

import com.hp.tools.common.beans.BaseBean;

/**
 * @author ping.huang
 * 2016年11月4日
 */
public class RegisterBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1692033597929830899L;
	
	/**
	 * 服务的beanName
	 */
	private String beanName;
	
	/**
	 * 接口的class
	 */
	private Class<?> className;
	

}
