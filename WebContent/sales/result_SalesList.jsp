<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.sql.ResultSet,com.misc.lib.CustomHelper,com.misc.lib.RolePermission" %> 


<% 
		String sitePathInitMin =request.getContextPath()+"/";
		HttpSession sSub=request.getSession();
		RolePermission rp=new RolePermission();
		rp.gAppProperties(request.getRealPath("/"));
		
		CustomHelper ch=new CustomHelper();

		ResultSet rs = (ResultSet) request.getAttribute("listSales");
		
		Boolean deleteSales=rp.verifyModule(Integer.parseInt(sSub.getAttribute("roleid").toString()),5,5);
		Boolean editSales=rp.verifyModule(Integer.parseInt(sSub.getAttribute("roleid").toString()),5,2);

		
		if (rs.next()) {  
		
	  do{
	  
%>	  
   <tr>
      <td class="lucida_10_red <% if(deleteSales){ %>jq_DeleteSales hnd<% } %>"  alt="<%=rs.getString("channel_id")%>:<%=rs.getString("revenue_date")%>:<%=rs.getString("revenue_type")%>" style="text-align: center">
      <% if(deleteSales){ %>
      x
      <% } %>
      </td>
      <td style="text-align: center"><%=rs.getString("revenue_date")%></td>
      <td class="text_12_tungsten" style="text-align: center"><%=rs.getString("channel_name")%></td>
      <td style="text-align: center"><%=rs.getString("amount")%></td>
      <td style="text-align: center"><%=rs.getString("revenue_type")%></td>
      <td class="lucida_10_red <% if(editSales){ %>jq_EditSales hnd<%} %>"  alt="<%=rs.getString("channel_id")%>:<%=rs.getString("revenue_date")%>:<%=rs.getString("revenue_type")%>" style="text-align: center">
      <% if(editSales){ %>
      <img  class='hnd' src="<%=sitePathInitMin%>images/pencil-edit.png">
      <% } %>
      </td>
    </tr>

<%		
		 } while (rs.next());
		
	    }	
%>	