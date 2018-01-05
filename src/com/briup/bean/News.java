package com.briup.bean;

public class News {
private long neewid ;
private String newstitle ;
private String type;
private String date;
private String newcontent;
public News() {}
public News(String newstitle, String type, String date, String newcontent) {	
	this.newstitle = newstitle;
	this.type = type;
	this.date = date;
	this.newcontent = newcontent;
}
public String getNewstitle() {
	return newstitle;
}
public void setNewstitle(String newstitle) {
	this.newstitle = newstitle;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getNewcontent() {
	return newcontent;
}
public void setNewcontent(String newcontent) {
	this.newcontent = newcontent;
}
@Override
public String toString() {
	return "News [newstitle=" + newstitle + ", type=" + type + ", date=" + date + ", newcontent="
			+ newcontent + "]";
}

}
