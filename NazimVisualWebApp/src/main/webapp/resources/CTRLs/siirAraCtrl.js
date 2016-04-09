app.controller('siirAraCtrl', ['$scope','$http','BaseAPI','appConfig',function($scope,$http,BaseAPI,appConfig) {
	$scope.aramaSonucList = {};
	$scope.showResults = false;
	$scope.showSiir = false;
	$scope.siirIcerik = "";
	$scope.seciliSiir = 0;
	$scope.gorselSonucShow = false;
	$scope.showProgressBar = false;
	$scope.zazaa = "zazzzzzza";

	$scope.baseImagePathUrl = appConfig.baseImagePathUrl;

	$scope.addToGorsellestirSiirList = function(work){

		var symbol = $("#sonucSymb"+work.workID);

		if(symbol.hasClass("glyphicon-minus")){
			symbol.removeClass("glyphicon-minus").addClass("glyphicon-plus");
			symbol.css("color","green");
			$scope.seciliSiir = 0
		}else if(symbol.hasClass("glyphicon-plus")){
			symbol.removeClass("glyphicon-plus").addClass("glyphicon-minus");
			symbol.css("color","red");
			$scope.seciliSiir = work.workID;
		}
	}	

    $scope.callSearch = function () {
    	
    	loadProgress();
    	$scope.zazaa = 5 ;
    	//$scope.$apply();
    	BaseAPI.callServlet('searchSiir',{searchText : $scope.searchTextM}).then(function(response){
    		$scope.aramaSonucList = response;
    		$scope.showResults = true;
    	});


        BaseAPI.callServlet('WordFrequencyGraphServlet',{searchText : $scope.searchTextM}).then(function(responseText) {
        	$scope.showResults = true;

			$('#imgFrequency').attr('src',$scope.baseImagePathUrl+responseText); 
            $('#imgFrequency')[0].src = $scope.baseImagePathUrl+responseText; 
        	$("#imgFrequency").show();	

        	loadEnded();		
        });

        
	}

	$scope.getSiir = function (work) {
		loadProgress();
		BaseAPI.callServlet('getSiir',{siirId:work.workID+""}).then(function(response) {
			$scope.showSiir = true;

			$scope.siirIcerik = response;
			console.log(response);
			$('#siirinKendi').html(response); 
			loadEnded();
        });
	}

	$scope.getWordCloud = function(){
		loadProgress();
		$scope.zazaa = 6;
		BaseAPI.callServlet('WordCloudServlet',{siirId:$scope.seciliSiir+""}).then(function(responseText) {
			$scope.gorselSonucShow = true;

            $('#imgGorselResult').attr('src',$scope.baseImagePathUrl+responseText); 
            $('#imgGorselResult')[0].src =$scope.baseImagePathUrl+responseText; 
        	$("#imgGorselResult").show();

			loadEnded();
			
        });
	}

	$scope.getRandomLines = function(){
		loadProgress();
		BaseAPI.callServlet('randomLineGorselleriServlet',{siirId:$scope.seciliSiir+""}).then(function(responseText) {
            
            $scope.gorselSonucShow = true;

            $('#imgGorselResult').attr('src',$scope.baseImagePathUrl+responseText); 
            $('#imgGorselResult')[0].src =$scope.baseImagePathUrl+responseText; 
        	$("#imgGorselResult").show(); 

			loadEnded();
        });
	}

	function loadProgress(){
			$scope.showProgressBar = true;
				
	}

	function loadEnded(){
			$scope.showProgressBar = false;
	}

}]);