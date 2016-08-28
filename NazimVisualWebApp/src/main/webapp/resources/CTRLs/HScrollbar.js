/**
* This object is scroll bar which is used in processing canvases.
* It is used both by mouse scroll or mouse press events.
* To change position of the scroll in processing instance, 
* we get processing instance as a parameter and use of it.
* bar means the scroll bar as a whole.
* slider is just the part which we slide.
* xp: x position of bar
* yp: y position of bar
* sw: slider width
* sh: slider height
* l: looose
* h: height of bar
* processing: processing instance where we draw scroll bar. 
*/
var HScrollbar = function (xp, yp, sw, sh, l,h,processing){
	this.over;           // is the mouse over the slider?
	this.locked;

    this.swidth = sw;
    this.sheight = sh;
    this.bheight = h;
    this.ratio = 1 / h;
    this.xpos = xp;
    this.ypos = yp;
    this.spos = 0;
    this.newspos = this.spos;
    this.sposMin = this.spos;
    this.sposMax = h-sh;
    this.loose = l;
    this.processing = processing;
	this.scrollPos = 0; //this variable is to keep position of slider when there is scrollPos event.
}


/**
* returns whether mouse is ovre slider or not
*/
HScrollbar.prototype.overEvent = function() {
	if (this.processing.mouseX > this.xpos && this.processing.mouseX < this.xpos+ this.swidth &&
	   this.processing.mouseY > this.ypos && this.processing.mouseY < this.ypos+ this.bheight) {
	  return true;
	} else {
	  return false;
	}
}

/**
* this function will be used to get newpos of slider.
*/
HScrollbar.prototype.constrain = function(val, minv, maxv) {
	return Math.min(Math.max(val, minv), maxv);
}

/**
* this function returns a value between 0 and 1.
* return value shows where we are in, when scrool. 
*/
HScrollbar.prototype.getPos = function () {
	var pos = this.spos * this.ratio;
	return pos;
}

/**
* to draw bar and slider. if mouse enter event occurs then we highlight the slider.
*/
HScrollbar.prototype.display = function() {
	this.processing.noStroke();
	this.processing.fill(204);
	this.processing.rect(this.xpos, this.ypos, this.swidth, this.bheight);
	if (this.over || this.locked) {
	  this.processing.fill(0, 0, 0);
	} else {
	  this.processing.fill(102, 102, 102);
	}
	this.processing.rect(this.xpos, this.spos, this.swidth, this.sheight);
}

/**
* to update position of slider
*/
HScrollbar.prototype.update = function() {
	
	//if over the slider
	if (this.overEvent()) {
	  this.over = true;
	} else {
	  this.over = false;
	}
	
	//if mouse is pressed and over the slider
	if (this.processing.mousePressed  && this.over) {
	  this.locked = true;
	}
	
	//if mouse is not pressed
	if (!this.processing.mousePressed ) {
	  this.locked = false;
	}
	
	//if mouse locked which is determined above, determine newspos using mouse position, else use scroll position
	if (this.locked) {
		//update newspos by using the mouse's Y-axis position value.
	    this.newspos = this.constrain(this.processing.mouseY-this.swidth/2, this.sposMin, this.sposMax);
	    //for keeping scrollpos as new position update it
	    this.scrollPos = this.newspos;
	}else{
		//update newspos by using mouse scroll position
		this.newspos = this.constrain(this.scrollPos-this.swidth/2, this.sposMin, this.sposMax);
	}
	
	//update slider position if position changes
	if (Math.abs(this.newspos - this.spos) > 1) {
	  this.spos = this.spos + (this.newspos- this.spos)/this.loose;
	}

	//to avoid situations where slider is scrolled in the boundaries
	if (this.scrollPos > this.bheight){
		this.scrollPos = this.bheight;
	}else if(this.scrollPos < 0){
		this.scrollPos = this.sposMin;
	}
}	

//to set the scrollpos ftom outside
HScrollbar.prototype.setScrollPos = function(scrollPos){
	this.scrollPos += scrollPos;
}