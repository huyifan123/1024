package com.briup.main;

import java.util.List;
import java.util.Scanner;

import com.briup.bean.News;
import com.briup.bean.User;
import com.briup.bean.UserLog;
import com.briup.impl.SeqSelect;
import com.briup.impl.SysOperation;
import com.briup.impl.ULog;
import com.briup.impl.UserDaoImpl;
import com.briup.util.JvHeHelper;
import com.briup.util.SqlHelper;

public class NewsView {
	
	private static UserDaoImpl userDaoImpl;
	
public static void main(String[] args){

	userDaoImpl=new UserDaoImpl(SqlHelper.getInstance());
	System.out.println("新闻客户端");
	System.out.println("请选择");
	System.out.println("1.注册");
	System.out.println("2.登录");
	
	Scanner sc=new Scanner(System.in);
	String type=sc.next();
	//注册
	if(type.equals("1")) {
		System.out.println("请输入用户名");
		String username=sc.next();
		System.out.println("请输入密码");
		String password=sc.next();
		System.out.println("请再次输入密码");
		String password2=sc.next();
		
		if(username.equals("")||password.equals("")||password2.equals("")) {			
			System.out.println("用户名或密码不能为空");	
		}
		if(password.equals(password2)) {
			System.out.println("问题1：您妈妈的名字是什么？");
			String pass1=sc.next();
			System.out.println("问题2：您手机尾号是什么？");
			String pass2=sc.next();
			System.out.println("问题3：您最喜欢的水果是什么？");
			String pass3=sc.next();
			//UserLog u=
			//System.out.println(u);
			userDaoImpl.insertUser(new User(username,password,3),new UserLog(username,null,pass1,pass2,pass3));
			System.out.println("注册成功");
		}
	//登录
	}else if(type.equals("2")) {
		System.out.println("请输入用户名");
		String username=sc.next();
		System.out.println("请输入密码");
		String password=sc.next();		
		User user=userDaoImpl.checkPassword(username);
		if(user==null) {
			System.out.println("用户不存在，去注册");
		}else if(!password.equals(user.getPassword())){
			//找回密码
			System.out.println("密码错误");
			System.out.println("1.通过密保重置密码");
			System.out.println("2.退出");
			int i=sc.nextInt();
			if(i==1) {
				//密保找回
				ULog ul=new ULog();
				ul.logAnswer(username);
											/*获取曾用密码字符串-----暂时放弃
											String userwords;
											userwords=new SeqSelect().log(username).getUserWords();
											String[] words=userwords.split("[|]");
											if(words.length==0) {
												System.out.println("");
											}				*/				
			}else if(i==2) {
				return;
			}else {
				System.out.println("输入有误");
			}
		}
		else {
			if(user.getPassword().equals(password)) {
				ULog ul=new ULog();
				System.out.println("登录成功");
                System.out.println("上次登录时间："+ul.log(user.getUserName()).getLastTime());
                //重置数据库时间
                ul.resetTime(user.getUserName());
                //判断权限,进入不同操作界面
                while(true) {
                if(user.getUserGrade()==2) {
                	System.out.println("请选择");
    				System.out.println("1.注销普通用户");
    				System.out.println("2.查看新闻");
    				System.out.println("3.删除新闻");
    				System.out.println("4.修改密码");
    				int operate=sc.nextInt();
    				new SysOperation().opt(operate,user);
                }else {
				//新闻选择
				List<News> newsList=null;
				try {
					newsList = new SeqSelect().watch();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(newsList!=null){
				for(News news : newsList) {
					  System.out.print("【");
					  System.out.print(news.getNewstitle());
					  System.out.println("】");
					  System.out.println(news.getNewcontent());
					}
				}else if(newsList==null){
					System.out.println("未找到相关内容");
				}
                }
			}}else {
				System.out.println("密码错误");
			}
		}		
	}else {
		System.out.println("输入非法字符");
	}
}
}