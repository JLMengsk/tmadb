/* 
 * zoom pan (canvas tutorial)
 * ref: https://developer.mozilla.org/en-US/docs/Web/API/Canvas_API/Tutorial
 * ref: https://developer.mozilla.org/en-US/docs/Web/JavaScript/Introduction_to_Object-Oriented_JavaScript
 * ref: https://developer.mozilla.org/en/docs/Web/API/MouseEvent
 * 
 */

//////////////////////////////
/// START OF UTIL FUNCTION ///
/**
 * clear selection
 * - needed after handling a double click event
 * 
 * @returns {undefined}
 */
function clearSelection() {
    if (document.selection && document.selection.empty) {
        document.selection.empty();
    } else if (window.getSelection) {
        var sel = window.getSelection();
        sel.removeAllRanges();
    }
}
;

/// END OF UTIL FUNCTION   ///
//////////////////////////////

/**
 * keep track of the part of the image showing
 * @param {type} sX
 * @param {type} sY
 * @param {type} sWidth
 * @param {type} sHeight
 * @param {type} orgWidth - original width of source image
 * @param {type} orgHeight - original height of source image
 * @returns {ViewWindow}
 */
var ViewWindow = function (sX, sY, sWidth, sHeight, orgWidth, orgHeight) {
    this.sX = sX;
    this.sY = sY;
    this.sWidth = sWidth;
    this.sHeight = sHeight;
    this.orgWidth = orgWidth;
    this.orgHeight = orgHeight;
    this.currMag = 1; // current mag level
    this.maxMag = 2; // maximum zoom in
    this.minMag = 0.04; // minimum zoom out
    this.zoomIncr = 0.04;
}

ViewWindow.prototype.getSX = function () {
    return this.sX;
};
ViewWindow.prototype.getSY = function () {
    return this.sY;
};
ViewWindow.prototype.getSWidth = function () {
    return this.sWidth;
};
ViewWindow.prototype.getSHeight = function () {
    return this.sHeight;
};
ViewWindow.prototype.getCurrMag = function () {
    return this.currMag;
};
ViewWindow.prototype.setSX = function (sX) {
    this.sX = sX;
};
ViewWindow.prototype.setSY = function (sY) {
    this.sY = sY;
};
ViewWindow.prototype.setSWidth = function (sWidth) {
    this.sWidth = sWidth;
};
ViewWindow.prototype.setSHeight = function (sHeight) {
    this.sHeight = sHeight;
};

ViewWindow.prototype.getOrgWidth = function () {
    return this.orgWidth;
}

ViewWindow.prototype.getOrgHeight = function () {
    return this.orgHeight;
}

/**
 * return the width of the projected image on the canvas
 * @param {type} canvasWidth
 * @param {type} canvasHeight
 * @returns {undefined}
 */
ViewWindow.prototype.getDWidth = function (canvasWidth, canvasHeight) {
    return ((canvasWidth / canvasHeight < this.sWidth / this.sHeight) ? canvasWidth : canvasHeight * this.sWidth / this.sHeight);
};

/**
 * return the height of the projected image on the canvas
 * @param {type} canvasWidth
 * @param {type} canvasHeight
 * @returns {undefined}
 */
ViewWindow.prototype.getDHeight = function (canvasWidth, canvasHeight) {
    return ((canvasWidth / canvasHeight < this.sWidth / this.sHeight) ? canvasWidth * this.sHeight / this.sWidth : canvasHeight);
};

/**
 * return the x coordinate of the projected image on the canvas
 * @param {type} canvasWidth
 * @param {type} canvasHeight
 * @returns {undefined}
 */
ViewWindow.prototype.getDX = function (canvasWidth, canvasHeight) {
    return ((canvasWidth - this.getDWidth(canvasWidth, canvasHeight)) / 2);
};

/**
 * return the y coordinate of the projected image on the canvas
 * @param {type} canvasWidth
 * @param {type} canvasHeight
 * @returns {type|Number|undefined}
 */
ViewWindow.prototype.getDY = function (canvasWidth, canvasHeight) {
    return ((canvasHeight - this.getDHeight(canvasWidth, canvasHeight)) / 2);
};

/**
 * get SX given DX
 * @param {type} sX
 * @param {type} canvasWidth
 * @param {type} canvasHeight
 * @returns {undefined|dWidth|Number|type}
 */
ViewWindow.prototype.dXToSX = function (sX, canvasWidth, canvasHeight) {
    return (sX - this.getDX(canvasWidth, canvasHeight)) / this.getCurrMag() + this.getSX();
};

/**
 * get SY given DY
 * @param {type} sY
 * @param {type} canvasWidth
 * @param {type} canvasHeight
 * @returns {type|dWidth|Number|undefined}
 */
ViewWindow.prototype.dYToSY = function (sY, canvasWidth, canvasHeight) {
    return (sY - this.getDY(canvasWidth, canvasHeight)) / this.getCurrMag() + this.getSY();
};

/**
 * zoom in/out
 * @param {type} delta
 * @param {type} canvasWidth
 * @param {type} canvasHeight
 * @returns {undefined}
 */
ViewWindow.prototype.changeMagnification = function (delta, canvasWidth, canvasHeight) {
    // capture centre point of view window BEFORE magnification change
    var midX = this.sX + this.sWidth / 2;
    var midY = this.sY + this.sHeight / 2;

    var dWidth = this.getDWidth(canvasWidth, canvasHeight);
    var dHeight = this.getDHeight(canvasWidth, canvasHeight);
    this.currMag = Math.min(this.maxMag, Math.max(this.minMag, dWidth / this.sWidth - delta * this.zoomIncr));

    // adjust sWidth, sHeight according to magnification
    this.sWidth = Math.min(this.orgWidth, Math.max(dWidth / this.maxMag, dWidth / this.currMag));
    this.sHeight = Math.min(this.orgHeight, Math.max(dHeight / this.maxMag, dHeight / this.currMag));

    // fill in the canvas width/height
    this.sWidth = Math.min(this.orgWidth, this.sWidth + Math.max(0, (canvasWidth - dWidth) * this.sWidth / dWidth));
    this.sHeight = Math.min(this.orgHeight, this.sHeight + Math.max(0, (canvasHeight - dHeight) * this.sHeight / dHeight));

    // need to update sX/sY 
    this.sX = Math.min(this.orgWidth - this.sWidth, Math.max(0, midX - this.sWidth * 0.5));
    this.sY = Math.min(this.orgHeight - this.sHeight, Math.max(0, midY - this.sHeight * 0.5));

    // reset magnification after sWidth is adjusted
    this.currMag = this.getDWidth(canvasWidth, canvasHeight) / this.sWidth;

    //console.log("mag=" + magnification + " dY=" + this.getDY(canvasWidth, canvasHeight));
};

/**
 * move ViewWindow in x/y direction
 * @param {type} x - in source image coord. system
 * @param {type} y - in source image coord. system
 * @returns {undefined}
 */
ViewWindow.prototype.move = function (x, y) {
    this.sX = Math.max(0, Math.min(this.orgWidth - this.sWidth, this.sX + x));
    this.sY = Math.max(0, Math.min(this.orgHeight - this.sHeight, this.sY + y));
};


/**
 * 
 * @param {type} vw - ViewWindow
 * @param {type} twWidth - thumbnail window width
 * @param {type} twHeight - thumbnail window height
 * @returns {ThumbnailWindow}
 */
var ThumbnailWindow = function (vw, twWidth, twHeight) {
    this.vw = vw;
    var orgWidth = vw.getOrgWidth();
    var orgHeight = vw.getOrgHeight();
    this.twWidth = twWidth;
    this.twHeight = twHeight;
    if (twWidth / twHeight < orgWidth / orgHeight) {
        this.dWidth = twWidth;
        this.scale = this.dWidth / orgWidth;
        this.dHeight = orgHeight * this.scale;
    } else {
        this.dHeight = twHeight;
        this.scale = this.dHeight / orgHeight;
        this.dWidth = orgWidth * this.scale;
    }
}
ThumbnailWindow.prototype.getTwWidth = function () {
    return(this.twWidth);
}
ThumbnailWindow.prototype.getTwHeight = function () {
    return(this.twHeight);
}
ThumbnailWindow.prototype.getDWidth = function () {
    return (this.dWidth);
};

ThumbnailWindow.prototype.getDHeight = function () {
    return (this.dHeight);
};

ThumbnailWindow.prototype.getBoxDX = function (canvasWidth) {
    return canvasWidth - this.getTwWidth() + this.vw.getSX() * this.scale + (this.getTwWidth() - this.getDWidth()) * 0.5;
};

ThumbnailWindow.prototype.getBoxDY = function () {
    return this.vw.getSY() * this.scale + (this.getTwHeight() - this.getDHeight()) * 0.5;
};

ThumbnailWindow.prototype.getBoxDWidth = function () {
    return this.vw.getSWidth() * this.scale;
}

ThumbnailWindow.prototype.getBoxDHeight = function () {
    return this.vw.getSHeight() * this.scale;
}
/**
 * class Zoompan - provides functionality to zoom and pan on a single image
 * @param {type} imageUrl
 * @param {type} canvasId
 * @returns {Zoompan}
 */
var Zoompan = function (imageUrl, canvasId, drawThumbnailWindow) {
    // properties
    this.img = new Image();
    this.img.src = imageUrl;
    this.canvas = document.getElementById(canvasId);
    this.ctx = this.canvas.getContext('2d'); // the canvas context
    this.ctx.fillStyle = "white"; // fill color
    this.isPanning = false;
    this.startDX = 0; // for panning
    this.startDY = 0; // for panning
    this.drawThumbnailWindow = drawThumbnailWindow;
    var zoompan = this;

    // define viewWindow
    this.img.onload = function () {
        zoompan.vw = new ViewWindow(0, 0, zoompan.img.width, zoompan.img.height, zoompan.img.width, zoompan.img.height);
        zoompan.vw.currMag = Math.min(zoompan.vw.maxMag, Math.max(zoompan.vw.minMag, zoompan.vw.getDWidth(zoompan.canvas.width, zoompan.canvas.height) / zoompan.vw.getSWidth()));
        zoompan.tw = new ThumbnailWindow(zoompan.vw, zoompan.canvas.width * 0.25, zoompan.canvas.height * 0.25);
        zoompan.repaint();
    };

    // add mouse event handler
    if (this.canvas.addEventListener) {
        // ZOOM IN/OUT
        // IE9, Chrome, Safari, Opera
        this.canvas.addEventListener("mousewheel", function (e) {
            zoompan.mouseWheelHandlerForZoom(e);
        }, false);
        // Firefox
        this.canvas.addEventListener("DOMMouseScroll", function (e) {
            zoompan.mouseWheelHandlerForZoom(e);
        }, false);

        // DRAG
        this.canvas.addEventListener("mousedown", function (e) {
            clearSelection(); // want to clear all selection so that the browser won't drag the image after a double click event
            zoompan.isPanning = true;
            zoompan.startDX = e.clientX;
            zoompan.startDY = e.clientY;
            //console.log("mousedown: x:" + e.clientX + " y:" + e.clientY);
        }, false);

        this.canvas.addEventListener("mousemove", function (e) {
            zoompan.mouseDragHandlerForZoom(e);
        }, false);

        this.canvas.addEventListener("mouseup", function (e) {
            zoompan.isPanning = false;//console.log("mouseup: x:" + e.clientX + " y:" + e.clientY);
        }, false);

        this.canvas.addEventListener("mouseout", function (e) {
            zoompan.isPanning = false;
        }, false);
    }
};

/**
 * toggle whether to draw the thumbnail image or not
 * @type type
 */
Zoompan.prototype.toggleThumbnailWindow = function () {
    this.drawThumbnailWindow = !this.drawThumbnailWindow;
    this.repaint();
}

/**
 * draw thumbnail window
 * @returns {undefined}
 */
Zoompan.prototype.repaintThumbnailWindow = function () {
    this.ctx.beginPath();
    this.ctx.strokeStyle = "black";
    this.ctx.rect(this.canvas.width - this.tw.getTwWidth(), 0, this.tw.getTwWidth(), this.tw.getTwHeight());
    this.ctx.fill();
    // draw thumbnail image
    this.ctx.drawImage(this.img, 0, 0, this.img.width, this.img.height,
            this.canvas.width - this.tw.getTwWidth() + (this.tw.getTwWidth() - this.tw.getDWidth()) * 0.5,
            (this.tw.getTwHeight() - this.tw.getDHeight()) * 0.5,
            this.tw.getDWidth(),
            this.tw.getDHeight()
            );
    this.ctx.stroke();
    // draw thumbnail box
    //console.log(this.tw.getBoxDX(this.canvas.width) + " " + this.tw.getBoxDY() + " " + this.tw.getBoxDWidth() + " " + this.tw.getBoxDHeight());
    this.ctx.strokeRect(this.tw.getBoxDX(this.canvas.width), this.tw.getBoxDY(), this.tw.getBoxDWidth(), this.tw.getBoxDHeight());

}

/**
 * draw without thumbnail window
 * @returns {undefined}
 */
Zoompan.prototype.repaintMainWindow = function () {
    //console.log("sX=" + this.vw.getSX() + " sY=" + this.vw.getSY() + " sWidth=" + this.vw.getSWidth() + " sHeight=" + this.vw.getSHeight()
    //        + " dX=" + this.vw.getDX(this.canvas.width, this.canvas.height)
    //        + " dWidth=" + this.vw.getDWidth(this.canvas.width, this.canvas.height));
    this.ctx.fillStyle = "white"; // fill color
    this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);
    this.ctx.drawImage(this.img,
            this.vw.getSX(),
            this.vw.getSY(),
            this.vw.getSWidth(),
            this.vw.getSHeight(),
            this.vw.getDX(this.canvas.width, this.canvas.height),
            this.vw.getDY(this.canvas.width, this.canvas.height),
            this.vw.getDWidth(this.canvas.width, this.canvas.height),
            this.vw.getDHeight(this.canvas.width, this.canvas.height));
}

/**
 * initial display of image
 * @returns {undefined}
 */
Zoompan.prototype.repaint = function () {
    this.repaintMainWindow();
    if (this.drawThumbnailWindow) {
        this.repaintThumbnailWindow();
    }
};

/**
 * draw after canvas resize
 * - may need to adjust ViewWindow
 * @returns {undefined}
 */
Zoompan.prototype.repaintResize = function () {
    // want to keep the mid x,y the same
    var midX = this.vw.getSX() + this.vw.getSWidth() / 2;
    var midY = this.vw.getSY() + this.vw.getSHeight() / 2;
    // need to adjust sWidth/sHeight, dWidth/dHeight
    this.vw.setSX(midX - this.canvas.width / this.vw.currMag * 0.5);
    this.vw.setSY(midY - this.canvas.height / this.vw.currMag * 0.5);
    this.vw.setSWidth(this.canvas.width / this.vw.currMag);
    this.vw.setSHeight(this.canvas.height / this.vw.currMag);
    this.repaint();
};

/**
 * zoom in 
 * @returns {undefined}
 */
Zoompan.prototype.zoomIn = function () {
    this.vw.changeMagnification(-1, this.canvas.width, this.canvas.height);
    this.repaint();
};

/**
 * zoom out
 * @returns {undefined}
 */
Zoompan.prototype.zoomOut = function () {
    this.vw.changeMagnification(1, this.canvas.width, this.canvas.height);
    this.repaint();
};

/**
 * fit the window
 * @returns {undefined}
 */
Zoompan.prototype.zoomFit = function () {
    var beforeMag = this.vw.currMag;
    var afterMag = -1;
    var attempt = 1;
    var maxAttempt = 1000;
    while (beforeMag !== afterMag && attempt < maxAttempt) {
        this.vw.changeMagnification(1, this.canvas.width, this.canvas.height);
        afterMag = this.vw.currMag;
        attempt++;
    }
    this.repaint();
};

/**
 * zoom to 1:1
 * @returns {undefined}
 */
Zoompan.prototype.zoomOne = function () {
    var afterMag = this.vw.currMag;
    var zoomDirection = afterMag < 1 ? -1 : 1;
    var distanceFromOne = Math.abs(1 - afterMag);
    var attempt = 1;
    var maxAttempt = 1000;
    while ((distanceFromOne > 0.01) && attempt < maxAttempt) {
        this.vw.changeMagnification(zoomDirection, this.canvas.width, this.canvas.height);
        afterMag = this.vw.currMag;
        if (Math.abs(1 - afterMag) > distanceFromOne) {
            // overshoot!  back up one step and stop
            this.vw.changeMagnification(-1 * zoomDirection, this.canvas.width, this.canvas.height);
            break;
        }
        distanceFromOne = Math.abs(1 - afterMag);
        attempt++;
    }
    this.repaint();
};

/**
 * pan x
 * @param {type} x
 * @returns {undefined}
 */
Zoompan.prototype.moveX = function (x) {
    this.vw.move(x, 0);
    this.repaint();
};

/**
 * pan y
 * @param {type} y
 * @returns {undefined}
 */
Zoompan.prototype.moveY = function (y) {
    this.vw.move(0, y);
    this.repaint();
};

/**
 * mouse event handler for zoom in/out
 * @param {type} e
 * @returns {undefined}
 */
Zoompan.prototype.mouseWheelHandlerForZoom = function (e) {
    // cross-browser wheel delta
    e = window.event || e; // old IE support
    var zoomDirection = e.wheelDelta;
    if (typeof zoomDirection === 'undefined' || zoomDirection === null) {
        zoomDirection = -1 * e.detail; // for firefox's DOMMouseScroll event
    }
    if (zoomDirection > 0) {
        this.zoomIn();
    } else {
        this.zoomOut();
    }
    if (e.preventDefault) {//disable default wheel action of scrolling page
        e.preventDefault();
    }
};

/**
 * mouse event handler for panning
 * @param {type} e
 * @returns {undefined}
 */
Zoompan.prototype.mouseDragHandlerForZoom = function (e) {
    if (this.isPanning) {
        // cross-browser wheel delta
        e = window.event || e; // old IE support
        //this.vw.currMag = this.vw.getSWidth() / this.vw.getDWidth(this.canvas.width, this.canvas.height);
        //console.log("mouseup: x:" + e.clientX + " y:" + e.clientY+" mag:"+this.vw.getCurrMag());
        this.vw.move((this.startDX - e.clientX) / this.vw.currMag, (this.startDY - e.clientY) / this.vw.currMag);
        this.repaint();
        this.startDX = e.clientX;
        this.startDY = e.clientY;
    }
}


//var zp = new Zoompan(
//        "http://www.gpecdata.med.ubc.ca/images/bliss/10-011/10-011_Ki67_MIB-1_-Dako_B12_v1_b3/10-011_Ki67(MIB-1)-Dako_B12_v1_b3_004_r1c4.jpg",
//        "tutorial",
//        true);

