<!--
  actual scoring (nuclei count on core biopsy glass) for Ki67-QC phase 3
-->
<%@ page import="ca.ubc.gpec.tmadb.DisplayConstant"%>
<%@ page import="ca.ubc.gpec.tmadb.ImageViewerMethods"%>
<%@ page import="ca.ubc.gpec.tmadb.util.ViewConstants"%>
<%@ page import="ca.ubc.gpec.tmadb.util.MiscUtil"%>
<%@ page import="ca.ubc.gpec.tmadb.util.Scoring_sessionsConstants"%>
<%@ page import="ca.ubc.gpec.tmadb.Whole_section_scorings"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Scoring session: ${scoring_sessionInstance.getName()}</title>
        

        <asset:javascript src="ca/ubc/gpec/tmadb/image_helpers/fieldselector.js"/>
        <asset:javascript src="ca/ubc/gpec/tmadb/image_helpers/zoompan.js"/>
        <asset:javascript src="ca/ubc/gpec/tmadb/scoring_sessions/ki67_qc_phase3.js"/>

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
        <g:if test="${!showPageBodyOnly}">
            <h1>Scoring session: ${scoring_sessionInstance.getName()}</h1>
            <g:showFlashMessage />
            <p><g:display_scoring_session_progress id="${scoring_sessionInstance.getId()}"/></p>
            <!-- show timer if user has not submitted the results yet ...-->
            <g:if test="${whole_section_scoring?.showIsAllowedToUpdateScore()}">
                <!-- <asset:script>timeMsg();</asset:script> -->
            </g:if>
            </g:if>
            <g:display_scoring_image_option scoring="${scoring}"  showReference="${showReference}"  showPageBodyOnly="${showPageBodyOnly}" showHE="${showHE}" showMM="${showMM}" />
        <br>
        <div dojoType='dijit.form.Form' action="" method='POST' id="${ViewConstants.HTML_FORM_NAME_KI67_QC_PHASE3_INIT}">
            <g:display_scoring_objects_ids scoring_session="${scoring_sessionInstance}" tma_scoring="${null}" whole_section_scoring="${whole_section_scoring}"/>
            <div class="dialog">
                <g:display_scoring_objects_description_and_navigation scoring_session="${scoring_sessionInstance}" tma_scoring="${null}" whole_section_scoring="${whole_section_scoring}" showPageBodyOnly="${showPageBodyOnly}"/>
                <table>
                    <tr>
                        <td><g:display_field_selector_applet whole_section_scoring="${whole_section_scoring}" scoringFieldsAllocator="${scoringFieldsAllocator}" width="550" height="550" state="${ImageViewerMethods.FIELD_SELECTOR_STATE_SCORING}"/></td>
                        <td>
                            <table>
                                <tr><g:display_scoring_commands id="scoring_command_id" whole_section_scoring="${whole_section_scoring}" displayInOneTd="true"/></tr>
                                <tr><td style="vertical-align: middle"><div id="beforeNucleiCounterAppletActiveDiv">
                                            <g:if test="${whole_section_scoring?.showIsAllowedToUpdateScore()}">
                                                <span style="background-color: yellow">Please click on ${whole_section_scoring.getState()==Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_COUNT_HOTSPOT ? "the hotspot":"a field of view"} to begin scoring.</span><br><br>
                                                <button dojoType='dijit.form.Button'
                                                onclick="return go_back();"
                                                title="click me to go back to previous step i.e. select ${whole_section_scoring.getState()==Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_COUNT_HOTSPOT ? "hotspot":"fields of view"}">BACK</button>
                                            </g:if>
                                            <g:else>
                                                <span style="background-color: yellow">Please select a field of view to view scores.</span> 
                                            </g:else>
                                        </div>
                                        <div id="${ViewConstants.HTML_ELEMENT_ID_NUCLEI_COUNTER_DIV}"><g:display_nuclei_counter_for_field_selector_applet whole_section_scoring="${whole_section_scoring}"/></div>
                                        <g:if test="${!(whole_section_scoring?.showIsAllowedToUpdateScore())}">
                                            <br><br>
                                            <div>
                                                <table>
                                                    <tr><td><g:display_ki67_qc_phase3_init_input whole_section_scoring="${whole_section_scoring}" /></td></tr>
                                                    <tr><td><g:display_ki67_qc_phase3_estimate_percent_input whole_section_scoring="${whole_section_scoring}" /></td></tr>
                                                </table>
                                                <br><br>
                                                <g:if test="${whole_section_scoring.showScoringComment().length()>0}">
                                                    User's comment (on whole slide):<br>
                                                    <textarea
                                                id='${ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_WHOLE_SECTION_SCORING_COMMENT}'
                                                    style='width:30em;'
                                                disabled='disabled'>${whole_section_scoring.showScoringComment()}</textarea>
                                            </g:if>
                                        </div>
                                    </g:if>
                                </td></tr>
                        </table>
                    </td>
                </tr>
            </table>
            <div style="display: none" id="${Scoring_sessionsConstants.NUCLEI_COUNT_STATUS_NUM_POS_ID}"></div>
            <div style="display: none" id="${Scoring_sessionsConstants.NUCLEI_COUNT_STATUS_NUM_NEG_ID}"></div>
            <div style="display: none" id="${Scoring_sessionsConstants.NUCLEI_COUNT_STATUS_NUM_TOTAL_ID}"></div>
        </div> <!-- <div class="dialog"> -->
    </div> <!-- <div dojoType='dijit.form.Form'> -->
</div>
<asset:script type="text/javascript">
    var _tempFirstTimeLoading = true;
    var _tempSaveStatusOK = false;
    var _tempResetInProgress = false;
    var _tempSuppressSaveOkMsg = false;
    
    <g:display_whole_section_scoring_he_mm_javascripts id="${whole_section_scoring?.getId()}"/>

    var whole_section_region_scoring_id = null; // this variable will be updated by displayNucleiCounterAppletBasedOnWhole_section_region_scoring()
    var number_of_remaining_fields = ${whole_section_scoring.showNumberOfUnscoredWhole_section_region_scorings()}; // number of remaining fields
    var scoring_command_display_style="";
    var nuclei_selection_notification_nuclei_count_array = [];
    var nuclei_selection_notification_message_array = [];

    <g:if test="${whole_section_scoring?.showIsAllowedToUpdateScore()}"> 
        // connect onkeypress event for keyStrokeListener
        require(['dojo/ready'], function(ready){ready(function(){
        dojo.connect(dojo.byId("keyStrokeListener"), "onkeypress", function(e){
        var result = sendCellCounterKeyStrokeToApplet(e);
        dojo.stopEvent(e); // prevent the keystroke event propagating to the text box
        return result;
        }); // dojo.connect(dojo.byId("keyStrokeListener"), "onkeypress", function(e){
        });}); // require(['dojo/ready'], function(ready){ready(function(){
    </g:if>

    // this function is called by nucleiCounter
    function updateNucleiSelectionCount(numPos, numNeg, numTotal) {
    // NOTE: currently the nuclei counter is deployed at page loade
    //       therefore, need to hide the associated input text boxes initially 
    //       as the user is NOT scoring yet
    //       whole_section_region_scoring_id is null initally
    if (whole_section_region_scoring_id == null) {
    // hide counter related buttons and text box
    <g:if test="${whole_section_scoring?.showIsAllowedToUpdateScore()}"> 
        document.getElementById("keyStrokeListenerTdId").style.display="none";
        document.getElementById("saveNucleiCountButtonId").style.display="none";
        document.getElementById("resetCounterButtonId").style.display="none";
        document.getElementById("doneWithThisFieldButtonId").style.display="none";
        document.getElementById("goBackButtonId").style.display="none";
    </g:if>
    document.getElementById("scoringInstruction").style.display="none";
    document.getElementById("commentInstruction").style.display="none";
    document.getElementById("inputComment").style.display="none";
    } else {
    // only update if really scoring!!!
    // special version for Ki67-QC phase 3 ... prevent counting when count >= nuclei_selection_notification_nuclei_count
    updateNucleiSelectionCountHelperKi67QcPhase3(numPos, numNeg, numTotal, false, nuclei_selection_notification_nuclei_count_array, nuclei_selection_notification_message_array);
    }
    // enable the nucleiCounter textbox since the nucleiCounter must be ready by this point
    if (dojo.byId("keyStrokeListener")!=null) {
    if (dojo.byId("keyStrokeListener").disabled) {
    dojo.byId("keyStrokeListener").value="";
    dojo.byId("keyStrokeListener").placeholder="Click here to begin counting using '${Scoring_sessionsConstants.NUCLEI_COUNT_KEY_POSITIVE}', '${Scoring_sessionsConstants.NUCLEI_COUNT_KEY_NEGATIVE}' and '${Scoring_sessionsConstants.NUCLEI_COUNT_KEY_UNDO}' keys"
    dojo.byId("keyStrokeListener").disabled=false;
    } // if (dojo.byId("keyStrokeListener")!=null)
    } // if (dojo.byId("keyStrokeListener").disabled)
    }

    function uploadNucleiSelection(nucleiSelectionParamString) {
    var inputComment = document.getElementById('inputComment').value;
    var xhrHandle = null;
    //showWaitDialogMsg(1,"uploading ...","uploading nuclei selections ... please wait");
    
    require(["dojo/_base/xhr"], function (xhr) {
        xhrHandle = xhr.post({
            url:  "${createLink(controller:"whole_section_region_scorings", action:"uploadNucleiSelection")}", // read the url: from the action="" of the <form>
            content: { id:whole_section_region_scoring_id, nucleiSelectionParamString:nucleiSelectionParamString, inputComment:inputComment },
            handleAs: "text",
            load: function (e) {
                //closeWaitDialog();
                checkUpdateNucleiSelection(e, "${createLink(controller:"scoring_sessions", action:"score",params:[id:scoring_sessionInstance.getId(), whole_section_scoring_id:whole_section_scoring.getId()])}");
                if (!_tempSaveStatusOK) {
                    showMessageDialog("ERROR", "ERROR. failed to save nuclei count.");
                } else {
                    if (nucleiSelectionParamString!=="0" && !_tempSuppressSaveOkMsg) { // nothing changed ... no need to show messsage
                        showMessageDialog("Nuclei count saved.", "Nuclei count saved.");
                    }
                    _tempSuppressSaveOkMsg = false; // reset this flag
                }
                nucleiCounter.nucleiSelection.resetUpdateCount(); // NEED TO RESET COUNTERS!!!        
                nucleiCounter.repaint();
            },
            onError: function (e) {
                alert("Error occurred. Failed to retrieve whole section region scoring record. Error message: " + e);
            }
        }); // xhr.post
    }); // function (xhr)
    return xhrHandle;
    } // function uploadNucleiSelection()
    
    //
    // helper function for displayNucleiCounterAppletBasedOnWhole_section_region_scoringCoordinates
    //
    function displayNucleiCounterAppletBasedOnWhole_section_region_scoringCoordinates_wo_uploadNucleiSelection (x, y, diameter, ki67state) {
        document.getElementById("beforeNucleiCounterAppletActiveDiv").style.display="none"; // hide the extra "back" button
    // display the hidden counter related td's
    <g:if test="${whole_section_scoring?.showIsAllowedToUpdateScore()}"> 
        document.getElementById("keyStrokeListenerTdId").style.display="block";
        document.getElementById("saveNucleiCountButtonId").style.display="block";
        document.getElementById("resetCounterButtonId").style.display="block";
        document.getElementById("doneWithThisFieldButtonId").style.display="block";
        document.getElementById("goBackButtonId").style.display="block";
    </g:if>
    document.getElementById("scoringInstruction").style.display="block";
    document.getElementById("commentInstruction").style.display="block";
    document.getElementById("inputComment").style.display="block";
    document.getElementById("inputComment").style.resize="both";
    // end of display the hidden counter related td's
    displayNucleiCounterAppletBasedOnWhole_section_region_scoringCoordinatesNeedAjaxUrl(
    "${ViewConstants.HTML_ELEMENT_ID_NUCLEI_COUNTER_DIV}", // appletDomElementId
    "${(MiscUtil.showIsLoggedIn(session)?grailsApplication.config.grails.serverSecureURL_noAppName:grailsApplication.config.grails.serverURL_noAppName)}", // baseUrl
    ${whole_section_scoring?.showIsAllowedToUpdateScore()}, // allowToUpdateScore
    "${createLink(controller:"whole_section_scorings", action:"ajax_start_scoring_whole_section_region_scoring_and_get_id", id:whole_section_scoring.getId())}",  
    x, 
    y, 
    diameter,
    ki67state,
    "${createLink(controller:"whole_section_region_scorings", action:"set_scoring_date")}",
    <g:if test="${whole_section_scoring.getState()==Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_COUNT_HOTSPOT}">
        "${null}", // null when scoring HOTSPOT
        "${createLink(controller:"scoring_sessions", action:"score", id:scoring_sessionInstance.getId(), params:[(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_SCORING_ID):whole_section_scoring.getId()])}" // nextUrl 
    </g:if>
    <g:else>
        "${createLink(controller:"whole_section_scorings", action:"set_scoring_date", id:whole_section_scoring.getId())}",
        "${createLink(controller:"scoring_sessions", action:"score", id:scoring_sessionInstance.getId())}" // nextUrl 
    </g:else>
    );
    document.getElementById('scoring_command_id').style.display=scoring_command_display_style; // display scoring commands
    } // function displayNucleiCounterAppletBasedOnWhole_section_region_scoringCoordinates_wo_uploadNucleiSelection (x, y, diameter, ki67state) {
    
    //
    // function to activate nuclei counter as well as get the whole_section_region_scoring_id
    // - will try to save nuclei selection first
    // NOTE: x, y are in full res image coordinates (NOT low res, as stored in applet)
    //       ki67state is name of the ki67state on SERVER NOT how it is represented on the client
    function displayNucleiCounterAppletBasedOnWhole_section_region_scoringCoordinates (x, y, diameter, ki67state) {
    // show nuclei counter
    document.getElementById("${ViewConstants.HTML_ELEMENT_ID_NUCLEI_COUNTER_DIV}").style.visibility='visible';
    <g:if test="${whole_section_scoring?.showIsAllowedToUpdateScore()}"> // save current nuclei selection - ONLY if allowed to update
        if (whole_section_region_scoring_id != null) {
            try {
                // NOTE: nucleiCounter.uploadNucleiSelection() will call the javascript function uploadNucleiSelection to do the real uploading
                nucleiCounter.uploadNucleiSelection().then(function(){
                    if (!_tempSaveStatusOK) {
                        alert('ERROR! nuclei selection save failed');
                    } else {
                        displayNucleiCounterAppletBasedOnWhole_section_region_scoringCoordinates_wo_uploadNucleiSelection (x, y, diameter, ki67state); 
                    }
                });
            } catch (err) { // nucleiCounter failed to load!!!
                alert("An runtime error has occured.  All scores has been saved.  The page will now reload.  Please re-select the field to activate nuclei counter.");
                window.location = "${createLink(controller:"scoring_sessions", action:"score",params:[id:scoring_sessionInstance.getId(), whole_section_scoring_id:whole_section_scoring.getId()])}";
            }
        } else {
            displayNucleiCounterAppletBasedOnWhole_section_region_scoringCoordinates_wo_uploadNucleiSelection (x, y, diameter, ki67state); 
        }
    </g:if>
    <g:else>
        displayNucleiCounterAppletBasedOnWhole_section_region_scoringCoordinates_wo_uploadNucleiSelection (x, y, diameter, ki67state);
    </g:else>
    } // function displayNucleiCounterAppletBasedOnWhole_section_region_scoringCoordinates (x, y, diameter, ki67state) {
            
    // function to go back to previous step i.e. select fields
    function go_back() {
    ki67_qc_phase3_go_back_one_step(
    "${createLink(controller:"scoring_sessions", action:"ajax_ki67_qc_phase3_go_back",params:[id:scoring_sessionInstance.getId(), whole_section_scoring_id:whole_section_scoring.getId()])}", 
    "${createLink(controller:"scoring_sessions", action:"score",                      params:[id:scoring_sessionInstance.getId(), whole_section_scoring_id:whole_section_scoring.getId()])}");
    // clean up
    cleanUp();
    return false; // prevent page refresh
    } // function go_back() {

    <g:if test="${!showHE && !showMM}">
        require(['dojo/ready'], function(ready){ready(function(){
        scoring_command_display_style=document.getElementById('scoring_command_id').style.display;
        document.getElementById('scoring_command_id').style.display="none"; // need to initially hide scoring command since its not applicable until nuclei counter appears
        document.getElementById("${ViewConstants.HTML_ELEMENT_ID_NUCLEI_COUNTER_DIV}").style.visibility="hidden"; // want to hide the counter initially
        });});
    </g:if>

</asset:script>
</body>
</html>

