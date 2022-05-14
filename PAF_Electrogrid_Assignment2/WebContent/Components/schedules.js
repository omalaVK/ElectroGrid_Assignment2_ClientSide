$(document).ready(function() 
{ 
if ($("#alertSuccess").text().trim() == "") 
 { 
 $("#alertSuccess").hide(); 
 } 
 $("#alertError").hide(); 
}); 

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) 
{ 
// Clear alerts---------------------
 $("#alertSuccess").text(""); 
 $("#alertSuccess").hide(); 
 $("#alertError").text(""); 
 $("#alertError").hide(); 
// Form validation-------------------
var status = validateScheduleForm(); 
if (status != true) 
 { 
 $("#alertError").text(status); 
 $("#alertError").show(); 
 return; 
 } 
// If valid------------------------
var type = ($("#hidSecIDSave").val() == "") ? "POST" : "PUT"; 
 $.ajax( 
 { 
 url : "SchedulesAPI", 
 type : type, 
 data : $("#formSchedule").serialize(), 
 dataType : "text", 
 complete : function(response, status) 
 { 
 onScheduleSaveComplete(response.responseText, status); 
 } 
 }); 
});

// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) 
{ 
$("#hidSecIdSave").val($(this).data("sheduleId")); 
 $("#lineNO").val($(this).closest("tr").find('td:eq(0)').text()); 
 $("areaNo#").val($(this).closest("tr").find('td:eq(1)').text()); 
 $("#areaName").val($(this).closest("tr").find('td:eq(2)').text()); 
 $("#startTime").val($(this).closest("tr").find('td:eq(3)').text()); 
$("#endTime").val($(this).closest("tr").find('td:eq(4)').text()); 
$("#date").val($(this).closest("tr").find('td:eq(5)').text()); 
$("#reason").val($(this).closest("tr").find('td:eq(6)').text()); 
});

$(document).on("click", ".btnRemove", function(event) 
{ 
 $.ajax( 
 { 
 url : "ScheduleAPI", 
 type : "DELETE", 
 data : "sheduleId=" + $(this).data("sheduleId"),
 dataType : "text", 
 complete : function(response, status) 
 { 
 onScheduleDeleteComplete(response.responseText, status); 
 } 
 }); 
});
// CLIENT-MODEL================================================================
function validateScheduleForm() 
{ 
// line NO
if ($("#lineNo").val().trim() == "") 
 { 
 return "Insert Line Number."; 
 } 
// area No
if ($("#areaNo").val().trim() == "") 
 { 
 return "Insert Area Number."; 
 } 
// area Name-------------------------------
if ($("#areaName").val().trim() == "") 
 { 
 return "Insert Area Name."; 
 } 
// start Time-------------------------------
if ($("#startTime").val().trim() == "") 
 { 
 return "Insert Start Time."; 
 } 
//end Time-------------------------------
if ($("#endTime").val().trim() == "") 
 { 
 return "Insert end Time."; 
 } 
// date-------------------------------
if ($("#date").val().trim() == "") 
 { 
 return "Insert Date."; 
 } 
// reason-------------------------------
if ($("#reason").val().trim() == "") 
 { 
 return "Insert Reason."; 
 } 

return true; 
}

function onScheduleSaveComplete(response, status) 
{ 
if (status == "success") 
 { 
 var resultSet = JSON.parse(response); 
 if (resultSet.status.trim() == "success") 
 { 
 $("#alertSuccess").text("Successfully saved."); 
 $("#alertSuccess").show(); 
 $("#divItemsGrid").html(resultSet.data); 
 } else if (resultSet.status.trim() == "error") 
 { 
 $("#alertError").text(resultSet.data); 
 $("#alertError").show(); 
 } 
 } else if (status == "error") 
 { 
 $("#alertError").text("Error while saving."); 
 $("#alertError").show(); 
 } else
 { 
 $("#alertError").text("Unknown error while saving.."); 
 $("#alertError").show(); 
 } 
 $("#hidSecIDSave").val(""); 
 $("#formSchedule")[0].reset(); 
}


function onScheduleDeleteComplete(response, status) 
{ 
if (status == "success") 
 { 
 var resultSet = JSON.parse(response); 
 if (resultSet.status.trim() == "success") 
 { 
 $("#alertSuccess").text("Successfully deleted."); 
 $("#alertSuccess").show(); 
 $("#divItemsGrid").html(resultSet.data); 
 } else if (resultSet.status.trim() == "error") 
 { 
 $("#alertError").text(resultSet.data); 
 $("#alertError").show(); 
 } 
 } else if (status == "error") 
 { 
 $("#alertError").text("Error while deleting."); 
 $("#alertError").show(); 
 } else
 { 
 $("#alertError").text("Unknown error while deleting.."); 
 $("#alertError").show(); 
 } 
}