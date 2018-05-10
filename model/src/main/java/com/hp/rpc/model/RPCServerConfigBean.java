/**
 * 
 */
package com.hp.rpc.model;

import org.apache.commons.lang3.StringUtils;

import com.hp.core.common.beans.BaseBean;

/**
 * 远程调用服务端参数配置
 * 允许的参数是前提，禁止的是在允许的范围内，再进行的筛选
 * @author ping.huang
 * 2016年11月4日
 */
public class RPCServerConfigBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8073831289068931300L;

	/**
	 * 服务端启动的线程数
	 */
	private int threadSize;
	
	/**
	 * 默认暴露的端口
	 */
	private int port;
	
	/**
	 * 禁止远程调用的包名（前缀匹配）
	 */
	private String forbidPackages;
	
	/**
	 * 禁止远程调用的类名（完全匹配）
	 */
	private String forbidClasses;
	
	/**
	 * 禁止远程调用的类名+方法名（完全匹配）
	 */
	private String forbidClassMethods;
	
	/**
	 * 允许远程调用的包名（前缀匹配）
	 */
	private String allowPackages;
	
	/**
	 * 允许远程调用的类名（完全匹配）
	 */
	private String allowClasses;
	
	/**
	 * 允许远程调用的类名+方法名（完全匹配）
	 */
	private String allowClassMethods;
	
	/**
	 * 是否配置了允许策略
	 * @return
	 */
	public boolean allowConfig() {
		return StringUtils.isNotEmpty(allowPackages) || StringUtils.isNotEmpty(allowClasses) || StringUtils.isNotEmpty(allowClassMethods);
	}
	
	/**
	 * 是否配置了禁止策略
	 * @return
	 */
	public boolean forbidConfig() {
		return StringUtils.isNotEmpty(forbidPackages) || StringUtils.isNotEmpty(forbidClasses) || StringUtils.isNotEmpty(forbidClassMethods);
	}

	public String getForbidPackages() {
		return forbidPackages;
	}

	public void setForbidPackages(String forbidPackages) {
		this.forbidPackages = forbidPackages;
	}

	public String getForbidClasses() {
		return forbidClasses;
	}

	public void setForbidClasses(String forbidClasses) {
		this.forbidClasses = forbidClasses;
	}

	public String getForbidClassMethods() {
		return forbidClassMethods;
	}

	public void setForbidClassMethods(String forbidClassMethods) {
		this.forbidClassMethods = forbidClassMethods;
	}

	public int getThreadSize() {
		return threadSize;
	}

	public void setThreadSize(int threadSize) {
		this.threadSize = threadSize;
	}

	public String getAllowPackages() {
		return allowPackages;
	}

	public void setAllowPackages(String allowPackages) {
		this.allowPackages = allowPackages;
	}

	public String getAllowClasses() {
		return allowClasses;
	}

	public void setAllowClasses(String allowClasses) {
		this.allowClasses = allowClasses;
	}

	public String getAllowClassMethods() {
		return allowClassMethods;
	}

	public void setAllowClassMethods(String allowClassMethods) {
		this.allowClassMethods = allowClassMethods;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	
}
