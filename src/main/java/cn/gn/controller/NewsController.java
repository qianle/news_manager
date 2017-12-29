package cn.gn.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gn.model.MyNews;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Servlet implementation class NewsController
 */
public class NewsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewsController() {
        super();
        // TODO Auto-generated constructor stub
    }

    MyNews myNews = new MyNews();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String HTML_DIR = "E:/html/";
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;chaset=UTF-8");
		PrintWriter  out = response.getWriter();
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		Date date = new Date();
		String dateTime = dateFormat.format(date);
		System.out.println(dateTime);
		String uuid = UUID.randomUUID().toString();
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
		//配置ftl查找目录
		configuration.setDirectoryForTemplateLoading(new File("src/main/resources"));
		//设置数据的抓取模式
		configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_23));
		Map root = new HashMap();
		root.put("title", title);
		root.put("content", content);
		root.put("time", dateTime);
		//实例化模版对象
		Template temp = configuration.getTemplate("newsdetail.ftl");
		String  newsPath = HTML_DIR+uuid+".html";
		FileOutputStream fos = new FileOutputStream(newsPath);
		//生成html 输出到目标
		Writer writer = new OutputStreamWriter(fos);
		try {
			temp.process(root, writer);
			writer.flush();
		
		myNews.inserNews(title, dateTime,content, uuid+".html");
		out.println("发布新闻成功！");
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
