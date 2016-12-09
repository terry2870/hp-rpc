/**
 * 
 */
package com.hp.rpc.common.constants;

import com.hp.tools.common.utils.StringUtil;

/**
 * @author ping.huang
 * 2016年12月8日
 */
public class RPCConstant {

	//服务注册到zk的根节点
	public static final String ZK_ROOT_PATH = "/yh/rpcservice";
	
	//zk连接的超时时间
	public static final int ZK_SESSION_TIMEOUT = 5000;
	
	//本机ip
	public static final String localIp = StringUtil.fetchLocalIP();
	
}
