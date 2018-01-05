package com.briup.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.briup.bean.News;
import com.briup.bean.UserLog;
import com.briup.util.JvHeHelper;
import com.briup.util.SqlHelper;

public class SeqSelect {
	 int category=0;
	 String type=null;
	 List<News> newsList=new ArrayList<News>();	
	 JvHeHelper jvhehelper=new JvHeHelper();
public SeqSelect() {}	

public List<News> watch() throws Exception {
	System.out.println("请选择新闻类别");
	System.out.println("1.娱乐");
	System.out.println("2.军事");
	System.out.println("3.时尚");
	System.out.println("4.科技");
	System.out.println("5.体育");
	System.out.println("6.今日头条");
	System.out.println("7.关键字搜索");
	//返回新闻内容
	Scanner sc=new Scanner(System.in);
	int category=sc.nextInt();
	if(category==7) {
		//模糊查询KeyWord，涉及数据库
		System.out.println("请输入关键字");
    	sc=new Scanner(System.in);
    	String keyWord=sc.next();
    	//得到结果
    	List<News> consequence = null;
		try {
			consequence = new KeyWordSelect().display(keyWord);
		} catch (Exception e) {
		}
    	return consequence;   	
	}else{
	switch (category) {
	case 1:
		type="yule";
		break;
    case 2:
    	type="junshi";
		break;
    case 3:
    	type="keji";
        break;
    case 4:
    	type="shishang";
        break;
    case 5:
    	type="tiyu";
        break;
    case 6:
    	type="top";
        break;
	default:
		break;
	}	
	List<News> newsList = jvhehelper.con(type);    //返回数据
	
	  return newsList;
    }    
}

}







