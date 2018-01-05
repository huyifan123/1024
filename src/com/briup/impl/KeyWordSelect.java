package com.briup.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.briup.bean.News;
import com.briup.util.JdbcHelper;
import com.briup.util.JvHeHelper;

public class KeyWordSelect {
    JdbcHelper jdbchelper=new JdbcHelper();
    String type=null;
    List<String> newsList=new ArrayList<String>();	
    JvHeHelper jvhehelper=new JvHeHelper();
    StringBuilder stringBuilder=null;
    BufferedReader br = null;
    String Content=null;
public KeyWordSelect() {}
//最后结果显示到客户端
public List<News> display(String keyword) throws Exception {
	//先插入
	insert();	
	//再查询????????????????????????????每次会插入相同的内容distinct
	String sql="select distinct newstitle,newscategory,newsdate,newscontent from news where newscontent like '%"+keyword+"%'";	
	List<News> s=jdbchelper.SqlSelect(sql);
    if(s.isEmpty()){    //isEmpty集合空不空  null集合存不存在 
    	return null;
    }else {
	return s;
	}
}
//获取全部新闻，并插入到数据库
public void insert() throws Exception {	
	String[] allCategory= {"yule","junshi"};//,"keji","shishang","tiyu","guoji","caijing","top"};
	for(String type:allCategory) {
		type=allCategory.toString();
	
		URL url=new URL("http://v.juhe.cn/toutiao/index?type="+type+"&key=cdee1f6f16dd9a5228165b8b5738fbd1");        	    	    
	    HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setRequestMethod("GET");
		connect.setReadTimeout(8000);
		connect.setConnectTimeout(8000);
		connect.connect();				
		if (connect.getResponseCode() == HttpURLConnection.HTTP_OK){
			br = new BufferedReader(new InputStreamReader(connect.getInputStream()));
			String s = null;
			stringBuilder = new StringBuilder();	
			while ((s = br.readLine())!= null) {
				stringBuilder.append(s + "\n");
			}
			
		JSONObject jsonObject = new JSONObject(stringBuilder.toString());
		JSONObject result = jsonObject.getJSONObject("result");    
		JSONArray jsonArray = result.getJSONArray("data");
		for(int i=1;i<jsonArray.length();i++) {
			JSONObject obj = (JSONObject) jsonArray.get(i);
			String jsonTitle = obj.getString("title");			    
            String jsonDate = obj.getString("date");		    
		    String jsonCategory = obj.getString("category");		    
		    String jsonContent = obj.getString("url");
		    //获取url里的内容
		    StringBuffer sb = new StringBuffer(); 
		    URL ContentUrl=new URL(jsonContent);
		    InputStream in = new BufferedInputStream(ContentUrl.openStream()); 
		    InputStreamReader theHTML = new InputStreamReader(in,"UTF-8"); 
		    int c; 
		    while ((c = theHTML.read()) != -1) { 
		           sb.append((char)c); 		           
		    }
		           String str=sb.toString();		         
		           if(str.indexOf("<p class=\"section txt\">")>-1) { 
				       Content = str.substring(str.indexOf("<p class=\"section txt\">")+23,str.indexOf("</p>"));		        	
				       //往数据库插入所有类别新闻	    
					   jdbchelper.SqlInsert(jsonTitle,jsonDate,jsonCategory,Content);
				 }       	  			   		    
		           }	    
		}
		    
	}
	br.close();
	}
	
  }


