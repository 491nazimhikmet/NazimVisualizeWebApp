var app = angular.module('myApp', []);

app.controller('siirAraCtrl', function($scope) {
	$scope.aramaSonucList = {};
	$scope.showResults = false;
	$scope.showSiir = false;
	$scope.siirIcerik = "";

    $scope.callSearch = function () {

		$.post('searchSiir?',{searchText : $scope.searchTextM},function(response) {
			$scope.showResults = true;

			$scope.aramaSonucList = response;			
        });

        /*$.post('searchSiir?',{searchText : $scope.searchTextM},function(response) {
			$scope.showResults = true;

			$scope.aramaSonucList = response;			
        });*/
	}

	$scope.getSiir = function (work) {

		$.get('getSiir?siirId='+work.workID,function(response) {
			$scope.showSiir = true;

			$scope.siirIcerik = response;
			console.log(response);
			$('#siirinKendi').html(response); 
			
        });
	}
});

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