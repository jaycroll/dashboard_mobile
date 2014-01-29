<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.sql.ResultSet,com.misc.lib.CustomHelper" %> 
<% 
	String sitePathInit =request.getContextPath()+"/";

	ResultSet rs = (ResultSet) request.getAttribute("detChannel");
		if (rs.next()) {  
		
	  do{
%>
	
<div class='divCenter txtCenter'>
  <form id="formEditChannel" name="form1" method="post" action="">
  
  <div class='modalTitle' >Edit Channel</div>
  <div class='jq_EditChannelStatus txtCenter' >&nbsp;</div>

	 <table class='txtCenter' width="280" cellspacing="0" cellpadding="5" border="0" align="center">
      <tbody>
      
      
      <tr>
        <td width="250" class="text12_tungsten" align='left'>Channel ID:</td>
        <td width="200" align="left" class="text10_red">
        	<%=rs.getString("channel_id")%>
        </td>
      </tr>

     <tr>
        <td width="250" class="text12_tungsten" align='left'>Channel Name:</td>
        <td width="200" align="left" class="text10_red">
        	<input type="text" size="20" id="textfield" name="channel_name" value="<%=rs.getString("channel_name")%>">
        </td>
      </tr>
      
       <tr>
        <td width="250" class="text12_tungsten" align='left'>Channel Group</td>
        <td width="200" align="left" class="text10_red">
        		<select name='channel_group_id' class='text_12_tungsten''>
				        <%		
				    				ResultSet rstGrp = (ResultSet) request.getAttribute("listChannelGroup");							
									if (rstGrp.next()) {  
									 do{
						%>			<option value='<%=rstGrp.getString("channel_group_id")%>' <% if(rstGrp.getString("channel_group_id").equals(rs.getString("channel_group_id").toString())){ %>selected<% }%>><%=rstGrp.getString("channel_group_name")%></option>
				    	<% 			} while (rstGrp.next());
										}
						%>	
        		</select>
        </td>
      </tr>
      
       <tr>
        <td width="250" class="text12_tungsten" align='left'>Automated</td>
        <td width="200" align="left" class="text10_red">
        		<select name='channel_automated' class='text_12_tungsten'>
				       <option value='Yes' <% if(rs.getString("channel_automated").toString().equals("Yes")){ %>selected<% }%>>Yes</option>
				       <option value='No' <% if(rs.getString("channel_automated").toString().equals("No")){ %>selected<% }%>>No</option>
        		</select>
        </td>
      </tr>
      
      
      
       <tr>
        <td width="250" class="text12_tungsten" align='left'>App</td>
        <td width="200" align="left" class="text10_red">
        		<select name='app_id' class='text_12_tungsten'>
        				<option value="0"></option>
				        <%		
				    				ResultSet rst = (ResultSet) request.getAttribute("listApp");							
									if (rst.next()) {  
									 do{
						%>			<option value='<%=rst.getString("app_id")%>' <% if(rst.getString("app_id").equals(rs.getString("app_id").toString())){ %>selected<% }%>><%=rst.getString("app_name")%></option>
				    	<% 			} while (rst.next());
										}
						%>	
        		</select>
        </td>
      </tr>
   
       <tr>
        <td align="center" class="text12_tungsten" colspan="3"><img  class='jq_ConfirmEditChannel hnd' width="65" height="30" src="<%=sitePathInit%>images/edit.png"></td>
      </tr>
      
      
    </tbody></table>
    
    <input type='hidden' name='channel_id' value='<%=rs.getString("channel_id")%>' >
	<input type='hidden' name='action' value='processEditChannel' >
	</form>
</div>
<%		
		 } while (rs.next());
		
	    }	
%>	
<script>
$(document).ready(function(){
	$(".jq_ConfirmEditChannel").click(function () {
		if(checkLogged()){
			var mainModule=false;
			if(checkPermission(6,2)){
				mainModule=true;
			}else{
				mainModule=false;
			}
			
			if(mainModule){
			$.post("<%=sitePathInit%>AChannel",$("#formEditChannel").serialize(),
					   function(data){
					
								$(".jq_EditChannelStatus").html(data.status);	
							
							if(data.process){
								$(".jq_EditChannelStatus").removeClass("red");
								$(".jq_EditChannelStatus").addClass("lnkGrn");
								loadChannel();
								setTimeout("$.modal.close()",2000);
							}else{
								$(".jq_EditChannelStatus").removeClass("lnkGrn");	
								$(".jq_EditChannelStatus").addClass("red");
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