<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.sql.ResultSet,com.misc.lib.CustomHelper" %> 
<%
	CustomHelper ch=new CustomHelper();
%>

<% 
	String sitePathInitSub =request.getContextPath()+"/";


String modulePrivilege="";
String modulePrivilegeValue="";

String moduleStatus="";
String vModuleStatusName="";
		
		
		
%>    
<div class='divCenter txtCenter'>
  <form id="formEditRole" name="form1" method="post" action="">
	<div class='jq_EditRoleStatus txtCenter'>&nbsp;</div>
	 <table width="550" cellspacing="0" cellpadding="5" border="0" align="center">
      <tbody><tr>
        <td width="250" class="text12_tungsten">Role Name</td>
        <td width="200" align="center" class="text10_red"><span class="text12_tungsten">
          <input type="text" id="textfield2" name="rolename" value='<%=request.getAttribute("rolename")%>'>
          </span></td>
        <td width="200" align="center" class="text10_red">&nbsp;</td>
      </tr>
      
      
      <tr>
        <td class="text12_tungsten red text12_tungsten_bold" styles='padding-top:30px;'>
        	<div>Privileges Settings</div>
        </td>
        <td align="center" class="text10_red">&nbsp;</td>
        <td align="center" class="text10_red">&nbsp;</td>
      </tr>
      
      <tr>
        <td width="250" class="text12_tungsten_bold">Module</td>
        <td width="200" align="center" class="text12_tungsten_bold">Privileges</td>
        <td width="200" align="center" class="text12_tungsten_bold">Status</td>
      </tr>
      
<%      
		ResultSet rsprivilege = (ResultSet) request.getAttribute("privilegeList");
    	ResultSet rsModule = (ResultSet) request.getAttribute("moduleList"); 

    	while(rsModule.next()){
    		
    	   
       	 vModuleStatusName="moduleStatus"+rsModule.getString("moduleid");
         moduleStatus=(String) request.getAttribute(""+vModuleStatusName+""); 

%>    
      <tr class='jqRoleSpecSettings'>
        <td class="text12_tungsten" valign='top'>
        		<%=ch.UpperCaseWords(rsModule.getString("modulename"))%>
        </td>
        <td align="center" class="text12_tungsten ">
        
        		<div class='rolePrivilege'>
        		
        		<div class='hnd jq_togglePrivilege jqGroupSpecSetting text10_red' <% if(moduleStatus.equals("disabled")){%>style="display:none;"<%} %> >Edit/Settings</div>
        		
        		<div class='rolePrivilegeSettings jqGroupSpecSetting' style='background-color:#f8f9f9;'>
        	
        		<table  cellspacing="0" cellpadding="5" border='0'>
        		<%      
        			
					while(rsprivilege.next()){
						
					
						modulePrivilege="modulePrivilegeStatus_"+rsModule.getString("moduleid")+"_"+rsprivilege.getString("privilegeid");
						modulePrivilegeValue=(String) request.getAttribute(""+modulePrivilege+"");
				%>  		<tr>
   								<td><%=ch.UpperCaseWords(rsprivilege.getString("privilege"))%></td>    
   								<td>
   								
   								<select   name='privilege_status_<%=rsModule.getString("moduleid")%>_<%=rsprivilege.getString("privilegeid")%>'>
   									<option value='1' <% if(modulePrivilegeValue.equals("1")){ %>selected<% }%>>Grant</option>
   									<option value='0' <% if(modulePrivilegeValue.equals("0")){ %>selected<% }%>>Deny</option>
   								</select>
   								
   								</td>
   							</tr>
   						     		
        		<% }   rsprivilege.beforeFirst(); %>
        		</table>
        		
        		
        		</div>
        		
        		</div>
        		</div>
        
        </td>
        <td align="center" class="text10_red" valign='top'>
 
         	<select  class='jqRoleSettings' name='module_status<%=rsModule.getString("moduleid")%>'>
         		<option value='disabled' <% if(moduleStatus.equals("disabled")){ %>selected<% }%>>Disabled</option>
         		<option value='enabled' <% if(moduleStatus.equals("enabled")){ %>selected<% }%>>Enabled</option>
            </select>
           
        </td>
      </tr>
      
<% } %>      
      
      
       <tr>
        <td align="center" class="text12_tungsten" colspan="3"><img  class='hnd jq_ConfirmEditRole' width="65" height="30" src="<%=sitePathInitSub%>images/button.png"></td>
      </tr>
      
      
    </tbody></table>

   
    
	<input type='hidden' name='action' value='processEdit' >
	<input type='hidden' name='roleid' value="<%=request.getAttribute("roleid")%>" >
	</form>
	 <div class='clr'>&nbsp;</div>
</div>
<script type='text/javascript'>
$(document).ready(function($){
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
	$(".jq_ConfirmEditRole").click(function () {
		if(checkLogged()){
			
			$.post("<%=sitePathInitSub%>ARole",$("#formEditRole").serialize(),
					   function(data){
					
								$(".jq_EditRoleStatus").html(data.status);	
							
							if(data.process){
								$(".jq_EditRoleStatus").removeClass("red");
								$(".jq_EditRoleStatus").addClass("lnkGrn");
								loadRole();
								setTimeout("$.modal.close()",2000);
							}else{
								$(".jq_EditRoleStatus").removeClass("lnkGrn");	
								$(".jq_EditRoleStatus").addClass("red");
							}
			},"json");
			 setTimeout(function(){
				 window.location.reload();
			 },1000);
		}else{
			window.location='<%=sitePathInitSub%>';
		}
	
	});
	$(".rolePrivilegeSettings").hide();
});
</script>
         