<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.sql.ResultSet,com.misc.lib.CustomHelper" %> 
<div class="ui-grid-solo" style="margin-top:20px">
Actual Sales and Target
</div>
<div id="chartContainer">

  <script type="text/javascript">
  	var width = (window.innerWidth > 0) ? window.innerWidth : screen.width;	
  	var height = (window.innerHeight > 0) ? window.innerHeight : screen.height;	
    var svg = dimple.newSvg("#chartContainer", width*.8,height);
    var data = [
                {'Projection': 'MTD', 'Amount': <%=request.getAttribute("rsMonthRevenue")%>,'Type':'Actual'},
                {'Projection': 'MTD', 'Amount': <%=request.getAttribute("rsMonthTarget")%>,'Type':'Target'},
                {'Projection': 'YTD', 'Amount': <%=request.getAttribute("rsYearRevenue")%>,'Type':'Actual'},
                {'Projection': 'YTD', 'Amount': <%=request.getAttribute("rsYearTarget")%>,'Type':'Target'},
                {'Projection': 'Total', 'Amount': <%=request.getAttribute("rsYearRevenue")%>,'Type':'Actual'},
                {'Projection': 'Total', 'Amount': <%=request.getAttribute("rsYearTarget")%>,'Type':'Target'},

          

            ];
    var myChart = new dimple.chart(svg, data);
    myChart.setBounds(34.5, 20, width*.8, height-(height*.5));
    var xAxis = myChart.addCategoryAxis("x", ["Projection","Type"]);
    var yAxis = myChart.addMeasureAxis("y","Amount");
    xAxis.addOrderRule(["MTD","YTD","Total"]);
    
    
    myChart.addSeries("Type", dimple.plot.bar);

    myChart.addLegend(65, 10, width*.8-(width*0.08), 20, "right");
    myChart.draw();
    if (yAxis.titleShape !== null && yAxis.titleShape !== undefined) {
    	yAxis.titleShape.remove();
    	}
  </script>
</div>

