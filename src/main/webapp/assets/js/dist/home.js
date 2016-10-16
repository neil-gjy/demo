$(function() {
	
	$("#btnRet").click(function(){
   	 window.location.href = baseUrl + "/home";
   });
   
   $("#btnLogout").click(function(){
  	 window.location.href = baseUrl + "/logout";
  });
   
	
});


function linkTo(href){
	window.location.href = href;
}