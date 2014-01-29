<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,com.misc.lib.CustomHelper" %> 
<% 
	String sitePathInitSub =request.getContextPath()+"/";
	CustomHelper ch=new CustomHelper();
%>    
<div class='divCenter txtCenter jq_DeletePrivilegeStatus'>

		<div class='txtCenter'>Are you sure you want to delete this privilege?</div>
		<div class='txtCenter lnkGrn '><%=ch.UpperCaseWords((String)request.getAttribute("privilege"))%></div>
		<div class='txtCenter'>
				 <span class='lnkRed jq_ConfirmDeletePrivilege' alt='<%=request.getAttribute("privilegeid")%>'>Confirm</span> &nbsp; <span  class='lnkRed jq_ModalClose'>Cancel</span>
		</div>
		<div class='clr'>&nbsp;</div>
</div>
<script>
$(document).ready(function(){
	$(".jq_ConfirmDeletePrivilege").click(function () {
		if(checkLogged()){
			
			$.post("<%=sitePathInitSub%>APrivilege",{action:'processDelete',privilegeid:$(this).attr("alt")},
					   function(data){
				
							$(".jq_DeletePrivilegeStatus").html(data.status);	
							
							if(data.process){
								$(".jq_DeletePrivilegeStatus").removeClass("red");		
								setTimeout("$.modal.close()",2000);
								setTimeout(function () { location.reload(1); }, 2000);
							}else{
								$(".jq_DeletePrivilegeStatus").addClass("red");	
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