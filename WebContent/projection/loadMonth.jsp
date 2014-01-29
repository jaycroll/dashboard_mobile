<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.sql.ResultSet,com.misc.lib.CustomHelper" %> 
		
<%  
		String sitePathInitMin =request.getContextPath()+"/";
		CustomHelper ch=new CustomHelper();
		float[] iActualSales = (float[]) request.getAttribute("iActualSales");
		float[] iActualTarget = (float[]) request.getAttribute("iActualTarget");
		String[] strMonths=ch.loadShortMonths();
		
%>	
<div class="ui-grid-solo" style="margin-top:20px">
Actual Sales and Target
</div>
<div id="chartContainer">

  <script type="text/javascript">
  	var width = (window.innerWidth > 0) ? window.innerWidth : screen.width;	
  	var height = (window.innerHeight > 0) ? window.innerHeight : screen.height;	
    var svg = dimple.newSvg("#chartContainer", width*.8,height);
    var TargetM = new Array();
    var ActualM = new Array();
    <% for(int a=1;a<13;a++){ %>
    TargetM[<%=a%>-1]='<%=iActualTarget[a]%>';
    ActualM[<%=a%>-1]='<%=iActualSales[a]%>';
    <% } %>
    var data = [

				<% for(int a=1;a<12;a++){ %>		
				{'Month':'<%=strMonths[a]%>','Type':'Target','Amount':TargetM[<%=a%>]},
				{'Month':'<%=strMonths[a]%>','Type':'Actual','Amount':ActualM[<%=a%>]},				
				<% } %>	
				{'Month':'<%=strMonths[12]%>','Type':'Target','Amount':TargetM[12]},
				{'Month':'<%=strMonths[12]%>','Type':'Actual','Amount':ActualM[12]}			
            ];
    var myChart = new dimple.chart(svg, data);
    myChart.setBounds(34.5, 20, width*.8, height-(height*.5));
    var xAxis = myChart.addCategoryAxis("x", ["Month","Type"]);
    var yAxis = myChart.addMeasureAxis("y","Amount");
    xAxis.addOrderRule(["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"]);
    xAxis.addOrderRule("Type");
    
    myChart.addSeries("Type", dimple.plot.bar);

    myChart.addLegend(65, 10, width*.8-(width*0.08), 20, "right");
    myChart.draw();
    if (yAxis.titleShape !== null && yAxis.titleShape !== undefined) {
    	yAxis.titleShape.remove();
    	}

  </script>
</div>
<script>

// function createMTDGraph(area,am,tm){
// 	var area = area;
// 	var ctx = $("#"+area).get(0).getContext("2d");
// 	var myNewChart = new Chart(ctx);
// 	var areas = new Array();
// 	var months = new Array();
<%-- 	<% for(int a=1;a<13;a++){ %>			 --%>
<%-- 		months[<%=a%>-1]="<%=strMonths[a]%>"; --%>
<%-- 	<% } %>	 --%>
// 	var data = {
// 			labels : months,
// 			datasets : [

// 				{
// 					fillColor : "rgba(41,171,226,1)",
// 					strokeColor : "rgba(41,171,226,1)",
// 					data : am
// 				},
// 				{
// 					fillColor : "rgba(25,131,43,1)",
// 					strokeColor : "rgba(25,131,43,1)",
// 					data : tm
// 				}
// 			]
			
// 		};
// 	var largest = Math.max.apply(Math, tm); 
// 	var options={
// 			scaleOverride:true,				
// 			scaleSteps: 10,
// 			scaleStepWidth: Math.ceil(largest/10),
// 			scaleStartValue: 0
// 	};
// 	var thisNewChart = new Chart(ctx).Bar(data,options);
// }			
</script>
<!-- <div style="border: 1px solid #000000;width:700px;"> -->
<!-- 		<h2>Actual Sales and Target</h2> -->
<!-- 		<canvas  id="mtd" width="600" height="300" ></canvas> -->
<!-- 				<div><div style="background-color:#000fff;width:10px;height:10px"></div>Target</div> -->
<!-- 				<div><div style="background-color:#0adcef;width:10px;height:10px"></div>Actual</div>	 -->
<script>
// 			var ActualM = new Array();
// 			var TargetM = new Array();
<%-- 			<% for(int a=1;a<13;a++){ %> --%>
<%-- 				TargetM[<%=a%>-1]='<%=iActualTarget[a]%>'; --%>
<%-- 				ActualM[<%=a%>-1]='<%=iActualSales[a]%>'; --%>
<%-- 			<% } %> --%>
// 			createMTDGraph("mtd",ActualM,TargetM);
</script>		
<!-- </div>				 -->

