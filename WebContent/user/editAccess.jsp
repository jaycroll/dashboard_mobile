<%@ include file="../sub/header.jsp"%>
<%@ page import="java.util.*,java.sql.ResultSet,com.misc.lib.RolePermission" %> 

<%  
		Map usrDet=new HashMap();
		usrDet = (Map)request.getAttribute("usrDet");
		
		String[] listMonth=(String[]) request.getAttribute("listMonth");
		
		
		RolePermission rpM=new RolePermission();
		rpM.gAppProperties(request.getRealPath("/"));
		HttpSession seM=request.getSession();
		
%>

<style type="text/css">
.text_12_tungsten {font-family:"Lucida Sans Unicode", "Lucida Grande", sans-serif;
	font-size:12px;
	color:#333;
	text-align: center;
}
</style>

<div id="header"><a href="<%=sitePathInit%>"><img src="<%=sitePathInit%>images/ppe-blk.png" width="400" height="42" /></a></div>
<div id="title"><a href="<%=sitePathInit%>"><img src="<%=sitePathInit%>images/lock_small.png" align="absmiddle"/><span class="lucida_12_lead"> Dashboard</span></a></div>

<% if(loggedState){%>
<div id="menu">
	<div id="menu-right" class="lucida_12_tungsten_b"> Hello, <%=sess.getAttribute("username")%> (<%=sess.getAttribute("rolename")%>). <a href="<%=sitePathInit%>User/logout"><span class="lucida_12_red_b">Logout</span></a></div>
</div>
<% }%>


<div id="primary" >
  <% if(!usrDet.isEmpty()){ %>
  <form id="formEditAccess" name="form1" method="post" action="">

  
    <table width="650" border="0" align="center" cellpadding="3" cellspacing="0" style='padding-left:150px;'>
             
          <tr>
             <td width="150" class="lucida_12_tungsten_b">User Form</td>
             <td>
              
            </td>
           </tr>
           <tr>
             <td width="150" class="text12_tungsten_bold">First Name</td>
             <td>
               <input name="userfirstname" type="text" id="textfield" value="<%=usrDet.get("userfirstname") %>" />
               <span class='iError_userfirstname text10_red'></span>
            </td>
           </tr>
             <tr>
             <td width="150" class="text12_tungsten_bold">Last Name</td>
             <td>
               <input name="userlastname" type="text" id="textfield"  value="<%=usrDet.get("userlastname") %>" />
              <span class='iError_userlastname text10_red'></span>
            </td>
           </tr>
           
           <tr>
             <td class="text12_tungsten_bold">Username</td>
             <td><input type="text" name="username" id="textfield2" value="<%=usrDet.get("username") %>" />
             	<span class='iError_username text10_red'></span>
             </td>
           </tr>
           <tr>
             <td class="text12_tungsten_bold">Password</td>
             <td><input type="password" name="password" id="textfield3" />
             	<span class='iError_password text10_red'></span>
             </td>
           </tr>
           <tr>
             <td class="text12_tungsten_bold">Confirm Password</td>
             <td><input type="password" name="password2" id="textfield4" />
             	 <span class='iError_password2 text10_red'></span>
             </td>
           </tr>
           
           <tr>
             <td class="text12_tungsten_bold">Birthdate</td>
             <td>
             <%
             
           
             String str =(String) usrDet.get("birthdate");
             String[] bDate=new String[3];
             if(str==null){
            	 bDate[0]="";
            	 bDate[1]="";
            	 bDate[2]="";
             }else{
             	 bDate = str.split("-");
             }
        	%>
             	<select name="bMonth" class="text12_tungsten" id="select2">
             	<% for(int i=0;i<listMonth.length;i++){ %>
             			<option value="<%=String.format("%02d",i)%>"  <% if(bDate[1].equals(String.format("%02d", i))){ %>selected<% } %> ><%=listMonth[i]%></option>
             	<% } %>	
             	</select>
             	
             	<select name="bDate" class="text12_tungsten" id="select2">
             			<option value=""></option>
             	<% for(int i=1;i<32;i++){ %>
             			<option value="<%=String.format("%02d",i)%>" <% if(bDate[2].equals(String.format("%02d", i))){ %>selected<% } %>><%=i%></option>
             	<% } %>		
             	</select>
             	
             	<select name="bYear" class="text12_tungsten" id="select2">
             			<option value=""></option>
             	<% for(int i=1985;i<2024;i++){ %>
             			<option value="<%=i%>" <% if(bDate[0].equals(String.format("%02d", i))){ %>selected<% } %>><%=i%></option>
             	<% } %>		
             	</select>
             
             	 <span class='iError_password2 text10_red'></span>
             </td>
           </tr>
           <tr>
             <td class="text12_tungsten_bold">Status</td>
             <td>
             <select name="status" class="text12_tungsten" id="select">
               <option  value='active' <% if(usrDet.get("status").equals("active")){ %>selected<% } %>>Active</option>
               <option value='inactive' <% if(usrDet.get("status").equals("inactive")){ %>selected<% } %>>Inactive</option>
               <option value='awol' <% if(usrDet.get("status").equals("awol")){ %>selected<% } %>>AWOL</option>
               <option value='resigned' <% if(usrDet.get("status").equals("resigned")){ %>selected<% } %>>Resigned</option>
               <option value='terminated' <% if(usrDet.get("status").equals("terminated")){ %>selected<% } %>>Terminated</option>
               	
             </select></td>
           </tr>
           <tr>
             <td class="text12_tungsten_bold">Email</td>
             <td><input name="email_address" type="text" id="textfield5"  value='<%=usrDet.get("email_address") %>'/>
             	 <span class='iError_email_address text10_red'></span>
             </td>
           </tr>
           <tr>
             <td class="text12_tungsten_bold">Mobile no</td>
             <td><input type="text" name="mobile" id="textfield6" value='<%=usrDet.get("mobile") %>'/>
             	 <span class='iError_mobile text10_red'></span>	
             </td>
           </tr>
           <tr>
             <td class="text12_tungsten_bold">Role</td>
             <td><select name="roleid" class="text12_tungsten" id="select2">
              	<%  
					ResultSet rs = (ResultSet) request.getAttribute("usrRole"); 
					while(rs.next()){
				%>
				   <option value='<%=rs.getString("roleid") %>' <% if(rs.getString("roleid").equals(usrDet.get("roleid").toString())){ %>selected<% }%> ><%=rs.getString("rolename") %></option>    	
              	<% } %>
             	</select>
             
             </td>
           </tr>
           
            <tr>
             <td class="text12_tungsten_bold">Hiredate</td>
             <td>
             
             <%
             
             String str2 =(String) usrDet.get("hireddate");
             String[] hDate = new String[3];
             if(str2==null){
            	 hDate[0]="";
            	 hDate[1]="";
            	 hDate[2]="";
             }else{
            	 hDate = str2.split("-");
             }
        
        	%>
             	<select name="hMonth" class="text12_tungsten" id="select2">
             	<% for(int i=0;i<listMonth.length;i++){ %>
             			<option value="<%=String.format("%02d",i)%>" <% if(hDate[1].equals(String.format("%02d", i))){ %>selected<% } %>><%=listMonth[i]%></option>
             	<% } %>	
             	</select>
             	
             	<select name="hDate" class="text12_tungsten" id="select2">
             			<option value=""></option>
             	<% for(int i=1;i<32;i++){ %>
             			<option value="<%=String.format("%02d",i)%>" <% if(hDate[2].equals(String.format("%02d", i))){ %>selected<% } %>><%=i%></option>
             	<% } %>		
             	</select>
             	
             	<select name="hYear" class="text12_tungsten" id="select2">
             			<option value=""></option>
             	<% for(int i=2005;i<2024;i++){ %>
             			<option value="<%=i%>" <% if(hDate[0].equals(String.format("%02d", i))){ %>selected<% } %>><%=i%></option>
             	<% } %>		
             	</select>
             
             	 <span class='iError_password2 text10_red'></span>
             </td>
           </tr>
      
      
       <% if(rpM.verifyModule(Integer.parseInt(seM.getAttribute("roleid").toString()),1,2)){ %>    
           <tr>
             <td class="text12_tungsten_bold">&nbsp;</td>
             <td><img src="<%=sitePathInit%>images/edit.png" class='jq_btnEditAccess' border="0" align="absmiddle" /></td>
           </tr>
       <% } %>    
       </table> 
     
       <input type='hidden' name='action' value='processEditAccess'>
       <input type='hidden' name='userid' value='<%=usrDet.get("userid")%>'>
       </form>
      <% }else{ %>
       <div class='divCenter txtCenter red'>User does not exists</div>
      <% } %> 
     
      <br />
	
	
	  
</div><!---------------------end-container2------------>
<script type='text/javascript' src='<%=sitePathInit%>include/custom.jsp'></script>
<script type='text/javascript' src='<%=sitePathInit%>include/custom_notlogged.jsp'></script>

<script type='text/javascript'>
$(document).ready(function($){
	
	
	checkPermission(1,2);
	
<% if(rpM.verifyModule(Integer.parseInt(seM.getAttribute("roleid").toString()),1,2)){ %>   

	$(".jq_btnEditAccess").click(function() {
		
		$.post("<%=sitePathInit%>AUser",$("#formEditAccess").serialize(),
				   function(data){
						if(!data.logged){
								window.location='<%=sitePathInit%>';
						}else{
							
						  if(!data.process){	
							$.each(data, function(index, value) {
								$("."+index).html(value).fadeIn("fast");
							});
						   }else{
							   window.location='<%=sitePathInit%>User';
						   }	
						
						}
						
		}, "json");
		
	});
	
	
<% } %>
				
		
		
});				
</script>
<%@ include file="../sub/footer.jsp"%>