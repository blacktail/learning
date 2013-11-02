package com.learning;

import javax.servlet.*;
import javax.servlet.http.*;
import com.mysql.jdbc.jdbc2.optional.*;
import java.sql.*;
import javax.sql.*;

public class LearningServletContextListener implements ServletContextListener {
	private MysqlDataSource ds = null;

	public void contextInitialized(ServletContextEvent event) {
		ServletContext sc = event.getServletContext();
		String jdbcurl = sc.getInitParameter("jdbcURL");
		String user = sc.getInitParameter("user");
		String password = sc.getInitParameter("password");

		try {
			ds = new MysqlDataSource();
			ds.setUrl(jdbcurl);
			ds.setUser(user);
			ds.setPassword(password);
		} catch (Exception e) {
			sc.log("Can not create datasource: " + e.getMessage());
			return;
		}

		sc.setAttribute("dataSource", ds);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		sc.removeAttribute("dataSource");
		ds = null;
	}
}
