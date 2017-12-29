package cn.gn.model;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class MyNews {
	public void inserNews(String title,String date,String content,String newsPath) throws SQLException, Exception{
		
		String sql = "insert into news(ntitle,ntime,ncontent,nhtmlpath) values('"+title+"','"+date+"','"+content+"','"+newsPath+"')";
		System.out.println(sql);
		DbUtils.execute(sql);
	}
	public List<Map> queryNews() throws SQLException, Exception{
		String sql = "select * from news";
		return DbUtils.query(sql);
	}
}
