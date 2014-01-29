<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %> 
<% 
	String sitePathInitSub =request.getContextPath()+"/";
%>    
<div class='divCenter txtCenter jq_DeleteUserStatus'>

		<div class='txtCenter'>Are you sure you want to delete this user?</div><br />
		<div class='txtCenter lnkGrn '><%=request.getAttribute("fullname")%> (<%=request.getAttribute("rolename")%>)</div><br />
		<div class='txtCenter'>
				 <span class='lnkRed jq_ConfirmDeleteAccess' alt='<%=request.getAttribute("userid")%>'>Confirm</span> &nbsp; <span  class='lnkRed jq_ModalClose'>Cancel</span>
		</div><br />
		<div class='clr'>&nbsp;</div>
</div>
<script>
	$(document).ready(function(){
		$(".jq_ConfirmDeleteAccess").click(function () {
			if(checkLogged()){
				
				$.post("<%=sitePathInitSub%>AUser",{action:'processDelete',userid:$(this).attr("alt")},
						   function(data){
					
								$(".jq_DeleteUserStatus").html(data.status);	
								
								if(data.process){
									$(".jq_DeleteUserStatus").removeClass("red");	
									loadUser();
									setTimeout("$.modal.close()",2000);
								}else{
									$(".jq_DeleteUserStatus").addClass("red");	
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