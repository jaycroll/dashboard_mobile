<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.sql.ResultSet,com.misc.lib.CustomHelper" %> 
		
<%  
		String sitePathInitMin =request.getContextPath()+"/";
		CustomHelper ch=new CustomHelper();
		float[] iActualSales = (float[]) request.getAttribute("iActualSales");
		float[] iActualTarget = (float[]) request.getAttribute("iActualTarget");
		String[] strMonths=ch.loadInitMonths();
		
%>				
<table class="pie line area bar" style='display:none;'>
	<caption>Actual Sales and Target Per Month</caption>
	<thead>
		<tr>
			<td></td>
			
	<% for(int a=1;a<13;a++){ %>			
			<th><%=strMonths[a]%></th>
	<% } %>		
			
		</tr>
	</thead>
	<tbody>
		<tr>
			<th>Target</th>
			<% for(int a=1;a<13;a++){ %>
				<td><%=iActualTarget[a]%></td>
			<% } %>
		</tr>
		
		
			<tr>
			<th>Actual</th>
			<% for(int a=1;a<13;a++){ %>
				<td><%=iActualSales[a]%></td>
			<% } %>
			
		</tr>
	</tbody>
</table>
