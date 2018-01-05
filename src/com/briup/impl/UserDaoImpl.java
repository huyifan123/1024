package com.briup.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.briup.bean.User;
import com.briup.bean.UserLog;
import com.briup.dao.UserDao;
import com.briup.util.SqlHelper;

public class UserDaoImpl implements UserDao {
    private SqlHelper sqlHlper;
    private Connection con=null;
    private PreparedStatement sta=null;
    
    public  UserDaoImpl(SqlHelper sqlHlper) {
		this.sqlHlper=sqlHlper;
	}

	public void insertUser(User user,UserLog userlog) {		
		try {
			con=sqlHlper.getConnection();			
			User u=checkPassword(user.getUserName());
			if(u==null) {
			  con.setAutoCommit(false);
			  sta=con.prepareStatement("insert into newsuser(username,password) values(?,?)");
		      sta.setString(1, user.getUserName());
		      sta.setString(2, user.getPassword());
		      sta.executeUpdate();
		      //同时记录进log表
		      sta=con.prepareStatement("insert into userlog(username,user_last_login_time,p_answer1,p_answer2,p_answer3) values(?,to_char(sysdate,'YYYY-MM-DD HH24:MI:SS'),?,?,?)");		      
		      sta.setString(1,user.getUserName());
		      sta.setString(2,userlog.getP_answer1());
		      sta.setString(3,userlog.getP_answer2());
		      sta.setString(4,userlog.getP_answer3());
		     /* 连接|
		      StringBuffer sb=new StringBuffer(user.getPassword());
		      String p=sb.toString();+"&"*/		      
		      sta.executeUpdate();
		      con.setAutoCommit(true);
		      con.commit();		           
			}else {
				System.out.println("用户已存在，请登录");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			sqlHlper.close(con, sta, null);
		}
		
	}

	public User checkPassword(String u) {
		User user=null;	
		try {
			con=sqlHlper.getConnection();
			String sql="select username,password,usergrade from newsuser where username='"+u+"'";
			sta=con.prepareStatement(sql);
			ResultSet res=sta.executeQuery();
			//
			if(res.next()) {
				user=new User(res.getString("username"),res.getString("password"),res.getInt("usergrade"));				
			}
		} catch (Exception e) {		
		}//不能关闭，调用者还要用
		return user;
	}
    //删除用户	
	public User checkUser(String u) {
		User user=null;	
		try {
			con=sqlHlper.getConnection();
			String sql="select username,password,usergrade from newsuser where username='"+u+"'";
			sta=con.prepareStatement(sql);
			ResultSet res=sta.executeQuery();
			//
			if(res.next()) {
				user=new User(res.getString("username"),res.getString("password"),res.getInt("usergrade"));				
			}
		} catch (Exception e) {		
		}finally {
			sqlHlper.close(con, sta, null);
		}
		return user;
	}
}
