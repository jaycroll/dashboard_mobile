<%@ include file="../sub/header.jsp"%>


<%@ page import="com.misc.lib.RolePermission" %> 

<% 
	HttpSession sMain=request.getSession();
	RolePermission rpMain=new RolePermission();
	rpMain.gAppProperties(request.getRealPath("/"));
	
	Boolean searchPriv=rpMain.verifyModule(Integer.parseInt(sMain.getAttribute("roleid").toString()),5,7);
%> 


	<link rel="stylesheet" href="<%=sitePathInit%>css/jquery.ui.all.css">
	<link rel="stylesheet" href="<%=sitePathInit%>css/datepicker.css">

	<script type='text/javascript' src='<%=sitePathInit%>js/jquery.ui.core.js'></script>
	<script type='text/javascript' src='<%=sitePathInit%>js/jquery.ui.datepicker.js'></script>

<script>
		$(function() {
			$( ".datepicker" ).datepicker();
		});
</script>

<style type="text/css">
.text_12_tungsten {font-family:"Lucida Sans Unicode", "Lucida Grande", sans-serif;
	font-size:12px;
	color:#333;
	text-align: center;
}
</style>


<div id="header" align="center"><img src="<%=sitePathInit%>images/ppe-blk.png"/></div>
<div id="title">

<a href="<%=sitePathInit%>" class="lucida_21_black"><img src="<%=sitePathInit%>images/lock_small.png" align="absmiddle"/>Dashboard</a>

</div>
<% if(loggedState){%>
<div id="menu">
	<div id="menu-right" class="lucida_12_tungsten_b"> Hello, <%=sess.getAttribute("username")%> (<%=sess.getAttribute("rolename")%>). <a href="<%=sitePathInit%>User/logout"><span class="lucida_12_red_b">Logout</span></a></div>
</div>
<div id="submenu">

<div>
<ul class="tabs jq_UserTabs access">
            <li id='access1' ><a href="<%=sitePathInit%>User"  class=' ibtntabs' title='User Management'></a></li>
            <li id='access2' ><a href="<%=sitePathInit%>Roles"  class='ibtntabs' title='Role Management'></a></li>
            <li id='access3' ><a href="<%=sitePathInit%>Privilege"  class='ibtntabs' title='Privileges Management'></a></li>
     	 	<li id='access4' ><a href="<%=sitePathInit%>Target"  class='ibtntabs' title='Target Management'></a></li>
     	 	<li id='access7' ><a href="<%=sitePathInit%>ProductTarget" alt='loadPrivilege' class='ibtntabs' title='Product Target Management'></a></li>
     	 	<li id='access5' ><a href="<%=sitePathInit%>Channel"  class='ibtntabs' title='Channel Management'></a></li>
     	 	<li id='access6' ><a href="<%=sitePathInit%>Sales"  class='ibtntabs active' title='Sales Management'></a></li>
     	 </ul>

</div>
<% }%>
<form id='formSalesList'>


<div id="primary">

<% if(rpMain.verifyModule(Integer.parseInt(sMain.getAttribute("roleid").toString()),5,0)){	%>  
 <table width="900" border="0" align="center" cellpadding="5" cellspacing="0" bordercolor="#cccccc">
    <tr class="text12_tungsten_bold">
      <td width="75" style="text-align: center"> </td>
      <td style="text-align: center">Revenue Date</td>
      <td style="text-align: center">Channel</td>
      <td style="text-align: center">Amount</td>
      <td style="text-align: center">Revenue Type</td>
      <td width="75" style="text-align: center">&nbsp;</td>
    </tr>
 
     <tr>
      <td class="text10_red" style="text-align: center">
      
      <% if(rpMain.verifyModule(Integer.parseInt(sMain.getAttribute("roleid").toString()),5,1)){%>  
      		<img src="<%=sitePathInit%>images/add.png" class='jq_AddSales'  width="28" height="28" />
      <% } %>
      </td>
      <td style="text-align: center">
      
      <% if(searchPriv){ %>
      	<input name="revenue_date" type="text" id="textfield" class='datepicker jqSalesInput' size="20" />
      	<img src="<%=sitePathInit%>images/calendar.png" width="28" height="28" hspace="5" align="absmiddle" />
      <% } %>
      
        </td>
      <td style="text-align: center">
    
      <% if(searchPriv){ %>
      <select id="select" class="text_12_tungsten jqSalesInput" name="channel_id">
      	<option value=""></option>
       	<%  
	       ResultSet listChannel = (ResultSet) request.getAttribute("listChannel");
			
			if (listChannel.next()) {  
			
		  	do{
			%>
	        <option value='<%=listChannel.getString("channel_id")%>'><%=listChannel.getString("channel_name")%></option>
	        <%		
			 	} while (listChannel.next());
		    	}	
			%>      
      </select>
      <% } %>
      </td>
      <td style="text-align: center">
     <% if(searchPriv){ %>
      <input name="amount" type="text" id="textfield2" class='jqSalesInput' size="20" />
     <% } %> 
      </td>
      <td style="text-align: center">
      <% if(searchPriv){ %>
      	<select id="select" class="text_12_tungsten jqSalesInput" name="revenue_type">
      		<option value=""></option>
       		<option value='Sales'>Sales</option>
       		<option value='LOP'>LOP</option>
       		<option value='Refund'>Refund</option>
       </select>
       <% } %>
      </td>
      <td class="text10_red" style="text-align: center">
      	
      </td>
    </tr>

    
   <tbody class='tblSalesList'> 
   
   <%-- <tr>
      <td class="lucida_10_red" style="text-align: center">x</td>
      <td style="text-align: center">02/14/2012</td>
      <td class="text_12_tungsten" style="text-align: center">Domestic Web</td>
      <td style="text-align: center">&nbsp;</td>
      <td style="text-align: center">LOP</td>
      <td class="lucida_10_red" style="text-align: center">edit</td>
    </tr>--%>
    
   <% if(rpMain.verifyModule(Integer.parseInt(sMain.getAttribute("roleid").toString()),5,6)){	%>  
       		<%@ include file="../sales/result_SalesList.jsp"%>
       		
   <% }else{ %>    		
      <tr><td colspan='6'>
		<div class='divCenter txtCenter'>
							   <div class='access_denied'>Access Denied</div>
							   <div class='clr'>&nbsp;</div>
							  <div class='clr'><a  style='color:#3E7836;' href="<%=sitePathInit%>">Back to Main</a></div>
	                           <div class='clr'>&nbsp;</div>
	                           </div>
	</td></tr>
 <% } %>	
 
   </tbody></table>
   
     <% }else{ %>
	
		<div class='divCenter txtCenter'>
			  <div class='access_denied'>Access Denied</div>
			  <div class='clr'>&nbsp;</div>
			  <div class='clr'><a  style='color:#3E7836;' href="<%=sitePathInit%>">Back to Main</a></div>
              <div class='clr'>&nbsp;</div>
	    </div>
	                           
<% } %>  		
  
  
  
  <!---------primary-right------------->
</div>

<input type='hidden' name='action'  value='loadSales'> 
</form>

<script type='text/javascript'>

$(document).ready(function($){
	
	
	
$(".jq_AddSales").click(function () {
		
		if(checkLogged()){
				
			if(checkPermission(5,1)){
				mainModule=true;
			}else{
				mainModule=false;
			}
			
			if(mainModule){
				$.post("<%=sitePathInit%>ASales",{action:'initAddSales'},
						   function(data){
									$('.basic-container').html(data);
									initModal();
				});
		    }	
			
		}else{
			window.location='<%=sitePathInit%>';	
		}
});
	
	



$(".jq_EditSales").click(function () {
	if(checkLogged()){
		
		
		var mainModule=false;
		if(checkPermission(5,2)){
			mainModule=true;
		}else{
			mainModule=false;
		}
		
		if(mainModule){
		
		$.post("<%=sitePathInit%>ASales",{action:'initEditSales',salesinfo:$(this).attr("alt")},
				   function(data){
							$('.basic-container').html(data);
							initModal();
		});
		
		}
	}else{
		window.location='<%=sitePathInit%>';	
	}
});	






$(".jqSalesInput").change(function(event) {
	
	
	var mainModule=false;
	if(checkPermission(5,7)){
		mainModule=true;
	}else{
		mainModule=false;
	}
	
	if(mainModule){
	
	loadSales();
	
	}
});


$(".jqSalesInput").keyup(function(event) {
	
	
	var mainModule=false;
	if(checkPermission(5,7)){
		mainModule=true;
	}else{
		mainModule=false;
	}
	
	if(mainModule){
		loadSales();
	}
});


$(".jq_DeleteSales").click(function () {
	
	if(checkLogged()){
			
		
		var mainModule=false;
		if(checkPermission(5,5)){
			mainModule=true;
		}else{
			mainModule=false;
		}
		
		if(mainModule){
	
			$.post("<%=sitePathInit%>ASales",{action:'initDeleteSales',salesinfo:$(this).attr("alt")},
					   function(data){
								$('.basic-container').html(data);
								initModal();
			});
			
		}	
	}else{
		window.location='<%=sitePathInit%>';	
	}
});	

	
});


function loadSales(){
	$.post("<%=sitePathInit%>ASales",$("#formSalesList").serialize(),
			   function(data){
				$(".tblSalesList").html(data);	
	});
}
</script>

