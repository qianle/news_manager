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
 * jdbc��װ��
 * @author Administrator
 *
 */
public class DbUtils {
	private static final String String = null;
	//����һ��Properties���������洢��jdbc.properties��ȡ������
	static Properties p = new Properties();
	
	static{
		//����һ����ȡ�� ��ȡjdbc.properties������
		InputStream is =DbUtils.class.getResourceAsStream("/jdbc.properties") ;
		try {
			//��properties�����ȡ���洢is��������
			p.load(is);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * ��ȡ����
	 * @return
	 * @throws Exception 
	 */
	public static Connection getConnection() throws Exception{
		//���������ȡjdbc.Properties��keyΪurl��valueֵ    valueֵ��jdbc���ӵ����ݿ��������ip �˿� ���ݿ���
		String url = p.getProperty("url");
		//���������ȡjdbc.Properties��keyΪdriverClass��valueֵ   valueֵ�Ǹ���jdbcʹ�õ���ʲô���ݿ� ��ͬ�����ṩһЩ��ͬ����
		String driverClass=p.getProperty("driverClass");
		//���������ȡjdbc.Properties��keyΪuserName��valueֵ   valueֵ�����ݿ��û��ʺ�
		String userName=p.getProperty("userName");
		//���������ȡjdbc.Properties��keyΪpassword��valueֵ  valueֵ�����ݿ��û�����
		String password=p.getProperty("password");
		//���ص���
		Class.forName(driverClass);
		//�������ݿ� ����ɹ�
		Connection conn = DriverManager.getConnection(url, userName, password);
		//�����������ݿ����Ϣ
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
