app.controller('siirAraCtrl', ['$scope','$http','BaseAPI','appConfig',function($scope,$http,BaseAPI,appConfig) {
	$scope.aramaSonucList = {};
	$scope.showResults = false;
	$scope.showSiir = false;
	$scope.seciliSiir = 0;
	$scope.gorselSonucShow = false;
	//$scope.showProgressBar = false;

	$scope.baseImagePathUrl = appConfig.baseImagePathUrl;

	$scope.addToGorsellestirSiirList = function(work){


		$scope.seciliSiir = work.workID;

		/*var symbol = $("#sonucSymb"+work.workID);

		if(symbol.hasClass("glyphicon-minus")){
			symbol.removeClass("glyphicon-minus").addClass("glyphicon-plus");
			symbol.css("color","green");
			$scope.seciliSiir = 0
		}else if(symbol.hasClass("glyphicon-plus")){
			symbol.removeClass("glyphicon-plus").addClass("glyphicon-minus");
			symbol.css("color","red");
			$scope.seciliSiir = work.workID;
		}*/
	}	

    $scope.callSearch = function () {
    	
    	loadProgress();
    	//$scope.$apply();
    	BaseAPI.callServlet('searchSiir',{searchText : $scope.searchTextM}).then(function(response){
    		if(response.length==0){
				bootbox.alert("Sonuç bulunamadı!");
				loadEnded();	
    			return;
    		}
    		$scope.aramaSonucList = response;
    		$scope.showResults = true;

    		//image olan görsel sonuçlarını getirmek için call atılır
    		BaseAPI.callServlet('WordFrequencyGraphServlet',{searchText : $scope.searchTextM,drawType:"1"}).then(function(responseText) {
	        	$scope.showResults = true;

				/*$('#imgFrequency').attr('src',$scope.baseImagePathUrl+responseText); 
	            $('#imgFrequency')[0].src = $scope.baseImagePathUrl+responseText; 
	        	$("#imgFrequency").show();*/

	        	$('#imgFrequency2').attr('src',$scope.baseImagePathUrl+responseText); 
	        	$('#imgFrequency3').attr('src',$scope.baseImagePathUrl+responseText); 	

	        	loadEnded();		
	        });

    		//processing js ile canvas çizimi için fonksyionu çağır
			$scope.drawWordFreqOverYear(1);

    	});

        
	}

	// When the user clicks on the button, open the modal 
	$scope.showFrequencyModal = function() {
		$("#myModal").show();
		//$("#modalImg").attr("src" , $("#imgFrequency").attr("src"));
		var canvasHeight = 1080;
     	var canvasWidth = 1920;
		$scope.sketchFreqYearCanvas($scope.yearFreqList,canvasHeight,canvasWidth,true);
		$("#canvasFreqOverYearModal").css("width","100%");

	}


	$scope.drawWordFreqOverYear = function(type){
		if($scope.searchTextM == "") return;
		
		var canvasHeight = $("#canvasFreqOverYear").height();
     	var canvasWidth = $("#canvasFreqOverYear").width();

		if (type == 1){//arama snucu için çiz
			BaseAPI.callServlet('getWordFrequencyData',{searchText : $scope.searchTextM}).then(function(response){
	    	 	$scope.yearFreqList = response;

				var yearFreqList = $scope.yearFreqList;
	
				$scope.sketchFreqYearCanvas(yearFreqList,canvasHeight,canvasWidth,false);
				$("#canvasFreqOverYear").height(150);
     			$("#canvasFreqOverYear").width(220);
	    	});
		}else if (type == 2){//mouse enter
			$("#canvasFreqOverYear").height(170);
     		$("#canvasFreqOverYear").width(280);
			$scope.sketchFreqYearCanvas($scope.yearFreqList,canvasHeight,canvasWidth,false);
		}else if(type == 3){//mouse leave
			$("#canvasFreqOverYear").height(150);
     		$("#canvasFreqOverYear").width(220);
			$scope.sketchFreqYearCanvas($scope.yearFreqList,canvasHeight,canvasWidth,false);
		}

	}


	$scope.sketchFreqYearCanvas = function (yearFreqList,canvasHeight,canvasWidth,isModal){
	

		function getMaxFreqMinMaxYear(){
			var maxFreq = 0;
			var minYear = 10000;
			var maxYear = 0;
			for (var i = 0; i < yearFreqList.length; i++) {
				if (yearFreqList[i].frequency > maxFreq && yearFreqList[i].year != 0)
					maxFreq = yearFreqList[i].frequency;
				
				if(yearFreqList[i].year < minYear && yearFreqList[i].year != 0)
					minYear = yearFreqList[i].year;
				
				if(yearFreqList[i].year > maxYear)
					maxYear = yearFreqList[i].year;
			}
			return [maxFreq,minYear,maxYear];	 
		}

		function sketchProc2(processing) {
			processing.setup = function(){
				/*processing.width = 300;
				processing.height = 300;*/
				
				processing.size(canvasWidth,canvasHeight);
				processing.noLoop();
			};

			// Override draw function, by default it will be called 60 times per second
			processing.draw = function() {
				processing.background(220);
				
				var width = processing.width;
				var height = processing.height;
				var margin = 15;
				var rectX = 1;

				var maxFreqMinMaxYear = getMaxFreqMinMaxYear();
				var textSize = processing.map(width, 0, 3200,2, 30);
				margin = Math.round( processing.map(height, 0, 2000, 0, 200) );
				rectX = processing.map(width, 0, 3200, 0, 10);

				for (var i = 0; i < yearFreqList.length; i++) {
					var currentYear = yearFreqList[i];

					if (currentYear.year == 0) {
						processing.fill(50);
						processing.textSize(textSize);
						processing.textAlign(processing.LEFT, processing.TOP);
						processing.textLeading(textSize); 
						processing.text("Aramanın tarihi bulunamayan tekrar sayısı : " + currentYear.frequency,0,0);
						continue;
					}

					var xPosDataOne = processing.map(currentYear.year, maxFreqMinMaxYear[1]-1, maxFreqMinMaxYear[2], 0 + margin, width - margin);
					var yPosDataOne = processing.map(currentYear.frequency, 0, maxFreqMinMaxYear[0], 0, height - Math.round(1.3 * margin));

					var year = currentYear.year + "";							

					processing.fill(104, 58, 58);
					processing.rectMode(processing.CORNER);
					processing.rect(xPosDataOne, height - margin, rectX, -1 * yPosDataOne);
				
					processing.textAlign(processing.LEFT, processing.BOTTOM);
					processing.textSize(textSize);
					processing.text(currentYear.frequency,xPosDataOne,height-margin-yPosDataOne);

					processing.fill(50);
					processing.pushMatrix() ;
					processing.translate(xPosDataOne,height - margin);
					processing.rotate(-1*processing.PI/2);
					processing.textSize(textSize);
					processing.textAlign(processing.RIGHT, processing.UP);
					processing.textLeading(textSize); 
					processing.text(year,-5,rectX*1.5);
					processing.popMatrix() ;
				}

			};
		}


		if(isModal == false){
			var canvasFreqOverYear = document.getElementById("canvasFreqOverYear");
			// attaching the sketchProc function to the canvas
			var processingInstance2 = new Processing(canvasFreqOverYear, sketchProc2);
		}
		
		
		if(isModal == true){
			var canvasFreqOverYearModal = document.getElementById("canvasFreqOverYearModal");
			// attaching the sketchProc function to the canvas
			var processingInstance3 = new Processing(canvasFreqOverYearModal, sketchProc2);

			$("#canvasFreqOverYearModal").css("width","100%");
		}
		//processingInstance2.noStroke();
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