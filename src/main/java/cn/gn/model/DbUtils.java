package cn.gn.model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;



/**
 * jdbc封装类
 * @author Administrator
 *
 */
public class DbUtils {
	private static final String String = null;
	//定义一个Properties对象用来存储从jdbc.properties读取的数据
	static Properties p = new Properties();
	
	static{
		//定义一个读取流 读取jdbc.properties的数据
		InputStream is =DbUtils.class.getResourceAsStream("/jdbc.properties") ;
		try {
			//用properties对象读取并存储is流的数据
			p.load(is);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取连接
	 * @return
	 * @throws Exception 
	 */
	public static Connection getConnection() throws Exception{
		//定义变量获取jdbc.Properties里key为url的value值    value值是jdbc连接的数据库服务器的ip 端口 数据库名
		String url = p.getProperty("url");
		//定义变量获取jdbc.Properties里key为driverClass的value值   value值是告诉jdbc使用的是什么数据库 不同数据提供一些不同类型
		String driverClass=p.getProperty("driverClass");
		//定义变量获取jdbc.Properties里key为userName的value值   value值是数据库用户帐号
		String userName=p.getProperty("userName");
		//定义变量获取jdbc.Properties里key为password的value值  value值是数据库用户密码
		String password=p.getProperty("password");
		//加载的类
		Class.forName(driverClass);
		//连接数据库 登入成功
		Connection conn = DriverManager.getConnection(url, userName, password);
		//返回连接数据库的信息
		return conn; 
	}
	public static List<Map> query(String sql) throws SQLException, Exception{
		PreparedStatement pst = getConnection().prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		ResultSetMetaData rsmd = pst.getMetaData();
		int count = rsmd.getColumnCount();
		List<Map> list = new ArrayList<Map>();
		while(rs.next()){
			Map map = new HashMap();
			for(int i = 1;i<=count;i++){
				String key = rsmd.getColumnName(i);
				String value = rs.getString(i);
				map.put(key, value);
			}
			list.add(map);
			
		}
		return list;
	}
	public static void execute(String sql) throws SQLException, Exception{
		PreparedStatement pst = getConnection().prepareStatement(sql);
		pst.executeUpdate();
	}
	
}
