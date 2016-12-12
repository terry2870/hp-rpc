/**
 * 
 */
package com.hp.rpc.model;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.hp.tools.common.beans.BaseBean;

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
	private List<String> forbidPackages;
	
	/**
	 * 禁止远程调用的类名（完全匹配）
	 */
	private List<String> forbidClasses;
	
	/**
	 * 禁止远程调用的类名+方法名（完全匹配）
	 */
	private List<String> forbidClassMethods;
	
	/**
	 * 允许远程调用的包名（前缀匹配）
	 */
	private List<String> allowPackages;
	
	/**
	 * 允许远程调用的类名（完全匹配）
	 */
	private List<String> allowClasses;
	
	/**
	 * 允许远程调用的类名+方法名（完全匹配）
	 */
	private List<String> allowClassMethods;
	
	/**
	 * 是否配置了允许策略
	 * @return
	 */
	public boolean allowConfig() {
		return CollectionUtils.isNotEmpty(allowPackages) || CollectionUtils.isNotEmpty(allowClasses) || CollectionUtils.isNotEmpty(allowClassMethods);
	}
	
	/**
	 * 是否配置了禁止策略
	 * @return
	 */
	public boolean forbidConfig() {
		return CollectionUtils.isNotEmpty(forbidPackages) || CollectionUtils.isNotEmpty(forbidClasses) || CollectionUtils.isNotEmpty(forbidClassMethods);
	}

	public List<String> getForbidPackages() {
		return forbidPackages;
	}

	public void setForbidPackages(List<String> forbidPackages) {
		this.forbidPackages = forbidPackages;
	}

	public List<String> getForbidClasses() {
		return forbidClasses;
	}

	public void setForbidClasses(List<String> forbidClasses) {
		this.forbidClasses = forbidClasses;
	}

	public List<String> getForbidClassMethods() {
		return forbidClassMethods;
	}

	public void setForbidClassMethods(List<String> forbidClassMethods) {
		this.forbidClassMethods = forbidClassMethods;
	}

	public int getThreadSize() {
		return threadSize;
	}

	public void setThreadSize(int threadSize) {
		this.threadSize = threadSize;
	}

	public List<String> getAllowPackages() {
		return allowPackages;
	}

	public void setAllowPackages(List<String> allowPackages) {
		this.allowPackages = allowPackages;
	}

	public List<String> getAllowClasses() {
		return allowClasses;
	}

	public void setAllowClasses(List<String> allowClasses) {
		this.allowClasses = allowClasses;
	}

	public List<String> getAllowClassMethods() {
		return allowClassMethods;
	}

	public void setAllowClassMethods(List<String> allowClassMethods) {
		this.allowClassMethods = allowClassMethods;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	
}
