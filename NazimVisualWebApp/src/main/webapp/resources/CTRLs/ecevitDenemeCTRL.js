app.controller('ecevitDenemeCTRL', ['$scope','$http','BaseAPI',function($scope,$http,BaseAPI) {
	
	function loadProgress(){

	}

	function loadEnded(){

	}

	$scope.$on('callSearch',function(event, args){
		$scope.callSearch();
	});

	var arrayGorsels = [];

	$scope.callSearch = function () {
    	
    	loadProgress();

    	BaseAPI.callServlet('searchYazi',{searchText : $scope.searchTextM}).then(function(response){
    		if(response.length==0){
				bootbox.alert("Sonuç bulunamadi!");
				loadEnded();	
    			return;
    		}
    		$scope.showGorselGeriDon = false;
    		$scope.aramaSonucList = response;
    		$scope.showResults = true;

	    	loadEnded();			

    	});

	}

	$scope.setSelectedWork = function(work){
		$scope.selectedWorkId = work.workID;
		$scope.selectedWork = work;
	}	

	$scope.makeOperation = function(type){
		if($scope.selectedWorkId && $scope.selectedWorkId > 0){
			console.log($scope.selectedWorkId);
			if(type == 1){
				$scope.getEffectAnalyzer();
			}else if(type == 2){
				$scope.readSelectedWork();	
			}else if (type == 3){
				$scope.drawGraph();
			}
			$scope.shownWorkName = $scope.selectedWork.header;
			
		}else{
			bootbox.alert("Lütfen bir çalışma seçiniz.");
		}
	}

	$scope.readSelectedWork = function(){
		window.open("http://ecevityazilari.org/items/show/"+$scope.selectedWork.pageId, '_blank');
	}

	function killPrevGorsels(){
		for(var i=0; i<arrayGorsels.length; i++){
			arrayGorsels[i].exit();
		}
	}


	$scope.drawGraph = function(){
		BaseAPI.callServlet('getGraphNetData',{}).then(function(graphData) {
    		$scope.showGraphNetResult = true;
    		    // create an array with nodes
		    /*var nodes = new vis.DataSet([
		        {id: 1, label: 'Node 1'},
		        {id: 2, label: 'Node 2'},
		        {id: 3, label: 'Node 3'},
		        {id: 4, label: 'Node 4'},
		        {id: 5, label: 'Node 5'}
		    ]);

		    // create an array with edges
		    var edges = new vis.DataSet([
		        {from: 1, to: 3},
		        {from: 1, to: 2},
		        {from: 2, to: 4},
		        {from: 2, to: 5}
		    ]);*/
		    var nodes = graphData.vn;
		    var edges = graphData.ve.slice(0,20);

		    // create a network
		    var container = document.getElementById('mynetwork');

		    // provide the data in the vis format
		    var data = {
		        nodes: nodes,
		        edges: edges
		    };
		   
			var options = {
		      nodes: {
		          shape: 'dot',
		          shadow:true,
		          font: {
		            size: 12,
		          },
		      },
		      edges: {
		          shadow:true,
		          font: {
		            align: 'top',
		            size: 8,
		          },
		      },
		      interaction: {
		        hover: true,
		        navigationButtons: true,
		        keyboard: false,
		      },
		      physics: {
		        // enabled: true,
		      },
		      layout: {
		        randomSeed: 8
		      }/*,
		      manipulation: {
		        enabled: false,
		        addEdge: function(edgeData,callback) {
		          console.log(edgeData);
		          if (edgeData.from === edgeData.to) {
		            var r = confirm("Do you want to connect the node to itself?");
		            if (r === true) {
		              callback(edgeData);
		            }
		          }
		          else {
		            // console.log(nodes);
		            var allNodes = nodes.get({returnType:"Object"});
		            $('#connectionSource').val(allNodes[edgeData.from].title); 
		            $('#connectionTarget').val(allNodes[edgeData.to].title); 
		            document.getElementById('addConnectionButton').onclick = saveData.bind(this, edgeData, callback);
		            $('#addConnectionModal').modal('show');
		            // callback(edgeData);
		          }
		        }
		      }*/
		    };
		    // initialize your network!
		    var network = new vis.Network(container, data, options);
			loadEnded();
		});
	}

	$scope.getEffectAnalyzer = function(){

		BaseAPI.callServlet('getSentecesOfEcevitWork',{workId:$scope.selectedWorkId+""}).then(function(sentences) {
            
			BaseAPI.callServlet('getEcevitWordsOfWorkWithParsedForm',{workId:$scope.selectedWorkId+""}).then(function(parsedWords) {
            
				BaseAPI.callServlet('getEcevitAffectiveResultsOfWork',{workId:$scope.selectedWorkId+""}).then(function(affectiveResults) {
            		$scope.gorselSonucShow = true;

            		$("#canvasGorselSonuc").height(600);
					$("#canvasGorselSonuc").width(1170);	
					var canvasHeight = $("#canvasGorselSonuc").height();
					var canvasWidth = $("#canvasGorselSonuc").width();
					$scope.sketchBarkodeGorselSonucCanvas(sentences,parsedWords,affectiveResults,canvasHeight,canvasWidth);


					loadEnded();
		        });
	        });
        });
	}

	$scope.sketchBarkodeGorselSonucCanvas = function (sentences,words,affectiveResults,height,width){
	

		function sketchBarkodGorselResult(processing) {
			
			var defaultColour = 150;

			var maxSentenceLength= 0;
			processing.setup = function(){
				
				processing.size (width, height);		
				processing.frameRate(10);  	
				processing.stroke(255);

				for(var i=0;i<sentences.length; i++){
					if(sentences[i].text.length > maxSentenceLength){
						maxSentenceLength = sentences[i].text.length;
					}
				}

			};

			// Override draw function, by default it will be called 60 times per second
			processing.draw = function() {				
				processing.background(255);
		
				
				var lengthOfPoem = sentences.length;
				
				var topMargin = processing.map(height,0,1080,0,200);
				var barcodeLength = height-topMargin*1.1;
				
				var interY = barcodeLength/lengthOfPoem;
				
				var textX = width/8;
				var textY = processing.map(height,0,1080,0,50);
				
				var shift = processing.map(height,0,1080,0,25);
				
				processing.fill(0);
				processing.text("Her bir çizgi çalışmadaki bir cümleyi ifade eder.",textX,textY);
				processing.text("Etken fiil içeren cümleler yeşil, edilgen fiil içeren cümleler kirmizi ile renklendirilmiştir.",textX,textY+shift);
				processing.text("Cümlelede geçen fillerin çekimlerine göre sirasiyla: ben/kirmizi, sen/sari, o/yeşil, biz/cyan, siz/mavi , onlar/mor ile renklendirilmiştir.",textX,textY+2*shift);
				processing.text("Cümleler olumluluk/valence, uyarilma/arousal ve baskinlik/dominance değerlerine göre yüksek değerliler yeşil, düşük değerliler kirmizi ile renklendirilmiştir.",textX,textY+3*shift);

			
				var x = width/8;
				var y = topMargin;

				processing.fill(0,0,200);
				processing.stroke(0,0,200);
				
				processing.text("satir uzunluklari:",x,y);
				processing.text("etken-edilgen :",x*2,y);
				processing.text("kişi çekimi :",x*3,y);
				processing.text("olumluluk/valence:",x*4,y);
				processing.text("uyarilma/arousal:",x*5,y);
				processing.text("baskinlik/dominance:",x*6,y);
				
				processing.strokeWeight(2);
				y = topMargin + 2*interY;
				
				var satirLength;
				var barWidth = width/12;  
				
				var mouseMargin = processing.map(height,0,1080,0,5);

				for(var i=0;i<lengthOfPoem;i++){
					
					satirLength = (sentences[i].text.length)/maxSentenceLength * barWidth ; //satic dediğimiz sentence, burada onun uzunluğunu string uzunluğu olarak assume ediyoruz.
					processing.stroke(defaultColour);
					
					processing.line(x,y,x+satirLength,y);
					y += interY;

				}	
				
				y = topMargin + 2*interY;

				
				for(var i=0;i<lengthOfPoem;i++){ 	//etken edilgen:
					var isEtken = false;
					var isEdilgen = false;
					for(var j=0;j<words.length;j++){
						if(sentences[i].sentenceId == words[j].sentenceId){ // bulunduğumuz satirdaki sözcüklere göre satiri renklendir 
							if(etken(j)){
								processing.stroke(0,220,40);//yeşil
								isEtken = true;
							}
							
							if(edilgen(j)){
								processing.stroke(220,0,0);//kirmizi
								isEdilgen = true;
							}

							if(isEtken == true && isEdilgen == true){ // hem etken hem edilgense
								processing.stroke(2200,220,40);//yeşil
								isEtken = true;
								isEdilgen = true;
							}

							if(isEtken == false && isEdilgen == false){
								processing.stroke(defaultColour);
							}
						}
					}
					processing.line(2*x,y,2*x+barWidth,y);	
					y += interY;
				}
				
				y =  (topMargin + 2*interY);

				
				for(var i=0;i<lengthOfPoem;i++){	//ben ve biz ayrimi:
					var isAnyCekim = false;
					for(var j=0;j<words.length;j++){
						if(sentences[i].sentenceId == words[j].sentenceId){
							isAnyCekim = strokeSubject(j,isAnyCekim);
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

			function strokeSubject(i,isAnyCekim){
				if (words[i].parsedForm.indexOf("A1sg") > -1 ||words[i].parsedForm.indexOf("P1sg") > -1 ){// ben-kirmizi
					processing.fill(255,0,0);
					processing.stroke(255,0,0);
					isAnyCekim = true;			
				}else if (words[i].parsedForm.indexOf("A2sg") > -1 ||words[i].parsedForm.indexOf("P2sg") > -1 ){// sen-sari
					processing.fill(255,220,0);
					processing.stroke(255,220,0);
					isAnyCekim = true;
				}else if (words[i].parsedForm.indexOf("A1pl") > -1 ||words[i].parsedForm.indexOf("P1pl") > -1 ){// biz-cyan
					processing.fill(0,240,255);
					processing.stroke(0,240,255);
					isAnyCekim = true;
				}else if (words[i].parsedForm.indexOf("A2pl") > -1 ||words[i].parsedForm.indexOf("P2pl") > -1 ){// siz-mavi
					processing.fill(0,0,255);
					processing.stroke(0,0,255);
					isAnyCekim = true;
				}else if (words[i].parsedForm.indexOf("A3pl") > -1 ||words[i].parsedForm.indexOf("P3pl") > -1 ){// onlar-mor
					processing.fill(255,0,255);
					processing.stroke(255,0,255);
					isAnyCekim = true;
				}else if (words[i].parsedForm.indexOf("A3sg") > -1 ||words[i].parsedForm.indexOf("P3sg") > -1 ){// o-yeşil
					processing.fill(0,240,0);
					processing.stroke(0,240,0);
					isAnyCekim = true;
				}
				else{
					if(isAnyCekim == false){
						processing.fill(defaultColour);
						processing.stroke(defaultColour);
					}
					
				}
				return isAnyCekim;
			}

				
		}

			killPrevGorsels();
		
			var canvasGorselSonuc = document.getElementById("canvasGorselSonuc");
			// attaching the sketchProc function to the canvas
			var processingInstance13 = new Processing(canvasGorselSonuc, sketchBarkodGorselResult);

			arrayGorsels.push(processingInstance13);

			$("#canvasGorselSonuc").css("width","100%");
	}


}]);
