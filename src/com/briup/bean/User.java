package com.briup.bean;

public class User {
   private String userName;
   private String password;
   private int userGrade;
public User() {}
public User(String userName, String password, int userGrade) {
	super();
	this.userName = userName;
	this.password = password;
	this.userGrade = userGrade;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public int getUserGrade() {
	return userGrade;
}
public void setUserGrade(int userGrade) {
	this.userGrade = userGrade;
}
   
}
