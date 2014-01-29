<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.sql.ResultSet,com.misc.lib.CustomHelper" %>
<% 
	String sitePathInitSub =request.getContextPath()+"/";
	
	ResultSet rs = (ResultSet) request.getAttribute("detProductTarget");
		if (rs.next()) {  
		
	  do{
%>  
  <div class='modalTitle' >Delete Target</div>
<div class='divCenter txtCenter jq_DeleteTargetStatus'>

		<div class='txtCenter'>Are you sure you want to delete this target?</div>
		<div class='txtCenter lnkGrn '>Channel Name: <%=rs.getString("channel_name")%></div>
		<div class='txtCenter lnkGrn '>Target Date: <%=rs.getString("target_date")%></div>
		<div class='txtCenter lnkGrn '>Target Amount:<%=rs.getString("target_amount")%></div>
<%-- 		 --%>
		
		<div class='txtCenter'>
				 <span class='lnkRed jq_ConfirmDeleteTarget' alt='<%=rs.getString("target_id")%>'>Confirm</span> &nbsp; <span  class='lnkRed jq_ModalClose'>Cancel</span>
		</div>
		<div class='clr'>&nbsp;</div>
</div>
<%		
		 } while (rs.next());
		
	    }	
%>	
<script>
$(document).ready(function(){

	$(".jq_ConfirmDeleteTarget").click(function() {
		if(checkLogged()){
			
			
			var mainModule=false;
			if(checkPermission(4,5)){
				mainModule=true;
			}else{
				mainModule=false;
			}
			
			if(mainModule){	
			$.post("<%=sitePathInitSub%>AProductTarget",{action:'processDeleteTarget',target_id:$(this).attr("alt")},
					   function(data){
				
							$(".jq_DeleteTargetStatus").html(data.status);	
							
							if(data.process){
								$(".jq_DeleteTargetStatus").removeClass("red");	
								loadTarget();
								setTimeout("$.modal.close()",2000);
							}else{
								$(".jq_DeleteTargetStatus").addClass("red");	
							}
							
							
			},"json");
			
			}
			setTimeout(function(){
				window.location.reload();
			},1000);
		}else{
			window.location='<%=sitePathInitSub%>';
		}

	});
		

});</script>