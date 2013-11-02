package com.learning.web;

import com.learning.bean.Status;
import com.google.gson.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.sql.*;

public class ServiceNodes extends HttpServlet {
	private void sendResponse(HttpServletResponse res, Status status)
			throws IOException {
		Gson gson = new Gson();

		res.getOutputStream().print(gson.toJson(status));
		res.getOutputStream().flush();
	}

	public void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws IOException, ServletException {

		response.setContentType("application/json");

		int parentId;

		try {
			parentId = Integer.parseInt(request.getParameter("id"));
		} catch (Exception ex) {
			parentId = 1;
		}

		Status status = new Status();

		try {
			DataSource ds = (DataSource) getServletContext().getAttribute("dataSource");
			Connection con = ds.getConnection();

			 //Create Query
			String query = "select id, name, 1 as isParent from parent_nodes where parentId=? union " +
				"select id, name, 0 as isParent from service where parentId=?";

			PreparedStatement dbStatement = null;

			dbStatement = con.prepareStatement(query);
			dbStatement.setInt(1, parentId);
			dbStatement.setInt(2, parentId);

			ResultSet rs = dbStatement.executeQuery();
			ResultSetMetaData rsmd = null;
			JsonObject element = null;
			JsonArray ja = new JsonArray();

			rsmd = rs.getMetaData();

			while (rs.next()) {
				element = new JsonObject();

				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					String columnName = rsmd.getColumnName(i + 1);

					if (columnName.equals("isParent") || rsmd.getColumnType(i + 1) == java.sql.Types.INTEGER) {
						element.addProperty(columnName, rs.getInt(columnName));
					} else if (rsmd.getColumnType(i + 1) == java.sql.Types.BOOLEAN) {
						element.addProperty(columnName, rs.getBoolean(columnName));
					} else {
						element.addProperty(columnName, rs.getString(columnName));
					}
				}

				ja.add(element);
			}

			status.setStatus(true);
			status.setData(ja);

			sendResponse(response, status);
		} catch (Exception e) {
			e.printStackTrace();

			status.setStatus(false);
			status.setMsg(e.getMessage());

			sendResponse(response, status);
		}
	}
}
