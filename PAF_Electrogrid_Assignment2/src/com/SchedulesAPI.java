package com;
import java.util.HashMap; 
import java.util.Map; 
import java.util.Scanner;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Schedule;

/**
 * Servlet implementation class SchedulesAPI
 */

@WebServlet("/SchedulesAPI")
public class SchedulesAPI extends HttpServlet {
	Schedule scheduleObj = new Schedule();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SchedulesAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		String output = scheduleObj.InsertPowerCutDetails(request.getParameter("lineNo"), 
				 request.getParameter("areaNo"), 
				request.getParameter("areaName"), 
				request.getParameter("startTime"),
				request.getParameter("endTime"),
				request.getParameter("date"),
				request.getParameter("reason")); 
				response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) 
			 throws ServletException, IOException 
			{ 
			 Map paras = getParasMap(request); 
			 String output = scheduleObj.updateSchedule(paras.get("hidSecIDSave").toString(), 
			 paras.get("lineNo").toString(), 
			paras.get("areaNo").toString(), 
			paras.get("areaName").toString(), 
			paras.get("startTime").toString(), 
			paras.get("endTime").toString(), 
			paras.get("date").toString(), 
			paras.get("reason").toString());
			response.getWriter().write(output); 
			} 
	
	
	private static Map getParasMap(HttpServletRequest request) 
	{ 
	 Map<String, String> map = new HashMap<String, String>(); 
	try
	 { 
	 Scanner scanner = new Scanner(request.getInputStream(), "UTF-8"); 
	 String queryString = scanner.hasNext() ? 
	 scanner.useDelimiter("\\A").next() : ""; 
	 scanner.close(); 
	 String[] params = queryString.split("&"); 
	 for (String param : params) 
	 { 
	 String[] p = param.split("="); 
	 map.put(p[0], p[1]); 
	 } 
	 } 
	catch (Exception e) 
	 { 
	 } 
	return map; 
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
			 throws ServletException, IOException 
			{ 
			 Map paras = getParasMap(request); 
			 String output = scheduleObj.deleteShedule(paras.get("scheduleId").toString()); 
			response.getWriter().write(output); 
			}


}
