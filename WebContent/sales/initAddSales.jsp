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
  <form id="formAddSales" name="form1" method="post" action="">
  
  <div class='modalTitle' >Add Sales</div>
  <div class='jq_AddSalesStatus txtCenter' >&nbsp;</div>

	 <table class='txtCenter' width="280" cellspacing="0" cellpadding="5" border="0" align="center">
      <tbody>
      


 		<tr>
        <td width="250" class="text12_tungsten" align='left'>Channel</td>
        <td width="200" align="left" class="text10_red">
        		<select name='channel_id' class='text_12_tungsten''>
				        <%		
				    				ResultSet rstGrp = (ResultSet) request.getAttribute("listChannel");							
									if (rstGrp.next()) {  
									 do{
						%>			<option value='<%=rstGrp.getString("channel_id")%>'><%=rstGrp.getString("channel_name")%></option>
				    	<% 			} while (rstGrp.next());
										}
						%>	
        		</select>
        </td>
      </tr>
      
      
     <tr>
        <td width="250" class="text12_tungsten" align='left'>Revenue Date:</td>
        <td width="200" align="left" class="text10_red">
        	<input type="text" size="20" class="datepicker" name=revenue_date>
        </td>
      </tr>
      
       
      <tr>
        <td width="250" class="text12_tungsten" align='left'>Amount:</td>
        <td width="200" align="left" class="text10_red">
        	<input type="text" size="20" id="textfield" name="amount">
        </td>
      </tr>
      
      
       
      
        <tr>
        <td width="250" class="text12_tungsten" align='left'>Revenue Type</td>
        <td width="200" align="left" class="text10_red">
        		<select name='revenue_type' class='text_12_tungsten''>
        		  	<option value='Sales'>Sales</option>
        		  	<option value='LOP'>LOP</option>
        		  	<option value='Refund'>Refund</option>
        		</select>
        </td>
      </tr>
      
        
       <tr>
        <td align="center" class="text12_tungsten" colspan="3"><img  class='jq_ConfirmAddSales' width="65" height="30" src="<%=sitePathInit%>images/add_button.png"></td>
      </tr>
      
      
    </tbody></table>
    
	<input type='hidden' name='action' value='processAddSales' >
	</form>
</div>
<script>
$(document).ready(function(){
	$(".jq_ConfirmAddSales").click(function () {
		if(checkLogged()){
			
			var mainModule=false;
			if(checkPermission(5,1)){
				mainModule=true;
			}else{
				mainModule=false;
			}
			
			if(mainModule){
			$.post("<%=sitePathInit%>ASales",$("#formAddSales").serialize(),
					   function(data){
					
							$(".jq_AddSalesStatus").html(data.status);	
							
							if(data.process){
								$(".jq_AddSalesStatus").removeClass("red");
								$(".jq_AddSalesStatus").addClass("lnkGrn");
								loadSales();
								setTimeout("$.modal.close()",2000);
							}else{
								$(".jq_AddSalesStatus").removeClass("lnkGrn");	
								$(".jq_AddSalesStatus").addClass("red");
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