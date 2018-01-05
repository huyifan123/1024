package com.briup.util;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import com.briup.bean.News;

public class JdbcHelper {
String sql=null;   
SqlHelper sh=new SqlHelper();
public JdbcHelper() {}

//匹配查询并返回新闻结果
public  List<News> SqlSelect(String sql){
	
	 //Connection con=sh.getConnection();
	 List<News> s=sh.executeQuery(sql);	
	 return s;	
}
//插入新闻
public void SqlInsert(String title,String date,String category,String content){                            
   sql="insert into news(newstitle,newsdate,newscategory,newscontent) values('"+title+"',to_date('"+date+"','yyyy-mm-dd hh24:mi'),'"+category+"','"+content+"')";
   Connection con=sh.getConnection();   
   Statement sta=sh.executeUpdate(sql,con); 
   sh.close(con, sta, null);
}
//删除某天新闻???????????????????日期转换  插入的格式和读取的格式不同
public void Sqldelete(String date){
	   sql="delete from news where to_char(newsdate)=to_char(to_date('"+date+"','yyyy-mm-dd hh24:mi'))";
	   //判断是否有那天的新闻,返回集合	   
	   if(sh.executeQuery("select * from news where to_char(newsdate)=to_char(to_date('"+date+"','yyyy-mm-dd'))").isEmpty()) {
		   System.out.println("****此日期的新闻不存在****");
		   System.out.println("");
	   }else {
	   sh.deleteNews(sql);
	   }
	}
}
