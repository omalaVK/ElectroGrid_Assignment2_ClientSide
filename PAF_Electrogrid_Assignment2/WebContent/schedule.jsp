<%@page import="model.Schedule"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%
if (request.getParameter("") != null) 
{ 
 Schedule readObj = new Schedule(); 
 String stsMsg = ""; 
//Insert--------------------------
if (request.getParameter("hidSecIDSave") == "") 
 { 
 stsMsg = readObj.InsertPowerCutDetails(request.getParameter("lineNo"),  
 request.getParameter("areaNo"), 
 request.getParameter("areaName"), 
 request.getParameter("startTime"), 
 request.getParameter("endTime"), 
 request.getParameter("date"), 
 request.getParameter("reason")); 
 } 
else      //Update----------------------
 { 
 stsMsg = readObj.updateSchedule(request.getParameter("hidSecIDSave"), 
 request.getParameter("lineNo"), 
 request.getParameter("areaNo"), 
 request.getParameter("areaName"), 
 request.getParameter("startTime"), 
 request.getParameter("endTime"), 
 request.getParameter("date"), 
 request.getParameter("reason")); 
 } 
 session.setAttribute("statusMsg", stsMsg); 
} 

//Delete--------------------------------------------------
if (request.getParameter("hidSecIDDelete") != null) 
{ 
	Schedule readObj = new Schedule();
	String stsMsg = 
	readObj.deleteShedule(request.getParameter("hidSecIDDelete")); 
	session.setAttribute("statusMsg", stsMsg); 
}
%>

    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">


<link rel="stylesheet" href="Views/bootstrap.min.css"> 
<link rel="stylesheet" type="text/css" href="css\footer.css"> 
<script src="Components/jquery-3.6.0.js"></script>
<script src="Components/main.js"></script>

               
<title>Schedule Management</title>
</head>
<body>

<div class="container"><div class="row"><div class="col-6">
<h1>Power Cut Schedule Management</h1>
	
	<form  id="formItem" name="formItem" method="post" action="units.jsp">
		Line No: <input id="line" name="line" type="text" class="form-control form-control-sm"> <br>
		Area No: <input id="areano" name="areano" type="text" class="form-control form-control-sm"> <br>
		Area Name: <input id="areaname" name="areaname" type="text" class="form-control form-control-sm"> <br>
		Start Time: <input id="starttime" name="starttime" type="text" class="form-control form-control-sm"> <br>
		End Time: <input id="endtime" name="endtime" type="text" class="form-control form-control-sm"> <br>
		Date: <input id="date" name="date" type="text" class="form-control form-control-sm"> <br>
		Reason: <input id="reason" name="reason" type="text" class="form-control form-control-sm"> <br>
		
		<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
		<input type="hidden" id="hidSecIDSave" name="hidSecIDSave" value="">
		
	</form>
	
	<div id="alertSuccess" class="alert alert-success"></div>
	<div id="alertError" class="alert alert-danger"></div>
	
<div id="divItemsGrid">
<%
Schedule readObj = new Schedule();
 out.print(readObj.readPowerCutDetails());
%>
</div>


</div></div></div>
</body>
</html>