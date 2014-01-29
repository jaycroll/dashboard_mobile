<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<% 
	String sitePathInitJs =request.getContextPath()+"/";
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
	   
});

function loginModule(){
		
		$.post("<%=sitePathInitJs%>AUser",$("#formLogin").serialize(),
				   function(data){
				   	
						if(data.logged){
								window.location='<%=sitePathInitJs%>';
						}else{
							$(".sLoginStatus").html(data.status);
						}
		},"json");
		
          
  }
  
 
