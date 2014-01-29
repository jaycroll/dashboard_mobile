<%@ include file="../sub/header.jsp"%>

<%@ page import="com.misc.lib.RolePermission" %> 


<% 
	HttpSession sMain=request.getSession();
	RolePermission rpMain=new RolePermission();
	rpMain.gAppProperties(request.getRealPath("/"));
	
	Boolean searchPriv=rpMain.verifyModule(Integer.parseInt(sMain.getAttribute("roleid").toString()),6,7);
%> 


<style type="text/css">
.text_12_tungsten {font-family:"Lucida Sans Unicode", "Lucida Grande", sans-serif;
	font-size:12px;
	color:#333;
	text-align: center;
}
</style>

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
     	 	<li id='access7' ><a href="<%=sitePathInit%>ProductTarget" alt='loadPrivilege' class='ibtntabs' title='Product Target Management'></a></li>
     	 	<li id='access5' ><a href="<%=sitePathInit%>Channel" alt='loadPrivilege' class='ibtntabs active' title='Channel Management'></a></li>
     	 	<li id='access6' ><a href="<%=sitePathInit%>Sales"  class='ibtntabs' title='Sales Management'></a></li>
     	 </ul>

</div>

</div>

<form id='formChannelList'>


<div id="primary">
<% if(rpMain.verifyModule(Integer.parseInt(sMain.getAttribute("roleid").toString()),6,0)){	%> 

  <table width="900" cellspacing="0" cellpadding="5" bordercolor="#cccccc" border="0" align="center">
    <tr class="text12_tungsten_bold">
      <td width="75" style="text-align: center" class="lucida_12_tungsten_b">Disable</td>
      <td style="text-align: center" class="lucida_12_tungsten_b">Channel ID</td>
      <td style="text-align: center" class="lucida_12_tungsten_b">Channel Name</td>
      <td style="text-align: center" class="lucida_12_tungsten_b">Channel Group</td>
      <td style="text-align: center" class="lucida_12_tungsten_b">Automated</td>
      <td style="text-align: center" class="lucida_12_tungsten_b">APP Name</td>
      <td width="75" style="text-align: center">&nbsp;</td>
    </tr>
    <tr>
      <td style="text-align: center" class="text10_red">
         <% if(rpMain.verifyModule(Integer.parseInt(sMain.getAttribute("roleid").toString()),6,1)){%>  
      	<img width="28" height="28" class='jq_AddChannel hnd' src="images/add.png">
      	<% } %>
      	
     </td>
      <td style="text-align: center">
       <% if(searchPriv){ %>
      	<input type="text" size="10" class='jqChannelInput' id="textfield3" name="channel_id">
      	<% } %>
     </td>
      <td style="text-align: center">
       <% if(searchPriv){ %>
      	<input type="text" size="20" class='jqChannelInput' id="textfield2" name="channel_name">
      	<% } %>
      </td>
      <td style="text-align: center">
       <% if(searchPriv){ %>
      <select id="select" class="text_12_tungsten jqChannelInput" name="channel_group_id">
      	<option value=""></option>
       	<%  
	       ResultSet listChannelGroup = (ResultSet) request.getAttribute("listChannelGroup");
			
			if (listChannelGroup.next()) {  
			
		  	do{
			%>
	        <option value='<%=listChannelGroup.getString("channel_group_id")%>'><%=listChannelGroup.getString("channel_group_name")%></option>
	        <%		
			 	} while (listChannelGroup.next());
		    	}	
			%>      
      </select>
      <% } %>
      </td>
      <td style="text-align: center">
       <% if(searchPriv){ %>
      <select id="select2" class="text_12_tungsten jqChannelInput" name="channel_automated">
         <option value=''> </option>
        <option value='Yes'>Yes</option>
        <option value='No'>No</option>
      </select>
      <% } %>
      </td>
      <td style="text-align: center">
      
       <% if(searchPriv){ %>
      <select id="select3" class="text_12_tungsten jqChannelInput" name="app_id">
        <option value=""></option>
       	<%  
	       ResultSet listApp = (ResultSet) request.getAttribute("listApp");
			
			if (listApp.next()) {  
			
		  	do{
			%>
	        <option value='<%=listApp.getString("app_id")%>'><%=listApp.getString("app_name")%></option>
	        <%		
			 	} while (listApp.next());
		    	}	
		    %> 
       
       
      </select>
      <% } %>
      </td>
      <td style="text-align: center" class="text10_red"> </td>
    </tr>
    
   <tbody class='tblChannelList'> 
   
     <% if(rpMain.verifyModule(Integer.parseInt(sMain.getAttribute("roleid").toString()),6,6)){	%>  
   		<%@ include file="../channel/result_ChannelList.jsp"%>
   	  
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
   </tbody></table>
  
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

<input type='hidden' name='action'  value='loadChannel'> 
</form>

<script type='text/javascript'>

$(document).ready(function($){
	
	
	
$(".jq_AddChannel").click(function() {
		
		if(checkLogged()){
			if(checkPermission(6,1)){
				mainModule=true;
			}else{
				mainModule=false;
			}
			
			if(mainModule){
			
			$.post("<%=sitePathInit%>AChannel",{action:'initAddChannel'},
						   function(data){
									$('.basic-container').html(data);
									initModal();
				});
			}
			
		}else{
			window.location='<%=sitePathInit%>';	
		}
});
	




$(".jq_EditChannel").click(function () {
	
	if(checkLogged()){
		
		
		var mainModule=false;
		if(checkPermission(6,2)){
			mainModule=true;
		}else{
			mainModule=false;
		}
		
		if(mainModule){
		$.post("<%=sitePathInit%>AChannel",{action:'initEditChannel',channel_id:$(this).attr("alt")},
				   function(data){
							$('.basic-container').html(data);
							initModal();"WebContent/channel/result_ChannelList.jsp"
		});
		}
	}else{
		window.location='<%=sitePathInit%>';	
	}
});	








$(".jqChannelInput").change(function(event) {
	
	var mainModule=false;
	if(checkPermission(6,7)){
		mainModule=true;
	}else{
		mainModule=false;
	}
	
	if(mainModule){
	loadChannel();
	}
});


$(".jqChannelInput").keyup(function(event) {
	
	var mainModule=false;
	if(checkPermission(6,7)){
		mainModule=true;
	}else{
		mainModule=false;
	}
	
	if(mainModule){
	loadChannel();	
	}
});




$(".jq_DeleteChannel").click(function () {
	
	if(checkLogged()){
		
		var mainModule=false;
		if(checkPermission(6,5)){
			mainModule=true;
		}else{
			mainModule=false;
		}
		
		if(mainModule){
			
			$.post("<%=sitePathInit%>AChannel",{action:'initDeleteChannel',channel_id:$(this).attr("alt")},
					   function(data){
								$('.basic-container').html(data);
								initModal();
			});
		}	
	}else{
		window.location='<%=sitePathInit%>';	
	}
});	




	
});


function loadChannel(){
	$.post("<%=sitePathInit%>AChannel",$("#formChannelList").serialize(),
			   function(data){
				$(".tblChannelList").html(data);	
	});
}
</script>

