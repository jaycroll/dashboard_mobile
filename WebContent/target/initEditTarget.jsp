<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.sql.ResultSet,com.misc.lib.CustomHelper" %> 
<% 
	String sitePathInit =request.getContextPath()+"/";

	ResultSet rs = (ResultSet) request.getAttribute("detTarget");
		if (rs.next()) {  
		
	  do{
%>

<script>
		$(function() {
			$( ".datepicker" ).datepicker();
		});
</script>
	
<div class='divCenter txtCenter'>
  <form id="formEditTarget" name="form1" method="post" action="">
  
  <div class='modalTitle' >Edit Target</div>
  <div class='jq_EditTargetStatus txtCenter' >&nbsp;</div>

	 <table class='txtCenter' width="280" cellspacing="0" cellpadding="5" border="0" align="center">
      <tbody>
      

     <tr>
        <td width="250" class="text12_tungsten" align='left'>Target Amount:</td>
        <td width="200" align="left" class="text10_red">
        	<input type="text" size="20" id="textfield" name="target_amount" value="<%=rs.getString("target_amount")%>">
        </td>
      </tr>
      
      
       <tr>
        <td width="250" class="text12_tungsten" align='left'>Target Date:</td>
        <td width="200" align="left" class="text10_red">
        	<input type="text" size="20" class="datepicker" name="target_date" value='<%=rs.getString("target_date")%>'>
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
						%>			<option value='<%=rstGrp.getString("target_group_id")%>' <% if(rstGrp.getString("target_group_id").equals(rs.getString("target_group_id").toString())){ %>selected<% }%>><%=rstGrp.getString("target_group_name")%></option>
				    	<% 			} while (rstGrp.next());
										}
						%>	
        		</select>
        </td>
      </tr>
      
       <tr>
        <td width="250" class="text12_tungsten" align='left'>Department</td>
        <td width="200" align="left" class="text10_red">
        		<select name='department_id' class='text_12_tungsten jq_DeparmentSelect'>
				        <%		
				    				ResultSet rst = (ResultSet) request.getAttribute("listDepartment");							
									if (rst.next()) {  
									 do{
						%>			<option value='<%=rst.getString("department_id")%>' <% if(rst.getString("department_id").equals(rs.getString("department_id").toString())){ %>selected<% }%>><%=rst.getString("department_name")%></option>
				    	<% 			} while (rst.next());
										}
						%>	
        		</select>
        </td>
      </tr>
      
       <tr>
        <td width="250" class="text12_tungsten" align='left'>User</td>
        <td width="200" align="left" class="text10_red">
        		<select name='user_id' class='jq_UserSelect text_12_tungsten''>
				       <%=request.getAttribute("optionHtml")%>
        		</select>
        </td>
      </tr>
        
       <tr>
        <td align="center" class="text12_tungsten" colspan="3"><img  class='jq_ConfirmEditTarget hnd'  src="<%=sitePathInit%>images/edit.png"></td>
      </tr>
      
      
    </tbody></table>
    
    <input type='hidden' name='target_id' value='<%=rs.getString("target_id")%>' >
	<input type='hidden' name='action' value='processEditTarget' >
	</form>
</div>
<%		
		 } while (rs.next());
		
	    }	
%>	

<script>
$(document).ready(function(){
	$(".jq_ConfirmEditTarget").click(function () {
		if(checkLogged()){
			
			
			var mainModule=false;
			if(checkPermission(4,2)){
				mainModule=true;
			}else{
				mainModule=false;
			}
			
			if(mainModule){
			$.post("<%=sitePathInit%>ATarget",$("#formEditTarget").serialize(),
					   function(data){
					
								$(".jq_EditTargetStatus").html(data.status);	
							
							if(data.process){
								$(".jq_EditTargetStatus").removeClass("red");
								$(".jq_EditTargetStatus").addClass("lnkGrn");
								loadTarget();
								setTimeout("$.modal.close()",2000);
							}else{
								$(".jq_EditTargetStatus").removeClass("lnkGrn");	
								$(".jq_EditTargetStatus").addClass("red");
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
         