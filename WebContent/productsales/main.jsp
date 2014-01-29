<%@ include file="../sub/header.jsp"%>

<%@ page import="com.misc.lib.RolePermission" %> 
<%@ page import="com.model.lib.*" %>
<%@ page import="java.sql.*" %>
<% 
	HttpSession sMain=request.getSession();
	RolePermission rpMain=new RolePermission();
	rpMain.gAppProperties(request.getRealPath("/"));
	String[][] productArray = (String[][]) request.getAttribute("productArray");
	int[] arrayLength = (int[]) request.getAttribute("channels");
	String[] loc = (String[]) request.getAttribute("loc");
	String[] classCss = (String[]) request.getAttribute("classCSS");
%>
<div id="header" align="center"><img src="<%=sitePathInit%>images/ppe-blk.png"/></div>
<div id="title">
	<a href="<%=sitePathInit %>" class="lucida_21_black"><img src="<%=sitePathInit%>images/productsales_small.png" align="absmiddle"/>Dashboard</a>
	
</div>
<% if(loggedState){%>
<div id="menu">
<div id="menu-right" class="lucida_12_tungsten_b"> Hello, <%=sess.getAttribute("username")%> (<%=sess.getAttribute("rolename")%>). <a href="<%=sitePathInit%>User/logout"><span class="lucida_12_red_b">Logout</span></a></div>
<div id="menu-left">
	<ul class="map">
	<li id="domestic"><a href="<%=sitePathInit%>DomesticProducts" title="menu1" class="<%=classCss[0]%>"><span class="displace"></span></a></li>
	<li id="international"><a href="<%=sitePathInit%>InternationalProducts" title="menu2" class="<%=classCss[1]%>"><span class="displace"></span></a></li>

    </ul>
</div>
</div>



<div id="submenu-right">
	<ul class="tab jqSection">
		<li><a href="<%=sitePathInit+"/"+loc[0]%>" class="ibtntabs active" alt='loadDefault'>Snapshot</a></li>
		<li><a href="javascript:void(0);" class="ibtntabs" alt='loadMonth'>Monthly</a></li>
		
	</ul>
</div>


<br><br>
<script>


	function createYTDGraph(area,ay,ty){
		var area = area;
		var ctx = $("#"+area).get(0).getContext("2d");
		var myNewChart = new Chart(ctx);
		var areas = new Array();
		var data = {
				labels : ["MTD","YTD","Total"],
				datasets : [
					{
						fillColor : "rgba(220,220,220,0.5)",
						strokeColor : "rgba(220,220,220,1)",
						data : ay
					},
					{
						fillColor : "rgba(151,187,205,0.5)",
						strokeColor : "rgba(151,187,205,1)",
						data : ty
					}
				]
			};
		var lmt = Math.max.apply(Math, ty); 
		var lms = Math.max.apply(Math,ay);
		var largest = 0;
		if(lmt<lms){
			 largest = lms;
		}
		else{
			largest = lmt;
		}
		var options={
				scaleOverride:true,				
				scaleSteps: 10,
				scaleStepWidth: Math.ceil(largest/10),
				scaleStartValue: 0
		};
		var myNewChart = new Chart(ctx).Bar(data,options);
	}			


	
</script>

<form id='formTarget'>
<div id="content">

<% if(rpMain.verifyModule(Integer.parseInt(sMain.getAttribute("roleid").toString()),7,6)){	%>  
		<% 
			for(int i = 0;i<arrayLength[0];i++){
			%>
				<div><%=productArray[i][3]%></div>
				<div style="border: 1px solid #000000;width:700px;">
					<canvas id="<%=productArray[i][0]%>" width="600" height="300" ></canvas>
				</div>
				<script>
					var actual = new Array();
					var target = new Array();
					
					actual.push("<%=productArray[i][2]%>");
					actual.push("<%=productArray[i][1]%>");
					actual.push("<%=productArray[i][1]%>");
					target.push("<%=productArray[i][5]%>");
					target.push("<%=productArray[i][4]%>");
					target.push("<%=productArray[i][4]%>");
					<%//System.out.println(productArray[i][5]);%>
					createYTDGraph(<%=productArray[i][0]%>,actual,target);
				</script>
			<%
			}
		%>
<% }else{ %>
		
		<div style='clear:both'>&nbsp;</div>
		<div  class='divCenter txtCenter'>
			  <div class='access_denied'>Access Denied</div>
			  <div class='clr'>&nbsp;</div>
			  <div class='clr'><a  style='color:#3E7836;' href="<%=sitePathInit%>">Back to Main</a></div>
              <div class='clr'>&nbsp;</div>
	    </div>
<% } %>
<%--include file="../target/loadDefault.jsp" --%>
		
</div>
<input type='hidden' name='action' class='jqAction' value='loadDefault'> 
</form>
<script>
$(".jqSection .ibtntabs").click(function () {
	
	if(checkLogged()){
			
		$(".jqSection").find(".ibtntabs").removeClass("active");	
		$(this).addClass("active");
		
		$(".jqAction").val($(this).attr("alt"));
		
		if(checkPermission(7,6)){
			mainModule=true;
		}else{
			mainModule=false;
		}
		
  		if(mainModule){		
  			
  		}
  		
  		$("#content").empty();
			$.post("<%=sitePathInit%><%=classCss[2]%>",{action:$(this).attr("alt")},
					function(data){
							$("#content").append(data);
		});
  	}else{
			window.location='<%=sitePathInit%>';
	}	
});


</script>


<% }%>
