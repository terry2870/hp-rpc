/**
 * 
 */
package com.hp.rpc.common.constants;

import com.hp.core.common.utils.StringUtil;

/**
 * @author ping.huang
 * 2016年12月8日
 */
public class RPCConstant {

	//服务注册到zk的根节点
	public static final String ZK_ROOT_PATH = "/yh/rpcservice";
	
	//session超时时间
	public static final int ZK_SESSION_TIMEOUT = 5000;
	
	//连接超时时间
	public static final int ZK_CONNECTION_TIMEOUT = 5000;
	
	//本机ip
	public static final String localIp = StringUtil.fetchLocalIP();
	
	public static final String SERVICE_REGISTRY_CLASS_SPLIT = ",";
	
}
