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
public class RPCServerBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8073831289068931300L;

	/**
	 * 暴露服务的端口
	 */
	private int port;
	
	/**
	 * 暴露服务的接口的包名（该包下的所有接口都暴露出去）
	 */
	private List<String> packageList;
}
