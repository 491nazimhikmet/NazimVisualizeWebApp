app.controller('allWorkTensesCTRL', ['$scope','$http','BaseAPI',function($scope,$http,BaseAPI) {

		/** oluşturulan gorselin kaydetme kısmı başlangıç **/
	function download() {
	    var dt = document.getElementById("canvasTensesDenemem").toDataURL();
	    this.href = dt;
	};

	//downloadLnk2.addEventListener('click', download, false);

	downloadLnk2.addEventListener("click", function() {
		var canvas = document.getElementById('canvasTensesDenemem');

		// only jpeg is supported by jsPDF
		var imgData = canvas.toDataURL("image/jpeg", 1.0);
		//window.location = imgData;
		var pdf = new jsPDF('p','pt','a1');

		pdf.addImage(imgData, 'JPEG', 0,0,1350, 750);
		var downloadLnk2 = document.getElementById('downloadLnk2');

		pdf.save("downloadOverYear.pdf");
	}, false);

			/** oluşturulan gorselin kaydetme kısmı başlangıç **/
	function downloadPlace() {
	    var dt = document.getElementById("canvasTensesDenememPlace").toDataURL();
	    this.href = dt;
	};

	//downloadLnk3.addEventListener('click', downloadPlace, false);


	downloadLnk3.addEventListener("click", function() {
		var canvas = document.getElementById('canvasTensesDenememPlace');

		// only jpeg is supported by jsPDF
		var imgData = canvas.toDataURL("image/jpeg", 1.0);
		//window.location = imgData;
		var pdf = new jsPDF('p','pt','a1');

		pdf.addImage(imgData, 'JPEG', 0,0,1350, 750);
		var downloadLnk3 = document.getElementById('downloadLnk3');

		pdf.save("downloadOverPlace.pdf");
	}, false);

	downloadLnk4.addEventListener("click", function() {
		var canvas = document.getElementById('canvasCompareBarkod');

		// only jpeg is supported by jsPDF
		var imgData = canvas.toDataURL("image/jpeg", 1.0);
		//window.location = imgData;
		var pdf = new jsPDF('p','pt','a1');

		pdf.addImage(imgData, 'JPEG', 0,0,1350, 750);
		var downloadLnk4 = document.getElementById('downloadLnk4');

		pdf.save("downloadBarkodCompare.pdf");
	}, false);

	$scope.getRoundedGorselOfSiiir = function(){
		loadProgress();
		BaseAPI.callServlet('getTensesAllWorks',{siirId:$scope.seciliSiir+""}).then(function(response) {
			
			$scope.drawRoundedGorsel(response);

			loadEnded();

        });
	}

	$scope.getYereGoreOfSiiir = function(){
		loadProgress();
		BaseAPI.callServlet('getTensesAllWorksForPlaces',{}).then(function(response) {
			
			$scope.drawPlaceGorsel(response);

			loadEnded();

        });
	}

	
	$scope.getBarkodCompare = function(){
		loadProgress();
		var seciliSiir1 =6735;//6161;
		var seciliSiir2 = 6681;//6593;//6175;

		BaseAPI.callServlet('getWorkLinesOfWork',{siirId:seciliSiir1+""}).then(function(siirLines1) {
            
			BaseAPI.callServlet('getWordsOfWorkWithParsedForm',{siirId:seciliSiir1+""}).then(function(parsedWords1) {
            
				BaseAPI.callServlet('getAffectiveResultsOfWork',{siirId:seciliSiir1+""}).then(function(affectiveResults1) {

					BaseAPI.callServlet('getWorkLinesOfWork',{siirId:seciliSiir2+""}).then(function(siirLines2) {
            
						BaseAPI.callServlet('getWordsOfWorkWithParsedForm',{siirId:seciliSiir2+""}).then(function(parsedWords2) {
			            
							BaseAPI.callServlet('getAffectiveResultsOfWork',{siirId:seciliSiir2+""}).then(function(affectiveResults2) {

			            		$scope.drawBarkodeGorsel(siirLines1,parsedWords1,affectiveResults1,siirLines2,parsedWords2,affectiveResults2);

								loadEnded();
					        });
				        });
			        });
		        });
	        });
        });
	}
	
	$scope.drawRoundedGorsel = function(works){
			
		var canvasHeight =4000;// $("#canvasTensesDenemem").height();
		var canvasWidth = 6400;//$("#canvasTensesDenemem").width();

		$scope.sketchRoundedGorselSonucCanvas(works,canvasHeight,canvasWidth);

	}

	$scope.drawPlaceGorsel = function(works){
			
		var canvasHeight =4000;// $("#canvasTensesDenemem").height();
		var canvasWidth = 6400;//$("#canvasTensesDenemem").width();

		$scope.sketchPlaceTensesSonucCanvas(works,canvasHeight,canvasWidth);

	}

	$scope.drawBarkodeGorsel = function(siirLines1,parsedWords1,affectiveResults1,siirLines2,parsedWords2,affectiveResults2){
			
		var canvasHeight =2000;// $("#canvasTensesDenemem").height();
		var canvasWidth = 3200;//$("#canvasTensesDenemem").width();

		$scope.sketchBarkodeCompareGorsel(siirLines1,parsedWords1,affectiveResults1,siirLines2,parsedWords2,affectiveResults2,canvasHeight,canvasWidth);

	}

	$scope.sketchBarkodeCompareGorsel = function (satirlar1,words1,affectiveResults1,satirlar2,words2,affectiveResults2,height,width){
	

		function sketchBarkodGorselResult(processing) {
			
			var benList = [];
			var bizList = []; 

			var defaultColour = 150;

			processing.setup = function(){
				processing.size (width, height);		
				//processing.frameRate(1);
				processing.noLoop();  	
				processing.stroke(255);

			};

			// Override draw function, by default it will be called 60 times per second
			processing.draw = function() {				
				processing.background(255);
		
				var textSize = processing.map(width, 0, 3200,10, 40);
				var lengthOfPoem = Math.max(satirlar1.length,satirlar2.length);
				
				var topMargin = processing.map(height,0,1080,0,200);
				var barcodeLength = width-topMargin*1.1;
				
				var interY = barcodeLength/lengthOfPoem;
				
				var textX = width/8;
				var textY = processing.map(height,0,1080,0,20);
				
				var shift = processing.map(height,0,1080,0,20);
				
			
				var x = 0;
				var y = height/8;


				processing.fill(0);
				processing.textSize(textSize/1.2);
				processing.text("Her bir çizgi şiirdeki bir satırı ifade eder.",textX,textY);
				processing.text("Etken fiil içeren satırlar yeşil, edilgen fiil içeren satırlar kırmızı ile renklendirilmiştir.",textX,textY+shift);
				processing.text("Satırda geçen fillerin çekimlerine göre sırasıyla: ben/kırmızı, sen/sarı, o/yeşil, biz/cyan, siz/mavi , onlar/mor ile renklendirilmiştir.",textX,textY+2*shift);
				processing.text("Satırlar olumluluk/valence, uyarılma/arousal ve baskınlık/dominance değerlerine göre yüksek değerliler yeşil, düşük değerliler kırmızı ile renklendirilmiştir.",textX,textY+3*shift);
				processing.text("Birinci Şiir : " + satirlar1[0].line,textX,textY+4*shift);
				processing.text("İkinci Şiir : " + satirlar2[0].line,textX,textY+5*shift);
			

				processing.fill(0,0,200);
				processing.stroke(0,0,200);
				
				processing.textSize(textSize);
				processing.text("satır uzunlukları:",x,y*1.5);
				processing.text("etken-edilgen :",x,y*2.5);
				processing.text("kişi çekimi :",x,y*3.5);
				processing.text("olumluluk/valence:",x,y*4.5);
				processing.text("uyarılma/arousal:",x,y*5.5);
				processing.text("baskınlık/dominance:",x,y*6.5);
				
				processing.strokeWeight(2);

				

				var mouseMargin = processing.map(height,0,1080,0,5);

				x = topMargin + 2*interY;

				for(var i=0;i<satirlar1.length;i++){
					
					var satirLength = Math.abs(satirlar1[i].lineFinish - satirlar1[i].lineStart)/2 ;
					processing.stroke(defaultColour);
					
					processing.line(x,y,x,Math.min(y+satirLength,y*1.4));
					x += interY;

				}

				x = topMargin + 2*interY;

				for(var i=0;i<satirlar2.length;i++){
					
					var satirLength = Math.abs(satirlar2[i].lineFinish - satirlar2[i].lineStart)/2 ;
					processing.stroke(defaultColour);
					

					processing.line(x,y*1.5,x,Math.min(y*1.5+satirLength,y*1.9));
					x += interY;

				}

				//seperator lines
				processing.line(0,y*0.95,width,y*0.95);
				processing.line(0,y*1.95,width,y*1.95);
				processing.line(0,y*2.95,width,y*2.95);
				processing.line(0,y*3.95,width,y*3.95);
				processing.line(0,y*4.95,width,y*4.95);
				processing.line(0,y*5.95,width,y*5.95);

				x = topMargin + 2*interY;
				for(var i=0;i<satirlar1.length;i++){ 	//etken edilgen:
					for(var j=0;j<words1.length;j++){
						if(satirlar1[i].lineID == words1[j].workLineID){ // bulunduğumuz satırdaki sözcüklere göre satırı renklendir 
							if(etken(words1,j)){
								processing.stroke(0,220,40);//yeşil
							}
							else if(edilgen(words1,j)){
								processing.stroke(220,0,0);//kırmızı
							}
							else
								processing.stroke(defaultColour);
						}
					}
					processing.line(x,y*2,x,y*2.4);	
					x += interY;
				}

				x = topMargin + 2*interY;
				for(var i=0;i<satirlar2.length;i++){ 	//etken edilgen:
					for(var j=0;j<words2.length;j++){
						if(satirlar2[i].lineID == words2[j].workLineID){ // bulunduğumuz satırdaki sözcüklere göre satırı renklendir 
							if(etken(words2,j)){
								processing.stroke(0,220,40);//yeşil
							}
							else if(edilgen(words2,j)){
								processing.stroke(220,0,0);//kırmızı
							}
							else
								processing.stroke(defaultColour);
						}
					}
					processing.line(x,y*2.5,x,y*2.9);	
					x += interY;
				}

				x = topMargin + 2*interY;

				
				for(var i=0;i<satirlar1.length;i++){	//ben ve biz ayrımı:
					for(var j=0;j<words1.length;j++){
						if(satirlar1[i].lineID == words1[j].workLineID){
							strokeSubject(words1,j);
						}
					}
					processing.line(x,y*3,x,y*3.4);	
					x += interY;
				}
				
				x = topMargin + 2*interY;

				for(var i=0;i<satirlar2.length;i++){	//ben ve biz ayrımı:
					for(var j=0;j<words2.length;j++){
						if(satirlar2[i].lineID == words2[j].workLineID){
							strokeSubject(words2,j);
						}
					}
					processing.line(x,y*3.5,x,y*3.9);	
					x += interY;
				}
				
				x = topMargin + 2*interY;

				for(var i=0;i<satirlar1.length;i++){ //valence-arousal-dominance 1
			
					strokeValence(affectiveResults1,i);
					processing.line(x,y*4,x,y*4.4);
					strokeArousal(affectiveResults1,i);
					processing.line(x,y*5,x,y*5.4);
					strokeDominance(affectiveResults1,i);
					processing.line(x,y*6,x,y*6.4);
					
					x += interY;
				}

				x = topMargin + 2*interY;

				for(var i=0;i<satirlar2.length;i++){ //valence-arousal-dominance 2
			
					strokeValence(affectiveResults2,i);
					processing.line(x,y*4.5,x,y*4.9);
					strokeArousal(affectiveResults2,i);
					processing.line(x,y*5.5,x,y*5.9);
					strokeDominance(affectiveResults2,i);
					processing.line(x,y*6.5,x,y*6.9);
					
					x += interY;
				}
				
			};

			function edilgen(words,j){
				if (words[j].parsedForm.indexOf("Verb") > -1 && words[j].parsedForm.indexOf("Pass") > -1) return true;
				else return false;		
			}
			function etken(words,j){
				if (words[j].parsedForm.indexOf("Verb") > -1 && !(words[j].parsedForm.indexOf("Pass") > -1)) return true;
				else return false;	
			}
			
			function strokeValence(affectiveResults,i){
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

			function strokeArousal(affectiveResults,i){
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

			function strokeDominance(affectiveResults,i){
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

			function strokeSubject(words,i){
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
		
			var canvasCompareBarkod = document.getElementById("canvasCompareBarkod");
			// attaching the sketchProc function to the canvas
			var processingInstance13 = new Processing(canvasCompareBarkod, sketchBarkodGorselResult);

			$("#canvasCompareBarkod").css("width","100%");
		//processingInstance2.noStroke();
	}

	$scope.sketchRoundedGorselSonucCanvas = function (works,height,width){
	

		function sketchRoundedGorselResult(processing) {
			
			processing.setup = function(){
				
				processing.size (width, height);		
				processing.frameRate(1);  	
				//processing.noLoop();
				
			};

			// Override draw function, by default it will be called 60 times per second
			processing.draw = function() {				
				processing.background(245);
				processing.stroke(0);

				var width = processing.width;
				var height = processing.height;

				var marginX = processing.map(width, 0, 3200, 0, 200); //soldan sağa margin
				var marginY = processing.map(height,0,1900,0,100); // aşağıdan yukarı margin
				var textSize = processing.map(width, 0, 3200,10, 40);
				var recSize = processing.map(width, 0, 3200, 1, 20); // karelerin kenarı
				var marginPointer = processing.map(width, 0, 6400, 1, 100); // karelerin kenarı

				var originX = marginX;
				var originY = height-marginY;
				var xLength = width-2*marginX;
				var yLength = height-2*marginY;

				processing.strokeWeight(3);
				processing.line(originX, originY, originX, originY-yLength); //Y axis
				processing.line(originX, originY, originX+xLength, originY); //X Axis
					

				//time seperators
				processing.strokeWeight(2);
				var firstSeperatorY = originY - yLength/3;
				var secondSeperatorY = originY - yLength/3 *2;				

				processing.line(originX, firstSeperatorY, originX+xLength, firstSeperatorY); //X1 Axis
				processing.line(originX, secondSeperatorY, originX+xLength, secondSeperatorY); //X2 Axis

				processing.textSize(textSize);
				
				processing.pushMatrix() ;
				processing.translate(originX-50,secondSeperatorY-yLength/6);
				processing.rotate(-1*processing.PI/2);
				processing.textAlign(processing.RIGHT, processing.UP);
				processing.textLeading(textSize); 
				processing.text("Gelecek Zaman",-recSize/2,recSize);
				processing.popMatrix() ;

				processing.pushMatrix() ;
				processing.translate(originX-50,firstSeperatorY-yLength/6);
				processing.rotate(-1*processing.PI/2);
				processing.textAlign(processing.RIGHT, processing.UP);
				processing.textLeading(textSize); 
				processing.text("Şimdiki Zaman",-recSize/2,recSize);
				processing.popMatrix() ;

				processing.pushMatrix() ;
				processing.translate(originX-50,originY-yLength/6);
				processing.rotate(-1*processing.PI/2);
				processing.textAlign(processing.RIGHT, processing.UP);
				processing.textLeading(textSize); 
				processing.text("Geçmiş Zaman",-recSize/2,recSize);
				processing.popMatrix() ;

				//getPoints of boundaries so that divide works
				var boundaries = getBoundaryValues();

				var c = processing.color(10, 10, 255);  // Define color 'c'
				  // Use color variable 'c' as fill color
				processing.noStroke();  // Don't draw a stroke around shapes

				for (var i = works.length - 1; i >= 0; i--) {
					var currentWork = works[i];
					
					var xPos = recSize + processing.map(currentWork.year, boundaries[1], boundaries[0],originX,originX+xLength);
					var yPos;

					var maxValue = Math.max(currentWork.simdikiCnt,currentWork.gelecekCnt,currentWork.gecmisCnt);

					var sum = currentWork.simdikiCnt + currentWork.gelecekCnt + currentWork.gecmisCnt;
					if(maxValue == currentWork.gelecekCnt){//gelecek zamaan ağırlıklı ise
						yPos = secondSeperatorY - yLength/3 * currentWork.gelecekCnt / sum;
					}else if(maxValue == currentWork.simdikiCnt){//şimdiki zaman ağırlıklı ise
						yPos = firstSeperatorY - yLength/3 * currentWork.gelecekCnt/(sum - currentWork.simdikiCnt);
					}else if(maxValue == currentWork.gecmisCnt){//geçmiş zaman ağırlıklı ise
						yPos = firstSeperatorY + yLength/3 * currentWork.gecmisCnt / sum;
					}

					processing.fill(c);
					processing.rect(xPos-recSize/2,yPos-recSize/2,recSize,recSize);
					
					processing.fill(50, 55, 100);
					processing.textSize(textSize/1.2);
					/*processing.textAlign(processing.CENTER, processing.UP);
					processing.text(currentWork.year, xPos,originY + recSize*2);*/

					processing.pushMatrix() ;
					processing.translate(xPos,height - marginY);
					processing.rotate(-1*processing.PI/2);
					processing.textAlign(processing.RIGHT, processing.UP);
					processing.textLeading(textSize); 
					processing.text(currentWork.year,-recSize/2,recSize);
					processing.popMatrix() ;

					// mouse over yemiyor çünkü biz büyültüüp sonra küçültüyoruz. Bundan ötürü problem var. 
					// eğer width ve height i real olanla verirsek olur
					if(processing.mouseX <= xPos + marginPointer && processing.mouseX >= xPos - marginPointer
						&& processing.mouseY <= yPos + marginPointer && processing.mouseY >= yPos - marginPointer){
						processing.text(currentWork.gelecekCnt+ " , "+currentWork.simdikiCnt+ " , "+currentWork.gecmisCnt, xPos,yPos);
					}
				}


			};

			function getBoundaryValues(){
				var max  = 0;
				var min = 999999;

				for(var i = 0; i<works.length; i++){
					if(works[i].year > max){
						max = works[i].year ;
					}

					if(works[i].year < min){
						min = works[i].year ;
					}
				}

				return [max,min];
			}

				
		}

			var canvasTensesDenemem = document.getElementById("canvasTensesDenemem");
			// attaching the sketchProc function to the canvas
			var processingInstance11 = new Processing(canvasTensesDenemem, sketchRoundedGorselResult);

			$("#canvasTensesDenemem").css("width","100%");
	}


	$scope.sketchPlaceTensesSonucCanvas = function (works,height,width){
	

		function sketchPlaceTenseGorselResult(processing) {
			
			processing.setup = function(){
				
				processing.size (width, height);		
				processing.frameRate(1);  	
				//processing.noLoop();
				
			};

			// Override draw function, by default it will be called 60 times per second
			processing.draw = function() {				
				processing.background(245);
				processing.stroke(0);

				var width = processing.width;
				var height = processing.height;

				var marginX = processing.map(width, 0, 3200, 0, 200); //soldan sağa margin
				var marginY = processing.map(height,0,1900,0,200); // aşağıdan yukarı margin
				var textSize = processing.map(width, 0, 3200,10, 40);
				var recSize = processing.map(width, 0, 3200, 1, 20); // karelerin kenarı
				var marginPointer = processing.map(width, 0, 6400, 1, 100); // karelerin kenarı

				var originX = marginX;
				var originY = height-marginY;
				var xLength = width-2*marginX;
				var yLength = height-2*marginY;

				processing.strokeWeight(3);
				processing.line(originX, originY, originX, originY-yLength); //Y axis
				processing.line(originX, originY, originX+xLength, originY); //X Axis
					

				//time seperators
				processing.strokeWeight(2);
				var firstSeperatorY = originY - yLength/3;
				var secondSeperatorY = originY - yLength/3 *2;				

				processing.line(originX, firstSeperatorY, originX+xLength, firstSeperatorY); //X1 Axis
				processing.line(originX, secondSeperatorY, originX+xLength, secondSeperatorY); //X2 Axis

				processing.textSize(textSize);
				
				processing.pushMatrix() ;
				processing.translate(originX-50,secondSeperatorY-yLength/6);
				processing.rotate(-1*processing.PI/2);
				processing.textAlign(processing.RIGHT, processing.UP);
				processing.textLeading(textSize); 
				processing.text("Gelecek Zaman",-recSize/2,recSize);
				processing.popMatrix() ;

				processing.pushMatrix() ;
				processing.translate(originX-50,firstSeperatorY-yLength/6);
				processing.rotate(-1*processing.PI/2);
				processing.textAlign(processing.RIGHT, processing.UP);
				processing.textLeading(textSize); 
				processing.text("Şimdiki Zaman",-recSize/2,recSize);
				processing.popMatrix() ;

				processing.pushMatrix() ;
				processing.translate(originX-50,originY-yLength/6);
				processing.rotate(-1*processing.PI/2);
				processing.textAlign(processing.RIGHT, processing.UP);
				processing.textLeading(textSize); 
				processing.text("Geçmiş Zaman",-recSize/2,recSize);
				processing.popMatrix() ;

				//getPoints of boundaries so that divide works
				var boundaries = getBoundaryValues();

				//get number of diffreent places
				var places = getUniquePlaces();

				var incrementalX = xLength / places.length;

				  // Use color variable 'c' as fill color
				processing.noStroke();  // Don't draw a stroke around shapes

				for (var i = works.length - 1; i >= 0; i--) {
					var currentWork = works[i];

					var c = processing.color(10, processing.map(currentWork.year, boundaries[1], boundaries[0],30,255), 10);  // Define color 'c'
					
					var xPos = originX + (getPlaceIndex(places,currentWork.place)+1) * incrementalX;
					var yPos;

					var maxValue = Math.max(currentWork.simdikiCnt,currentWork.gelecekCnt,currentWork.gecmisCnt);

					var sum = currentWork.simdikiCnt + currentWork.gelecekCnt + currentWork.gecmisCnt;
					if(maxValue == currentWork.gelecekCnt){//gelecek zamaan ağırlıklı ise
						yPos = secondSeperatorY - yLength/3 * currentWork.gelecekCnt / sum;
					}else if(maxValue == currentWork.simdikiCnt){//şimdiki zaman ağırlıklı ise
						yPos = firstSeperatorY - yLength/3 * currentWork.gelecekCnt/(sum - currentWork.simdikiCnt);
					}else if(maxValue == currentWork.gecmisCnt){//geçmiş zaman ağırlıklı ise
						yPos = firstSeperatorY + yLength/3 * currentWork.gecmisCnt / sum;
					}

					processing.fill(c);
					processing.rect(xPos-recSize/2,yPos-recSize/2,recSize,recSize);
					
					processing.fill(50, 55, 100);
					processing.textSize(textSize/1.2);
					/*processing.textAlign(processing.CENTER, processing.UP);
					processing.text(currentWork.year, xPos,originY + recSize*2);*/

					processing.pushMatrix() ;
					processing.translate(xPos,height - marginY);
					processing.rotate(-1*processing.PI/2);
					processing.textAlign(processing.RIGHT, processing.UP);
					processing.textLeading(textSize); 
					processing.text(currentWork.place,-recSize/2,recSize);
					processing.popMatrix() ;

					// mouse over yemiyor çünkü biz büyültüüp sonra küçültüyoruz. Bundan ötürü problem var. 
					// eğer width ve height i real olanla verirsek olur
					if(processing.mouseX <= xPos + marginPointer && processing.mouseX >= xPos - marginPointer
						&& processing.mouseY <= yPos + marginPointer && processing.mouseY >= yPos - marginPointer){
						processing.text(currentWork.gelecekCnt+ " , "+currentWork.simdikiCnt+ " , "+currentWork.gecmisCnt, xPos,yPos);
					}
				}


			};

			Array.prototype.containsPlace = function(v) {
			    for(var i = 0; i < this.length; i++) {
			        if(this[i] === v.place) return true;
			    }
			    return false;
			};

			Array.prototype.uniquePlace = function() {
			    var arr = [];
			    for(var i = 0; i < this.length; i++) {
			        if(!arr.containsPlace(this[i])) {
			            arr.push(this[i].place);
			        }
			    }
			    return arr; 
			}

			function getUniquePlaces(){
				return works.uniquePlace();
			}

			function getPlaceIndex(placeArr, curPlace){
				for(var i = 0; i < placeArr.length; i++) {
			        if(placeArr[i] === curPlace) return i;
			    }
			    return -1;
			}

			function getBoundaryValues(){
				var max  = 0;
				var min = 999999;

				for(var i = 0; i<works.length; i++){
					if(works[i].year > max){
						max = works[i].year ;
					}

					if(works[i].year < min){
						min = works[i].year ;
					}
				}

				return [max,min];
			}
				
		}

			var canvasTensesDenememPlace = document.getElementById("canvasTensesDenememPlace");
			// attaching the sketchProc function to the canvas
			var processingInstance11 = new Processing(canvasTensesDenememPlace, sketchPlaceTenseGorselResult);

			$("#canvasTensesDenememPlace").css("width","100%");
			
	}


	function loadProgress(){
		$scope.$parent.showLoading(); 		
	}

	function loadEnded(){
		$scope.$parent.showLoaded(); 
	}

}]);