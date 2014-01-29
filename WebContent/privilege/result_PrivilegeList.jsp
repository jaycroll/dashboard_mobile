<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ include file="../sub/header.jsp"%>
<%@ page import="java.util.*,java.sql.ResultSet,com.misc.lib.CustomHelper,com.misc.lib.RolePermission" %> 
<%  

		CustomHelper ch=new CustomHelper();
		String sitePathInitS =request.getContextPath()+"/";
		
		HttpSession sSub=request.getSession();
		RolePermission rp=new RolePermission();
		rp.gAppProperties(request.getRealPath("/"));
		
%>
<style type="text/css">
.text_12_tungsten {font-family:"Lucida Sans Unicode", "Lucida Grande", sans-serif;
	font-size:12px;
	color:#333;
	text-align: center;

</style>

<div id="header" align="center"><img src="<%=sitePathInitS%>images/ppe-blk.png"/></div>
<div id="title"><a href="<%=sitePathInitS%>" class="lucida_21_black"><img src="<%=sitePathInit%>images/lock_small.png" align="absmiddle"/>Dashboard</a></div>

<% if(loggedState){%>
<div id="menu">
	<div id="menu-right" class="lucida_12_tungsten_b"> Hello, <%=sess.getAttribute("username")%> (<%=sess.getAttribute("rolename")%>). <a href="<%=sitePathInit%>User/logout"><span class="lucida_12_red_b">Logout</span></a></div>
</div>

<div id="submenu">

<div>

<ul class="tabs jq_UserTabs access">
            <li id='access1' ><a href="<%=sitePathInitS%>User" alt='loadUser' class=' ibtntabs' title='User Management'></a></li>
            <li id='access2' ><a href="<%=sitePathInitS%>Roles" alt='loadRole' class='ibtntabs' title='Role Management'></a></li>
            <li id='access3' ><a href="<%=sitePathInitS%>Privilege" alt='loadPrivilege' class='ibtntabs active' title='Privileges Management'></a></li>
     	 	<li id='access4' ><a href="<%=sitePathInitS%>Target" alt='loadPrivilege' class='ibtntabs' title='Target Management'></a></li>
     	 	<li id='access7' ><a href="<%=sitePathInit%>ProductTarget" alt='loadPrivilege' class='ibtntabs' title='Product Target Management'></a></li>
     	 	<li id='access5' ><a href="<%=sitePathInitS%>Channel" alt='loadPrivilege' class='ibtntabs' title='Channel Management'></a></li>
     	 	<li id='access6' ><a href="<%=sitePathInitS%>Sales"  class='ibtntabs' title='Sales Management'></a></li>
     	 </ul>

</div>

</div>

<form action="" method="post" name="form1" id="formPrivilegeList">
    <table width="650" cellspacing="0" cellpadding="5" border="0" class='lucida_12_tungsten'>
      <tbody>
      <tr>
        <td width="250" class="text12_tungsten" align="left">
         <% if(rp.verifyModule(Integer.parseInt(sSub.getAttribute("roleid").toString()),3,1)){ %>
        <a href="javascript:void(0);" class="jq_AddPrivilege"><img width="28" height="26" src="<%=sitePathInitS%>images/add.png" class='hnd'></a> <span class='lucida_12_tungsten_b'>Privileges</span>
        <% } else{%>
        	<span class='lucida_12_tungsten_b' style="margin-top:-2%">Privileges</span>
        <%
        }%>
        </td>
        <td><span class='lucida_12_tungsten_b' style="margin-top:2%">Bit</span></td>
        <td><span class='lucida_12_tungsten_b' style="margin-top:2%">Delete</span></td>
        </tr>
     </tbody></table>

      
<table width="650" cellspacing="0" cellpadding="5" border="0" align="center" class='tblPrivilegelist'>
<%		
		
		ResultSet rsPrivilege = (ResultSet) request.getAttribute("prvList"); 
		while(rsPrivilege.next()){
%>  
   
<tr>
        <td><span class="text12_tungsten hnd jq_EditPrivilege" alt='<%=rsPrivilege.getString("privilegeid") %>' width="250"><%=ch.UpperCaseWords(rsPrivilege.getString("privilege"))%></span></td>
        <td class="lucida_10_red" width="200"><%=rsPrivilege.getString("privilegeid") %></td>
        <% if(rp.verifyModule(Integer.parseInt(sSub.getAttribute("roleid").toString()),3,5)){ %>
        <td class="lucida_10_red  hnd jq_DeletePrivilege"  alt="<%=rsPrivilege.getString("privilegeid") %>" width="200">
        
        <span class="jq_DeletePrivilege">X</span>
       
        
         </td>
         <% } %>
        
</tr>

<% }%>
		</table>
	     <input type='hidden' name='action' value='subLoadPrivilege'>
	</form>
<% 
}
else{ %>

		<div class='divCenter txtCenter'>
							   <div class='access_denied'>Access Denied</div>
							   <div class='clr'>&nbsp;</div>
							  <div class='clr'><a  style='color:#3E7836;' href="<%=sitePathInit%>">Back to Main</a></div>
	                           <div class='clr'>&nbsp;</div>
	                           </div>
<% } %>      
</div></div></div>

</div>
<script type='text/javascript' src='<%=sitePathInitS%>include/custom.jsp'></script>
<script type='text/javascript' src='<%=sitePathInitS%>include/custom_logged.jsp'></script>
<br/>
<br/>
<script type='text/javascript'>
$(document).ready(function($){

		$(".jq_AddPrivilege").click(function () {
			
			if(checkLogged()){
					
					$.post("<%=sitePathInit%>APrivilege",{action:'initAdd'},
							   function(data){
										$('.basic-container').html(data);
										initModal();
					});
			}else{
				window.location='<%=sitePathInit%>';	
			}
		});
		
		
		
	$(".jq_EditPrivilege").click(function () {
			
			if(checkLogged()){
				
				var mainModule=false;
				
				if(checkPermission(3,2)){
					mainModule=true;
				}else{
					mainModule=false;
				}
				
				if(mainModule){
					$.post("<%=sitePathInit%>APrivilege",{action:'initEdit',privilegeid:$(this).attr("alt")},
							   function(data){
										$('.basic-container').html(data);
										initModal();
					});
				}
				
				
				
				
				
			}else{
				window.location='<%=sitePathInit%>';	
			}
		});	
		
	
		
		
		
		
		$(".jq_DeletePrivilege").click(function () {
			
			if(checkLogged()){
					
					$.post("<%=sitePathInit%>APrivilege",{action:'initDelete',privilegeid:$(this).attr("alt")},
							   function(data){
										$('.basic-container').html(data);
										initModal();
					});
			}else{
				window.location='<%=sitePathInit%>';	
			}
		});	
		
		
		
		
		
		
		$(".jq_togglePrivilege").click(function () {
			$(this).parent(".rolePrivilege").find(".rolePrivilegeSettings").toggle();
		});
		
		
		$(".jqRoleSettings").change(function () {
			if($(this).find('option:selected').val()=='enabled'){
				$(this).parents(".jqRoleSpecSettings").find(".jqGroupSpecSetting").show();
			}else{
				$(this).parents(".jqRoleSpecSettings").find(".jqGroupSpecSetting").hide();
			}
		});
		
		
		
		
});	


function loadPrivilege(){
	$.post("<%=sitePathInit%>APrivilege",$("#formPrivilegeList").serialize(),
			   function(data){
		$(".tblPrivilegelist").html(data);	
	});
}


</script>
<%@ include file="../sub/footer.jsp"%>
