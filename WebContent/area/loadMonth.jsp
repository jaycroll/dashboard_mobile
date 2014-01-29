<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.sql.ResultSet,com.misc.lib.CustomHelper" %> 

		
<%  
		String sitePathInitMin =request.getContextPath()+"/";
		CustomHelper ch=new CustomHelper();

		String[] strMonths=ch.loadShortMonths();
		Map areaMap = (HashMap) request.getAttribute("areaMap");
		//Map usrDet=new HashMap();
		//Map detMap = (HashMap) request.getAttribute("map");
		//usrDet = (Map)request.getAttribute("usrDet");
		
		
%>
  <% //if(!usrDet.isEmpty()){ %>
	<a href="#team" id="OpenTeam">Area Team</a>
	<a href="#year" id="OpenYear">Yearly Projection</a>
	<a href="#month" id="OpenMonth">Monthly Projection</a>
	<div id="team" style="display:none">
		
	</div>
	<div id="year" style="display:none">year</div>
	<div id="month" style="display:none">month</div>
	
	<script>
		$(document).ready(function(){
			$("#team").show();
			$("#OpenTeam").click(function(){
				$("#team").show();
				$("#year").hide();
				$("#month").hide();
			});
			$("#OpenYear").click(function(){
				$("#year").show();
				$("#team").hide();
				$("#month").hide();
			});
			$("#OpenMonth").click(function(){
				$("#month").show();
				$("#year").hide();
				$("#team").hide();
			});
		});
	</script>
  <% //} %>	