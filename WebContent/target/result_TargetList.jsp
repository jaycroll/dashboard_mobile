<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.sql.ResultSet,com.misc.lib.CustomHelper,com.misc.lib.RolePermission" %> 

<%  
		String sitePathInitMin =request.getContextPath()+"/";
		CustomHelper ch=new CustomHelper();
		
		
		HttpSession sSub=request.getSession();
		RolePermission rp=new RolePermission();
		rp.gAppProperties(request.getRealPath("/"));
		
		
		Boolean deleteTarget=rp.verifyModule(Integer.parseInt(sSub.getAttribute("roleid").toString()),4,5);
		Boolean editTarget=rp.verifyModule(Integer.parseInt(sSub.getAttribute("roleid").toString()),4,2);
		
		ResultSet rs = (ResultSet) request.getAttribute("listTarget");
		
		if (rs.next()) {  
		
	  do{
%>
	<tr>
      <td class="lucida_10_red  <% if(deleteTarget){ %>jq_DeleteTarget hnd <% } %>"  alt="<%=rs.getString("target_id")%>" style="text-align: center"><% if(deleteTarget){ %>x<% } %></td>
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
      <td class="lucida_10_red <% if(editTarget){ %>jq_EditTarget hnd<% } %>"  alt="<%=rs.getString("target_id")%>" style="text-align: center">
      <% if(editTarget){ %>
      <img  class='hnd'  src="<%=sitePathInitMin%>images/pencil-edit.png">
      <%  } %>
      </td>
    </tr>
    
<%		
		 } while (rs.next());
		
	    }	
%>	