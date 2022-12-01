
<%@ page import="ca.ubc.gpec.tmadb.Tma_core_images"%>
<%@ page import="ca.ubc.gpec.tmadb.DisplayConstant"%>
<%@ page import="ca.ubc.gpec.tmadb.ImageViewerMethods"%>
<%@ page import="ca.ubc.gpec.tmadb.Scoring_sessions"%>
<%@ page import="ca.ubc.gpec.tmadb.util.Scoring_sessionsConstants"%>
<%@ page import="ca.ubc.gpec.tmadb.util.ViewConstants"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName"
        value="${message(code: 'scoring_sessions.label', default: 'Scoring_sessions')}" />
        <title>Scoring session: ${fieldValue(bean: scoring_sessionInstance, field: "name")}</title>



        <asset:javascript src="ca/ubc/gpec/tmadb/image_helpers/fieldselector.js"/>
        <asset:javascript src="ca/ubc/gpec/tmadb/image_helpers/zoompan.js"/>
        <asset:javascript src="ca/ubc/gpec/tmadb/scoring_sessions/scoring_sessions.js"/>

        <script type="text/javascript">
        /* 
         * Nucleicounter class - extends Zoompan
         * ref: https://developer.mozilla.org/en-US/docs/Web/JavaScript/Introduction_to_Object-Oriented_JavaScript
         */

        var NUCLEI_STATE_POSITIVE = 1;
        var NUCLEI_STATE_NEGATIVE = 0;
        var NUCLEI_STATE_BEGIN = 2;
        var NUCLEI_TIME_NOT_SET = -1;
        var X_NOT_SET = -1;
        var Y_NOT_SET = -1;
        var X_LABEL = "x";
        var Y_LABEL = "y";
        var NUCLEI_SELECTION_PARAM_STRING_DELIMITER = "_";
        var POSITIVE_LABEL = "p";
        var NEGATIVE_LABEL = "n";
        var COMPARE_TAG = "COMPARE";
        var BEGIN_TAG = "BEGIN";
        var NUCLEI_SELECTION_RADIUS = 5; // for draw nuclei selection
        var NUCLEI_SELECTION_LINE_WIDTH = 3;
        var NUCLEI_COLOR_POSITIVE = "red";
        var NUCLEI_COLOR_NEGATIVE = "green";

        //////////////////////////////
        /// START OF UTIL FUNCTION ///

        /**
         * return true if a ends with b
         * - for browsers compatibility
         * @param {type} a
         * @param {type} b
         * @returns {undefined}
         */
        function endsWith_IE11(a, b) {
            return b.length > a.length ? false : (a.substring(a.length - b.length, a.length) === b);
        }
        ;

        /**
         * return true if a starts with b
         * - for browsers compatibility
         * @param {type} a
         * @param {type} b
         * @returns {undefined}
         */
        function startsWith_IE11(a, b) {
            return b.length > a.length ? false : (a.substring(0, b.length) === b);
        }

        /**
         * add replaceAll to String
         * @param {type} target
         * @param {type} replacement
         * @returns {String.prototype@call;split@call;join}
         */
        String.prototype.replaceAll = function (target, replacement) {
            return this.split(target).join(replacement);
        };

        /// END OF UTIL FUNCTION   ///
        //////////////////////////////

        /**
         * keep track of selection of a single nuclei
         * @param {type} x - source image coord
         * @param {type} y - source image coord
         * @param {type} time - time in milliseconds
         * @param {type} state - 1=positive 0=negative, 2=begin
         * @returns {undefined}
         */
        var Nuclei = function (x, y, time, state) {
            this.x = x;
            this.y = y;
            this.time = time;
            this.state = state;
        };
        Nuclei.prototype.getX = function () {
            return(this.x);
        };
        Nuclei.prototype.getY = function () {
            return(this.y);
        };
        Nuclei.prototype.getDX = function (vw, canvasWidth, canvasHeight) {
            return (this.x - vw.getSX()) * vw.getCurrMag() + vw.getDX(canvasWidth, canvasHeight);
        };
        Nuclei.prototype.getDY = function (vw, canvasWidth, canvasHeight) {
            return (this.y - vw.getSY()) * vw.getCurrMag() + vw.getDY(canvasWidth, canvasHeight);
        };
        Nuclei.prototype.getTime = function () {
            return(this.time);
        };
        Nuclei.prototype.getState = function () {
            return(this.state);
        };
        Nuclei.prototype.inView = function (vw) {
            return (vw.getSX() <= this.x
                    & (vw.getSX() + vw.getSWidth()) >= this.x
                    & vw.getSY() <= this.y
                    & (vw.getSY() + vw.getSHeight()) >= this.y);
        };

        /**
         * keeps track of nuclei selections
         * @returns {undefined}
         */
        var NucleiSelection = function () {
            this.nucleiSelected = [];
            this.numPositive = 0;
            this.numNegative = 0;
            this.updateCount = 0;    // initially no nuclei needs to be updated - because no nuclei has been deleted
            this.numNucleiToRemove = 0; // initially no nuclei needs to be updated - because no nuclei has been deleted
            this.initialTotal = 0;
        };

        NucleiSelection.prototype.incrementUpdateCount = function () {
            this.updateCount++;
        };

        NucleiSelection.prototype.decrementUpdateCount = function () {
            this.updateCount--;
            if (this.updateCount < this.numNucleiToRemove) {
                this.numNucleiToRemove = this.updateCount;
            }
        };

        NucleiSelection.prototype.resetUpdateCount = function () {
            this.updateCount = 0;
            this.numNucleiToRemove = 0;
            this.initialTotal = this.getNumTotal();
        };

        NucleiSelection.prototype.addNuclei = function (x, y, time, state) {
            this.nucleiSelected.push(new Nuclei(x, y, time, state));
            this.incrementUpdateCount();
            this.updateSelectedNucleiCount(state);
        };
        /**
         * add nuclei
         * @param nuclei 
         */
        NucleiSelection.prototype.addNucleiObj = function (nuclei) {
            this.nucleiSelected.push(nuclei);
            this.incrementUpdateCount();
            this.updateSelectedNucleiCount(nuclei.getState());
        };

        NucleiSelection.prototype.updateSelectedNucleiCount = function (state) {
            if (state === NUCLEI_STATE_POSITIVE) {
                this.numPositive++;
            } else if (state === NUCLEI_STATE_NEGATIVE) {
                this.numNegative++;
            }
        }

        NucleiSelection.prototype.removeNuclei = function () {
            var size = this.nucleiSelected.length;
            if (size > 0) {
                var nucleiRemoved = this.nucleiSelected.pop();
                this.decrementUpdateCount();
                if (nucleiRemoved.getState() === NUCLEI_STATE_POSITIVE) {
                    this.numPositive--;
                } else if (nucleiRemoved.getState() === NUCLEI_STATE_NEGATIVE) {
                    this.numNegative--;
                }
            }
        };

        /**
         * return a list of selected nuclei
         */
        NucleiSelection.prototype.getNucleiSelected = function () {
            return this.nucleiSelected;
        };

        /**
         * get number of positive nuclei selected
         */
        NucleiSelection.prototype.getNumPositive = function () {
            return this.numPositive;
        };

        /**
         * get number of negative nuclei selected
         */
        NucleiSelection.prototype.getNumNegative = function () {
            return this.numNegative;
        };

        /**
         * return total number of nuclei selected
         */
        NucleiSelection.prototype.getNumTotal = function () {
            return this.numNegative + this.numPositive;
        };

        /**
         * return the total count after the last resetUpdateCount()
         */
        NucleiSelection.prototype.getInitialTotal = function () {
            return this.initialTotal;
        };

        /**
         * return number of nuclei to remove on the server
         * - return a positive number (even though numNucleiToRemove is recorded as a negative number here)
         */
        NucleiSelection.prototype.getNumNucleiToRemove = function () {
            return 0 - this.numNucleiToRemove;
        };

        /**
         * return the number of newly selected nuclei to be added to the server
         */
        NucleiSelection.prototype.getNumNucleiToAdd = function () {
            return this.updateCount - this.numNucleiToRemove;
        };

        /**
         * parse the input string into an array of nuclei selection stored in
         * a NucleiSelections object
         * 
         * test case
         * parseNucleiSelectionParamString("211x1111y1347476601334p_322x2222y-9123n_422x2223y-9133p_522x2224y-9154p_622x2225y-9184p");
         * 
         * @param {type} nucleiSelectionParamString
         * @returns {nucleiSelection}
         */
        function parseNucleiSelectionParamString(nucleiSelectionParamString) {
            var numSet; // number of nuclei selections (i.e. > 1 if this is used to compare nuclei section for > 1 scorers)

            // String.prototype.startsWith is NOT support in IE 11 ... try to avoid it!!!
            if (startsWith_IE11(nucleiSelectionParamString, COMPARE_TAG)) {
                numSet = nucleiSelectionParamString.split(BEGIN_TAG).length;
            } else {
                numSet = 1;
            }

            var nucleiSelection = new NucleiSelection();

            // if nucleiSelectionParamString is empty, do nothing just return nucleiSelection
            if (nucleiSelectionParamString !== "") {
                nucleiSelectionParamString = nucleiSelectionParamString.replaceAll(COMPARE_TAG + NUCLEI_SELECTION_PARAM_STRING_DELIMITER, "");

                var paramStringArr = nucleiSelectionParamString.split(NUCLEI_SELECTION_PARAM_STRING_DELIMITER);

                var timeOfPreviousNuclei = NUCLEI_TIME_NOT_SET; // keep track of time of previous nuclei

                for (var i = 0; i < paramStringArr.length; i++) {
                    var paramString = paramStringArr[i];
                    if (paramString === BEGIN_TAG) {
                        // this is start of a new set of nuclei selection
                        // add nuclei with state BEGIN
                        nucleiSelection.addNuclei(0, 0, -1, NUCLEI_STATE_BEGIN);
                    } else {
                        // try to figure out among the three types of nuclei selection
                        // e.g. 12x45yp_98x65yp
                        //      12x45y1003000p_98x65y10p 
                        //      1003000p_1000p   

                        var x = X_NOT_SET;
                        var y = Y_NOT_SET;
                        var time;
                        // String.prototype.includes is NOT support in IE 11 ... try to avoid it!!!
                        if (paramString.indexOf(X_LABEL) !== -1) {
                            // must have coordinates
                            // format either:
                            // 12x45yp_98x65yp OR 12x45y1003000p_98x65y10p 
                            var arr = paramString.split(X_LABEL);
                            x = parseInt(arr[0]);
                            var arr2 = arr[1].split(Y_LABEL);
                            y = arr2[0];
                            time = (arr2[1].length > 1) ? parseInt(arr2[1].substring(0, arr2[1].length - 1)) * 1000 : NUCLEI_TIME_NOT_SET; // since time in Nuclei is in milliseconds
                        } else {
                            // no coordinates - MUST have time ... currently do not support no time AND no coordinates
                            // format: 123p
                            time = parseInt(paramString.substring(0, paramString.length - 1)) * 1000; // since time in Nuclei is in milliseconds
                        }

                        // need to figure out if time is absolute time or time from previous nuclei
                        if (timeOfPreviousNuclei === NUCLEI_TIME_NOT_SET) {
                            // must be either:
                            // - first nuclei OR
                            // - time not set for ALL nuclei
                            timeOfPreviousNuclei = time;
                        } else {
                            // must have seen the first nuclei, the current nuclei is the
                            // time difference from previous nuclei
                            time = timeOfPreviousNuclei + time;
                            timeOfPreviousNuclei = time;
                        }

                        // note: String.prototype.endsWith is NOT supported in IE 11 ... try to avoid it!!!
                        if (endsWith_IE11(paramString, POSITIVE_LABEL)) {
                            nucleiSelection.addNuclei(x, y, time, NUCLEI_STATE_POSITIVE);
                        } else {
                            // if not positive, assume negative
                            nucleiSelection.addNuclei(x, y, time, NUCLEI_STATE_NEGATIVE);
                        }
                    }
                }
            }
            nucleiSelection.resetUpdateCount();
            return nucleiSelection;
        }

        /**
         * generate nuclei selection param string from the nuclei selection object
         * 
         * @param {type} nucleiSelection
         * @return param string
         */
        function generateNucleiSelectionParamString(nucleiSelection) {
            //e.g. 76_12x45y1000355p_98x65y-1p
            var result = "" + nucleiSelection.getNumNucleiToRemove();

            // figure out how many NOT newly selected nuclei
            // - these can be skipped - no need to update on server
            var numNucleiToSkip = nucleiSelection.getInitialTotal() - nucleiSelection.getNumNucleiToRemove();

            var coordinatesAvailable = false;
            var coordinatesChecked = false;
            var timeOfPreviousNuclei = NUCLEI_TIME_NOT_SET; // keep track of time of previous nuclei
            var carryOver = 0; // amount of time need to carry over because we are rounding

            for (var i = numNucleiToSkip; i < nucleiSelection.getNucleiSelected().length; i++) {
                var nuclei = nucleiSelection.getNucleiSelected()[i];
                var state = POSITIVE_LABEL;
                if (nuclei.getState() === NUCLEI_STATE_NEGATIVE) {
                    state = NEGATIVE_LABEL;
                }

                if (!coordinatesChecked) {
                    // check if coordinates available
                    coordinatesAvailable = (nuclei.getX() !== X_NOT_SET);
                    coordinatesChecked = true;
                }

                // calculate the time
                var time = nuclei.getTime();
                // NOTE: time in NucleiSelectionParamString is in SECONDS NOT milliseconds
                if (timeOfPreviousNuclei === NUCLEI_TIME_NOT_SET) {
                    // either this is the first nuclei or time not set for all nuclei
                    timeOfPreviousNuclei = (time === NUCLEI_TIME_NOT_SET) ? NUCLEI_TIME_NOT_SET : time / 1000; // time stored in seconds
                    time = time / 1000;
                } else {
                    // NOTE: time stored in Nuclei object is ALWAYS absolute and in milliseconds
                    time = time / 1000;
                    var timeDiff = time - timeOfPreviousNuclei;
                    timeOfPreviousNuclei = time;
                    time = timeDiff+carryOver;
                    carryOver = time-Math.round(time)
                }
                time = Math.round(time); // time needs to be integer
                result = result + NUCLEI_SELECTION_PARAM_STRING_DELIMITER + (coordinatesAvailable ? Math.round(nuclei.getX()) + X_LABEL + Math.round(nuclei.getY()) + Y_LABEL : "") + ((time === Nuclei.NUCLEI_TIME_NOT_SET) ? "" : time) + state;
            }
            return result;
        }

        /**
         * 
         * @param {type} imageURL
         * @param {type} canvasId
         * @param {type} drawThumbnailWindow
         * @param {type} counterImageURL - if not emty, cell counter only
         * @param {type} negativeSoundURL,
         * @param {type} positiveSoundURL,
         * undoSoundURL,
         * notificationSoundURL,
         * @param {type} nucleiSelectionParamString
         * @param {type} readOnly
         * @returns {NucleiCounter}
         */
        var NucleiCounter = function (
                imageURL,
                canvasId,
                drawThumbnailWindow,
                counterImageURL,
                negativeSoundURL,
                positiveSoundURL,
                undoSoundURL,
                notificationSoundURL,
                nucleiSelectionParamString,
                readOnly) {
            this.counterOnly = counterImageURL !== "";
            this.nucleiSelection = parseNucleiSelectionParamString(nucleiSelectionParamString);
            this.nucleiSelected = this.nucleiSelection.getNucleiSelected();
            if (!this.counterOnly) {
                // nuclei counter with zoomable image
                Zoompan.call(this, imageURL, canvasId, drawThumbnailWindow);
                this.showNucleiSelection = true;
                var nucleiCounter = this;
                // add mouse event handler
                if (this.canvas.addEventListener && !readOnly) {
                    this.canvas.addEventListener("dblclick", function (e) {
                        clearSelection();
                        //console.log(e.clientX+" "+e.clientY);
                        var clientRect = nucleiCounter.canvas.getBoundingClientRect();
                        if (e.shiftKey) {
                            nucleiCounter.nucleiSelection.addNuclei(
                                    nucleiCounter.vw.dXToSX(e.clientX - clientRect.left, nucleiCounter.canvas.width, nucleiCounter.canvas.height),
                                    nucleiCounter.vw.dYToSY(e.clientY - clientRect.top, nucleiCounter.canvas.width, nucleiCounter.canvas.height),
                                    (new Date()).getTime(), NUCLEI_STATE_POSITIVE);
                            nucleiCounter.updateNucleiCountOnBrowser();
                            nucleiCounter.repaint();
                        } else if (e.ctrlKey) {
                            nucleiCounter.nucleiSelection.addNuclei(
                                    nucleiCounter.vw.dXToSX(e.clientX - clientRect.left, nucleiCounter.canvas.width, nucleiCounter.canvas.height),
                                    nucleiCounter.vw.dYToSY(e.clientY - clientRect.top, nucleiCounter.canvas.width, nucleiCounter.canvas.height),
                                    (new Date()).getTime(), NUCLEI_STATE_NEGATIVE);
                            nucleiCounter.updateNucleiCountOnBrowser();
                            nucleiCounter.repaint();
                        }
                    }, false);
                }
            } else {
                // cell counter only
                // NOTE this no longer inherit Zoompan!!!
                // if counter only, there is no zoompan ... just an image of nuclei counter
                this.img = new Image();
                this.img.src = counterImageURL;
                this.canvas = document.getElementById(canvasId);
                this.ctx = this.canvas.getContext('2d'); // the canvas context
                this.ctx.fillStyle = "white"; // fill color
                // sound for +/- nuclei selection ...
                this.negativeSound = new Audio(negativeSoundURL);
                this.positiveSound = new Audio(positiveSoundURL);
                this.undoSound = new Audio(undoSoundURL);
                this.ccDWidth = 0; // cell counter dwidth
                this.ccDHeight = 0; // cell counter dheight
                this.ccScale = 0; // cell counter scale
                this.ccDX = 0; // cell counter dX
                this.ccDY = 0; // cell counter dY
                this.negDispX = 0; // negative display box
                this.negDispY = 0;
                this.dispWidth = 0;
                this.dispHeight = 0;
                this.posDispX = 0; // positive display box
                this.posDispY = 0;
                this.dispFont = "px Arial";
                var cellCounter = this;
                this.img.onload = function () {
                    // cell counter only
                    if (cellCounter.canvas.width / cellCounter.canvas.height < cellCounter.img.width / cellCounter.img.height) {
                        cellCounter.ccDWidth = cellCounter.canvas.width;
                        cellCounter.ccScale = cellCounter.ccDWidth / cellCounter.img.width;
                        cellCounter.ccDHeight = cellCounter.img.height * cellCounter.ccScale;
                        cellCounter.ccDY = (cellCounter.canvas.height - cellCounter.ccDHeight) * 0.5;
                    } else {
                        cellCounter.ccDHeight = cellCounter.canvas.height;
                        cellCounter.ccScale = cellCounter.ccDHeight / cellCounter.img.height;
                        cellCounter.ccDWidth = cellCounter.img.width * cellCounter.ccScale;
                        cellCounter.ccDX = (cellCounter.canvas.width - cellCounter.ccDWidth) * 0.5
                    }
                    cellCounter.dispWidth = 40 * cellCounter.ccScale;
                    cellCounter.dispHeight = 20 * cellCounter.ccScale;
                    cellCounter.negDispX = 88 * cellCounter.ccScale + cellCounter.ccDX;
                    cellCounter.negDispY = 80 * cellCounter.ccScale + cellCounter.ccDY;
                    cellCounter.posDispX = 139 * cellCounter.ccScale + cellCounter.ccDX;
                    cellCounter.posDispY = cellCounter.negDispY;
                    cellCounter.dispFont = cellCounter.dispHeight + cellCounter.dispFont;
                    cellCounter.repaint();
                    // need to call cellCounter.updateNucleiCountOnBrowser() to initialize any help elements (e.g. cell counter input text box) on html page, when dojo is ready!!!
                };
            }
            this.notificationSound = new Audio(notificationSoundURL);
        };
        // NOTE: the following two lines are needed to inherit from Zoompan
        // Create a NucleiCounter.prototype object that inherits from Person.prototype.
        NucleiCounter.prototype = Object.create(Zoompan.prototype);
        // Set the "constructor" property to refer to NucleiCounter
        NucleiCounter.prototype.constructor = NucleiCounter;

        NucleiCounter.prototype.repaint = function () {
            this.ctx.beginPath(); // this is so to CLEAR the previous circles!!!
            if (!this.counterOnly) {
                // call Zoompan's repaint first
                //Object.getPrototypeOf(this.constructor.prototype).repaint.call(this);
                this.repaintMainWindow();
                // draw nuclei
                var orgLineWidth = this.ctx.lineWidth;
                this.ctx.lineWidth = NUCLEI_SELECTION_LINE_WIDTH;
                var nucleiSelectionRadius = NUCLEI_SELECTION_RADIUS * this.vw.getCurrMag();
                for (var i = 0; i < this.nucleiSelected.length; i++) {
                    var nuclei = this.nucleiSelected[i];
                    if (this.showNucleiSelection & nuclei.inView(this.vw)) {
                        // draw selected nuclei
                        if (nuclei.getState() === NUCLEI_STATE_POSITIVE) {
                            // draw red square
                            this.ctx.strokeStyle = NUCLEI_COLOR_POSITIVE;
                            this.ctx.strokeRect(
                                    nuclei.getDX(this.vw, this.canvas.width, this.canvas.height) - nucleiSelectionRadius,
                                    nuclei.getDY(this.vw, this.canvas.width, this.canvas.height) - nucleiSelectionRadius,
                                    nucleiSelectionRadius * 2,
                                    nucleiSelectionRadius * 2);
                        } else if (nuclei.getState() === NUCLEI_STATE_NEGATIVE) {
                            // draw red square
                            this.ctx.strokeStyle = NUCLEI_COLOR_NEGATIVE;
                            this.ctx.beginPath(); // this is needed
                            this.ctx.arc(
                                    nuclei.getDX(this.vw, this.canvas.width, this.canvas.height),
                                    nuclei.getDY(this.vw, this.canvas.width, this.canvas.height),
                                    nucleiSelectionRadius,
                                    0,
                                    2 * Math.PI);
                            this.ctx.stroke();
                            //this.ctx.closePath(); // don't know why this is not needed
                        }
                    }
                }
                //this.ctx.closePath(); // don't know why this is not needed
                this.ctx.lineWidth = orgLineWidth;
                if (this.drawThumbnailWindow) {
                    this.repaintThumbnailWindow();
                }
            } else {
                // cell counter
                this.ctx.drawImage(this.img, 0, 0, this.img.width, this.img.height,
                        this.ccDX, this.ccDY, this.ccDWidth, this.ccDHeight);
                // draw the cell count display boxes
                this.ctx.fillStyle = "white"; // fill color
                this.ctx.fillRect(this.negDispX, this.negDispY, this.dispWidth, this.dispHeight);
                this.ctx.fillRect(this.posDispX, this.posDispY, this.dispWidth, this.dispHeight);
                this.ctx.fillStyle = "black"; // text color
                this.ctx.font = this.dispFont;
                var xoffset = this.dispWidth * 0.05;
                var yoffset = this.dispHeight * 0.9;
                this.ctx.fillText(this.nucleiSelection.getNumNegative(), this.negDispX + xoffset, this.negDispY + yoffset);
                this.ctx.fillText(this.nucleiSelection.getNumPositive(), this.posDispX + xoffset, this.posDispY + yoffset);
            }
        }

        /**
         * re-initialize applet with new nucleiSelectionParamString
         *
         * @param nucleiSelectionParamString
         * @param readOnly
         */
        NucleiCounter.prototype.reinit = function (nucleiSelectionParamString, readOnly) {
            this.nucleiSelection = parseNucleiSelectionParamString(nucleiSelectionParamString);
            this.nucleiSelected = this.nucleiSelection.getNucleiSelected();
            this.updateNucleiCountOnBrowser(); // call javascript to update browser nuclei count
            //revalidate(); seems to cause black out screen?
            this.repaint();
        };

        //////////////////////////////////////////////////
        // functions for listeners



        /////////////////////////////////////////////////
        // the following are required interface

        /**
         * upload nuclei selection
         */
        NucleiCounter.prototype.uploadNucleiSelection = function () {
            // the following function is defined in the html page
            // assume uploadNucleiSelection() returns the xhr handle
            return uploadNucleiSelection(generateNucleiSelectionParamString(this.nucleiSelection));
        };

        /**
         * repaint the applet, showing the wait image. 
         */
        NucleiCounter.prototype.showWaitImage = function () {
            this.ctx.fillStyle = "white"; // fill color
            this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);
            this.ctx.fillStyle = "black"; // text color
            var fontSize = Math.min(24, this.canvas.height);
            this.ctx.font = fontSize + "px Arial";
            this.ctx.fillText("Please wait ...", 0, fontSize);
        };

        /**
         * show wait message
         */
        NucleiCounter.prototype.startLoadingWaitImageMsg = function (waitImageMessage) {
            this.ctx.fillStyle = "white"; // fill color
            this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);
            this.ctx.fillStyle = "black"; // text color
            var fontSize = Math.min(24, this.canvas.height);
            this.ctx.font = fontSize + "px Arial";
            this.ctx.fillText(waitImageMessage, 0, fontSize);
        };

        /**
         * show wait image with no message
         */
        NucleiCounter.prototype.startLoadingWaitImage = function () {
            this.startLoadingWaitImageMsg("Loading... please wait ...");
        };

        /**
         * end the loading image
         */
        NucleiCounter.prototype.endLoadingWaitImage = function () {
            // no need to do anything
        };

        /**
         * add positive nuclei no coordinates
         */
        NucleiCounter.prototype.addPositiveNucleiCount = function () {
            this.nucleiSelection.addNuclei(
                    X_NOT_SET,
                    Y_NOT_SET,
                    (new Date()).getTime(), NUCLEI_STATE_POSITIVE);
            this.playAddPositiveNucleiSound();
            this.updateNucleiCountOnBrowser();
            this.repaint();
        };

        /**
         * add negative nuclei no coordinates
         */
        NucleiCounter.prototype.addNegativeNucleiCount = function () {
            this.nucleiSelection.addNuclei(
                    X_NOT_SET,
                    Y_NOT_SET,
                    (new Date()).getTime(), NUCLEI_STATE_NEGATIVE);
            this.playAddNegativeNucleiSound();
            this.updateNucleiCountOnBrowser();
            this.repaint();
        };

        /**
         * remove last selected nuclei
         */
        NucleiCounter.prototype.removeLastNucleiSelection = function () {
            if (this.nucleiSelection.getNumTotal() !== 0) {
                this.nucleiSelection.removeNuclei();
                this.playUndoNucleiSound(); // play sound
                this.updateNucleiCountOnBrowser();
                this.repaint();
            } // do nothing if no nuclei selected
        };

        /**
         * remove last selected nuclei
         */
        NucleiCounter.prototype.undoNucleiCount = function () {
            this.removeLastNucleiSelection();
        };

        /**
         * get number of positive
         *
         * @return
         */
        NucleiCounter.prototype.getNumPositive = function () {
            return this.nucleiSelection.getNumPositive();
        };

        /**
         * get number of negative
         *
         * @return
         */
        NucleiCounter.prototype.getNumNegative = function () {
            return this.nucleiSelection.getNumNegative();
        };

        /**
         * get number of nuclei to removve
         *
         * @return
         */
        NucleiCounter.prototype.getNumToRemove = function () {
            return this.nucleiSelection.getNumNucleiToRemove();
        };

        /**
         * get total number of nuclei
         *
         * @return
         */
        NucleiCounter.prototype.getNumTotal = function () {
            return this.nucleiSelection.getNumTotal();
        };

        /**
         * update nuclei count on browser
         * @returns {undefined}
         */
        NucleiCounter.prototype.updateNucleiCountOnBrowser = function () {
            // the following function is defined in the html page
            updateNucleiSelectionCount(
                    this.getNumPositive(),
                    this.getNumNegative(),
                    this.getNumTotal()
                    );
        };

        /**
         * undo n nuclei
         *
         * @param n
         */
        NucleiCounter.prototype.unNucleiCount = function (n) {
            for (var i = 0; i < n; i++) {
                this.removeLastNucleiSelection();
            }
        };

        /**
         * return nuclei selection
         *
         * @return
         */
        NucleiCounter.prototype.getNucleiSelection = function () {
            return nucleiSelection;
        };

        NucleiCounter.prototype.setNucleiSelectionVisible = function (visible) {
            this.showNucleiSelection = visible;
            // TODO
            this.repaint(); // REPAINT!!!!   
        };

        NucleiCounter.prototype.playAddPositiveNucleiSound = function () {
            if (this.positiveSound) {
                this.positiveSound.play();
            }
        };

        NucleiCounter.prototype.playAddNegativeNucleiSound = function () {
            if (this.negativeSound) {
                this.negativeSound.play();
            }
        };

        NucleiCounter.prototype.playUndoNucleiSound = function () {
            if (this.undoSound) {
                this.undoSound.play();
            }
        };

        NucleiCounter.prototype.playNotificationSound = function () {
            if (this.notificationSound) {
                this.notificationSound.play();
            }
        };
        </script>

</head>
<body>
    <div class="body">
        <g:if test="${!showReference && !showPageBodyOnly}">
            <h1>Scoring session: ${scoring_sessionInstance.name}</h1>
            <g:showFlashMessage />
            <p><g:display_scoring_session_progress id="${scoring_sessionInstance.id}"/></p>
            <!-- show timer if user has not submitted the results yet ...-->
            <g:if test="${tma_scoringInstance?.showIsAllowedToUpdateScore() || whole_section_scoring?.showIsAllowedToUpdateScore()}">
                <!-- <assert:script>timeMsg();</assert:script> -->
            </g:if>
            </g:if>
            <g:elseif test="${showReference}"><h1>${referenceTitle}</h1></g:elseif>
        <g:elseif test="${showHE}"><h1>H&E image</h1></g:elseif>
        <g:elseif test="${showMM}"><h1>Myoepithelial marker image</h1></g:elseif>
        <g:display_scoring_image_option scoring="${scoring}"  showReference="${showReference}"  showPageBodyOnly="${showPageBodyOnly}" showHE="${showHE}" showMM="${showMM}" />
        <br>
        <div id="scoring_session_form" dojoType='dijit.form.Form' action="${createLink(base:grailsApplication.config.grails.serverSecureURL, controller:"scoring_sessions", action:"saveScore")}" method='POST'>
            <g:display_scoring_objects_ids scoring_session="${scoring_sessionInstance}" tma_scoring="${tma_scoringInstance}" whole_section_scoring="${whole_section_scoring}"/>
            <div class="dialog" style="width: ${ImageViewerMethods.IMAGE_VIEWER_WIDTH+50}px">
                <g:display_scoring_objects_description_and_navigation scoring_session="${scoring_sessionInstance}" tma_scoring="${tma_scoringInstance}" whole_section_scoring="${whole_section_scoring}" showPageBodyOnly="${showPageBodyOnly}" showHE="${showHE}" showMM="${showMM}"/>
                <g:if test="${showHE}">
                    <g:display_scoring_object_he scoring_session="${scoring_sessionInstance}" tma_scoring="${tma_scoringInstance}" whole_section_scoring="${whole_section_scoring}"/>
                </g:if> 
                <g:elseif test="${showMM}">
                    <g:display_scoring_object_mm scoring_session="${scoring_sessionInstance}" tma_scoring="${tma_scoringInstance}" whole_section_scoring="${whole_section_scoring}"/>
                </g:elseif>
                <g:else>
                    <table>
                        <tbody>
                            <tr class="prop">
                                <g:if test="${tma_scoringInstance.showIsScoringTypeNucleiCount() || tma_scoringInstance.showIsScoringTypeNucleiCountNoCoord()}">
                                    <g:display_scoring_commands tma_scoring="${tma_scoringInstance}" />
                                </tr>
                                <g:if test="${!showHE && !showMM}">                
                                    <tr class="prop">
                                        <g:display_nuclei_count_status 
                                        numNeg_id="${Scoring_sessionsConstants.NUCLEI_COUNT_STATUS_NUM_NEG_ID}" 
                                        numPos_id="${Scoring_sessionsConstants.NUCLEI_COUNT_STATUS_NUM_POS_ID}" 
                                        numTotal_id="${Scoring_sessionsConstants.NUCLEI_COUNT_STATUS_NUM_TOTAL_ID}"/>
                                    </tr>  
                                    <g:if test="${tma_scoringInstance.showIsScoringTypeNucleiCount()}"><tr class="prop"><g:display_tma_scoring_image_zoom_pan_command /></tr></g:if>
                                </g:if>    
                                <tr class="prop"> 
                                    <td colspan=2 style="width:${tma_scoringInstance.showIsScoringTypeNucleiCountNoCoord()?"500":ImageViewerMethods.IMAGE_VIEWER_WIDTH}px">
                                        <g:if test="${tma_scoringInstance.showIsScoringTypeNucleiCount()}"><g:display_nuclei_selector_applet tma_scoring="${tma_scoringInstance}"/></g:if>
                                        <g:elseif test="${tma_scoringInstance.showIsScoringTypeNucleiCountNoCoord()}"><g:display_nuclei_counter_with_sector_map tma_scoring="${tma_scoringInstance}"/></g:elseif>
                                    </g:if>
                                    <g:else>
                                    <td colspan=2>${tma_scoringInstance.tma_core_image.showImageHtml(createLink(uri: '/').toString(),ImageViewerMethods.IMAGE_VIEWER_WIDTH,ImageViewerMethods.IMAGE_VIEWER_HEIGHT)}
                                    </g:else>
                                </g:else>
                            </td>
                        </tr>
                        <tr class="prop">
                            <g:if test="${!tma_scoringInstance?.showIsScoringTypeNucleiCount() && !tma_scoringInstance?.showIsScoringTypeNucleiCountNoCoord() && !showHE && !showMM && !showPageBodyOnly}">
                                <td valign="top" class="name">${tma_scoringInstance.showIsAllowedToUpdateScore()?"Please enter score:":"Score entered:"}</td>
                                <td valign="top" class="value">
                                    <g:if test="${tma_scoringInstance.showIsAllowedToUpdateScore()}"><g:textField id="inputScore" name="inputScore" value="${tma_scoringInstance.showScore()}"/></g:if>
                                    <g:else>${tma_scoringInstance.showScore()}</g:else>
                                    </td>
                            </g:if>
                            <g:else>
                                <g:if test="${!showHE && !showMM && tma_scoringInstance?.showIsScoringTypeNucleiCount()}">
                                  <!-- no need to repeat display of commands/nuclei count for Nuclei count no-coord -->
                                    <g:display_tma_scoring_image_zoom_pan_command /></tr>
                                <tr class="prop">
                                    <g:display_nuclei_count_status 
                                    numNeg_id="${Scoring_sessionsConstants.NUCLEI_COUNT_STATUS_NUM_NEG_BOTTOM_ID}" 
                                    numPos_id="${Scoring_sessionsConstants.NUCLEI_COUNT_STATUS_NUM_POS_BOTTOM_ID}" 
                                    numTotal_id="${Scoring_sessionsConstants.NUCLEI_COUNT_STATUS_NUM_TOTAL_BOTTOM_ID}"/>
                                </g:if>
                            </g:else>
                        </tr>
                        <g:if test="${tma_scoringInstance?.showIsAllowedToUpdateScore() && !showHE && !showMM && !showPageBodyOnly}">
                            <tr>
                                <td></td><td><g:display_nuclei_counter_undo_save_button tma_scoring="${tma_scoringInstance}"/></td>
                            </tr>
                            <tr>
                                <td valign="top" class="name">Please enter any comment (optional):</td>
                                <td valign="top" class="value"><g:textArea id="inputComment" name="inputComment" dojoType="dijit.form.Textarea" value="${tma_scoringInstance.showScoringComment()}" style="width:300px;"/></td>
                            </tr>
                        </g:if>
                    </tbody>
                </table>
            </div>

            <g:if test="${tma_scoringInstance?.showIsAllowedToUpdateScore() && !showPageBodyOnly}">
                <g:if test="${showHE || showMM}">
                    <i>please go back to non-H&E view to save score and to continue scoring</i>
                </g:if>
                <g:else>
                    <div class="buttons_large_font">
                        <span class="button">
                            <g:if test="${tma_scoringInstance?.showIsScoringTypeNucleiCount() || tma_scoringInstance?.showIsScoringTypeNucleiCountNoCoord()}">
                                <button 
                                dojoType='dijit.form.Button' 
                                type="submit" 
                                value="save" 
                                onclick="cleanUp(); _tempSubmitFormAfterUploadNucleiSelection=true; nucleiCounter.uploadNucleiSelection(); return false">save score and go to next image</button>
                            </g:if>
                            <g:else>
                                <button 
                                dojoType='dijit.form.Button' 
                                type="submit" 
                                value="save" 
                                onclick="cleanUp(); return confirm('Scored entered = '+document.getElementById('inputScore').value+'.\\nContinue to save score?'">save score and go to next image</button>	
                            </g:else>
                        </span>
                    </div>
                </g:else>
            </g:if>


        </div> <!-- dijit.form.Form -->

    </div>

<asset:script>
    <g:if test="${!showHE && !showMM}">
        var _tempFirstTimeLoading = true;
        var _tempResetInProgress = false;
        var _tempSubmitFormAfterUploadNucleiSelection = false;
        function updateNucleiSelectionCount(numPos, numNeg, numTotal) {
        updateNucleiSelectionCountHelper(numPos, numNeg, numTotal, 
        ${tma_scoringInstance?.showIsScoringTypeNucleiCount()?"true":"false"},//isScoringTypeNucleiCount, 
        <g:tma_scoring_nuclei_selection_notification_nuclei_counts id="${tma_scoringInstance?.getId()}"/>, //nuclei_selection_notification_nuclei_count_array
        <g:tma_scoring_nuclei_selection_notification_messages id="${tma_scoringInstance?.getId()}"/> //nuclei_selection_notification_message_array
        );}

        function uploadNucleiSelection(nucleiSelectionParamString) {
        var inputComment = document.getElementById('inputComment').value;

        showWaitDialogMsg(1,"uploading ...","uploading nuclei selections ... please wait")

        require(["dojo/_base/xhr"], function(xhr){
        xhr.post({
            url: "${createLink(controller:"tma_scorings", action:"uploadNucleiSelection")}", // read the url: from the action="" of the <form>
            timeout: 120000, // give up after 120 seconds
            content: { id:"${tma_scoringInstance?.getId()}", nucleiSelectionParamString:nucleiSelectionParamString, inputComment:inputComment },
            load: function(x){
                closeWaitDialog();
                checkUpdateNucleiSelection(x,window.location);
                if (_tempSaveStatusOK) {
                    if (_tempSubmitFormAfterUploadNucleiSelection) {
                        showWaitDialogMsg(1, "Nuclei selection saved", "Nuclei selection saved successfully.  Loading next page ... please wait");
                        // submit form!!!
                        dijit.byId("scoring_session_form").submit();
                    } else {
                        showMessageDialog("Nuclei selection saved", "Nuclei selection saved successfully.");
                    }
                }
                nucleiCounter.nucleiSelection.resetUpdateCount(); // NEED TO RESET COUNTERS!!!        
                nucleiCounter.repaint();
            }
            }); // xhr.post
        }); // function(xhr)

        } // function uploadNucleiSelection(nucleiSelectionParamString)
        
        var heWindowHandle = null;
        var refWindowHandle = null;

        function cleanUp() {
            cleanUpBeforeChangeMainWindow([heWindowHandle,refWindowHandle]);
            return true;
        }

            function openHeWindow() {heWindowHandle=window.open("<g:display_tma_scoring_he_link id="${tma_scoringInstance?.getId()}"/>","${DisplayConstant.H_AND_E_WINDOW}",JAVASCRIPT_OPEN_POPUP_WINDOW_SPECS);}
        </g:if>
</asset:script>

</body>
</html>
