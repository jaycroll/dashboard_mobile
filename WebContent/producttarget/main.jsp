<%@ include file="../sub/header.jsp"%>

<%@ page import="com.misc.lib.RolePermission" %> 


<% 
	HttpSession sMain=request.getSession();
	RolePermission rpMain=new RolePermission();
	rpMain.gAppProperties(request.getRealPath("/"));
	
	Boolean searchPriv=rpMain.verifyModule(Integer.parseInt(sMain.getAttribute("roleid").toString()),4,7);
%> 


<style type="text/css">
.text_12_tungsten {font-family:"Lucida Sans Unicode", "Lucida Grande", sans-serif;
	font-size:12px;
	color:#333;
	text-align: center;
}
</style>


	<link rel="stylesheet" href="<%=sitePathInit%>css/jquery.ui.all.css">
	<link rel="stylesheet" href="<%=sitePathInit%>css/datepicker.css">

	<script type='text/javascript' src='<%=sitePathInit%>js/jquery.ui.core.js'></script>
	<script type='text/javascript' src='<%=sitePathInit%>js/jquery.ui.datepicker.js'></script>

<script>
		$(function() {
			$( ".datepicker" ).datepicker();
		});
</script>

<div id="header" align="center"><img src="<%=sitePathInit%>images/ppe-blk.png"/></div>
<div id="title">

<a href="<%=sitePathInit%>" class="lucida_21_black"><img src="<%=sitePathInit%>images/lock_small.png" align="absmiddle"/>Dashboard</a>

</div>

<% if(loggedState){%>
<div id="menu">
	<div id="menu-right" class="lucida_12_tungsten_b"> Hello, <%=sess.getAttribute("username")%> (<%=sess.getAttribute("rolename")%>). <a href="<%=sitePathInit%>User/logout"><span class="lucida_12_red_b">Logout</span></a></div>
</div>
<% }%>
<div id="submenu">

<div>
<ul class="tabs jq_UserTabs access">
            <li id='access1' ><a href="<%=sitePathInit%>User" alt='loadUser' class=' ibtntabs' title='User Management'></a></li>
            <li id='access2' ><a href="<%=sitePathInit%>Roles" alt='loadRole' class='ibtntabs' title='Role Management'></a></li>
            <li id='access3' ><a href="<%=sitePathInit%>Privilege" alt='loadPrivilege' class='ibtntabs' title='Privileges Management'></a></li>
     	 	<li id='access4' ><a href="<%=sitePathInit%>Target" alt='loadPrivilege' class='ibtntabs' title='Target Management'></a></li>
     	 	<li id='access7' ><a href="<%=sitePathInit%>ProductTarget" alt='loadPrivilege' class='ibtntabs active' title='Product Target Management'></a></li>
     	 	<li id='access5' ><a href="<%=sitePathInit%>Channel" alt='loadPrivilege' class='ibtntabs' title='Channel Management'></a></li>
     	 	<li id='access6' ><a href="<%=sitePathInit%>Sales"  class='ibtntabs' title='Sales Management'></a></li>
     	 </ul>

</div>

</div>
<form id='formTargetList'>


<div id="primary">

<% if(rpMain.verifyModule(Integer.parseInt(sMain.getAttribute("roleid").toString()),4,0)){	%> 

  <table width="900" border="0" align="center" cellpadding="5" cellspacing="0" bordercolor="#cccccc">
    <tr class="text12_tungsten_bold">
      <td width="75" style="text-align: center"></td>
      <td style="text-align: center">Target Date</td>
       <td style="text-align: center">Target Group</td>
      <td style="text-align: center">Target Category</td>
      <td style="text-align: center">Target Amount</td>
      <td style="text-align: center">Channel Name</td>
      <td width="75" style="text-align: center">&nbsp;</td>
    </tr>
    <tr>
      <td class="text10_red" style="text-align: center">
      <% if(rpMain.verifyModule(Integer.parseInt(sMain.getAttribute("roleid").toString()),4,1)){%>  
      	<img class='jq_AddTarget' src="images/add.png" width="28" height="28" />
      <% } %>	
      </td>
      <td style="text-align: center">
      <% if(searchPriv){ %>
      <input name="target_date" class='datepicker jqTargetInput' type="text" id="textfield" size="20" />
        <img src="images/calendar.png" width="28" height="28" hspace="5" align="absmiddle" /></td>
      <% } %> 
       <td style="text-align: center">
       
      <% if(searchPriv){ %> 
       <select name="target_group_id" class="text_12_tungsten jqTargetInput" id="select">
           <option value=''></option>
           <%  
           ResultSet listTargetGroup = (ResultSet) request.getAttribute("listTargetGroup");
			
			if (listTargetGroup.next()) {  
			
		  	do{
			%>
	        <option value='<%=listTargetGroup.getString("target_group_id")%>'><%=listTargetGroup.getString("target_group_name")%></option>
	        <%		
			 	} while (listTargetGroup.next());
		    	}	
           
			%>
      </select>
      <% } %>
      </td>
      <td style="text-align: center">
      
       <% if(searchPriv){ %> 
      <select name="category" class="text_12_tungsten jqTargetInput" id="select">
        	<option value=''></option>
        	<option value='Domestic'>Domestic</option>
        	<option value='International'>International</option>
      </select>
      <% } %>
      </td>
      <td style="text-align: center">
       <% if(searchPriv){ %> 
      <input name="target_amount" class='jqTargetInput' type="text" id="textfield2" size="20"  style='width:100px;' />
      <% } %>
      </td>
      <td style="text-align: center">
       <% if(searchPriv){ %> 
         <select name="channel_id" class="text_12_tungsten jqTargetInput" id="select">
           <option value=''></option>
           <%  
	       ResultSet listChannel = (ResultSet) request.getAttribute("listChannel");
			
			if (listChannel.next()) {  
			
		  	do{
			%>
	        <option value='<%=listChannel.getString("channel_id")%>'><%=listChannel.getString("channel_name")%></option>
	        <%		
			 	} while (listChannel.next());
		    	}	
			%> 
      </select>
       <% } %>
      </td>
      <td class="text10_red" style="text-align: center"></td>
    </tr>
    
    <tbody class='tblTargetList'>
    
     <% if(rpMain.verifyModule(Integer.parseInt(sMain.getAttribute("roleid").toString()),4,6)){	%>  
     <%@ include file="../producttarget/result_TargetList.jsp"%>
     
     <% }else{ %>    		
      <tr><td colspan='7'>
		<div class='divCenter txtCenter'>
							   <div class='access_denied'>Access Denied</div>
							   <div class='clr'>&nbsp;</div>
							  <div class='clr'><a  style='color:#3E7836;' href="<%=sitePathInit%>">Back to Main</a></div>
	                           <div class='clr'>&nbsp;</div>
	                           </div>
	</td></tr>
 	<% } %>	
    </tbody>
  </table>
     <% }else{ %>
	
		<div class='divCenter txtCenter'>
			  <div class='access_denied'>Access Denied</div>
			  <div class='clr'>&nbsp;</div>
			  <div class='clr'><a  style='color:#3E7836;' href="<%=sitePathInit%>">Back to Main</a></div>
              <div class='clr'>&nbsp;</div>
	    </div>
	                           
<% } %> 
  <!---------primary-right------------->
</div>

<input type='hidden' name='action'  value='loadTarget'> 
</form>

<script type='text/javascript'>

$(document).ready(function($){
	
	
	
$(".jq_AddTarget").click(function () {
		
		if(checkLogged()){
			
			if(checkPermission(4,1)){
				mainModule=true;
			}else{
				mainModule=false;
			}
			
			if(mainModule){
				$.post("<%=sitePathInit%>AProductTarget",{action:'initAddTarget'},
						   function(data){
									$('.basic-container').html(data);
									initModal();
				});
			}	
		}else{
			window.location='<%=sitePathInit%>';	
		}
});
	




$(".jq_EditTarget").click(function () {
	
	if(checkLogged()){
		
		
		var mainModule=false;
		if(checkPermission(4,2)){
			mainModule=true;
		}else{
			mainModule=false;
		}
		
		if(mainModule){
		
		$.post("<%=sitePathInit%>AProductTarget",{action:'initEditTarget',target_id:$(this).attr("alt"),channel_id:$('#channel_name').attr("alt")},
				   function(data){
							$('.basic-container').html(data);
							initModal();
		});
		
		}
	}else{
		window.location='<%=sitePathInit%>';	
	}
});	







$(".jq_DeparmentSelect").change(function(event) {
	
	if(checkLogged()){
		
		if($(this).val()!='1'){
		
		$.post("<%=sitePathInit%>AProductTarget",{action:'loadDeparmentUser',department_id:$(this).val()},
				   function(data){
					$(".jq_UserSelect").html(data.html);			
		},"json");
		
		}else{
			$(".jq_UserSelect").html("<option value='0'>All</option>");
		}
		
	}else{
		window.location='<%=sitePathInit%>';
	}
	
});


$(".jqTargetInput").change(function(event) {
	
	var mainModule=false;
	if(checkPermission(4,7)){
		mainModule=true;
	}else{
		mainModule=false;
	}
	
	if(mainModule){
	loadTarget();
	}
});


$(".jqTargetInput").keyup(function(event) {
	
	var mainModule=false;
	if(checkPermission(4,7)){
		mainModule=true;
	}else{
		mainModule=false;
	}
	
	if(mainModule){
	loadTarget();	
	}
});






});


function loadTarget(){
	$.post("<%=sitePathInit%>AProductTarget",$("#formTargetList").serialize(),
			   function(data){
				$(".tblTargetList").html(data);	
	});
}
</script>

