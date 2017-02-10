app.controller('siirAraCtrl', ['$scope','$http','BaseAPI','appConfig',function($scope,$http,BaseAPI,appConfig) {
	$scope.aramaSonucList = {};
	$scope.showResults = false;
	$scope.showSiir = false;
	$scope.seciliSiir = 0;
	$scope.seciliSiirYear = 0;
	$scope.seciliSiirPlace = "";
	$scope.seciliSiirBook = 0;
	$scope.seciliSiirName = "";
	$scope.gorselSonucShow = false;
	$scope.highlightFrequencies = false;
	$scope.highlightSiirYear = 0;
	$scope.highlightSiirBook = 0;
	$scope.highlightSiirPlace = "";

	$scope.$on('changeSearch',function(event, args){
		$scope.searchTextM = args.searchText;
	});

	$scope.$on('callSearch',function(event, args){
		$scope.callSearch();
	});

	$scope.baseImagePathUrl = appConfig.baseImagePathUrl;

	$scope.addToGorsellestirSiirList = function(work){


		$scope.seciliSiir = work.workID;

		$scope.seciliSiirYear = work.year;

		$scope.seciliSiirPlace = work.locationOfComp;

		$scope.seciliSiirBook = work.bookID;

		$scope.seciliSiirName = work.name;

	}	


	var arrayGorsels = [];

	$scope.highLightPlaceYear = function(work,type){

		$scope.highlightSiirYear = work.year;
		$scope.highlightSiirBook = work.bookID;
		$scope.highlightSiirPlace = work.locationOfComp;

		if(type == 2){//mouse over
			$scope.highlightFrequencies = true;
		}else if(type == 3){//mouse leave
			$scope.highlightSiirYear = 0;
			$scope.highlightSiirBook = 0;
			$scope.highlightSiirPlace = "";
			$scope.highlightFrequencies = false;
		}

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
    		$scope.showGorselGeriDon = false;
    		$scope.aramaSonucList = response;
    		$scope.showResults = true;

	    	loadEnded();			

    	});

        
	}

	/** oluşturulan gorselin kaydetme kısmı başlangıç **/
	function download() {
	    var dt = document.getElementById("canvasGorselSonuc").toDataURL();
	    this.href = dt;
	};
	//downloadLnk.addEventListener('click', download, false);

	downloadLnk.addEventListener("click", function() {
		var canvas = document.getElementById('canvasGorselSonuc');

		// only jpeg is supported by jsPDF
		var imgData = canvas.toDataURL("image/jpeg", 1.0);
		//window.location = imgData;
		var pdf = new jsPDF('p','pt','a2');

		pdf.addImage(imgData, 'JPEG', 0,0,1350, 750);
		var downloadLnk = document.getElementById('downloadLnk');

		pdf.save("GorselSonuc.pdf");
	}, false);

	
	function convertDataURLToImageData(dataURL, callback) {
	    if (dataURL !== undefined && dataURL !== null) {
	        var canvas, context, image;
	        canvas = document.createElement('canvas');
	        canvas.width = 600;
	        canvas.height = 1170;
	        context = canvas.getContext('2d');
	        image = new Image();
	        image.addEventListener('load', function(){
	            context.drawImage(image, 0, 0, canvas.width, canvas.height);
	            callback(context.getImageData(0, 0, canvas.width, canvas.height));
	        }, false);
	        image.src = dataURL;
	    }
	}

	$scope.saveCurrentGorsel = function() {

		var d = new Date();
		var tm = d.getTime();

		//var k = arrayGorsels[arrayGorsels.length-1].save(tm);

		var image = document.getElementsByTagName("canvas")[0].toDataURL();
		//console.log(image);

		convertDataURLToImageData(
		   	image,
		    function(imageData){
		         // Do something with imageData
		    	console.log(imageData);
		    }
		);

		bootbox.alert("Oluşturulduğunuz görsel kaydedilmiştir.");
	}

	/** oluşturulan gorselin kaydetme kısmı bitiş **/


	function addMouseUpDownWheelEventsToCanvas(varCanvas, varProcessing,varHScrollBar){
		varCanvas.addEventListener("mousedown",function(){
			varProcessing.mousePressed = true;
		},false);
		varCanvas.addEventListener("mouseup",function(){
			varProcessing.mousePressed = false;	
		},false);

		var isMoving=false;

		varCanvas.addEventListener('mousewheel',function(event){
		    //mouseController.wheel(event);
		    event.preventDefault();
		    if (isMoving) return;

		    varHScrollBar.setScrollPos(event.deltaY);

		    isMoving = true;
			   setTimeout(function() {
			    isMoving=false;
			   },250);

		    return false; 
		}, false);
	}

	$scope.drawTekrarGorsel = function(wordList){
			
		var canvasHeight = $("#canvasGorselSonuc").height();
		var canvasWidth = $("#canvasGorselSonuc").width();
		$scope.sketchGorselSonucCanvas(wordList,canvasHeight,canvasWidth);

	}

	$scope.drawSifatGorsel = function(wordList,worklines){
			
		var canvasHeight = $("#canvasGorselSonuc").height();
		var canvasWidth = $("#canvasGorselSonuc").width();
		$scope.sketchSifatGorselSonucCanvas(wordList,worklines,canvasHeight,canvasWidth);

	}

	$scope.drawKipveKisiGorsel = function(wordList,worklines){
			
		var canvasHeight = $("#canvasGorselSonuc").height();
		var canvasWidth = $("#canvasGorselSonuc").width();
		$scope.sketchKipveKisiGorselSonucCanvas(wordList,worklines,canvasHeight,canvasWidth);

	}

	$scope.drawRoundedGorsel = function(wordList,worklines){
			
		var canvasHeight = $("#canvasGorselSonuc").height();
		var canvasWidth = $("#canvasGorselSonuc").width();
		$scope.sketchRoundedGorselSonucCanvas(wordList,worklines,canvasHeight,canvasWidth);

	}

	$scope.drawBarkodeGorsel = function(siirLines,parsedWords,affectiveResults){
			
		$("#canvasGorselSonuc").height(600);
		$("#canvasGorselSonuc").width(1170);	
		var canvasHeight = $("#canvasGorselSonuc").height();
		var canvasWidth = $("#canvasGorselSonuc").width();
		$scope.sketchBarkodeGorselSonucCanvas(siirLines,parsedWords,affectiveResults,canvasHeight,canvasWidth);

	}

	$scope.drawBubbleGorsel = function(siirLines,parsedWords){
			
		$("#canvasGorselSonuc").height(600);
		$("#canvasGorselSonuc").width(1170);	
		var canvasHeight = $("#canvasGorselSonuc").height();
		var canvasWidth = $("#canvasGorselSonuc").width();
		$scope.sketchBuubleGorselSonucCanvas(siirLines,parsedWords,canvasHeight,canvasWidth);

	}

	$scope.drawfreqOverYearGorsel = function(){
			
		var canvasHeight = $("#canvasGorselSonuc").height();
		var canvasWidth = $("#canvasGorselSonuc").width();
		var yearFreqList = $scope.yearFreqList;
		$scope.sketchFreqYearCanvas(yearFreqList,canvasHeight,canvasWidth);

	}

	$scope.drawfreqOverBookGorsel = function(){
			
		var canvasHeight = $("#canvasGorselSonuc").height();
		var canvasWidth = $("#canvasGorselSonuc").width();
		var bookFreqList = $scope.bookFreqList;
		$scope.sketchFreqBookCanvas(bookFreqList,canvasHeight,canvasWidth);

	}

	$scope.drawfreqOverPlaceGorsel = function(){
			
		var canvasHeight = $("#canvasGorselSonuc").height();
		var canvasWidth = $("#canvasGorselSonuc").width();
		$scope.sketchFreqPlaceCanvas($scope.placeFreqList,canvasHeight,canvasWidth);

	}

	function killPrevGorsels(){
		for(var i=0; i<arrayGorsels.length; i++){
			arrayGorsels[i].exit();
		}
	}

	$scope.sketchBuubleGorselSonucCanvas = function (satırlar,words,height,width){
	

		function sketchBubbleGorselResult(processing) {
			
			var zoom = 1;
			var flag = false;
			var spaceCount=0;

			processing.setup = function(){
				
				processing.size (width, height);		
				processing.frameRate(50);  	
				processing.stroke(255);

			};

			// Override draw function, by default it will be called 60 times per second
			processing.draw = function() {						
				
				if(flag)
					processing.background(255);//circle 
				else
					processing.background(180);
				
				processing.scale(zoom);
				
				
				var x=processing.map(width,0,1920,0,30); //x coordinate of the ellipse
				var y=processing.map(height,0,1080,0,40); //y coordinate of the ellipse
				var r=processing.map(height,0,1080,0,15);
				var color = [50,50,50];
						
				var alpha=255;
				
				var interY = processing.map(height,0,1080,0,40);
						
				for(var i=0;i<words.length;i++){
					if(i!=0 && words[i].workLineID != words[i-1].workLineID){	
						y+=interY;
					}	
						
					x=words[i].wordStart*3/2;	
					r=words[i].text.length*2;//length of the word
						
					if(flag)	
					alpha = 40;
								
					color = setColor(words[i].text);	
					processing.fill(color[0],color[1],color[2],alpha);
								
					processing.ellipse(x,y,r,r);	
								
					//to see poetry
					if(flag){
						alpha = 255;
						processing.textSize(11);
						processing.fill(0,255);
						processing.text(words[i].text,x,y);	
					}		
				}
				
			};

			/*processing.keyPressed() {
				if(processing.key == processing.CODED){
					if(processing.keyCode == processing.UP){
						zoom += .03;
					}
					else if(processing.keyCode == processing.DOWN){
						zoom -= .03;	
					}
				}	
				if (processing.key == ' '){
					spaceCount++;
					if(spaceCount%2 == 1){
						flag = true;
					}else 
						flag = false;
				}
			}*/

			function setColor(word){
				var color = [50,50,50];
				
				if(word.indexOf("beyaz") !== -1){
					color[0] =255;
					color[1] =255;
					color[2] =255;
				}else if(word.indexOf("kızıl") !== -1){
					//fill(127,0,0);
					color[0] =127;
					color[1] =0;
					color[2] =0;
				
				}else if(word.indexOf("sarı")  !== -1 && (!word.indexOf("sarık"))  !== -1){
					//fill(255,255,20);
					color[0] =255;
					color[1] =255;
					color[2] =20;
					
				}else if(word.indexOf("siyah")  !== -1 ||word.indexOf("kara")  !== -1 ){
					//fill(255,255,20);
					color[0] =0;
					color[1] =0;
					color[2] =0;	
				}else if(word.indexOf("kırmızı")  !== -1){
					color[0] =255;
					color[1] =0;
					color[2] =0;	
				}else if(word.indexOf("yeşil")  !== -1){
					color[0] =0;
					color[1] =140;
					color[2] =0;	
				}else if(word.indexOf("mavi")  !== -1){
					color[0] =0;
					color[1] =0;
					color[2] =200;	
				}else if(word.indexOf("mor") !== -1 ){
					color[0] =100;
					color[1] =0;
					color[2] =100;	
				}
				return color;
			}
				
		}

			killPrevGorsels();
		
			var canvasGorselSonuc = document.getElementById("canvasGorselSonuc");
			// attaching the sketchProc function to the canvas
			var processingInstance13 = new Processing(canvasGorselSonuc, sketchBubbleGorselResult);

			arrayGorsels.push(processingInstance13);

			$("#canvasGorselSonuc").css("width","100%");
		//processingInstance2.noStroke();
	}

	$scope.sketchBarkodeGorselSonucCanvas = function (satırlar,words,affectiveResults,height,width){
	

		function sketchBarkodGorselResult(processing) {
			
			var benList = [];
			var bizList = []; 

			var defaultColour = 150;

			processing.setup = function(){
				
				processing.size (width, height);		
				processing.frameRate(10);  	
				processing.stroke(255);

			};

			// Override draw function, by default it will be called 60 times per second
			processing.draw = function() {				
				processing.background(255);
		
				
				var lengthOfPoem = satırlar.length;
				var nameOfPoem = satırlar[0].line;
				
				var topMargin = processing.map(height,0,1080,0,200);
				var barcodeLength = height-topMargin*1.1;
				
				var interY = barcodeLength/lengthOfPoem;
				
				var textX = width/8;
				var textY = processing.map(height,0,1080,0,50);
				
				var shift = processing.map(height,0,1080,0,25);
				
				processing.fill(0);
				processing.text("Her bir çizgi şiirdeki bir satırı ifade eder.",textX,textY);
				processing.text("Etken fiil içeren satırlar yeşil, edilgen fiil içeren satırlar kırmızı ile renklendirilmiştir.",textX,textY+shift);
				processing.text("Satırda geçen fillerin çekimlerine göre sırasıyla: ben/kırmızı, sen/sarı, o/yeşil, biz/cyan, siz/mavi , onlar/mor ile renklendirilmiştir.",textX,textY+2*shift);
				processing.text("Satırlar olumluluk/valence, uyarılma/arousal ve baskınlık/dominance değerlerine göre yüksek değerliler yeşil, düşük değerliler kırmızı ile renklendirilmiştir.",textX,textY+3*shift);

			
				var x = width/8;
				var y = topMargin;

				processing.fill(0,0,200);
				processing.stroke(0,0,200);
				
				processing.text("satır uzunlukları:",x,y);
				processing.text("etken-edilgen :",x*2,y);
				processing.text("kişi çekimi :",x*3,y);
				processing.text("olumluluk/valence:",x*4,y);
				processing.text("uyarılma/arousal:",x*5,y);
				processing.text("baskınlık/dominance:",x*6,y);
				
				processing.strokeWeight(2);
				y = topMargin + 2*interY;
				
				var satırLength;
				var barWidth = width/12;  
				
				var mouseMargin = processing.map(height,0,1080,0,5);
				
				for(var i=0;i<lengthOfPoem;i++){
					
					satırLength = (satırlar[i].lineFinish - satırlar[i].lineStart)/2 ;
					processing.stroke(defaultColour);
					
					processing.line(x,y,x+satırLength,y);
					y += interY;

				}	
				
				y = topMargin + 2*interY;

				
				for(var i=0;i<lengthOfPoem;i++){ 	//etken edilgen:
					for(var j=0;j<words.length;j++){
						if(satırlar[i].lineID == words[j].workLineID){ // bulunduğumuz satırdaki sözcüklere göre satırı renklendir 
							if(etken(j)){
								processing.stroke(0,220,40);//yeşil
							}
							else if(edilgen(j)){
								processing.stroke(220,0,0);//kırmızı
							}
							else
								processing.stroke(defaultColour);
						}
					}
					processing.line(2*x,y,2*x+barWidth,y);	
					y += interY;
				}
				
				y =  (topMargin + 2*interY);

				
				for(var i=0;i<lengthOfPoem;i++){	//ben ve biz ayrımı:
					for(var j=0;j<words.length;j++){
						if(satırlar[i].lineID == words[j].workLineID){
							strokeSubject(j);
						}
					}
					processing.line(3*x,y,3*x+barWidth,y);	
					y += interY;
				}
				
				y = (topMargin + 2*interY);

				
				for(var i=0;i<lengthOfPoem;i++){ //valence-arousal-dominance
			
					strokeValence(i);
					processing.line(4*x,y,4*x+barWidth,y);
					strokeArousal(i);
					processing.line(5*x,y,5*x+barWidth,y);
					strokeDominance(i);
					processing.line(6*x,y,6*x+barWidth,y);
					
					y += interY;
				}
				
			};

			function edilgen(j){
				if (words[j].parsedForm.indexOf("Verb") > -1 && words[j].parsedForm.indexOf("Pass") > -1) return true;
				else return false;		
			}
			function etken(j){
				if (words[j].parsedForm.indexOf("Verb") > -1 && !(words[j].parsedForm.indexOf("Pass") > -1)) return true;
				else return false;	
			}
			
			function strokeValence(i){
				var valence = 3;
				if(i<affectiveResults.length)
					valence = parseFloat(affectiveResults[i].valence);

				if(valence<2.5){
					processing.stroke(220,0,0);//red
				}else if(valence>3.5){
					processing.stroke(0,220,0);//green
				}else
					processing.stroke(defaultColour);
			}

			function strokeArousal(i){
				var arousal=3;
				if(i<affectiveResults.length)
				arousal = parseFloat(affectiveResults[i].arousal);
				
				//int color = (int) ((arousal/5.0)*250);	
				if(arousal<2.5){
					processing.stroke(220,0,0);
				}else if(arousal>3.5){
					processing.stroke(0,220,0);
				}else
					processing.stroke(defaultColour);
			}

			function strokeDominance(i){
				var dominance =3;
				if(i<affectiveResults.length)
				dominance = parseFloat(affectiveResults[i].dominance);
				
				//int color = (int) ((dominance/5.0)*250);	
				if(dominance<2.5){
					processing.stroke(220,0,0);
				}else if(dominance>3.5){
					processing.stroke(0,220,0);
				}else
					processing.stroke(defaultColour);
			}

			function strokeSubject(i){
				if (words[i].parsedForm.indexOf("A1sg") > -1 ||words[i].parsedForm.indexOf("P1sg") > -1 ){// ben-kırmızı
					processing.fill(255,0,0);
					processing.stroke(255,0,0);			
				}else if (words[i].parsedForm.indexOf("A2sg") > -1 ||words[i].parsedForm.indexOf("P2sg") > -1 ){// sen-sarı
					processing.fill(255,220,0);
					processing.stroke(255,220,0);
				}else if (words[i].parsedForm.indexOf("A1pl") > -1 ||words[i].parsedForm.indexOf("P1pl") > -1 ){// biz-cyan
					processing.fill(0,240,255);
					processing.stroke(0,240,255);
				}else if (words[i].parsedForm.indexOf("A2pl") > -1 ||words[i].parsedForm.indexOf("P2pl") > -1 ){// siz-mavi
					processing.fill(0,0,255);
					processing.stroke(0,0,255);
				}else if (words[i].parsedForm.indexOf("A3pl") > -1 ||words[i].parsedForm.indexOf("P3pl") > -1 ){// onlar-mor
					processing.fill(255,0,255);
					processing.stroke(255,0,255);
				}else if (words[i].parsedForm.indexOf("A3sg") > -1 ||words[i].parsedForm.indexOf("P3sg") > -1 ){// o-yeşil
					processing.fill(0,240,0);
					processing.stroke(0,240,0);
				}
				else{
					processing.fill(defaultColour);
					processing.stroke(defaultColour);
				}
			}

				
		}

			killPrevGorsels();
		
			var canvasGorselSonuc = document.getElementById("canvasGorselSonuc");
			// attaching the sketchProc function to the canvas
			var processingInstance13 = new Processing(canvasGorselSonuc, sketchBarkodGorselResult);

			arrayGorsels.push(processingInstance13);

			$("#canvasGorselSonuc").css("width","100%");
		//processingInstance2.noStroke();
	}

	$scope.sketchRoundedGorselSonucCanvas = function (words,worklines,height,width){
	

		function sketchRoundedGorselResult(processing) {
			
			var red = [];
			var green = [];
			var blue = []; 

			processing.setup = function(){
				
				processing.size (width, height);		
				processing.frameRate(10);  	
				//processing.noLoop();
				
				for(var i=0;i<worklines.length;i++){
					red.push(processing.random(255));
					green.push(processing.random(255));
					blue.push(processing.random(255));
				}
			};

			// Override draw function, by default it will be called 60 times per second
			processing.draw = function() {				
				processing.background(255);
				
				var rIn = processing.map(height,0,1080,0,200);
				var rOut = processing.map(height,0,1080,0,500);

				var centerX = width/2;
				var centerY  = height/2;
				var intervalOut = 2*processing.PI/words.length;
				var intervalIn = 2*processing.PI/worklines.length;

				processing.ellipseMode(processing.CENTER);
				processing.fill(0);
				//processing.text(worklines[0].line,centerX-rIn/2,centerY); //bunun yerine şiir adı gelicek
				
				var xOut=0;
				var yOut=0;
				var xIn=0;
				var yIn=0;
				
				var ellipseSize = 10;
				var mouseMargin = 5;
				
				
				for(var i=0;i<worklines.length;i++){
					
					processing.stroke(red[i],green[i],blue[i]);
					processing.fill(red[i],green[i],blue[i]);

					xIn=centerX-rIn*processing.cos(i*intervalIn);
					yIn=centerY-(rIn*processing.sin(i*intervalIn));
					ellipseSize = 10;
					processing.ellipse(xIn,yIn,ellipseSize,ellipseSize);		
					
					ellipseSize=8;
					
					for(var j=0;j<words.length;j++){	
						xOut=centerX-rOut*processing.cos(j*intervalOut);
						yOut=centerY-rOut*processing.sin(j*intervalOut);
						
						processing.stroke(red[i],green[i],blue[i]);
						processing.fill(red[i],green[i],blue[i]);
						
						
						if(worklines[i].lineID ==words[j].workLineID){
							processing.ellipse(xOut,yOut,ellipseSize,ellipseSize);	
							processing.line(xOut,yOut,xIn,yIn);
						}		
					}
				}	
				
			};

				
		}

			killPrevGorsels();
		
			var canvasGorselSonuc = document.getElementById("canvasGorselSonuc");
			// attaching the sketchProc function to the canvas
			var processingInstance11 = new Processing(canvasGorselSonuc, sketchRoundedGorselResult);

			arrayGorsels.push(processingInstance11);

			$("#canvasGorselSonuc").css("width","100%");
		//processingInstance2.noStroke();
	}

	$scope.sketchKipveKisiGorselSonucCanvas = function (words,worklines,height,width){
	

		function sketchKipveKisiGorselResult(processing) {
			processing.setup = function(){
				
				processing.size (width, height);		
				processing.frameRate(10);  	
				//processing.noLoop();
			};

			// Override draw function, by default it will be called 60 times per second
			processing.draw = function() {				
				processing.background(255);
				processing.stroke(0);
				processing.fill(0);
				var x=0;
				var y=0;
				var margin = processing.map(height,0,1080,0,60);
				var visualHeight=height-margin;
				var mul=1;
				if(worklines.length > 20)
					mul = 2;
				var intervalY= visualHeight/(worklines.length-1) * mul;
				//if(worklines.size()>50)
					//intervalY =2*intervalY; // çok uzun şiirlerde karışıklık oluyor diye böyle yaptım, değiştirmek lazım!
				var intervalX= processing.map(width,0,1920,0,40);
				var r = processing.map(height,0,1080,0,10);
				var textX = processing.map(width,0,1920,0,60);
				var textY = processing.map(height,0,1080,0,100);
			
				processing.text("KİP VE KİŞİ ÇEKİMLERİNE GÖRE",textX,textY);
				textX += processing.map(width,0,1920,0,10);
				textY += processing.map(height,0,1080,0,30);
				processing.text($scope.seciliSiirName+":",textX,textY);

				textX = processing.map(width,0,1920,0,100);
				textY= processing.map(height,0,1080,0,250);
				
				var shiftX = processing.map(width,0,1920,0,200);
				var shiftY = processing.map(height,0,1080,0,30);
				
				processing.strokeWeight(3);
				processing.stroke(255,0,0);
				processing.fill(255,0,0);
				processing.line(textX+shiftX/2,textY,textX+shiftX,textY);
				processing.text("BEN",textX,textY);
				
				processing.strokeWeight(3);
				processing.stroke(255,220,0);
				processing.fill(255,220,0);
				processing.line(textX+shiftX/2,textY+shiftY,textX+shiftX,textY+shiftY);
				processing.text("SEN",textX,textY+shiftY);
				
				processing.strokeWeight(3);
				processing.stroke(0,240,0);
				processing.fill(0,240,0);
				processing.line(textX+shiftX/2,textY+shiftY*2,textX+shiftX,textY+shiftY*2);
				processing.text("O",textX,textY+shiftY*2);

				processing.strokeWeight(3);
				processing.stroke(0,240,255);
				processing.fill(0,240,255);
				processing.line(textX+shiftX/2,textY+shiftY*3,textX+shiftX,textY+shiftY*3);
				processing.text("BİZ",textX,textY+shiftY*3);
				
				processing.strokeWeight(3);
				processing.stroke(0,0,255);
				processing.fill(0,0,255);
				processing.line(textX+shiftX/2,textY+shiftY*4,textX+shiftX,textY+shiftY*4);
				processing.text("SİZ",textX,textY+shiftY*4);
				
				processing.strokeWeight(3);
				processing.stroke(255,0,255);
				processing.fill(255,0,255);
				processing.line(textX+shiftX/2,textY+shiftY*5,textX+shiftX,textY+shiftY*5);
				processing.text("ONLAR",textX,textY+shiftY*5);

				textY=processing.map(width,0,1920,0,500);
				processing.stroke(0);
				processing.fill(0);
				processing.text("GEÇMİŞ ZAMAN",textX,textY);
				processing.strokeWeight(2);
				shiftX = processing.map(width,0,1920,0,250);
				x=textX+shiftX;
				y=textY;
				processing.line(x-r,y,x,y-r);
				processing.line(x-r,y,x+r,y);
				processing.line(x-r,y,x,y+r);
				shiftY = processing.map(height,0,1080,0,5);
				textY += processing.map(height,0,1080,0,50);
				y=textY-shiftY;
				processing.text("ŞİMDİKİ ZAMAN",textX,textY);
				processing.rectMode(processing.CENTER);
				processing.rect(x,y,r,r);
				textY += processing.map(height,0,1080,0,50);
				y=textY;
				processing.text("GELECEK ZAMAN",textX,textY);
				processing.strokeWeight(2);
				processing.line(x+r,y,x,y-r);
				processing.line(x-r,y,x+r,y);
				processing.line(x+r,y,x,y+r);
				textY += processing.map(height,0,1080,0,50);
				y=textY;
				processing.text("GENİŞ ZAMAN",textX,textY);
				processing.ellipseMode(processing.CENTER);
				processing.ellipse(x,y,3*r/2,3*r/2);
				
				
				
				x = processing.map(width,0,1920,0,600);
				y = processing.map(height,0,1080,0,30);
				r = processing.map(height,0,1080,0,9);
				textX = processing.map(width,0,1920,0,1300);
				textY = processing.map(height,0,1080,0,500);
				
				var flag=0;
				for(var i=0;i<words.length;i++){
					if(words[i].workLineID != worklines[0].lineID || words[i].workLineID != worklines[worklines.length-1].lineID){
						//System.out.println(words.get(i).getWorkLineID()+" "+worklines.get(0).getLineID()); //idler match etmiyor words with parsedform
						//System.out.print(words.get(i).getText()+"	"+words.get(i).getParsedForm()+"	");
						
						if(y>height-margin/2){
							y = processing.map(height,0,1080,0,30);
							x = processing.map(width,0,1920,0,1000);
							flag=1;
						}
						myStroke(i);
						if(words[i].parsedForm.indexOf("Past") > -1){ //geçmiş zaman
								processing.strokeWeight(2);
								processing.line(x-r,y,x,y-r);
								processing.line(x-r,y,x+r,y);
								processing.line(x-r,y,x,y+r);
						}else if(words[i].parsedForm.indexOf("Prog") > -1){ //şimdiki zaman
							processing.rectMode(processing.CENTER);
							processing.rect(x,y,r,r);
						}else if(words[i].parsedForm.indexOf("Fut") > -1){ //gelecek zaman
							processing.strokeWeight(2);
							processing.line(x+r,y,x,y-r);
							processing.line(x-r,y,x+r,y);
							processing.line(x+r,y,x,y+r);
						}else if(words[i].parsedForm.indexOf("Aor") > -1){ //geniş zaman
							processing.ellipseMode(processing.CENTER);
							processing.ellipse(x,y,3*r/2,3*r/2);
						}else
							processing.ellipse(x,y,r/3,r/3);
						
						if(processing.mouseX<x+r && processing.mouseX>x-r && processing.mouseY<y+r&& processing.mouseY>y-r){
							processing.textSize(13);
							processing.text(words[i].text,textX,textY);
							//text(words.get(i).getParsedForm(),textX,textY+(int) map(height,0,1080,0,40));
						}	
						
						x+=intervalX;
						if(i+1!=words.length && words[i+1].workLineID !=words[i].workLineID){
							y += intervalY;
							if(flag==1){
								x = processing.map(width,0,1920,0,1000);
							}
							else 
								x = processing.map(width,0,1920,0,600);
							//System.out.println();
						}
						//text(words.get(i).getText(),100,(i+1)*10);
					}
				}
			};

			function myStroke(i){
				if (words[i].parsedForm.indexOf("A1sg") > -1 ||words[i].parsedForm.indexOf("P1sg") > -1){// ben-kırmızı
					processing.fill(255,0,0);
					processing.stroke(255,0,0);			
				}else if (words[i].parsedForm.indexOf("A2sg") > -1 ||words[i].parsedForm.indexOf("P2sg") > -1){// sen-sarı
					processing.fill(255,220,0);
					processing.stroke(255,220,0);
				}else if (words[i].parsedForm.indexOf("A1pl") > -1 ||words[i].parsedForm.indexOf("P1pl") > -1){// biz-cyan
					processing.fill(0,240,255);
					processing.stroke(0,240,255);
				}else if (words[i].parsedForm.indexOf("A2pl") > -1 ||words[i].parsedForm.indexOf("P2pl") > -1){// siz-mavi
					processing.fill(0,0,255);
					processing.stroke(0,0,255);
				}else if (words[i].parsedForm.indexOf("A3pl") > -1 ||words[i].parsedForm.indexOf("P3pl") > -1){// onlar-mor
					processing.fill(255,0,255);
					processing.stroke(255,0,255);
				}else if (words[i].parsedForm.indexOf("A3sg")> -1 ||words[i].parsedForm.indexOf("P3sg") > -1){// o-yeşil
					processing.fill(0,240,0);
					processing.stroke(0,240,0);
				}
				else{
					processing.fill(0);
					processing.stroke(0);
				}
			}	

				
		}

			killPrevGorsels();
		
			var canvasGorselSonuc = document.getElementById("canvasGorselSonuc");
			// attaching the sketchProc function to the canvas
			var processingInstance10 = new Processing(canvasGorselSonuc, sketchKipveKisiGorselResult);

			arrayGorsels.push(processingInstance10);

			$("#canvasGorselSonuc").css("width","100%");
		//processingInstance2.noStroke();
	}

	

	$scope.sketchSifatGorselSonucCanvas = function (words,worklines,height,width){
	
		var hs1; //scroll bar is defined here because it should be global for updating its position in mouse wheel event. 

		function sketchSifatGorselResult(processing) {
			
			var parts = 30;
			var numOfWordsToShowInInterval = 30; //number of words to show in the screen.

			processing.setup = function(){
				
				processing.size (width, height);		
				//processing.frameRate(10);  	
				//processing.noLoop();

				var margin = processing.map(height,0,1080,0,60);

				var slider_height = (height-margin)/parts; // to determine slider's height. 

				var bar_width = 16;

				hs1 = new HScrollbar(width-bar_width, 0, bar_width, slider_height, bar_width,height,this);
			};

			// Override draw function, by default it will be called 60 times per second
			processing.draw = function() {


				processing.background(255);
				processing.stroke(0);
				processing.fill(0);
				
				//processing.translate(processing.map(width,0,870,0,200),0)
				var leftShift = processing.map(width,0,870,0,200);
				var x= processing.map(width,0,1920,0,100) +leftShift*0.8 ;
				var y= processing.map(height,0,1080,0,30);
				var margin = processing.map(height,0,1080,0,60);
				var visualHeight = height - margin;
				var intervalY= visualHeight/parts; //(worklines.length-1);
				var intervalX= processing.map(width,0,1920,0,40);
				var r= processing.map(height,0,1080,0,2);
				var textY= processing.map(height,0,1080,0,80);
				var textX= processing.map(width,0,1920,0,430) + leftShift;
				var textY2= processing.map(height,0,1080,0,80);
				var textX2= processing.map(width,0,1920,0,680) + leftShift;

				//update and display scrollbar
				hs1.update();
				hs1.display();


				processing.textSize( processing.map(width,0,1920,0,25));
				processing.fill(0,0,200);
				processing.text("Seçili Şiir: "+worklines[0].line, processing.map(width,0,1920,0,360)+leftShift,textY- processing.map(height,0,1080,0,60));
				processing.text("Şiirde Geçen Sıfatlar:", processing.map(width,0,1920,0,400)+leftShift,textY-processing.map(height,0,500,0,10));
				processing.text("Diğer Sözcükler:", processing.map(width,0,1920,0,650)+leftShift,textY-processing.map(height,0,500,0,10));
				processing.textSize( processing.map(width,0,1920,0,20));
				processing.fill(0);

				// get new starting index of words to show,
				// by getting the position value of scrollbar which is between 0 and 1
				var startIndex = parseInt(words.length * hs1.getPos());

				for(var i=startIndex; i<Math.min(startIndex + numOfWordsToShowInInterval, words.length) ;i++){

					if(processing.mouseX<x+4*r && processing.mouseX>x-(4*r) 
						&& processing.mouseY<y+(4*r) && processing.mouseY>y-(4*r)){
						processing.fill(250,0,0);
					}	
					if(isAdj(i)){	
						if(textY>height-margin/2){
							textY = processing.map(height,0,1080,0,100);
							textX += processing.map(width,0,1920,0,100);
						}
						textY += processing.map(height,0,1080,0,20);
						processing.quad(x-(4*r), y,x, y-(4*r), x+(4*r), y,x,y+(4*r));
						processing.text(words[i].text,textX,textY);		
					}
					else{ 
						if(textY2>height-margin/2){
							textY2 = processing.map(height,0,1080,0,100);
							textX2 += processing.map(width,0,1920,0,200) ;
						}
						processing.ellipse(x,y,r,r);
						if(words[i].workLineID != worklines[0].lineID){
							textY2 += processing.map(height,0,1080,0,20);
							processing.text(words[i].text,textX2,textY2);
						}
					}
				
					processing.fill(0);
					x += intervalX;
					if(i+1!=words.length && words[i+1].workLineID != words[i].workLineID){
						y+=intervalY;
						x= processing.map(width,0,1920,0,100) +leftShift*0.8;
					}
					//text(words.get(i).getText(),100,(i+1)*10);	
				}		


			};

			function isAdj(i){
				if (words[i].parsedForm.indexOf("Adj") > -1){ 
					return true;
				}
				else return false;	
			}

		}

		killPrevGorsels();
	
		var canvasGorselSonuc = document.getElementById("canvasGorselSonuc");
		// attaching the sketchProc function to the canvas
		var processingInstance9 = new Processing(canvasGorselSonuc, sketchSifatGorselResult);

		arrayGorsels.push(processingInstance9);

		$("#canvasGorselSonuc").css("width","100%");

		addMouseUpDownWheelEventsToCanvas(canvasGorselSonuc,processingInstance9,hs1);

	}

	

	$scope.sketchGorselSonucCanvas = function (words,height,width){
	

		function sketchGorselResult(processing) {
			processing.setup = function(){
				
				processing.size (width, height);		
				processing.frameRate(10);  	
				//processing.noLoop();
			};

			// Override draw function, by default it will be called 60 times per second
			processing.draw = function() {

				processing.background(255);
				processing.stroke(0);
				var r= processing.map(height,0,1080,0,300);
				var shift = processing.map(width,0,1920,0,50);
				var centerX = width/2 - 7*shift;
				var centerY  = height/2-shift;
				var interval = 2*processing.PI/words.length;
				var textX=centerX-r;
				var textY=processing.map(height,0,1080,0,200);
				
				processing.ellipseMode(processing.CENTER);
				processing.ellipse(centerX,centerY,2*r,2*r);
				
				var x=0;
				var y=0;
				var xR=0;
				var yR=0;
				
				var preline=words[0].workLineID;
			
				processing.textSize(processing.map(height,0,1080,0,16));
				
				for(var i=0;i<words.length;i++){	
					x=centerX-r*processing.cos(i*interval); //herbir kelime için x ve y hesapla -> circle çiz
					y=centerY-r*processing.sin(i*interval);
					
					shift = processing.map(width,0,1920,0, (2.5*words[i].wordStart));
					textX =  (1.8*centerX+shift); 
					
					if(words[i].workLineID!=preline){
						textY+=processing.map(height,0,1080,0,15);
					}	
					
					preline=words[i].workLineID;
					processing.fill(0);
					
					processing.text(words[i].text,textX,textY);
					processing.noFill();
					
					for(var j=0;j<words.length;j++){//şiirdeki kendisi dışında herbir kelime için parsed formu control et
						
						if(words[i].disambiguated == words[j].disambiguated && j!=i){
							
							xR=centerX-r*processing.cos(j*interval);
							yR=centerY-r*processing.sin(j*interval);
							
							if((processing.mouseX<x+5 && processing.mouseX>x-5 && processing.mouseY<y+5 && 
								processing.mouseY>y-5) || (processing.mouseX<xR+5 && processing.mouseX>xR-5 
								&& processing.mouseY<yR+5 && processing.mouseY>yR-5)){
								processing.stroke(250,0,0);
								processing.fill(250,0,0);
								processing.text(words[i].text,textX,textY);
								processing.ellipse(x,y,processing.map(height,0,1080,0,10),processing.map(height,0,1080,0,10));
								processing.ellipse(xR,yR,processing.map(height,0,1080,0,10),processing.map(height,0,1080,0,10));
								processing.noFill();
							}else{
								processing.fill(0);
								processing.ellipse(x,y,processing.map(height,0,1080,0,10),processing.map(height,0,1080,0,10));
								processing.ellipse(xR,yR,processing.map(height,0,1080,0,10),processing.map(height,0,1080,0,10));
								processing.noFill();
							}
							processing.strokeWeight(0.5);
							processing.curve(x+xR,y+yR,x,y,xR,yR,x-xR,y-yR);
							
							processing.stroke(0);
						}	
					}
				}

			};
		}

		
			killPrevGorsels();

			var canvasGorselSonuc = document.getElementById("canvasGorselSonuc");
			// attaching the sketchProc function to the canvas
			var processingInstance8 = new Processing(canvasGorselSonuc, sketchGorselResult);

			arrayGorsels.push(processingInstance8);

			$("#canvasGorselSonuc").css("width","100%");
		//processingInstance2.noStroke();
	}

	$scope.sketchFreqBookCanvas = function (bookFreqList,canvasHeight,canvasWidth){
	

		function getMaxFreqMinMaxBookId(){
			var maxFreq = 0;
			var minBookId = 10000;
			var maxBookId = 0;
			for (var i = 0; i < bookFreqList.length; i++) {
				if (bookFreqList[i].frequency > maxFreq)
					maxFreq = bookFreqList[i].frequency;
				
				if(bookFreqList[i].bookId < minBookId)
					minBookId = bookFreqList[i].bookId;
				
				if(bookFreqList[i].bookId > maxBookId)
					maxBookId = bookFreqList[i].bookId;
			}
			return [maxFreq,minBookId,maxBookId];	 
		}


		function sketchProcFreqOverBook(processing) {
			processing.setup = function(){
				
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

				var maxFreqMinMaxBookId = getMaxFreqMinMaxBookId();
				var textSize = processing.map(width, 0, 3200,2, 30);
				margin = Math.round( processing.map(height, 0, 2000, 0, 400) );
				rectX = processing.map(width, 0, 3200, 0, 10);

				for (var i = 0; i < bookFreqList.length; i++) {
					var currentBook= bookFreqList[i];

					var xPosDataOne = processing.map(i, 1, bookFreqList.length, 0 + margin, width - margin);
					var yPosDataOne = processing.map(currentBook.frequency, 0, maxFreqMinMaxBookId[0], 0, height - Math.round(1.3 * margin));

					var bookName = currentBook.bookName;						

					
					processing.rectMode(processing.CORNER);

					if($scope.highlightFrequencies == true && $scope.highlightSiirBook != 0  
						&& $scope.highlightSiirBook == currentBook.bookId){
						processing.fill(222,0,0)//(55,112,130);
						processing.rect(xPosDataOne, height - margin, rectX * 2, -1 * yPosDataOne);
					}else if($scope.highlightFrequencies == false && $scope.seciliSiirBook != 0  && $scope.seciliSiirBook == currentBook.bookId){
						processing.fill(222,0,0)//(55,112,130);
						processing.rect(xPosDataOne, height - margin, rectX * 2, -1 * yPosDataOne);
					}else{
						processing.fill(104, 58, 58);
						processing.rect(xPosDataOne, height - margin, rectX, -1 * yPosDataOne);
					}

					
				
					processing.textAlign(processing.LEFT, processing.BOTTOM);
					processing.textSize(textSize);
					processing.text(currentBook.frequency,xPosDataOne,height-margin-yPosDataOne);

					processing.fill(50);
					processing.pushMatrix() ;
					processing.translate(xPosDataOne,height - margin);
					processing.rotate(-1*processing.PI/2);
					processing.textSize(textSize);
					processing.textAlign(processing.RIGHT, processing.UP);
					processing.textLeading(textSize); 
					processing.text(bookName,-5,rectX*1.5);
					processing.popMatrix() ;
				}

			};

		}

		killPrevGorsels();
		
		var canvasGorselSonuc = document.getElementById("canvasGorselSonuc");
		// attaching the sketchProc function to the canvas
		var processingInstance6 = new Processing(canvasGorselSonuc, sketchProcFreqOverBook);

		arrayGorsels.push(processingInstance6);

		$("#canvasGorselSonuc").css("width","100%");

	}

	$scope.sketchFreqPlaceCanvas = function (placeFreqList,canvasHeight,canvasWidth){
	

		function getMaxFreq(){
			var maxFreq = 0;
			for (var i = 0; i < placeFreqList.length; i++) {
				if (placeFreqList[i].frequency > maxFreq && placeFreqList[i].place.trim() != "")
					maxFreq = placeFreqList[i].frequency;

			}
			return maxFreq;	 
		}

		function sketchProcFreqOverPlace(processing) {
			processing.setup = function(){
				
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

				//var maxFreqMinMaxYear = getMaxFreqMinMaxYear();
				var textSize = processing.map(width, 0, 3200,2, 30);
				margin = Math.round( processing.map(height, 0, 2000, 0, 400) );
				rectX = processing.map(width, 0, 3200, 0, 10);

				for (var i = 0; i < placeFreqList.length; i++) {
					var currentPlace= placeFreqList[i];

					if (currentPlace.place.trim() == "") {
						processing.fill(50);
						processing.textSize(textSize);
						processing.textAlign(processing.LEFT, processing.TOP);
						processing.textLeading(textSize); 
						processing.text("Aramanın yazıldığı yeri bulunamayan tekrar sayısı : " + currentPlace.frequency,0,0);
						continue;
					}

					var xPosDataOne = processing.map(i, 1, placeFreqList.length, 0 + margin, width - margin);
					var yPosDataOne = processing.map(currentPlace.frequency, 0, getMaxFreq(), 0, height - Math.round(1.3 * margin));

					var place = currentPlace.place;						

					
					processing.rectMode(processing.CORNER);

					if($scope.highlightFrequencies == true && $scope.highlightSiirPlace != 0  
						&& $scope.highlightSiirPlace.toLowerCase() == currentPlace.place.toLowerCase()){
						processing.fill(222,0,0)//(55,112,130);
						processing.rect(xPosDataOne, height - margin, rectX * 2, -1 * yPosDataOne);
					}else if($scope.highlightFrequencies == false && $scope.seciliSiirPlace != ""  
						&& $scope.seciliSiirPlace.toLowerCase() == currentPlace.place.toLowerCase()){
						processing.fill(222,0,0)//(55,112,130);
						processing.rect(xPosDataOne, height - margin, rectX * 2, -1 * yPosDataOne);
					}else{
						processing.fill(104, 58, 58);
						processing.rect(xPosDataOne, height - margin, rectX, -1 * yPosDataOne);
					}

					
				
					processing.textAlign(processing.LEFT, processing.BOTTOM);
					processing.textSize(textSize);
					processing.text(currentPlace.frequency,xPosDataOne,height-margin-yPosDataOne);

					processing.fill(50);
					processing.pushMatrix() ;
					processing.translate(xPosDataOne,height - margin);
					processing.rotate(-1*processing.PI/2);
					processing.textSize(textSize);
					processing.textAlign(processing.RIGHT, processing.UP);
					processing.textLeading(textSize); 
					processing.text(place,-5,rectX*1.5);
					processing.popMatrix() ;
				}

			};
		}

		killPrevGorsels();
		
		var canvasGorselSonuc = document.getElementById("canvasGorselSonuc");
		// attaching the sketchProc function to the canvas
		var processingInstance5 = new Processing(canvasGorselSonuc, sketchProcFreqOverPlace);

		arrayGorsels.push(processingInstance5);

		$("#canvasGorselSonuc").css("width","100%");
		
	}


	$scope.sketchFreqYearCanvas = function (yearFreqList,canvasHeight,canvasWidth){
	

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
				
				processing.size(canvasWidth,canvasHeight);
				//processing.frameRate(10);
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

					
					processing.rectMode(processing.CORNER);

					if($scope.highlightFrequencies == true && $scope.highlightSiirYear != 0  
						&& $scope.highlightSiirYear == currentYear.year){
						processing.fill(222,0,0)//(55,112,130);
						processing.rect(xPosDataOne, height - margin, rectX * 2, -1 * yPosDataOne);
					}else if($scope.highlightFrequencies == false && $scope.seciliSiirYear > 0  && $scope.seciliSiirYear == currentYear.year){
						processing.fill(222,0,0)//(55,112,130);
						processing.rect(xPosDataOne, height - margin, rectX * 2, -1 * yPosDataOne);
					}else{
						processing.fill(104, 58, 58);
						processing.rect(xPosDataOne, height - margin, rectX, -1 * yPosDataOne);
					}

					
				
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



		killPrevGorsels();
		
		var canvasGorselSonuc = document.getElementById("canvasGorselSonuc");
		// attaching the sketchProc function to the canvas
		var processingInstance3 = new Processing(canvasGorselSonuc, sketchProc2);

		arrayGorsels.push(processingInstance3);

		$("#canvasGorselSonuc").css("width","100%");
	}

	$scope.getWordCloud = function(){
 		loadProgress();
 		BaseAPI.callServlet('WordCloudServlet',{siirId:$scope.seciliSiir+""}).then(function(responseText) {
 			$scope.gorselSonucShow = true;
 			$scope.showImgResult = true;
 
             $('#imgGorselResult').attr('src',$scope.baseImagePathUrl+responseText); 
             $('#imgGorselResult')[0].src =$scope.baseImagePathUrl+responseText; 
         	 $("#imgGorselResult").show();
 
 			loadEnded();
 			
         });
 	}


	$scope.getGorsel = function(type){
		if($scope.seciliSiir <= 0 ){
			bootbox.alert("Lütfen bir şiir seçiniz");
		}else{
			//$scope.$parent.showAramaSonuc = true;
			//$scope.$parent.showSection(3);
			$scope.showSiir = false;
			$scope.gorselSonucShow = false;
			$scope.showGorselGeriDon = false;

			$scope.showImgResult = false;

			if(type == 1){
				$scope.siiriOku();
			}else if(type == 2){
				$scope.getWordCloud();
			}else if(type == 3){
				$scope.getRandomLines();
			}else if(type == 4){
				$scope.getBarkodOfSiir();
			}else if(type == 5){
				$scope.getTekrarGorselOfSiir();
			}else if(type == 6){
				$scope.getSifatGorselOfSiir();
			}else if(type == 7){
				$scope.getKisveKisiGorselOfSiiir();
			}else if(type == 8){
				$scope.getRoundedGorselOfSiiir();
			}else if(type == 9){
				$scope.getfreqOverYearGorselOfSiiir();
			}else if(type == 10){
				$scope.getfreqOverBookGorselOfSiiir();
			}else if(type == 11){
				$scope.getfreqOverPlaceGorselOfSiiir();
			}else if(type == 12){
				$scope.getBubbleGorselOfSiiir();
			}else{
				//$scope.$parent.showAramaSonuc = false;
				//$scope.$parent.showSection(2);
			}
			$scope.siiriOku();
		}
	}

	$scope.getfreqOverPlaceGorselOfSiiir = function(){
		BaseAPI.callServlet('getWordFrequencyOverPlaceData',{searchText : $scope.searchTextM}).then(function(response){
    	 	$scope.placeFreqList = response;
    	 	$scope.gorselSonucShow = true;
			$scope.drawfreqOverPlaceGorsel();
    	});
		
	}

	$scope.getfreqOverYearGorselOfSiiir = function(){
		BaseAPI.callServlet('getWordFrequencyData',{searchText : $scope.searchTextM}).then(function(response){
    	 	$scope.yearFreqList = response;
    	 	$scope.gorselSonucShow = true;
			$scope.drawfreqOverYearGorsel();
    	});
		
	}

	$scope.getfreqOverBookGorselOfSiiir = function(){
		BaseAPI.callServlet('getWordFrequencyOverBookData',{searchText : $scope.searchTextM}).then(function(response){
    	 	$scope.bookFreqList = response;
    	 	$scope.gorselSonucShow = true;
			$scope.drawfreqOverBookGorsel();
    	});
		
	}

	$scope.getBubbleGorselOfSiiir = function(){
		loadProgress();
		BaseAPI.callServlet('getWorkLinesOfWork',{siirId:$scope.seciliSiir+""}).then(function(siirLines) {
            
			BaseAPI.callServlet('getWordsOfWorkWithParsedForm',{siirId:$scope.seciliSiir+""}).then(function(parsedWords) {
            
            		$scope.gorselSonucShow = true;

            		$scope.drawBubbleGorsel(siirLines,parsedWords);

					loadEnded();
	        });
        });
        
        
	}

	$scope.getBarkodOfSiir = function(){
		loadProgress();
		BaseAPI.callServlet('getWorkLinesOfWork',{siirId:$scope.seciliSiir+""}).then(function(siirLines) {
            
			BaseAPI.callServlet('getWordsOfWorkWithParsedForm',{siirId:$scope.seciliSiir+""}).then(function(parsedWords) {
            
				BaseAPI.callServlet('getAffectiveResultsOfWork',{siirId:$scope.seciliSiir+""}).then(function(affectiveResults) {
            		$scope.gorselSonucShow = true;

            		$scope.drawBarkodeGorsel(siirLines,parsedWords,affectiveResults);

					loadEnded();
		        });
	        });
        });
        
        
	}

	$scope.getRoundedGorselOfSiiir = function(){
		
		BaseAPI.callServlet('getWordsOfWorkWithParsedForm',{siirId:$scope.seciliSiir+""}).then(function(response) {
			

			BaseAPI.callServlet('getWorkLinesOfWork',{siirId:$scope.seciliSiir+""}).then(function(siirLines) {
            	$scope.gorselSonucShow = true;

				$scope.drawRoundedGorsel(response,siirLines);

				loadEnded();
	        });

        });
	}

	$scope.getKisveKisiGorselOfSiiir = function(){
		
		BaseAPI.callServlet('getWordsOfWorkWithParsedForm',{siirId:$scope.seciliSiir+""}).then(function(response) {
			
			console.log(response);

			BaseAPI.callServlet('getWorkLinesOfWork',{siirId:$scope.seciliSiir+""}).then(function(siirLines) {
            	$scope.gorselSonucShow = true;

				$scope.drawKipveKisiGorsel(response,siirLines);

				loadEnded();
	        });

        });
	}

	$scope.getSifatGorselOfSiir = function(){
		
		BaseAPI.callServlet('getWordsOfWorkWithParsedForm',{siirId:$scope.seciliSiir+""}).then(function(response) {
			

			BaseAPI.callServlet('getWorkLinesOfWork',{siirId:$scope.seciliSiir+""}).then(function(siirLines) {
            	$scope.gorselSonucShow = true;

				$scope.drawSifatGorsel(response,siirLines);

				loadEnded();
	        });

			

        });
	}

	$scope.getTekrarGorselOfSiir = function(){
		
		BaseAPI.callServlet('getWordsOfWorkWithParsedForm',{siirId:$scope.seciliSiir+""}).then(function(response) {
			$scope.gorselSonucShow = true;

			$scope.drawTekrarGorsel(response);

        }, function(reason) {
		  // rejection
		  $scope.gorselSonucShow = false; 
		  $scope.showGorselGeriDon=true;
		});
	}

	$scope.siiriOku = function(){
		//loadProgress();
		
		BaseAPI.callServlet('getSiir',{siirId:$scope.seciliSiir+""}).then(function(response) {
			$scope.showSiir = true;
			$scope.gorselSonucShow = true;
			$('#siirinKendi').html(response); 
			//loadEnded();
        });
	}

	$scope.getSiir = function (work) {
		loadProgress();
		BaseAPI.callServlet('getSiir',{siirId:work.workID+""}).then(function(response) {
			$scope.showSiir = true;
			$('#siirinKendi').html(response); 
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