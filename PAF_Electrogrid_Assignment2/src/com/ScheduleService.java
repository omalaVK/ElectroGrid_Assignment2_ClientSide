package com;

import model.Schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("/Schedule")
public class ScheduleService {

	Schedule scheduleObj = new Schedule();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPowerCutDetails() {
		return scheduleObj.readPowerCutDetails();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)

	public String InsertPowerCutDetails(@FormParam("lineNo") String lineNo, @FormParam("areaNo") String areaNo,
			@FormParam("areaName") String areaName, @FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime, @FormParam("date") String date, @FormParam("reason") String reason) {

		if (lineNo.isEmpty() || areaNo.isEmpty() || areaName.isEmpty() || startTime.isEmpty() || endTime.isEmpty()
				|| date.isEmpty() || reason.isEmpty()) {
			return "All the Fields Should be inserted";
		} else if (lineNo.length() != 8) {
			return "Line NO should consist of 8 characters";
		} else if (areaNo.length() != 4) {
			return "Area No should be consist of 4 characters ";
		}

		String output = scheduleObj.InsertPowerCutDetails(lineNo, areaNo, areaName, startTime, endTime, date, reason);
		return output;

	}

	@GET
	@Path("/searchSchedule")
	@Produces(MediaType.TEXT_HTML)
	public String searchSchedules(String scheduleData) {
		Document doc = Jsoup.parse(scheduleData, "", Parser.xmlParser());
		String lineno = doc.select("lineno").text();
		return scheduleObj.searchSchedules(lineno);
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)

	public String updateSchedule(String scheduleData) {
		// Convert the input string to a JSON object
		JsonObject scheduleObject = new JsonParser().parse(scheduleData).getAsJsonObject();

		// Read the values from the JSON object
		String sheduleId = scheduleObject.get("sheduleId").getAsString();
		String lineNo = scheduleObject.get("lineNo").getAsString();
		String areaNo = scheduleObject.get("areaNo").getAsString();
		String areaName = scheduleObject.get("areaName").getAsString();
		String startTime = scheduleObject.get("startTime").getAsString();
		String endTime = scheduleObject.get("endTime").getAsString();
		String date = scheduleObject.get("date").getAsString();
		String reason = scheduleObject.get("reason").getAsString();

		if (lineNo.isEmpty() || areaNo.isEmpty() || areaName.isEmpty() || startTime.isEmpty() || endTime.isEmpty()
				|| date.isEmpty() || reason.isEmpty()) {
			return "All the Fields Should be inserted";
		} else if (lineNo.length() != 8) {
			return "Line NO should consist of 8 characters";
		} else if (areaNo.length() != 4) {
			return "Area No should be consist of 4 characters ";
		}
		String output = scheduleObj.updateSchedule(sheduleId, lineNo, areaNo, areaName, startTime, endTime, date,
				reason);

		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteShedule(String sheduleData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(sheduleData, "", Parser.xmlParser());
		// Read the value from the element <sheduleId>
		String scheduleId = doc.select("sheduleId").text();

		String output = scheduleObj.deleteShedule(scheduleId);

		return output;
	}
}