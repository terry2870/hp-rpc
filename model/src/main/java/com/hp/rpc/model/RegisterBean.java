/**
 * 
 */
package com.hp.rpc.model;

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
	
	/**
	 * 包名
	 */
	private String packageName;
	
	/**
	 * 方法名
	 */
	private String methodName;

	/**
	 * 端口
	 */
	private int port;
	
	/**
	 * ip
	 */
	private String ip;
	
	/**
	 * uuid
	 */
	private String uuid;
	
	/**
	 * 注册时间
	 */
	private int registerTime;

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public Class<?> getClassName() {
		return className;
	}

	public void setClassName(Class<?> className) {
		this.className = className;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(int registerTime) {
		this.registerTime = registerTime;
	}
}
