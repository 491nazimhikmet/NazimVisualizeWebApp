app.controller('detayliAramaCTRL', ['$scope','$http','BaseAPI','appConfig',function($scope,$http,BaseAPI,appConfig) {

	$scope.showYearComp = true;
	$scope.showPlaceComp = false;

	$scope.showLastResult = false;

	var arrayGorsels = [];

	function initialize(){
		loadProgress();
		$scope.getAllYears();
		$scope.getAllPlaces();
	}

	$scope.getAllYears = function(){
		loadProgress();
		BaseAPI.callServlet('getAllYears',{searchText : "1"}).then(function(response) {
        	$scope.allYears = response;
        	$scope.seciliBasDonemIlk = $scope.allYears[0];
			$scope.seciliBasDonemSon = $scope.allYears[$scope.allYears.length-1];
			$scope.seciliBitDonemIlk = $scope.allYears[0];
			$scope.seciliBitDonemSon = $scope.allYears[$scope.allYears.length-1];

        	loadEnded();		
        });
	}

	$scope.getAllPlaces = function(){
		loadProgress();
		BaseAPI.callServlet('getAllPlaces',{searchText : "1"}).then(function(response) {
        	$scope.allPlaces = response;
        	$scope.firstPlace = $scope.allPlaces[0];
			$scope.secondPlace = $scope.allPlaces[0];
			
        	loadEnded();		
        });
	}

	function killPrevGorsels(){
		for(var i=0; i<arrayGorsels.length; i++){
			arrayGorsels[i].exit();
		}
	}



	initialize();


	$scope.detayliAra = function(){
		loadProgress();
		var searchType = 0;

		if($scope.showYearComp == true){
			searchType = 2;
		}else{
			searchType = 1;
		}

		if($scope.searchWords.legth == 0){
			bootbox.alert("Lütfen aramak istediğiniz kelime dizinini giriniz");
		}


		BaseAPI.callServlet('detayliAra',{type : searchType,
						words: $scope.searchWords,
						seciliBasDonemIlk: $scope.seciliBasDonemIlk + "",
						seciliBasDonemSon: $scope.seciliBasDonemSon + "",
						seciliBitDonemIlk: $scope.seciliBitDonemIlk + "",
						seciliBitDonemSon: $scope.seciliBitDonemSon + "",
						firstPlace: $scope.firstPlace,
						secondPlace: $scope.secondPlace
						}).then(function(response) {
        	
        	console.log(response);

        	var xAxisStr = "";
        	var yAxisStr = "";

        	if(searchType == 1){ // place comp
        		xAxisStr = $scope.firstPlace;
        		yAxisStr = $scope.secondPlace;
        	}else if(searchType == 2){//year comp
        		xAxisStr = $scope.seciliBasDonemIlk + " - " + $scope.seciliBasDonemSon;
        		yAxisStr = $scope.seciliBitDonemIlk + " - " + $scope.seciliBitDonemSon;
        	}
        	var canvasHeight = 1080;
     		var canvasWidth = 1920;
     		$scope.sketchSpaceCanvas(xAxisStr,yAxisStr,response,canvasHeight,canvasWidth);
			$("#canvasSpace").css("width","100%");
			$("#spaceModal").show();
			$scope.showLastResult = true;
        	loadEnded();		
        });

	}


	$scope.sketchSpaceCanvas = function (xAxisStr,yAxisStr,pointList,canvasHeight,canvasWidth){

		function getMaxValues(){
			var maxX  = 0 ;
			var maxY = 0;

			for(var i = 0; i<pointList.length; i++){
				if(pointList[i].x > maxX){
					maxX = pointList[i].x ;
				}

				if(pointList[i].y > maxY){
					maxY = pointList[i].y;
				}

			}

			return [maxX,maxY];
		}

		function sketchProc2(processing) {
			processing.setup = function(){				
				processing.size(canvasWidth,canvasHeight);
				processing.frameRate(1);
				//processing.noLoop();
			};

			// Override draw function, by default it will be called 60 times per second
			processing.draw = function() {
				processing.background(255);
				
				var width = processing.width;
				var height = processing.height;

				var marginX = processing.map(width, 0, 3200, 0, 500); //soldan sağa margin
				var marginY = processing.map(height,0,1900,0,200); // aşağıdan yukarı margin
				var textSize = processing.map(width, 0, 3200,10, 40);
				var radius = processing.map(height,0,1900,10,50); // aşağıdan yukarı margin
					
				processing.fill(0);
				processing.strokeWeight(4);
				processing.line(marginX,height-marginY,width-marginX+radius,height-marginY); // X ekseni
				processing.line(marginX,marginY-radius,marginX,height-marginY);  //Y ekseni

				processing.stroke(150);
				processing.strokeWeight(1);
				processing.line(marginX,height-marginY,width-marginX,marginY);
				processing.stroke(0);
				processing.strokeWeight(4);

				processing.fill(50);
				processing.pushMatrix() ;
				processing.translate(0, height);
				processing.rotate(-1*processing.PI/2);
				processing.fill(0,236,29);
				processing.textSize(textSize);
				processing.textAlign(processing.CENTER,processing.UP);
				processing.textLeading(textSize); 
				processing.text(yAxisStr,height/2,marginX/2);
				processing.popMatrix() ;

				processing.fill(15,36,240);
				processing.textSize(textSize);
				processing.textAlign(processing.CENTER, processing.UP);
				processing.textLeading(textSize); 
				processing.text(xAxisStr,width/2,height-marginY/2);

				processing.fill(252,14,0);
				processing.textSize(textSize/1.2);
				processing.textAlign(processing.CENTER, processing.UP);
				processing.textLeading(textSize); 
				processing.text("Arama detayına ulaşılamayan tekrar sayıları kırmızı ile belirtilmiştir",width/2,marginY/4);


				var maxs = getMaxValues();
				var maxXval = maxs[0] + 1;
				var maxYval = maxs[1] + 1;

				for(var i = 0 ; i<pointList.length; i++){
					var xVal = pointList[i].x;
					var yVal = pointList[i].y;
					var nfVal = pointList[i].nf;
					var posX = processing.map(xVal,0,maxXval,marginX,width-marginX);
					var posY = processing.map(yVal,0,maxYval,marginY,height-marginY);
					posY = height - posY;

					var prevEq = 1;
					for(var j = 0 ; j<i; j++){
						if(pointList[i].x == pointList[j].x && pointList[i].y == pointList[j].y){
							prevEq ++;
						}
					}

					//if(processing.mouseX  <= )
					processing.fill(130,201,235,90);
					processing.ellipse(posX,posY,radius,radius);
					processing.fill(50);
					//processing.textSize(textSize);
					//processing.textAlign(processing.CENTER, processing.CENTER);
					//processing.textLeading(textSize); 
					//processing.text(" ("+Math.round(xVal)+" , "+Math.round(yVal)+") " + pointList[i].word,posX+radius,posY - prevEq * radius);
					processing.text(pointList[i].word+" ( ",posX+radius,posY - prevEq * radius );

					
					var a = pointList[i].word+" ( ";
					var leftMargin = processing.textWidth(a) ;
					//- a.length * textSize * 0.15
					processing.fill(15,36,240);
					processing.text(Math.round(xVal)+",",posX+radius+leftMargin/ getBaseLog(4.5,a.length) , posY - prevEq * radius);

					processing.fill(0,236,29);
					a = pointList[i].word+" ( "+Math.round(xVal) + ", ";
					leftMargin = processing.textWidth(a);
					processing.text(Math.round(yVal) + ",",posX+radius+leftMargin/ getBaseLog(8,a.length), posY - prevEq * radius);

					processing.fill(252,14,0);
					a = pointList[i].word+" ( "+Math.round(xVal) + ", "+Math.round(yVal)+",";
					leftMargin = processing.textWidth(a);
					processing.text(Math.round(nfVal),posX+radius+leftMargin/ getBaseLog(11,a.length), posY - prevEq * radius);

					processing.fill(0);
					a = pointList[i].word+" ( "+Math.round(xVal) + ", "+Math.round(yVal)+","+Math.round(nfVal);
					leftMargin = processing.textWidth(a);
					processing.text(")",posX+radius+leftMargin/ getBaseLog(12,a.length), posY - prevEq * radius);
				}

			};

			function getBaseLog(x, y) {
			  return Math.log(y) / Math.log(x);
			}
		}

		killPrevGorsels();
		
		var canvasSpace = document.getElementById("canvasSpace");
		// attaching the sketchProc function to the canvas
		var processingInstance1 = new Processing(canvasSpace, sketchProc2);


		arrayGorsels.push(processingInstance1);



		
	}

	function loadProgress(){
		$scope.$parent.showLoading(); 		
	}

	function loadEnded(){
		$scope.$parent.showLoaded(); 
	}

}]);