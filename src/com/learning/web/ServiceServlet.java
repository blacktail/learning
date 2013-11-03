package com.learning.web;

import com.learning.bean.Service;
import com.learning.bean.Status;
import com.google.gson.*;
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

			ResultSet rs = dbStatement.getGeneratedKeys();
			int autoIncKey = 0;

			if (rs.next()) {
				autoIncKey = rs.getInt(1);
			}

			serv.setId(autoIncKey);

			status.setStatus(true);
			status.setData(serv);

			sendResponse(response, status);
		} catch(Exception ex) {
			ex.printStackTrace();

			status.setStatus(false);
			status.setMsg(ex.getMessage());

			sendResponse(response, status);
		}
	}

	public void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("application/json");
		Gson gson = new Gson();
		Status status = new Status();

		try {
			int id;
			id = Integer.parseInt(request.getParameter("id"));

			DataSource ds = (DataSource) getServletContext().getAttribute("dataSource");
			Connection con = ds.getConnection();

			 //Create Query
			String query = "select * from service where id=?";

			PreparedStatement dbStatement = null;

			dbStatement = con.prepareStatement(query);
			dbStatement.setInt(1, id);
			ResultSet rs = dbStatement.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			JsonObject element = null;

			if (rs.next()) {
				element = new JsonObject();

				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					String columnName = rsmd.getColumnName(i + 1);

					if (rsmd.getColumnType(i + 1) == java.sql.Types.INTEGER) {
						element.addProperty(columnName, rs.getInt(columnName));
					} else if (rsmd.getColumnType(i + 1) == java.sql.Types.BOOLEAN) {
						element.addProperty(columnName, rs.getBoolean(columnName));
					} else {
						element.addProperty(columnName, rs.getString(columnName));
					}
				}
			}

			if (null == element) {
				status.setStatus(false);
				status.setMsg("service is not found");
			} else {
				status.setStatus(true);
				status.setData(element);
			}

			sendResponse(response, status);
		} catch (Exception ex) {
			ex.printStackTrace();

			status.setStatus(false);
			status.setMsg(ex.getMessage());

			sendResponse(response, status);
		}
	}

	public void doPut(HttpServletRequest request,
			HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("application/json");
		Gson gson = new Gson();
		Status status = new Status();

		try {
			int id = Integer.parseInt(request.getParameter("id"));

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
            String query = "UPDATE learning.service set name=?, globalSmAlertCond=?, individualAlertLevel=?," +
				"description=?, overallAlertLevel=?, pollingInterval=?, transition=?, url=?, parentId=? WHERE " + 
				"id=?";
	
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
			dbStatement.setInt(10, id);

			dbStatement.executeUpdate();

			status.setStatus(true);
			status.setData(serv);

			sendResponse(response, status);
		} catch(Exception ex) {
			ex.printStackTrace();

			status.setStatus(false);
			status.setMsg(ex.getMessage());

			sendResponse(response, status);
		}
	}

	public void doDelete(HttpServletRequest request,
			HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("application/json");
		Gson gson = new Gson();
		Status status = new Status();

		try {
			int id;
			id = Integer.parseInt(request.getParameter("id"));

			DataSource ds = (DataSource) getServletContext().getAttribute("dataSource");
			Connection con = ds.getConnection();

			 //Create Query
			String query = "delete from service where id=?";

			PreparedStatement dbStatement = null;

			dbStatement = con.prepareStatement(query);
			dbStatement.setInt(1, id);
			int rowCount = dbStatement.executeUpdate();

			if (rowCount > 0) {
				status.setStatus(true);
				sendResponse(response, status);
			} else {
				status.setStatus(false);
				status.setMsg("Service is not existed.");
				response.setStatus(403);
				sendResponse(response, status);
			}

		} catch (Exception ex) {
			ex.printStackTrace();

			status.setStatus(false);
			status.setMsg(ex.getMessage());

			sendResponse(response, status);
		}
	}
}
