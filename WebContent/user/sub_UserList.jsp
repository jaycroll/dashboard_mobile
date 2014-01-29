<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.sql.ResultSet,com.misc.lib.RolePermission" %> 
<% 

	HttpSession se=request.getSession();
	RolePermission rpM=new RolePermission();
	
	String sitePathInitSub =request.getContextPath()+"/";
	rpM.gAppProperties(request.getRealPath("/"));
%>    


<% if(rpM.verifyModule(Integer.parseInt(se.getAttribute("roleid").toString()),1,7)){	%>
<form id="formUserList" name="form1" method="post" action="">
    <table  border="0" align="center" cellpadding="5" cellspacing="0">
      
      <% if(rpM.verifyModule(Integer.parseInt(se.getAttribute("roleid").toString()),1,1)){ %>
      <tr>
        <td colspan="4" class="text12_tungsten"><a href="<%=sitePathInitSub%>User/AddAccess"><img src="<%=sitePathInitSub%>images/add-user.png" width="28" height="26" border="0" align="absmiddle" /></a> <span class='lucida_12_tungsten_b'>User</span></td>
        </tr>
      <% } %>  
      <tr>
        <td width="200" class="text12_tungsten">
        <select name="userid" id="select" class='iUserSelect'>
         <option value=''></option>
<%  
		ResultSet rsUserList = (ResultSet) request.getAttribute("usrListSpec"); 
		while(rsUserList.next()){
%>
          <option value='<%=rsUserList.getString("userid")%>'><%=rsUserList.getString("userfirstname")%> <%=rsUserList.getString("userlastname")%></option>
<% } %>          
        </select>
        </td>
        <td width="200" class="text12_tungsten"><span class="text10_red">
          <select name="roleid" class="text12_tungsten iUserSelect" id="select2">
           <option value=""></option>
<%  
		ResultSet rsRole = (ResultSet) request.getAttribute("urlRole"); 
		while(rsRole.next()){
%>
            <option value="<%=rsRole.getString("roleid")%>"><%=rsRole.getString("rolename")%></option>
<% } %>            
          </select>
          
          
        </span></td>
        <td width="100" align="center" class="text10_red">&nbsp;</td>
        <td width="100" align="center" class="text10_red">&nbsp;</td>
      </tr>
 </table>
 
 <input type='hidden' name='action' value='subLoadUser'>
</form>
<% }  %>  
<% if(rpM.verifyModule(Integer.parseInt(se.getAttribute("roleid").toString()),1,6)){	%>     
<table  border="0" align="center" cellpadding="5" cellspacing="0" class='tblUserlist'>      
  <%@ include file="../user/result_UserList.jsp" %>
</table>
<% } %>


