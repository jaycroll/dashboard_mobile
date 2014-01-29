<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.sql.ResultSet,com.misc.lib.CustomHelper" %> 

		
<%  
		String sitePathInitMin =request.getContextPath()+"/";
		CustomHelper ch=new CustomHelper();
		float[] iActualSales = (float[]) request.getAttribute("iActualSales");
		float[] iActualTarget = (float[]) request.getAttribute("iActualTarget");
		String[] strMonths=ch.loadShortMonths();
		
		Map usrDet=new HashMap();
		usrDet = (Map)request.getAttribute("usrDet");
		
		
%>		
  <% if(!usrDet.isEmpty()){ %>

  	<div>
  			<div class='grayTitle'><%=usrDet.get("userfirstname") %> <%=usrDet.get("userlastname") %></div>
  			<div class='gry fnt13'>Born On <%=ch.aDate((String) usrDet.get("birthdate"),"1")%></div>
  			
  			
  			<div class='gry fnt13' style='margin-top:10px;'><%=usrDet.get("mobile") %></div>
  			<div class='gry fnt13'>Hired last <%=ch.aDate((String) usrDet.get("hireddate"),"1")%></div>
  			<div class='gry fnt13'>Handling  <%=request.getAttribute("agent_area")%></div>
	</div>
	<div id="chartContainer2">

  <script type="text/javascript">
  	var width = (window.innerWidth > 0) ? window.innerWidth : screen.width;	
  	var height = (window.innerHeight > 0) ? window.innerHeight : screen.height;	
    var svg = dimple.newSvg("#chartContainer2", width*.8,height);
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

  <% } %>	






