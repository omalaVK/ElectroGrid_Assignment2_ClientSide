package model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Schedule {
	private Connection connect() {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
//Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ElectroGrid", "root", "");
			System.out.println("Successfully Connecting");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Connecting");
		}
		return con;
	}
	
	public String InsertPowerCutDetails(String lineno, String areano, String areaname, String starttime, String endtime,
			String date, String reason) {
		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the DB for inserting";
			}

			String query = " insert into shedules (`sheduleId`,`lineNo`,`areaNo`,`areaName`,`startTime`,`endTime`,`date`,`reason`)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStat = con.prepareStatement(query);
			// values binding
			preparedStat.setInt(1, 0);
			preparedStat.setString(2, lineno);
			preparedStat.setString(3, areano);
			preparedStat.setString(4, areaname);
			preparedStat.setString(5, starttime);
			preparedStat.setString(6, endtime);
			preparedStat.setString(7, date);
			preparedStat.setString(8, reason);
			// Execute statement

			preparedStat.execute();
			con.close();
			output = "Power cut schedule details inserted successfully";
			
			String newSchedules = readPowerCutDetails();
	 		 output = "{\"status\":\"success\", \"data\": \"" + newSchedules + "\"}";

		} catch (Exception e) {
			output = "Error while inserting power cut schedule details";
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the schedule.\"}";
		 	System.err.println(e.getMessage());
			
		}
		return output;
	}
	
	public String readPowerCutDetails() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the DB for reading";
			}

			// html table to be display
			output = "<table border='1'><tr><th>Line Number</th><th>Area Number</th><th>Area Name</th>"
					+ "<th>Start Time</th><th>End Time</th>" + "<th>Date</th><th>Reason</th></tr>";
			String query = "select * from shedules";
			Statement stat = con.createStatement();
			ResultSet rs = stat.executeQuery(query);

			// iteration through the rows of result set
			while (rs.next()) {
				String sheduleId = Integer.toString(rs.getInt("sheduleId"));
				String lineNo = rs.getString("lineNo");
				String areaNo = rs.getString("areaNo");
				String areaName = rs.getString("areaName");
				String startTime = rs.getString("startTime");
				String endTime = rs.getString("endTime");
				String date = rs.getString("date");
				String reason = rs.getString("reason");
				
				// add raws into the html table
				output += "<tr><td>" + lineNo + "</td>";
				output += "<td>" + areaNo + "</td>";
				output += "<td>" + areaName + "</td>";
				output += "<td>" + startTime + "</td>";
				output += "<td>" + endTime + "</td>";
				output += "<td>" + date + "</td>";
				output += "<td>" + reason + "</td>";

				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
						+ "<td><form method='post' action='shedules.jsp'>"
						+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
						+ "<input name='sheduleId' type='hidden' value='" + sheduleId + "'>" + "</form></td></tr>";
			}

			con.close();
			// complete html table
			output += "</table>";

		} catch (Exception e) {
			output = "Error while reading the power cut shedule details.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	public String searchSchedules(String lineno) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database";
			}
			output = "<table border='1'><tr><th>Line No</th>" + "<th>Area No</th>" + "<th>Area Name</th>"
					+ "<th>Starting Time</th>" + "<th>Ending Time</th>" + "<th>Date</th>" + "<th>Reason</th></tr>";

			// create a prepared statement
			String query = "select * from shedules where lineNo='" + lineno + "'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				String areano = rs.getString("areano");
				String areaname = rs.getString("areaname");
				String starttime = rs.getString("starttime");
				String endtime = rs.getString("endtime");
				String date = rs.getString("date");
				String reason = rs.getString("reason");
				output += "<td>" + lineno + "</td>";
				output += "<td>" + areano + "</td>";
				output += "<td>" + areaname + "</td>";
				output += "<td>" + starttime + "</td>";
				output += "<td>" + endtime + "</td>";
				output += "<td>" + date + "</td>";
				output += "<td>" + reason + "</td></tr>";
			}
			con.close();
			output += "</table></html>";
		} catch (Exception e) {
			output = "Error while searching";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateSchedule(String ID, String lineno, String areano, String areaname, String starttime,
			String endtime, String date, String reason) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE shedules SET lineNo=?,areaNo=?, areaName=?, startTime=?, endTime=?, date=?, reason=? WHERE sheduleId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, lineno);
			preparedStmt.setString(2, areano);
			preparedStmt.setString(3, areaname);
			preparedStmt.setString(4, starttime);
			preparedStmt.setString(5, endtime);
			preparedStmt.setString(6, date);
			preparedStmt.setString(7, reason);
			preparedStmt.setInt(8, Integer.parseInt(ID));

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
			
			String newSchedules = readPowerCutDetails();
			output = "{\"status\":\"success\", \"data\": \"" +
			newSchedules + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while updating the Schedule.\"}";
					System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteShedule(String sheduleId) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from shedules where sheduleId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(sheduleId));

			// execute the statement
			preparedStmt.execute();
			con.close();
			String newSchedules =readPowerCutDetails();
			output = "{\"status\":\"success\", \"data\": \"" +
						newSchedules + "\"}";
			
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the schedule.\"}";
					System.err.println(e.getMessage());
		}
		return output;
	}
	
	
	
}
