/**
 * 
 */
package com.hp.rpc.server;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.hp.core.zookeeper.bean.ZKConfig;
import com.hp.core.zookeeper.curator.ZKCuratorFrameworkFactory;
import com.hp.core.zookeeper.helper.ZookeeperHelper;
import com.hp.rpc.common.constants.RPCConstant;
import com.hp.rpc.model.RPCServerConfigBean;
import com.hp.rpc.model.RegisterBean;
import com.hp.tools.common.utils.DateUtil;
import com.hp.tools.common.utils.StringUtil;

/**
 * 服务注册
 * @author ping.huang
 * 2016年12月7日
 */
public class ServiceRegistry_bak implements BeanPostProcessor, Closeable {

	static Logger log = LoggerFactory.getLogger(ServiceRegistry_bak.class);
	
	private RPCServerConfigBean serverConfigBean;
	
	private ZKConfig zkConfig;
	
	private ZooKeeper zooKeeper;
	
	/**
	 * 初始化服务
	 * @throws Exception
	 */
	public void init() throws Exception {
		log.info("init start ServiceRegistry");
		if (StringUtils.isEmpty(zkConfig.getBasePath())) {
			zkConfig.setBasePath(RPCConstant.ZK_ROOT_PATH);
		}
		if (zkConfig.getSessionTimeoutMs() == 0) {
			zkConfig.setSessionTimeoutMs(RPCConstant.ZK_SESSION_TIMEOUT);
		}
		if (zkConfig.getConnectionTimeoutMs() == 0) {
			zkConfig.setConnectionTimeoutMs(RPCConstant.ZK_CONNECTION_TIMEOUT);
		}
		
		ZKConfig config = new ZKConfig();
		config.setConnectionTimeoutMs(1);
		ZKCuratorFrameworkFactory.getInstance().init(config);
		
		
		
		//初始化zk
		if (zooKeeper == null) {
			zooKeeper = ZookeeperHelper.getConnection(zkConfig.getConnectString(), zkConfig.getSessionTimeoutMs());
		}
		
		//创建zk根节点
		ZookeeperHelper.createNode(zooKeeper, zkConfig.getBasePath(), null, CreateMode.PERSISTENT);
		
		log.info("init success ServiceRegistry");
	}
	
	@Override
	public void close() throws IOException {
		if (zooKeeper != null) {
			try {
				zooKeeper.close();
			} catch (InterruptedException e) {
				log.error("close zk error. with msg={}", e.getMessage(), e);
			}
		}
	}
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (serverConfigBean == null) {
			//没有设置配置文件
			log.warn("ServiceRegistry error. with config is null");
			return bean;
		}
		if (!serverConfigBean.allowConfig()) {
			//没有设置允许的值
			log.warn("ServiceRegistry error. with allowConfig is false");
			return bean;
		}
		Class<?> clazz = bean.getClass();
		Class<?>[] clazzes = clazz.getInterfaces();
		if (ArrayUtils.isEmpty(clazzes)) {
			//该类没有实现接口
			log.warn("ServiceRegistry error. with bean have not interface. with class={}", bean.getClass());
			return bean;
		}
		
		//只取该实现类的第一个接口进行暴露
		clazz = clazzes[0];

		//检查该接口与是否被禁止暴露给远程调用
		if (!checkClassAndPackage(clazz)) {
			log.debug("ServiceRegistry error. with interface is forbidden or not allow. with class={}", bean.getClass());
			return bean;
		}
		
		//注册服务
		registry(clazz, beanName);
		
		return bean;
	}
	
	/**
	 * 检查该接口是否可以暴露
	 * @param clazz
	 * @return
	 */
	private boolean checkClassAndPackage(Class<?> clazz) {
		if (clazz == null) {
			return false;
		}
		String packageName = clazz.getPackage().getName();
		String className = clazz.getName();
		//检查是否在禁止的包下面
		if (CollectionUtils.isNotEmpty(serverConfigBean.getForbidPackages())) {
			for (String str : serverConfigBean.getForbidPackages()) {
				if (packageName.startsWith(str)) {
					log.warn("checkClassAndPackage error. with interface is in forbidden package. with class={}", clazz);
					return false;
				}
			}
		}
		
		//检查是否是禁止的类
		if (CollectionUtils.isNotEmpty(serverConfigBean.getForbidClasses()) && serverConfigBean.getForbidClasses().contains(className)) {
			log.warn("checkClassAndPackage error. with interface is forbidden. with class={}", clazz);
			return false;
		}
		
		//检查是否在允许的包内
		if (CollectionUtils.isNotEmpty(serverConfigBean.getAllowPackages())) {
			for (String str : serverConfigBean.getAllowPackages()) {
				if (packageName.startsWith(str)) {
					return true;
				}
			}
		}
		
		//检查是否是允许的类
		if (CollectionUtils.isNotEmpty(serverConfigBean.getAllowClasses()) && serverConfigBean.getAllowClasses().contains(className)) {
			return true;
		}
		
		log.warn("checkClassAndPackage error. with interface is not in allow. with class={}", clazz);
		return false;
	}
	
	/**
	 * 注册服务
	 * @param clazz
	 * @param beanName
	 */
	private void registry(Class<?> clazz, String beanName) {
		if (clazz == null) {
			return;
		}
		Method[] methods = clazz.getDeclaredMethods();
		if (ArrayUtils.isEmpty(methods)) {
			return;
		}
		for (Method m : methods) {
			if (!checkMethod(clazz.getName(), m.getName())) {
				//方法不允许注册
				continue;
			}
			try {
				//创建服务节点
				ZookeeperHelper.createNode(zooKeeper, zkConfig.getBasePath() + "/" + clazz.getName() + "." + m.getName(), null, CreateMode.PERSISTENT);
				
				//创建服务数据
				String uuid = UUID.randomUUID().toString();
				RegisterBean register = new RegisterBean();
				register.setBeanName(beanName);
				register.setClassName(clazz);
				register.setIp(StringUtil.fetchLocalIP());
				register.setMethodName(m.getName());
				register.setPackageName(clazz.getPackage().getName());
				register.setPort(serverConfigBean.getPort());
				register.setRegisterTime(DateUtil.getCurrentTimeSeconds());
				register.setUuid(uuid);
				ZookeeperHelper.createNode(zooKeeper, zkConfig.getBasePath() + "/" + clazz.getName() + "." + m.getName() + "/" + uuid, register.toString(), CreateMode.EPHEMERAL);
			} catch (Exception e) {
				log.error("registry error. with class={}, method={}", clazz, m.getName(), e);
				continue;
			}
		}
	}
	
	/**
	 * 检查方法是否可以注册
	 * @param className
	 * @param methodName
	 * @return
	 */
	private boolean checkMethod(String className, String methodName) {
		if (StringUtils.isEmpty(className) || StringUtils.isEmpty(className)) {
			log.warn("checkMethod error. with className is empty or methodName is empty. with className={}, methodName={}", className, methodName);
			return false;
		}
		
		//判断该方法是否在被禁止的范围内
		if (CollectionUtils.isNotEmpty(serverConfigBean.getForbidClassMethods()) && serverConfigBean.getForbidClassMethods().contains(className + "." + methodName)) {
			log.warn("checkMethod error. with method is forbidden. with className={}, methodName={}", className, methodName);
			return false;
		}
		
		//判断该方法是否在允许的范围内
		if (CollectionUtils.isEmpty(serverConfigBean.getAllowClassMethods())) {
			return true;
		}
		if (serverConfigBean.getAllowClassMethods().contains(className + "." + methodName)) {
			return true;
		}
		log.warn("checkMethod error. with method is not in allow. with className={}, methodName={}", className, methodName);
		return false;
	}


	public RPCServerConfigBean getServerConfigBean() {
		return serverConfigBean;
	}

	public void setServerConfigBean(RPCServerConfigBean serverConfigBean) {
		this.serverConfigBean = serverConfigBean;
	}

	public ZKConfig getZkConfig() {
		return zkConfig;
	}

	public void setZkConfig(ZKConfig zkConfig) {
		this.zkConfig = zkConfig;
	}

}
