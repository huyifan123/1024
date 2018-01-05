package com.briup.dao;

import com.briup.bean.News;

/*
 * 对新闻的规范
 */
public interface NewsDao {
public News watchByCategory(int category) throws Exception;
public News insertNews(News news);
}
