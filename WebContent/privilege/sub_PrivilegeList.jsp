<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.sql.ResultSet,com.misc.lib.RolePermission" %> 
<% 
	String sitePathInitSub =request.getContextPath()+"/";

	HttpSession se=request.getSession();
	RolePermission rpM=new RolePermission();
	rpM.gAppProperties(request.getRealPath("/"));
%>


<form action="" method="post" name="form1" id="formPrivilegeList">
    <table width="650" cellspacing="0" cellpadding="5" border="0" align="center" class='lucida_12_tungsten'>
      <tbody><tr>
        <td width="250" class="text12_tungsten">
         <% if(rpM.verifyModule(Integer.parseInt(se.getAttribute("roleid").toString()),3,1)){ %>
        <img width="28" height="26" src="<%=sitePathInitSub%>images/add.png" class='hnd jq_AddPrivilege'> <span class='lucida_12_tungsten_b'>Privileges</span>
        <% } %>
        </td>
        <td width="200" align="center" class="text10_red">&nbsp;</td>
        <td width="200" align="center" class="text10_red">&nbsp;</td>
        </tr>
      <tr class="text12_tungsten_bold">
        <td>Privileges</td>
        <td align="center">Bit</td>
        <td align="center">&nbsp;</td>
        </tr>
     </tbody></table>

      
<table width="650" cellspacing="0" cellpadding="5" border="0" align="center" class='tblPrivilegelist'>  
  <%@ include file="../privilege/result_PrivilegeList.jsp" %>
</table>
     <input type='hidden' name='action' value='subLoadPrivilege'>
</form>

