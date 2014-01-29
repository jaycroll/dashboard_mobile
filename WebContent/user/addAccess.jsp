<%@ include file="../sub/header.jsp"%>
<%@ page import="java.util.*,java.sql.ResultSet,com.misc.lib.RolePermission" %> 

<%


RolePermission rp=new RolePermission();
rp.gAppProperties(request.getRealPath("/"));

String[] listMonth=(String[]) request.getAttribute("listMonth");
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
  <form id="formAddAccess" name="form1" method="post" action="">
       <table width="650" border="0" align="center" cellpadding="3" cellspacing="0" style='padding-left:150px;'>
        
        <tr>
             <td width="150" class="lucida_12_tungsten_b">User Form</td>
             <td>
              
            </td>
           </tr>
           <tr>
             <td width="150" class="text12_tungsten_bold">First Name</td>
             <td>
               <input name="userfirstname" type="text" id="textfield"/>
               <span class='iError_userfirstname lucida_10_red'></span>
            </td>
           </tr>
             <tr>
             <td width="150" class="text12_tungsten_bold">Last Name</td>
             <td>
               <input name="userlastname" type="text" id="textfield"  />
              <span class='iError_userlastname lucida_10_red'></span>
            </td>
           </tr>
           <tr>
             <td class="text12_tungsten_bold">Username</td>
             <td><input type="text" name="username" id="textfield2" />
             	<span class='iError_username lucida_10_red'></span>
             </td>
           </tr>
           <tr>
             <td class="text12_tungsten_bold">Password</td>
             <td><input type="password" name="password" id="textfield3" />
             	<span class='iError_password lucida_10_red'></span>
             </td>
           </tr>
           <tr>
             <td class="text12_tungsten_bold">Confirm Password</td>
             <td><input type="password" name="password2" id="textfield4" />
             	 <span class='iError_password2 lucida_10_red'></span>
             </td>
           </tr>
           
           
           <tr>
             <td class="text12_tungsten_bold">Birthdate</td>
             <td>
           
             	<select name="bMonth" class="text12_tungsten" id="select2">
             	<% for(int i=0;i<listMonth.length;i++){ %>
             			<option value="<%=String.format("%02d",i)%>"  ><%=listMonth[i]%></option>
             	<% } %>	
             	</select>
             	
             	<select name="bDate" class="text12_tungsten" id="select2">
             			<option value=""></option>
             	<% for(int i=1;i<32;i++){ %>
             			<option value="<%=String.format("%02d",i)%>" ><%=i%></option>
             	<% } %>		
             	</select>
             	
             	<select name="bYear" class="text12_tungsten" id="select2">
             			<option value=""></option>
             	<% for(int i=1985;i<2024;i++){ %>
             			<option value="<%=i%>" ><%=i%></option>
             	<% } %>		
             	</select>
             
             	 <span class='iError_birthdate lucida_10_red'></span>
             </td>
           </tr>
           <tr>
             <td class="text12_tungsten_bold">Status</td>
             <td><select name="status" class="text12_tungsten" id="select">
               <option value='active'>Active</option>
               <option value='inactive'>Inactive</option>
               <option value='awol'>AWOL</option>
               <option value='resigned'>Resigned</option>
               <option value='terminated'>Terminated</option>
             </select></td>
           </tr>
           <tr>
             <td class="text12_tungsten_bold">Email</td>
             <td><input name="email_address" type="text" id="textfield5" />
             	 <span class='iError_email_address lucida_10_red'></span>
             </td>
           </tr>
           <tr>
             <td class="text12_tungsten_bold">Mobile no</td>
             <td><input type="text" name="mobile" id="textfield6" />
             	 <span class='iError_mobile lucida_10_red'></span>	
             </td>
           </tr>
           <tr>
             <td class="text12_tungsten_bold">Role</td>
             <td><select name="roleid" class="text12_tungsten" id="select2">
              	<%  
					ResultSet rs = (ResultSet) request.getAttribute("usrRole"); 
					while(rs.next()){
				%>
				   <option value='<%=rs.getString("roleid") %>'><%=rs.getString("rolename") %></option>    	
              	<% } %>
             	</select>
             </td>
           </tr>
           
            <tr>
             <td class="text12_tungsten_bold">Hiredate</td>
             <td>
             
             	<select name="hMonth" class="text12_tungsten" id="select2">
             	<% for(int i=0;i<listMonth.length;i++){ %>
             			<option value="<%=String.format("%02d",i)%>" ><%=listMonth[i]%></option>
             	<% } %>	
             	</select>
             	
             	<select name="hDate" class="text12_tungsten" id="select2">
             			<option value=""></option>
             	<% for(int i=1;i<32;i++){ %>
             			<option value="<%=String.format("%02d",i)%>" ><%=i%></option>
             	<% } %>		
             	</select>
             	
             	<select name="hYear" class="text12_tungsten" id="select2">
             			<option value=""></option>
             	<% for(int i=2005;i<2024;i++){ %>
             			<option value="<%=i%>" ><%=i%></option>
             	<% } %>		
             	</select>
             
             	 <span class='iError_hireddate lucida_10_red'></span>
             </td>
           </tr>
            <% if(rp.verifyModule(Integer.parseInt(sess.getAttribute("roleid").toString()),1,1)){ %>
           <tr>
             <td class="text12_tungsten_bold">&nbsp;</td>
             <td><img src="<%=sitePathInit%>images/add_button.png" class='jq_btnAddAccess' border="0" align="absmiddle" /></td>
           </tr>
           <% } %>
       </table> 
       <input type='hidden' name='action' value='processAddAccess'>
       </form>
   
	  
</div><!---------------------end-container2------------>
<script type='text/javascript' src='<%=sitePathInit%>include/custom.jsp'></script>
<script type='text/javascript' src='<%=sitePathInit%>include/custom_notlogged.jsp'></script>

<script type='text/javascript'>
$(document).ready(function($){
	
	checkPermission(1,1);
	
	$(".jq_btnAddAccess").click(function () {
		
		$.post("<%=sitePathInit%>AUser",$("#formAddAccess").serialize(),
				   function(data){
						if(!data.logged){
								window.location='<%=sitePathInit%>';
						}else{
							
						  if(!data.process){	
							$.each(data, function(index, value) {
								$("."+index).html(value).fadeIn("fast");
							});
								//$(".jq_Loading").hide();
								//$(".btnProcess").show();
						   }else{
							   window.location='<%=sitePathInit%>User';
						   }	
						
						}
						
		}, "json");
		setTimeout(function(){
			 window.location.reload();
		 },1000);
		
	});	
});				
</script>
<%@ include file="../sub/footer.jsp"%>