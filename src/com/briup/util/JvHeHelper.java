package com.briup.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;

import com.briup.bean.News;
import com.briup.impl.KeyWordSelect;

public class JvHeHelper {
	
	 String type=null;
	 StringBuilder stringBuilder=null;
	 BufferedReader br = null;
	 List<News> newsList=new ArrayList<News>();
	 String Content=null;
public JvHeHelper() {}

public List<News> con(String category) throws IOException{	 
	   try {
	    URL url=new URL("http://v.juhe.cn/toutiao/index?type="+type+"&key=cdee1f6f16dd9a5228165b8b5738fbd1");        	    	    
	    HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setRequestMethod("GET");
		connect.setReadTimeout(80000);
		connect.setConnectTimeout(80000);
		connect.connect();				
		if (connect.getResponseCode() == HttpURLConnection.HTTP_OK){
			br = new BufferedReader(new InputStreamReader(connect.getInputStream()));
			String s = null;
			stringBuilder = new StringBuilder();	
			while ((s = br.readLine())!= null) {
				stringBuilder.append(s + "\n");
			}
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
		    connect = (HttpURLConnection)ContentUrl.openConnection();		
		    connect.setReadTimeout(80000);
			connect.setConnectTimeout(80000);
			connect.connect();		
		    //每申请一个url就读取一次，网慢
		    InputStream in = new BufferedInputStream(ContentUrl.openStream()); 		    
		    InputStreamReader theHTML = new InputStreamReader(in,"UTF-8"); 

		    int c; 
		    while ((c = theHTML.read()) != -1) { 
		           sb.append((char)c);            
		    }
		           String str=sb.toString();
		         //获取内容
		           if(str.indexOf("<p class=\"section txt\">")>-1) { 
				       Content = str.substring(str.indexOf("<p class=\"section txt\">")+23,str.indexOf("</p>"));		        	
		        	   News news = new News(jsonTitle,jsonCategory,jsonDate,Content);		        	  
		   		       newsList.add(news);	
		   		    
		           }	    
		}	}catch(Exception e){
			System.out.println("网速慢");
		}	
		 br.close();	
		 return newsList;
	}		 
	
}	      	

