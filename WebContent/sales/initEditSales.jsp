<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.sql.ResultSet,com.misc.lib.CustomHelper" %> 
<% 
	String sitePathInit =request.getContextPath()+"/";

	ResultSet rs = (ResultSet) request.getAttribute("detSales");
		if (rs.next()) {  
		
	  do{
%>
	
<div class='divCenter txtCenter'>
  <form id="formEditSales" name="form1" method="post" action="">
  
  <div class='modalTitle' >Edit Sales</div>
  <div class='jq_EditSalesStatus txtCenter' >&nbsp;</div>

	 <table class='txtCenter' width="280" cellspacing="0" cellpadding="5" border="0" align="center">
      <tbody>
      
      <tr>
        <td width="250" class="text12_tungsten" align='left'>Channel Name:</td>
        <td width="200" align="left" class="text10_red">
        	<%=rs.getString("channel_name")%>
        	<input type="hidden" size="20" id="textfield" name="channel_id" value="<%=rs.getString("channel_id")%>">
        	
        </td>
      </tr>
		

	  <tr>
        <td width="250" class="text12_tungsten" align='left'>Revenue Date:</td>
        <td width="200" align="left" class="text10_red">
        	<%=rs.getString("revenue_date")%>
        	<input type="hidden" size="20" id="textfield" name="revenue_date" value="<%=rs.getString("revenue_date")%>">
        </td>
      </tr>
      
   
	  <tr>
        <td width="250" class="text12_tungsten" align='left'>Amount:</td>
        <td width="200" align="left" class="text10_red">
        	<input type="text" size="20" id="textfield" name="amount" value="<%=rs.getString("amount")%>">
        </td>
      </tr> 
      
       <tr>
        <td width="250" class="text12_tungsten" align='left'>Revenue Type:</td>
        <td width="200" align="left" class="text10_red">
        	<%=rs.getString("revenue_type")%>
        	<input type="hidden" size="20" id="textfield" name="revenue_type" value="<%=rs.getString("revenue_type")%>">
        </td>
      </tr>     
      
   
   
       <tr>
        <td align="center" class="text12_tungsten" colspan="3"><img  class='jq_ConfirmEditSales hnd'  src="<%=sitePathInit%>images/edit.png"></td>
      </tr>
      
      
    </tbody></table>
    
   <input type='hidden' name='action' value='processEditSales' >
	</form>
</div>
<%		
		 } while (rs.next());
		
	    }	
%>	
<script>
$(document).ready(function(){
	$(".jq_ConfirmEditSales").click(function () {
		if(checkLogged()){
			
			
			var mainModule=false;
			if(checkPermission(5,3)){
				mainModule=true;
			}else{
				mainModule=false;
			}
			
			if(mainModule){
				
			$.post("<%=sitePathInit%>ASales",$("#formEditSales").serialize(),
					   function(data){
					
								$(".jq_EditSalesStatus").html(data.status);	
							
							if(data.process){
								$(".jq_EditSalesStatus").removeClass("red");
								$(".jq_EditSalesStatus").addClass("lnkGrn");
								loadSales();
								setTimeout("$.modal.close()",1000);
							}else{
								$(".jq_EditSalesStatus").removeClass("lnkGrn");	
								$(".jq_EditSalesStatus").addClass("red");
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