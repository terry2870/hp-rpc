/**
 * 
 */
package com.hp.rpc.model;

import org.apache.commons.lang3.ArrayUtils;

import com.hp.core.common.beans.BaseBean;

/**
 * @author ping.huang
 * 2016年12月6日
 */
public class RPCRequestBean extends BaseBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -499567762752825122L;
	
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
	
	private Class<?> className;

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
		if (ArrayUtils.isNotEmpty(parameters)) {
			parameterTypes = new Class[parameters.length];
			for (int i = 0; i < parameterTypes.length; i++) {
				parameterTypes[i] = parameters[i].getClass();
			}
		}
	}

	public Class<?> getReturnType() {
		return returnType;
	}

	public void setReturnType(Class<?> returnType) {
		this.returnType = returnType;
	}

}
