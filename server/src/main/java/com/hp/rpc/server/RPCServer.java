/**
 * 
 */
package com.hp.rpc.server;

import java.io.Closeable;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.hp.core.netty.bean.NettyRequest;
import com.hp.core.netty.server.NettyServerImpl;
import com.hp.core.netty.server.NettyServerChannelInboundHandler.NettyProcess;
import com.hp.core.netty.server.NettyServer;
import com.hp.rpc.common.exceptions.BeanNoFoundException;
import com.hp.rpc.model.RPCRequestBean;
import com.hp.rpc.model.RPCServerConfigBean;
import com.hp.tools.common.utils.ObjectUtil;

/**
 * 远程调用服务端
 * @author ping.huang
 * 2016年11月4日
 */
public class RPCServer implements Closeable, ApplicationContextAware {
	
	static Logger log = LoggerFactory.getLogger(RPCServer.class);
	
	private ApplicationContext applicationContext;
	
	private RPCServerConfigBean serverConfigBean;
	private NettyServer server;
	
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
		server = new NettyServerImpl(serverConfigBean.getPort(), new NettyProcess() {

			@Override
			public Object process(NettyRequest request) throws Exception {
				log.info("process start. with request={}", request);
				RPCRequestBean bean = (RPCRequestBean) request.getData();
				Object serviceBean = null;
				if (StringUtils.isNotEmpty(bean.getBeanName())) {
					serviceBean = applicationContext.getBean(bean.getBeanName());
				} else {
					serviceBean = applicationContext.getBean(bean.getClassName());
				}
				if (serviceBean == null) {
					log.warn("process error. with getBean error. with request={}", request);
					throw new BeanNoFoundException("can not found bean of beanName="+ bean.getBeanName() +" or className=" + bean.getClassName() + ". with messageId=" + request.getMessageId());
				}
				
				Object result = ObjectUtil.executeJavaMethod(serviceBean, bean.getMethodName(), bean.getParameterTypes(), bean.getParameters());
				log.info("process success. with request={}", request);
				return result;
			}
			
			
		}, serverConfigBean.getThreadSize());
		
		//启动服务
		server.start();
		log.info("init RPCServer success");
	}
	
	@Override
	public void close() throws IOException {
		log.info("close RPCServer");
		if (server != null) {
			server.stop();
		}
	}

	public RPCServerConfigBean getServerConfigBean() {
		return serverConfigBean;
	}

	public void setServerConfigBean(RPCServerConfigBean serverConfigBean) {
		this.serverConfigBean = serverConfigBean;
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		this.applicationContext = arg0;
	}
}
