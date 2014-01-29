<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.sql.ResultSet,com.misc.lib.CustomHelper,com.misc.lib.RolePermission" %> 


<% 
		String sitePathInitMin =request.getContextPath()+"/";
		CustomHelper ch=new CustomHelper();
		
		HttpSession sSub=request.getSession();
		RolePermission rp=new RolePermission();
		rp.gAppProperties(request.getRealPath("/"));
		
		Boolean deleteChannel=rp.verifyModule(Integer.parseInt(sSub.getAttribute("roleid").toString()),6,5);
		Boolean editChannel=rp.verifyModule(Integer.parseInt(sSub.getAttribute("roleid").toString()),6,2);
		
		ResultSet rs = (ResultSet) request.getAttribute("listChannel");
		
		if (rs.next()) {  
		
	  do{
	  
%>	  
<tr>
      <td style="text-align: center" class="lucida_10_red <% if(deleteChannel){ %>jq_DeleteChannel hnd <% } %>" alt="<%=rs.getString("channel_id")%>"><% if(deleteChannel){ %>x<% } %></td>
      <td style="text-align: center" class="text_12_tungsten"><%=rs.getString("channel_id")%></td>
      <td style="text-align: center" class="text_12_tungsten"><%=rs.getString("channel_name")%></td>
      <td style="text-align: center" class="text_12_tungsten"><%=rs.getString("channel_group_name")%></td>
      <td style="text-align: center" class="text_12_tungsten"><%=rs.getString("channel_automated")%></td>
      <td style="text-align: center" class="text_12_tungsten"><%=rs.getString("app_name")%></td>
      <td style="text-align: center" class="lucida_10_red <% if(editChannel){ %>jq_EditChannel hnd<% } %>" alt="<%=rs.getString("channel_id")%>">
      <% if(editChannel){ %>
      <img  class='hnd'  src="<%=sitePathInitMin%>images/pencil-edit.png">
      <% } %>
      </td>
</tr>

<%		
		 } while (rs.next());
		
	    }	
%>	
    
    
<%-- 
		String sitePathInitMin =request.getContextPath()+"/";
		CustomHelper ch=new CustomHelper();

		ResultSet rs = (ResultSet) request.getAttribute("listTarget");
		
		if (rs.next()) {  
		
	  do{

	<tr>
      <td class="lucida_10_red jq_DeleteTarget hnd"  alt="<%=rs.getString("target_id")%>" style="text-align: center">x</td>
      <td style="text-align: center"><%=rs.getString("target_date")%></td>
	  <td style="text-align: center"><%=rs.getString("department_name")%></td>
      <td style="text-align: center"><%=rs.getString("target_group_name")%></td>
      <td style="text-align: center"><%=rs.getString("target_amount")%></td>
      <td style="text-align: center">
      	<%
      			if(rs.getString("userid")==null){
      					out.println("");
      			}else{
      					out.println(rs.getString("userfirstname")+" "+rs.getString("userlastname"));
      			}
      	%></td>
      <td class="lucida_10_red jq_EditTarget hnd"  alt="<%=rs.getString("target_id")%>" style="text-align: center"><img  class='hnd'  src="<%=sitePathInit%>images/pencil-edit.png"></td>
    </tr>
    
<%		
		 } while (rs.next());
		
	    }	
%>	--%>