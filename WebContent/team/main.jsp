<%@ include file="../sub/header.jsp"%>



<%@ page import="com.misc.lib.RolePermission" %> 


<% 
	HttpSession sMain=request.getSession();
	RolePermission rpMain=new RolePermission();
	rpMain.gAppProperties(request.getRealPath("/"));

	int iCtr = Integer.parseInt(request.getAttribute("iCtr").toString());
	String disabled="";
	String display_1="";
	String display_2="";
	String display_11="";
	String display_21="";
	if(loggedState){
		
		 if(rpMain.verifyModule(Integer.parseInt(sMain.getAttribute("roleid").toString()),8,6)){
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
<div id="team">

	<div data-role="panel" id="mypanel" data-display="overlay" data-position="right">
	    <ul data-role="listview" data-theme="a" data-divider-theme="a" data-count-theme="a">
	    <li data-role="list-divider" style="font-weight:normal;font-size:24px;">Target Menu</li>
	    <li><a href="<%=sitePathInit%>Projection" class="<%=disabled%>" style="font-weight:normal;">Company Projection</a></li>
	    <li><a href="<%=sitePathInit%>Team" class="<%=disabled%>" style="font-weight:normal;">Team Projection</a></li>
	    <li><a href="<%=sitePathInit%>Area" class="<%=disabled%>" style="font-weight:normal;">Area Projection</a></li>
	    <li><a href="<%=sitePathInit%>" style="font-weight:normal;">Home Page</a></li>
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
		<div class="ui-grid-solo" id="content2">	

				<table  data-role="table" class="ui-responsive table-stroke">
					 <thead class=" table-header-blue">
				       <tr >
				         <th class=" table-header-blue" style="text-align:center">Agent</th>
				         <th class=" table-header-blue" style="text-align:center">MTD</th>
				         <th class=" table-header-blue" style="text-align:center">YTD</th>
				         <th class=" table-header-blue" style="text-align:center">Total</th>
				       </tr>
				     </thead>
					<tbody>
					<% for(int i=0; i<=iCtr;i++){ %>
					<tr>
						<td class='col1'><a href="<%=sitePathInit%>Profile/<%=request.getAttribute("userid_"+i) %>" class="jq_viewProfile"><%=request.getAttribute("fullname_"+i) %></a></td>
						
						<td class='col2'><span class='green'><%=request.getAttribute("monthly_"+i) %></span> -  <span class='blu'><%=request.getAttribute("monthlyrevenue_"+i) %></span> <%=request.getAttribute("monthlypercentage_"+i) %>%</td>
			
						<td class='col2'><span class='green'><%=request.getAttribute("yearly_"+i) %></span> - <span class='blu'><%=request.getAttribute("yearlyrevenue_"+i) %></span> <%=request.getAttribute("yearlypercentage_"+i) %>%</td>
						<td class='col2'><span class='green'><%=request.getAttribute("yearlytotal_"+i) %></span> - <span class='blu'><%=request.getAttribute("yearlyrevenue_"+i) %></span> <%=request.getAttribute("yearlytotalpercentage_"+i) %>%</td>
					</tr>
					<% } %>
					</tbody>
				</table>

	</div>
	<div data-role="content" style="<%=display_21%>">
		<div class="ui-grid-solo content" style="text-align:center">
				  <div class=''>Access Denied</div>
				  <br>
				  <div class=''><a  style='color:#3E7836;' href="<%=sitePathInit%>">Back to Main</a></div>
		</div>
	</div>
</div>
<script type='text/javascript' src='<%=sitePathInit%>include/login.jsp'></script>
<script type='text/javascript' src='<%=sitePathInit%>include/custom.jsp'></script>
<script>
$(document).ready(function($){
	

	
	
	
});
</script>