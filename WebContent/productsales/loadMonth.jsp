<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.sql.ResultSet,com.misc.lib.CustomHelper" %> 
		
<%  
		String sitePathInitMin =request.getContextPath()+"/";
		CustomHelper ch=new CustomHelper();
		int[] channels = (int[]) request.getAttribute("channels"); 
		String[][][] productArray2 = (String[][][]) request.getAttribute("productArray2");
		
%>				

<script>


	function createMonthlyGraph(area,mS,mT){
		var area = area;
		var ctx = $("#"+area).get(0).getContext("2d");
		var myNewChart = new Chart(ctx); 
		var areas = new Array();
		var data = {
				labels : ["J","F","M","A","M","J","J","A","S","O","N","D"],
				datasets : [
					{
						fillColor : "rgba(220,220,220,0.5)",
						strokeColor : "rgba(220,220,220,1)",
						data : mS
					},
					{
						fillColor : "rgba(151,187,205,0.5)",
						strokeColor : "rgba(151,187,205,1)",
						data: mT
					}
				]
			};
		var lmt = Math.max.apply(Math, mT); 
		var lms = Math.max.apply(Math,mS);
		var largest = 0;
		if(lmt<lms){
			 largest = lms;
			$("#test").append(lms);
		}	
		else if(lmt>lms){
			largest = lmt;
			
			$("#test").append(lmt);
		}
		else{
			$("#test").append("100000");
		}
		var options={
				scaleOverride:true,				
				scaleSteps: 10,
				scaleStepWidth: Math.ceil(largest/10),
				scaleStartValue: 0,
				animation : false
		};
		var myNewChart = new Chart(ctx).Bar(data);
	}			
	

</script>
<div id="test"></div>
<div id="test2"></div>
<%
for(int i=0; i<channels[0];i++){
	if(productArray2[i][0][0] != null){
	%>
		<script>
		var monthlySales = new Array();
		var monthlyTarget = new Array();
		for(var x = 1;x<<%=channels[0]%>;x++ ){
			
		}
		</script>
		<br><br>
		<div style="border: 1px solid #000000;width:700px;"><div><%=productArray2[i][3][0] %></div><canvas id="<%=productArray2[i][0][0] %>" width="600" height="300" ></canvas></div>
		<br><br>
		
	<%
		for(int j = 0; j<productArray2[i][1].length;j++){
			if(productArray2[i][1][j]!=null){
	%>
					<script>
						monthlySales[<%=j%>-1] = <%=productArray2[i][1][j]%>;
					</script>
				<%
			}

		}
		for(int j = 0; j<productArray2[i][2].length;j++){
			if(productArray2[i][2][j]!=null){
	%>
					<script>
						monthlyTarget[<%=j%>-1] = <%=productArray2[i][2][j]%>;
					</script>
				<%
			}

		}
	%>
		<script>createMonthlyGraph(<%=productArray2[i][0][0] %>,monthlySales,monthlyTarget);</script>
	<%
	}
}
%>