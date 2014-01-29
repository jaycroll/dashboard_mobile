<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<% 
	String sitePathInit =request.getContextPath()+"/";
%> 

$(document).ready(function($){

	
	 $(".jq_InitLogin").click(function () {
			loginModule();
	  });
	 
	 
	 $('#formLogin input').keypress( function (e) {
			if(e.which == 13){
				loginModule();
			}	
	 });
		  
	 $(".jq_btnLogin").click(function () {
				loginModule();
	 });
	 $(".jq_btnLogout").hover(function(){
	 	$(".jq_btnLogout").removeClass("ui-bt-icon-notext");
	 });
	 $(".jq_btnLogout").click(function () {	
	 	$(".jq_btnLogout").removeClass("ui-bt-icon-notext");
			logoutModule();
	});
	   
});

function loginModule(){
		
		$.post("<%=sitePathInit%>AUser",$("#formLogin").serialize(),
				   function(data){
				   	
						if(data.logged){
								window.location='<%=sitePathInit%>';
						}else{
							$(".sLoginStatus").html(data.status);
						}
		},"json");
		
         
 }

function logoutModule(){
	$.post("<%=sitePathInit%>User",{action:'logout'},
			   function(data){		   						
	},"json");
	window.location='<%=sitePathInit%>';
     
}
   
   