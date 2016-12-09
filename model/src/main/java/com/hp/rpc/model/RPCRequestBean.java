/**
 * 
 */
package com.hp.rpc.model;

import com.hp.core.netty.bean.NettyRequest;

/**
 * @author ping.huang
 * 2016年12月6日
 */
public class RPCRequestBean extends NettyRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4214323567406179850L;

	//调用的bean对象
	private Class<?> className;
	
	//调用的bean的name（如果配置，就使用该值）
	private String beanName;
	
	//方法名
	private String methodName;
	
	//参数类型
	private Class<?>[] parameterTypes;
	
	//参数
	private Object[] parameters;
	
	//返回值的类型
	private Class<?> returnType;

	public Class<?> getClassName() {
		return className;
	}

	public void setClassName(Class<?> className) {
		this.className = className;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	public Class<?> getReturnType() {
		return returnType;
	}

	public void setReturnType(Class<?> returnType) {
		this.returnType = returnType;
	}
}
