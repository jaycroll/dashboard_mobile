<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.sql.ResultSet,com.misc.lib.RolePermission" %>  

<%  

		HttpSession sSub=request.getSession();
		RolePermission rp=new RolePermission();
		rp.gAppProperties(request.getRealPath("/"));


		String sitePathInitMin =request.getContextPath()+"/";
		ResultSet rsRole = (ResultSet) request.getAttribute("roleList"); 
		while(rsRole.next()){
%>  

      <tr>
        <td width="250" class='text12_tungsten'><%=rsRole.getString("rolename")%></td>
        <td width="200" align="center" class="text10_red">
        <% if(rp.verifyModule(Integer.parseInt(sSub.getAttribute("roleid").toString()),2,2)){ %>
        			<a href="javascript:void(0);" class='jq_EditRole'  style='text-decoration:none;' alt='<%=rsRole.getString("roleid")%>'><span class='lucida_10_red'><img  class='hnd'  src="<%=sitePathInitMin%>images/pencil-edit.png"></span></a>
        <% } %>
        </td>
        <td width="200" class="lucida_10_red" align="center">
        <% if(rp.verifyModule(Integer.parseInt(sSub.getAttribute("roleid").toString()),2,5)){ %>
        <a href='javascript:void(0);' style='font-decoration:none;' class="lucida_10_red jq_DeleteRole" alt='<%=rsRole.getString("roleid")%>'><span class='lucida_10_red'>x</span></a>
        <% } %>
        </td>
      </tr>
<% } %>         