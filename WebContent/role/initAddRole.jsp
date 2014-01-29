<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.sql.ResultSet,com.misc.lib.CustomHelper" %> 
<%
	CustomHelper ch=new CustomHelper();
%>

<% 
	String sitePathInitSub =request.getContextPath()+"/";
%>    
<div class='divCenter txtCenter'>
  <form id="formAddRole" name="form1" method="post" action="">
	<div class='jq_AddRoleStatus txtCenter'>&nbsp;</div>
	 <table width="550" cellspacing="0" cellpadding="5" border="0" align="center">
      <tbody><tr>
        <td width="250" class="text12_tungsten">Role Name</td>
        <td width="200" align="center" class="text10_red"><span class="text12_tungsten">
          <input type="text" id="textfield2" name="rolename">
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
%>    
      <tr class='jqRoleSpecSettings'>
        <td class="text12_tungsten" valign='top'>
        		<%=ch.UpperCaseWords(rsModule.getString("modulename"))%>
        </td>
        <td align="center" class="text12_tungsten">
        
        		<div class='rolePrivilege'>
        		
        		<div class='hnd jq_togglePrivilege jqGroupSpecSetting text10_red' style="display:none;">Edit/Settings</div>
        		
        		<div class='rolePrivilegeSettings jqGroupSpecSetting' style='background-color:#f1f1f1;'>
        	
        		<table  cellspacing="0" cellpadding="5" border='0'>
        		<%      
        			
					while(rsprivilege.next()){
				%>  		<tr>
   								<td><%=ch.UpperCaseWords(rsprivilege.getString("privilege"))%></td>    
   								<td>
   								
   								<select name='privilege_status_<%=rsModule.getString("moduleid")%>_<%=rsprivilege.getString("privilegeid")%>'>
   									<option value='1'>Grant</option>
   									<option value='0'>Deny</option>
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
        			<option value='disabled'>Disabled</option>
        			<option value='enabled'>Enabled</option>
        	</select>
        </td>
      </tr>
      
<% } %>      
      
      
       <tr>
        <td align="center" class="text12_tungsten" colspan="3"><img  class=' jq_ConfirmAddRole hnd' width="65" height="30" src="<%=sitePathInitSub%>images/button.png"></td>
      </tr>
      
      
    </tbody></table>

   
    
	<input type='hidden' name='action' value='processAdd' >
	</form>
	 <div class='clr'>&nbsp;</div>
</div>
<script type='text/javascript'>
$(document).ready(function(){
	$(".jq_ConfirmAddRole").click(function () {
		if(checkLogged()){
			
			$.post("<%=sitePathInitSub%>ARole",$("#formAddRole").serialize(),
					   function(data){
					
							$(".jq_AddRoleStatus").html(data.status);	
							
							if(data.process){
								$(".jq_AddRoleStatus").removeClass("red");
								$(".jq_AddRoleStatus").addClass("lnkGrn");
								loadRole();
								setTimeout("$.modal.close()",2000);
							}else{
								$(".jq_AddRoleStatus").removeClass("lnkGrn");	
								$(".jq_AddRoleStatus").addClass("red");
							}
			},"json");
			
		}else{
			window.location='<%=sitePathInitSub%>';
		}
	
	});
	$(".rolePrivilegeSettings").hide();

});

</script>
         