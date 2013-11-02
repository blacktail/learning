package com.learning.web;

import com.learning.bean.Service;
import com.learning.bean.Status;
import com.google.gson.Gson;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.sql.*;

public class ServiceServlet extends HttpServlet {
	private void sendResponse(HttpServletResponse res, Status status)
			throws IOException {
		Gson gson = new Gson();

		res.getOutputStream().print(gson.toJson(status));
		res.getOutputStream().flush();
	}

	public void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("application/json");
		Gson gson = new Gson();

		Status status = new Status();

		try {
			StringBuilder sb = new StringBuilder();
			String s;

			while ((s = request.getReader().readLine()) != null) {
				sb.append(s);
			}

			Service serv = gson.fromJson(sb.toString(), Service.class);

			if ("".equals(serv.getName())) {
				status.setStatus(false);
            	status.setMsg("Name is required.");
            	sendResponse(response, status);

            	return;
			}

			DataSource ds = (DataSource) getServletContext().getAttribute("dataSource");
			Connection con = ds.getConnection();

			//Create Query
            String query = "INSERT INTO learning.service (name, globalSmAlertCond, individualAlertLevel," +
            	"description, overallAlertLevel, pollingInterval, transition, url, parentId) " + 
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
            PreparedStatement dbStatement = null;

			dbStatement = con.prepareStatement(query);
			dbStatement.setString(1, serv.getName());
			dbStatement.setInt(2, serv.getGlobalSmAlertCond());
			dbStatement.setString(3, serv.getIndividualAlertLevel());
			dbStatement.setString(4, serv.getDescription());
			dbStatement.setString(5, serv.getOverallAlertLevel());
			dbStatement.setInt(6, serv.getPollingInterval());
			dbStatement.setInt(7, serv.getTransition());
			dbStatement.setString(8, serv.getUrl());
			dbStatement.setInt(9, serv.getParentId());

			dbStatement.executeUpdate();

			status.setStatus(true);
			status.setMsg(serv.getName());
			status.setData(serv);

			sendResponse(response, status);
		} catch(Exception ex) {
			ex.printStackTrace();

			status.setStatus(false);
			status.setMsg(ex.getMessage());

			sendResponse(response, status);
		}
	}
}
