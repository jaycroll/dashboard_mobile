<%@ include file="../sub/header.jsp"%>


<%
String disabled="";
String display_1="";
String display_2="";
if(loggedState){
	disabled="";
	display_1="display:none";
	display_2="";
}else{
	disabled="ui-disabled";
	display_1="";
	display_2="display:none";
}%>
<!--  <div id="header" align="center"><div class="header_img"></div></div>
<div id="title" class="lucida_21_black"> DashBoard</div> -->
<script>
$(document).on({
	  ajaxStart: function() { 
	    $.mobile.loading('show');
	  },
	  ajaxStop: function() {
	    $.mobile.loading('hide');
	  }    
	});
function changeImage(img,id){
    // document.getElementById('bigImage').src=img;
    setTimeout(function() {document.getElementById(id).src=img;},1250);
}

</script>
<div id="index" >
		
	  <div data-role="header" align="center" style="border:0px">
			<img src="<%=sitePathInit%>images/ppe-black.svg"  style="width:325px; "/>
			
	  </div>
	  
	  <div data-role="header" style="border:0px">
	  		<div id="title2">Dashboard</div>
	  		<a href="" class="jq_btnLogout ui-btn ui-btn-a ui-corner-all  ui-btn-block ui-mini" style="<%=display_2%>; font-size:14px"><span class="" >Logout</span></a>
	  </div>
				
	  <div class="ui-grid-a" style="margin-top:4%">
		    <div class="ui-block-a"><div class="ui-body ui-body-a" style="text-align:center; border:0px"><a href="<%=sitePathInit%>Projection" class="<%=disabled%>"><img src="<%=sitePathInit%>images/target.svg"></a></div></div>
		    <div class="ui-block-b"><div class="ui-body ui-body-a" style="text-align:center; border:0px"><a href="<%=sitePathInit%>" class="<%=disabled%>"><img src="<%=sitePathInit%>images/velocity.svg"></a></div></div>
		    <div class="ui-block-a"><div class="ui-body ui-body-a" style="text-align:center; border:0px"><a href="<%=sitePathInit%>DomesticProducts" class="<%=disabled%>"><img src="<%=sitePathInit%>images/product_sales.svg"></a></div></div>
		    <div class="ui-block-b"><div class="ui-body ui-body-a" style="text-align:center; border:0px"><a href="<%=sitePathInit%>User" class="<%=disabled%>"><img src="<%=sitePathInit%>images/access.svg"></a></div></div>
	 		
	  </div>
	 
	 <div class="ui-grid-b" style="text-align:center;margin-top:8%;">
			 <div class="ui-block-a" style="text-align:center"></div>
	  		<div class="ui-block-b" style="text-align:center;<%=display_1%>"><a href="#login"data-rel="popup" data-position-to="window" data-transition="pop" id="login2" class="ui-btn ui-btn-b ui-btn-icon-left ui-corner-all ui-icon-lock"> Login</a></div>
	  		<div class="ui-block-c" style="text-align:center"></div>
	  </div>
	  <div data-role="popup" id="login" data-theme="a" class="ui-content" data-overlay-theme="b" style="max-width: 400px;margin-left:-10%">
	    <form method="post" class="signin" id='formLogin' action="AUser">
	    		<a href="#" data-rel="back" class="ui-btn ui-corner-all ui-shadow ui-btn-a ui-icon-delete ui-btn-icon-notext ui-btn-right" style="margin-right:-18px;margin-top:-18px">Close</a>
          		<div class="ui-header ui-bar-a" style="border:0px">Login</div>
          		<div  class="sLoginStatus" align='center'> </div>
                <fieldset class="textbox">
             
                <input id="username" name="username" value="" type="text" autocomplete="on" placeholder="Username">
                
                <input id="password" name="password" value="" type="password" placeholder="Password">
				
				<a class="forgot " href="#">Forgot your password?</a>
				
                <button class="submit button jq_InitLogin ui-btn ui-btn-b ui-icon-lock ui-btn-icon-left" type="button">Login</button>
                </fieldset>
                
                <input  name="action" value="login" type="hidden">
                
          </form>
	</div>

	<!--  variables on this page:
			div id='menu'
			div id="menu-right"
			div id="icons"
	 		div id="login-container"
	 		a href="#login-box" class="login-window"
	 		div id="login-box" class="login-popup"
	 		form method="post" class="signin" id='formLogin' action="AUser"
	 		button class="submit button jq_InitLogin" type="button" value="Login"
	 		input  name="action" value="login" type="hidden"
	 -->	
</div>
<script type='text/javascript' src='<%=sitePathInit%>include/login.jsp'></script>
<%@ include file="../sub/footer.jsp"%>