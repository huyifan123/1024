package com.briup.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.briup.bean.UserLog;
import com.briup.util.SqlHelper;

public class ULog {
	//返回log对象，获取时间及曾用密码,获得密保返回问题及答案
	public UserLog log(String username) {
		String sql="select * from userlog where username='"+username+"'";
		return SqlHelper.getInstance().logQuery(sql);	
	}
	//刷新登录的时间
	public void resetTime(String username) {
		String sql="update userlog set user_last_login_time=to_char(sysdate,'YYYY-MM-DD HH24:MI:SS') where username='"+username+"'";
		SqlHelper.getInstance().update(sql);
	}
	//验证密保问题
	public void logAnswer(String username) {
		String[] qus = {"您妈妈的名字是什么？","您手机尾号是什么？","您最喜欢的水果是什么？"};
		String[] ans = {log(username).getP_answer1(),log(username).getP_answer2(),log(username).getP_answer3()};

        int i=(int)(Math.random()*qus.length);
        System.out.println(qus[i]);
        Scanner sc=new Scanner(System.in);
    	String an=sc.next();
    	if(an.equals(ans[i])) {
    		//修改密码，更新newsuser
    		System.out.println("回答正确，请输入您的新密码");
    		String newps=sc.next();
    		SqlHelper.getInstance().reSetWord(newps,username);
    	}else {
    		System.out.println("回答错误，请您再好好想想");
    	}
		
		
	}
	

}
