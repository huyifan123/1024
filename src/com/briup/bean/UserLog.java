package com.briup.bean;

public class UserLog {
    private String userName;
	//private String userWords;//曾用过的所有密码
	private String lastTime ;// 最后一次登录时间
	private String p_answer1;
	private String p_answer2;
	private String p_answer3;
	public UserLog() {}
	public UserLog(String userName, String lastTime, String p_answer1, String p_answer2, String p_answer3) {
		super();
		this.userName = userName;
		this.lastTime = lastTime;
		this.p_answer1 = p_answer1;
		this.p_answer2 = p_answer2;
		this.p_answer3 = p_answer3;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	public String getP_answer1() {
		return p_answer1;
	}
	public void setP_answer1(String p_answer1) {
		this.p_answer1 = p_answer1;
	}
	public String getP_answer2() {
		return p_answer2;
	}
	public void setP_answer2(String p_answer2) {
		this.p_answer2 = p_answer2;
	}
	public String getP_answer3() {
		return p_answer3;
	}
	public void setP_answer3(String p_answer3) {
		this.p_answer3 = p_answer3;
	}
	@Override
	public String toString() {
		return "UserLog [userName=" + userName + ", lastTime=" + lastTime + ", p_answer1=" + p_answer1 + ", p_answer2="
				+ p_answer2 + ", p_answer3=" + p_answer3 + "]";
	}
	
}
