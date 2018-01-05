package com.briup.impl;

import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.briup.bean.News;
import com.briup.bean.User;
import com.briup.util.JdbcHelper;
import com.briup.util.SqlHelper;

public class SysOperation {
	private Scanner sc=new Scanner(System.in);
	User u=null;
//处理管理员事务
public void opt(int i,User u){
	if(i==1) {
		//删除普通用户
		System.out.println("请输入要删除用户的用户名");
		String user=sc.next();
		delUser(user);
	}else if(i==2){
		List<News> newsList=null;
		try {
			newsList = new SeqSelect().watch();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    if(newsList!=null) {
		    for(News news : newsList) {
			  System.out.print("【");
			  System.out.print(news.getNewstitle());
			  System.out.println("】");
			  System.out.println(news.getNewcontent());
			}
		}else if(newsList==null){
			System.out.println("未找到相关内容");
		}
	}else if(i==3) {
		//删除新闻
		System.out.println("要删除哪一天的新闻");
		System.out.println("（时间格式为yyyy-MM-dd）");
		String time=sc.next();
		//判断格式
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		//验证输入格式是否正确
		//System.out.println(date);Tue Nov 28 00:00:00 CST 2017
	    //String day= date.toString();Tue Nov 28 00:00:00 CST 2017
		try {
			date = format.parse(time); //导包 util sql
			String day= format.format(date); //yyyy-MM-dd
			//格式正确后，执行删除
			new JdbcHelper().Sqldelete(day);
		} catch (Exception e) {
			System.out.println("*******格式错误*******");
			System.out.println("");
		}	
		
		   
		    
			
	}else if(i==4){
		//管理员改自己的密码
		ULog ul=new ULog();
		ul.logAnswer(u.getUserName());
	}else {
		System.out.println("输入非法字符");
	}
}

//删除普通用户
public void delUser(String username) {
	UserDaoImpl udi=new UserDaoImpl(SqlHelper.getInstance());
	//判断用户是否存在
	User u=udi.checkPassword(username);
	if(!(u==null)) {
	String sql1="delete from userlog where username='"+username+"'";
	String sql2="delete from newsuser where username='"+username+"'";
	SqlHelper.getInstance().deleteUser(sql1, sql2);
	}else {
		System.out.println("****操作失败，此用户不存在****");
		System.out.println("");
	}
}

//删除新闻
public void delNews(String day) {
	new JdbcHelper().Sqldelete(day);
}
}
