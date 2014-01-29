<%@ include file="../sub/header.jsp"%>
<%@ page import="com.misc.lib.RolePermission" %> 


<% 
	HttpSession sMain=request.getSession();
	RolePermission rpMain=new RolePermission();
	rpMain.gAppProperties(request.getRealPath("/"));
	String disabled="";
	String display_1="";
	String display_2="";
	String display_11="";
	String display_21="";
	if(loggedState){
		
		 if(rpMain.verifyModule(Integer.parseInt(sMain.getAttribute("roleid").toString()),7,6)){
			 disabled="";
		     display_11="";
			 display_21="display:none";
			 disabled="";
			
		 }
		 else{
			 disabled="ui-disabled";
			 display_11="display:none";
			 display_21="";
		 }
	}else{
		
		display_1="";
		display_2="display:none";
	}
%> 

<div id="projection" >

	<div data-role="panel" id="mypanel" data-display="overlay" data-position="right">
	    <ul data-role="listview" data-theme="a" data-divider-theme="a" data-count-theme="a">
	    <li data-role="list-divider" style="font-weight:normal;font-size:24px;">Target Menu</li>
	    <li><a href="<%=sitePathInit%>Projection" class="<%=disabled%>" data-icon="false" style="font-weight:normal;">Company Projection</a></li>
	    <li><a href="<%=sitePathInit%>Team" class="<%=disabled%>" style="font-weight:normal;">Team Projection</a></li>
	    <li><a href="<%=sitePathInit%>Area" class="<%=disabled%>" style="font-weight:normal;">Area Projection</a></li>
	    <li><a href="<%=sitePathInit%>" class="<%=disabled%>" style="font-weight:normal;">Home Page</a></li>
	</ul>
	</div>
	<div data-role="header" align="center" style="border:0px;margin-bottom:2%;">
			<img src="<%=sitePathInit%>images/ppe-black.svg"  style="width:325px; "/>		
	</div>
	<div data-role="header" style="border:0px">
	  		<div id="title2">Dashboard</div>
	  		<a href="" class="jq_btnLogout ui-btn ui-btn-a ui-corner-all  ui-btn-block " style="<%=display_1%>; font-size:14px"><span class="">Logout</span></a>
	  		<a href="#mypanel" class="ui-btn ui-btn-a ui-corner-all  ui-btn-block " style="<%=display_1%>; font-size:14px"><span class="" >Menu</span></a>
	</div>
	<div data-role="content" style="<%=display_11%>">
		<div class="ui-grid-b jqSection" style="margin-top:-2%">
		    <div class="ui-block-a"><a href="javascript:void(0);" class="ibtntabs active ui-btn ui-btn-b ui-corner-all ui-shadow ui-mini" alt='loadDefault'>Snapshot</a></div>
		    <div class="ui-block-b"><a href="javascript:void(0);" class="ibtntabs ui-btn ui-btn-b ui-corner-all ui-shadow ui-mini" alt='loadMonth'>Monthly</a></div>
		    <div class="ui-block-c"><a href="javascript:void(0);" class="ibtntabs ui-btn ui-btn-b ui-corner-all ui-shadow ui-mini">Query</a></div>
		</div>
		<div class="ui-grid-solo" id="content">
		
		</div>
	</div>
	<div data-role="content" style="<%=display_21%>">
		<div class="ui-grid-solo content" style="text-align:center">
				  <div class=''>Access Denied</div>
				  <br>
				  <div class=''><a  style='color:#3E7836;' href="<%=sitePathInit%>">Back to Main</a></div>
		</div>
	</div>
	<form id="formTarget">
		<input type='hidden' name='action' class='jqAction' value='loadDefault'> 
	</form>
<script type='text/javascript' src='<%=sitePathInit%>include/login.jsp'></script>
<script type='text/javascript' src='<%=sitePathInit%>include/custom.jsp'></script>
<script type='text/javascript'>

$(document).ready(function($){

	<% if(rpMain.verifyModule(Integer.parseInt(sMain.getAttribute("roleid").toString()),7,6)){	%> 	
			loadTarget();
	<% } %>		

	
	
	$(".jqSection .ibtntabs").click(function () {
		
		if(checkLogged()){
				
			$(".jqSection").find(".ibtntabs").removeClass("active");	
			$(this).addClass("active");
			
			$(".jqAction").val($(this).attr("alt"));
			
			if(checkPermission(7,6)){
				mainModule=true;
			}else{
				mainModule=false;
			}
			
	  		if(mainModule){		
				loadTarget();
	  		}
	  		
	  	}else{
				window.location='<%=sitePathInit%>';
		}	
	});
	
	
	
/*
	
	$(".jq_AreaTabs .ibtntabs").click(function () {
		
		if(checkLogged()){
			
			$(".jq_AreaTabs").find(".ibtntabs").removeClass("selected");	
			$(this).addClass("selected");
			
				$.post("<%=sitePathInit%>APurchase",{action:$(this).attr("alt")},
						   function(data){
									//Submit Form	
									$(".jq_AreaManagementView").html(data);
				});
		}else{
				window.location='<%=sitePathInit%>';
		}	
	});
	
	*/
	
});



function loadTarget(){
	$.post("<%=sitePathInit%>AProjection",$("#formTarget").serialize(),
			   function(data){
		$("#content").empty();
		$("#content").html(data);	
		$('#content').click(function() {
				var d = new Date();
				d.setDate(d.getDate() - 1);
				var dmonth = d.getMonth();
				if (dmonth < 10)
					dmonth = "0" + dmonth;
					
				var href = "<%=sitePathInit%>SalesReports";
					window.location = href;

				$('#content').hover(function() {
					$(this).css('cursor', 'hand');
				},function() {
					$(this).css('cursor','auto');
				});
		});
	});
}

</script>
