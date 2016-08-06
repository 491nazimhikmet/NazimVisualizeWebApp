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


function showMainModal(){
	$('#siginModal').hide();
	$('#sigUpModal').hide();
	$('#mainModal').show();
};

app.controller('siirOkuCTRL', ['$scope','$http','BaseAPI','appConfig',function($scope,$http,BaseAPI,appConfig) {
		$scope.callSearch = function () {
    	
	    	BaseAPI.callServlet('getSiir',{siirId:$scope.searchID+""}).then(function(response) {

				$('#siirinOku').html(response); 
	        });

	        
		}
	}]);