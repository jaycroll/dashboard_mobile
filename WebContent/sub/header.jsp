<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.misc.lib.CustomHelper,java.sql.ResultSet" %> 

<% 

	CustomHelper chMain=new CustomHelper();

	String sitePathInit =request.getContextPath()+"/";
	HttpSession sess=request.getSession();
	
	Boolean loggedState=false;
	if((sess.getAttribute("username")==null) || (sess.getAttribute("username")=="") || (sess.getAttribute("username")=="null")){
		loggedState=false;
	}else{
		loggedState=true;
	}
	
	Boolean graphContent = (Boolean) request.getAttribute("graphContent");


%>    

<!DOCTYPE html>

<html>
<head>
<meta http-equiv="content-type"  charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" type="image/png" href="images/favicon.ico" />
<link rel="stylesheet" href="<%=sitePathInit%>css/jquery.mobile.structure-1.4.0.css" /> 
<link rel="stylesheet" href="<%=sitePathInit%>css/jquery.mobile-1.4.0.css"/>

<link rel="stylesheet" type="text/css"  href="<%=sitePathInit%>css/style2.css">
<link rel="stylesheet" type="text/css" href="<%=sitePathInit%>css/font.css">
<link rel="stylesheet" type="text/css" href="<%=sitePathInit%>css/nv.d3.css">
<!-- Contact Form CSS files -->
<!-- Load jQuery, SimpleModal and Basic JS files -->
<script type='text/javascript' src='<%=sitePathInit%>js/jquery.js'></script>
<script type='text/javascript' src='<%=sitePathInit%>js/jquery.mobile-1.4.0.js'></script>
<script type='text/javascript' src='<%=sitePathInit%>js/d3.js' charset="utf-8"></script>
<script type='text/javascript' src='<%=sitePathInit%>js/nv.d3.js'></script>
<script type='text/javascript' src='<%=sitePathInit%>js/stream_layers.js'></script>
<script type='text/javascript' src='<%=sitePathInit%>js/dimple.v1.1.4.min.js'></script>
<title>Dashboard</title>
</head>

<body>


<div id='basic-modal-content'>
	<div class='clr'> </div>
	<div class='basic-container'>
				
	</div>
	<div class='clr'>&nbsp;</div>
</div>

<div id="page">
<div id="wrapper">
