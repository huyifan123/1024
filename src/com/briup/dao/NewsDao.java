package com.briup.dao;

import com.briup.bean.News;

/*
 * �����ŵĹ淶
 */
public interface NewsDao {
public News watchByCategory(int category) throws Exception;
public News insertNews(News news);
}
