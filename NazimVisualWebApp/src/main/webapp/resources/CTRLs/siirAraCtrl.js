app.controller('siirAraCtrl', ['$scope','$http','BaseAPI','appConfig',function($scope,$http,BaseAPI,appConfig) {
	$scope.aramaSonucList = {};
	$scope.showResults = false;
	$scope.showSiir = false;
	$scope.seciliSiir = 0;
	$scope.gorselSonucShow = false;
	//$scope.showProgressBar = false;

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


        BaseAPI.callServlet('WordFrequencyGraphServlet',{searchText : $scope.searchTextM,drawType:"1"}).then(function(responseText) {
        	$scope.showResults = true;

			$('#imgFrequency').attr('src',$scope.baseImagePathUrl+responseText); 
            $('#imgFrequency')[0].src = $scope.baseImagePathUrl+responseText; 
        	$("#imgFrequency").show();

        	$('#imgFrequency2').attr('src',$scope.baseImagePathUrl+responseText); 
        	$('#imgFrequency3').attr('src',$scope.baseImagePathUrl+responseText); 	

        	loadEnded();		
        });

        
	}

	$scope.getGorsel = function(type){
		if($scope.seciliSiir <= 0 ){
			bootbox.alert("Lütfen bir şiir seçiniz");
		}else{
			$scope.$parent.showAramaSonuc = true;
			$scope.$parent.showSection(3);
			$scope.showSiir = false;
			$scope.gorselSonucShow = false;
			if(type == 1){
				$scope.siiriOku();
			}else if(type == 2){
				$scope.getWordCloud();
			}else if(type == 3){
				$scope.getRandomLines();
			}else if(type == 4){
				//wordNet
			}else{
				$scope.$parent.showAramaSonuc = false;
				$scope.$parent.showSection(2);
			}
		}
	}

	$scope.siiriOku = function(){
		loadProgress();
		
		BaseAPI.callServlet('getSiir',{siirId:$scope.seciliSiir+""}).then(function(response) {
			$scope.showSiir = true;
			$scope.gorselSonucShow = true;
			console.log(response);
			$('#siirinKendi').html(response); 
			loadEnded();
        });
	}

	$scope.getSiir = function (work) {
		loadProgress();
		BaseAPI.callServlet('getSiir',{siirId:work.workID+""}).then(function(response) {
			$scope.showSiir = true;
			console.log(response);
			$('#siirinKendi').html(response); 
			loadEnded();
        });
	}

	$scope.getWordCloud = function(){
		loadProgress();
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
			$scope.$parent.showLoading(); 
				
	}

	function loadEnded(){
			$scope.$parent.showLoaded(); 
	}

}]);