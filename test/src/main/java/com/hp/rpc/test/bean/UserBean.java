/**
 * 
 */
package com.hp.rpc.test.bean;

import com.hp.core.common.beans.BaseBean;

/**
 * @author ping.huang
 * 2016年12月12日
 */
public class UserBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3825640567289415596L;

	private String userName;
	private int userId;
	private String mobile;
	
	public UserBean(){}
	
	/**
	 * @param userName
	 * @param userId
	 * @param mobile
	 */
	public UserBean(String userName, int userId, String mobile) {
		super();
		this.userName = userName;
		this.userId = userId;
		this.mobile = mobile;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
