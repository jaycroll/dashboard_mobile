<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.sql.ResultSet,com.misc.lib.RolePermission" %> 

<%  
		String sitePathInitMin =request.getContextPath()+"/";

		HttpSession sSub=request.getSession();
		RolePermission rp=new RolePermission();
		rp.gAppProperties(request.getRealPath("/"));
		
		
		Boolean userEditPermission=rp.verifyModule(Integer.parseInt(sSub.getAttribute("roleid").toString()),1,2);
		Boolean userDeletePermission=rp.verifyModule(Integer.parseInt(sSub.getAttribute("roleid").toString()),1,5);
		
		ResultSet rs = (ResultSet) request.getAttribute("usrList");
		while(rs.next()){
%>

<tr>
        <td class="text12_tungsten" width="200"><%=rs.getString("userfirstname")+" "+rs.getString("userlastname")%></td>
        <td class="text12_tungsten" width="200"><%=rs.getString("rolename")%></td>
        <td align="center" class="lucida_10_red" width="100">
        
         <% if(userEditPermission){ %>
        <a href='<%=sitePathInitMin%>User/EditAccess/<%=rs.getString("userid")%>' style='text-decoration:none;'><span class='lucida_10_red'><img  class='hnd'  src="<%=sitePathInitMin%>images/pencil-edit.png"></span></a>
         <% } %>
        </td>
        <td align="center" class="lucida_10_red" width="100">
        
         <% if(userDeletePermission){ %>
        <span class='hnd jq_DeleteAccess' alt='<%=rs.getString("userid")%>'>x</span>
        <% } %>
        </td>
        </tr>
<%		
	}
%>	    