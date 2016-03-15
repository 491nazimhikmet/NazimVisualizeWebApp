$(document).ready(function(){
	$('#siginModal').hide();
	$('#sigUpModal').hide();
	$('#mainModal').show();
	if(showGiris == true){
		showSignIn();
	}else if (showKaydol == true){
		showSignUp();
	}
	$("#imgResult").hide();
	$("#imgCloud").hide();
});

function showSignIn(){
	$('#siginModal').show();
	$('#sigUpModal').hide();
	$('#mainModal').hide();

};

function showSignUp(){
	$('#siginModal').hide();
	$('#sigUpModal').show();
	$('#mainModal').hide();
};

function getPng(){
	$.get('BaseServlet',function(responseText) {
			setTimeout(function(){ //alert("Hello");
			d = new Date();
            $('#imgResult').attr('src','/NazimVisualWebApp/resources/'+responseText); 
            $('#imgResult')[0].src ='/NazimVisualWebApp/resources/'+responseText; 
        	$("#imgResult").show(); }, 20); 
			
        });
}

function getWordCloud(){
	$.get('WordCloudServlet',function(responseText) {
			setTimeout(function(){ //alert("Hello");
			d = new Date();
            $('#imgCloud').attr('src','/NazimVisualWebApp/resources/'+responseText); 
            $('#imgCloud')[0].src ='/NazimVisualWebApp/resources/'+responseText; 
        	$("#imgCloud").show(); }, 20); 
			
        });
}