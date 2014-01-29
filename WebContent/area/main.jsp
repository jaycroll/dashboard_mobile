<%@ include file="../sub/header.jsp"%>

<%@ page import="com.misc.lib.RolePermission" %> 
<%@ page import="com.model.lib.*" %>
<%@ page import="java.sql.*" %>
<% 
	HttpSession sMain=request.getSession();
	RolePermission rpMain=new RolePermission();
	rpMain.gAppProperties(request.getRealPath("/"));
	String[][] territories = (String[][]) request.getAttribute("territories");
	String[][] areas = (String[][]) request.getAttribute("areas");
	String selected = request.getParameter("selected");
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
<div id="area">
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
		<div class="ui-grid-solo">	
				Select Territory:
			    <form>
				<fieldset data-role="controlgroup" data-type="horizontal">
			    	<label for="select2">Territory:</label>
			    	<select name="territory" name="select2" id="select2" data-inline="true" data-mini="true">
			    		<option value="" >--Territory--</option>
					    <%
					    	for(int i = 0; i<territories.length;i++){
					    	
					    			%>
					    				<option value="<%=territories[i][0] %>" ><%=territories[i][1] %></option>
					    			<%
					    		
					    	}
					    %>
				    </select>
				    <label for="select2">Area:</label>
				    <select class="state" name="user.state" id="area" data-role="select" data-inline="true" data-mini="true">
						<option value='area' selected="selected">--Area--</option>
			        </select>
				</fieldset>
				</form>
		</div>
		<div class="ui-grid-solo" id="content4">
		</div> 
	</div>
	<div data-role="content" style="<%=display_21%>">
		<div class="ui-grid-solo content" style="text-align:center">
				  <div class=''>Access Denied</div>
				  <br>
				  <div class=''><a  style='color:#3E7836;' href="<%=sitePathInit%>">Back to Main</a></div>
		</div>
	</div>
<script type='text/javascript' src='<%=sitePathInit%>include/login.jsp'></script>
<script type='text/javascript' src='<%=sitePathInit%>include/custom.jsp'></script>
</div>

<option id="test">asd</option>
<script type='text/javascript'>

$(document).ready(function($){
	$("#content4").html("");	
	$("#team").show();


});

var AreaArray = new Array(<%=areas.length%>);
<%
for(int i = 0;i<areas.length;i++){
	%>
		AreaArray[<%=i%>] = new Array(2);
		AreaArray[<%=i%>][0] = <%=areas[i][0]%>;
		AreaArray[<%=i%>][1] = "<%=areas[i][1]%>";
		AreaArray[<%=i%>][2] = <%=areas[i][2]%>;
		
	<%
}
%>

$("#select2").change(function(){
	var str = "";
    $( "#select2 option:selected" ).each(function() {
      str += $( this ).val();
      
    	});

    $('select.state').empty();
    $('select.state option:selected').remove();
	$('select.state').append("<option value='area'selected='selected'>--Area--</option>");
    
	for(var i = 0;i<AreaArray.length;i++){
		
		if(AreaArray[i][2] == str){
			$('select.state').append("<option value="+AreaArray[i][0]+">"+AreaArray[i][1]+"</option>");
		}
	}

	//$("#content4").empty();
    //loadAreas(str);

});
$("select.state").change(function(){
	var areaid = "";
    $( "select.state option:selected" ).each(function() {
      areaid += $( this ).val();
    });
	$("#content4").empty();
	if(areaid!='area'){loadAreaProjection(areaid);}
	
	
});
function loadAreaProjection(areaid){
	var areaid = areaid;
	$.post("<%=sitePathInit%>AArea",{action:'loadProjection',areaid:areaid},
			   function(data){
	
		$("#content4").append(data);
	});
}



</script>

