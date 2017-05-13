/**
 * 
 */
package com.hp.rpc.client.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.netty.client.Client;
import com.hp.core.netty.client.NettyClient;

/**
 * @author ping.huang 2017年2月10日
 */
public class NettyClientFactory {

	private NettyClientFactory() {
	}

	static Logger log = LoggerFactory.getLogger(NettyClientFactory.class);

	// 饿汉模式，加载类，就初始化实例
	private static NettyClientFactory instance = new NettyClientFactory();

	/**
	 * 获取示例，保证单例
	 */
	public static NettyClientFactory getInstance() {
		return instance;
	}

	// 存放客户端的netty对象
	private Map<String, Client> nettyClientMap = new ConcurrentHashMap<>();

	/**
	 * 获取netty客户端实例
	 * @param ip
	 * @param port
	 * @return
	 */
	public synchronized Client getNettyClient(String ip, int port) throws Exception {
		if (StringUtils.isEmpty(ip) || port == 0) {
			log.warn("getNettyClient error. with param is error. with ip={}, port={}", ip, port);
			return null;
		}
		String key = ip + "_" + port;
		Client client = nettyClientMap.get(key);
		if (client == null) {
			client = new NettyClient(ip, port).init();
			nettyClientMap.put(key, client);
		}
		return client;
	}

}
