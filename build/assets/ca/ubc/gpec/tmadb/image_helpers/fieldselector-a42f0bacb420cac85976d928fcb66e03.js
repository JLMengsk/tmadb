var process = process || {env: {NODE_ENV: "development"}};
/* 
 * field selector - extends Zoompan
 * ref: https://developer.mozilla.org/en-US/docs/Web/JavaScript/Introduction_to_Object-Oriented_JavaScript
 */

// some constants
var FOV_VIEWING_STATE_CURRENT = "fov_view_state_current";
var FOV_VIEWING_STATE_PREVIEW = "fov_view_state_preview";
var FOV_VIEWING_STATE_NOT_CURRENT = "fov_view_state_not_current";
var FOV_SCORING_STATE_NOT_SCORED = "fov_scoring_state_not_scored";
var FOV_SCORING_STATE_SCORING = "fov_scoring_state_scoring";
var FOV_SCORING_STATE_SCORED = "fov_scoring_state_scored";
var FOV_KI67STATE_NEGLIGIBLE = "fov_ki67state_negligible";
var FOV_KI67STATE_LOW = "fov_ki67state_low";
var FOV_KI67STATE_MEDIUM = "fov_ki67state_medium";
var FOV_KI67STATE_HIGH = "fov_ki67state_high";
var FOV_KI67STATE_HOT_SPOT = "fov_ki67state_hot-spot";

var FIELD_SELECTOR_STATE_SELECTING = "field_selector_state_selecting";
var FIELD_SELECTOR_STATE_SCORING = "field_selector_state_scoring";
var FIELD_SELECTOR_STATE_READ_ONLY = "field_selector_state_read_only";

var FOV_COLOR_NEGLIGIBLE = "black";
var FOV_COLOR_LOW = "green";
var FOV_COLOR_MEDIUM = "yellow";
var FOV_COLOR_HIGH = "orange";
var FOV_COLOR_HOT_SPOT = "red";
var FOV_COLOR_UNKNOWN = "grey";

var FOV_PARAMSTRING_DELIMITER = "_";
var FOV_PARAMSTRING_TAG_X = "x";
var FOV_PARAMSTRING_TAG_Y = "y";
var FOV_PARAMSTRING_TAG_VIEWING_STATE_CURRENT = "c";
var FOV_PARAMSTRING_TAG_VIEWING_STATE_PREVIEW = "p";
var FOV_PARAMSTRING_TAG_VIEWING_STATE_NOT_CURRENT = "n";
var FOV_PARAMSTRING_TAG_SCORING_STATE_NOT_SCORED = "o";
var FOV_PARAMSTRING_TAG_SCORING_STATE_SCORING = "i";
var FOV_PARAMSTRING_TAG_SCORING_STATE_SCORED = "s";
var FOV_PARAMSTRING_TAG_KI67_PP = "pp";
var FOV_PARAMSTRING_KI67_PP_LEVEL_HIGHEST = 4; // highest percent positive i.e. hotstpot
var FOV_PARAMSTRING_KI67_PP_LEVEL_HIGH = 3; // high percent positive
var FOV_PARAMSTRING_KI67_PP_LEVEL_MEDIUM = 2; // med percent positive
var FOV_PARAMSTRING_KI67_PP_LEVEL_LOW = 1; // low percent positive
var FOV_PARAMSTRING_KI67_PP_LEVEL_NEGLIGIBLE = 0; // negligible percent positive
var KI67_QC_PHASE3_FIELD_SELECTION_PARAMSTRING_TAG_VIEWING_STATE_CURRENT = FOV_PARAMSTRING_TAG_VIEWING_STATE_CURRENT;
var KI67_QC_PHASE3_FIELD_SELECTION_PARAMSTRING_TAG_KI67_PP = FOV_PARAMSTRING_TAG_KI67_PP;
var KI67_QC_PHASE3_FIELD_SELECTION_PARAMSTRING_DELIMITER = FOV_PARAMSTRING_DELIMITER;
var KI67_QC_PHASE3_FIELD_SELECTION_PARAMSTRING_KI67_PP_LEVEL_HIGHEST = FOV_PARAMSTRING_KI67_PP_LEVEL_HIGHEST;
var KI67_QC_PHASE3_FIELD_SELECTION_PARAMSTRING_KI67_PP_LEVEL_HIGH = FOV_PARAMSTRING_KI67_PP_LEVEL_HIGH;
var KI67_QC_PHASE3_FIELD_SELECTION_PARAMSTRING_KI67_PP_LEVEL_MEDIUM = FOV_PARAMSTRING_KI67_PP_LEVEL_MEDIUM;
var KI67_QC_PHASE3_FIELD_SELECTION_PARAMSTRING_KI67_PP_LEVEL_LOW = FOV_PARAMSTRING_KI67_PP_LEVEL_LOW;
var KI67_QC_PHASE3_FIELD_SELECTION_PARAMSTRING_KI67_PP_LEVEL_NEGLIGIBLE = FOV_PARAMSTRING_KI67_PP_LEVEL_NEGLIGIBLE;

/**
 * translate client Ki67 state representation to how they are represented in the server
 * @param {type} ki67State
 * @returns {undefined}
 */
function mapClientToServerKi67State(ki67State) {
    switch (ki67State) {
        case FOV_KI67STATE_NEGLIGIBLE:
            return "negative";
            break;
        case FOV_KI67STATE_LOW:
            return "low";
            break;
        case FOV_KI67STATE_MEDIUM:
            return "medium";
            break;
        case FOV_KI67STATE_HIGH:
            return "high";
            break;
        case FOV_KI67STATE_HOT_SPOT:
            return "hot-spot";
            break;
        default:
            return null;
    }
}

/**
 * keep track of field selections
 * 
 * @param {type} x
 * @param {type} y
 * @param {type} diameter
 * @param {type} viewingState
 * @param {type} scoringState
 * @param {type} ki67State
 * @returns {FieldOfView}
 */
var FieldOfView = function (x, y, diameter, viewingState, scoringState, ki67State) {
    this.x = x;
    this.y = y;
    this.diameter = diameter;
    this.viewingState = viewingState;
    this.scoringState = scoringState;
    this.ki67State = ki67State;
};

FieldOfView.prototype.getX = function () {
    return this.x;
};
FieldOfView.prototype.getY = function () {
    return this.y;
};
FieldOfView.prototype.getDX = function (vw, canvasWidth, canvasHeight) {
    return (this.x - vw.getSX()) * vw.getCurrMag() + vw.getDX(canvasWidth, canvasHeight);
};
FieldOfView.prototype.getDY = function (vw, canvasWidth, canvasHeight) {
    return (this.y - vw.getSY()) * vw.getCurrMag() + vw.getDY(canvasWidth, canvasHeight);
};
FieldOfView.prototype.getDiameter = function () {
    return this.diameter;
};
FieldOfView.prototype.getViewingState = function () {
    return this.viewingState;
};
FieldOfView.prototype.getScoringState = function () {
    return this.scoringState;
};
FieldOfView.prototype.getKi67State = function () {
    return this.ki67State;
};
FieldOfView.prototype.getKi67StateColor = function () {
    var ki67StateColor = FOV_COLOR_UNKNOWN;
    switch (this.getKi67State()) {
        case FOV_KI67STATE_NEGLIGIBLE:
            ki67StateColor = FOV_COLOR_NEGLIGIBLE;
            break;
        case FOV_KI67STATE_LOW:
            ki67StateColor = FOV_COLOR_LOW;
            break;
        case FOV_KI67STATE_MEDIUM:
            ki67StateColor = FOV_COLOR_MEDIUM;
            break;
        case FOV_KI67STATE_HIGH:
            ki67StateColor = FOV_COLOR_HIGH;
            break;
        case FOV_KI67STATE_HOT_SPOT:
            ki67StateColor = FOV_COLOR_HOT_SPOT;
            break;
    }
    return ki67StateColor;
};
FieldOfView.prototype.getKi67StateText = function () {
    var ki67StateText = "UNKNOWN";
    switch (this.getKi67State()) {
        case FOV_KI67STATE_NEGLIGIBLE:
            ki67StateText = "NEGLIGIBLE";
            break;
        case FOV_KI67STATE_LOW:
            ki67StateText = "LOW";
            break;
        case FOV_KI67STATE_MEDIUM:
            ki67StateText = "MEDIUM";
            break;
        case FOV_KI67STATE_HIGH:
            ki67StateText = "HIGH";
            break;
        case FOV_KI67STATE_HOT_SPOT:
            ki67StateText = "HOT-SPOT";
            break;
    }
    return ki67StateText;
};

FieldOfView.prototype.isCurrentViewing = function () {
    return this.viewingState === FOV_VIEWING_STATE_CURRENT;
};
FieldOfView.prototype.isPreviewing = function () {
    return this.viewingState === FOV_VIEWING_STATE_PREVIEW;
};
FieldOfView.prototype.isScored = function () {
    return this.scoringState === FOV_SCORING_STATE_SCORED;
};
FieldOfView.prototype.isHotspot = function () {
    return this.ki67State === FOV_KI67STATE_HOT_SPOT;
};

FieldOfView.prototype.setX = function (x) {
    this.x = x;
};
FieldOfView.prototype.setY = function (y) {
    this.y = y;
};
FieldOfView.prototype.setDiameter = function (diameter) {
    this.diameter = diameter;
};
FieldOfView.prototype.setScoringState = function (scoringState) {
    this.scoringState = scoringState;
};
FieldOfView.prototype.setViewingState = function (viewingState) {
    this.viewingState = viewingState;
};
/**
 * check if this field of view contains the input x/y
 * @param {type} inputX
 * @param {type} inputY
 * @returns {Boolean}
 */
FieldOfView.prototype.inView = function (inputX, inputY) {
    return (inputX <= (this.x + this.diameter / 2)
            && inputX >= (this.x - this.diameter / 2)
            && inputY <= (this.y + this.diameter / 2)
            && inputY >= (this.y - this.diameter / 2));
};

/**
 * check to see if this field of view is within viewWindow
 * @param {type} viewWindow - defined in zoompan.js
 * @returns {Boolean}
 */
FieldOfView.prototype.inViewWindow = function (viewWindow) {
    return (viewWindow.getSX() <= (this.x + this.diameter / 2)
            && (viewWindow.getSX() + viewWindow.getSWidth()) >= (this.x - this.diameter / 2)
            && viewWindow.getSY() <= (this.y + this.diameter / 2)
            && (viewWindow.getSY() + viewWindow.getSHeight()) >= (this.y - this.diameter / 2));
};

/**
 * draw this field on the context (ctx)
 * 
 * currentViewing field: double circle, bold face word, background grey (instead of light grey)
 * 
 * @param {type} ctx
 * @param {type} viewWindow
 * @param {type} canvasWidth
 * @param {type} canvasHeight
 * @returns {undefined}
 */
FieldOfView.prototype.draw = function (ctx, viewWindow, canvasWidth, canvasHeight) {
    var fovDX = this.getDX(viewWindow, canvasWidth, canvasHeight);
    var fovDY = this.getDY(viewWindow, canvasWidth, canvasHeight);
    var fovRadius = (this.getDiameter() * viewWindow.getCurrMag()) / 2;
    var fovColor = this.getKi67StateColor();
    var fovTextFontSize = fovRadius / 3;

    ctx.strokeStyle = fovColor;
    ctx.lineWidth = 4;

    var boldText = false;
    var textBoxColor = "lightgrey";
    var drawCross = false;
    var drawDoubleCircle = false;

    // draw field differently depending on what viewing state it is in
    if (this.isCurrentViewing()) {
        // double circle
        drawDoubleCircle = true;
        textBoxColor = "grey";
        boldText = true;
    } else if (this.isPreviewing()) {
        // with a cross
        drawCross = true;
    }

    if (this.isScored()) {
        // dotted line
        ctx.setLineDash([5]);
    }

    ctx.beginPath(); // this is needed
    ctx.arc(
            fovDX,
            fovDY,
            fovRadius,
            0,
            2 * Math.PI);
    ctx.stroke();
    if (drawDoubleCircle) {
        ctx.arc(
                fovDX,
                fovDY,
                fovRadius + ctx.lineWidth*2,
                0,
                2 * Math.PI);
        ctx.stroke();
    }
    ctx.setLineDash([]); // remove dash line

    // draw a light grey rectangle for ki67StateText
    ctx.fillStyle = textBoxColor;
    ctx.fillRect(fovDX - fovRadius * 0.9, fovDY - fovTextFontSize * 0.9, fovRadius * 1.8, fovTextFontSize * 1.1);
    // write text
    ctx.fillStyle = fovColor;
    ctx.font = fovTextFontSize + "px Arial";
    if (boldText) {
        ctx.font = "bold " + fovTextFontSize + "px Arial";
    }
    ctx.textAlign = "center";
    ctx.fillText(this.getKi67StateText(), fovDX, fovDY);

    if (drawCross) {
        // draw cross
        ctx.moveTo(fovDX, fovDY - fovRadius * 1.25);
        ctx.lineTo(fovDX, fovDY + fovRadius * 1.25);
        ctx.stroke();
        ctx.moveTo(fovDX - fovRadius * 1.25, fovDY);
        ctx.lineTo(fovDX + fovRadius * 1.25, fovDY);
        ctx.stroke();
    }
};

/**
 * parse the field selection param string
 * 
 * parameter string format: (all numbers are in pixels, in coordinate system of ORIGINAL image i.e. not preview/lowres image)
 * [x-coordinate]x[y-coordinate]y[field diameter]pp[categorical staining level][viewing state flag][scoring state flag]_
 * 
 * viewing state flag
 * CURRENT = c
 * PREVIEW = p
 * NOT_CURRENT = n
 * 
 * scoring state flag
 * NOT_SCORED = o
 * SCORING = i
 * SCORED = s
 * 
 * e.g.
 * 3822x4856y4000pp0no_13474x4347y4000pp1no_34563x5981y4000pp2ns_46164x3418y4000pp4no_7299x1828y4000pp3cs
 * first selection @ x=3822, y=4856, negligible Ki67, field diameter=4000, not current viewing, not scored
 * second selection @ x=13474, y=4347, low Ki67, field diameter=4000, not current scoring, not scored
 * third selection @ x=34563, y=5981, medium Ki67, field diameter=4000, not current scoring, scored
 * 
 * the second selection is the current "scoring" field of view
 * 
 * IMPORTANT!!! FieldOfView object stored SCALED coordinates (NOT ORIGINAL COORDINATES)
 * 
 * @param {type} fieldSelectionParamString
 * @param {type} scaleToOriginal
 * @returns an array of FieldOfView objects
 */
function parseFieldSelectionParamString(fieldSelectionParamString, scaleToOriginal) {
    var fovs = []; // initialize array
    if (fieldSelectionParamString !== "") {
        var selectionStringArr = fieldSelectionParamString.split(FOV_PARAMSTRING_DELIMITER);
        for (var i = 0; i < selectionStringArr.length; i++) {
            var selectionString = selectionStringArr[i];
            var temp = selectionString.split(FOV_PARAMSTRING_TAG_X);
            if (temp.length !== 2) {
                console.log("trying to get x value, parsing: (" + selectionString + ") within: " + fieldSelectionParamString);
            }
            var x = parseInt(temp[0]);
            var temp2 = temp[1].split(FOV_PARAMSTRING_TAG_Y);
            if (temp2.length !== 2) {
                console.log("trying to get y value, parsing: (" + temp[1] + ") within: " + fieldSelectionParamString);
            }
            var y = parseInt(temp2[0]);
            // figure out scoring state ... need to check this first because ASSUME scoring flag ALWAYS appear after viewing flag
            var scoringState = FOV_SCORING_STATE_NOT_SCORED; // default
            if (endsWith_IE11(temp2[1], FOV_PARAMSTRING_TAG_SCORING_STATE_SCORING)) {
                scoringState = FOV_SCORING_STATE_SCORING;
                temp2[1] = temp2[1].substring(0, temp2[1].length - FOV_PARAMSTRING_TAG_SCORING_STATE_SCORING.length);
            } else if (endsWith_IE11(temp2[1], FOV_PARAMSTRING_TAG_SCORING_STATE_SCORED)) {
                scoringState = FOV_SCORING_STATE_SCORED;
                temp2[1] = temp2[1].substring(0, temp2[1].length - FOV_PARAMSTRING_TAG_SCORING_STATE_SCORED.length);
            } else if (endsWith_IE11(temp2[1], FOV_PARAMSTRING_TAG_SCORING_STATE_NOT_SCORED)) {
                scoringState = FOV_SCORING_STATE_NOT_SCORED;
                temp2[1] = temp2[1].substring(0, temp2[1].length - FOV_PARAMSTRING_TAG_SCORING_STATE_NOT_SCORED.length);
            }
            // figure out view state ... 
            var viewingState = FOV_VIEWING_STATE_NOT_CURRENT; // default
            if (endsWith_IE11(temp2[1], FOV_PARAMSTRING_TAG_VIEWING_STATE_CURRENT)) {
                viewingState = FOV_VIEWING_STATE_CURRENT;
                temp2[1] = temp2[1].substring(0, temp2[1].length - FOV_PARAMSTRING_TAG_VIEWING_STATE_CURRENT.length);
            } else if (endsWith_IE11(temp2[1], FOV_PARAMSTRING_TAG_VIEWING_STATE_PREVIEW)) {
                viewingState = FOV_VIEWING_STATE_PREVIEW;
                temp2[1] = temp2[1].substring(0, temp2[1].length - FOV_PARAMSTRING_TAG_VIEWING_STATE_PREVIEW.length);
            } else if (endsWith_IE11(temp2[1], FOV_PARAMSTRING_TAG_VIEWING_STATE_NOT_CURRENT)) {
                viewingState = FOV_VIEWING_STATE_NOT_CURRENT;
                temp2[1] = temp2[1].substring(0, temp2[1].length - FOV_PARAMSTRING_TAG_VIEWING_STATE_NOT_CURRENT.length);
            }

            var temp3 = temp2[1].split(FOV_PARAMSTRING_TAG_KI67_PP);
            var diameter = parseInt(temp3[0]);
            var ki67State;
            switch (parseInt(temp3[1])) {
                case FOV_PARAMSTRING_KI67_PP_LEVEL_HIGHEST:
                    ki67State = FOV_KI67STATE_HOT_SPOT;
                    break;
                case FOV_PARAMSTRING_KI67_PP_LEVEL_HIGH:
                    ki67State = FOV_KI67STATE_HIGH;
                    break;
                case FOV_PARAMSTRING_KI67_PP_LEVEL_MEDIUM:
                    ki67State = FOV_KI67STATE_MEDIUM;
                    break;
                case FOV_PARAMSTRING_KI67_PP_LEVEL_LOW:
                    ki67State = FOV_KI67STATE_LOW;
                    break;
                default:
                    ki67State = FOV_KI67STATE_NEGLIGIBLE;
                    break;
            }
            fovs.push(new FieldOfView(x, y, diameter, viewingState, scoringState, ki67State));
        }
    }
    // need to scale all fields so that all coordinates and diamters is in the scale of the low res image
    for (var i = 0; i < fovs.length; i++) {
        var fov = fovs[i];
        fov.setX(fov.getX() / scaleToOriginal);
        fov.setY(fov.getY() / scaleToOriginal);
        fov.setDiameter(fov.getDiameter() / scaleToOriginal);
    }
    return fovs;
}

/**
 * convert Ki67State to numeric code
 *
 * @param ki67State
 * @return
 */
function ki67StateToNumericCode(ki67State) {
    switch (ki67State) {
        case FOV_KI67STATE_HOT_SPOT:
            return FOV_PARAMSTRING_KI67_PP_LEVEL_HIGHEST;
        case FOV_KI67STATE_HIGH:
            return FOV_PARAMSTRING_KI67_PP_LEVEL_HIGH;
        case FOV_KI67STATE_MEDIUM:
            return FOV_PARAMSTRING_KI67_PP_LEVEL_MEDIUM;
        case FOV_KI67STATE_LOW:
            return FOV_PARAMSTRING_KI67_PP_LEVEL_LOW;
        case FOV_KI67STATE_NEGLIGIBLE:
            return FOV_PARAMSTRING_KI67_PP_LEVEL_NEGLIGIBLE;
        default:
            return -1; // unknown Ki67State
    }
}


/**
 * 
 * @param {type} imageURL
 * @param {type} canvasId
 * @param {type} scaleToOriginal
 * @param {type} fieldSelectionParamString
 * @param {type} fieldDiameterInPixel
 * @param {type} state
 * @param {type} maxNumFieldHigh
 * @param {type} maxNumFieldMedium
 * @param {type} maxNumFieldLow
 * @param {type} maxNumFieldNegligible
 * @param {type} showHotspot
 * @param {type} showOtherFields
 * @param {type} readOnly
 * @returns {undefined}
 */
var FieldSelector = function (
        imageURL, // imageName
        canvasId,
        scaleToOriginal,
        fieldSelectionParamString,
        fieldDiameterInPixel, // 0.498700 micron per pixel ... 1mm ~ 2000 pixel 
        state,
        maxNumFieldHigh,
        maxNumFieldMedium,
        maxNumFieldLow,
        maxNumFieldNegligible,
        showHotspot,
        showOtherFields,
        readOnly
        ) {
    Zoompan.call(this, imageURL, canvasId, false /* disable thumbnail by default*/);
    this.scaleToOriginal = scaleToOriginal;
    this.originalFieldSelectionParamString = fieldSelectionParamString;
    this.maxNumFieldHighest = 1; // max # of fields allow to choose for highest score; always only one hotspot
    this.maxNumFieldHigh = maxNumFieldHigh;
    this.maxNumFieldMedium = maxNumFieldMedium;
    this.maxNumFieldLow = maxNumFieldLow;
    this.maxNumFieldNegligible = maxNumFieldNegligible;
    this.showHotspot = showHotspot; // whether or not to show hot-spot field
    this.showOtherFields = showOtherFields; // whether or not to show other fields (neg/low/med/high)
    this.state = state;
    this.ki67SelectionState = null; // the ki67 state for newly selected field - set to null initially
    this.fieldDiameterInPixel = fieldDiameterInPixel / scaleToOriginal; // scale is back to coord system of the low res image
    this.previewField = null; // to store the preview field
    this.badPreviewMsg = null; // indicate whether the preview is good or bad.  If non-null, this is a badPreview and this contains the error message
    this.disableFieldSelectionMouseListener = false; // if true, disable mouse listener
    this.initializeFieldSelection(fieldSelectionParamString);
    this.readOnly = readOnly;

    // if show hotspot only AND state is FIELD_SELECTOR_STATE_SELECTING, select hotspot automatically if it is present
    if (showHotspot && (!showOtherFields) && state === FIELD_SELECTOR_STATE_SELECTING) {
        for (var i = 0; i < this.selectedFields.length; i++) {
            var field = this.selectedFields[i];
            if (field.isHotspot()) {
                field.setViewingState(FOV_VIEWING_STATE_CURRENT);
                break;
            }
        }
    }

    var fieldSelector = this;
    // add mouse event handler
    if (this.canvas.addEventListener) {

        this.canvas.addEventListener("click", function (e) {
            if (!e.shiftKey && !fieldSelector.disableFieldSelectionMouseListener) {
                // make the clicked field the current viewing field
                var field = fieldSelector.getMouseClickedFieldSelection(e);
                if (field !== null) {
                    // if the selected view are not viewable i.e. user clicked on the field without seeing it ... disregard this selection
                    if ((!fieldSelector.showHotspot && field.isHotspot()) || (!fieldSelector.showOtherFields && !field.isHotspot())) {
                        field = null;
                    }
                }
                if (field !== null) {
                    if (!field.isCurrentViewing()) {
                        // set this field to be the current scoring field
                        for (var i = 0; i < fieldSelector.selectedFields.length; i++) {
                            var f = fieldSelector.selectedFields[i];
                            f.setViewingState(FOV_VIEWING_STATE_NOT_CURRENT);
                        }
                        field.setViewingState(FOV_VIEWING_STATE_CURRENT);
                        fieldSelector.repaint();

                        // defined in scoring_sessions/ki67QC.phase3/score.gsp
                        if (typeof displayNucleiCounterAppletBasedOnWhole_section_region_scoringCoordinates === "function") {
                            displayNucleiCounterAppletBasedOnWhole_section_region_scoringCoordinates(
                                    Math.round(field.getX() * fieldSelector.scaleToOriginal),
                                    Math.round(field.getY() * fieldSelector.scaleToOriginal),
                                    Math.round(field.getDiameter() * fieldSelector.scaleToOriginal),
                                    mapClientToServerKi67State(field.getKi67State()));
                        }
                    }
                }
            }
        }, false);

        if (!readOnly) { // the following listener only needed if NOT read only
            this.canvas.addEventListener("mousedown", function (e) {
                // initialize ki67SelectionState if needed
                if (fieldSelector.ki67SelectionState === null) {
                    if (!fieldSelector.readOnly && typeof initializeFieldSelectorSelectionState === "function") {
                        initializeFieldSelectorSelectionState(); // this function is defined in gsp page
                    }
                }
                if (e.shiftKey && fieldSelector.ki67SelectionState !== null && !fieldSelector.disableFieldSelectionMouseListener) {
                    // preview a selection
                    var clientRect = fieldSelector.canvas.getBoundingClientRect();

                    fieldSelector.previewField = new FieldOfView(
                            fieldSelector.vw.dXToSX(e.clientX - clientRect.left, fieldSelector.canvas.width, fieldSelector.canvas.height),
                            fieldSelector.vw.dYToSY(e.clientY - clientRect.top, fieldSelector.canvas.width, fieldSelector.canvas.height),
                            fieldSelector.fieldDiameterInPixel, FOV_VIEWING_STATE_PREVIEW, FOV_SCORING_STATE_NOT_SCORED, fieldSelector.ki67SelectionState);

                    // two cases when a preview would be considered "bad"
                    if (fieldSelector.isOutSideImage(e, fieldSelector.fieldDiameterInPixel / 2)) {
                        // add error text message to draw
                        fieldSelector.badPreviewMsg = "ERROR: entire field must\nbe inside slide area";
                    } else if (fieldSelector.maxFieldReached(fieldSelector.ki67SelectionState)) {
                        // add error text message to draw
                        fieldSelector.badPreviewMsg = "ERROR: max. # of " + fieldSelector.previewField.getKi67StateText() + "\nfields selected already.";
                    }

                    fieldSelector.repaint();
                }
            }, false);

            this.canvas.addEventListener("mouseup", function (e) {
                // clear preview selection
                fieldSelector.previewField = null;
                fieldSelector.badPreviewMsg = null;
                fieldSelector.repaint();
            }, false);

            this.canvas.addEventListener("dblclick", function (e) {
                if (e.shiftKey && !fieldSelector.disableFieldSelectionMouseListener && fieldSelector.ki67SelectionState !== null) {
                    // make a selection
                    if (!fieldSelector.isOutSideImage(e, fieldSelector.fieldDiameterInPixel / 2) && !fieldSelector.maxFieldReached(fieldSelector.ki67SelectionState)) {
                        var clientRect = fieldSelector.canvas.getBoundingClientRect();
                        // first need to set viewing state of all fields to not current
                        for (var i = 0; i < fieldSelector.selectedFields.length; i++) {
                            var f = fieldSelector.selectedFields[i];
                            if (f.isCurrentViewing()) {
                                f.setViewingState(FOV_VIEWING_STATE_NOT_CURRENT);
                            }
                        }



                        fieldSelector.selectedFields.push(new FieldOfView(
                                fieldSelector.vw.dXToSX(e.clientX - clientRect.left, fieldSelector.canvas.width, fieldSelector.canvas.height),
                                fieldSelector.vw.dYToSY(e.clientY - clientRect.top, fieldSelector.canvas.width, fieldSelector.canvas.height),
                                fieldSelector.fieldDiameterInPixel, FOV_VIEWING_STATE_CURRENT, FOV_SCORING_STATE_NOT_SCORED, fieldSelector.ki67SelectionState));
                        fieldSelector.repaint();
                        updateButtonDescs(); // defined in ki67_qc_phase3.js
                    }
                }
            }, false);
        }

    }
};

FieldSelector.prototype = Object.create(Zoompan.prototype);
// Set the "constructor" property to refer to NucleiCounter
FieldSelector.prototype.constructor = FieldSelector;

/**
 * initialize nuclei sections
 *
 * NOTE: if there is a hot-spot, move it to the BACK of the selectedFields
 * list.
 *
 * @param fieldSelectionParamString
 */
FieldSelector.prototype.initializeFieldSelection = function (fieldSelectionParamString) {
    this.originalFieldSelectionParamString = fieldSelectionParamString;
    this.selectedFields = parseFieldSelectionParamString(fieldSelectionParamString, this.scaleToOriginal);
    this.badPreviewMsg = null;
};


FieldSelector.prototype.repaint = function () {
    this.ctx.beginPath(); // this is so to CLEAR the previous circles!!!
    // call Zoompan's repaint first
    //Object.getPrototypeOf(this.constructor.prototype).repaint.call(this);
    this.repaintMainWindow();
    // draw field selections
    var orgLineWidth = this.ctx.lineWidth;
    var orgFillStyle = this.ctx.fillStyle;
    // want to draw in order: 1) other fields 2) current field 3) preview field
    var currentFOV = null;
    // 1) other fields
    for (var i = 0; i < this.selectedFields.length; i++) {
        var fov = this.selectedFields[i];
        // need to check showHotspot & showOtherFields if need to show 
        var needToShow = false;
        if ((this.showHotspot && fov.isHotspot()) || (this.showOtherFields && !fov.isHotspot())) {
            needToShow = true;
        }
        if (needToShow) {
            if (fov.inViewWindow(this.vw)) {
                //console.log("in view: " + fov.getX() + "/" + fov.getY());
                if (fov.isCurrentViewing()) {
                    currentFOV = fov;
                } else {
                    fov.draw(this.ctx, this.vw, this.canvas.width, this.canvas.height);
                }
            }
        }
    }
    // 2) current field
    if (currentFOV !== null) {
        currentFOV.draw(this.ctx, this.vw, this.canvas.width, this.canvas.height);
    }
    // 3) preview field
    if (this.previewField !== null) {
        if (this.previewField.inViewWindow(this.vw)) {
            if (this.badPreviewMsg === null) {
                this.previewField.draw(this.ctx, this.vw, this.canvas.width, this.canvas.height);
            } else {
                // this is a bad preview, do not draw it, rather print the error message
                var fontSize = (this.fieldDiameterInPixel / 6);
                var fovDX = this.previewField.getDX(this.vw, this.canvas.width, this.canvas.height);
                var fovDY = this.previewField.getDY(this.vw, this.canvas.width, this.canvas.height);
                var textArr = this.badPreviewMsg.split("\n");
                var textBoxWidth = textArr[0].length * fontSize * 0.6;
                this.ctx.fillStyle = "red";
                this.ctx.fillRect(fovDX - textBoxWidth * 0.05, fovDY - fontSize * 2, textBoxWidth, fontSize * 1.2 * textArr.length);
                // write text
                this.ctx.fillStyle = "black";
                this.ctx.font = "bold " + fontSize + "px Arial";
                this.ctx.textAlign = "left";
                for (var i = 0; i < textArr.length; i++) {
                    this.ctx.fillText(textArr[i], fovDX, fovDY + fontSize * 1.1 * (i - 1));
                }
            }
        }
    }
    this.ctx.lineWidth = orgLineWidth; // restore original line width
    this.ctx.fillStyle = orgFillStyle; // restore fill style
    if (this.drawThumbnailWindow) {
        this.repaintThumbnailWindow();
    }
};

/**
 * set the flag to show hot-spot
 * @returns {undefined}
 */
FieldSelector.prototype.showHotspot = function () {
    this.showHotspot = true;
};

/**
 * set the flag to hide hotspot
 */
FieldSelector.prototype.hideHotspot = function () {
    this.showHotspot = false;
};

/**
 * show other fields - does NOT affect whether to show the hotspot or not
 */
FieldSelector.prototype.showOtherFields = function () {
    this.showOtherFields = true;
};

/**
 * hide other fields - does NOT affect whether to show the hotspot or not
 */
FieldSelector.prototype.hideOtherFields = function () {
    this.showOtherFields = false;
};

/**
 * disable the field selection function
 */
FieldSelector.prototype.disableFieldSelectionMouseListener = function () {
    this.disableFieldSelectionMouseListener = true;
};

/**
 * enable the field selection function
 */
FieldSelector.prototype.enableFieldSelectionMouseListener = function () {
    this.disableFieldSelectionMouseListener = false;
};

/**
 * return state of this fieldSelectorPanel
 *
 * @return
 */
FieldSelector.prototype.getState = function () {
    return this.state;
};

/**
 * return max # of fields allow to choose for highest score i.e. hotspot
 *
 * @return
 */
FieldSelector.prototype.getMaxNumFieldHighest = function () {
    return this.maxNumFieldHighest;
};

/**
 * return max # of fields allow to choose for high score
 *
 * @return
 */
FieldSelector.prototype.getMaxNumFieldHigh = function () {
    return this.maxNumFieldHigh;
};

/**
 * return max # of fields allow to choose for medium score
 *
 * @return
 */
FieldSelector.prototype.getMaxNumFieldMedium = function () {
    return this.maxNumFieldMedium;
};

/**
 * return max # of fields allow to choose for low score
 *
 * @return
 */
FieldSelector.prototype.getMaxNumFieldLow = function () {
    return this.maxNumFieldLow;
};

/**
 * return max # of fields allow to choose for negligible score
 *
 * @return
 */
FieldSelector.prototype.getMaxNumFieldNegligible = function () {
    return this.maxNumFieldNegligible;
};

/**
 * return the array of selected fields
 * 
 * @return 
 */
FieldSelector.prototype.getSelectedFields = function () {
    return this.selectedFields;
};

/**
 * return true if it is showing hotspot ONLY!!! - this method is used in the
 * constructor, thus the "final"
 *
 * @return
 */
FieldSelector.prototype.isShowingHotspotOnly = function () {
    return this.showHotspot && !this.showOtherFields;
};

/**
 * return true if it is showing other fields ONLY!!!
 *
 * @return
 */
FieldSelector.prototype.isShowingOtherFieldsOnly = function () {
    return !this.showHotspot && this.showOtherFields;
};

/**
 * return true if it is showing BOTH hotspot and other fields
 *
 * @return
 */
FieldSelector.prototype.isShowingAllFields = function () {
    return this.showHotspot && this.showOtherFields;
};


/**
 * set the state that selection will be labeled at to HIGHEST
 */
FieldSelector.prototype.setKi67SelectionStateToHighest = function () {
    this.ki67SelectionState = FOV_KI67STATE_HOT_SPOT;
};

/**
 * set the state that selection will be labeled at to HIGH
 */
FieldSelector.prototype.setKi67SelectionStateToHigh = function () {
    this.ki67SelectionState = FOV_KI67STATE_HIGH;
};

/**
 * set the state that selection will be labeled at to MEDIUM
 */
FieldSelector.prototype.setKi67SelectionStateToMedium = function () {
    this.ki67SelectionState = FOV_KI67STATE_MEDIUM;
};

/**
 * set the state that selection will be labeled at to LOW
 */
FieldSelector.prototype.setKi67SelectionStateToLow = function () {
    this.ki67SelectionState = FOV_KI67STATE_LOW;
};

/**
 * set the state that selection will be labeled at to NEGLIGIBLE
 */
FieldSelector.prototype.setKi67SelectionStateToNegligible = function () {
    this.ki67SelectionState = FOV_KI67STATE_NEGLIGIBLE;
};

/**
 * trigger the hotspot nuclei counter on the client browser - does NOTHING
 * if there is no hotspot - will only pick the FIRST hotspot
 */
FieldSelector.prototype.triggerHotspotNucleiCounterWhenShowingHotspotOnly = function () {
    //fieldSelector.triggerHotspotNucleiCounterWhenShowingHotspotOnly();
};

/**
 * set ScoringState of current view of field to scored. if not current view,
 * do nothing
 */
FieldSelector.prototype.setCurrentFieldOfViewToScoringStateScored = function () {
    var needRepaint = false;
    for (var i = 0; i < this.selectedFields.length; i++) {
        var f = this.selectedFields[i];
        if (f.isCurrentViewing()) {
            if (!f.isScored()) {
                f.setScoringState(FOV_SCORING_STATE_SCORED);
                needRepaint = true;
            }
        }
    }
    // do a repaint
    if (needRepaint) {
        this.repaint();
    }
};

/**
 * remove the current viewing field - do nothing if no current viewing field
 */
FieldSelector.prototype.removeCurrentViewingField = function () {
    for (var i = 0; i < this.selectedFields.length; i++) {
        if (this.selectedFields[i].isCurrentViewing()) {
            this.selectedFields.splice(i, 1);
            this.repaint();
            break;
        }
    }
};

/**
 * check to see if all required fields is selected
 *
 * @return
 */
FieldSelector.prototype.isAllFieldsSelected = function () {
    var numHighest = 0;
    var numHigh = 0;
    var numMedium = 0;
    var numLow = 0;
    var numNegligible = 0;
    for (var i = 0; i < this.selectedFields.length; i++) {
        var f = this.selectedFields[i];
        switch (f.getKi67State()) {
            case FOV_KI67STATE_HOT_SPOT:
                numHighest++;
                break;
            case FOV_KI67STATE_HIGH:
                numHigh++;
                break;
            case FOV_KI67STATE_MEDIUM:
                numMedium++;
                break;
            case FOV_KI67STATE_LOW:
                numLow++;
                break;
            case FOV_KI67STATE_NEGLIGIBLE:
                numNegligible++;
                break;

        }
    }

    var allFieldSelected = true;
    if (this.showHotspot && numHighest !== this.maxNumFieldHighest) {
        allFieldSelected = false;
    }
    if (this.showOtherFields
            && (numHigh !== this.maxNumFieldHigh
                    || numMedium !== this.maxNumFieldMedium
                    || numLow !== this.maxNumFieldLow
                    || numNegligible !== this.maxNumFieldNegligible)) {
        allFieldSelected = false;
    }

    return allFieldSelected;
};

/**
 * return true if it is showing hotspot ONLY!!!
 *
 * @return
 */
FieldSelector.prototype.isShowingHotspotOnly = function () {
    return this.showHotspot && !this.showOtherFields;
};

/**
 * reset selection to original state
 *
 * @throws FieldSelectionParamStringParseException
 */
FieldSelector.prototype.resetSelections = function () {
    this.initializeFieldSelection(this.originalFieldSelectionParamString);
    this.repaint();
};

/**
 * return the field selection param string ... to be passed back to server
 *
 * @return
 */
FieldSelector.prototype.getfieldSelectionParamString = function () {
    var result = "";
    for (var i = 0; i < this.selectedFields.length; i++) {
        var field = this.selectedFields[i];
        result = result + Math.round(field.getX() * this.scaleToOriginal) + FOV_PARAMSTRING_TAG_X + Math.round(field.getY() * this.scaleToOriginal) + FOV_PARAMSTRING_TAG_Y + Math.round(field.getDiameter() * this.scaleToOriginal) + FOV_PARAMSTRING_TAG_KI67_PP;
        // figure out Ki67 state
        result = result + ki67StateToNumericCode(field.getKi67State());

        // figure out viewing state flag
        switch (field.getViewingState()) {
            case FOV_VIEWING_STATE_CURRENT:
                result = result + FOV_PARAMSTRING_TAG_VIEWING_STATE_CURRENT;
                break;
            case FOV_VIEWING_STATE_PREVIEW:
                result = result + FOV_PARAMSTRING_TAG_VIEWING_STATE_PREVIEW;
                break;
            case FOV_VIEWING_STATE_NOT_CURRENT:
                result = result + FOV_PARAMSTRING_TAG_VIEWING_STATE_NOT_CURRENT;
                break;
            default:
                break;
        }
        // figure out scoring state flag
        switch (field.getScoringState()) {
            case FOV_SCORING_STATE_NOT_SCORED:
                result = result + FOV_PARAMSTRING_TAG_SCORING_STATE_NOT_SCORED;
                break;
            case FOV_SCORING_STATE_SCORING:
                result = result + FOV_PARAMSTRING_TAG_SCORING_STATE_SCORING;
                break;
            case FOV_SCORING_STATE_SCORED:
                result = result + FOV_PARAMSTRING_TAG_SCORING_STATE_SCORED;
                break;
            default:
                break;
        }
        result = result + FOV_PARAMSTRING_DELIMITER;
    }
    return result.length === 0 ? "" : result.substr(0, result.length - FOV_PARAMSTRING_DELIMITER.length); // need to get rid of the last DELIMITER
};

/**
 * 
 * @returns an array of viewable fields
 */
FieldSelector.prototype.getViewableFields = function () {
    var viewableFields = []; // initialize array
    if (this.isShowingHotspotOnly()) {
        for (var i = 0; i < this.selectedFields.length; i++) {
            var field = this.selectedFields[i];
            if (field.isHotspot()) {
                viewableFields.push(field);
            }
        }
    } else if (this.isShowingOtherFieldsOnly()) {
        for (var i = 0; i < this.selectedFields.length; i++) {
            var field = this.selectedFields[i];
            if (!field.isHotspot()) {
                viewableFields.push(field);
            }
        }
    } else {
        for (var i = 0; i < this.selectedFields.length; i++) {
            var field = this.selectedFields[i];
            viewableFields.push(field);
        }
    }
    return viewableFields;
};

/**
 * return the field click by mouse; cycles through the fields if > 1 fields
 * contains the mouse click position
 * 
 * @param {type} e
 * @returns null if no field contains the mouse click posittion
 */
FieldSelector.prototype.getMouseClickedFieldSelection = function (e) {
    // only concerned about viewable fields
    var viewableFields = this.getViewableFields();
    var numSelectedFields = viewableFields.length;
    var counter = 0;
    var possibleSelectedField = null;
    var clientRect = this.canvas.getBoundingClientRect();
    var projectedX = this.vw.dXToSX(e.clientX - clientRect.left, this.canvas.width, this.canvas.height);
    var projectedY = this.vw.dYToSY(e.clientY - clientRect.top, this.canvas.width, this.canvas.height);
    for (var i = 0; i < viewableFields.length; i++) {
        var field = viewableFields[i];
        counter++;
        if (field.inView(projectedX, projectedY)) {
            if (field.isCurrentViewing()) {
                // need to look for the next field (if exist)
                if (counter === numSelectedFields) {
                    // this is the last field ...
                    // search for overlap fields from the start of the selectedFields arraylist
                    for (var j = 0; j < counter; j++) {
                        var nextField = viewableFields[j];
                        if (nextField.inView(projectedX, projectedY)) {
                            return nextField; // want to cycle through the fields when the user clicks on an area overlapped by > 1 circles.
                        }
                    }
                } else {
                    for (var j = counter; j < numSelectedFields; j++) {
                        var nextField = viewableFields[j];
                        if (nextField.inView(projectedX, projectedY)) {
                            return nextField; // want to cycle through the fields when the user clicks on an area overlapped by > 1 circles.
                        }
                    }
                    // needs to wrap around!
                    for (var j = 0; j < counter; j++) {
                        var nextField = viewableFields[j];
                        if (nextField.inView(projectedX, projectedY)) {
                            return nextField; // want to cycle through the fields when the user clicks on an area overlapped by > 1 circles.
                        }
                    }
                }
                return field; // no next field available ... return current field
            } else {
                possibleSelectedField = field;
            }
        }
    }
    return possibleSelectedField; // no other field is selected ... return possibleSelectedField
};


FieldSelector.prototype.isOutSideImage = function (e, offsetRWTOriginalImage) {
    var clientRect = this.canvas.getBoundingClientRect();
    var projectedX = this.vw.dXToSX(e.clientX - clientRect.left, this.canvas.width, this.canvas.height);
    var projectedY = this.vw.dYToSY(e.clientY - clientRect.top, this.canvas.width, this.canvas.height);
    return !(projectedX > offsetRWTOriginalImage
            && projectedY > offsetRWTOriginalImage
            && projectedX < (this.vw.getOrgWidth() - offsetRWTOriginalImage)
            && projectedY < (this.vw.getOrgHeight() - offsetRWTOriginalImage));
};

/**
 * check to see if max # of field for a particular score level has been
 * reached
 *
 * @param ki67State
 * @return
 */
FieldSelector.prototype.maxFieldReached = function (ki67State) {
    var currNumSelected = 0;
    // find out how many fields of the specified score is currently selected
    for (var i = 0; i < this.selectedFields.length; i++) {
        var f = this.selectedFields[i];
        if (f.getKi67State() === ki67State) {
            currNumSelected++;
        }
    }

    switch (ki67State) {
        case FOV_KI67STATE_HOT_SPOT:
            return currNumSelected >= this.getMaxNumFieldHighest();
        case FOV_KI67STATE_HIGH:
            return currNumSelected >= this.getMaxNumFieldHigh();
        case FOV_KI67STATE_MEDIUM:
            return currNumSelected >= this.getMaxNumFieldMedium();
        case FOV_KI67STATE_LOW:
            return currNumSelected >= this.getMaxNumFieldLow();
        case FOV_KI67STATE_NEGLIGIBLE:
            return currNumSelected >= this.getMaxNumFieldNegligible();
        default:
            return false;
    }
};



