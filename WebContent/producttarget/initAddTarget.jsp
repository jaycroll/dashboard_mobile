<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.sql.ResultSet,com.misc.lib.CustomHelper" %> 
<% 
	String sitePathInit =request.getContextPath()+"/";
%>    


<script>
		$(function() {
			$( ".datepicker" ).datepicker();
		});
</script>
	
<div class='divCenter txtCenter'>
  <form id="formAddTarget" name="form1" method="post" action="">
  
  <div class='modalTitle' >Add Target</div>
  <div class='jq_AddTargetStatus txtCenter' >&nbsp;</div>

	 <table class='txtCenter' width="280" cellspacing="0" cellpadding="5" border="0" align="center">
      <tbody>
      <tr>
        <td width="250" class="text12_tungsten" align='left'>Channel Name</td>
        <td width="200" align="left" class="text10_red">
        		<select name='channel_id' class='text_12_tungsten''>
				        <%		
				    				ResultSet products = (ResultSet) request.getAttribute("listProducts");							
									if (products.next()) {  
									 do{
						%>			<option value='<%=products.getString("channel_id")%>'><%=products.getString("channel_name")%></option>
				    	<% 			} while(products.next());
										}
						%>	
        		</select>
        </td>
      </tr>

     <tr>
        <td width="250" class="text12_tungsten" align='left'>Target Amount:</td>
        <td width="200" align="left" class="text10_red">
        	<input type="text" size="20" id="textfield" name="target_amount">
        </td>
      </tr>
      
      
       <tr>
        <td width="250" class="text12_tungsten" align='left'>Target Date:</td>
        <td width="200" align="left" class="text10_red">
        	<input type="text" size="20" class="datepicker" name="target_date">
        </td>
      </tr>
      
       <tr>
        <td width="250" class="text12_tungsten" align='left'>Target Group</td>
        <td width="200" align="left" class="text10_red">
        		<select name='target_group_id' class='text_12_tungsten''>
				        <%		
				    				ResultSet rstGrp = (ResultSet) request.getAttribute("listTargetGroup");							
									if (rstGrp.next()) {  
									 do{
						%>			<option value='<%=rstGrp.getString("target_group_id")%>'><%=rstGrp.getString("target_group_name")%></option>
				    	<% 			} while (rstGrp.next());
										}
						%>	
        		</select>
        </td>
      </tr>

        
       <tr>
        <td align="center" class="text12_tungsten" colspan="3"><img  class='jq_ConfirmAddTarget' width="65" height="30" src="<%=sitePathInit%>images/add_button.png"></td>
      </tr>
      
      
    </tbody></table>
    
	<input type='hidden' name='action' value='processAddTarget' >
	</form>
</div>
<script>
$(document).ready(function(){
	$(".jq_ConfirmAddTarget").click(function () {
		if(checkLogged()){
			
			if(checkPermission(4,1)){
				mainModule=true;
			}else{
				mainModule=false; 
			}
			
			if(mainModule){
			
			$.post("<%=sitePathInit%>AProductTarget",$("#formAddTarget").serialize(),
					   function(data){
					
							$(".jq_AddTargetStatus").html(data.status);	
							
							if(data.process){
								$(".jq_AddTargetStatus").removeClass("red");
								$(".jq_AddTargetStatus").addClass("lnkGrn");
								loadTarget();
								setTimeout("$.modal.close()",2000);
							}else{
								$(".jq_AddTargetStatus").removeClass("lnkGrn");	
								$(".jq_AddTargetStatus").addClass("red");
							}
			},"json");
		
			}
			setTimeout(function(){
				window.location.reload();
			},1000);
		}else{
			window.location='<%=sitePathInit%>';
		}
		
	});
});
</script>