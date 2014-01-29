<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.sql.ResultSet,com.misc.lib.CustomHelper" %> 
<% 
	String sitePathInitSub =request.getContextPath()+"/";
%>    
<div class='divCenter txtCenter'>
  <form id="formAddPrivilege" name="form1" method="post" action="">
	<div class='jq_AddPrivilegeStatus txtCenter'>&nbsp;</div>
	 <table width="550" cellspacing="0" cellpadding="5" border="0" align="center">
      <tbody>
      
      
      
      <tr>
        <td width="250" class="text12_tungsten">Privilege ID</td>
        <td width="200" align="center" class="text10_red"><span class="text12_tungsten">
          <input type="text" id="textfield2" name="privilegeid">
          </span></td>
        <td width="200" align="center" class="text10_red">&nbsp;</td>
      </tr>
       
      <tr>
        <td width="250" class="text12_tungsten">Privilege</td>
        <td width="200" align="center" class="text10_red"><span class="text12_tungsten">
          <input type="text" id="textfield2" name="privilege">
          </span></td>
        <td width="200" align="center" class="text10_red">&nbsp;</td>
      </tr>
       
       <tr>
        <td align="center" class="text12_tungsten" colspan="3"><img  class=' jq_ConfirmAddPrivilege' width="65" height="30" src="<%=sitePathInitSub%>images/add_button.png"></td>
      </tr>
      
      
    </tbody></table>
    
	<input type='hidden' name='action' value='processAdd' >
	</form>
	 <div class='clr'>&nbsp;</div>
</div>
<script>
$(document).ready(function(){
	$(".jq_ConfirmAddPrivilege").click(function () {
		if(checkLogged()){
			
			$.post("<%=sitePathInitSub%>APrivilege",$("#formAddPrivilege").serialize(),
					   function(data){
					
							$(".jq_AddPrivilegeStatus").html(data.status);	
							
							if(data.process){
								$(".jq_AddPrivilegeStatus").removeClass("red");
								$(".jq_AddPrivilegeStatus").addClass("lnkGrn");
								
								setTimeout("$.modal.close()",2000);
								setTimeout(function () { location.reload(1); }, 2000);
							}else{
								$(".jq_AddPrivilegeStatus").removeClass("lnkGrn");	
								$(".jq_AddPrivilegeStatus").addClass("red");
							}
			},"json");
			setTimeout(function(){
				 window.location.reload();
			 },1000);
		}else{
			window.location='<%=sitePathInitSub%>';
		}
	
	});
});
</script>