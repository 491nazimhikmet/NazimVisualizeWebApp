
	function sketchRoundedGorselSonucCanvas(height,width){
	

		var scrollPos = 0;

		function sketchRoundedGorselResult(processing) {
			
			var hs1, hs2;


			processing.setup = function(){
				
				processing.size(width, height);
				processing.noStroke();
				//processing.frameRate(1);
				var bar_width = 16;

				hs1 = new HScrollbar(width-bar_width, 0, bar_width, 10, bar_width,height);

				
			};

			// Override draw function, by default it will be called 60 times per second
			processing.draw = function() {				
				processing.background(255);


				hs1.update();
				hs1.display();

				processing.stroke(0);
				processing.line(0, height/2, width, height/2);

			};

			var HScrollbar = function (xp, yp, sw, sh, l,h){
				this.over;           // is the mouse over the slider?
				this.locked;

			    this.swidth = sw;
			    this.sheight = sh;
			    this.bheight = h;
			    var widthtoheight = h - sh;
			    this.ratio = sh / widthtoheight;
			    this.xpos = xp;
			    this.ypos = yp;
			    this.spos = 0;
			    this.newspos = this.spos;
			    this.sposMin = this.spos;
			    this.sposMax = h-sh;
			    this.loose = l;
				
			}

			HScrollbar.prototype.overEvent = function() {
				/*if (processing.mouseX > this.xpos && processing.mouseX < this.xpos+ this.swidth &&
				   processing.mouseY > this.ypos && processing.mouseY < this.ypos+ this.bheight) {
				  return true;
				} else {
				  return false;
				}*/
				return true;
			}

			HScrollbar.prototype.constrain = function(val, minv, maxv) {
				return Math.min(Math.max(val, minv), maxv);
			}

			HScrollbar.prototype.getPos = function () {
				// Convert spos to be values between
				// 0 and the total width of the scrollbar
				return this.spos * this.ratio;
			}

			HScrollbar.prototype.display = function() {
				processing.noStroke();
				processing.fill(204);
				processing.rect(this.xpos, this.ypos, this.swidth, this.bheight);
				if (this.over || this.locked) {
				  processing.fill(0, 0, 0);
				} else {
				  processing.fill(102, 102, 102);
				}
				processing.rect(this.xpos, this.spos, this.swidth, this.sheight);
			}

			HScrollbar.prototype.update = function() {
				if (this.overEvent()) {
				  this.over = true;
				} else {
				  this.over = false;
				}
				if (processing.mousePressed  && this.over) {
				  this.locked = true;
				}
				if (!processing.mousePressed ) {
				  this.locked = false;
				}
				//if (this.locked) {
				  this.newspos = this.constrain(scrollPos-this.swidth/2, this.sposMin, this.sposMax);
				//}
				if (Math.abs(this.newspos - this.spos) > 1) {
				  this.spos = this.spos + (this.newspos- this.spos)/this.loose;

				}

				if (scrollPos > this.bheight){
					scrollPos = this.bheight;
				}else if(scrollPos < 0){
					scrollPos = this.sposMin;
				}
			}
				
		}
		
		var canvasGorselSonuc = document.getElementById("canvasDeneme");
		// attaching the sketchProc function to the canvas
		var processingInstance11 = new Processing(canvasGorselSonuc, sketchRoundedGorselResult);

		canvasGorselSonuc.addEventListener("mousedown",function(){
			processingInstance11.mousePressed = true;
		},false);
		canvasGorselSonuc.addEventListener("mouseup",function(){
			processingInstance11.mousePressed = false;	
		},false);

		var isMoving=false;

		canvasGorselSonuc.addEventListener('mousewheel',function(event){
		    //mouseController.wheel(event);
		    event.preventDefault();
		    if (isMoving) return;

		    scrollPos += event.deltaY;

		    isMoving = true;
			   setTimeout(function() {
			    isMoving=false;
			   },250);

		    return false; 
		}, false);


		//$("#canvasDeneme").css("width","100%");
		//processingInstance2.noStroke();
	}

	var canvasHeight = $("#canvasDeneme").height();
	var canvasWidth = $("#canvasDeneme").width();
	sketchRoundedGorselSonucCanvas(canvasHeight,canvasWidth);