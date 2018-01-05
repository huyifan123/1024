package com.briup.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.briup.bean.News;
import com.briup.bean.UserLog;


public class SqlHelper {
	
	public static SqlHelper instance=null;
	
	private Connection con=null;	
	private Properties properties=null;
	private FileInputStream fis=null;
	private PreparedStatement sta=null;
	private ResultSet res=null;
	private String driver="";
	private String url="";
	private String userName="";
	private String passWord="";
	private UserLog u = null;
	
	public static SqlHelper getInstance() {
		if(instance==null) {
			instance=new SqlHelper();
		}
		return instance;		
	}
	
	//1.
	 SqlHelper(){
    	try {
    	
    	properties=new Properties();
    	fis=new FileInputStream("dbinfo.properties");
    	properties.load(fis);
    	driver=properties.getProperty("driver");
    	url=properties.getProperty("url");
    	userName=properties.getProperty("userName");
    	passWord=properties.getProperty("passWord");
        
    	Class.forName(driver);
    	}catch(Exception e) {  		
    	}
    } 
    //2.
    public Connection getConnection() {
    	try {
			con=DriverManager.getConnection(url,userName,passWord);                                                                                                                                                                                                                                                                                                                                                                                                                   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return con;
    }
    //插入新闻
    public Statement executeUpdate(String sql,Connection c) {
    	try {
    	//con=getConnection(); 
    	//con.setAutoCommit(true); 
    	//检查重复新闻
    		
    		
    	sta=c.prepareStatement(sql); 
 		sta.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sta;
    }
    //查询新闻
    public List<News> executeQuery(String sql){
    	List<News> list=new ArrayList<>();
    	try {
    		con=getConnection();   		  
    		sta=con.prepareStatement(sql);  		    		
    		res=sta.executeQuery();   		
    		News news=null;
    		while(res.next()) {
    			news=new News(res.getString("newstitle"),res.getString("newscategory"),res.getDate("newsdate").toString(),res.getString("newscontent"));
    			list.add(news);
    			news=null;
    		}
    	}catch(Exception e) {    		
    	}finally {
    		close(con, sta, res);  
    	}
		return list;  	
    }
    //查询log数据   
    public UserLog logQuery(String sql){   	
    	try {  		
    		con=getConnection();   		  
    		sta=con.prepareStatement(sql);   		
    		res=sta.executeQuery();
 
    		while(res.next()){                 //不用while就会出错？？？？？？？？？
    		u=new UserLog(res.getString("username"),res.getString("user_last_login_time"),res.getString("p_answer1"),res.getString("p_answer2"),res.getString("p_answer3"));
    		}
    	}catch(Exception e) {    		
    	}finally {
    		close(con, sta, res);  
    	}
		return u; 		
    }
    //更新时间
    public void update(String sql) {   			  
		try {
			con=getConnection();   
			sta=con.prepareStatement(sql);
			res=sta.executeQuery();			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
    		close(con, sta, res);  
    	}  				
	}   
    //修改用户密码
    public void reSetWord(String reword,String username) {
    	try {
    		con=getConnection();
    		String sql ="update newsuser set password='"+reword+"' where username='"+username+"'";   
			sta=con.prepareStatement(sql);
			res=sta.executeQuery();		
			System.out.println("****密码修改成功****");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
    		close(con, sta, res);  
    	}  	
    }
    //删除普通用户
    public void deleteUser(String sql1,String sql2) {   			  
		try {
						
			con=getConnection();  
			con.setAutoCommit(true);
			sta=con.prepareStatement(sql1);
			sta.executeUpdate();
			sta=con.prepareStatement(sql2);
			sta.executeUpdate();
			con.commit();
			System.out.println("****注销成功****");
			System.out.println("");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
    		close(con, sta, res);  
    	}  				
	}   
    //删除某一天新闻  sql的date
    public void deleteNews(String sql) {     	
		try {
			con=getConnection();  
			con.setAutoCommit(true);
			sta=con.prepareStatement(sql);
			sta.executeUpdate();
			System.out.println("****删除成功****");
			System.out.println("");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
    		close(con, sta, res);  
    	}  				
    }
    //5.
    public void close(Connection con,Statement sta,ResultSet rs) {
    	if(con!=null) {
			try {
				con.close();
				con=null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(sta!=null) {
			try {
				sta.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
}