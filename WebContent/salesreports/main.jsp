<%@ include file="../sub/header.jsp"%>
<%@ page import="com.misc.lib.RolePermission" %> 


<% 
	/**IMPORTANT NOTE!**/
	//- ANDROID NATIVE BROWSER DOES NOT SUPPORT PDF VIEWING NOR DOWNLOADING
	//- POSSIBLE WORK AROUND WOULD BE DOWNLOADING A DIFFERENT BROWSER
	//- IT WILL WORK FINE ON IOS' NATIVE BROWSER
	HttpSession sMain=request.getSession();
	RolePermission rpMain=new RolePermission();
	rpMain.gAppProperties(request.getRealPath("/"));
	CustomHelper ch = new CustomHelper();
	String[] months = ch.loadMonths();
	String disabled="";
	String display_1="";
	String display_2="";
	String display_11="";
	String display_21="";
	if(loggedState){
		
		 if(rpMain.verifyModule(Integer.parseInt(sMain.getAttribute("roleid").toString()),7,6)){
			 disabled="";
		     display_11="";
			 display_21="display:none";
			 disabled="";
			
		 }
		 else{
			 disabled="ui-disabled";
			 display_11="display:none";
			 display_21="";
		 }
	}else{
		
		display_1="";
		display_2="display:none";
	}
%>

<div id="salesreports">

	<div data-role="panel" id="mypanel" data-display="overlay" data-position="right">
	    <ul data-role="listview" data-theme="a" data-divider-theme="a" data-count-theme="a">
	    <li data-role="list-divider" style="font-weight:normal;font-size:24px;">Target Menu</li>
	    <li><a href="<%=sitePathInit%>Projection" class="<%=disabled%>" style="font-weight:normal;">Company Projection</a></li>
	    <li><a href="<%=sitePathInit%>Team" class="<%=disabled%>" style="font-weight:normal;">Team Projection</a></li>
	    <li><a href="<%=sitePathInit%>Area" class="<%=disabled%>" style="font-weight:normal;">Area Projection</a></li>
	    <li><a href="<%=sitePathInit%>" style="font-weight:normal;">Home Page</a></li>
	</ul>
	</div>
	<div data-role="header" align="center" style="border:0px;margin-bottom:2%;">
			<img src="<%=sitePathInit%>images/ppe-black.svg"  style="width:325px; "/>		
	</div>
	<div data-role="header" style="border:0px">
	  		<div id="title2">Dashboard</div>
	  		<a href="" class="jq_btnLogout ui-btn ui-btn-a ui-corner-all  ui-btn-block " style="<%=display_1%>; font-size:14px"><span class="">Logout</span></a>
	  		<a href="#mypanel" class="ui-btn ui-btn-a ui-corner-all  ui-btn-block " style="<%=display_1%>; font-size:14px"><span class="" >Menu</span></a>
	</div>
	<div data-role="content" style="<%=display_11%>">
		<div class="ui-grid-solo">	
			    <div data-role="controlgroup" data-type="horizontal">
			    	<form id='formTarget' method="post">
				    <select name="month" id="month" data-mini="true">
						<%
							for(int i = 1;i<13;i++){
								%>
									<option value="<%=i %>"><%=months[i] %>&nbsp;&nbsp;</option>
								<%
							}
						%>
				    </select>
					<select name="year" id="year" data-mini="true">
						<%
							for(int i = 2013;i<2021;i++){
								%>
									<option value="<%=i %>"><%=i %>&nbsp;&nbsp;</option>
								<%
							}
						%>
				    </select>
				    <input type='hidden' name='action' class='jqAction' value='export'> 
				    <input type="button" class="ui-shadow ui-btn ui-corner-all ui-btn-a ui-mini ui-btn-inline "  data-mini="true"onclick="getReport();" value="Submit">
				    </form>
				</div>
		</div>

		<div class="ui-grid-solo" id="content3">
		<script type="text/javascript">
			jQuery(document).ready(function ($){

			
			navigator.sayswho= (function(){
			    var ua= navigator.userAgent, tem, 
			    M= ua.match(/(opera|chrome|safari|firefox|android|msie|trident(?=\/))\/?\s*([\d\.]+)/i) || [];
			    if(/trident/i.test(M[1])){
			        tem=  /\brv[ :]+(\d+(\.\d+)?)/g.exec(ua) || [];
			       
			        return 'IE '+(tem[1] || '');
			    }
			    M= M[2]? [M[1], M[2]]:[navigator.appName, navigator.appVersion, '-?'];
			    if((tem= ua.match(/version\/([\.\d]+)/i))!= null) M[2]= tem[1];
			    return M.join(' ');
			})();
			if(navigator.sayswho.indexOf("Android")!=-1){
				$(".alert").show();
			}
			});
		</script>
		<div class="alert" style="display:none;border:3px #FF0000 solid;padding:10px;background-color:#FFD3D5">
			<div style="text-align:center"><img src="<%=sitePathInit %>images/Simple_Alert.svg"/></div>
			<div style="text-align:justify;">This device's native browser may not display or download the PDF files that you are requesting for.
			Please use a different browser if the PDF files fail to display or download.</div>
		</div>
		<iframe name="ifrm" id="ifrm" src="" frameborder="0" width="100%" height="700">
		Your browser doesn't support iframes.</iframe>
		 <BR><img  src=""  align="left" id="siimage" width="100%" height="100%" /><BR>
		</div>
	</div>
	<div data-role="content" style="<%=display_21%>">
		<div class="ui-grid-solo content" style="text-align:center">
				  <div class=''>Access Denied</div>
				  <br>
				  <div class=''><a  style='color:#3E7836;' href="<%=sitePathInit%>">Back to Main</a></div>
		</div>
	</div>
<script type='text/javascript' src='<%=sitePathInit%>include/login.jsp'></script>
<script type='text/javascript' src='<%=sitePathInit%>include/custom.jsp'></script>
<script type='text/javascript'>

var d = new Date();
d.setDate(d.getDate() - 1);
var currentmonth = d.getMonth()+1;
var currentyear = d.getFullYear();
var currentdiv = "graphs";
defaultheight=700;

$(document).ready(function($){

        <% if(rpMain.verifyModule(Integer.parseInt(sMain.getAttribute("roleid").toString()),7,6)){      %>
                        loadTarget("graphs");
        <% } %>

        $(".jqSection .ibtntabs").click(function () {

                if(checkLogged()){

                        $(".jqSection").find(".ibtntabs").removeClass("active");
                        $(this).addClass("active");

                        $(".jqAction").val($(this).attr("alt"));

                        loadTarget($(this).attr("alt"));

                }else{
                                window.location='<%=sitePathInit%>';
                }
        });



});


function loadTarget(dfile)
{
        currentdiv = dfile;


        if (currentmonth < 10)
                dmonth = "0" + currentmonth;
        else
                dmonth = currentmonth;


        dfilemmyyyy= "graphs" + "-" + dmonth + currentyear + ".pdf";
        if (dfile != 'graphs')
        {
                dfilemmyyyy = dfile + "-"+ dmonth + currentyear + ".html";
        }


        var url = "<%=sitePathInit%>export/" + dfilemmyyyy ;
        var $iframe = $('#ifrm');
        if ( $iframe.length ) {
                 $iframe.attr('src',url);
		if (dfile == 'mtdgraph')
			$iframe.attr('height',100);
		else if (dfile == 'targetvsactual')
			$iframe.attr('height',500);
		else if (dfile == 'actualmonthlysales')
			$iframe.attr('height',560);
		else
			$iframe.attr('height',defaultheight);

        }
        if (dfile != 'mtdgraph')
        {
                $("#siimage").hide();
        }
        else
        {
                url = "<%=sitePathInit%>export/" + dfile + "-"+ dmonth + currentyear + ".gif" ;
                var $iframeimg = $('#siimage');
                if ( $iframeimg.length ) {
                         $('#siimage').attr('src',url);
			  $("#siimage").show();


                }
        }

}

function getReport(){

        currentmonth = $("#month").val();
        currentyear = $("#year").val();

        loadTarget(currentdiv);
}
</script>