/**
 * 
 */
package com.hp.rpc.server;

import java.io.Closeable;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.netty.bean.NettyRequest;
import com.hp.core.netty.bean.NettyResponse;
import com.hp.core.netty.server.NettyServer;
import com.hp.core.netty.server.NettyServerChannelInboundHandler.NettyProcess;
import com.hp.core.netty.server.Server;
import com.hp.rpc.common.exceptions.BeanNoFoundException;
import com.hp.rpc.model.RPCRequestBean;
import com.hp.rpc.model.RPCServerConfigBean;
import com.hp.tools.common.utils.ObjectUtil;
import com.hp.tools.common.utils.SpringContextUtil;

/**
 * 远程调用服务端
 * @author ping.huang
 * 2016年11月4日
 */
public class RPCServer implements Closeable {
	
	static Logger log = LoggerFactory.getLogger(RPCServer.class);
	
	private RPCServerConfigBean serverConfigBean;
	
	/**
	 * 初始化服务端
	 */
	public void init() throws Exception {
		log.info("init RPCServer start");
		if (serverConfigBean.getPort() == 0) {
			log.warn("init RPCServer error. with ports is empty");
			return;
		}
		//初始化服务
		Server server = new NettyServer(serverConfigBean.getPort(), new NettyProcess() {

			@Override
			public NettyResponse process(NettyRequest request) throws Exception {
				log.debug("process start. with request={}", request);
				RPCRequestBean bean = (RPCRequestBean) request;
				Object serviceBean = null;
				if (StringUtils.isNotEmpty(bean.getBeanName())) {
					serviceBean = SpringContextUtil.getBean(bean.getBeanName());
				} else {
					serviceBean = SpringContextUtil.getBean(bean.getClassName());
				}
				if (serviceBean == null) {
					throw new BeanNoFoundException("can not found bean of beanName="+ bean.getBeanName() +" or className=" + bean.getClassName() + ". with messageId=" + bean.getMessageId());
				}
				
				Object result = ObjectUtil.executeJavaMethod(serviceBean, bean.getMethodName(), bean.getParameterTypes(), bean.getParameters());
				log.debug("process success. with request={}", request);
				return new NettyResponse(request.getMessageId(), result, bean.getReturnType());
			}
			
			
		}, serverConfigBean.getThreadSize());
		
		//启动服务
		server.start();
		log.info("init RPCServer success");
	}
	
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

	public RPCServerConfigBean getServerConfigBean() {
		return serverConfigBean;
	}

	public void setServerConfigBean(RPCServerConfigBean serverConfigBean) {
		this.serverConfigBean = serverConfigBean;
	}
}
