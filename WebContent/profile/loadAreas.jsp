<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.sql.ResultSet,com.misc.lib.CustomHelper" %> 

		
<%  
		String sitePathInitMin =request.getContextPath()+"/";
		CustomHelper ch=new CustomHelper();
		//String areas = (String) request.getAttribute("areas");
		String[] strMonths=ch.loadShortMonths();
		Map usrDet=new HashMap();
		Map detMap = (HashMap) request.getAttribute("map");
		usrDet = (Map)request.getAttribute("usrDet");
		
		
%>

	
<%
%>