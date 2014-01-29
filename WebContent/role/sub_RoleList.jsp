<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="java.util.*,java.sql.ResultSet,com.misc.lib.RolePermission" %> 
<% 

	HttpSession se=request.getSession();
	RolePermission rpM=new RolePermission();
	
	String sitePathInitSub =request.getContextPath()+"/";
	rpM.gAppProperties(request.getRealPath("/"));
%> 

  <form id="formRoleList" name="form1" method="post" action="">
    <table width="650" border="0" align="center" cellpadding="5" cellspacing="0">
    
      <tr>
        <td class="text12_tungsten"> 
        
        <% if(rpM.verifyModule(Integer.parseInt(se.getAttribute("roleid").toString()),2,1)){	%>
         <div id='basic-modal'>
         	<a href='#' class='basic text12_tungsten'><img src="<%=sitePathInitSub%>images/add.png" width="28" height="26" border="0" align="absmiddle" class='jq_AddRole' /></a> <span class='lucida_12_tungsten_b'>Role</span>
         </div> 
        <% } %> 
         </td>
        <td align="center" class="text10_red">&nbsp;</td>
        <td align="center" class="text10_red">&nbsp;</td>
      </tr>
      
      </table>
      
      
<% if(rpM.verifyModule(Integer.parseInt(se.getAttribute("roleid").toString()),2,6)){	%>       
<table  width="650" border="0" align="center" cellpadding="5" cellspacing="0" class='tblRolelist'>      
  <%@ include file="result_RoleList.jsp" %>
</table>
<% } %>
      

    </table>
 
     <input type='hidden' name='action' value='subLoadRole'>
</form>

