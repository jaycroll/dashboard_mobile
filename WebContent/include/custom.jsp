<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<% 
	String sitePathInitJs =request.getContextPath()+"/";
%>  

$(document).ready(function($){

		$(".jq_ModalClose").click(function () {
				$.modal.close();
		});
		
		
    $('a.login-window').click(function() {
		
                //Getting the variable's value from a link 
		var loginBox = $(this).attr('href');

		//Fade in the Popup
		$(loginBox).fadeIn(300);
		
		//Set the center alignment padding + border see css style
		var popMargTop = ($(loginBox).height() + 24) / 2; 
		var popMargLeft = ($(loginBox).width() + 24) / 2; 
		
		$(loginBox).css({ 
			'margin-top' : -popMargTop,
			'margin-left' : -popMargLeft
		});
		
		// Add the mask to body
		$('body').append('<div id="mask"></div>');
		$('#mask').fadeIn(300);
		
		return false;
	});
	
	// When clicking on the button close or the mask layer the popup closed
	$('a.close, #mask').click( function() { 
	  $('#mask , .login-popup').fadeOut(300 , function() {
		$('#mask').remove();  
	}); 
	return false;
	});

});


	function loadingInitID(divID){ 
		$.post("<%=sitePathInitJs%>Misc",{action:'initModal'},
				   function(data){
						$("#"+divID).html(data);	
		});
	} 
	
	function loadingInit(divID){ 
		$.post("<%=sitePathInitJs%>Misc",{action:'initModal'},
				   function(data){
						$("."+divID).html(data);	
		});
	} 
	
	function initModal(){
			$('#basic-modal-content').modal();
			$(".simplemodal-wrap").css('overflow','visible');
			$('#simplemodal-container').css('height',20+$(".basic-container").height()+"px");
			
	}
	
	
	function modalPermission(){
		    $('#basic-modal-content').modal({overlayClose:false});
			$(".simplemodal-wrap").css('overflow','visible');
			$('#simplemodal-container').css('height',20+$(".basic-container").height()+"px");
			
	}
	
	function initModalScroll(){
			$('#basic-modal-content').modal();
				$(".simplemodal-wrap").css('overflow','scroll');
			$('#simplemodal-container').css('height',20+$(".basic-container").height()+"px");
			$('#simplemodal-container').css("position","absolute");
			$('#simplemodal-container').css("top","50px");
			$('#simplemodal-container').css("width","600px");
			
			
			
	}
	
	
	function checkLogged(){
		
		var state=false;
		$.ajax({
		  url:"<%=sitePathInitJs%>AUser",
		   data: {action:'chkLogged'},
		  type: 'POST',
		  dataType:'json',
		  async:false,
		  success: function(data) {
		  	 if(data.logged){
				state=true;
		  	 }else{
		  	 	state=false;
		  	 }
		  }
		});
		return state;
 		
	}   
	
	
	function checkPermission(moduleid,privilegeid){
		
		var state=false;
		$.ajax({
		  url:"<%=sitePathInitJs%>APermission",
		   data: {action:'checkPermission',moduleid:moduleid,privilegeid:privilegeid},
		  type: 'POST',
		  dataType:'json',
		  async:false,
		  success: function(data) {
		  	 if(data.logged){
				
				if(!data.granted){
					$('.basic-container').html(data.html);
						modalPermission();
					$(".modalCloseImg").hide();
					$("#simplemodal-overlay").unbind("simplemodal-close");
					$("#basic-modal-content").unbind("simplemodal-close");
					
					state=false;
					
					
				}else{
					state=true;
				}
				
		  	 }else{
		  	 	window.location="<%=sitePathInitJs%>";
		  	 }
		  }
		});
		return state;
 		
	}   
 
	