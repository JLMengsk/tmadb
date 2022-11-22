var process = process || {env: {NODE_ENV: "development"}};
// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better
// to create separate JavaScript files as needed.
//
//= require_self
//= require_tree .
//= require_full_tree .


var process = process || {env: {NODE_ENV: "development"}};
/* 
 * helper functions related to biomarkers
 */

/*
 * check biomarker input for create/edit biomarker
 * NOTE: if ajax_url == null, this means its editing and NO check for existing
 * biomarker name.
 */
function checkNewBiomarkerInput(
        html_input_name_biomarker_name, // ${ViewConstants.HTML_PARAM_NAME_EDIT_BIOMARKER_INPUT_NAME}
        html_input_name_biomarker_description, // ${ViewConstants.HTML_PARAM_NAME_EDIT_BIOMARKER_INPUT_NAME_DESCRIPTION}
        html_input_name_biomarker_type_id, // ${ViewConstants.HTML_PARAM_NAME_EDIT_BIOMARKER_INPUT_NAME_BIOMARKER_TYPE_ID}
        ajax_url // ${createLink(controller:"biomarkers", action:"ajax_get_biomarker_by_name", params:[(ViewConstants.HTML_PARAM_NAME_EDIT_BIOMARKER_INPUT_NAME) : ""])}
        ) {
    // first check to make sure no empty biomarker name
    var newBiomarkerName = document.getElementById(html_input_name_biomarker_name).value.trim();
    if (newBiomarkerName === "") {
        alert("name is required");
        return false;
    }
    // check to see if biomarker name exist ... if so, generate a warning message
    var existingBiomarkerCheckOk = false;
    if (ajax_url === null) {
        // this is editting and no check will be made for existing biomarker name
        existingBiomarkerCheckOk = true;
    } else {
        new Ajax.Request(
                ajax_url + newBiomarkerName,
                {asynchronous: false, evalScripts: true, onComplete: function (e) {
                        var existingBiomarkers;
                        if (e === null) {
                            existingBiomarkers = eval("([])"); // generate empty JSON
                        } else {
                            existingBiomarkers = e; // e is a JSON
                            if (existingBiomarkers.numRows === 0) {
                                existingBiomarkerCheckOk = true;
                            } else {
                                var existingBiomarkerListing = '';
                                for (var i = 0, l = existingBiomarkers.items.length; i < l; i++) {
                                    var existingBiomarker = existingBiomarkers.items[i]
                                    existingBiomarkerListing = existingBiomarkerListing + existingBiomarker['name'] + " (" + existingBiomarker['biomarker_type'] + ")" + "\n";
                                }
                                existingBiomarkerCheckOk = confirm(
                                        'Warning: existing biomarker with same name found:\n' +
                                        existingBiomarkerListing +
                                        '\n' +
                                        'continue to save?'
                                        );
                            }
                        }
                    }}
        );
    }
    if (!existingBiomarkerCheckOk) {
        return false;
    } else {
        return confirm(
                'new biomarker info ...\n' +
                'biomarker type: ' + document.getElementById(html_input_name_biomarker_type_id).options[document.getElementById(html_input_name_biomarker_type_id).selectedIndex].text + "\n" +
                'name: ' + document.getElementById(html_input_name_biomarker_name).value + '\n' +
                'description: ' + document.getElementById(html_input_name_biomarker_description).value + '\n\n' +
                'OK to save?');
    }
}


var process = process || {env: {NODE_ENV: "development"}};
/* 
 * some function for create account
 */

/**
 * check user enter password ... make sure password1 and passord2 are the same
 * @param password1Id = id of text field
 * @param password2Id = id of text field
 */
function checkPassword(password1Id, password2Id) {
    if (document.getElementById(password1Id).value == document.getElementById(password2Id).value) {
        return true;
    } else {
        alert("ERROR: the two passwords entered are different.  Please try again.");
        return false;
    }
}


/**
 * make sure the field is not empty
 */
function checkNonEmptyField(fieldId, fieldNameToDisplay) {
    if (trim(document.getElementById(fieldId).value).length > 0) {
        return true;
    } else {
        alert("ERROR: " + fieldNameToDisplay + " cannot be empty.  Please try again.");
        return false;
    }
}

/**
 * check fields for change passwords ...
 * @param oldPasswordId = id of text field for old password
 * @param password1Id = id of text field for new password
 * @param password2Id = id of text field for new password check
 */
function checkFieldsForChangePassword(oldPasswordId, password1Id, password2Id) {
    return checkPassword(password1Id, password2Id) &&
            checkNonEmptyField(oldPasswordId, 'old password') &&
            checkNonEmptyField(password1Id, 'new password') &&
            checkNonEmptyField(password2Id, 'new password');
}

/**
 * check fields for create passwords ...
 * @param password1Id = id of text field for new password
 * @param password2Id = id of text field for new password check
 * @param first_nameId = id of text field for first name
 * @param last_nameId = id for text field for last name
 * @param emailId = id for text field for email 
 */
function checkFieldsForCreatePassword(password1Id, password2Id, nameId, emailId) {
    return checkPassword(password1Id, password2Id) &&
            checkNonEmptyField(nameId, 'name') &&
            checkNonEmptyField(emailId, 'email') &&
            checkNonEmptyField(password1Id, 'password') &&
            checkNonEmptyField(password2Id, 'password');
}
var process = process || {env: {NODE_ENV: "development"}};
/* 
 * help functions for doing edits
 */

/* 
 * helper functions for populating a select box
 * @param e - ajax response
 * @param htmlTagId - html select tag id 
 * 
 * assume json is a list of [name]___[id]
 */
function updateTissue_typesInfos(e, htmlTagId) {
    // The response comes back as a bunch-o-JSON
    var tissue_types;
    if (e == null) {
        tissue_types = eval("([])"); // generate empty JSON
    } else {
        tissue_types = e.items;	// e is a JSON
    }

    if (tissue_types) {
        var rselect = document.getElementById(htmlTagId)

        for (var i = 0; i < tissue_types.length; i++) {
            var tissue_type = tissue_types[i].name.split(AJAX_RESPONSE_DELIMITER);
            var opt = document.createElement('option');
            opt.text = tissue_type[0];
            opt.value = tissue_type[1];
            try {
                rselect.add(opt, null) // standards compliant; doesn't work in IE
            }
            catch (ex) {
                rselect.add(opt) // IE only
            }
        }
    }
}




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



var process = process || {env: {NODE_ENV: "development"}};
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


var process = process || {env: {NODE_ENV: "development"}};
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


var process = process || {env: {NODE_ENV: "development"}};
/* 
 * helper functions related to scoreTma
 */

/* 
 * table to browse the available scoring sessions
 * 
 * targetShowScoring_sessions: link to <g:createLink controller='scoring_sessions' action='score'/>
 * ajaxResponse: response from ajax call
 * htmlTagName: name of html tag
 */
function buildAvailableScoring_sessionsBrowserTable(
        targetShowScoring_sessions,
        ajaxResponse,
        htmlTagName) {
    var dataStore;
    var grid;
    function format_name(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        var items2 = items[0].split(AJAX_RESPONSE_DELIMITER_2);
        return "<a href='" + targetShowScoring_sessions + "/" + items[1] + "' title='" + items2[1] + "; Please click me to view available actions on this scoring session.'>" + items2[0] + "</a>";
    }

    // The response comes back as a bunch-o-JSON
    var scoring_sessions;
    if (ajaxResponse === null) {
        scoring_sessions = eval("([])"); // generate empty JSON
    } else {
        scoring_sessions = ajaxResponse; // ajaxResponse is a JSON
    }
    // width of table: 800 with right margin of 10px
    if (scoring_sessions) {
        dataStore = new dojo.data.ItemFileReadStore({data: scoring_sessions});
        // default ... not doing anything special
        grid = new dojox.grid.DataGrid({
            structure: [
                {name: "Assigned date", field: "start_date", width: "150px"},
                {name: "Name", field: "name", width: "550px", formatter: format_name},
                {name: "Status", field: "status", width: "100px"}
            ],
            store: dataStore,
            autoWidth: true,
            loadingMessage: "... loading ... please wait"

        }, htmlTagName);
    }
    grid.startup();
    return grid;
}

/* 
 * table to browse submitted scoring sessions (for score audit)
 * 
 * targetReportScoring_sessions: link to <g:createLink controller='scoring_sessions' action='report'/>
 * targetDownloadScoring_sessions: link to <g:createLink controller='downloadData' action='downloadScoringSessionScores'/>
 * ajaxResponse: response from ajax call
 * htmlTagName: name of html tag
 */
function buildSubmittedScoring_sessionsBrowserTable(
        targetReportScoring_sessions,
        targetDownloadScoring_sessions,
        ajaxResponse,
        htmlTagName) {
    var dataStore;
    var grid;
    function format_name(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        var items2 = items[0].split(AJAX_RESPONSE_DELIMITER_2);
        return "<a href='" + targetReportScoring_sessions + "/" + items[1] + "' title='" + items2[1] + "; Please click me to view scoring session report and available actions on this scoring session.'>" + items2[0] + "</a>";
    }
    function format_download(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        return "<a href='" + targetDownloadScoring_sessions + "/" + items[1] + "' title='Please click me to download scores in tab-delimited text format.'>download</a>";
    }
    // The response comes back as a bunch-o-JSON
    var scoring_sessions;
    if (ajaxResponse == null) {
        scoring_sessions = eval("([])") // evaluate JSON
    } else {
        scoring_sessions = eval("(" + ajaxResponse.responseText + ")")  // evaluate JSON
    }
    // width of table: 620 with right margin of 10px
    if (scoring_sessions) {
        dataStore = new dojo.data.ItemFileReadStore({data: scoring_sessions});
        // default ... not doing anything special
        grid = new dojox.grid.DataGrid({
            store: dataStore,
            structure: [
                {name: "Scorer", field: "scorer", width: "150px"},
                {name: "Scoring session", field: "name", width: "450px", formatter: format_name},
                {name: "Completed date", field: "scoring_date", width: "100px"},
                {name: "Download scores", field: "name", width: "100px", formatter: format_download}
            ],
            autoWidth: true,
            loadingMessage: "... loading ... please wait"
        }, htmlTagName);
    }
    grid.startup();
    return grid;
}
var process = process || {env: {NODE_ENV: "development"}};
/* 
 * functions specific to KI67 QC phase 3 study
 */

/**
 * this variable is needed to keep track of the onclick event call back function,
 * since this function will change as the scoring progresses
 */
var doneWithThisFieldButtonId_onclick_handle = null;

function enableFieldSelectorButtonHighest() {
    if (dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_HIGHEST)) {
        dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_HIGHEST).setLabel("Hot-spot");
        dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_HIGHEST).set('disabled', false);
    }
}
function enableFieldSelectorButtonHigh() {
    if (dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_HIGH)) {
        dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_HIGH).setLabel("High");
        dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_HIGH).set('disabled', false);
    }
}
function enableFieldSelectorButtonMedium() {
    if (dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_MEDIUM)) {
        dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_MEDIUM).setLabel("Medium");
        dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_MEDIUM).set('disabled', false);
    }
}
function enableFieldSelectorButtonLow() {
    if (dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_LOW)) {
        dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_LOW).setLabel("Low");
        dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_LOW).set('disabled', false);
    }
}
function enableFieldSelectorButtonNegligible() {
    if (dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_NEGLIGIBLE)) {
        dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_NEGLIGIBLE).setLabel("Negative");
        dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_NEGLIGIBLE).set('disabled', false);
    }
}

/**
 * set field sector mode to select hot spot
 * @returns {Boolean}
 */
function setFieldSelectorToHighest() {
    window[KI67_QC_PHASE3_FIELD_SELECTOR_ID].setKi67SelectionStateToHighest();
    dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_HIGHEST).setLabel("selecting Hot-spot ...");
    dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_HIGHEST).set('disabled', true);
    // enable other buttons ...
    enableFieldSelectorButtonHigh();
    enableFieldSelectorButtonMedium();
    enableFieldSelectorButtonLow();
    enableFieldSelectorButtonNegligible();
    return false;
}

function setFieldSelectorToHigh() {
    window[KI67_QC_PHASE3_FIELD_SELECTOR_ID].setKi67SelectionStateToHigh();
    dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_HIGH).setLabel("selecting High ...");
    dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_HIGH).set('disabled', true);
    // enable other buttons ...
    enableFieldSelectorButtonHighest();
    enableFieldSelectorButtonMedium();
    enableFieldSelectorButtonLow();
    enableFieldSelectorButtonNegligible();
    return false;
}

function setFieldSelectorToMedium() {
    window[KI67_QC_PHASE3_FIELD_SELECTOR_ID].setKi67SelectionStateToMedium();
    dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_MEDIUM).setLabel("selecting Medium ...");
    dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_MEDIUM).set('disabled', true);
    // enable other buttons ...
    enableFieldSelectorButtonHighest();
    enableFieldSelectorButtonHigh();
    enableFieldSelectorButtonLow();
    enableFieldSelectorButtonNegligible();
    return false;
}

function setFieldSelectorToLow() {
    window[KI67_QC_PHASE3_FIELD_SELECTOR_ID].setKi67SelectionStateToLow();
    dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_LOW).setLabel("selecting Low ...");
    dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_LOW).set('disabled', true);
    // enable other buttons ...
    enableFieldSelectorButtonHighest();
    enableFieldSelectorButtonHigh();
    enableFieldSelectorButtonMedium();
    enableFieldSelectorButtonNegligible();
    return false;
}

function setFieldSelectorToNegligible() {
    window[KI67_QC_PHASE3_FIELD_SELECTOR_ID].setKi67SelectionStateToNegligible();
    dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_NEGLIGIBLE).setLabel("selecting Negative ...");
    dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_NEGLIGIBLE).set('disabled', true);
    // enable other buttons ...
    enableFieldSelectorButtonHighest();
    enableFieldSelectorButtonHigh();
    enableFieldSelectorButtonMedium();
    enableFieldSelectorButtonLow();
    return false;
}

/**
 * check to see if there are any current selected field
 * 
 * @returns true if there is a current selected field, false otherwise
 */
function anyCurrentSelectedField() {
    var paramString = window[KI67_QC_PHASE3_FIELD_SELECTOR_ID].getfieldSelectionParamString();
    var fieldSelectionParamStrings = paramString.split(KI67_QC_PHASE3_FIELD_SELECTION_PARAMSTRING_DELIMITER);
    for (var i = 0; i < fieldSelectionParamStrings.length; i++) {
        var fieldSelectionParamString = fieldSelectionParamStrings[i];
        var tagIndex = fieldSelectionParamString.indexOf(KI67_QC_PHASE3_FIELD_SELECTION_PARAMSTRING_TAG_KI67_PP);
        if (fieldSelectionParamString.substring(tagIndex + 3, tagIndex + 4) == KI67_QC_PHASE3_FIELD_SELECTION_PARAMSTRING_TAG_VIEWING_STATE_CURRENT) {
            return true; // current selected field found!
        }
    }
    return false; // no current selected field found
}

/**
 * remove curent selected field an dupdate button descriptions
 * @param {type} confirmMsg - confirmation message
 * @returns {undefined}
 */
function removeCurrentSelectedField(confirmMsg) {
    if (anyCurrentSelectedField()) {
        if (confirm(confirmMsg)) {
            window[KI67_QC_PHASE3_FIELD_SELECTOR_ID].removeCurrentViewingField();
            updateButtonDescs();
        }
    } // do nothing if no current selected field.
}

/**
 * get current selections from applet and update the corresponding button descriptions
 * @returns {undefined}
 */
function updateButtonDescs() {
    updateButtonDescsWithInputFieldSelectionParamString(window[KI67_QC_PHASE3_FIELD_SELECTOR_ID].getfieldSelectionParamString());
}

/**
 * parse the input paramString and update the corresponding button descriptions
 * @param {type} paramString
 * @returns {undefined}
 */
function updateButtonDescsWithInputFieldSelectionParamString(paramString) {
    var fieldSelectionParamStrings = paramString.split(KI67_QC_PHASE3_FIELD_SELECTION_PARAMSTRING_DELIMITER);
    var descElementHighest = document.getElementById(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_HIGHEST);
    var descElementHigh = document.getElementById(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_HIGH);
    var descElementMedium = document.getElementById(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_MEDIUM);
    var descElementLow = document.getElementById(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_LOW);
    var descElementNegligible = document.getElementById(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_NEGLIGIBLE);
    if (fieldSelectionParamStrings.length == 0) {
        // set all to zero
        if (descElementHighest != null) {
            descElementHighest.innerHTML = "(not selected)"; // special case for hot-spot
        }
        if (descElementHigh != null) {
            var descHigh = descElementHigh.innerHTML;
            descElementHigh.innerHTML = descHigh.replace(/\(\d+/, "(0");
        }
        if (descElementMedium != null) {
            var descMedium = descElementMedium.innerHTML;
            descElementMedium.innerHTML = descMedium.replace(/\(\d+/, "(0");
        }
        if (descElementLow != null) {
            var descLow = descElementLow.innerHTML;
            descElementLow.innerHTML = descLow.replace(/\(\d+/, "(0");
        }
        if (descElementNegligible != null) {
            var descNegligible = descElementNegligible.innerHTML;
            descElementNegligible.innerHTML = descNegligible.replace(/\(\d+/, "(0");
        }
    } else {
        // some selection!!!
        var numHighest = 0;
        var numHigh = 0;
        var numMedium = 0;
        var numLow = 0;
        var numNegligible = 0;
        for (var i = 0; i < fieldSelectionParamStrings.length; i++) {
            var fieldSelectionParamString = fieldSelectionParamStrings[i];
            // find out the ki67 level of this field selection ...
            var tagIndex = fieldSelectionParamString.indexOf(KI67_QC_PHASE3_FIELD_SELECTION_PARAMSTRING_TAG_KI67_PP);
            switch (parseInt(fieldSelectionParamString.substring(tagIndex + 2, tagIndex + 3))) {
                case KI67_QC_PHASE3_FIELD_SELECTION_PARAMSTRING_KI67_PP_LEVEL_NEGLIGIBLE:
                    numNegligible++;
                    break;
                case KI67_QC_PHASE3_FIELD_SELECTION_PARAMSTRING_KI67_PP_LEVEL_LOW:
                    numLow++;
                    break;
                case KI67_QC_PHASE3_FIELD_SELECTION_PARAMSTRING_KI67_PP_LEVEL_MEDIUM:
                    numMedium++;
                    break;
                case KI67_QC_PHASE3_FIELD_SELECTION_PARAMSTRING_KI67_PP_LEVEL_HIGH:
                    numHigh++;
                    break;
                case KI67_QC_PHASE3_FIELD_SELECTION_PARAMSTRING_KI67_PP_LEVEL_HIGHEST:
                    numHighest++;
                    break;
            }
        }
        if (descElementHighest != null) {
            descElementHighest.innerHTML = numHighest > 0 ? "(selected)" : "(not selected)"; // special case for hot-spot
        }
        if (descElementHigh != null) {
            var descHigh = descElementHigh.innerHTML;
            descElementHigh.innerHTML = descHigh.replace(/\(\d+/, "(" + numHigh + "");
        }
        if (descElementMedium != null) {
            var descMedium = descElementMedium.innerHTML;
            descElementMedium.innerHTML = descMedium.replace(/\(\d+/, "(" + numMedium + "");
        }
        if (descElementLow != null) {
            var descLow = descElementLow.innerHTML;
            descElementLow.innerHTML = descLow.replace(/\(\d+/, "(" + numLow + "");
        }
        if (descElementNegligible != null) {
            var descNegligible = descElementNegligible.innerHTML;
            descElementNegligible.innerHTML = descNegligible.replace(/\(\d+/, "(" + numNegligible + "");
        }
    }
}

/**
 * reset selections to original state
 * @returns {undefined}
 */
function resetSelections() {
    showConfirmDialog(
            "Please confirm ...",
            "Reset all selections to original?",
            "Yes", // confirm
            "No", // decline
            function () {
                window[KI67_QC_PHASE3_FIELD_SELECTOR_ID].resetSelections();
                updateButtonDescs();
            }
    );
}

/**
 * do check before saving field selections
 * @param {type} confirmMsg - confirmation message
 * @returns true of ok to save, false otherwise
 */
function checkBeforeSavingFieldSelections(confirmMsg) {
    if (!window[KI67_QC_PHASE3_FIELD_SELECTOR_ID].isAllFieldsSelected()) {
        alert("please select all required fields");
        return false;
    } else if (confirm(confirmMsg)) {
        // put paramstring to html param
        document.getElementById(HTML_PARAM_NAME_KI67_QC_PHASE3_SELECT_REGION_PARAMSTRING).value = window[KI67_QC_PHASE3_FIELD_SELECTOR_ID].getfieldSelectionParamString();
        return true;
    }
    return false;
}

/**
 * response function to when the "done with all fields" button has been click ...
 * i.e. set scoring_date on current whole_section_scoring record and go to next available whole_section_scoring record
 * 
 * @param {type} ajaxSetWhole_sectionOrRegion_scoringScoring_dateUrl - this can be whole_section_scoring OR whole_section_region_scoring depending on whether its form a hot-spot scoring or not
 * @param {type} nextUrl
 * @returns {undefined}
 */
function doneWithAllFieldsButtonResponseFunction(ajaxSetWhole_sectionOrRegion_scoringScoring_dateUrl, nextUrl) {
    // save nuclei and go to next case
    nucleiCounter.uploadNucleiSelection().then(function () {
        if (!_tempSaveStatusOK) {
            alert("ERROR. failed to save nuclei count");
        } else {
            showConfirmDialog(
                    "Please confirm ...",
                    window[KI67_QC_PHASE3_FIELD_SELECTOR_ID].isShowingHotspotOnly() ? "done with hotspot on this image and continue?" : "done with all fields on this image and continue?",
                    "Yes", // confirm
                    "No", // decline
                    function () {
                        require(["dojo/_base/xhr"], function (xhr) {
                            xhr.get({
                                url: ajaxSetWhole_sectionOrRegion_scoringScoring_dateUrl,
                                handleAs: "text",
                                load: function (e) {
                                    if (e == AJAX_RESPONSE_ERROR) {
                                        alert("Error occurred. Failed to save (whole section scoring) record to database.")
                                    } else if (e == AJAX_RESPONSE_NA) {
                                        alert("Error occurred. Not all fields has been scored.")
                                    } else {
                                        // do next scoring
                                        cleanUpBeforeChangeMainWindow([heWindowHandle, mmWindowHandle]); // clean-up any H&E and MM window that MAY have opened
                                        window.location.href = nextUrl;
                                    }
                                    return;
                                },
                                onError: function (e) {
                                    alert("Error occurred. Failed to save (whole section scoring) record to database. Error message: " + e);
                                }
                            }); // xhr.get
                        }); // function (xhr)
                    }
            ); // showConfirmDialog(
        }
    }) // nucleiCounter.uploadNucleiSelection().then(function() {
}

/**
 * prepare e.g. define onClick function
 * 
 * @param {int} number_of_remaining_fields
 * @param {type} ajaxSetWhole_section_region_scoringScoring_dateUrl
 * @param {type} ajaxSetWhole_section_scoringScoring_dateUrl
 * @param {type} nextUrl
 * @returns {undefined}
 */
function prepareSaveButton(number_of_remaining_fields, ajaxSetWhole_section_region_scoringScoring_dateUrl, ajaxSetWhole_section_scoringScoring_dateUrl, nextUrl) {
    var saveButton = dijit.byId("doneWithThisFieldButtonId");
    var showingHotspotOnly = window[KI67_QC_PHASE3_FIELD_SELECTOR_ID].isShowingHotspotOnly();
    if (doneWithThisFieldButtonId_onclick_handle != null) {
        dojo.disconnect(doneWithThisFieldButtonId_onclick_handle);
    }
    if (number_of_remaining_fields > 0 && !showingHotspotOnly) {
        // change text of doneWithThisFieldButton
        saveButton.set("label", "DONE with this field (" + number_of_remaining_fields + " remaining)");
        saveButton.set("title", "click me to indicate finished scoring this field.");
        doneWithThisFieldButtonId_onclick_handle = dojo.connect(saveButton, "onClick", function () {
            doneWithThisFieldAction(ajaxSetWhole_section_region_scoringScoring_dateUrl, ajaxSetWhole_section_scoringScoring_dateUrl, nextUrl);
        });
    } else {
        if (showingHotspotOnly) {
            saveButton.set("label", "DONE with hotspot");
            saveButton.set("title", "click me to indicate finished scoring hotspot on this case and continue.");
            doneWithThisFieldButtonId_onclick_handle = dojo.connect(saveButton, "onClick", function () {
                doneWithAllFieldsButtonResponseFunction(ajaxSetWhole_section_region_scoringScoring_dateUrl, nextUrl);
            });
        } else {
            saveButton.set("label", "DONE with all fields!");
            saveButton.set("title", "click me to indicate finished scoring all fields on this case and continue.");
            doneWithThisFieldButtonId_onclick_handle = dojo.connect(saveButton, "onClick", function () {
                doneWithAllFieldsButtonResponseFunction(ajaxSetWhole_section_scoringScoring_dateUrl, nextUrl);
            });
        }
    }
}

/**
 * display nuclei counter applet
 * 
 * - requires the following function to be defined (in gsp file): finishedScoringCurrentField()
 * 
 * @param {type} appletDomElementId
 * @param {type} baseUrl
 * @param {type} allowedToUpdateScore
 * @param {type} nucleiSelectionParamString
 * @param {type} ihc_score_category_name
 * @param {type} numNucleiToCount
 * @param {type} comment
 * @param {type} ajaxSetWhole_section_region_scoringScoring_dateUrl = "${createLink(controller:"whole_section_region_scorings", action:"set_scoring_date")}" ... need id of whole_section_region_scoring
 * @param {type} ajaxSetWhole_section_scoringScoring_dateUrl = "${createLink(controller:"whole_section_scorings", action:"set_scoring_date")}"
 * @param {type} nextUrl = "${createLink(controller:"scoring_sessions", action:"score")}" - no id so that scoring_sessions controller can decide what to show next
 *
 * 
 * @returns {undefined}
 */
function  displayNucleiCounterAppletBasedOnNucleiSelectionParamString(
        appletDomElementId,
        baseUrl,
        allowedToUpdateScore,
        nucleiSelectionParamString,
        ihc_score_category_name,
        numNucleiToCount,
        comment,
        ajaxSetWhole_section_region_scoringScoring_dateUrl,
        ajaxSetWhole_section_scoringScoring_dateUrl,
        nextUrl
        ) {
    var appletSource = "<br>";
    if (allowedToUpdateScore) {
        //appletSource += "<br><span style='background-color: yellow'>please type the cell counter commands:</span><br>(i.e. '" + NUCLEI_COUNT_KEY_POSITIVE_LOWER_CASE + "','" + NUCLEI_COUNT_KEY_NEGATIVE_LOWER_CASE + "','" + NUCLEI_COUNT_KEY_UNDO_LOWER_CASE + "') in text box below:<br>";
        appletSource += "<input id='keyStrokeListener' type='text' dojoType='dijit.form.TextBox' style='width: 30em; border-style:solid; border-width:medium; border-color:red; value='please wait ...' disabled='disabled' onkeypress='return sendCellCounterKeyStrokeToApplet(event);'/>";
        appletSource += "<br><br>";
    }
    var scoringInstruction = "User indicated Ki67 level: <b>" + ihc_score_category_name.toUpperCase() + "</b><br>" + (allowedToUpdateScore ? "Please count up to " : "Require ") + "<b>" + numNucleiToCount + " invasive tumour nuclei" + (allowedToUpdateScore ? "" : " count") + "</b>";
    appletSource += "<span id='scoringInstruction' style=\"font-size:larger; background-color: #F6CECE; display: inline-block;\">" + scoringInstruction + "</span><br>";

    appletSource += "<table>";
    appletSource += "<tr>";
    appletSource += "<td id='" + appletDomElementId + "_deployed'></td>";
    if (allowedToUpdateScore) {
        // start of save/reset buttons
        appletSource += "<td style='vertical-align:middle;'>";
        appletSource += "<button id=\"saveNucleiCountButtonId\" type=\"button\"></button>";
        appletSource += "<br><br>"
        appletSource += "<button id=\"resetCounterButtonId\" type=\"button\"></button>";
        appletSource += "</td>";
        // end of save/reset buttons
    }
    appletSource += "</tr>"
    appletSource += "</table>";

    if (allowedToUpdateScore) {
        appletSource += "<br>";
        appletSource += "Please enter any comments in the box below (optional)<br>";
        appletSource += "<textarea id='inputComment' name='inputComment' dojoType='dijit.form.Textarea' style='width:30em; border-color:black;'>" + escape_javascript_for_html(comment) + "</textarea>";
        appletSource += "<br><br>";
        appletSource += "<button id=\"doneWithThisFieldButtonId\" type=\"button\"></button>";
        appletSource += "<div style=\"display: inline; float: right\"><button id=\"goBackButtonId\" type=\"button\"></button></div>";
    } else {
        appletSource += "<br><br>";
        appletSource += "User's comment (on this field):<br>";
        appletSource += "<textarea id='inputComment' dojoType='dijit.form.Textarea' style='width:30em;' disabled='disabled'>" + escape_javascript_for_html(comment) + "</textarea>";
    }

    if (typeof nucleiCounter === 'object' || typeof nucleiCounter == 'function') {
        // nuclei counter loaded already ... update the nuclei selection, do not reload
        // update comment
        dojo.byId("inputComment").value = escape_javascript_for_html(comment);
        if (allowedToUpdateScore) {
            // if allow to update ... disable keyStrokeListener first
            dojo.byId("keyStrokeListener").disabled = "disabled";
            dojo.byId("keyStrokeListener").placeholder = "please wait ...";
        }
        document.getElementById("scoringInstruction").innerHTML = scoringInstruction;
        nucleiCounter.reinit(nucleiSelectionParamString, !allowedToUpdateScore);
    }
    if (allowedToUpdateScore) {
        dojo.ready(function () {
            drawCounterButtons();
            prepareSaveButton(number_of_remaining_fields, ajaxSetWhole_section_region_scoringScoring_dateUrl, ajaxSetWhole_section_scoringScoring_dateUrl, nextUrl);
        });
        if (dijit.byId("doneWithThisFieldButtonId") != null) {
            prepareSaveButton(number_of_remaining_fields, ajaxSetWhole_section_region_scoringScoring_dateUrl, ajaxSetWhole_section_scoringScoring_dateUrl, nextUrl);
        }
    }
}

/**
 * define action to perform if/when doneWithThisField button is pressed
 * and NOT all fields are done
 * 
 * @param {type} ajaxSetWhole_section_region_scoringScoring_dateUrl
 * @param {type} ajaxSetWhole_section_scoringScoring_dateUrl
 * @param {type} nextUrl
 * @returns {undefined}
 */
function doneWithThisFieldAction(ajaxSetWhole_section_region_scoringScoring_dateUrl, ajaxSetWhole_section_scoringScoring_dateUrl, nextUrl) {
    // save nuclei and ask user to go to the next field
    if (typeof(_tempSuppressSaveOkMsg) !== 'undefined') {
        _tempSuppressSaveOkMsg = true; // suppress nuclei save ok message
    }
    nucleiCounter.uploadNucleiSelection().then(function () {
        if (!_tempSaveStatusOK) {
            alert("ERROR. failed to save nuclei count");
        } else {
            // set scoring date on whole_section_region_scoring object 
            // and reload page to prompt user to select next field to score
            finishedScoringCurrentField(ajaxSetWhole_section_region_scoringScoring_dateUrl, ajaxSetWhole_section_scoringScoring_dateUrl, nextUrl);
        }
    }); // nucleiCounter.uploadNucleiSelection().then(function () {
}

/**
 * draw or destroy then redraw the buttons associated with the counter
 * i.e. save, reset, back done
 * 
 * if button exist already, do not redraw!!!
 * 
 * @returns {undefined}
 */
function drawCounterButtons() {
    if (dijit.byId("saveNucleiCountButtonId") == null) {
        new dijit.form.Button({
            label: "SAVE nuclei count",
            title: "click me to save nuclei counts",
            onClick: function () {
                nucleiCounter.uploadNucleiSelection();
                // since nucleiCounter.uploadNucleiSelection is a ajax function call 
                // will not be able to check status here ... leave it for the 
                // loading function ... see function uploadNucleiSelection(nucleiSelectionParamString) implemented in gsp file
            }
        },
                "saveNucleiCountButtonId");
    }

    if (dijit.byId("resetCounterButtonId") == null) {
        new dijit.form.Button({
            label: "RESET counter",
            title: "click me to reset cell counter",
            onClick: function () {
                return resetCellCounter(); // defined in scoring_session.js
            }
        },
                "resetCounterButtonId");
    }

    if (dijit.byId("goBackButtonId") == null) {
        new dijit.form.Button({
            label: "BACK",
            title: "click me to go back to previous step i.e. select fields",
            onClick: function () {
                go_back(); // defined in score.gsp
                return false;
            }
        },
                "goBackButtonId");
    }

    if (dijit.byId("doneWithThisFieldButtonId") == null) {
        // details e.g. call back function will be added later
        new dijit.form.Button({}, "doneWithThisFieldButtonId");
    }
}

/**
 * display nuclie counter applet on the specified whole_section_region_scoring ...
 * - need ajaxUrl ... therefore, need a version defined on gsp with the ajaxUrl defined
 * 
 * @requires whole_section_region_scoring_id ... defined in score.gsp
 * @requires {array} nuclei_selection_notification_nuclei_count_array
 * @requires {array} nuclei_selection_notification_message_array
 * 
 * @param {type} appletDomElementId
 * @param {type} baseUrl
 * @param {type} allowedToUpdateScore
 * @param {type} ajaxGetWhole_section_region_scoringUrl
 * @param {type} x
 * @param {type} y
 * @param {type} diameter
 * @param {type} ki67state
 * @param {type} ajaxSetWhole_section_region_scoringScoring_dateUrl = "${createLink(controller:"whole_section_region_scorings", action:"set_scoring_date")}"
 * @param {type} ajaxSetWhole_section_scoringScoring_dateUrl = "${createLink(controller:"whole_section_scorings", action:"set_scoring_date")}"
 * @param {type} nextUrl = "${createLink(controller:"scoring_sessions", action:"score", id:scoring_sessionInstance.getId())}"
 * 
 * NOTE: expect to update a GLOBAL variable call whole_sectin_region_scoring_id
 */
function displayNucleiCounterAppletBasedOnWhole_section_region_scoringCoordinatesNeedAjaxUrl(
        appletDomElementId,
        baseUrl,
        allowedToUpdateScore,
        ajaxGetWhole_section_region_scoringUrl,
        x,
        y,
        diameter,
        ki67state,
        ajaxSetWhole_section_region_scoringScoring_dateUrl,
        ajaxSetWhole_section_scoringScoring_dateUrl,
        nextUrl
        ) {
    // ajax ask for whole_section_region_scoring id and nucleiSelectionParamString
    var requestUrl = ajaxGetWhole_section_region_scoringUrl + "?" +
            HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_REGION_SCORING_X + "=" + x + "&" +
            HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_REGION_SCORING_Y + "=" + y + "&" +
            HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_REGION_SCORING_DIAMETER + "=" + diameter + "&" + 
            HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_REGION_SCORING_KI67STATE + "=" + ki67state;

    require(["dojo/_base/xhr"], function (xhr) {
        xhr.get({
            url: requestUrl,
            handleAs: "json",
            load: function (e) {
                if (e === null) {
                    alert("Error occurred. Failed to retrieve whole section region scoring record.")
                } else {
                    var response = e; // e is a JSON
                    whole_section_region_scoring_id = response.items[0].id; // defined in score.gsp
                    var nucleiSelectionParamString = response.items[0].nucleiSelectionParamString;
                    var ihc_score_category_name = response.items[0].ihc_score_category_name;
                    var comment = response.items[0].comment;
                    nuclei_selection_notification_nuclei_count_array[0] = response.items[0].nuclei_selection_notification_nuclei_count;
                    nuclei_selection_notification_message_array[0] = response.items[0].nuclei_selection_notification_message;
                    var numNucleiToCount = response.items[0].nuclei_selection_notification_nuclei_count;
                    // (re-)activate nuclei counter applet!!!
                    _tempFirstTimeLoading = true;
                    displayNucleiCounterAppletBasedOnNucleiSelectionParamString(
                            appletDomElementId,
                            baseUrl,
                            allowedToUpdateScore,
                            nucleiSelectionParamString,
                            ihc_score_category_name,
                            numNucleiToCount,
                            comment,
                            ajaxSetWhole_section_region_scoringScoring_dateUrl + "/" + whole_section_region_scoring_id, // NOTE: add whole_section_region_scoring_id!!!
                            ajaxSetWhole_section_scoringScoring_dateUrl,
                            nextUrl
                            );
                    // show pop message to give some instructions to user regarding how many nuclei he/she should count
                    if (allowedToUpdateScore) {
                        nucleiCounter.updateNucleiCountOnBrowser(); // initialize any help elements (e.g. cell counter input text box) on html page
                        //alert((numNucleiToCount === 500 ? "This is a HOT-SPOT.  " : "") + "Please count up to " + numNucleiToCount + " invasive tumour nuclei.\n\nNote: please count nuclei by viewing the glass slide (via microscope)\nand not the zoomable online image.");
                        showMessageDialog(
                                "Please count up to " + numNucleiToCount + " invasive tumour nuclei",
                                (numNucleiToCount === 500 ? "This is a HOT-SPOT.  " : "") + "Please count up to " + numNucleiToCount + " invasive tumour nuclei. Note: please count nuclei by viewing the glass slide (via microscope) and not the zoomable online image.");
                    }
                }
                return;
            },
            onError: function (e) {
                alert("Error occurred. Failed to retrieve whole section region scoring record. Error message: " + e);
            }
        }); // xhr.get
    }); // function (xhr)
}


/**
 *  helper function to the function to indicate finished scoring the current field
 *  
 *  - reason for a helper function ... need to get ajaxUrl
 *  - this function updates number_of_remaining_fields ... defined in score.gsp
 *  
 *  if ajaxSetWhole_section_scoringScoring_dateUrl==null, means scoring HOT-SPOT
 *  
 * @requires number_of_remaining_fields
 * @requires fieldSelector - the applet
 * 
 * @param {type} ajaxSetWhole_section_region_scoringScoring_dateUrl = "${createLink(controller:"whole_section_region_scorings", action:"set_scoring_date")}"
 * @param {type} ajaxSetWhole_section_scoringScoring_dateUrl = "${createLink(controller:"whole_section_scorings", action:"set_scoring_date")}"
 * @param {type} nextUrl = "${createLink(controller:"scoring_sessions", action:"score", id:scoring_sessionInstance.getId())}"
 * @returns {undefined}
 */
function finishedScoringCurrentField(ajaxSetWhole_section_region_scoringScoring_dateUrl, ajaxSetWhole_section_scoringScoring_dateUrl, nextUrl) {
    require(["dojo/_base/xhr"], function (xhr) {
        xhr.get({
            url: ajaxSetWhole_section_region_scoringScoring_dateUrl,
            handleAs: "text",
            load: function (e) {
                if (e === AJAX_RESPONSE_ERROR) {
                    alert("Error occurred. Failed to set scoring date on whole section region scoring record.");
                } else {
                    number_of_remaining_fields = e;
                    if (number_of_remaining_fields > 0) {
                        showMessageDialog("Please select another field","Please select another field to score ("+number_of_remaining_fields+" remaining).");
                    }
                    window[KI67_QC_PHASE3_FIELD_SELECTOR_ID].setCurrentFieldOfViewToScoringStateScored();
                    // update done button (doneWithThisFieldButtonId)
                    prepareSaveButton(number_of_remaining_fields, ajaxSetWhole_section_region_scoringScoring_dateUrl, ajaxSetWhole_section_scoringScoring_dateUrl, nextUrl);
                }
                return;
            },
            onError: function (e) {
                alert("Error occurred. Failed to retrieve whole section region scoring record. Error message: " + e);
            }
        }); // xhr.get
    }); // function (xhr)
}

/**
 * function to go back to previous step i.e. count nuclei -> select fields 
 * 
 * @param {type} goBackUrl - go back on step
 * @param {type} nextUrl
 * @returns {undefined}
 */
function ki67_qc_phase3_go_back_one_step(goBackUrl, nextUrl) {
    showConfirmDialog(
            "Please confirm ...",
            "Going back to previous step MAY result in lost of ALL nuclei counts.  OK to continue?",
            "Yes", // confirm
            "No", // decline
            function () {
                showWaitDialog();
                require(["dojo/_base/xhr"], function (xhr) {
                    xhr.get({
                        url: goBackUrl,
                        handleAs: "text",
                        load: function (e) {
                            if (e === AJAX_RESPONSE_ERROR) {
                                alert("Error occurred. Failed to save (whole section scoring) record to database.");
                            } else if (e === AJAX_RESPONSE_NA) {
                                alert("Error occurred. Option not available.");
                            } else {
                                // do next scoring
                                window.location.href = nextUrl;
                            }
                            return;
                        },
                        onError: function (e) {
                            alert("Error occurred. Failed to save (whole section scoring) record to database. Error message: " + e);
                        }
                    }); // xhr.get
                }); // function (xhr)
            }
    );
    return false; // prevent page refresh
}
;
var process = process || {env: {NODE_ENV: "development"}};
/* 
 * some helper functions for scoring_sessions
 */

/**
 * timer
 * @returns {undefined}
 */
function timeMsg()
{
    var t = setTimeout("alertMsg()", (SCORING_SESSION_TIMEOUT_IN_SECONDS * 1000 - 60000));
}

/**
 * show warning message when timer is up
 * @returns {undefined}
 */
function alertMsg()
{
    var now = new Date();
    showMessageDialog(
            "Warning",
            now + ": Session will time out in 1 minute.  Please save your score NOW.  Warning: if save has not occurred after session time out, all scores since last save will be lost.  No further warning messages will be shown.  Please save your score NOW."
            );
}
;


/**
 * update nuclei selection count, also check to see if need to display any notification message(s)
 * @param {type} numPos
 * @param {type} numNeg
 * @param {type} numTotal
 * @param {type} isScoringTypeNucleiCount
 * @param {type} nuclei_selection_notification_nuclei_count_array
 * @param {type} nuclei_selection_notification_message_array
 * @returns {undefined}
 * 
 * WARNING!!! assume length of nuclei_selection_notification_nuclei_count_array and nuclei_selection_notification_message_array are the same!!!
 */
function updateNucleiSelectionCountHelper(numPos, numNeg, numTotal, isScoringTypeNucleiCount, nuclei_selection_notification_nuclei_count_array, nuclei_selection_notification_message_array) {
    document.getElementById(NUCLEI_COUNT_STATUS_NUM_POS_ID).innerHTML = numPos;
    document.getElementById(NUCLEI_COUNT_STATUS_NUM_NEG_ID).innerHTML = numNeg;
    document.getElementById(NUCLEI_COUNT_STATUS_NUM_TOTAL_ID).innerHTML = numTotal;
    if (isScoringTypeNucleiCount) {
        // ONLY nuclei selection will need to show bottom nuclei count
        document.getElementById(NUCLEI_COUNT_STATUS_NUM_POS_BOTTOM_ID).innerHTML = numPos;
        document.getElementById(NUCLEI_COUNT_STATUS_NUM_NEG_BOTTOM_ID).innerHTML = numNeg;
        document.getElementById(NUCLEI_COUNT_STATUS_NUM_TOTAL_BOTTOM_ID).innerHTML = numTotal;
    }
    for (var i = 0; i < nuclei_selection_notification_nuclei_count_array.length; i++) {
        if (numTotal == nuclei_selection_notification_nuclei_count_array[i] && (!_tempResetInProgress) && (!_tempFirstTimeLoading)) {
            try {
                nucleiCounter.playNotificationSound();
            } catch (err) {
            } // just silently ignore if failed to play notification sound
            var showMessageDialog = false; // if message dialog is showing already, do not reshow it
            if (message_dialog_object === null) {
                showMessageDialog = true;
            } else if (message_dialog_object.title !== "Please note ...") {
                showMessageDialog = true;
            }
            if (showMessageDialog) {
                showMessageDialogWithCallBack(
                        "Please note ...",
                        nuclei_selection_notification_message_array[i],
                        function () {
                            nucleiCounter.uploadNucleiSelection(); // upload now ... since upload takes a long time
                        });
            }
        }
    }
    _tempFirstTimeLoading = false; // WATCH OUT!!! make sure this is indeed the first time loaded;
}

/**
 * update nuclei selection count, also check to see if need to display any notification message(s)
 * 
 * special version for Ki67-QC phase 3 
 * 
 * @param {type} numPos
 * @param {type} numNeg
 * @param {type} numTotal
 * @param {type} isScoringTypeNucleiCount
 * @param {type} nuclei_selection_notification_nuclei_count_array
 * @param {type} nuclei_selection_notification_message_array
 * @returns {undefined}
 * 
 * WARNING!!! assume length of nuclei_selection_notification_nuclei_count_array and nuclei_selection_notification_message_array are the same!!!
 */
function updateNucleiSelectionCountHelperKi67QcPhase3(numPos, numNeg, numTotal, isScoringTypeNucleiCount, nuclei_selection_notification_nuclei_count_array, nuclei_selection_notification_message_array) {
    document.getElementById(NUCLEI_COUNT_STATUS_NUM_POS_ID).innerHTML = numPos;
    document.getElementById(NUCLEI_COUNT_STATUS_NUM_NEG_ID).innerHTML = numNeg;
    document.getElementById(NUCLEI_COUNT_STATUS_NUM_TOTAL_ID).innerHTML = numTotal;
    if (isScoringTypeNucleiCount) {
        // ONLY nuclei selection will need to show bottom nuclei count
        document.getElementById(NUCLEI_COUNT_STATUS_NUM_POS_BOTTOM_ID).innerHTML = numPos;
        document.getElementById(NUCLEI_COUNT_STATUS_NUM_NEG_BOTTOM_ID).innerHTML = numNeg;
        document.getElementById(NUCLEI_COUNT_STATUS_NUM_TOTAL_BOTTOM_ID).innerHTML = numTotal;
    }
    for (var i = 0; i < nuclei_selection_notification_nuclei_count_array.length; i++) {
        if (numTotal >= nuclei_selection_notification_nuclei_count_array[i] && (!_tempResetInProgress) && (!_tempFirstTimeLoading)) {
            try {
                nucleiCounter.playNotificationSound();
            } catch (err) {
            } // just silently ignore if failed to play notification sound
            _tempResetInProgress = true
            while (numTotal > nuclei_selection_notification_nuclei_count_array[i]) {
                // decrement nuclei counter back to numTotal
                nucleiCounter.undoNucleiCount();
                numTotal--;
            }
            _tempResetInProgress = false;
            var showMessageDialog = false; // if message dialog is showing already, do not reshow it
            if (message_dialog_object === null) {
                showMessageDialog = true;
            } else if (message_dialog_object.title !== "Please note ...") {
                showMessageDialog = true;
            }
            if (showMessageDialog) {
                showMessageDialogWithCallBack(
                        "Please note ...",
                        nuclei_selection_notification_message_array[i],
                        function () {
                            nucleiCounter.uploadNucleiSelection(); // upload now ... since upload takes a long time
                        }
                );
            }
        }
    }
    _tempFirstTimeLoading = false; // WATCH OUT!!! make sure this is indeed the first time loaded;
}

/**
 * check to see if save (i.e. udpate) nuclei selection is successful
 * - this is a function to deal with the response of an ajax request to save nuclei selection
 * - show MESSAGE DIALOG: Nuclei selection saved successfully ...
 * 
 * @param {type} e
 * @param {type} curr_location
 * @returns {undefined}
 */
function checkUpdateNucleiSelection(e, curr_location) {
    _tempSaveStatusOK = false;
    if (e !== "SAVE SUCCESSFUL" && e !== "REMOVE SUCCESSFUL") {
        showMessageDialog(
                "ERROR in saving nuclei selection",
                "ERROR in saving nuclei selection.  Nuclei selection NOT SAVED.  This webpage will refresh/reload now.  You may continue to score after the webpage refresh.  We apologize for the inconvenience.");
        window.location = curr_location;
    } else {
        _tempSaveStatusOK = true;
    }
}

/**
 * send cell counter key stroke to applet
 * 
 * @param {type} e
 * @returns {Boolean}
 */
function sendCellCounterKeyStrokeToApplet(e) {
    var x;

    // for firefox, event needs to be passed in i.e. input param 'e'
    var evt = e || window.event;

    if (window.event) // IE8 and earlier
    {
        x = evt.keyCode;
    } else if (evt.which) // IE9/Firefox/Chrome/Opera/Safari
    {
        x = evt.which;
    }
    var keychar = String.fromCharCode(x);
    document.getElementById('keyStrokeListener').value = "";
    if (keychar == NUCLEI_COUNT_KEY_POSITIVE_LOWER_CASE || keychar == NUCLEI_COUNT_KEY_POSITIVE_UPPER_CASE) {
        nucleiCounter.addPositiveNucleiCount();
    } else if (keychar == NUCLEI_COUNT_KEY_NEGATIVE_LOWER_CASE || keychar == NUCLEI_COUNT_KEY_NEGATIVE_UPPER_CASE) {
        nucleiCounter.addNegativeNucleiCount();
    } else if (keychar == NUCLEI_COUNT_KEY_UNDO_LOWER_CASE || keychar == NUCLEI_COUNT_KEY_UNDO_UPPER_CASE) {
        nucleiCounter.undoNucleiCount();
    }

    if (typeof evt.stopPropagation != "undefined") {
        evt.stopPropagation();
    } else {
        evt.cancelBubble = true;
    }

    return false;
}

/**
 * reset cell counter
 * 
 * @returns {Boolean}
 */
function resetCellCounter() {
    showConfirmDialog(
            "Please confirm ...",
            "RESET cell counter?",
            "Yes", // confirm
            "No", // decline
            function () {
                _tempResetInProgress = true; // to suppress notification messages
                // reset cell counter by doing undocell count for numTotal times
                while (parseInt(document.getElementById('numTotal').innerHTML) > 0) {
                    nucleiCounter.undoNucleiCount();
                }
                _tempResetInProgress = false;
                nucleiCounter.uploadNucleiSelection(); // do a save first, since save takes a long time
            }
    );
    return false; // since is this called by a href/onclick element
}

/**
 * show wait message at html tag
 * 
 * @param {type} id - html tag id
 * @param {type} msg
 * @returns {Boolean}
 */
function showWaitMessage(id, msg) {
    var e = document.getElementById(id);
    e.value = msg;
    return true;
}

/**
 * close the H&E image window
 * @param {type} heWindowHandle H&E window name
 * @returns {undefined}
 */
function closeHeWindow(heWindowHandle) {
    if (heWindowHandle) {
        heWindowHandle.close();
    }
}

/**
 *  this to do before changing the main window 
 *  - close H&E window if opened
 *  
 * @param {type} windowHandles - array of window handles
 * @returns true
 */
function cleanUpBeforeChangeMainWindow(windowHandles) {
    for (var i = 0; i < windowHandles.length; i++) {
        closeHeWindow(windowHandles[i]);
    }
    true;
}

/**
 * look though the fields (assume input textboxes) with ids in field_ids
 * and sum them up.  If sum is not equal to required_sum, popup error message
 * err_msg and return false.  otherwise, return true
 * 
 * @param {type} field_ids
 * @param {type} required_sum
 * @param {type} err_msg
 * @returns {undefined}
 */
function checkSumOfField(field_ids, required_sum, err_msg) {
    var curr_sum = 0;
    for (var i = 0; i < field_ids.length; i++) {
        curr_sum = curr_sum + parseFloat(document.getElementById(field_ids[i]).value)
    }
    if (curr_sum !== required_sum) {
        showMessageDialog("Error", err_msg);
        return false;
    } else {
        return true;
    }
}

/* 
 * table to browse the available scoring sessions
 * 
 * ajaxResponse: response from ajax call
 * htmlTagName: name of html tag
 */
function buildScoring_sessionReportTable(
        ajaxResponse,
        htmlTagName) {
    var dataStore;
    var grid;

    function format_description(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        return "<a href='" + items[1] + "' title='" + items[2] + "'>" + items[0] + "</a>";
    }

    // The response comes back as a bunch-o-JSON
    var scoring_sessionReportTable;
    if (ajaxResponse === null) {
        scoring_sessionReportTable = eval("([])"); // generate empty JSON
    } else {
        scoring_sessionReportTable = ajaxResponse; // ajaxResponse is a JSON
    }
    // width of table: 800 with right margin of 10px  
    if (scoring_sessionReportTable) {
        dataStore = new dojo.data.ItemFileReadStore({data: scoring_sessionReportTable});
        // default ... not doing anything special
        if (scoring_sessionReportTable.scoring_session_scoring_type === SCORING_SESSION_SCORING_TYPE_TMA_SCORING) {
            if (scoring_sessionReportTable.references_available) {
                grid = new dojox.grid.DataGrid({
                    store: dataStore,
                    structure: [
                        {name: "TMA core ID", field: "core_id", width: "50px"},
                        {name: "Description", field: "description", width: "250px", formatter: format_description},
                        {name: "Score type", field: "type", width: "80px"},
                        {name: "Score", field: "score", width: "250px"},
                        {name: "Scoring date/time", field: "scoring_date", width: "100px"},
                        {name: "Reference score", field: "reference_score", width: "70px"}
                    ],
                    autoWidth: true,
                    loadingMessage: "... loading ... please wait"
                }, htmlTagName);
            } else {
                grid = new dojox.grid.DataGrid({
                    store: dataStore,
                    structure: [
                        {name: "TMA core ID", field: "core_id", width: "50px"},
                        {name: "Description", field: "description", width: "285px", formatter: format_description},
                        {name: "Score type", field: "type", width: "80px"},
                        {name: "Score", field: "score", width: "285px"},
                        {name: "Scoring date/time", field: "scoring_date", width: "100px"}
                    ],
                    autoWidth: true,
                    loadingMessage: "... loading ... please wait"
                }, htmlTagName);
            }
        } else if (scoring_sessionReportTable.scoring_session_scoring_type === SCORING_SESSION_SCORING_TYPE_WHOLE_SECTION_SCORING) {
            grid = new dojox.grid.DataGrid({
                store: dataStore,
                structure: [
                    {name: "Specimen #", field: "whole_section_specimen_num", width: "100px"},
                    {name: "Description", field: "description", width: "220px", formatter: format_description},
                    {name: "Score type", field: "type", width: "80px"},
                    {name: "Score", field: "score", width: "300px"},
                    {name: "Scoring date/time", field: "scoring_date", width: "100px"}
                ],
                autoWidth: true,
                loadingMessage: "... loading ... please wait"
            }, htmlTagName);
        }
    }
    grid.startup();
    return grid;
}




var process = process || {env: {NODE_ENV: "development"}};
/* 
 * table to browse the available tma projects
 * 
 * ajaxResponse: response from ajax call
 * htmlTagName: name of html tag
 */
function buildTma_projectsBrowserTable(
        targetShowTma_projects,
        ajaxResponse,
        htmlTagName,
        projectCountTagName) {
    var dataStore;
    var grid;
    function format_name(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        return "<a href='" + targetShowTma_projects + "/" + items[1] + "' title='click me to view TMA project details'>" + items[0] + "</a>";
    }

    // The response comes back as a bunch-o-JSON
    var tma_projects;
    if (ajaxResponse === null) {
        tma_projects = eval("([])") // empty JSON
    } else {
        tma_projects = ajaxResponse;
    }
    // width of table: 800 with right margin of 10px
    if (tma_projects) {
        dataStore = new dojo.data.ItemFileReadStore({data: tma_projects});
        // default ... not doing anything special
        grid = new dojox.grid.DataGrid({
            store: dataStore,
            structure: [
                {name: "Name", field: "name", width: "80px", formatter: format_name},
                {name: "Built by", field: "built_by", width: "150px"},
                {name: "Description", field: "description", width: "350px"},
                {name: "Core ID name", field: "core_id_name", width: "80px"}
            ],
            autoWidth: true,
            loadingMessage: "... loading ... please wait"
        }, htmlTagName);
    }
    grid.startup();

    // set the project count ...
    var numProjects = tma_projects.items.length;
    var projectCountNode = document.createElement("a");
    projectCountNode.setAttribute("title", "this table contains " + numProjects + " TMA project" + (numProjects <= 1 ? "" : "s"));
    projectCountNode.appendChild(document.createTextNode(numProjects));
    dojo.byId(projectCountTagName).appendChild(projectCountNode);

    return grid;
}

var process = process || {env: {NODE_ENV: "development"}};
/* 
 * table to browse the available tma slices
 * 
 * targetShowTma_slices: link to <g:createLink controller='tma_slices' action='show'/>
 * targetShowTma_blocks: link to <g:createLink controller='tma_blocks' action='show'/>
 * targetShowTma_arrays: link to <g:createLink controller='tma_arrays' action='show'/>
 * targetShowTma_projects: link to <g:createLink controller='tma_projects' action='show'/>
 * ajaxResponse: response from ajax call
 * htmlTagName: name of html tag
 * slideCountTagName: name of the slide_count div
 */
function buildTma_slicesBrowserTable(
        targetShowTma_slices,
        targetShowTma_blocks,
        targetShowTma_arrays,
        targetShowTma_projects,
        targetShowBiomarkers,
        ajaxResponse,
        htmlTagName,
        slideCountTagName) {
    var dataStore;
    var grid;
    function format_name(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        return "<a href='" + targetShowTma_slices + "/" + items[1] + "' title='click me to view TMA slice details'>" + items[0] + "</a>";
    }
    function format_tma_block(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        return "<a href='" + targetShowTma_blocks + "/" + items[1] + "' title='click me to view TMA block details'>" + items[0] + "</a>";
    }
    function format_tma_array(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        return "<a href='" + targetShowTma_arrays + "/" + items[1] + "' title='click me to view TMA array details'>" + items[0] + "</a>";
    }
    function format_tma_project(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        var items2 = items[0].split(AJAX_RESPONSE_DELIMITER_2);
        return "<a href='" + targetShowTma_projects + "/" + items[1] + "' title='click me to view TMA project details'>" + items2[0] + " (" + items2[1] + ")</a>";
    }
    function format_biomarker(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        return "<a href='" + targetShowBiomarkers + "/" + items[1] + "' title='click me to view biomarker details'>" + items[0] + "</a>";
    }
    function format_name_w_button(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        var button = new dijit.form.Button({
            label: "view",
            title: "click me to view TMA slice (" + items[0] + ")",
            onClick: function() {
                // generate preview window
                window.location.href = targetShowTma_slices + "/" + items[1];
            }
        });
        return button;
    }

    // The response comes back as a bunch-o-JSON
    var tma_slices;
    if (ajaxResponse == null) {
        tma_slices = eval("([])") // empty JSON
    } else {
        tma_slices = ajaxResponse;
    }
    // width of table: 800 with right margin of 10px
    if (tma_slices) {
        dataStore = new dojo.data.ItemFileReadStore({data: tma_slices});
        // default ... not doing anything special
        grid = new dojox.grid.DataGrid({
            store: dataStore,
            structure: [
                {name: "TMA project", field: "tma_project", width: "400px", formatter: format_tma_project},
                {name: "Version", field: "tma_array", width: "40px", formatter: format_tma_array},
                {name: "Block", field: "tma_block", width: "40px", formatter: format_tma_block},
                {name: "Biomarker", field: "biomarker", width: "200px", formatter: format_biomarker},
                {name: "Slice", field: "name", width: "40px", formatter: format_name},
                {name: "View slice", field: "name", width: "80px", formatter: format_name_w_button}
            ],
            autoWidth: true,
            loadingMessage: "... loading ... please wait"
        }, htmlTagName);
    }
    grid.startup();

    // set the slide count ...
    var numSlides = tma_slices.items.length;
    var slideCountNode = document.createElement("a");
    slideCountNode.setAttribute("title", "this table contains " + numSlides + " slice" + (numSlides <= 1 ? "" : "s"));
    slideCountNode.appendChild(document.createTextNode(numSlides));
    dojo.byId(slideCountTagName).appendChild(slideCountNode);

    return grid;
}


/* 
 * helper functions for tma_slices
 * @param e - ajax response
 * @param tma_result_names_id - current shown tma_result_names_id; tma_result_names_id=="" if display core ids
 * @param htmlTagId - html select tag id 
 * @param url to show tma_slices
 */
function updateTmaScorersInfos(e, tma_result_names_id, htmlTagId, showTma_slicesUrl) {
    // The response comes back as a bunch-o-JSON
    var tma_scorers_infos;
    if (e == null) {
        tma_scorers_infos = eval("([])"); // generate empty JSON
    } else {
        tma_scorers_infos = e;	// e is a JSON
    }

    if (tma_scorers_infos) {
        var rselect = document.getElementById(htmlTagId)

        // Clear all previous options
        var l = rselect.length

        while (l > 0) {
            l--
            rselect.remove(l)
        }

        // Rebuild the select
        // always add display core IDS as the first one
        var displayCoreIdOpt = document.createElement('option');
        displayCoreIdOpt.text = "Core IDs";
        displayCoreIdOpt.value = showTma_slicesUrl;
        try {
            rselect.add(displayCoreIdOpt, null) // standards compliant; doesn't work in IE
        }
        catch (ex) {
            rselect.add(displayCoreIdOpt) // IE only
        }

        for (var i = 0; i < tma_scorers_infos.length; i++) {
            var tma_scorers_info = tma_scorers_infos[i];
            var opt = document.createElement('option');
            var tma_scorer1_name = "";
            if (tma_scorers_info[1] != null) {
                tma_scorer1_name = tma_scorers_info[1].name;
            }
            var tma_scorer2_name = "";
            if (tma_scorers_info[2] != null) {
                tma_scorer2_name = tma_scorers_info[2].name;
            }
            var tma_scorer3_name = "";
            if (tma_scorers_info[3] != null) {
                tma_scorer3_name = tma_scorers_info[3].name;
            }
            var score_type = tma_scorers_info[5]
            if (!(typeof(score_type) == "string")) {
                score_type = tma_scorers_info[5].name;
            }

            opt.text = ""
            if (tma_scorers_info[1] != null) {
                opt.text = tma_scorer1_name;
            }
            if (tma_scorers_info[2] != null) {
                opt.text = opt.text + " / " + tma_scorer2_name;
            }
            if (tma_scorers_info[3] != null) {
                opt.text = opt.text + " / " + tma_scorer3_name;
            }
            opt.text = opt.text + " (scored on " + tma_scorers_info[6] + "; using " + score_type + ")";
            opt.value = showTma_slicesUrl + "?tma_result_names_id=" + tma_scorers_info[7]; // this is tma_result_names id !!!
            if (tma_result_names_id==tma_scorers_info[7]) {
                opt.selected = "selected";
            }
            try {
                rselect.add(opt, null) // standards compliant; doesn't work in IE
            }
            catch (ex) {
                rselect.add(opt) // IE only
            }
        }
    }
}





var process = process || {env: {NODE_ENV: "development"}};
/* 
 * some util that would be helper in the whole tmadb
 */

/**
 * trim white spaces (front/end)
 * http://www.somacon.com/p355.php
 **/
function trim(stringToTrim) {
    return stringToTrim.replace(/^\s+|\s+$/g, "");
}

/**
 * return an instance of wait dialog with custom message
 * @param {type} title
 * @param {type} msg
 * @returns {undefined}
 */
function getWaitDialogMsg(title, msg) {
    var dialogObj = new dijit.Dialog({
        title: title,
        content: msg
    });
    dialogObj.closeButtonNode.style.display = "none"; // remove close [x] button!!!
    dialogObj.show();
    return dialogObj;
}
/**
 * return an instance of the wait dialog
 * also shows it!!!!
 */
function getWaitDialog() {
    return getWaitDialogMsg(
            "Please wait ...",
            "<img src='" + IMAGE_SRC_SPINNER + "'/> Please wait for the page to load ... please refresh the page if waited for more than a minute"
            );
}
/**
 * show a wait dialog (with custom message) that closes when wait_dialog_object_close_count reaches 0
 * 
 * @param count
 * @param title
 * @param msg
 */
function showWaitDialogMsg(count, title, msg) {
    wait_dialog_object = getWaitDialogMsg(title, msg);
    wait_dialog_object_close_count = count;
}
/**
 * show a wait dialog that closes when wait_dialog_object_close_count reaches 0
 * 
 * @param count
 */
function showWaitDialog(count) {
    wait_dialog_object = getWaitDialog();
    wait_dialog_object_close_count = count;
}
/**
 * decrement wait_dialog_object_close_count
 */
function closeWaitDialog() {
    wait_dialog_object_close_count = wait_dialog_object_close_count - 1;
    if (wait_dialog_object_close_count === 0) {
        wait_dialog_object.destroy();
    }
}

/**
 * return an instance of the message dialog with OK button
 * also shows it!!!!
 * reference: http://rleesjsblog.blogspot.ca/2011/02/simple-dojo-confirmation-dialog.html
 * 
 * example usage:
 * confirmationDialog({
 *   title:"WARNING",
 *   message:"Are you sure, Continue[Y/N]?",
 *   actionButtons:[
 *      {
 *          label:'No',
 *          callBack: function() {
 *              alert('no');
 *          }
 *      },
 *      {
 *          label:'Yes',
 *          callBack: function() {
 *              alert('yes');
 *          }
 *      }
 *   ]
 * });
 * @param configJson
 */
function confirmationDialog(configJson) {
    var dialog = new dijit.Dialog({
        title: configJson.title,
        content: ["<div style='width:50em'>", configJson.message, "</div>"].join(''),
        style: "background-color: white"
    });
    dialog.onButtonClickEvent = function (button) {
        return function () {
            button.callBack.apply(this, []);
            dialog.onCancel();
        };
    };
    for (actionButton in configJson.actionButtons) {
        if (configJson.actionButtons.hasOwnProperty(actionButton)) {
            dojo.place(new dijit.form.Button({
                label: configJson.actionButtons[actionButton].label,
                onClick: dialog.onButtonClickEvent.apply(dialog, [configJson.actionButtons[actionButton]])
            }).domNode,
                    dialog.containerNode,
                    'after');
        }
    }
    dialog.closeButtonNode.style.display = "none"; // remove close [x] button!!!
    return dialog;
}
/**
 * show message dialog - return pointer to dialog
 *
 * @param title
 * @param msg
 * @param callback
 */
function showMessageDialogWithCallBack(title, msg, callback) {
    var result = null;
    var dialog = confirmationDialog({
        title: title,
        message: msg,
        actionButtons: [
            {
                label: 'OK',
                callBack: function () {
                    result = true;
                    dialog.destroy();
                    callback();
                }
            }
        ]
    });
    dialog.startup();
    dialog.show().then(function () {
        return dialog;
    });
    message_dialog_object = dialog; // message_dialog_object is a global variable
    return dialog;
}

/**
 * show message dialog - no call back function
 * @param {type} title
 * @param {type} msg
 * @returns {undefined}
 */
function showMessageDialog(title, msg) {
    return showMessageDialogWithCallBack(title, msg, function () {});
}


/**
 * yes/no dialog ... run onConfirmed on click yes
 * 
 * @param {type} title
 * @param {type} msg
 * @param {type} buttonLabelConfim
 * @param {type} buttonLabelDecline
 * @param {type} onConfirmed
 * @returns {undefined}
 */
function showConfirmDialog(title, msg, buttonLabelConfim, buttonLabelDecline, onConfirmed) {
    var userResponse = new dojo.Deferred();
    var dialog = confirmationDialog({
        title: title,
        message: msg,
        actionButtons: [
            {
                label: buttonLabelDecline,
                callBack: function () {
                    userResponse.resolve(false);
                }
            },
            {
                label: buttonLabelConfim,
                callBack: function () {
                    userResponse.resolve(true);
                }
            }
        ]
    });
    dialog.startup();
    dialog.show();

    userResponse.then(function (confirmed) {
        if (confirmed) {
            onConfirmed();
        }
    });
}

/**
 * ask some questions and have use confirm before redirecting to create 
 * account page
 * 
 * @param url - url of create account page
 * 
 * @returns {undefined}
 */
function makeSureOkToGoToCreateAccountPage(url) {
    showConfirmDialog(
            "Please confirm ...",
            "Ki67-QC phase 3 participants, please DO NOT create an account here.  Please contact study coordinator for your username/password.",
            "No, I am NOT a Ki67-QC phase 3 participant", // confirm
            "Yes, I am a Ki67-QC phase 3 participant", // decline
            function () {
                window.location.href = url;
            }
    );
}


/**
 * 
 * @param {type} nodeId
 * @param {type} buttonId
 * @param {type} showButtonLabel
 * @param {type} hideButtonLabel
 * @param {type} initialHide
 * @returns {undefined}
 */
function showHideNode(nodeId, buttonId, showButtonLabel, hideButtonLabel, initialHide) {
    var toggler = new dojo.fx.Toggler({
        node: nodeId
    });
    dojo.connect(
            dijit.byId(buttonId),
            "onClick",
            toggler,
            function () {
                var b = dijit.byId(buttonId);
                if (b.label == showButtonLabel) {
                    b.setLabel(hideButtonLabel);
                    toggler.show();
                } else {
                    b.setLabel(showButtonLabel);
                    toggler.hide();
                }
            });
    if (initialHide) {
        toggler.hide();
    }
}

/**
 * force UI to redraw
 * reference: http://stackoverflow.com/questions/2921845/css-doesnt-apply-to-dynamically-created-elements-in-ie-7
 * @param {type} elm
 * @returns {redraw.elm}
 */
function redraw(elm) {
    var n = document.createTextNode(' ');
    elm.appendChild(n);
    setTimeout(function () {
        n.parentNode.removeChild(n)
    }, 0);
    return elm;
}

/**
 * escape character for javascript safe ... currently only escape single and double quote
 * ref: http://joshua.perina.com/post/javascript-replace-all-with-variable
 */
function escape_javascript(input) {
    return(input.replace(new RegExp("\'", 'g'), "\\x27").replace(new RegExp("\"", 'g'), "\\x22"))
}

/**
 * escape character for html safe ... currently only escape single and double quote
 */
function escape_javascript_for_html(input) {
    if (input == null) {
        return null;
    }
    return(input.replace(new RegExp("\'", 'g'), "&#39;").replace(new RegExp("\"", 'g'), "&#34;"))
}

/**
 * resize disorder table
 * 
 * @param gridId id of dojoGrid
 * @param gridOffset - distance between loginHeader and 
 */
function resizeDojoDataGrid_table(gridId) {
    if (dijit.byId(gridId) === null) {
        // no gridId ... just do nothing
        return;
    }
    // for firefox
    dojo.byId(gridId).setAttribute("style", "height:" + (document.getElementById("footer").getBoundingClientRect().top - document.getElementById(gridId).getBoundingClientRect().top) + "px");
    // for IE and Chrome
    dojo.style(gridId, "height", (document.getElementById("footer").getBoundingClientRect().top - document.getElementById(gridId).getBoundingClientRect().top) + "px");


    dijit.byId(gridId).resize();
    dijit.byId(gridId).update();
}

/**
 * get browser height
 * reference: http://stackoverflow.com/questions/3333329/javascript-get-browser-height
 */
function getBrowserHeight() {
    var myWidth = 0, myHeight = 0;
    if (typeof (window.innerWidth) == 'number') {
        //Non-IE
        myWidth = window.innerWidth;
        myHeight = window.innerHeight;
    } else if (document.documentElement && (document.documentElement.clientWidth || document.documentElement.clientHeight)) {
        //IE 6+ in 'standards compliant mode'
        myWidth = document.documentElement.clientWidth;
        myHeight = document.documentElement.clientHeight;
    } else if (document.body && (document.body.clientWidth || document.body.clientHeight)) {
        //IE 4 compatible
        myWidth = document.body.clientWidth;
        myHeight = document.body.clientHeight;
    }
    //window.alert( 'Width = ' + myWidth );
    //window.alert( 'Height = ' + myHeight );
    return myHeight;
}

/**
 * get browser width
 * reference: http://stackoverflow.com/questions/3333329/javascript-get-browser-height
 */
function getBrowserWidth() {
    var myWidth = 0, myHeight = 0;
    if (typeof (window.innerWidth) == 'number') {
        //Non-IE
        myWidth = window.innerWidth;
        myHeight = window.innerHeight;
    } else if (document.documentElement && (document.documentElement.clientWidth || document.documentElement.clientHeight)) {
        //IE 6+ in 'standards compliant mode'
        myWidth = document.documentElement.clientWidth;
        myHeight = document.documentElement.clientHeight;
    } else if (document.body && (document.body.clientWidth || document.body.clientHeight)) {
        //IE 4 compatible
        myWidth = document.body.clientWidth;
        myHeight = document.body.clientHeight;
    }
    //window.alert( 'Width = ' + myWidth );
    //window.alert( 'Height = ' + myHeight );
    return myWidth;
}

/**
 * set navBody height to height of browser
 * WARNING: all attributes except height should match main.css!!!!!  this is HARD CODED HERE
 */
function setInitialPageBodyHeight() {
    // for firefox!!
    dojo.byId('pageBody').setAttribute("style", "width:" + (getBrowserWidth() - PAGEBODY_MARGIN_LEFT) + "px; margin-left:" + PAGEBODY_MARGIN_LEFT + "px; margin-right:" + PAGEBODY_MARGIN_RIGHT + "px; height:" + (getBrowserHeight() - HEADER_HEIGHT - FOOTER_HEIGHT) + "px; margin-top: 0px; margin-bottom: 0px");
    // for IE and chrome
    dojo.style("pageBody", "margin", "0px " + PAGEBODY_MARGIN_RIGHT + "px 0px " + PAGEBODY_MARGIN_LEFT + "px"); // top, right, bottom, left, this format works for all (7,8,10) IE's 
    dojo.style("pageBody", "width", (getBrowserWidth() - PAGEBODY_MARGIN_LEFT) + "px"); // ignore IE6 bug
    dojo.style("pageBody", "height", (getBrowserHeight() - HEADER_HEIGHT - FOOTER_HEIGHT) + "px");

    // for IE to redraw ... needed for IE7
    redraw(dojo.byId('footer'));
}

/**
 * set initial footer position
 * - want to footer to be fix and below navBody
 */
function setInitialFooterPosition() {
    // for firefox!!
    dojo.byId('footer').setAttribute("style", "clear:both; float:left; width:100%; text-align: center; position: fixed; margin-top:" + (getBrowserHeight() - HEADER_HEIGHT) + "px; margin-bottom: 0px; margin-left: 0px; margin-right: 0px");
    //dojo.byId('footer').setAttribute("style", "clear:both; float:left; width:100%; text-align: center; position: fixed; padding-top:" + (getBrowserHeight() - HEADER_HEIGHT) + "px");
    // for IE and chrome
    dojo.style("footer", "clear", "both");
    dojo.style("footer", "float", "left");
    dojo.style("footer", "width", "100%"); // ignore IE6 bug
    dojo.style("footer", "text-align", "center");
    dojo.style("footer", "position", "fixed");
    //dojo.style("footer", "margin-top", (getBrowserHeight() - HEADER_HEIGHT) + "px"); // DOES NOT WORK IN IE7!!!
    dojo.style("footer", "margin", (getBrowserHeight() - HEADER_HEIGHT) + "px 0px 0px 0px"); // top, right, bottom, left, this format works for all (6,7,8,10) IE's
}

/**
 * set the input to null for all the fields with ids in the input array "field_ids"
 * 
 * @param {type} field_ids
 * @returns {undefined}
 */
function resetFormFields(field_ids) {
    for (var i = 0; i < field_ids.length; i++) {
        document.getElementById(field_ids[i]).value = ""; // if set to null, will set to default value instead of blank
    }
}

/**
 * CLEAN UP before redirecting to another page.
 * 
 * NOTE: this function is expected to be OVERRIDEN by definitions defined in downstream js/gsp files
 * @returns {undefined}
 */
function cleanUp() {
}
var process = process || {env: {NODE_ENV: "development"}};
/* 
 * table to browse the available whole section slices
 * 
 * ajaxResponse: response from ajax call
 * htmlTagName: name of html tag
 * slideCountTagName: name of the slide_count div
 */
function buildWhole_section_slicesBrowserTable(
        ajaxResponse,
        htmlTagName,
        slideCountTagName) {
    var dataStore;
    var grid;
    function format_name(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        return "<a href='" + items[1] + "' title='click me to view whole section slice details'>" + items[0] + "</a>";
    }
    function format_block(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        return "<a href='" + items[1] + "' title='click me to view block details'>" + items[0] + "</a>";
    }
    function format_biomarker(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        return "<a href='" + items[1] + "' title='click me to view biomarker details'>" + items[0] + "</a>";
    }
    function format_name_w_button(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        var button = new dijit.form.Button({
            label: "view",
            title: "click me to view whole section slice (" + items[0] + ")",
            onClick: function() {
                // generate preview window
                window.location.href = items[1];
            }
        });
        return button;
    }

    // The response comes back as a bunch-o-JSON
    var whole_section_slices;
    if (ajaxResponse == null) {
        whole_section_slices = eval("([])")	// evaluate JSON
    } else {
        whole_section_slices = ajaxResponse;	// ajaxResponse is a JSON
    }
    // width of table: 800 with right margin of 10px
    if (whole_section_slices) {
        dataStore = new dojo.data.ItemFileReadStore({data: whole_section_slices});
        // default ... not doing anything special
        grid = new dojox.grid.DataGrid({
            store: dataStore,
            structure: [
                {name: "Block", field: "block", width: "400px", formatter: format_block},
                {name: "Biomarker", field: "biomarker", width: "280px", formatter: format_biomarker},
                {name: "Slice", field: "name", width: "40px", formatter: format_name},
                {name: "View slice", field: "name", width: "80px", formatter: format_name_w_button}
            ],
            autoWidth: true,
            loadingMessage: "... loading ... please wait"
        }, htmlTagName);
    }
    grid.startup();

    // set the slide count ...
    var numSlides = whole_section_slices.items.length;
    var slideCountNode = document.createElement("a");
    slideCountNode.setAttribute("title", "this table contains " + numSlides + " slice" + (numSlides <= 1 ? "" : "s"));
    slideCountNode.appendChild(document.createTextNode(numSlides));
    dojo.byId(slideCountTagName).appendChild(slideCountNode);

    return grid;
}

/* 
 * select - whole section slices
 * 
 * ajaxResponse: response from ajax call
 * currSelectId: id of currently selected whole_section_slice
 * htmlTagName: name of html tag
 */
function buildWhole_section_slicesSelect(
        ajaxResponse,
        currSelectId,
        htmlTagName) {

    // The response comes back as a bunch-o-JSON
    var whole_section_slices;
    if (ajaxResponse === null) {
        whole_section_slices = eval("([])"); // generate empty JSON
    } else {
        whole_section_slices = ajaxResponse;	// ajaxResponse is a JSON
    }
    // width of table: 800 with right margin of 10px
    if (whole_section_slices) {
        // build select
        var rselect = document.getElementById(htmlTagName)
        for (var i = 0; i < whole_section_slices.items.length; i++) {
            var whole_section_slice = whole_section_slices.items[i]
            var opt = document.createElement('option');
            var tempArr = whole_section_slice.name.split(AJAX_RESPONSE_DELIMITER);
            opt.text = whole_section_slice.block.split(AJAX_RESPONSE_DELIMITER)[0] + " " + whole_section_slice.biomarker.split(AJAX_RESPONSE_DELIMITER)[0] + " slice:" + tempArr[0];
            opt.value = tempArr[1];
            opt.title = "click me to view whole section slice details";
            if (whole_section_slice.id === currSelectId) {
                opt.selected = true;
            }
            try {
                rselect.add(opt, null) // standards compliant; doesn't work in IE
            }
            catch (ex) {
                rselect.add(opt) // IE only
            }
        }


    }

}
var process = process || {env: {NODE_ENV: "development"}};

/*
    Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
    Available via Academic Free License >= 2.1 OR the modified BSD license.
    see: http://dojotoolkit.org/license for details
*/

//>>built
define("dojo/_base/xhr",["./kernel","./sniff","require","../io-query","../dom","../dom-form","./Deferred","./json","./lang","./array","../on"],function(_1,_2,_3,_4,_5,_6,_7,_8,_9,_a,on){_2.add("native-xhr",function(){return typeof XMLHttpRequest!=="undefined";});if(1){_1._xhrObj=_3.getXhr;}else{if(_2("native-xhr")){_1._xhrObj=function(){try{return new XMLHttpRequest();}catch(e){throw new Error("XMLHTTP not available: "+e);}};}else{for(var _b=["Msxml2.XMLHTTP","Microsoft.XMLHTTP","Msxml2.XMLHTTP.4.0"],_c,i=0;i<3;){try{_c=_b[i++];if(new ActiveXObject(_c)){break;}}catch(e){}}_1._xhrObj=function(){return new ActiveXObject(_c);};}}var _d=_1.config;_1.objectToQuery=_4.objectToQuery;_1.queryToObject=_4.queryToObject;_1.fieldToObject=_6.fieldToObject;_1.formToObject=_6.toObject;_1.formToQuery=_6.toQuery;_1.formToJson=_6.toJson;_1._blockAsync=false;var _e=_1._contentHandlers=_1.contentHandlers={"text":function(_f){return _f.responseText;},"json":function(xhr){return _8.fromJson(xhr.responseText||null);},"json-comment-filtered":function(xhr){if(!_1.config.useCommentedJson){console.warn("Consider using the standard mimetype:application/json."+" json-commenting can introduce security issues. To"+" decrease the chances of hijacking, use the standard the 'json' handler and"+" prefix your json with: {}&&\n"+"Use djConfig.useCommentedJson=true to turn off this message.");}var _10=xhr.responseText;var _11=_10.indexOf("/*");var _12=_10.lastIndexOf("*/");if(_11==-1||_12==-1){throw new Error("JSON was not comment filtered");}return _8.fromJson(_10.substring(_11+2,_12));},"javascript":function(xhr){return _1.eval(xhr.responseText);},"xml":function(xhr){var _13=xhr.responseXML;if(_2("ie")){if((!_13||!_13.documentElement)){var ms=function(n){return "MSXML"+n+".DOMDocument";};var dp=["Microsoft.XMLDOM",ms(6),ms(4),ms(3),ms(2)];_a.some(dp,function(p){try{var dom=new ActiveXObject(p);dom.async=false;dom.loadXML(xhr.responseText);_13=dom;}catch(e){return false;}return true;});}}return _13;},"json-comment-optional":function(xhr){if(xhr.responseText&&/^[^{\[]*\/\*/.test(xhr.responseText)){return _e["json-comment-filtered"](xhr);}else{return _e["json"](xhr);}}};_1._ioSetArgs=function(_14,_15,_16,_17){var _18={args:_14,url:_14.url};var _19=null;if(_14.form){var _1a=_5.byId(_14.form);var _1b=_1a.getAttributeNode("action");_18.url=_18.url||(_1b?_1b.value:null);_19=_6.toObject(_1a);}var _1c=[{}];if(_19){_1c.push(_19);}if(_14.content){_1c.push(_14.content);}if(_14.preventCache){_1c.push({"dojo.preventCache":new Date().valueOf()});}_18.query=_4.objectToQuery(_9.mixin.apply(null,_1c));_18.handleAs=_14.handleAs||"text";var d=new _7(_15);d.addCallbacks(_16,function(_1d){return _17(_1d,d);});var ld=_14.load;if(ld&&_9.isFunction(ld)){d.addCallback(function(_1e){return ld.call(_14,_1e,_18);});}var err=_14.error;if(err&&_9.isFunction(err)){d.addErrback(function(_1f){return err.call(_14,_1f,_18);});}var _20=_14.handle;if(_20&&_9.isFunction(_20)){d.addBoth(function(_21){return _20.call(_14,_21,_18);});}if(_d.ioPublish&&_1.publish&&_18.args.ioPublish!==false){d.addCallbacks(function(res){_1.publish("/dojo/io/load",[d,res]);return res;},function(res){_1.publish("/dojo/io/error",[d,res]);return res;});d.addBoth(function(res){_1.publish("/dojo/io/done",[d,res]);return res;});}d.ioArgs=_18;return d;};var _22=function(dfd){dfd.canceled=true;var xhr=dfd.ioArgs.xhr;var _23=typeof xhr.abort;if(_23=="function"||_23=="object"||_23=="unknown"){xhr.abort();}var err=dfd.ioArgs.error;if(!err){err=new Error("xhr cancelled");err.dojoType="cancel";}return err;};var _24=function(dfd){var ret=_e[dfd.ioArgs.handleAs](dfd.ioArgs.xhr);return ret===undefined?null:ret;};var _25=function(_26,dfd){if(!dfd.ioArgs.args.failOk){console.error(_26);}return _26;};var _27=null;var _28=[];var _29=0;var _2a=function(dfd){if(_29<=0){_29=0;if(_d.ioPublish&&_1.publish&&(!dfd||dfd&&dfd.ioArgs.args.ioPublish!==false)){_1.publish("/dojo/io/stop");}}};var _2b=function(){var now=(new Date()).getTime();if(!_1._blockAsync){for(var i=0,tif;i<_28.length&&(tif=_28[i]);i++){var dfd=tif.dfd;var _2c=function(){if(!dfd||dfd.canceled||!tif.validCheck(dfd)){_28.splice(i--,1);_29-=1;}else{if(tif.ioCheck(dfd)){_28.splice(i--,1);tif.resHandle(dfd);_29-=1;}else{if(dfd.startTime){if(dfd.startTime+(dfd.ioArgs.args.timeout||0)<now){_28.splice(i--,1);var err=new Error("timeout exceeded");err.dojoType="timeout";dfd.errback(err);dfd.cancel();_29-=1;}}}}};if(_1.config.debugAtAllCosts){_2c.call(this);}else{_2c.call(this);}}}_2a(dfd);if(!_28.length){clearInterval(_27);_27=null;}};_1._ioCancelAll=function(){try{_a.forEach(_28,function(i){try{i.dfd.cancel();}catch(e){}});}catch(e){}};if(_2("ie")){on(window,"unload",_1._ioCancelAll);}_1._ioNotifyStart=function(dfd){if(_d.ioPublish&&_1.publish&&dfd.ioArgs.args.ioPublish!==false){if(!_29){_1.publish("/dojo/io/start");}_29+=1;_1.publish("/dojo/io/send",[dfd]);}};_1._ioWatch=function(dfd,_2d,_2e,_2f){var _30=dfd.ioArgs.args;if(_30.timeout){dfd.startTime=(new Date()).getTime();}_28.push({dfd:dfd,validCheck:_2d,ioCheck:_2e,resHandle:_2f});if(!_27){_27=setInterval(_2b,50);}if(_30.sync){_2b();}};var _31="application/x-www-form-urlencoded";var _32=function(dfd){return dfd.ioArgs.xhr.readyState;};var _33=function(dfd){return 4==dfd.ioArgs.xhr.readyState;};var _34=function(dfd){var xhr=dfd.ioArgs.xhr;if(_1._isDocumentOk(xhr)){dfd.callback(dfd);}else{var err=new Error("Unable to load "+dfd.ioArgs.url+" status:"+xhr.status);err.status=xhr.status;err.responseText=xhr.responseText;err.xhr=xhr;dfd.errback(err);}};_1._ioAddQueryToUrl=function(_35){if(_35.query.length){_35.url+=(_35.url.indexOf("?")==-1?"?":"&")+_35.query;_35.query=null;}};_1.xhr=function(_36,_37,_38){var dfd=_1._ioSetArgs(_37,_22,_24,_25);var _39=dfd.ioArgs;var xhr=_39.xhr=_1._xhrObj(_39.args);if(!xhr){dfd.cancel();return dfd;}if("postData" in _37){_39.query=_37.postData;}else{if("putData" in _37){_39.query=_37.putData;}else{if("rawBody" in _37){_39.query=_37.rawBody;}else{if((arguments.length>2&&!_38)||"POST|PUT".indexOf(_36.toUpperCase())==-1){_1._ioAddQueryToUrl(_39);}}}}xhr.open(_36,_39.url,_37.sync!==true,_37.user||undefined,_37.password||undefined);if(_37.headers){for(var hdr in _37.headers){if(hdr.toLowerCase()==="content-type"&&!_37.contentType){_37.contentType=_37.headers[hdr];}else{if(_37.headers[hdr]){xhr.setRequestHeader(hdr,_37.headers[hdr]);}}}}if(_37.contentType!==false){xhr.setRequestHeader("Content-Type",_37.contentType||_31);}if(!_37.headers||!("X-Requested-With" in _37.headers)){xhr.setRequestHeader("X-Requested-With","XMLHttpRequest");}_1._ioNotifyStart(dfd);if(_1.config.debugAtAllCosts){xhr.send(_39.query);}else{try{xhr.send(_39.query);}catch(e){_39.error=e;dfd.cancel();}}_1._ioWatch(dfd,_32,_33,_34);xhr=null;return dfd;};_1.xhrGet=function(_3a){return _1.xhr("GET",_3a);};_1.rawXhrPost=_1.xhrPost=function(_3b){return _1.xhr("POST",_3b,true);};_1.rawXhrPut=_1.xhrPut=function(_3c){return _1.xhr("PUT",_3c,true);};_1.xhrDelete=function(_3d){return _1.xhr("DELETE",_3d);};_1._isDocumentOk=function(_3e){var _3f=_3e.status||0;_3f=(_3f>=200&&_3f<300)||_3f==304||_3f==1223||!_3f;return _3f;};_1._getText=function(url){var _40;_1.xhrGet({url:url,sync:true,load:function(_41){_40=_41;}});return _40;};_9.mixin(_1.xhr,{_xhrObj:_1._xhrObj,fieldToObject:_6.fieldToObject,formToObject:_6.toObject,objectToQuery:_4.objectToQuery,formToQuery:_6.toQuery,formToJson:_6.toJson,queryToObject:_4.queryToObject,contentHandlers:_e,_ioSetArgs:_1._ioSetArgs,_ioCancelAll:_1._ioCancelAll,_ioNotifyStart:_1._ioNotifyStart,_ioWatch:_1._ioWatch,_ioAddQueryToUrl:_1._ioAddQueryToUrl,_isDocumentOk:_1._isDocumentOk,_getText:_1._getText,get:_1.xhrGet,post:_1.xhrPost,put:_1.xhrPut,del:_1.xhrDelete});return _1.xhr;});

var process = process || {env: {NODE_ENV: "development"}};
/*
Copyright (c) 2006, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.net/yui/license.txt
version: 0.10.0
*/
YAHOO.util.Anim=function(el,attributes,duration,method){if(el){this.init(el,attributes,duration,method);}};YAHOO.util.Anim.prototype={doMethod:function(attribute,start,end){return this.method(this.currentFrame,start,end-start,this.totalFrames);},setAttribute:function(attribute,val,unit){YAHOO.util.Dom.setStyle(this.getEl(),attribute,val+unit);},getAttribute:function(attribute){return parseFloat(YAHOO.util.Dom.getStyle(this.getEl(),attribute));},defaultUnit:'px',defaultUnits:{opacity:' '},init:function(el,attributes,duration,method){var isAnimated=false;var startTime=null;var endTime=null;var actualFrames=0;var defaultValues={};el=YAHOO.util.Dom.get(el);this.attributes=attributes||{};this.duration=duration||1;this.method=method||YAHOO.util.Easing.easeNone;this.useSeconds=true;this.currentFrame=0;this.totalFrames=YAHOO.util.AnimMgr.fps;this.getEl=function(){return el;};this.setDefault=function(attribute,val){if(val.constructor!=Array&&(val=='auto'||isNaN(val))){switch(attribute){case'width':val=el.clientWidth||el.offsetWidth;break;case'height':val=el.clientHeight||el.offsetHeight;break;case'left':if(YAHOO.util.Dom.getStyle(el,'position')=='absolute'){val=el.offsetLeft;}else{val=0;}break;case'top':if(YAHOO.util.Dom.getStyle(el,'position')=='absolute'){val=el.offsetTop;}else{val=0;}break;default:val=0;}}defaultValues[attribute]=val;};this.getDefault=function(attribute){return defaultValues[attribute];};this.isAnimated=function(){return isAnimated;};this.getStartTime=function(){return startTime;};this.animate=function(){if(this.isAnimated()){return false;}this.onStart.fire();this._onStart.fire();this.totalFrames=(this.useSeconds)?Math.ceil(YAHOO.util.AnimMgr.fps*this.duration):this.duration;YAHOO.util.AnimMgr.registerElement(this);var attributes=this.attributes;var el=this.getEl();var val;for(var attribute in attributes){val=this.getAttribute(attribute);this.setDefault(attribute,val);}isAnimated=true;actualFrames=0;startTime=new Date();};this.stop=function(){if(!this.isAnimated()){return false;}this.currentFrame=0;endTime=new Date();var data={time:endTime,duration:endTime-startTime,frames:actualFrames,fps:actualFrames/this.duration};isAnimated=false;actualFrames=0;this.onComplete.fire(data);};var onTween=function(){var start;var end=null;var val;var unit;var attributes=this['attributes'];for(var attribute in attributes){unit=attributes[attribute]['unit']||this.defaultUnits[attribute]||this.defaultUnit;if(typeof attributes[attribute]['from']!='undefined'){start=attributes[attribute]['from'];}else{start=this.getDefault(attribute);}if(typeof attributes[attribute]['to']!='undefined'){end=attributes[attribute]['to'];}else if(typeof attributes[attribute]['by']!='undefined'){if(start.constructor==Array){end=[];for(var i=0,len=start.length;i<len;++i){end[i]=start[i]+attributes[attribute]['by'][i];}}else{end=start+attributes[attribute]['by'];}}if(end!==null&&typeof end!='undefined'){val=this.doMethod(attribute,start,end);if((attribute=='width'||attribute=='height'||attribute=='opacity')&&val<0){val=0;}this.setAttribute(attribute,val,unit);}}actualFrames+=1;};this._onStart=new YAHOO.util.CustomEvent('_onStart',this);this.onStart=new YAHOO.util.CustomEvent('start',this);this.onTween=new YAHOO.util.CustomEvent('tween',this);this._onTween=new YAHOO.util.CustomEvent('_tween',this);this.onComplete=new YAHOO.util.CustomEvent('complete',this);this._onTween.subscribe(onTween);}};YAHOO.util.AnimMgr=new function(){var thread=null;var queue=[];var tweenCount=0;this.fps=200;this.delay=1;this.registerElement=function(tween){if(tween.isAnimated()){return false;}queue[queue.length]=tween;tweenCount+=1;this.start();};this.start=function(){if(thread===null){thread=setInterval(this.run,this.delay);}};this.stop=function(tween){if(!tween){clearInterval(thread);for(var i=0,len=queue.length;i<len;++i){if(queue[i].isAnimated()){queue[i].stop();}}queue=[];thread=null;tweenCount=0;}else{tween.stop();tweenCount-=1;if(tweenCount<=0){this.stop();}}};this.run=function(){for(var i=0,len=queue.length;i<len;++i){var tween=queue[i];if(!tween||!tween.isAnimated()){continue;}if(tween.currentFrame<tween.totalFrames||tween.totalFrames===null){tween.currentFrame+=1;if(tween.useSeconds){correctFrame(tween);}tween.onTween.fire();tween._onTween.fire();}else{YAHOO.util.AnimMgr.stop(tween);}}};var correctFrame=function(tween){var frames=tween.totalFrames;var frame=tween.currentFrame;var expected=(tween.currentFrame*tween.duration*1000/tween.totalFrames);var elapsed=(new Date()-tween.getStartTime());var tweak=0;if(elapsed<tween.duration*1000){tweak=Math.round((elapsed/expected-1)*tween.currentFrame);}else{tweak=frames-(frame+1);}if(tweak>0&&isFinite(tweak)){if(tween.currentFrame+tweak>=frames){tweak=frames-(frame+1);}tween.currentFrame+=tweak;}};};YAHOO.util.Bezier=new function(){this.getPosition=function(points,t){var n=points.length;var tmp=[];for(var i=0;i<n;++i){tmp[i]=[points[i][0],points[i][1]];}for(var j=1;j<n;++j){for(i=0;i<n-j;++i){tmp[i][0]=(1-t)*tmp[i][0]+t*tmp[parseInt(i+1,10)][0];tmp[i][1]=(1-t)*tmp[i][1]+t*tmp[parseInt(i+1,10)][1];}}return[tmp[0][0],tmp[0][1]];};};YAHOO.util.Easing=new function(){this.easeNone=function(t,b,c,d){return b+c*(t/=d);};this.easeIn=function(t,b,c,d){return b+c*((t/=d)*t*t);};this.easeOut=function(t,b,c,d){var ts=(t/=d)*t;var tc=ts*t;return b+c*(tc+-3*ts+3*t);};this.easeBoth=function(t,b,c,d){var ts=(t/=d)*t;var tc=ts*t;return b+c*(-2*tc+3*ts);};this.backIn=function(t,b,c,d){var ts=(t/=d)*t;var tc=ts*t;return b+c*(-3.4005*tc*ts+10.2*ts*ts+-6.2*tc+0.4*ts);};this.backOut=function(t,b,c,d){var ts=(t/=d)*t;var tc=ts*t;return b+c*(8.292*tc*ts+-21.88*ts*ts+22.08*tc+-12.69*ts+5.1975*t);};this.backBoth=function(t,b,c,d){var ts=(t/=d)*t;var tc=ts*t;return b+c*(0.402*tc*ts+-2.1525*ts*ts+-3.2*tc+8*ts+-2.05*t);};};YAHOO.util.Motion=function(el,attributes,duration,method){if(el){this.initMotion(el,attributes,duration,method);}};YAHOO.util.Motion.prototype=new YAHOO.util.Anim();YAHOO.util.Motion.prototype.defaultUnits.points='px';YAHOO.util.Motion.prototype.doMethod=function(attribute,start,end){var val=null;if(attribute=='points'){var translatedPoints=this.getTranslatedPoints();var t=this.method(this.currentFrame,0,100,this.totalFrames)/100;if(translatedPoints){val=YAHOO.util.Bezier.getPosition(translatedPoints,t);}}else{val=this.method(this.currentFrame,start,end-start,this.totalFrames);}return val;};YAHOO.util.Motion.prototype.getAttribute=function(attribute){var val=null;if(attribute=='points'){val=[this.getAttribute('left'),this.getAttribute('top')];if(isNaN(val[0])){val[0]=0;}if(isNaN(val[1])){val[1]=0;}}else{val=parseFloat(YAHOO.util.Dom.getStyle(this.getEl(),attribute));}return val;};YAHOO.util.Motion.prototype.setAttribute=function(attribute,val,unit){if(attribute=='points'){YAHOO.util.Dom.setStyle(this.getEl(),'left',val[0]+unit);YAHOO.util.Dom.setStyle(this.getEl(),'top',val[1]+unit);}else{YAHOO.util.Dom.setStyle(this.getEl(),attribute,val+unit);}};YAHOO.util.Motion.prototype.initMotion=function(el,attributes,duration,method){YAHOO.util.Anim.call(this,el,attributes,duration,method);attributes=attributes||{};attributes.points=attributes.points||{};attributes.points.control=attributes.points.control||[];this.attributes=attributes;var start;var end=null;var translatedPoints=null;this.getTranslatedPoints=function(){return translatedPoints;};var translateValues=function(val,self){var pageXY=YAHOO.util.Dom.getXY(self.getEl());val=[val[0]-pageXY[0]+start[0],val[1]-pageXY[1]+start[1]];return val;};var onStart=function(){start=this.getAttribute('points');var attributes=this.attributes;var control=attributes['points']['control']||[];if(control.length>0&&control[0].constructor!=Array){control=[control];}if(YAHOO.util.Dom.getStyle(this.getEl(),'position')=='static'){YAHOO.util.Dom.setStyle(this.getEl(),'position','relative');}if(typeof attributes['points']['from']!='undefined'){YAHOO.util.Dom.setXY(this.getEl(),attributes['points']['from']);start=this.getAttribute('points');}else if((start[0]===0||start[1]===0)){YAHOO.util.Dom.setXY(this.getEl(),YAHOO.util.Dom.getXY(this.getEl()));start=this.getAttribute('points');}var i,len;if(typeof attributes['points']['to']!='undefined'){end=translateValues(attributes['points']['to'],this);for(i=0,len=control.length;i<len;++i){control[i]=translateValues(control[i],this);}}else if(typeof attributes['points']['by']!='undefined'){end=[start[0]+attributes['points']['by'][0],start[1]+attributes['points']['by'][1]];for(i=0,len=control.length;i<len;++i){control[i]=[start[0]+control[i][0],start[1]+control[i][1]];}}if(end){translatedPoints=[start];if(control.length>0){translatedPoints=translatedPoints.concat(control);}translatedPoints[translatedPoints.length]=end;}};this._onStart.subscribe(onStart);};YAHOO.util.Scroll=function(el,attributes,duration,method){if(el){YAHOO.util.Anim.call(this,el,attributes,duration,method);}};YAHOO.util.Scroll.prototype=new YAHOO.util.Anim();YAHOO.util.Scroll.prototype.defaultUnits.scroll=' ';YAHOO.util.Scroll.prototype.doMethod=function(attribute,start,end){var val=null;if(attribute=='scroll'){val=[this.method(this.currentFrame,start[0],end[0]-start[0],this.totalFrames),this.method(this.currentFrame,start[1],end[1]-start[1],this.totalFrames)];}else{val=this.method(this.currentFrame,start,end-start,this.totalFrames);}return val;};YAHOO.util.Scroll.prototype.getAttribute=function(attribute){var val=null;var el=this.getEl();if(attribute=='scroll'){val=[el.scrollLeft,el.scrollTop];}else{val=parseFloat(YAHOO.util.Dom.getStyle(el,attribute));}return val;};YAHOO.util.Scroll.prototype.setAttribute=function(attribute,val,unit){var el=this.getEl();if(attribute=='scroll'){el.scrollLeft=val[0];el.scrollTop=val[1];}else{YAHOO.util.Dom.setStyle(el,attribute,val+unit);}};

var process = process || {env: {NODE_ENV: "development"}};
// script.aculo.us builder.js v1.8.3, Thu Oct 08 11:23:33 +0200 2009

// Copyright (c) 2005-2009 Thomas Fuchs (http://script.aculo.us, http://mir.aculo.us)
//
// script.aculo.us is freely distributable under the terms of an MIT-style license.
// For details, see the script.aculo.us web site: http://script.aculo.us/

var Builder = {
  NODEMAP: {
    AREA: 'map',
    CAPTION: 'table',
    COL: 'table',
    COLGROUP: 'table',
    LEGEND: 'fieldset',
    OPTGROUP: 'select',
    OPTION: 'select',
    PARAM: 'object',
    TBODY: 'table',
    TD: 'table',
    TFOOT: 'table',
    TH: 'table',
    THEAD: 'table',
    TR: 'table'
  },
  // note: For Firefox < 1.5, OPTION and OPTGROUP tags are currently broken,
  //       due to a Firefox bug
  node: function(elementName) {
    elementName = elementName.toUpperCase();

    // try innerHTML approach
    var parentTag = this.NODEMAP[elementName] || 'div';
    var parentElement = document.createElement(parentTag);
    try { // prevent IE "feature": http://dev.rubyonrails.org/ticket/2707
      parentElement.innerHTML = "<" + elementName + "></" + elementName + ">";
    } catch(e) {}
    var element = parentElement.firstChild || null;

    // see if browser added wrapping tags
    if(element && (element.tagName.toUpperCase() != elementName))
      element = element.getElementsByTagName(elementName)[0];

    // fallback to createElement approach
    if(!element) element = document.createElement(elementName);

    // abort if nothing could be created
    if(!element) return;

    // attributes (or text)
    if(arguments[1])
      if(this._isStringOrNumber(arguments[1]) ||
        (arguments[1] instanceof Array) ||
        arguments[1].tagName) {
          this._children(element, arguments[1]);
        } else {
          var attrs = this._attributes(arguments[1]);
          if(attrs.length) {
            try { // prevent IE "feature": http://dev.rubyonrails.org/ticket/2707
              parentElement.innerHTML = "<" +elementName + " " +
                attrs + "></" + elementName + ">";
            } catch(e) {}
            element = parentElement.firstChild || null;
            // workaround firefox 1.0.X bug
            if(!element) {
              element = document.createElement(elementName);
              for(attr in arguments[1])
                element[attr == 'class' ? 'className' : attr] = arguments[1][attr];
            }
            if(element.tagName.toUpperCase() != elementName)
              element = parentElement.getElementsByTagName(elementName)[0];
          }
        }

    // text, or array of children
    if(arguments[2])
      this._children(element, arguments[2]);

     return $(element);
  },
  _text: function(text) {
     return document.createTextNode(text);
  },

  ATTR_MAP: {
    'className': 'class',
    'htmlFor': 'for'
  },

  _attributes: function(attributes) {
    var attrs = [];
    for(attribute in attributes)
      attrs.push((attribute in this.ATTR_MAP ? this.ATTR_MAP[attribute] : attribute) +
          '="' + attributes[attribute].toString().escapeHTML().gsub(/"/,'&quot;') + '"');
    return attrs.join(" ");
  },
  _children: function(element, children) {
    if(children.tagName) {
      element.appendChild(children);
      return;
    }
    if(typeof children=='object') { // array can hold nodes and text
      children.flatten().each( function(e) {
        if(typeof e=='object')
          element.appendChild(e);
        else
          if(Builder._isStringOrNumber(e))
            element.appendChild(Builder._text(e));
      });
    } else
      if(Builder._isStringOrNumber(children))
        element.appendChild(Builder._text(children));
  },
  _isStringOrNumber: function(param) {
    return(typeof param=='string' || typeof param=='number');
  },
  build: function(html) {
    var element = this.node('div');
    $(element).update(html.strip());
    return element.down();
  },
  dump: function(scope) {
    if(typeof scope != 'object' && typeof scope != 'function') scope = window; //global scope

    var tags = ("A ABBR ACRONYM ADDRESS APPLET AREA B BASE BASEFONT BDO BIG BLOCKQUOTE BODY " +
      "BR BUTTON CAPTION CENTER CITE CODE COL COLGROUP DD DEL DFN DIR DIV DL DT EM FIELDSET " +
      "FONT FORM FRAME FRAMESET H1 H2 H3 H4 H5 H6 HEAD HR HTML I IFRAME IMG INPUT INS ISINDEX "+
      "KBD LABEL LEGEND LI LINK MAP MENU META NOFRAMES NOSCRIPT OBJECT OL OPTGROUP OPTION P "+
      "PARAM PRE Q S SAMP SCRIPT SELECT SMALL SPAN STRIKE STRONG STYLE SUB SUP TABLE TBODY TD "+
      "TEXTAREA TFOOT TH THEAD TITLE TR TT U UL VAR").split(/\s+/);

    tags.each( function(tag){
      scope[tag] = function() {
        return Builder.node.apply(Builder, [tag].concat($A(arguments)));
      };
    });
  }
};
var process = process || {env: {NODE_ENV: "development"}};
// script.aculo.us controls.js v1.8.3, Thu Oct 08 11:23:33 +0200 2009

// Copyright (c) 2005-2009 Thomas Fuchs (http://script.aculo.us, http://mir.aculo.us)
//           (c) 2005-2009 Ivan Krstic (http://blogs.law.harvard.edu/ivan)
//           (c) 2005-2009 Jon Tirsen (http://www.tirsen.com)
// Contributors:
//  Richard Livsey
//  Rahul Bhargava
//  Rob Wills
//
// script.aculo.us is freely distributable under the terms of an MIT-style license.
// For details, see the script.aculo.us web site: http://script.aculo.us/

// Autocompleter.Base handles all the autocompletion functionality
// that's independent of the data source for autocompletion. This
// includes drawing the autocompletion menu, observing keyboard
// and mouse events, and similar.
//
// Specific autocompleters need to provide, at the very least,
// a getUpdatedChoices function that will be invoked every time
// the text inside the monitored textbox changes. This method
// should get the text for which to provide autocompletion by
// invoking this.getToken(), NOT by directly accessing
// this.element.value. This is to allow incremental tokenized
// autocompletion. Specific auto-completion logic (AJAX, etc)
// belongs in getUpdatedChoices.
//
// Tokenized incremental autocompletion is enabled automatically
// when an autocompleter is instantiated with the 'tokens' option
// in the options parameter, e.g.:
// new Ajax.Autocompleter('id','upd', '/url/', { tokens: ',' });
// will incrementally autocomplete with a comma as the token.
// Additionally, ',' in the above example can be replaced with
// a token array, e.g. { tokens: [',', '\n'] } which
// enables autocompletion on multiple tokens. This is most
// useful when one of the tokens is \n (a newline), as it
// allows smart autocompletion after linebreaks.

if(typeof Effect == 'undefined')
  throw("controls.js requires including script.aculo.us' effects.js library");

var Autocompleter = { };
Autocompleter.Base = Class.create({
  baseInitialize: function(element, update, options) {
    element          = $(element);
    this.element     = element;
    this.update      = $(update);
    this.hasFocus    = false;
    this.changed     = false;
    this.active      = false;
    this.index       = 0;
    this.entryCount  = 0;
    this.oldElementValue = this.element.value;

    if(this.setOptions)
      this.setOptions(options);
    else
      this.options = options || { };

    this.options.paramName    = this.options.paramName || this.element.name;
    this.options.tokens       = this.options.tokens || [];
    this.options.frequency    = this.options.frequency || 0.4;
    this.options.minChars     = this.options.minChars || 1;
    this.options.onShow       = this.options.onShow ||
      function(element, update){
        if(!update.style.position || update.style.position=='absolute') {
          update.style.position = 'absolute';
          Position.clone(element, update, {
            setHeight: false,
            offsetTop: element.offsetHeight
          });
        }
        Effect.Appear(update,{duration:0.15});
      };
    this.options.onHide = this.options.onHide ||
      function(element, update){ new Effect.Fade(update,{duration:0.15}) };

    if(typeof(this.options.tokens) == 'string')
      this.options.tokens = new Array(this.options.tokens);
    // Force carriage returns as token delimiters anyway
    if (!this.options.tokens.include('\n'))
      this.options.tokens.push('\n');

    this.observer = null;

    this.element.setAttribute('autocomplete','off');

    Element.hide(this.update);

    Event.observe(this.element, 'blur', this.onBlur.bindAsEventListener(this));
    Event.observe(this.element, 'keydown', this.onKeyPress.bindAsEventListener(this));
  },

  show: function() {
    if(Element.getStyle(this.update, 'display')=='none') this.options.onShow(this.element, this.update);
    if(!this.iefix &&
      (Prototype.Browser.IE) &&
      (Element.getStyle(this.update, 'position')=='absolute')) {
      new Insertion.After(this.update,
       '<iframe id="' + this.update.id + '_iefix" '+
       'style="display:none;position:absolute;filter:progid:DXImageTransform.Microsoft.Alpha(opacity=0);" ' +
       'src="javascript:false;" frameborder="0" scrolling="no"></iframe>');
      this.iefix = $(this.update.id+'_iefix');
    }
    if(this.iefix) setTimeout(this.fixIEOverlapping.bind(this), 50);
  },

  fixIEOverlapping: function() {
    Position.clone(this.update, this.iefix, {setTop:(!this.update.style.height)});
    this.iefix.style.zIndex = 1;
    this.update.style.zIndex = 2;
    Element.show(this.iefix);
  },

  hide: function() {
    this.stopIndicator();
    if(Element.getStyle(this.update, 'display')!='none') this.options.onHide(this.element, this.update);
    if(this.iefix) Element.hide(this.iefix);
  },

  startIndicator: function() {
    if(this.options.indicator) Element.show(this.options.indicator);
  },

  stopIndicator: function() {
    if(this.options.indicator) Element.hide(this.options.indicator);
  },

  onKeyPress: function(event) {
    if(this.active)
      switch(event.keyCode) {
       case Event.KEY_TAB:
       case Event.KEY_RETURN:
         this.selectEntry();
         Event.stop(event);
       case Event.KEY_ESC:
         this.hide();
         this.active = false;
         Event.stop(event);
         return;
       case Event.KEY_LEFT:
       case Event.KEY_RIGHT:
         return;
       case Event.KEY_UP:
         this.markPrevious();
         this.render();
         Event.stop(event);
         return;
       case Event.KEY_DOWN:
         this.markNext();
         this.render();
         Event.stop(event);
         return;
      }
     else
       if(event.keyCode==Event.KEY_TAB || event.keyCode==Event.KEY_RETURN ||
         (Prototype.Browser.WebKit > 0 && event.keyCode == 0)) return;

    this.changed = true;
    this.hasFocus = true;

    if(this.observer) clearTimeout(this.observer);
      this.observer =
        setTimeout(this.onObserverEvent.bind(this), this.options.frequency*1000);
  },

  activate: function() {
    this.changed = false;
    this.hasFocus = true;
    this.getUpdatedChoices();
  },

  onHover: function(event) {
    var element = Event.findElement(event, 'LI');
    if(this.index != element.autocompleteIndex)
    {
        this.index = element.autocompleteIndex;
        this.render();
    }
    Event.stop(event);
  },

  onClick: function(event) {
    var element = Event.findElement(event, 'LI');
    this.index = element.autocompleteIndex;
    this.selectEntry();
    this.hide();
  },

  onBlur: function(event) {
    // needed to make click events working
    setTimeout(this.hide.bind(this), 250);
    this.hasFocus = false;
    this.active = false;
  },

  render: function() {
    if(this.entryCount > 0) {
      for (var i = 0; i < this.entryCount; i++)
        this.index==i ?
          Element.addClassName(this.getEntry(i),"selected") :
          Element.removeClassName(this.getEntry(i),"selected");
      if(this.hasFocus) {
        this.show();
        this.active = true;
      }
    } else {
      this.active = false;
      this.hide();
    }
  },

  markPrevious: function() {
    if(this.index > 0) this.index--;
      else this.index = this.entryCount-1;
    this.getEntry(this.index).scrollIntoView(true);
  },

  markNext: function() {
    if(this.index < this.entryCount-1) this.index++;
      else this.index = 0;
    this.getEntry(this.index).scrollIntoView(false);
  },

  getEntry: function(index) {
    return this.update.firstChild.childNodes[index];
  },

  getCurrentEntry: function() {
    return this.getEntry(this.index);
  },

  selectEntry: function() {
    this.active = false;
    this.updateElement(this.getCurrentEntry());
  },

  updateElement: function(selectedElement) {
    if (this.options.updateElement) {
      this.options.updateElement(selectedElement);
      return;
    }
    var value = '';
    if (this.options.select) {
      var nodes = $(selectedElement).select('.' + this.options.select) || [];
      if(nodes.length>0) value = Element.collectTextNodes(nodes[0], this.options.select);
    } else
      value = Element.collectTextNodesIgnoreClass(selectedElement, 'informal');

    var bounds = this.getTokenBounds();
    if (bounds[0] != -1) {
      var newValue = this.element.value.substr(0, bounds[0]);
      var whitespace = this.element.value.substr(bounds[0]).match(/^\s+/);
      if (whitespace)
        newValue += whitespace[0];
      this.element.value = newValue + value + this.element.value.substr(bounds[1]);
    } else {
      this.element.value = value;
    }
    this.oldElementValue = this.element.value;
    this.element.focus();

    if (this.options.afterUpdateElement)
      this.options.afterUpdateElement(this.element, selectedElement);
  },

  updateChoices: function(choices) {
    if(!this.changed && this.hasFocus) {
      this.update.innerHTML = choices;
      Element.cleanWhitespace(this.update);
      Element.cleanWhitespace(this.update.down());

      if(this.update.firstChild && this.update.down().childNodes) {
        this.entryCount =
          this.update.down().childNodes.length;
        for (var i = 0; i < this.entryCount; i++) {
          var entry = this.getEntry(i);
          entry.autocompleteIndex = i;
          this.addObservers(entry);
        }
      } else {
        this.entryCount = 0;
      }

      this.stopIndicator();
      this.index = 0;

      if(this.entryCount==1 && this.options.autoSelect) {
        this.selectEntry();
        this.hide();
      } else {
        this.render();
      }
    }
  },

  addObservers: function(element) {
    Event.observe(element, "mouseover", this.onHover.bindAsEventListener(this));
    Event.observe(element, "click", this.onClick.bindAsEventListener(this));
  },

  onObserverEvent: function() {
    this.changed = false;
    this.tokenBounds = null;
    if(this.getToken().length>=this.options.minChars) {
      this.getUpdatedChoices();
    } else {
      this.active = false;
      this.hide();
    }
    this.oldElementValue = this.element.value;
  },

  getToken: function() {
    var bounds = this.getTokenBounds();
    return this.element.value.substring(bounds[0], bounds[1]).strip();
  },

  getTokenBounds: function() {
    if (null != this.tokenBounds) return this.tokenBounds;
    var value = this.element.value;
    if (value.strip().empty()) return [-1, 0];
    var diff = arguments.callee.getFirstDifferencePos(value, this.oldElementValue);
    var offset = (diff == this.oldElementValue.length ? 1 : 0);
    var prevTokenPos = -1, nextTokenPos = value.length;
    var tp;
    for (var index = 0, l = this.options.tokens.length; index < l; ++index) {
      tp = value.lastIndexOf(this.options.tokens[index], diff + offset - 1);
      if (tp > prevTokenPos) prevTokenPos = tp;
      tp = value.indexOf(this.options.tokens[index], diff + offset);
      if (-1 != tp && tp < nextTokenPos) nextTokenPos = tp;
    }
    return (this.tokenBounds = [prevTokenPos + 1, nextTokenPos]);
  }
});

Autocompleter.Base.prototype.getTokenBounds.getFirstDifferencePos = function(newS, oldS) {
  var boundary = Math.min(newS.length, oldS.length);
  for (var index = 0; index < boundary; ++index)
    if (newS[index] != oldS[index])
      return index;
  return boundary;
};

Ajax.Autocompleter = Class.create(Autocompleter.Base, {
  initialize: function(element, update, url, options) {
    this.baseInitialize(element, update, options);
    this.options.asynchronous  = true;
    this.options.onComplete    = this.onComplete.bind(this);
    this.options.defaultParams = this.options.parameters || null;
    this.url                   = url;
  },

  getUpdatedChoices: function() {
    this.startIndicator();

    var entry = encodeURIComponent(this.options.paramName) + '=' +
      encodeURIComponent(this.getToken());

    this.options.parameters = this.options.callback ?
      this.options.callback(this.element, entry) : entry;

    if(this.options.defaultParams)
      this.options.parameters += '&' + this.options.defaultParams;

    new Ajax.Request(this.url, this.options);
  },

  onComplete: function(request) {
    this.updateChoices(request.responseText);
  }
});

// The local array autocompleter. Used when you'd prefer to
// inject an array of autocompletion options into the page, rather
// than sending out Ajax queries, which can be quite slow sometimes.
//
// The constructor takes four parameters. The first two are, as usual,
// the id of the monitored textbox, and id of the autocompletion menu.
// The third is the array you want to autocomplete from, and the fourth
// is the options block.
//
// Extra local autocompletion options:
// - choices - How many autocompletion choices to offer
//
// - partialSearch - If false, the autocompleter will match entered
//                    text only at the beginning of strings in the
//                    autocomplete array. Defaults to true, which will
//                    match text at the beginning of any *word* in the
//                    strings in the autocomplete array. If you want to
//                    search anywhere in the string, additionally set
//                    the option fullSearch to true (default: off).
//
// - fullSsearch - Search anywhere in autocomplete array strings.
//
// - partialChars - How many characters to enter before triggering
//                   a partial match (unlike minChars, which defines
//                   how many characters are required to do any match
//                   at all). Defaults to 2.
//
// - ignoreCase - Whether to ignore case when autocompleting.
//                 Defaults to true.
//
// It's possible to pass in a custom function as the 'selector'
// option, if you prefer to write your own autocompletion logic.
// In that case, the other options above will not apply unless
// you support them.

Autocompleter.Local = Class.create(Autocompleter.Base, {
  initialize: function(element, update, array, options) {
    this.baseInitialize(element, update, options);
    this.options.array = array;
  },

  getUpdatedChoices: function() {
    this.updateChoices(this.options.selector(this));
  },

  setOptions: function(options) {
    this.options = Object.extend({
      choices: 10,
      partialSearch: true,
      partialChars: 2,
      ignoreCase: true,
      fullSearch: false,
      selector: function(instance) {
        var ret       = []; // Beginning matches
        var partial   = []; // Inside matches
        var entry     = instance.getToken();
        var count     = 0;

        for (var i = 0; i < instance.options.array.length &&
          ret.length < instance.options.choices ; i++) {

          var elem = instance.options.array[i];
          var foundPos = instance.options.ignoreCase ?
            elem.toLowerCase().indexOf(entry.toLowerCase()) :
            elem.indexOf(entry);

          while (foundPos != -1) {
            if (foundPos == 0 && elem.length != entry.length) {
              ret.push("<li><strong>" + elem.substr(0, entry.length) + "</strong>" +
                elem.substr(entry.length) + "</li>");
              break;
            } else if (entry.length >= instance.options.partialChars &&
              instance.options.partialSearch && foundPos != -1) {
              if (instance.options.fullSearch || /\s/.test(elem.substr(foundPos-1,1))) {
                partial.push("<li>" + elem.substr(0, foundPos) + "<strong>" +
                  elem.substr(foundPos, entry.length) + "</strong>" + elem.substr(
                  foundPos + entry.length) + "</li>");
                break;
              }
            }

            foundPos = instance.options.ignoreCase ?
              elem.toLowerCase().indexOf(entry.toLowerCase(), foundPos + 1) :
              elem.indexOf(entry, foundPos + 1);

          }
        }
        if (partial.length)
          ret = ret.concat(partial.slice(0, instance.options.choices - ret.length));
        return "<ul>" + ret.join('') + "</ul>";
      }
    }, options || { });
  }
});

// AJAX in-place editor and collection editor
// Full rewrite by Christophe Porteneuve <tdd@tddsworld.com> (April 2007).

// Use this if you notice weird scrolling problems on some browsers,
// the DOM might be a bit confused when this gets called so do this
// waits 1 ms (with setTimeout) until it does the activation
Field.scrollFreeActivate = function(field) {
  setTimeout(function() {
    Field.activate(field);
  }, 1);
};

Ajax.InPlaceEditor = Class.create({
  initialize: function(element, url, options) {
    this.url = url;
    this.element = element = $(element);
    this.prepareOptions();
    this._controls = { };
    arguments.callee.dealWithDeprecatedOptions(options); // DEPRECATION LAYER!!!
    Object.extend(this.options, options || { });
    if (!this.options.formId && this.element.id) {
      this.options.formId = this.element.id + '-inplaceeditor';
      if ($(this.options.formId))
        this.options.formId = '';
    }
    if (this.options.externalControl)
      this.options.externalControl = $(this.options.externalControl);
    if (!this.options.externalControl)
      this.options.externalControlOnly = false;
    this._originalBackground = this.element.getStyle('background-color') || 'transparent';
    this.element.title = this.options.clickToEditText;
    this._boundCancelHandler = this.handleFormCancellation.bind(this);
    this._boundComplete = (this.options.onComplete || Prototype.emptyFunction).bind(this);
    this._boundFailureHandler = this.handleAJAXFailure.bind(this);
    this._boundSubmitHandler = this.handleFormSubmission.bind(this);
    this._boundWrapperHandler = this.wrapUp.bind(this);
    this.registerListeners();
  },
  checkForEscapeOrReturn: function(e) {
    if (!this._editing || e.ctrlKey || e.altKey || e.shiftKey) return;
    if (Event.KEY_ESC == e.keyCode)
      this.handleFormCancellation(e);
    else if (Event.KEY_RETURN == e.keyCode)
      this.handleFormSubmission(e);
  },
  createControl: function(mode, handler, extraClasses) {
    var control = this.options[mode + 'Control'];
    var text = this.options[mode + 'Text'];
    if ('button' == control) {
      var btn = document.createElement('input');
      btn.type = 'submit';
      btn.value = text;
      btn.className = 'editor_' + mode + '_button';
      if ('cancel' == mode)
        btn.onclick = this._boundCancelHandler;
      this._form.appendChild(btn);
      this._controls[mode] = btn;
    } else if ('link' == control) {
      var link = document.createElement('a');
      link.href = '#';
      link.appendChild(document.createTextNode(text));
      link.onclick = 'cancel' == mode ? this._boundCancelHandler : this._boundSubmitHandler;
      link.className = 'editor_' + mode + '_link';
      if (extraClasses)
        link.className += ' ' + extraClasses;
      this._form.appendChild(link);
      this._controls[mode] = link;
    }
  },
  createEditField: function() {
    var text = (this.options.loadTextURL ? this.options.loadingText : this.getText());
    var fld;
    if (1 >= this.options.rows && !/\r|\n/.test(this.getText())) {
      fld = document.createElement('input');
      fld.type = 'text';
      var size = this.options.size || this.options.cols || 0;
      if (0 < size) fld.size = size;
    } else {
      fld = document.createElement('textarea');
      fld.rows = (1 >= this.options.rows ? this.options.autoRows : this.options.rows);
      fld.cols = this.options.cols || 40;
    }
    fld.name = this.options.paramName;
    fld.value = text; // No HTML breaks conversion anymore
    fld.className = 'editor_field';
    if (this.options.submitOnBlur)
      fld.onblur = this._boundSubmitHandler;
    this._controls.editor = fld;
    if (this.options.loadTextURL)
      this.loadExternalText();
    this._form.appendChild(this._controls.editor);
  },
  createForm: function() {
    var ipe = this;
    function addText(mode, condition) {
      var text = ipe.options['text' + mode + 'Controls'];
      if (!text || condition === false) return;
      ipe._form.appendChild(document.createTextNode(text));
    };
    this._form = $(document.createElement('form'));
    this._form.id = this.options.formId;
    this._form.addClassName(this.options.formClassName);
    this._form.onsubmit = this._boundSubmitHandler;
    this.createEditField();
    if ('textarea' == this._controls.editor.tagName.toLowerCase())
      this._form.appendChild(document.createElement('br'));
    if (this.options.onFormCustomization)
      this.options.onFormCustomization(this, this._form);
    addText('Before', this.options.okControl || this.options.cancelControl);
    this.createControl('ok', this._boundSubmitHandler);
    addText('Between', this.options.okControl && this.options.cancelControl);
    this.createControl('cancel', this._boundCancelHandler, 'editor_cancel');
    addText('After', this.options.okControl || this.options.cancelControl);
  },
  destroy: function() {
    if (this._oldInnerHTML)
      this.element.innerHTML = this._oldInnerHTML;
    this.leaveEditMode();
    this.unregisterListeners();
  },
  enterEditMode: function(e) {
    if (this._saving || this._editing) return;
    this._editing = true;
    this.triggerCallback('onEnterEditMode');
    if (this.options.externalControl)
      this.options.externalControl.hide();
    this.element.hide();
    this.createForm();
    this.element.parentNode.insertBefore(this._form, this.element);
    if (!this.options.loadTextURL)
      this.postProcessEditField();
    if (e) Event.stop(e);
  },
  enterHover: function(e) {
    if (this.options.hoverClassName)
      this.element.addClassName(this.options.hoverClassName);
    if (this._saving) return;
    this.triggerCallback('onEnterHover');
  },
  getText: function() {
    return this.element.innerHTML.unescapeHTML();
  },
  handleAJAXFailure: function(transport) {
    this.triggerCallback('onFailure', transport);
    if (this._oldInnerHTML) {
      this.element.innerHTML = this._oldInnerHTML;
      this._oldInnerHTML = null;
    }
  },
  handleFormCancellation: function(e) {
    this.wrapUp();
    if (e) Event.stop(e);
  },
  handleFormSubmission: function(e) {
    var form = this._form;
    var value = $F(this._controls.editor);
    this.prepareSubmission();
    var params = this.options.callback(form, value) || '';
    if (Object.isString(params))
      params = params.toQueryParams();
    params.editorId = this.element.id;
    if (this.options.htmlResponse) {
      var options = Object.extend({ evalScripts: true }, this.options.ajaxOptions);
      Object.extend(options, {
        parameters: params,
        onComplete: this._boundWrapperHandler,
        onFailure: this._boundFailureHandler
      });
      new Ajax.Updater({ success: this.element }, this.url, options);
    } else {
      var options = Object.extend({ method: 'get' }, this.options.ajaxOptions);
      Object.extend(options, {
        parameters: params,
        onComplete: this._boundWrapperHandler,
        onFailure: this._boundFailureHandler
      });
      new Ajax.Request(this.url, options);
    }
    if (e) Event.stop(e);
  },
  leaveEditMode: function() {
    this.element.removeClassName(this.options.savingClassName);
    this.removeForm();
    this.leaveHover();
    this.element.style.backgroundColor = this._originalBackground;
    this.element.show();
    if (this.options.externalControl)
      this.options.externalControl.show();
    this._saving = false;
    this._editing = false;
    this._oldInnerHTML = null;
    this.triggerCallback('onLeaveEditMode');
  },
  leaveHover: function(e) {
    if (this.options.hoverClassName)
      this.element.removeClassName(this.options.hoverClassName);
    if (this._saving) return;
    this.triggerCallback('onLeaveHover');
  },
  loadExternalText: function() {
    this._form.addClassName(this.options.loadingClassName);
    this._controls.editor.disabled = true;
    var options = Object.extend({ method: 'get' }, this.options.ajaxOptions);
    Object.extend(options, {
      parameters: 'editorId=' + encodeURIComponent(this.element.id),
      onComplete: Prototype.emptyFunction,
      onSuccess: function(transport) {
        this._form.removeClassName(this.options.loadingClassName);
        var text = transport.responseText;
        if (this.options.stripLoadedTextTags)
          text = text.stripTags();
        this._controls.editor.value = text;
        this._controls.editor.disabled = false;
        this.postProcessEditField();
      }.bind(this),
      onFailure: this._boundFailureHandler
    });
    new Ajax.Request(this.options.loadTextURL, options);
  },
  postProcessEditField: function() {
    var fpc = this.options.fieldPostCreation;
    if (fpc)
      $(this._controls.editor)['focus' == fpc ? 'focus' : 'activate']();
  },
  prepareOptions: function() {
    this.options = Object.clone(Ajax.InPlaceEditor.DefaultOptions);
    Object.extend(this.options, Ajax.InPlaceEditor.DefaultCallbacks);
    [this._extraDefaultOptions].flatten().compact().each(function(defs) {
      Object.extend(this.options, defs);
    }.bind(this));
  },
  prepareSubmission: function() {
    this._saving = true;
    this.removeForm();
    this.leaveHover();
    this.showSaving();
  },
  registerListeners: function() {
    this._listeners = { };
    var listener;
    $H(Ajax.InPlaceEditor.Listeners).each(function(pair) {
      listener = this[pair.value].bind(this);
      this._listeners[pair.key] = listener;
      if (!this.options.externalControlOnly)
        this.element.observe(pair.key, listener);
      if (this.options.externalControl)
        this.options.externalControl.observe(pair.key, listener);
    }.bind(this));
  },
  removeForm: function() {
    if (!this._form) return;
    this._form.remove();
    this._form = null;
    this._controls = { };
  },
  showSaving: function() {
    this._oldInnerHTML = this.element.innerHTML;
    this.element.innerHTML = this.options.savingText;
    this.element.addClassName(this.options.savingClassName);
    this.element.style.backgroundColor = this._originalBackground;
    this.element.show();
  },
  triggerCallback: function(cbName, arg) {
    if ('function' == typeof this.options[cbName]) {
      this.options[cbName](this, arg);
    }
  },
  unregisterListeners: function() {
    $H(this._listeners).each(function(pair) {
      if (!this.options.externalControlOnly)
        this.element.stopObserving(pair.key, pair.value);
      if (this.options.externalControl)
        this.options.externalControl.stopObserving(pair.key, pair.value);
    }.bind(this));
  },
  wrapUp: function(transport) {
    this.leaveEditMode();
    // Can't use triggerCallback due to backward compatibility: requires
    // binding + direct element
    this._boundComplete(transport, this.element);
  }
});

Object.extend(Ajax.InPlaceEditor.prototype, {
  dispose: Ajax.InPlaceEditor.prototype.destroy
});

Ajax.InPlaceCollectionEditor = Class.create(Ajax.InPlaceEditor, {
  initialize: function($super, element, url, options) {
    this._extraDefaultOptions = Ajax.InPlaceCollectionEditor.DefaultOptions;
    $super(element, url, options);
  },

  createEditField: function() {
    var list = document.createElement('select');
    list.name = this.options.paramName;
    list.size = 1;
    this._controls.editor = list;
    this._collection = this.options.collection || [];
    if (this.options.loadCollectionURL)
      this.loadCollection();
    else
      this.checkForExternalText();
    this._form.appendChild(this._controls.editor);
  },

  loadCollection: function() {
    this._form.addClassName(this.options.loadingClassName);
    this.showLoadingText(this.options.loadingCollectionText);
    var options = Object.extend({ method: 'get' }, this.options.ajaxOptions);
    Object.extend(options, {
      parameters: 'editorId=' + encodeURIComponent(this.element.id),
      onComplete: Prototype.emptyFunction,
      onSuccess: function(transport) {
        var js = transport.responseText.strip();
        if (!/^\[.*\]$/.test(js)) // TODO: improve sanity check
          throw('Server returned an invalid collection representation.');
        this._collection = eval(js);
        this.checkForExternalText();
      }.bind(this),
      onFailure: this.onFailure
    });
    new Ajax.Request(this.options.loadCollectionURL, options);
  },

  showLoadingText: function(text) {
    this._controls.editor.disabled = true;
    var tempOption = this._controls.editor.firstChild;
    if (!tempOption) {
      tempOption = document.createElement('option');
      tempOption.value = '';
      this._controls.editor.appendChild(tempOption);
      tempOption.selected = true;
    }
    tempOption.update((text || '').stripScripts().stripTags());
  },

  checkForExternalText: function() {
    this._text = this.getText();
    if (this.options.loadTextURL)
      this.loadExternalText();
    else
      this.buildOptionList();
  },

  loadExternalText: function() {
    this.showLoadingText(this.options.loadingText);
    var options = Object.extend({ method: 'get' }, this.options.ajaxOptions);
    Object.extend(options, {
      parameters: 'editorId=' + encodeURIComponent(this.element.id),
      onComplete: Prototype.emptyFunction,
      onSuccess: function(transport) {
        this._text = transport.responseText.strip();
        this.buildOptionList();
      }.bind(this),
      onFailure: this.onFailure
    });
    new Ajax.Request(this.options.loadTextURL, options);
  },

  buildOptionList: function() {
    this._form.removeClassName(this.options.loadingClassName);
    this._collection = this._collection.map(function(entry) {
      return 2 === entry.length ? entry : [entry, entry].flatten();
    });
    var marker = ('value' in this.options) ? this.options.value : this._text;
    var textFound = this._collection.any(function(entry) {
      return entry[0] == marker;
    }.bind(this));
    this._controls.editor.update('');
    var option;
    this._collection.each(function(entry, index) {
      option = document.createElement('option');
      option.value = entry[0];
      option.selected = textFound ? entry[0] == marker : 0 == index;
      option.appendChild(document.createTextNode(entry[1]));
      this._controls.editor.appendChild(option);
    }.bind(this));
    this._controls.editor.disabled = false;
    Field.scrollFreeActivate(this._controls.editor);
  }
});

//**** DEPRECATION LAYER FOR InPlace[Collection]Editor! ****
//**** This only  exists for a while,  in order to  let ****
//**** users adapt to  the new API.  Read up on the new ****
//**** API and convert your code to it ASAP!            ****

Ajax.InPlaceEditor.prototype.initialize.dealWithDeprecatedOptions = function(options) {
  if (!options) return;
  function fallback(name, expr) {
    if (name in options || expr === undefined) return;
    options[name] = expr;
  };
  fallback('cancelControl', (options.cancelLink ? 'link' : (options.cancelButton ? 'button' :
    options.cancelLink == options.cancelButton == false ? false : undefined)));
  fallback('okControl', (options.okLink ? 'link' : (options.okButton ? 'button' :
    options.okLink == options.okButton == false ? false : undefined)));
  fallback('highlightColor', options.highlightcolor);
  fallback('highlightEndColor', options.highlightendcolor);
};

Object.extend(Ajax.InPlaceEditor, {
  DefaultOptions: {
    ajaxOptions: { },
    autoRows: 3,                                // Use when multi-line w/ rows == 1
    cancelControl: 'link',                      // 'link'|'button'|false
    cancelText: 'cancel',
    clickToEditText: 'Click to edit',
    externalControl: null,                      // id|elt
    externalControlOnly: false,
    fieldPostCreation: 'activate',              // 'activate'|'focus'|false
    formClassName: 'inplaceeditor-form',
    formId: null,                               // id|elt
    highlightColor: '#ffff99',
    highlightEndColor: '#ffffff',
    hoverClassName: '',
    htmlResponse: true,
    loadingClassName: 'inplaceeditor-loading',
    loadingText: 'Loading...',
    okControl: 'button',                        // 'link'|'button'|false
    okText: 'ok',
    paramName: 'value',
    rows: 1,                                    // If 1 and multi-line, uses autoRows
    savingClassName: 'inplaceeditor-saving',
    savingText: 'Saving...',
    size: 0,
    stripLoadedTextTags: false,
    submitOnBlur: false,
    textAfterControls: '',
    textBeforeControls: '',
    textBetweenControls: ''
  },
  DefaultCallbacks: {
    callback: function(form) {
      return Form.serialize(form);
    },
    onComplete: function(transport, element) {
      // For backward compatibility, this one is bound to the IPE, and passes
      // the element directly.  It was too often customized, so we don't break it.
      new Effect.Highlight(element, {
        startcolor: this.options.highlightColor, keepBackgroundImage: true });
    },
    onEnterEditMode: null,
    onEnterHover: function(ipe) {
      ipe.element.style.backgroundColor = ipe.options.highlightColor;
      if (ipe._effect)
        ipe._effect.cancel();
    },
    onFailure: function(transport, ipe) {
      alert('Error communication with the server: ' + transport.responseText.stripTags());
    },
    onFormCustomization: null, // Takes the IPE and its generated form, after editor, before controls.
    onLeaveEditMode: null,
    onLeaveHover: function(ipe) {
      ipe._effect = new Effect.Highlight(ipe.element, {
        startcolor: ipe.options.highlightColor, endcolor: ipe.options.highlightEndColor,
        restorecolor: ipe._originalBackground, keepBackgroundImage: true
      });
    }
  },
  Listeners: {
    click: 'enterEditMode',
    keydown: 'checkForEscapeOrReturn',
    mouseover: 'enterHover',
    mouseout: 'leaveHover'
  }
});

Ajax.InPlaceCollectionEditor.DefaultOptions = {
  loadingCollectionText: 'Loading options...'
};

// Delayed observer, like Form.Element.Observer,
// but waits for delay after last key input
// Ideal for live-search fields

Form.Element.DelayedObserver = Class.create({
  initialize: function(element, delay, callback) {
    this.delay     = delay || 0.5;
    this.element   = $(element);
    this.callback  = callback;
    this.timer     = null;
    this.lastValue = $F(this.element);
    Event.observe(this.element,'keyup',this.delayedListener.bindAsEventListener(this));
  },
  delayedListener: function(event) {
    if(this.lastValue == $F(this.element)) return;
    if(this.timer) clearTimeout(this.timer);
    this.timer = setTimeout(this.onTimerEvent.bind(this), this.delay * 1000);
    this.lastValue = $F(this.element);
  },
  onTimerEvent: function() {
    this.timer = null;
    this.callback(this.element, $F(this.element));
  }
});
var process = process || {env: {NODE_ENV: "development"}};
// script.aculo.us dragdrop.js v1.8.3, Thu Oct 08 11:23:33 +0200 2009

// Copyright (c) 2005-2009 Thomas Fuchs (http://script.aculo.us, http://mir.aculo.us)
//
// script.aculo.us is freely distributable under the terms of an MIT-style license.
// For details, see the script.aculo.us web site: http://script.aculo.us/

if(Object.isUndefined(Effect))
  throw("dragdrop.js requires including script.aculo.us' effects.js library");

var Droppables = {
  drops: [],

  remove: function(element) {
    this.drops = this.drops.reject(function(d) { return d.element==$(element) });
  },

  add: function(element) {
    element = $(element);
    var options = Object.extend({
      greedy:     true,
      hoverclass: null,
      tree:       false
    }, arguments[1] || { });

    // cache containers
    if(options.containment) {
      options._containers = [];
      var containment = options.containment;
      if(Object.isArray(containment)) {
        containment.each( function(c) { options._containers.push($(c)) });
      } else {
        options._containers.push($(containment));
      }
    }

    if(options.accept) options.accept = [options.accept].flatten();

    Element.makePositioned(element); // fix IE
    options.element = element;

    this.drops.push(options);
  },

  findDeepestChild: function(drops) {
    deepest = drops[0];

    for (i = 1; i < drops.length; ++i)
      if (Element.isParent(drops[i].element, deepest.element))
        deepest = drops[i];

    return deepest;
  },

  isContained: function(element, drop) {
    var containmentNode;
    if(drop.tree) {
      containmentNode = element.treeNode;
    } else {
      containmentNode = element.parentNode;
    }
    return drop._containers.detect(function(c) { return containmentNode == c });
  },

  isAffected: function(point, element, drop) {
    return (
      (drop.element!=element) &&
      ((!drop._containers) ||
        this.isContained(element, drop)) &&
      ((!drop.accept) ||
        (Element.classNames(element).detect(
          function(v) { return drop.accept.include(v) } ) )) &&
      Position.within(drop.element, point[0], point[1]) );
  },

  deactivate: function(drop) {
    if(drop.hoverclass)
      Element.removeClassName(drop.element, drop.hoverclass);
    this.last_active = null;
  },

  activate: function(drop) {
    if(drop.hoverclass)
      Element.addClassName(drop.element, drop.hoverclass);
    this.last_active = drop;
  },

  show: function(point, element) {
    if(!this.drops.length) return;
    var drop, affected = [];

    this.drops.each( function(drop) {
      if(Droppables.isAffected(point, element, drop))
        affected.push(drop);
    });

    if(affected.length>0)
      drop = Droppables.findDeepestChild(affected);

    if(this.last_active && this.last_active != drop) this.deactivate(this.last_active);
    if (drop) {
      Position.within(drop.element, point[0], point[1]);
      if(drop.onHover)
        drop.onHover(element, drop.element, Position.overlap(drop.overlap, drop.element));

      if (drop != this.last_active) Droppables.activate(drop);
    }
  },

  fire: function(event, element) {
    if(!this.last_active) return;
    Position.prepare();

    if (this.isAffected([Event.pointerX(event), Event.pointerY(event)], element, this.last_active))
      if (this.last_active.onDrop) {
        this.last_active.onDrop(element, this.last_active.element, event);
        return true;
      }
  },

  reset: function() {
    if(this.last_active)
      this.deactivate(this.last_active);
  }
};

var Draggables = {
  drags: [],
  observers: [],

  register: function(draggable) {
    if(this.drags.length == 0) {
      this.eventMouseUp   = this.endDrag.bindAsEventListener(this);
      this.eventMouseMove = this.updateDrag.bindAsEventListener(this);
      this.eventKeypress  = this.keyPress.bindAsEventListener(this);

      Event.observe(document, "mouseup", this.eventMouseUp);
      Event.observe(document, "mousemove", this.eventMouseMove);
      Event.observe(document, "keypress", this.eventKeypress);
    }
    this.drags.push(draggable);
  },

  unregister: function(draggable) {
    this.drags = this.drags.reject(function(d) { return d==draggable });
    if(this.drags.length == 0) {
      Event.stopObserving(document, "mouseup", this.eventMouseUp);
      Event.stopObserving(document, "mousemove", this.eventMouseMove);
      Event.stopObserving(document, "keypress", this.eventKeypress);
    }
  },

  activate: function(draggable) {
    if(draggable.options.delay) {
      this._timeout = setTimeout(function() {
        Draggables._timeout = null;
        window.focus();
        Draggables.activeDraggable = draggable;
      }.bind(this), draggable.options.delay);
    } else {
      window.focus(); // allows keypress events if window isn't currently focused, fails for Safari
      this.activeDraggable = draggable;
    }
  },

  deactivate: function() {
    this.activeDraggable = null;
  },

  updateDrag: function(event) {
    if(!this.activeDraggable) return;
    var pointer = [Event.pointerX(event), Event.pointerY(event)];
    // Mozilla-based browsers fire successive mousemove events with
    // the same coordinates, prevent needless redrawing (moz bug?)
    if(this._lastPointer && (this._lastPointer.inspect() == pointer.inspect())) return;
    this._lastPointer = pointer;

    this.activeDraggable.updateDrag(event, pointer);
  },

  endDrag: function(event) {
    if(this._timeout) {
      clearTimeout(this._timeout);
      this._timeout = null;
    }
    if(!this.activeDraggable) return;
    this._lastPointer = null;
    this.activeDraggable.endDrag(event);
    this.activeDraggable = null;
  },

  keyPress: function(event) {
    if(this.activeDraggable)
      this.activeDraggable.keyPress(event);
  },

  addObserver: function(observer) {
    this.observers.push(observer);
    this._cacheObserverCallbacks();
  },

  removeObserver: function(element) {  // element instead of observer fixes mem leaks
    this.observers = this.observers.reject( function(o) { return o.element==element });
    this._cacheObserverCallbacks();
  },

  notify: function(eventName, draggable, event) {  // 'onStart', 'onEnd', 'onDrag'
    if(this[eventName+'Count'] > 0)
      this.observers.each( function(o) {
        if(o[eventName]) o[eventName](eventName, draggable, event);
      });
    if(draggable.options[eventName]) draggable.options[eventName](draggable, event);
  },

  _cacheObserverCallbacks: function() {
    ['onStart','onEnd','onDrag'].each( function(eventName) {
      Draggables[eventName+'Count'] = Draggables.observers.select(
        function(o) { return o[eventName]; }
      ).length;
    });
  }
};

/*--------------------------------------------------------------------------*/

var Draggable = Class.create({
  initialize: function(element) {
    var defaults = {
      handle: false,
      reverteffect: function(element, top_offset, left_offset) {
        var dur = Math.sqrt(Math.abs(top_offset^2)+Math.abs(left_offset^2))*0.02;
        new Effect.Move(element, { x: -left_offset, y: -top_offset, duration: dur,
          queue: {scope:'_draggable', position:'end'}
        });
      },
      endeffect: function(element) {
        var toOpacity = Object.isNumber(element._opacity) ? element._opacity : 1.0;
        new Effect.Opacity(element, {duration:0.2, from:0.7, to:toOpacity,
          queue: {scope:'_draggable', position:'end'},
          afterFinish: function(){
            Draggable._dragging[element] = false
          }
        });
      },
      zindex: 1000,
      revert: false,
      quiet: false,
      scroll: false,
      scrollSensitivity: 20,
      scrollSpeed: 15,
      snap: false,  // false, or xy or [x,y] or function(x,y){ return [x,y] }
      delay: 0
    };

    if(!arguments[1] || Object.isUndefined(arguments[1].endeffect))
      Object.extend(defaults, {
        starteffect: function(element) {
          element._opacity = Element.getOpacity(element);
          Draggable._dragging[element] = true;
          new Effect.Opacity(element, {duration:0.2, from:element._opacity, to:0.7});
        }
      });

    var options = Object.extend(defaults, arguments[1] || { });

    this.element = $(element);

    if(options.handle && Object.isString(options.handle))
      this.handle = this.element.down('.'+options.handle, 0);

    if(!this.handle) this.handle = $(options.handle);
    if(!this.handle) this.handle = this.element;

    if(options.scroll && !options.scroll.scrollTo && !options.scroll.outerHTML) {
      options.scroll = $(options.scroll);
      this._isScrollChild = Element.childOf(this.element, options.scroll);
    }

    Element.makePositioned(this.element); // fix IE

    this.options  = options;
    this.dragging = false;

    this.eventMouseDown = this.initDrag.bindAsEventListener(this);
    Event.observe(this.handle, "mousedown", this.eventMouseDown);

    Draggables.register(this);
  },

  destroy: function() {
    Event.stopObserving(this.handle, "mousedown", this.eventMouseDown);
    Draggables.unregister(this);
  },

  currentDelta: function() {
    return([
      parseInt(Element.getStyle(this.element,'left') || '0'),
      parseInt(Element.getStyle(this.element,'top') || '0')]);
  },

  initDrag: function(event) {
    if(!Object.isUndefined(Draggable._dragging[this.element]) &&
      Draggable._dragging[this.element]) return;
    if(Event.isLeftClick(event)) {
      // abort on form elements, fixes a Firefox issue
      var src = Event.element(event);
      if((tag_name = src.tagName.toUpperCase()) && (
        tag_name=='INPUT' ||
        tag_name=='SELECT' ||
        tag_name=='OPTION' ||
        tag_name=='BUTTON' ||
        tag_name=='TEXTAREA')) return;

      var pointer = [Event.pointerX(event), Event.pointerY(event)];
      var pos     = this.element.cumulativeOffset();
      this.offset = [0,1].map( function(i) { return (pointer[i] - pos[i]) });

      Draggables.activate(this);
      Event.stop(event);
    }
  },

  startDrag: function(event) {
    this.dragging = true;
    if(!this.delta)
      this.delta = this.currentDelta();

    if(this.options.zindex) {
      this.originalZ = parseInt(Element.getStyle(this.element,'z-index') || 0);
      this.element.style.zIndex = this.options.zindex;
    }

    if(this.options.ghosting) {
      this._clone = this.element.cloneNode(true);
      this._originallyAbsolute = (this.element.getStyle('position') == 'absolute');
      if (!this._originallyAbsolute)
        Position.absolutize(this.element);
      this.element.parentNode.insertBefore(this._clone, this.element);
    }

    if(this.options.scroll) {
      if (this.options.scroll == window) {
        var where = this._getWindowScroll(this.options.scroll);
        this.originalScrollLeft = where.left;
        this.originalScrollTop = where.top;
      } else {
        this.originalScrollLeft = this.options.scroll.scrollLeft;
        this.originalScrollTop = this.options.scroll.scrollTop;
      }
    }

    Draggables.notify('onStart', this, event);

    if(this.options.starteffect) this.options.starteffect(this.element);
  },

  updateDrag: function(event, pointer) {
    if(!this.dragging) this.startDrag(event);

    if(!this.options.quiet){
      Position.prepare();
      Droppables.show(pointer, this.element);
    }

    Draggables.notify('onDrag', this, event);

    this.draw(pointer);
    if(this.options.change) this.options.change(this);

    if(this.options.scroll) {
      this.stopScrolling();

      var p;
      if (this.options.scroll == window) {
        with(this._getWindowScroll(this.options.scroll)) { p = [ left, top, left+width, top+height ]; }
      } else {
        p = Position.page(this.options.scroll);
        p[0] += this.options.scroll.scrollLeft + Position.deltaX;
        p[1] += this.options.scroll.scrollTop + Position.deltaY;
        p.push(p[0]+this.options.scroll.offsetWidth);
        p.push(p[1]+this.options.scroll.offsetHeight);
      }
      var speed = [0,0];
      if(pointer[0] < (p[0]+this.options.scrollSensitivity)) speed[0] = pointer[0]-(p[0]+this.options.scrollSensitivity);
      if(pointer[1] < (p[1]+this.options.scrollSensitivity)) speed[1] = pointer[1]-(p[1]+this.options.scrollSensitivity);
      if(pointer[0] > (p[2]-this.options.scrollSensitivity)) speed[0] = pointer[0]-(p[2]-this.options.scrollSensitivity);
      if(pointer[1] > (p[3]-this.options.scrollSensitivity)) speed[1] = pointer[1]-(p[3]-this.options.scrollSensitivity);
      this.startScrolling(speed);
    }

    // fix AppleWebKit rendering
    if(Prototype.Browser.WebKit) window.scrollBy(0,0);

    Event.stop(event);
  },

  finishDrag: function(event, success) {
    this.dragging = false;

    if(this.options.quiet){
      Position.prepare();
      var pointer = [Event.pointerX(event), Event.pointerY(event)];
      Droppables.show(pointer, this.element);
    }

    if(this.options.ghosting) {
      if (!this._originallyAbsolute)
        Position.relativize(this.element);
      delete this._originallyAbsolute;
      Element.remove(this._clone);
      this._clone = null;
    }

    var dropped = false;
    if(success) {
      dropped = Droppables.fire(event, this.element);
      if (!dropped) dropped = false;
    }
    if(dropped && this.options.onDropped) this.options.onDropped(this.element);
    Draggables.notify('onEnd', this, event);

    var revert = this.options.revert;
    if(revert && Object.isFunction(revert)) revert = revert(this.element);

    var d = this.currentDelta();
    if(revert && this.options.reverteffect) {
      if (dropped == 0 || revert != 'failure')
        this.options.reverteffect(this.element,
          d[1]-this.delta[1], d[0]-this.delta[0]);
    } else {
      this.delta = d;
    }

    if(this.options.zindex)
      this.element.style.zIndex = this.originalZ;

    if(this.options.endeffect)
      this.options.endeffect(this.element);

    Draggables.deactivate(this);
    Droppables.reset();
  },

  keyPress: function(event) {
    if(event.keyCode!=Event.KEY_ESC) return;
    this.finishDrag(event, false);
    Event.stop(event);
  },

  endDrag: function(event) {
    if(!this.dragging) return;
    this.stopScrolling();
    this.finishDrag(event, true);
    Event.stop(event);
  },

  draw: function(point) {
    var pos = this.element.cumulativeOffset();
    if(this.options.ghosting) {
      var r   = Position.realOffset(this.element);
      pos[0] += r[0] - Position.deltaX; pos[1] += r[1] - Position.deltaY;
    }

    var d = this.currentDelta();
    pos[0] -= d[0]; pos[1] -= d[1];

    if(this.options.scroll && (this.options.scroll != window && this._isScrollChild)) {
      pos[0] -= this.options.scroll.scrollLeft-this.originalScrollLeft;
      pos[1] -= this.options.scroll.scrollTop-this.originalScrollTop;
    }

    var p = [0,1].map(function(i){
      return (point[i]-pos[i]-this.offset[i])
    }.bind(this));

    if(this.options.snap) {
      if(Object.isFunction(this.options.snap)) {
        p = this.options.snap(p[0],p[1],this);
      } else {
      if(Object.isArray(this.options.snap)) {
        p = p.map( function(v, i) {
          return (v/this.options.snap[i]).round()*this.options.snap[i] }.bind(this));
      } else {
        p = p.map( function(v) {
          return (v/this.options.snap).round()*this.options.snap }.bind(this));
      }
    }}

    var style = this.element.style;
    if((!this.options.constraint) || (this.options.constraint=='horizontal'))
      style.left = p[0] + "px";
    if((!this.options.constraint) || (this.options.constraint=='vertical'))
      style.top  = p[1] + "px";

    if(style.visibility=="hidden") style.visibility = ""; // fix gecko rendering
  },

  stopScrolling: function() {
    if(this.scrollInterval) {
      clearInterval(this.scrollInterval);
      this.scrollInterval = null;
      Draggables._lastScrollPointer = null;
    }
  },

  startScrolling: function(speed) {
    if(!(speed[0] || speed[1])) return;
    this.scrollSpeed = [speed[0]*this.options.scrollSpeed,speed[1]*this.options.scrollSpeed];
    this.lastScrolled = new Date();
    this.scrollInterval = setInterval(this.scroll.bind(this), 10);
  },

  scroll: function() {
    var current = new Date();
    var delta = current - this.lastScrolled;
    this.lastScrolled = current;
    if(this.options.scroll == window) {
      with (this._getWindowScroll(this.options.scroll)) {
        if (this.scrollSpeed[0] || this.scrollSpeed[1]) {
          var d = delta / 1000;
          this.options.scroll.scrollTo( left + d*this.scrollSpeed[0], top + d*this.scrollSpeed[1] );
        }
      }
    } else {
      this.options.scroll.scrollLeft += this.scrollSpeed[0] * delta / 1000;
      this.options.scroll.scrollTop  += this.scrollSpeed[1] * delta / 1000;
    }

    Position.prepare();
    Droppables.show(Draggables._lastPointer, this.element);
    Draggables.notify('onDrag', this);
    if (this._isScrollChild) {
      Draggables._lastScrollPointer = Draggables._lastScrollPointer || $A(Draggables._lastPointer);
      Draggables._lastScrollPointer[0] += this.scrollSpeed[0] * delta / 1000;
      Draggables._lastScrollPointer[1] += this.scrollSpeed[1] * delta / 1000;
      if (Draggables._lastScrollPointer[0] < 0)
        Draggables._lastScrollPointer[0] = 0;
      if (Draggables._lastScrollPointer[1] < 0)
        Draggables._lastScrollPointer[1] = 0;
      this.draw(Draggables._lastScrollPointer);
    }

    if(this.options.change) this.options.change(this);
  },

  _getWindowScroll: function(w) {
    var T, L, W, H;
    with (w.document) {
      if (w.document.documentElement && documentElement.scrollTop) {
        T = documentElement.scrollTop;
        L = documentElement.scrollLeft;
      } else if (w.document.body) {
        T = body.scrollTop;
        L = body.scrollLeft;
      }
      if (w.innerWidth) {
        W = w.innerWidth;
        H = w.innerHeight;
      } else if (w.document.documentElement && documentElement.clientWidth) {
        W = documentElement.clientWidth;
        H = documentElement.clientHeight;
      } else {
        W = body.offsetWidth;
        H = body.offsetHeight;
      }
    }
    return { top: T, left: L, width: W, height: H };
  }
});

Draggable._dragging = { };

/*--------------------------------------------------------------------------*/

var SortableObserver = Class.create({
  initialize: function(element, observer) {
    this.element   = $(element);
    this.observer  = observer;
    this.lastValue = Sortable.serialize(this.element);
  },

  onStart: function() {
    this.lastValue = Sortable.serialize(this.element);
  },

  onEnd: function() {
    Sortable.unmark();
    if(this.lastValue != Sortable.serialize(this.element))
      this.observer(this.element)
  }
});

var Sortable = {
  SERIALIZE_RULE: /^[^_\-](?:[A-Za-z0-9\-\_]*)[_](.*)$/,

  sortables: { },

  _findRootElement: function(element) {
    while (element.tagName.toUpperCase() != "BODY") {
      if(element.id && Sortable.sortables[element.id]) return element;
      element = element.parentNode;
    }
  },

  options: function(element) {
    element = Sortable._findRootElement($(element));
    if(!element) return;
    return Sortable.sortables[element.id];
  },

  destroy: function(element){
    element = $(element);
    var s = Sortable.sortables[element.id];

    if(s) {
      Draggables.removeObserver(s.element);
      s.droppables.each(function(d){ Droppables.remove(d) });
      s.draggables.invoke('destroy');

      delete Sortable.sortables[s.element.id];
    }
  },

  create: function(element) {
    element = $(element);
    var options = Object.extend({
      element:     element,
      tag:         'li',       // assumes li children, override with tag: 'tagname'
      dropOnEmpty: false,
      tree:        false,
      treeTag:     'ul',
      overlap:     'vertical', // one of 'vertical', 'horizontal'
      constraint:  'vertical', // one of 'vertical', 'horizontal', false
      containment: element,    // also takes array of elements (or id's); or false
      handle:      false,      // or a CSS class
      only:        false,
      delay:       0,
      hoverclass:  null,
      ghosting:    false,
      quiet:       false,
      scroll:      false,
      scrollSensitivity: 20,
      scrollSpeed: 15,
      format:      this.SERIALIZE_RULE,

      // these take arrays of elements or ids and can be
      // used for better initialization performance
      elements:    false,
      handles:     false,

      onChange:    Prototype.emptyFunction,
      onUpdate:    Prototype.emptyFunction
    }, arguments[1] || { });

    // clear any old sortable with same element
    this.destroy(element);

    // build options for the draggables
    var options_for_draggable = {
      revert:      true,
      quiet:       options.quiet,
      scroll:      options.scroll,
      scrollSpeed: options.scrollSpeed,
      scrollSensitivity: options.scrollSensitivity,
      delay:       options.delay,
      ghosting:    options.ghosting,
      constraint:  options.constraint,
      handle:      options.handle };

    if(options.starteffect)
      options_for_draggable.starteffect = options.starteffect;

    if(options.reverteffect)
      options_for_draggable.reverteffect = options.reverteffect;
    else
      if(options.ghosting) options_for_draggable.reverteffect = function(element) {
        element.style.top  = 0;
        element.style.left = 0;
      };

    if(options.endeffect)
      options_for_draggable.endeffect = options.endeffect;

    if(options.zindex)
      options_for_draggable.zindex = options.zindex;

    // build options for the droppables
    var options_for_droppable = {
      overlap:     options.overlap,
      containment: options.containment,
      tree:        options.tree,
      hoverclass:  options.hoverclass,
      onHover:     Sortable.onHover
    };

    var options_for_tree = {
      onHover:      Sortable.onEmptyHover,
      overlap:      options.overlap,
      containment:  options.containment,
      hoverclass:   options.hoverclass
    };

    // fix for gecko engine
    Element.cleanWhitespace(element);

    options.draggables = [];
    options.droppables = [];

    // drop on empty handling
    if(options.dropOnEmpty || options.tree) {
      Droppables.add(element, options_for_tree);
      options.droppables.push(element);
    }

    (options.elements || this.findElements(element, options) || []).each( function(e,i) {
      var handle = options.handles ? $(options.handles[i]) :
        (options.handle ? $(e).select('.' + options.handle)[0] : e);
      options.draggables.push(
        new Draggable(e, Object.extend(options_for_draggable, { handle: handle })));
      Droppables.add(e, options_for_droppable);
      if(options.tree) e.treeNode = element;
      options.droppables.push(e);
    });

    if(options.tree) {
      (Sortable.findTreeElements(element, options) || []).each( function(e) {
        Droppables.add(e, options_for_tree);
        e.treeNode = element;
        options.droppables.push(e);
      });
    }

    // keep reference
    this.sortables[element.identify()] = options;

    // for onupdate
    Draggables.addObserver(new SortableObserver(element, options.onUpdate));

  },

  // return all suitable-for-sortable elements in a guaranteed order
  findElements: function(element, options) {
    return Element.findChildren(
      element, options.only, options.tree ? true : false, options.tag);
  },

  findTreeElements: function(element, options) {
    return Element.findChildren(
      element, options.only, options.tree ? true : false, options.treeTag);
  },

  onHover: function(element, dropon, overlap) {
    if(Element.isParent(dropon, element)) return;

    if(overlap > .33 && overlap < .66 && Sortable.options(dropon).tree) {
      return;
    } else if(overlap>0.5) {
      Sortable.mark(dropon, 'before');
      if(dropon.previousSibling != element) {
        var oldParentNode = element.parentNode;
        element.style.visibility = "hidden"; // fix gecko rendering
        dropon.parentNode.insertBefore(element, dropon);
        if(dropon.parentNode!=oldParentNode)
          Sortable.options(oldParentNode).onChange(element);
        Sortable.options(dropon.parentNode).onChange(element);
      }
    } else {
      Sortable.mark(dropon, 'after');
      var nextElement = dropon.nextSibling || null;
      if(nextElement != element) {
        var oldParentNode = element.parentNode;
        element.style.visibility = "hidden"; // fix gecko rendering
        dropon.parentNode.insertBefore(element, nextElement);
        if(dropon.parentNode!=oldParentNode)
          Sortable.options(oldParentNode).onChange(element);
        Sortable.options(dropon.parentNode).onChange(element);
      }
    }
  },

  onEmptyHover: function(element, dropon, overlap) {
    var oldParentNode = element.parentNode;
    var droponOptions = Sortable.options(dropon);

    if(!Element.isParent(dropon, element)) {
      var index;

      var children = Sortable.findElements(dropon, {tag: droponOptions.tag, only: droponOptions.only});
      var child = null;

      if(children) {
        var offset = Element.offsetSize(dropon, droponOptions.overlap) * (1.0 - overlap);

        for (index = 0; index < children.length; index += 1) {
          if (offset - Element.offsetSize (children[index], droponOptions.overlap) >= 0) {
            offset -= Element.offsetSize (children[index], droponOptions.overlap);
          } else if (offset - (Element.offsetSize (children[index], droponOptions.overlap) / 2) >= 0) {
            child = index + 1 < children.length ? children[index + 1] : null;
            break;
          } else {
            child = children[index];
            break;
          }
        }
      }

      dropon.insertBefore(element, child);

      Sortable.options(oldParentNode).onChange(element);
      droponOptions.onChange(element);
    }
  },

  unmark: function() {
    if(Sortable._marker) Sortable._marker.hide();
  },

  mark: function(dropon, position) {
    // mark on ghosting only
    var sortable = Sortable.options(dropon.parentNode);
    if(sortable && !sortable.ghosting) return;

    if(!Sortable._marker) {
      Sortable._marker =
        ($('dropmarker') || Element.extend(document.createElement('DIV'))).
          hide().addClassName('dropmarker').setStyle({position:'absolute'});
      document.getElementsByTagName("body").item(0).appendChild(Sortable._marker);
    }
    var offsets = dropon.cumulativeOffset();
    Sortable._marker.setStyle({left: offsets[0]+'px', top: offsets[1] + 'px'});

    if(position=='after')
      if(sortable.overlap == 'horizontal')
        Sortable._marker.setStyle({left: (offsets[0]+dropon.clientWidth) + 'px'});
      else
        Sortable._marker.setStyle({top: (offsets[1]+dropon.clientHeight) + 'px'});

    Sortable._marker.show();
  },

  _tree: function(element, options, parent) {
    var children = Sortable.findElements(element, options) || [];

    for (var i = 0; i < children.length; ++i) {
      var match = children[i].id.match(options.format);

      if (!match) continue;

      var child = {
        id: encodeURIComponent(match ? match[1] : null),
        element: element,
        parent: parent,
        children: [],
        position: parent.children.length,
        container: $(children[i]).down(options.treeTag)
      };

      /* Get the element containing the children and recurse over it */
      if (child.container)
        this._tree(child.container, options, child);

      parent.children.push (child);
    }

    return parent;
  },

  tree: function(element) {
    element = $(element);
    var sortableOptions = this.options(element);
    var options = Object.extend({
      tag: sortableOptions.tag,
      treeTag: sortableOptions.treeTag,
      only: sortableOptions.only,
      name: element.id,
      format: sortableOptions.format
    }, arguments[1] || { });

    var root = {
      id: null,
      parent: null,
      children: [],
      container: element,
      position: 0
    };

    return Sortable._tree(element, options, root);
  },

  /* Construct a [i] index for a particular node */
  _constructIndex: function(node) {
    var index = '';
    do {
      if (node.id) index = '[' + node.position + ']' + index;
    } while ((node = node.parent) != null);
    return index;
  },

  sequence: function(element) {
    element = $(element);
    var options = Object.extend(this.options(element), arguments[1] || { });

    return $(this.findElements(element, options) || []).map( function(item) {
      return item.id.match(options.format) ? item.id.match(options.format)[1] : '';
    });
  },

  setSequence: function(element, new_sequence) {
    element = $(element);
    var options = Object.extend(this.options(element), arguments[2] || { });

    var nodeMap = { };
    this.findElements(element, options).each( function(n) {
        if (n.id.match(options.format))
            nodeMap[n.id.match(options.format)[1]] = [n, n.parentNode];
        n.parentNode.removeChild(n);
    });

    new_sequence.each(function(ident) {
      var n = nodeMap[ident];
      if (n) {
        n[1].appendChild(n[0]);
        delete nodeMap[ident];
      }
    });
  },

  serialize: function(element) {
    element = $(element);
    var options = Object.extend(Sortable.options(element), arguments[1] || { });
    var name = encodeURIComponent(
      (arguments[1] && arguments[1].name) ? arguments[1].name : element.id);

    if (options.tree) {
      return Sortable.tree(element, arguments[1]).children.map( function (item) {
        return [name + Sortable._constructIndex(item) + "[id]=" +
                encodeURIComponent(item.id)].concat(item.children.map(arguments.callee));
      }).flatten().join('&');
    } else {
      return Sortable.sequence(element, arguments[1]).map( function(item) {
        return name + "[]=" + encodeURIComponent(item);
      }).join('&');
    }
  }
};

// Returns true if child is contained within element
Element.isParent = function(child, element) {
  if (!child.parentNode || child == element) return false;
  if (child.parentNode == element) return true;
  return Element.isParent(child.parentNode, element);
};

Element.findChildren = function(element, only, recursive, tagName) {
  if(!element.hasChildNodes()) return null;
  tagName = tagName.toUpperCase();
  if(only) only = [only].flatten();
  var elements = [];
  $A(element.childNodes).each( function(e) {
    if(e.tagName && e.tagName.toUpperCase()==tagName &&
      (!only || (Element.classNames(e).detect(function(v) { return only.include(v) }))))
        elements.push(e);
    if(recursive) {
      var grandchildren = Element.findChildren(e, only, recursive, tagName);
      if(grandchildren) elements.push(grandchildren);
    }
  });

  return (elements.length>0 ? elements.flatten() : []);
};

Element.offsetSize = function (element, type) {
  return element['offset' + ((type=='vertical' || type=='height') ? 'Height' : 'Width')];
};
var process = process || {env: {NODE_ENV: "development"}};
// script.aculo.us effects.js v1.8.3, Thu Oct 08 11:23:33 +0200 2009

// Copyright (c) 2005-2009 Thomas Fuchs (http://script.aculo.us, http://mir.aculo.us)
// Contributors:
//  Justin Palmer (http://encytemedia.com/)
//  Mark Pilgrim (http://diveintomark.org/)
//  Martin Bialasinki
//
// script.aculo.us is freely distributable under the terms of an MIT-style license.
// For details, see the script.aculo.us web site: http://script.aculo.us/

// converts rgb() and #xxx to #xxxxxx format,
// returns self (or first argument) if not convertable
String.prototype.parseColor = function() {
  var color = '#';
  if (this.slice(0,4) == 'rgb(') {
    var cols = this.slice(4,this.length-1).split(',');
    var i=0; do { color += parseInt(cols[i]).toColorPart() } while (++i<3);
  } else {
    if (this.slice(0,1) == '#') {
      if (this.length==4) for(var i=1;i<4;i++) color += (this.charAt(i) + this.charAt(i)).toLowerCase();
      if (this.length==7) color = this.toLowerCase();
    }
  }
  return (color.length==7 ? color : (arguments[0] || this));
};

/*--------------------------------------------------------------------------*/

Element.collectTextNodes = function(element) {
  return $A($(element).childNodes).collect( function(node) {
    return (node.nodeType==3 ? node.nodeValue :
      (node.hasChildNodes() ? Element.collectTextNodes(node) : ''));
  }).flatten().join('');
};

Element.collectTextNodesIgnoreClass = function(element, className) {
  return $A($(element).childNodes).collect( function(node) {
    return (node.nodeType==3 ? node.nodeValue :
      ((node.hasChildNodes() && !Element.hasClassName(node,className)) ?
        Element.collectTextNodesIgnoreClass(node, className) : ''));
  }).flatten().join('');
};

Element.setContentZoom = function(element, percent) {
  element = $(element);
  element.setStyle({fontSize: (percent/100) + 'em'});
  if (Prototype.Browser.WebKit) window.scrollBy(0,0);
  return element;
};

Element.getInlineOpacity = function(element){
  return $(element).style.opacity || '';
};

Element.forceRerendering = function(element) {
  try {
    element = $(element);
    var n = document.createTextNode(' ');
    element.appendChild(n);
    element.removeChild(n);
  } catch(e) { }
};

/*--------------------------------------------------------------------------*/

var Effect = {
  _elementDoesNotExistError: {
    name: 'ElementDoesNotExistError',
    message: 'The specified DOM element does not exist, but is required for this effect to operate'
  },
  Transitions: {
    linear: Prototype.K,
    sinoidal: function(pos) {
      return (-Math.cos(pos*Math.PI)/2) + .5;
    },
    reverse: function(pos) {
      return 1-pos;
    },
    flicker: function(pos) {
      var pos = ((-Math.cos(pos*Math.PI)/4) + .75) + Math.random()/4;
      return pos > 1 ? 1 : pos;
    },
    wobble: function(pos) {
      return (-Math.cos(pos*Math.PI*(9*pos))/2) + .5;
    },
    pulse: function(pos, pulses) {
      return (-Math.cos((pos*((pulses||5)-.5)*2)*Math.PI)/2) + .5;
    },
    spring: function(pos) {
      return 1 - (Math.cos(pos * 4.5 * Math.PI) * Math.exp(-pos * 6));
    },
    none: function(pos) {
      return 0;
    },
    full: function(pos) {
      return 1;
    }
  },
  DefaultOptions: {
    duration:   1.0,   // seconds
    fps:        100,   // 100= assume 66fps max.
    sync:       false, // true for combining
    from:       0.0,
    to:         1.0,
    delay:      0.0,
    queue:      'parallel'
  },
  tagifyText: function(element) {
    var tagifyStyle = 'position:relative';
    if (Prototype.Browser.IE) tagifyStyle += ';zoom:1';

    element = $(element);
    $A(element.childNodes).each( function(child) {
      if (child.nodeType==3) {
        child.nodeValue.toArray().each( function(character) {
          element.insertBefore(
            new Element('span', {style: tagifyStyle}).update(
              character == ' ' ? String.fromCharCode(160) : character),
              child);
        });
        Element.remove(child);
      }
    });
  },
  multiple: function(element, effect) {
    var elements;
    if (((typeof element == 'object') ||
        Object.isFunction(element)) &&
       (element.length))
      elements = element;
    else
      elements = $(element).childNodes;

    var options = Object.extend({
      speed: 0.1,
      delay: 0.0
    }, arguments[2] || { });
    var masterDelay = options.delay;

    $A(elements).each( function(element, index) {
      new effect(element, Object.extend(options, { delay: index * options.speed + masterDelay }));
    });
  },
  PAIRS: {
    'slide':  ['SlideDown','SlideUp'],
    'blind':  ['BlindDown','BlindUp'],
    'appear': ['Appear','Fade']
  },
  toggle: function(element, effect, options) {
    element = $(element);
    effect  = (effect || 'appear').toLowerCase();
    
    return Effect[ Effect.PAIRS[ effect ][ element.visible() ? 1 : 0 ] ](element, Object.extend({
      queue: { position:'end', scope:(element.id || 'global'), limit: 1 }
    }, options || {}));
  }
};

Effect.DefaultOptions.transition = Effect.Transitions.sinoidal;

/* ------------- core effects ------------- */

Effect.ScopedQueue = Class.create(Enumerable, {
  initialize: function() {
    this.effects  = [];
    this.interval = null;
  },
  _each: function(iterator) {
    this.effects._each(iterator);
  },
  add: function(effect) {
    var timestamp = new Date().getTime();

    var position = Object.isString(effect.options.queue) ?
      effect.options.queue : effect.options.queue.position;

    switch(position) {
      case 'front':
        // move unstarted effects after this effect
        this.effects.findAll(function(e){ return e.state=='idle' }).each( function(e) {
            e.startOn  += effect.finishOn;
            e.finishOn += effect.finishOn;
          });
        break;
      case 'with-last':
        timestamp = this.effects.pluck('startOn').max() || timestamp;
        break;
      case 'end':
        // start effect after last queued effect has finished
        timestamp = this.effects.pluck('finishOn').max() || timestamp;
        break;
    }

    effect.startOn  += timestamp;
    effect.finishOn += timestamp;

    if (!effect.options.queue.limit || (this.effects.length < effect.options.queue.limit))
      this.effects.push(effect);

    if (!this.interval)
      this.interval = setInterval(this.loop.bind(this), 15);
  },
  remove: function(effect) {
    this.effects = this.effects.reject(function(e) { return e==effect });
    if (this.effects.length == 0) {
      clearInterval(this.interval);
      this.interval = null;
    }
  },
  loop: function() {
    var timePos = new Date().getTime();
    for(var i=0, len=this.effects.length;i<len;i++)
      this.effects[i] && this.effects[i].loop(timePos);
  }
});

Effect.Queues = {
  instances: $H(),
  get: function(queueName) {
    if (!Object.isString(queueName)) return queueName;

    return this.instances.get(queueName) ||
      this.instances.set(queueName, new Effect.ScopedQueue());
  }
};
Effect.Queue = Effect.Queues.get('global');

Effect.Base = Class.create({
  position: null,
  start: function(options) {
    if (options && options.transition === false) options.transition = Effect.Transitions.linear;
    this.options      = Object.extend(Object.extend({ },Effect.DefaultOptions), options || { });
    this.currentFrame = 0;
    this.state        = 'idle';
    this.startOn      = this.options.delay*1000;
    this.finishOn     = this.startOn+(this.options.duration*1000);
    this.fromToDelta  = this.options.to-this.options.from;
    this.totalTime    = this.finishOn-this.startOn;
    this.totalFrames  = this.options.fps*this.options.duration;

    this.render = (function() {
      function dispatch(effect, eventName) {
        if (effect.options[eventName + 'Internal'])
          effect.options[eventName + 'Internal'](effect);
        if (effect.options[eventName])
          effect.options[eventName](effect);
      }

      return function(pos) {
        if (this.state === "idle") {
          this.state = "running";
          dispatch(this, 'beforeSetup');
          if (this.setup) this.setup();
          dispatch(this, 'afterSetup');
        }
        if (this.state === "running") {
          pos = (this.options.transition(pos) * this.fromToDelta) + this.options.from;
          this.position = pos;
          dispatch(this, 'beforeUpdate');
          if (this.update) this.update(pos);
          dispatch(this, 'afterUpdate');
        }
      };
    })();

    this.event('beforeStart');
    if (!this.options.sync)
      Effect.Queues.get(Object.isString(this.options.queue) ?
        'global' : this.options.queue.scope).add(this);
  },
  loop: function(timePos) {
    if (timePos >= this.startOn) {
      if (timePos >= this.finishOn) {
        this.render(1.0);
        this.cancel();
        this.event('beforeFinish');
        if (this.finish) this.finish();
        this.event('afterFinish');
        return;
      }
      var pos   = (timePos - this.startOn) / this.totalTime,
          frame = (pos * this.totalFrames).round();
      if (frame > this.currentFrame) {
        this.render(pos);
        this.currentFrame = frame;
      }
    }
  },
  cancel: function() {
    if (!this.options.sync)
      Effect.Queues.get(Object.isString(this.options.queue) ?
        'global' : this.options.queue.scope).remove(this);
    this.state = 'finished';
  },
  event: function(eventName) {
    if (this.options[eventName + 'Internal']) this.options[eventName + 'Internal'](this);
    if (this.options[eventName]) this.options[eventName](this);
  },
  inspect: function() {
    var data = $H();
    for(property in this)
      if (!Object.isFunction(this[property])) data.set(property, this[property]);
    return '#<Effect:' + data.inspect() + ',options:' + $H(this.options).inspect() + '>';
  }
});

Effect.Parallel = Class.create(Effect.Base, {
  initialize: function(effects) {
    this.effects = effects || [];
    this.start(arguments[1]);
  },
  update: function(position) {
    this.effects.invoke('render', position);
  },
  finish: function(position) {
    this.effects.each( function(effect) {
      effect.render(1.0);
      effect.cancel();
      effect.event('beforeFinish');
      if (effect.finish) effect.finish(position);
      effect.event('afterFinish');
    });
  }
});

Effect.Tween = Class.create(Effect.Base, {
  initialize: function(object, from, to) {
    object = Object.isString(object) ? $(object) : object;
    var args = $A(arguments), method = args.last(),
      options = args.length == 5 ? args[3] : null;
    this.method = Object.isFunction(method) ? method.bind(object) :
      Object.isFunction(object[method]) ? object[method].bind(object) :
      function(value) { object[method] = value };
    this.start(Object.extend({ from: from, to: to }, options || { }));
  },
  update: function(position) {
    this.method(position);
  }
});

Effect.Event = Class.create(Effect.Base, {
  initialize: function() {
    this.start(Object.extend({ duration: 0 }, arguments[0] || { }));
  },
  update: Prototype.emptyFunction
});

Effect.Opacity = Class.create(Effect.Base, {
  initialize: function(element) {
    this.element = $(element);
    if (!this.element) throw(Effect._elementDoesNotExistError);
    // make this work on IE on elements without 'layout'
    if (Prototype.Browser.IE && (!this.element.currentStyle.hasLayout))
      this.element.setStyle({zoom: 1});
    var options = Object.extend({
      from: this.element.getOpacity() || 0.0,
      to:   1.0
    }, arguments[1] || { });
    this.start(options);
  },
  update: function(position) {
    this.element.setOpacity(position);
  }
});

Effect.Move = Class.create(Effect.Base, {
  initialize: function(element) {
    this.element = $(element);
    if (!this.element) throw(Effect._elementDoesNotExistError);
    var options = Object.extend({
      x:    0,
      y:    0,
      mode: 'relative'
    }, arguments[1] || { });
    this.start(options);
  },
  setup: function() {
    this.element.makePositioned();
    this.originalLeft = parseFloat(this.element.getStyle('left') || '0');
    this.originalTop  = parseFloat(this.element.getStyle('top')  || '0');
    if (this.options.mode == 'absolute') {
      this.options.x = this.options.x - this.originalLeft;
      this.options.y = this.options.y - this.originalTop;
    }
  },
  update: function(position) {
    this.element.setStyle({
      left: (this.options.x  * position + this.originalLeft).round() + 'px',
      top:  (this.options.y  * position + this.originalTop).round()  + 'px'
    });
  }
});

// for backwards compatibility
Effect.MoveBy = function(element, toTop, toLeft) {
  return new Effect.Move(element,
    Object.extend({ x: toLeft, y: toTop }, arguments[3] || { }));
};

Effect.Scale = Class.create(Effect.Base, {
  initialize: function(element, percent) {
    this.element = $(element);
    if (!this.element) throw(Effect._elementDoesNotExistError);
    var options = Object.extend({
      scaleX: true,
      scaleY: true,
      scaleContent: true,
      scaleFromCenter: false,
      scaleMode: 'box',        // 'box' or 'contents' or { } with provided values
      scaleFrom: 100.0,
      scaleTo:   percent
    }, arguments[2] || { });
    this.start(options);
  },
  setup: function() {
    this.restoreAfterFinish = this.options.restoreAfterFinish || false;
    this.elementPositioning = this.element.getStyle('position');

    this.originalStyle = { };
    ['top','left','width','height','fontSize'].each( function(k) {
      this.originalStyle[k] = this.element.style[k];
    }.bind(this));

    this.originalTop  = this.element.offsetTop;
    this.originalLeft = this.element.offsetLeft;

    var fontSize = this.element.getStyle('font-size') || '100%';
    ['em','px','%','pt'].each( function(fontSizeType) {
      if (fontSize.indexOf(fontSizeType)>0) {
        this.fontSize     = parseFloat(fontSize);
        this.fontSizeType = fontSizeType;
      }
    }.bind(this));

    this.factor = (this.options.scaleTo - this.options.scaleFrom)/100;

    this.dims = null;
    if (this.options.scaleMode=='box')
      this.dims = [this.element.offsetHeight, this.element.offsetWidth];
    if (/^content/.test(this.options.scaleMode))
      this.dims = [this.element.scrollHeight, this.element.scrollWidth];
    if (!this.dims)
      this.dims = [this.options.scaleMode.originalHeight,
                   this.options.scaleMode.originalWidth];
  },
  update: function(position) {
    var currentScale = (this.options.scaleFrom/100.0) + (this.factor * position);
    if (this.options.scaleContent && this.fontSize)
      this.element.setStyle({fontSize: this.fontSize * currentScale + this.fontSizeType });
    this.setDimensions(this.dims[0] * currentScale, this.dims[1] * currentScale);
  },
  finish: function(position) {
    if (this.restoreAfterFinish) this.element.setStyle(this.originalStyle);
  },
  setDimensions: function(height, width) {
    var d = { };
    if (this.options.scaleX) d.width = width.round() + 'px';
    if (this.options.scaleY) d.height = height.round() + 'px';
    if (this.options.scaleFromCenter) {
      var topd  = (height - this.dims[0])/2;
      var leftd = (width  - this.dims[1])/2;
      if (this.elementPositioning == 'absolute') {
        if (this.options.scaleY) d.top = this.originalTop-topd + 'px';
        if (this.options.scaleX) d.left = this.originalLeft-leftd + 'px';
      } else {
        if (this.options.scaleY) d.top = -topd + 'px';
        if (this.options.scaleX) d.left = -leftd + 'px';
      }
    }
    this.element.setStyle(d);
  }
});

Effect.Highlight = Class.create(Effect.Base, {
  initialize: function(element) {
    this.element = $(element);
    if (!this.element) throw(Effect._elementDoesNotExistError);
    var options = Object.extend({ startcolor: '#ffff99' }, arguments[1] || { });
    this.start(options);
  },
  setup: function() {
    // Prevent executing on elements not in the layout flow
    if (this.element.getStyle('display')=='none') { this.cancel(); return; }
    // Disable background image during the effect
    this.oldStyle = { };
    if (!this.options.keepBackgroundImage) {
      this.oldStyle.backgroundImage = this.element.getStyle('background-image');
      this.element.setStyle({backgroundImage: 'none'});
    }
    if (!this.options.endcolor)
      this.options.endcolor = this.element.getStyle('background-color').parseColor('#ffffff');
    if (!this.options.restorecolor)
      this.options.restorecolor = this.element.getStyle('background-color');
    // init color calculations
    this._base  = $R(0,2).map(function(i){ return parseInt(this.options.startcolor.slice(i*2+1,i*2+3),16) }.bind(this));
    this._delta = $R(0,2).map(function(i){ return parseInt(this.options.endcolor.slice(i*2+1,i*2+3),16)-this._base[i] }.bind(this));
  },
  update: function(position) {
    this.element.setStyle({backgroundColor: $R(0,2).inject('#',function(m,v,i){
      return m+((this._base[i]+(this._delta[i]*position)).round().toColorPart()); }.bind(this)) });
  },
  finish: function() {
    this.element.setStyle(Object.extend(this.oldStyle, {
      backgroundColor: this.options.restorecolor
    }));
  }
});

Effect.ScrollTo = function(element) {
  var options = arguments[1] || { },
  scrollOffsets = document.viewport.getScrollOffsets(),
  elementOffsets = $(element).cumulativeOffset();

  if (options.offset) elementOffsets[1] += options.offset;

  return new Effect.Tween(null,
    scrollOffsets.top,
    elementOffsets[1],
    options,
    function(p){ scrollTo(scrollOffsets.left, p.round()); }
  );
};

/* ------------- combination effects ------------- */

Effect.Fade = function(element) {
  element = $(element);
  var oldOpacity = element.getInlineOpacity();
  var options = Object.extend({
    from: element.getOpacity() || 1.0,
    to:   0.0,
    afterFinishInternal: function(effect) {
      if (effect.options.to!=0) return;
      effect.element.hide().setStyle({opacity: oldOpacity});
    }
  }, arguments[1] || { });
  return new Effect.Opacity(element,options);
};

Effect.Appear = function(element) {
  element = $(element);
  var options = Object.extend({
  from: (element.getStyle('display') == 'none' ? 0.0 : element.getOpacity() || 0.0),
  to:   1.0,
  // force Safari to render floated elements properly
  afterFinishInternal: function(effect) {
    effect.element.forceRerendering();
  },
  beforeSetup: function(effect) {
    effect.element.setOpacity(effect.options.from).show();
  }}, arguments[1] || { });
  return new Effect.Opacity(element,options);
};

Effect.Puff = function(element) {
  element = $(element);
  var oldStyle = {
    opacity: element.getInlineOpacity(),
    position: element.getStyle('position'),
    top:  element.style.top,
    left: element.style.left,
    width: element.style.width,
    height: element.style.height
  };
  return new Effect.Parallel(
   [ new Effect.Scale(element, 200,
      { sync: true, scaleFromCenter: true, scaleContent: true, restoreAfterFinish: true }),
     new Effect.Opacity(element, { sync: true, to: 0.0 } ) ],
     Object.extend({ duration: 1.0,
      beforeSetupInternal: function(effect) {
        Position.absolutize(effect.effects[0].element);
      },
      afterFinishInternal: function(effect) {
         effect.effects[0].element.hide().setStyle(oldStyle); }
     }, arguments[1] || { })
   );
};

Effect.BlindUp = function(element) {
  element = $(element);
  element.makeClipping();
  return new Effect.Scale(element, 0,
    Object.extend({ scaleContent: false,
      scaleX: false,
      restoreAfterFinish: true,
      afterFinishInternal: function(effect) {
        effect.element.hide().undoClipping();
      }
    }, arguments[1] || { })
  );
};

Effect.BlindDown = function(element) {
  element = $(element);
  var elementDimensions = element.getDimensions();
  return new Effect.Scale(element, 100, Object.extend({
    scaleContent: false,
    scaleX: false,
    scaleFrom: 0,
    scaleMode: {originalHeight: elementDimensions.height, originalWidth: elementDimensions.width},
    restoreAfterFinish: true,
    afterSetup: function(effect) {
      effect.element.makeClipping().setStyle({height: '0px'}).show();
    },
    afterFinishInternal: function(effect) {
      effect.element.undoClipping();
    }
  }, arguments[1] || { }));
};

Effect.SwitchOff = function(element) {
  element = $(element);
  var oldOpacity = element.getInlineOpacity();
  return new Effect.Appear(element, Object.extend({
    duration: 0.4,
    from: 0,
    transition: Effect.Transitions.flicker,
    afterFinishInternal: function(effect) {
      new Effect.Scale(effect.element, 1, {
        duration: 0.3, scaleFromCenter: true,
        scaleX: false, scaleContent: false, restoreAfterFinish: true,
        beforeSetup: function(effect) {
          effect.element.makePositioned().makeClipping();
        },
        afterFinishInternal: function(effect) {
          effect.element.hide().undoClipping().undoPositioned().setStyle({opacity: oldOpacity});
        }
      });
    }
  }, arguments[1] || { }));
};

Effect.DropOut = function(element) {
  element = $(element);
  var oldStyle = {
    top: element.getStyle('top'),
    left: element.getStyle('left'),
    opacity: element.getInlineOpacity() };
  return new Effect.Parallel(
    [ new Effect.Move(element, {x: 0, y: 100, sync: true }),
      new Effect.Opacity(element, { sync: true, to: 0.0 }) ],
    Object.extend(
      { duration: 0.5,
        beforeSetup: function(effect) {
          effect.effects[0].element.makePositioned();
        },
        afterFinishInternal: function(effect) {
          effect.effects[0].element.hide().undoPositioned().setStyle(oldStyle);
        }
      }, arguments[1] || { }));
};

Effect.Shake = function(element) {
  element = $(element);
  var options = Object.extend({
    distance: 20,
    duration: 0.5
  }, arguments[1] || {});
  var distance = parseFloat(options.distance);
  var split = parseFloat(options.duration) / 10.0;
  var oldStyle = {
    top: element.getStyle('top'),
    left: element.getStyle('left') };
    return new Effect.Move(element,
      { x:  distance, y: 0, duration: split, afterFinishInternal: function(effect) {
    new Effect.Move(effect.element,
      { x: -distance*2, y: 0, duration: split*2,  afterFinishInternal: function(effect) {
    new Effect.Move(effect.element,
      { x:  distance*2, y: 0, duration: split*2,  afterFinishInternal: function(effect) {
    new Effect.Move(effect.element,
      { x: -distance*2, y: 0, duration: split*2,  afterFinishInternal: function(effect) {
    new Effect.Move(effect.element,
      { x:  distance*2, y: 0, duration: split*2,  afterFinishInternal: function(effect) {
    new Effect.Move(effect.element,
      { x: -distance, y: 0, duration: split, afterFinishInternal: function(effect) {
        effect.element.undoPositioned().setStyle(oldStyle);
  }}); }}); }}); }}); }}); }});
};

Effect.SlideDown = function(element) {
  element = $(element).cleanWhitespace();
  // SlideDown need to have the content of the element wrapped in a container element with fixed height!
  var oldInnerBottom = element.down().getStyle('bottom');
  var elementDimensions = element.getDimensions();
  return new Effect.Scale(element, 100, Object.extend({
    scaleContent: false,
    scaleX: false,
    scaleFrom: window.opera ? 0 : 1,
    scaleMode: {originalHeight: elementDimensions.height, originalWidth: elementDimensions.width},
    restoreAfterFinish: true,
    afterSetup: function(effect) {
      effect.element.makePositioned();
      effect.element.down().makePositioned();
      if (window.opera) effect.element.setStyle({top: ''});
      effect.element.makeClipping().setStyle({height: '0px'}).show();
    },
    afterUpdateInternal: function(effect) {
      effect.element.down().setStyle({bottom:
        (effect.dims[0] - effect.element.clientHeight) + 'px' });
    },
    afterFinishInternal: function(effect) {
      effect.element.undoClipping().undoPositioned();
      effect.element.down().undoPositioned().setStyle({bottom: oldInnerBottom}); }
    }, arguments[1] || { })
  );
};

Effect.SlideUp = function(element) {
  element = $(element).cleanWhitespace();
  var oldInnerBottom = element.down().getStyle('bottom');
  var elementDimensions = element.getDimensions();
  return new Effect.Scale(element, window.opera ? 0 : 1,
   Object.extend({ scaleContent: false,
    scaleX: false,
    scaleMode: 'box',
    scaleFrom: 100,
    scaleMode: {originalHeight: elementDimensions.height, originalWidth: elementDimensions.width},
    restoreAfterFinish: true,
    afterSetup: function(effect) {
      effect.element.makePositioned();
      effect.element.down().makePositioned();
      if (window.opera) effect.element.setStyle({top: ''});
      effect.element.makeClipping().show();
    },
    afterUpdateInternal: function(effect) {
      effect.element.down().setStyle({bottom:
        (effect.dims[0] - effect.element.clientHeight) + 'px' });
    },
    afterFinishInternal: function(effect) {
      effect.element.hide().undoClipping().undoPositioned();
      effect.element.down().undoPositioned().setStyle({bottom: oldInnerBottom});
    }
   }, arguments[1] || { })
  );
};

// Bug in opera makes the TD containing this element expand for a instance after finish
Effect.Squish = function(element) {
  return new Effect.Scale(element, window.opera ? 1 : 0, {
    restoreAfterFinish: true,
    beforeSetup: function(effect) {
      effect.element.makeClipping();
    },
    afterFinishInternal: function(effect) {
      effect.element.hide().undoClipping();
    }
  });
};

Effect.Grow = function(element) {
  element = $(element);
  var options = Object.extend({
    direction: 'center',
    moveTransition: Effect.Transitions.sinoidal,
    scaleTransition: Effect.Transitions.sinoidal,
    opacityTransition: Effect.Transitions.full
  }, arguments[1] || { });
  var oldStyle = {
    top: element.style.top,
    left: element.style.left,
    height: element.style.height,
    width: element.style.width,
    opacity: element.getInlineOpacity() };

  var dims = element.getDimensions();
  var initialMoveX, initialMoveY;
  var moveX, moveY;

  switch (options.direction) {
    case 'top-left':
      initialMoveX = initialMoveY = moveX = moveY = 0;
      break;
    case 'top-right':
      initialMoveX = dims.width;
      initialMoveY = moveY = 0;
      moveX = -dims.width;
      break;
    case 'bottom-left':
      initialMoveX = moveX = 0;
      initialMoveY = dims.height;
      moveY = -dims.height;
      break;
    case 'bottom-right':
      initialMoveX = dims.width;
      initialMoveY = dims.height;
      moveX = -dims.width;
      moveY = -dims.height;
      break;
    case 'center':
      initialMoveX = dims.width / 2;
      initialMoveY = dims.height / 2;
      moveX = -dims.width / 2;
      moveY = -dims.height / 2;
      break;
  }

  return new Effect.Move(element, {
    x: initialMoveX,
    y: initialMoveY,
    duration: 0.01,
    beforeSetup: function(effect) {
      effect.element.hide().makeClipping().makePositioned();
    },
    afterFinishInternal: function(effect) {
      new Effect.Parallel(
        [ new Effect.Opacity(effect.element, { sync: true, to: 1.0, from: 0.0, transition: options.opacityTransition }),
          new Effect.Move(effect.element, { x: moveX, y: moveY, sync: true, transition: options.moveTransition }),
          new Effect.Scale(effect.element, 100, {
            scaleMode: { originalHeight: dims.height, originalWidth: dims.width },
            sync: true, scaleFrom: window.opera ? 1 : 0, transition: options.scaleTransition, restoreAfterFinish: true})
        ], Object.extend({
             beforeSetup: function(effect) {
               effect.effects[0].element.setStyle({height: '0px'}).show();
             },
             afterFinishInternal: function(effect) {
               effect.effects[0].element.undoClipping().undoPositioned().setStyle(oldStyle);
             }
           }, options)
      );
    }
  });
};

Effect.Shrink = function(element) {
  element = $(element);
  var options = Object.extend({
    direction: 'center',
    moveTransition: Effect.Transitions.sinoidal,
    scaleTransition: Effect.Transitions.sinoidal,
    opacityTransition: Effect.Transitions.none
  }, arguments[1] || { });
  var oldStyle = {
    top: element.style.top,
    left: element.style.left,
    height: element.style.height,
    width: element.style.width,
    opacity: element.getInlineOpacity() };

  var dims = element.getDimensions();
  var moveX, moveY;

  switch (options.direction) {
    case 'top-left':
      moveX = moveY = 0;
      break;
    case 'top-right':
      moveX = dims.width;
      moveY = 0;
      break;
    case 'bottom-left':
      moveX = 0;
      moveY = dims.height;
      break;
    case 'bottom-right':
      moveX = dims.width;
      moveY = dims.height;
      break;
    case 'center':
      moveX = dims.width / 2;
      moveY = dims.height / 2;
      break;
  }

  return new Effect.Parallel(
    [ new Effect.Opacity(element, { sync: true, to: 0.0, from: 1.0, transition: options.opacityTransition }),
      new Effect.Scale(element, window.opera ? 1 : 0, { sync: true, transition: options.scaleTransition, restoreAfterFinish: true}),
      new Effect.Move(element, { x: moveX, y: moveY, sync: true, transition: options.moveTransition })
    ], Object.extend({
         beforeStartInternal: function(effect) {
           effect.effects[0].element.makePositioned().makeClipping();
         },
         afterFinishInternal: function(effect) {
           effect.effects[0].element.hide().undoClipping().undoPositioned().setStyle(oldStyle); }
       }, options)
  );
};

Effect.Pulsate = function(element) {
  element = $(element);
  var options    = arguments[1] || { },
    oldOpacity = element.getInlineOpacity(),
    transition = options.transition || Effect.Transitions.linear,
    reverser   = function(pos){
      return 1 - transition((-Math.cos((pos*(options.pulses||5)*2)*Math.PI)/2) + .5);
    };

  return new Effect.Opacity(element,
    Object.extend(Object.extend({  duration: 2.0, from: 0,
      afterFinishInternal: function(effect) { effect.element.setStyle({opacity: oldOpacity}); }
    }, options), {transition: reverser}));
};

Effect.Fold = function(element) {
  element = $(element);
  var oldStyle = {
    top: element.style.top,
    left: element.style.left,
    width: element.style.width,
    height: element.style.height };
  element.makeClipping();
  return new Effect.Scale(element, 5, Object.extend({
    scaleContent: false,
    scaleX: false,
    afterFinishInternal: function(effect) {
    new Effect.Scale(element, 1, {
      scaleContent: false,
      scaleY: false,
      afterFinishInternal: function(effect) {
        effect.element.hide().undoClipping().setStyle(oldStyle);
      } });
  }}, arguments[1] || { }));
};

Effect.Morph = Class.create(Effect.Base, {
  initialize: function(element) {
    this.element = $(element);
    if (!this.element) throw(Effect._elementDoesNotExistError);
    var options = Object.extend({
      style: { }
    }, arguments[1] || { });

    if (!Object.isString(options.style)) this.style = $H(options.style);
    else {
      if (options.style.include(':'))
        this.style = options.style.parseStyle();
      else {
        this.element.addClassName(options.style);
        this.style = $H(this.element.getStyles());
        this.element.removeClassName(options.style);
        var css = this.element.getStyles();
        this.style = this.style.reject(function(style) {
          return style.value == css[style.key];
        });
        options.afterFinishInternal = function(effect) {
          effect.element.addClassName(effect.options.style);
          effect.transforms.each(function(transform) {
            effect.element.style[transform.style] = '';
          });
        };
      }
    }
    this.start(options);
  },

  setup: function(){
    function parseColor(color){
      if (!color || ['rgba(0, 0, 0, 0)','transparent'].include(color)) color = '#ffffff';
      color = color.parseColor();
      return $R(0,2).map(function(i){
        return parseInt( color.slice(i*2+1,i*2+3), 16 );
      });
    }
    this.transforms = this.style.map(function(pair){
      var property = pair[0], value = pair[1], unit = null;

      if (value.parseColor('#zzzzzz') != '#zzzzzz') {
        value = value.parseColor();
        unit  = 'color';
      } else if (property == 'opacity') {
        value = parseFloat(value);
        if (Prototype.Browser.IE && (!this.element.currentStyle.hasLayout))
          this.element.setStyle({zoom: 1});
      } else if (Element.CSS_LENGTH.test(value)) {
          var components = value.match(/^([\+\-]?[0-9\.]+)(.*)$/);
          value = parseFloat(components[1]);
          unit = (components.length == 3) ? components[2] : null;
      }

      var originalValue = this.element.getStyle(property);
      return {
        style: property.camelize(),
        originalValue: unit=='color' ? parseColor(originalValue) : parseFloat(originalValue || 0),
        targetValue: unit=='color' ? parseColor(value) : value,
        unit: unit
      };
    }.bind(this)).reject(function(transform){
      return (
        (transform.originalValue == transform.targetValue) ||
        (
          transform.unit != 'color' &&
          (isNaN(transform.originalValue) || isNaN(transform.targetValue))
        )
      );
    });
  },
  update: function(position) {
    var style = { }, transform, i = this.transforms.length;
    while(i--)
      style[(transform = this.transforms[i]).style] =
        transform.unit=='color' ? '#'+
          (Math.round(transform.originalValue[0]+
            (transform.targetValue[0]-transform.originalValue[0])*position)).toColorPart() +
          (Math.round(transform.originalValue[1]+
            (transform.targetValue[1]-transform.originalValue[1])*position)).toColorPart() +
          (Math.round(transform.originalValue[2]+
            (transform.targetValue[2]-transform.originalValue[2])*position)).toColorPart() :
        (transform.originalValue +
          (transform.targetValue - transform.originalValue) * position).toFixed(3) +
            (transform.unit === null ? '' : transform.unit);
    this.element.setStyle(style, true);
  }
});

Effect.Transform = Class.create({
  initialize: function(tracks){
    this.tracks  = [];
    this.options = arguments[1] || { };
    this.addTracks(tracks);
  },
  addTracks: function(tracks){
    tracks.each(function(track){
      track = $H(track);
      var data = track.values().first();
      this.tracks.push($H({
        ids:     track.keys().first(),
        effect:  Effect.Morph,
        options: { style: data }
      }));
    }.bind(this));
    return this;
  },
  play: function(){
    return new Effect.Parallel(
      this.tracks.map(function(track){
        var ids = track.get('ids'), effect = track.get('effect'), options = track.get('options');
        var elements = [$(ids) || $$(ids)].flatten();
        return elements.map(function(e){ return new effect(e, Object.extend({ sync:true }, options)) });
      }).flatten(),
      this.options
    );
  }
});

Element.CSS_PROPERTIES = $w(
  'backgroundColor backgroundPosition borderBottomColor borderBottomStyle ' +
  'borderBottomWidth borderLeftColor borderLeftStyle borderLeftWidth ' +
  'borderRightColor borderRightStyle borderRightWidth borderSpacing ' +
  'borderTopColor borderTopStyle borderTopWidth bottom clip color ' +
  'fontSize fontWeight height left letterSpacing lineHeight ' +
  'marginBottom marginLeft marginRight marginTop markerOffset maxHeight '+
  'maxWidth minHeight minWidth opacity outlineColor outlineOffset ' +
  'outlineWidth paddingBottom paddingLeft paddingRight paddingTop ' +
  'right textIndent top width wordSpacing zIndex');

Element.CSS_LENGTH = /^(([\+\-]?[0-9\.]+)(em|ex|px|in|cm|mm|pt|pc|\%))|0$/;

String.__parseStyleElement = document.createElement('div');
String.prototype.parseStyle = function(){
  var style, styleRules = $H();
  if (Prototype.Browser.WebKit)
    style = new Element('div',{style:this}).style;
  else {
    String.__parseStyleElement.innerHTML = '<div style="' + this + '"></div>';
    style = String.__parseStyleElement.childNodes[0].style;
  }

  Element.CSS_PROPERTIES.each(function(property){
    if (style[property]) styleRules.set(property, style[property]);
  });

  if (Prototype.Browser.IE && this.include('opacity'))
    styleRules.set('opacity', this.match(/opacity:\s*((?:0|1)?(?:\.\d*)?)/)[1]);

  return styleRules;
};

if (document.defaultView && document.defaultView.getComputedStyle) {
  Element.getStyles = function(element) {
    var css = document.defaultView.getComputedStyle($(element), null);
    return Element.CSS_PROPERTIES.inject({ }, function(styles, property) {
      styles[property] = css[property];
      return styles;
    });
  };
} else {
  Element.getStyles = function(element) {
    element = $(element);
    var css = element.currentStyle, styles;
    styles = Element.CSS_PROPERTIES.inject({ }, function(results, property) {
      results[property] = css[property];
      return results;
    });
    if (!styles.opacity) styles.opacity = element.getOpacity();
    return styles;
  };
}

Effect.Methods = {
  morph: function(element, style) {
    element = $(element);
    new Effect.Morph(element, Object.extend({ style: style }, arguments[2] || { }));
    return element;
  },
  visualEffect: function(element, effect, options) {
    element = $(element);
    var s = effect.dasherize().camelize(), klass = s.charAt(0).toUpperCase() + s.substring(1);
    new Effect[klass](element, options);
    return element;
  },
  highlight: function(element, options) {
    element = $(element);
    new Effect.Highlight(element, options);
    return element;
  }
};

$w('fade appear grow shrink fold blindUp blindDown slideUp slideDown '+
  'pulsate shake puff squish switchOff dropOut').each(
  function(effect) {
    Effect.Methods[effect] = function(element, options){
      element = $(element);
      Effect[effect.charAt(0).toUpperCase() + effect.substring(1)](element, options);
      return element;
    };
  }
);

$w('getInlineOpacity forceRerendering setContentZoom collectTextNodes collectTextNodesIgnoreClass getStyles').each(
  function(f) { Effect.Methods[f] = Element[f]; }
);

Element.addMethods(Effect.Methods);
var process = process || {env: {NODE_ENV: "development"}};
/*  Prototype JavaScript framework, version 1.6.1
 *  (c) 2005-2009 Sam Stephenson
 *
 *  Prototype is freely distributable under the terms of an MIT-style license.
 *  For details, see the Prototype web site: http://www.prototypejs.org/
 *
 *--------------------------------------------------------------------------*/

var Prototype = {
  Version: '1.6.1',

  Browser: (function(){
    var ua = navigator.userAgent;
    var isOpera = Object.prototype.toString.call(window.opera) == '[object Opera]';
    return {
      IE:             !!window.attachEvent && !isOpera,
      Opera:          isOpera,
      WebKit:         ua.indexOf('AppleWebKit/') > -1,
      Gecko:          ua.indexOf('Gecko') > -1 && ua.indexOf('KHTML') === -1,
      MobileSafari:   /Apple.*Mobile.*Safari/.test(ua)
    }
  })(),

  BrowserFeatures: {
    XPath: !!document.evaluate,
    SelectorsAPI: !!document.querySelector,
    ElementExtensions: (function() {
      var constructor = window.Element || window.HTMLElement;
      return !!(constructor && constructor.prototype);
    })(),
    SpecificElementExtensions: (function() {
      if (typeof window.HTMLDivElement !== 'undefined')
        return true;

      var div = document.createElement('div');
      var form = document.createElement('form');
      var isSupported = false;

      if (div['__proto__'] && (div['__proto__'] !== form['__proto__'])) {
        isSupported = true;
      }

      div = form = null;

      return isSupported;
    })()
  },

  ScriptFragment: '<script[^>]*>([\\S\\s]*?)<\/script>',
  JSONFilter: /^\/\*-secure-([\s\S]*)\*\/\s*$/,

  emptyFunction: function() { },
  K: function(x) { return x }
};

if (Prototype.Browser.MobileSafari)
  Prototype.BrowserFeatures.SpecificElementExtensions = false;


var Abstract = { };


var Try = {
  these: function() {
    var returnValue;

    for (var i = 0, length = arguments.length; i < length; i++) {
      var lambda = arguments[i];
      try {
        returnValue = lambda();
        break;
      } catch (e) { }
    }

    return returnValue;
  }
};

/* Based on Alex Arnell's inheritance implementation. */

var Class = (function() {
  function subclass() {};
  function create() {
    var parent = null, properties = $A(arguments);
    if (Object.isFunction(properties[0]))
      parent = properties.shift();

    function klass() {
      this.initialize.apply(this, arguments);
    }

    Object.extend(klass, Class.Methods);
    klass.superclass = parent;
    klass.subclasses = [];

    if (parent) {
      subclass.prototype = parent.prototype;
      klass.prototype = new subclass;
      parent.subclasses.push(klass);
    }

    for (var i = 0; i < properties.length; i++)
      klass.addMethods(properties[i]);

    if (!klass.prototype.initialize)
      klass.prototype.initialize = Prototype.emptyFunction;

    klass.prototype.constructor = klass;
    return klass;
  }

  function addMethods(source) {
    var ancestor   = this.superclass && this.superclass.prototype;
    var properties = Object.keys(source);

    if (!Object.keys({ toString: true }).length) {
      if (source.toString != Object.prototype.toString)
        properties.push("toString");
      if (source.valueOf != Object.prototype.valueOf)
        properties.push("valueOf");
    }

    for (var i = 0, length = properties.length; i < length; i++) {
      var property = properties[i], value = source[property];
      if (ancestor && Object.isFunction(value) &&
          value.argumentNames().first() == "$super") {
        var method = value;
        value = (function(m) {
          return function() { return ancestor[m].apply(this, arguments); };
        })(property).wrap(method);

        value.valueOf = method.valueOf.bind(method);
        value.toString = method.toString.bind(method);
      }
      this.prototype[property] = value;
    }

    return this;
  }

  return {
    create: create,
    Methods: {
      addMethods: addMethods
    }
  };
})();
(function() {

  var _toString = Object.prototype.toString;

  function extend(destination, source) {
    for (var property in source)
      destination[property] = source[property];
    return destination;
  }

  function inspect(object) {
    try {
      if (isUndefined(object)) return 'undefined';
      if (object === null) return 'null';
      return object.inspect ? object.inspect() : String(object);
    } catch (e) {
      if (e instanceof RangeError) return '...';
      throw e;
    }
  }

  function toJSON(object) {
    var type = typeof object;
    switch (type) {
      case 'undefined':
      case 'function':
      case 'unknown': return;
      case 'boolean': return object.toString();
    }

    if (object === null) return 'null';
    if (object.toJSON) return object.toJSON();
    if (isElement(object)) return;

    var results = [];
    for (var property in object) {
      var value = toJSON(object[property]);
      if (!isUndefined(value))
        results.push(property.toJSON() + ': ' + value);
    }

    return '{' + results.join(', ') + '}';
  }

  function toQueryString(object) {
    return $H(object).toQueryString();
  }

  function toHTML(object) {
    return object && object.toHTML ? object.toHTML() : String.interpret(object);
  }

  function keys(object) {
    var results = [];
    for (var property in object)
      results.push(property);
    return results;
  }

  function values(object) {
    var results = [];
    for (var property in object)
      results.push(object[property]);
    return results;
  }

  function clone(object) {
    return extend({ }, object);
  }

  function isElement(object) {
    return !!(object && object.nodeType == 1);
  }

  function isArray(object) {
    return _toString.call(object) == "[object Array]";
  }


  function isHash(object) {
    return object instanceof Hash;
  }

  function isFunction(object) {
    return typeof object === "function";
  }

  function isString(object) {
    return _toString.call(object) == "[object String]";
  }

  function isNumber(object) {
    return _toString.call(object) == "[object Number]";
  }

  function isUndefined(object) {
    return typeof object === "undefined";
  }

  extend(Object, {
    extend:        extend,
    inspect:       inspect,
    toJSON:        toJSON,
    toQueryString: toQueryString,
    toHTML:        toHTML,
    keys:          keys,
    values:        values,
    clone:         clone,
    isElement:     isElement,
    isArray:       isArray,
    isHash:        isHash,
    isFunction:    isFunction,
    isString:      isString,
    isNumber:      isNumber,
    isUndefined:   isUndefined
  });
})();
Object.extend(Function.prototype, (function() {
  var slice = Array.prototype.slice;

  function update(array, args) {
    var arrayLength = array.length, length = args.length;
    while (length--) array[arrayLength + length] = args[length];
    return array;
  }

  function merge(array, args) {
    array = slice.call(array, 0);
    return update(array, args);
  }

  function argumentNames() {
    var names = this.toString().match(/^[\s\(]*function[^(]*\(([^)]*)\)/)[1]
      .replace(/\/\/.*?[\r\n]|\/\*(?:.|[\r\n])*?\*\//g, '')
      .replace(/\s+/g, '').split(',');
    return names.length == 1 && !names[0] ? [] : names;
  }

  function bind(context) {
    if (arguments.length < 2 && Object.isUndefined(arguments[0])) return this;
    var __method = this, args = slice.call(arguments, 1);
    return function() {
      var a = merge(args, arguments);
      return __method.apply(context, a);
    }
  }

  function bindAsEventListener(context) {
    var __method = this, args = slice.call(arguments, 1);
    return function(event) {
      var a = update([event || window.event], args);
      return __method.apply(context, a);
    }
  }

  function curry() {
    if (!arguments.length) return this;
    var __method = this, args = slice.call(arguments, 0);
    return function() {
      var a = merge(args, arguments);
      return __method.apply(this, a);
    }
  }

  function delay(timeout) {
    var __method = this, args = slice.call(arguments, 1);
    timeout = timeout * 1000
    return window.setTimeout(function() {
      return __method.apply(__method, args);
    }, timeout);
  }

  function defer() {
    var args = update([0.01], arguments);
    return this.delay.apply(this, args);
  }

  function wrap(wrapper) {
    var __method = this;
    return function() {
      var a = update([__method.bind(this)], arguments);
      return wrapper.apply(this, a);
    }
  }

  function methodize() {
    if (this._methodized) return this._methodized;
    var __method = this;
    return this._methodized = function() {
      var a = update([this], arguments);
      return __method.apply(null, a);
    };
  }

  return {
    argumentNames:       argumentNames,
    bind:                bind,
    bindAsEventListener: bindAsEventListener,
    curry:               curry,
    delay:               delay,
    defer:               defer,
    wrap:                wrap,
    methodize:           methodize
  }
})());


Date.prototype.toJSON = function() {
  return '"' + this.getUTCFullYear() + '-' +
    (this.getUTCMonth() + 1).toPaddedString(2) + '-' +
    this.getUTCDate().toPaddedString(2) + 'T' +
    this.getUTCHours().toPaddedString(2) + ':' +
    this.getUTCMinutes().toPaddedString(2) + ':' +
    this.getUTCSeconds().toPaddedString(2) + 'Z"';
};


RegExp.prototype.match = RegExp.prototype.test;

RegExp.escape = function(str) {
  return String(str).replace(/([.*+?^=!:${}()|[\]\/\\])/g, '\\$1');
};
var PeriodicalExecuter = Class.create({
  initialize: function(callback, frequency) {
    this.callback = callback;
    this.frequency = frequency;
    this.currentlyExecuting = false;

    this.registerCallback();
  },

  registerCallback: function() {
    this.timer = setInterval(this.onTimerEvent.bind(this), this.frequency * 1000);
  },

  execute: function() {
    this.callback(this);
  },

  stop: function() {
    if (!this.timer) return;
    clearInterval(this.timer);
    this.timer = null;
  },

  onTimerEvent: function() {
    if (!this.currentlyExecuting) {
      try {
        this.currentlyExecuting = true;
        this.execute();
        this.currentlyExecuting = false;
      } catch(e) {
        this.currentlyExecuting = false;
        throw e;
      }
    }
  }
});
Object.extend(String, {
  interpret: function(value) {
    return value == null ? '' : String(value);
  },
  specialChar: {
    '\b': '\\b',
    '\t': '\\t',
    '\n': '\\n',
    '\f': '\\f',
    '\r': '\\r',
    '\\': '\\\\'
  }
});

Object.extend(String.prototype, (function() {

  function prepareReplacement(replacement) {
    if (Object.isFunction(replacement)) return replacement;
    var template = new Template(replacement);
    return function(match) { return template.evaluate(match) };
  }

  function gsub(pattern, replacement) {
    var result = '', source = this, match;
    replacement = prepareReplacement(replacement);

    if (Object.isString(pattern))
      pattern = RegExp.escape(pattern);

    if (!(pattern.length || pattern.source)) {
      replacement = replacement('');
      return replacement + source.split('').join(replacement) + replacement;
    }

    while (source.length > 0) {
      if (match = source.match(pattern)) {
        result += source.slice(0, match.index);
        result += String.interpret(replacement(match));
        source  = source.slice(match.index + match[0].length);
      } else {
        result += source, source = '';
      }
    }
    return result;
  }

  function sub(pattern, replacement, count) {
    replacement = prepareReplacement(replacement);
    count = Object.isUndefined(count) ? 1 : count;

    return this.gsub(pattern, function(match) {
      if (--count < 0) return match[0];
      return replacement(match);
    });
  }

  function scan(pattern, iterator) {
    this.gsub(pattern, iterator);
    return String(this);
  }

  function truncate(length, truncation) {
    length = length || 30;
    truncation = Object.isUndefined(truncation) ? '...' : truncation;
    return this.length > length ?
      this.slice(0, length - truncation.length) + truncation : String(this);
  }

  function strip() {
    return this.replace(/^\s+/, '').replace(/\s+$/, '');
  }

  function stripTags() {
    return this.replace(/<\w+(\s+("[^"]*"|'[^']*'|[^>])+)?>|<\/\w+>/gi, '');
  }

  function stripScripts() {
    return this.replace(new RegExp(Prototype.ScriptFragment, 'img'), '');
  }

  function extractScripts() {
    var matchAll = new RegExp(Prototype.ScriptFragment, 'img');
    var matchOne = new RegExp(Prototype.ScriptFragment, 'im');
    return (this.match(matchAll) || []).map(function(scriptTag) {
      return (scriptTag.match(matchOne) || ['', ''])[1];
    });
  }

  function evalScripts() {
    return this.extractScripts().map(function(script) { return eval(script) });
  }

  function escapeHTML() {
    return this.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;');
  }

  function unescapeHTML() {
    return this.stripTags().replace(/&lt;/g,'<').replace(/&gt;/g,'>').replace(/&amp;/g,'&');
  }


  function toQueryParams(separator) {
    var match = this.strip().match(/([^?#]*)(#.*)?$/);
    if (!match) return { };

    return match[1].split(separator || '&').inject({ }, function(hash, pair) {
      if ((pair = pair.split('='))[0]) {
        var key = decodeURIComponent(pair.shift());
        var value = pair.length > 1 ? pair.join('=') : pair[0];
        if (value != undefined) value = decodeURIComponent(value);

        if (key in hash) {
          if (!Object.isArray(hash[key])) hash[key] = [hash[key]];
          hash[key].push(value);
        }
        else hash[key] = value;
      }
      return hash;
    });
  }

  function toArray() {
    return this.split('');
  }

  function succ() {
    return this.slice(0, this.length - 1) +
      String.fromCharCode(this.charCodeAt(this.length - 1) + 1);
  }

  function times(count) {
    return count < 1 ? '' : new Array(count + 1).join(this);
  }

  function camelize() {
    var parts = this.split('-'), len = parts.length;
    if (len == 1) return parts[0];

    var camelized = this.charAt(0) == '-'
      ? parts[0].charAt(0).toUpperCase() + parts[0].substring(1)
      : parts[0];

    for (var i = 1; i < len; i++)
      camelized += parts[i].charAt(0).toUpperCase() + parts[i].substring(1);

    return camelized;
  }

  function capitalize() {
    return this.charAt(0).toUpperCase() + this.substring(1).toLowerCase();
  }

  function underscore() {
    return this.replace(/::/g, '/')
               .replace(/([A-Z]+)([A-Z][a-z])/g, '$1_$2')
               .replace(/([a-z\d])([A-Z])/g, '$1_$2')
               .replace(/-/g, '_')
               .toLowerCase();
  }

  function dasherize() {
    return this.replace(/_/g, '-');
  }

  function inspect(useDoubleQuotes) {
    var escapedString = this.replace(/[\x00-\x1f\\]/g, function(character) {
      if (character in String.specialChar) {
        return String.specialChar[character];
      }
      return '\\u00' + character.charCodeAt().toPaddedString(2, 16);
    });
    if (useDoubleQuotes) return '"' + escapedString.replace(/"/g, '\\"') + '"';
    return "'" + escapedString.replace(/'/g, '\\\'') + "'";
  }

  function toJSON() {
    return this.inspect(true);
  }

  function unfilterJSON(filter) {
    return this.replace(filter || Prototype.JSONFilter, '$1');
  }

  function isJSON() {
    var str = this;
    if (str.blank()) return false;
    str = this.replace(/\\./g, '@').replace(/"[^"\\\n\r]*"/g, '');
    return (/^[,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t]*$/).test(str);
  }

  function evalJSON(sanitize) {
    var json = this.unfilterJSON();
    try {
      if (!sanitize || json.isJSON()) return eval('(' + json + ')');
    } catch (e) { }
    throw new SyntaxError('Badly formed JSON string: ' + this.inspect());
  }

  function include(pattern) {
    return this.indexOf(pattern) > -1;
  }

  function startsWith(pattern) {
    return this.indexOf(pattern) === 0;
  }

  function endsWith(pattern) {
    var d = this.length - pattern.length;
    return d >= 0 && this.lastIndexOf(pattern) === d;
  }

  function empty() {
    return this == '';
  }

  function blank() {
    return /^\s*$/.test(this);
  }

  function interpolate(object, pattern) {
    return new Template(this, pattern).evaluate(object);
  }

  return {
    gsub:           gsub,
    sub:            sub,
    scan:           scan,
    truncate:       truncate,
    strip:          String.prototype.trim ? String.prototype.trim : strip,
    stripTags:      stripTags,
    stripScripts:   stripScripts,
    extractScripts: extractScripts,
    evalScripts:    evalScripts,
    escapeHTML:     escapeHTML,
    unescapeHTML:   unescapeHTML,
    toQueryParams:  toQueryParams,
    parseQuery:     toQueryParams,
    toArray:        toArray,
    succ:           succ,
    times:          times,
    camelize:       camelize,
    capitalize:     capitalize,
    underscore:     underscore,
    dasherize:      dasherize,
    inspect:        inspect,
    toJSON:         toJSON,
    unfilterJSON:   unfilterJSON,
    isJSON:         isJSON,
    evalJSON:       evalJSON,
    include:        include,
    startsWith:     startsWith,
    endsWith:       endsWith,
    empty:          empty,
    blank:          blank,
    interpolate:    interpolate
  };
})());

var Template = Class.create({
  initialize: function(template, pattern) {
    this.template = template.toString();
    this.pattern = pattern || Template.Pattern;
  },

  evaluate: function(object) {
    if (object && Object.isFunction(object.toTemplateReplacements))
      object = object.toTemplateReplacements();

    return this.template.gsub(this.pattern, function(match) {
      if (object == null) return (match[1] + '');

      var before = match[1] || '';
      if (before == '\\') return match[2];

      var ctx = object, expr = match[3];
      var pattern = /^([^.[]+|\[((?:.*?[^\\])?)\])(\.|\[|$)/;
      match = pattern.exec(expr);
      if (match == null) return before;

      while (match != null) {
        var comp = match[1].startsWith('[') ? match[2].replace(/\\\\]/g, ']') : match[1];
        ctx = ctx[comp];
        if (null == ctx || '' == match[3]) break;
        expr = expr.substring('[' == match[3] ? match[1].length : match[0].length);
        match = pattern.exec(expr);
      }

      return before + String.interpret(ctx);
    });
  }
});
Template.Pattern = /(^|.|\r|\n)(#\{(.*?)\})/;

var $break = { };

var Enumerable = (function() {
  function each(iterator, context) {
    var index = 0;
    try {
      this._each(function(value) {
        iterator.call(context, value, index++);
      });
    } catch (e) {
      if (e != $break) throw e;
    }
    return this;
  }

  function eachSlice(number, iterator, context) {
    var index = -number, slices = [], array = this.toArray();
    if (number < 1) return array;
    while ((index += number) < array.length)
      slices.push(array.slice(index, index+number));
    return slices.collect(iterator, context);
  }

  function all(iterator, context) {
    iterator = iterator || Prototype.K;
    var result = true;
    this.each(function(value, index) {
      result = result && !!iterator.call(context, value, index);
      if (!result) throw $break;
    });
    return result;
  }

  function any(iterator, context) {
    iterator = iterator || Prototype.K;
    var result = false;
    this.each(function(value, index) {
      if (result = !!iterator.call(context, value, index))
        throw $break;
    });
    return result;
  }

  function collect(iterator, context) {
    iterator = iterator || Prototype.K;
    var results = [];
    this.each(function(value, index) {
      results.push(iterator.call(context, value, index));
    });
    return results;
  }

  function detect(iterator, context) {
    var result;
    this.each(function(value, index) {
      if (iterator.call(context, value, index)) {
        result = value;
        throw $break;
      }
    });
    return result;
  }

  function findAll(iterator, context) {
    var results = [];
    this.each(function(value, index) {
      if (iterator.call(context, value, index))
        results.push(value);
    });
    return results;
  }

  function grep(filter, iterator, context) {
    iterator = iterator || Prototype.K;
    var results = [];

    if (Object.isString(filter))
      filter = new RegExp(RegExp.escape(filter));

    this.each(function(value, index) {
      if (filter.match(value))
        results.push(iterator.call(context, value, index));
    });
    return results;
  }

  function include(object) {
    if (Object.isFunction(this.indexOf))
      if (this.indexOf(object) != -1) return true;

    var found = false;
    this.each(function(value) {
      if (value == object) {
        found = true;
        throw $break;
      }
    });
    return found;
  }

  function inGroupsOf(number, fillWith) {
    fillWith = Object.isUndefined(fillWith) ? null : fillWith;
    return this.eachSlice(number, function(slice) {
      while(slice.length < number) slice.push(fillWith);
      return slice;
    });
  }

  function inject(memo, iterator, context) {
    this.each(function(value, index) {
      memo = iterator.call(context, memo, value, index);
    });
    return memo;
  }

  function invoke(method) {
    var args = $A(arguments).slice(1);
    return this.map(function(value) {
      return value[method].apply(value, args);
    });
  }

  function max(iterator, context) {
    iterator = iterator || Prototype.K;
    var result;
    this.each(function(value, index) {
      value = iterator.call(context, value, index);
      if (result == null || value >= result)
        result = value;
    });
    return result;
  }

  function min(iterator, context) {
    iterator = iterator || Prototype.K;
    var result;
    this.each(function(value, index) {
      value = iterator.call(context, value, index);
      if (result == null || value < result)
        result = value;
    });
    return result;
  }

  function partition(iterator, context) {
    iterator = iterator || Prototype.K;
    var trues = [], falses = [];
    this.each(function(value, index) {
      (iterator.call(context, value, index) ?
        trues : falses).push(value);
    });
    return [trues, falses];
  }

  function pluck(property) {
    var results = [];
    this.each(function(value) {
      results.push(value[property]);
    });
    return results;
  }

  function reject(iterator, context) {
    var results = [];
    this.each(function(value, index) {
      if (!iterator.call(context, value, index))
        results.push(value);
    });
    return results;
  }

  function sortBy(iterator, context) {
    return this.map(function(value, index) {
      return {
        value: value,
        criteria: iterator.call(context, value, index)
      };
    }).sort(function(left, right) {
      var a = left.criteria, b = right.criteria;
      return a < b ? -1 : a > b ? 1 : 0;
    }).pluck('value');
  }

  function toArray() {
    return this.map();
  }

  function zip() {
    var iterator = Prototype.K, args = $A(arguments);
    if (Object.isFunction(args.last()))
      iterator = args.pop();

    var collections = [this].concat(args).map($A);
    return this.map(function(value, index) {
      return iterator(collections.pluck(index));
    });
  }

  function size() {
    return this.toArray().length;
  }

  function inspect() {
    return '#<Enumerable:' + this.toArray().inspect() + '>';
  }









  return {
    each:       each,
    eachSlice:  eachSlice,
    all:        all,
    every:      all,
    any:        any,
    some:       any,
    collect:    collect,
    map:        collect,
    detect:     detect,
    findAll:    findAll,
    select:     findAll,
    filter:     findAll,
    grep:       grep,
    include:    include,
    member:     include,
    inGroupsOf: inGroupsOf,
    inject:     inject,
    invoke:     invoke,
    max:        max,
    min:        min,
    partition:  partition,
    pluck:      pluck,
    reject:     reject,
    sortBy:     sortBy,
    toArray:    toArray,
    entries:    toArray,
    zip:        zip,
    size:       size,
    inspect:    inspect,
    find:       detect
  };
})();
function $A(iterable) {
  if (!iterable) return [];
  if ('toArray' in Object(iterable)) return iterable.toArray();
  var length = iterable.length || 0, results = new Array(length);
  while (length--) results[length] = iterable[length];
  return results;
}

function $w(string) {
  if (!Object.isString(string)) return [];
  string = string.strip();
  return string ? string.split(/\s+/) : [];
}

Array.from = $A;


(function() {
  var arrayProto = Array.prototype,
      slice = arrayProto.slice,
      _each = arrayProto.forEach; // use native browser JS 1.6 implementation if available

  function each(iterator) {
    for (var i = 0, length = this.length; i < length; i++)
      iterator(this[i]);
  }
  if (!_each) _each = each;

  function clear() {
    this.length = 0;
    return this;
  }

  function first() {
    return this[0];
  }

  function last() {
    return this[this.length - 1];
  }

  function compact() {
    return this.select(function(value) {
      return value != null;
    });
  }

  function flatten() {
    return this.inject([], function(array, value) {
      if (Object.isArray(value))
        return array.concat(value.flatten());
      array.push(value);
      return array;
    });
  }

  function without() {
    var values = slice.call(arguments, 0);
    return this.select(function(value) {
      return !values.include(value);
    });
  }

  function reverse(inline) {
    return (inline !== false ? this : this.toArray())._reverse();
  }

  function uniq(sorted) {
    return this.inject([], function(array, value, index) {
      if (0 == index || (sorted ? array.last() != value : !array.include(value)))
        array.push(value);
      return array;
    });
  }

  function intersect(array) {
    return this.uniq().findAll(function(item) {
      return array.detect(function(value) { return item === value });
    });
  }


  function clone() {
    return slice.call(this, 0);
  }

  function size() {
    return this.length;
  }

  function inspect() {
    return '[' + this.map(Object.inspect).join(', ') + ']';
  }

  function toJSON() {
    var results = [];
    this.each(function(object) {
      var value = Object.toJSON(object);
      if (!Object.isUndefined(value)) results.push(value);
    });
    return '[' + results.join(', ') + ']';
  }

  function indexOf(item, i) {
    i || (i = 0);
    var length = this.length;
    if (i < 0) i = length + i;
    for (; i < length; i++)
      if (this[i] === item) return i;
    return -1;
  }

  function lastIndexOf(item, i) {
    i = isNaN(i) ? this.length : (i < 0 ? this.length + i : i) + 1;
    var n = this.slice(0, i).reverse().indexOf(item);
    return (n < 0) ? n : i - n - 1;
  }

  function concat() {
    var array = slice.call(this, 0), item;
    for (var i = 0, length = arguments.length; i < length; i++) {
      item = arguments[i];
      if (Object.isArray(item) && !('callee' in item)) {
        for (var j = 0, arrayLength = item.length; j < arrayLength; j++)
          array.push(item[j]);
      } else {
        array.push(item);
      }
    }
    return array;
  }

  Object.extend(arrayProto, Enumerable);

  if (!arrayProto._reverse)
    arrayProto._reverse = arrayProto.reverse;

  Object.extend(arrayProto, {
    _each:     _each,
    clear:     clear,
    first:     first,
    last:      last,
    compact:   compact,
    flatten:   flatten,
    without:   without,
    reverse:   reverse,
    uniq:      uniq,
    intersect: intersect,
    clone:     clone,
    toArray:   clone,
    size:      size,
    inspect:   inspect,
    toJSON:    toJSON
  });

  var CONCAT_ARGUMENTS_BUGGY = (function() {
    return [].concat(arguments)[0][0] !== 1;
  })(1,2)

  if (CONCAT_ARGUMENTS_BUGGY) arrayProto.concat = concat;

  if (!arrayProto.indexOf) arrayProto.indexOf = indexOf;
  if (!arrayProto.lastIndexOf) arrayProto.lastIndexOf = lastIndexOf;
})();
function $H(object) {
  return new Hash(object);
};

var Hash = Class.create(Enumerable, (function() {
  function initialize(object) {
    this._object = Object.isHash(object) ? object.toObject() : Object.clone(object);
  }

  function _each(iterator) {
    for (var key in this._object) {
      var value = this._object[key], pair = [key, value];
      pair.key = key;
      pair.value = value;
      iterator(pair);
    }
  }

  function set(key, value) {
    return this._object[key] = value;
  }

  function get(key) {
    if (this._object[key] !== Object.prototype[key])
      return this._object[key];
  }

  function unset(key) {
    var value = this._object[key];
    delete this._object[key];
    return value;
  }

  function toObject() {
    return Object.clone(this._object);
  }

  function keys() {
    return this.pluck('key');
  }

  function values() {
    return this.pluck('value');
  }

  function index(value) {
    var match = this.detect(function(pair) {
      return pair.value === value;
    });
    return match && match.key;
  }

  function merge(object) {
    return this.clone().update(object);
  }

  function update(object) {
    return new Hash(object).inject(this, function(result, pair) {
      result.set(pair.key, pair.value);
      return result;
    });
  }

  function toQueryPair(key, value) {
    if (Object.isUndefined(value)) return key;
    return key + '=' + encodeURIComponent(String.interpret(value));
  }

  function toQueryString() {
    return this.inject([], function(results, pair) {
      var key = encodeURIComponent(pair.key), values = pair.value;

      if (values && typeof values == 'object') {
        if (Object.isArray(values))
          return results.concat(values.map(toQueryPair.curry(key)));
      } else results.push(toQueryPair(key, values));
      return results;
    }).join('&');
  }

  function inspect() {
    return '#<Hash:{' + this.map(function(pair) {
      return pair.map(Object.inspect).join(': ');
    }).join(', ') + '}>';
  }

  function toJSON() {
    return Object.toJSON(this.toObject());
  }

  function clone() {
    return new Hash(this);
  }

  return {
    initialize:             initialize,
    _each:                  _each,
    set:                    set,
    get:                    get,
    unset:                  unset,
    toObject:               toObject,
    toTemplateReplacements: toObject,
    keys:                   keys,
    values:                 values,
    index:                  index,
    merge:                  merge,
    update:                 update,
    toQueryString:          toQueryString,
    inspect:                inspect,
    toJSON:                 toJSON,
    clone:                  clone
  };
})());

Hash.from = $H;
Object.extend(Number.prototype, (function() {
  function toColorPart() {
    return this.toPaddedString(2, 16);
  }

  function succ() {
    return this + 1;
  }

  function times(iterator, context) {
    $R(0, this, true).each(iterator, context);
    return this;
  }

  function toPaddedString(length, radix) {
    var string = this.toString(radix || 10);
    return '0'.times(length - string.length) + string;
  }

  function toJSON() {
    return isFinite(this) ? this.toString() : 'null';
  }

  function abs() {
    return Math.abs(this);
  }

  function round() {
    return Math.round(this);
  }

  function ceil() {
    return Math.ceil(this);
  }

  function floor() {
    return Math.floor(this);
  }

  return {
    toColorPart:    toColorPart,
    succ:           succ,
    times:          times,
    toPaddedString: toPaddedString,
    toJSON:         toJSON,
    abs:            abs,
    round:          round,
    ceil:           ceil,
    floor:          floor
  };
})());

function $R(start, end, exclusive) {
  return new ObjectRange(start, end, exclusive);
}

var ObjectRange = Class.create(Enumerable, (function() {
  function initialize(start, end, exclusive) {
    this.start = start;
    this.end = end;
    this.exclusive = exclusive;
  }

  function _each(iterator) {
    var value = this.start;
    while (this.include(value)) {
      iterator(value);
      value = value.succ();
    }
  }

  function include(value) {
    if (value < this.start)
      return false;
    if (this.exclusive)
      return value < this.end;
    return value <= this.end;
  }

  return {
    initialize: initialize,
    _each:      _each,
    include:    include
  };
})());



var Ajax = {
  getTransport: function() {
    return Try.these(
      function() {return new XMLHttpRequest()},
      function() {return new ActiveXObject('Msxml2.XMLHTTP')},
      function() {return new ActiveXObject('Microsoft.XMLHTTP')}
    ) || false;
  },

  activeRequestCount: 0
};

Ajax.Responders = {
  responders: [],

  _each: function(iterator) {
    this.responders._each(iterator);
  },

  register: function(responder) {
    if (!this.include(responder))
      this.responders.push(responder);
  },

  unregister: function(responder) {
    this.responders = this.responders.without(responder);
  },

  dispatch: function(callback, request, transport, json) {
    this.each(function(responder) {
      if (Object.isFunction(responder[callback])) {
        try {
          responder[callback].apply(responder, [request, transport, json]);
        } catch (e) { }
      }
    });
  }
};

Object.extend(Ajax.Responders, Enumerable);

Ajax.Responders.register({
  onCreate:   function() { Ajax.activeRequestCount++ },
  onComplete: function() { Ajax.activeRequestCount-- }
});
Ajax.Base = Class.create({
  initialize: function(options) {
    this.options = {
      method:       'post',
      asynchronous: true,
      contentType:  'application/x-www-form-urlencoded',
      encoding:     'UTF-8',
      parameters:   '',
      evalJSON:     true,
      evalJS:       true
    };
    Object.extend(this.options, options || { });

    this.options.method = this.options.method.toLowerCase();

    if (Object.isString(this.options.parameters))
      this.options.parameters = this.options.parameters.toQueryParams();
    else if (Object.isHash(this.options.parameters))
      this.options.parameters = this.options.parameters.toObject();
  }
});
Ajax.Request = Class.create(Ajax.Base, {
  _complete: false,

  initialize: function($super, url, options) {
    $super(options);
    this.transport = Ajax.getTransport();
    this.request(url);
  },

  request: function(url) {
    this.url = url;
    this.method = this.options.method;
    var params = Object.clone(this.options.parameters);

    if (!['get', 'post'].include(this.method)) {
      params['_method'] = this.method;
      this.method = 'post';
    }

    this.parameters = params;

    if (params = Object.toQueryString(params)) {
      if (this.method == 'get')
        this.url += (this.url.include('?') ? '&' : '?') + params;
      else if (/Konqueror|Safari|KHTML/.test(navigator.userAgent))
        params += '&_=';
    }

    try {
      var response = new Ajax.Response(this);
      if (this.options.onCreate) this.options.onCreate(response);
      Ajax.Responders.dispatch('onCreate', this, response);

      this.transport.open(this.method.toUpperCase(), this.url,
        this.options.asynchronous);

      if (this.options.asynchronous) this.respondToReadyState.bind(this).defer(1);

      this.transport.onreadystatechange = this.onStateChange.bind(this);
      this.setRequestHeaders();

      this.body = this.method == 'post' ? (this.options.postBody || params) : null;
      this.transport.send(this.body);

      /* Force Firefox to handle ready state 4 for synchronous requests */
      if (!this.options.asynchronous && this.transport.overrideMimeType)
        this.onStateChange();

    }
    catch (e) {
      this.dispatchException(e);
    }
  },

  onStateChange: function() {
    var readyState = this.transport.readyState;
    if (readyState > 1 && !((readyState == 4) && this._complete))
      this.respondToReadyState(this.transport.readyState);
  },

  setRequestHeaders: function() {
    var headers = {
      'X-Requested-With': 'XMLHttpRequest',
      'X-Prototype-Version': Prototype.Version,
      'Accept': 'text/javascript, text/html, application/xml, text/xml, */*'
    };

    if (this.method == 'post') {
      headers['Content-type'] = this.options.contentType +
        (this.options.encoding ? '; charset=' + this.options.encoding : '');

      /* Force "Connection: close" for older Mozilla browsers to work
       * around a bug where XMLHttpRequest sends an incorrect
       * Content-length header. See Mozilla Bugzilla #246651.
       */
      if (this.transport.overrideMimeType &&
          (navigator.userAgent.match(/Gecko\/(\d{4})/) || [0,2005])[1] < 2005)
            headers['Connection'] = 'close';
    }

    if (typeof this.options.requestHeaders == 'object') {
      var extras = this.options.requestHeaders;

      if (Object.isFunction(extras.push))
        for (var i = 0, length = extras.length; i < length; i += 2)
          headers[extras[i]] = extras[i+1];
      else
        $H(extras).each(function(pair) { headers[pair.key] = pair.value });
    }

    for (var name in headers)
      this.transport.setRequestHeader(name, headers[name]);
  },

  success: function() {
    var status = this.getStatus();
    return !status || (status >= 200 && status < 300);
  },

  getStatus: function() {
    try {
      return this.transport.status || 0;
    } catch (e) { return 0 }
  },

  respondToReadyState: function(readyState) {
    var state = Ajax.Request.Events[readyState], response = new Ajax.Response(this);

    if (state == 'Complete') {
      try {
        this._complete = true;
        (this.options['on' + response.status]
         || this.options['on' + (this.success() ? 'Success' : 'Failure')]
         || Prototype.emptyFunction)(response, response.headerJSON);
      } catch (e) {
        this.dispatchException(e);
      }

      var contentType = response.getHeader('Content-type');
      if (this.options.evalJS == 'force'
          || (this.options.evalJS && this.isSameOrigin() && contentType
          && contentType.match(/^\s*(text|application)\/(x-)?(java|ecma)script(;.*)?\s*$/i)))
        this.evalResponse();
    }

    try {
      (this.options['on' + state] || Prototype.emptyFunction)(response, response.headerJSON);
      Ajax.Responders.dispatch('on' + state, this, response, response.headerJSON);
    } catch (e) {
      this.dispatchException(e);
    }

    if (state == 'Complete') {
      this.transport.onreadystatechange = Prototype.emptyFunction;
    }
  },

  isSameOrigin: function() {
    var m = this.url.match(/^\s*https?:\/\/[^\/]*/);
    return !m || (m[0] == '#{protocol}//#{domain}#{port}'.interpolate({
      protocol: location.protocol,
      domain: document.domain,
      port: location.port ? ':' + location.port : ''
    }));
  },

  getHeader: function(name) {
    try {
      return this.transport.getResponseHeader(name) || null;
    } catch (e) { return null; }
  },

  evalResponse: function() {
    try {
      return eval((this.transport.responseText || '').unfilterJSON());
    } catch (e) {
      this.dispatchException(e);
    }
  },

  dispatchException: function(exception) {
    (this.options.onException || Prototype.emptyFunction)(this, exception);
    Ajax.Responders.dispatch('onException', this, exception);
  }
});

Ajax.Request.Events =
  ['Uninitialized', 'Loading', 'Loaded', 'Interactive', 'Complete'];








Ajax.Response = Class.create({
  initialize: function(request){
    this.request = request;
    var transport  = this.transport  = request.transport,
        readyState = this.readyState = transport.readyState;

    if((readyState > 2 && !Prototype.Browser.IE) || readyState == 4) {
      this.status       = this.getStatus();
      this.statusText   = this.getStatusText();
      this.responseText = String.interpret(transport.responseText);
      this.headerJSON   = this._getHeaderJSON();
    }

    if(readyState == 4) {
      var xml = transport.responseXML;
      this.responseXML  = Object.isUndefined(xml) ? null : xml;
      this.responseJSON = this._getResponseJSON();
    }
  },

  status:      0,

  statusText: '',

  getStatus: Ajax.Request.prototype.getStatus,

  getStatusText: function() {
    try {
      return this.transport.statusText || '';
    } catch (e) { return '' }
  },

  getHeader: Ajax.Request.prototype.getHeader,

  getAllHeaders: function() {
    try {
      return this.getAllResponseHeaders();
    } catch (e) { return null }
  },

  getResponseHeader: function(name) {
    return this.transport.getResponseHeader(name);
  },

  getAllResponseHeaders: function() {
    return this.transport.getAllResponseHeaders();
  },

  _getHeaderJSON: function() {
    var json = this.getHeader('X-JSON');
    if (!json) return null;
    json = decodeURIComponent(escape(json));
    try {
      return json.evalJSON(this.request.options.sanitizeJSON ||
        !this.request.isSameOrigin());
    } catch (e) {
      this.request.dispatchException(e);
    }
  },

  _getResponseJSON: function() {
    var options = this.request.options;
    if (!options.evalJSON || (options.evalJSON != 'force' &&
      !(this.getHeader('Content-type') || '').include('application/json')) ||
        this.responseText.blank())
          return null;
    try {
      return this.responseText.evalJSON(options.sanitizeJSON ||
        !this.request.isSameOrigin());
    } catch (e) {
      this.request.dispatchException(e);
    }
  }
});

Ajax.Updater = Class.create(Ajax.Request, {
  initialize: function($super, container, url, options) {
    this.container = {
      success: (container.success || container),
      failure: (container.failure || (container.success ? null : container))
    };

    options = Object.clone(options);
    var onComplete = options.onComplete;
    options.onComplete = (function(response, json) {
      this.updateContent(response.responseText);
      if (Object.isFunction(onComplete)) onComplete(response, json);
    }).bind(this);

    $super(url, options);
  },

  updateContent: function(responseText) {
    var receiver = this.container[this.success() ? 'success' : 'failure'],
        options = this.options;

    if (!options.evalScripts) responseText = responseText.stripScripts();

    if (receiver = $(receiver)) {
      if (options.insertion) {
        if (Object.isString(options.insertion)) {
          var insertion = { }; insertion[options.insertion] = responseText;
          receiver.insert(insertion);
        }
        else options.insertion(receiver, responseText);
      }
      else receiver.update(responseText);
    }
  }
});

Ajax.PeriodicalUpdater = Class.create(Ajax.Base, {
  initialize: function($super, container, url, options) {
    $super(options);
    this.onComplete = this.options.onComplete;

    this.frequency = (this.options.frequency || 2);
    this.decay = (this.options.decay || 1);

    this.updater = { };
    this.container = container;
    this.url = url;

    this.start();
  },

  start: function() {
    this.options.onComplete = this.updateComplete.bind(this);
    this.onTimerEvent();
  },

  stop: function() {
    this.updater.options.onComplete = undefined;
    clearTimeout(this.timer);
    (this.onComplete || Prototype.emptyFunction).apply(this, arguments);
  },

  updateComplete: function(response) {
    if (this.options.decay) {
      this.decay = (response.responseText == this.lastText ?
        this.decay * this.options.decay : 1);

      this.lastText = response.responseText;
    }
    this.timer = this.onTimerEvent.bind(this).delay(this.decay * this.frequency);
  },

  onTimerEvent: function() {
    this.updater = new Ajax.Updater(this.container, this.url, this.options);
  }
});



function $(element) {
  if (arguments.length > 1) {
    for (var i = 0, elements = [], length = arguments.length; i < length; i++)
      elements.push($(arguments[i]));
    return elements;
  }
  if (Object.isString(element))
    element = document.getElementById(element);
  return Element.extend(element);
}

if (Prototype.BrowserFeatures.XPath) {
  document._getElementsByXPath = function(expression, parentElement) {
    var results = [];
    var query = document.evaluate(expression, $(parentElement) || document,
      null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
    for (var i = 0, length = query.snapshotLength; i < length; i++)
      results.push(Element.extend(query.snapshotItem(i)));
    return results;
  };
}

/*--------------------------------------------------------------------------*/

if (!window.Node) var Node = { };

if (!Node.ELEMENT_NODE) {
  Object.extend(Node, {
    ELEMENT_NODE: 1,
    ATTRIBUTE_NODE: 2,
    TEXT_NODE: 3,
    CDATA_SECTION_NODE: 4,
    ENTITY_REFERENCE_NODE: 5,
    ENTITY_NODE: 6,
    PROCESSING_INSTRUCTION_NODE: 7,
    COMMENT_NODE: 8,
    DOCUMENT_NODE: 9,
    DOCUMENT_TYPE_NODE: 10,
    DOCUMENT_FRAGMENT_NODE: 11,
    NOTATION_NODE: 12
  });
}


(function(global) {

  var SETATTRIBUTE_IGNORES_NAME = (function(){
    var elForm = document.createElement("form");
    var elInput = document.createElement("input");
    var root = document.documentElement;
    elInput.setAttribute("name", "test");
    elForm.appendChild(elInput);
    root.appendChild(elForm);
    var isBuggy = elForm.elements
      ? (typeof elForm.elements.test == "undefined")
      : null;
    root.removeChild(elForm);
    elForm = elInput = null;
    return isBuggy;
  })();

  var element = global.Element;
  global.Element = function(tagName, attributes) {
    attributes = attributes || { };
    tagName = tagName.toLowerCase();
    var cache = Element.cache;
    if (SETATTRIBUTE_IGNORES_NAME && attributes.name) {
      tagName = '<' + tagName + ' name="' + attributes.name + '">';
      delete attributes.name;
      return Element.writeAttribute(document.createElement(tagName), attributes);
    }
    if (!cache[tagName]) cache[tagName] = Element.extend(document.createElement(tagName));
    return Element.writeAttribute(cache[tagName].cloneNode(false), attributes);
  };
  Object.extend(global.Element, element || { });
  if (element) global.Element.prototype = element.prototype;
})(this);

Element.cache = { };
Element.idCounter = 1;

Element.Methods = {
  visible: function(element) {
    return $(element).style.display != 'none';
  },

  toggle: function(element) {
    element = $(element);
    Element[Element.visible(element) ? 'hide' : 'show'](element);
    return element;
  },


  hide: function(element) {
    element = $(element);
    element.style.display = 'none';
    return element;
  },

  show: function(element) {
    element = $(element);
    element.style.display = '';
    return element;
  },

  remove: function(element) {
    element = $(element);
    element.parentNode.removeChild(element);
    return element;
  },

  update: (function(){

    var SELECT_ELEMENT_INNERHTML_BUGGY = (function(){
      var el = document.createElement("select"),
          isBuggy = true;
      el.innerHTML = "<option value=\"test\">test</option>";
      if (el.options && el.options[0]) {
        isBuggy = el.options[0].nodeName.toUpperCase() !== "OPTION";
      }
      el = null;
      return isBuggy;
    })();

    var TABLE_ELEMENT_INNERHTML_BUGGY = (function(){
      try {
        var el = document.createElement("table");
        if (el && el.tBodies) {
          el.innerHTML = "<tbody><tr><td>test</td></tr></tbody>";
          var isBuggy = typeof el.tBodies[0] == "undefined";
          el = null;
          return isBuggy;
        }
      } catch (e) {
        return true;
      }
    })();

    var SCRIPT_ELEMENT_REJECTS_TEXTNODE_APPENDING = (function () {
      var s = document.createElement("script"),
          isBuggy = false;
      try {
        s.appendChild(document.createTextNode(""));
        isBuggy = !s.firstChild ||
          s.firstChild && s.firstChild.nodeType !== 3;
      } catch (e) {
        isBuggy = true;
      }
      s = null;
      return isBuggy;
    })();

    function update(element, content) {
      element = $(element);

      if (content && content.toElement)
        content = content.toElement();

      if (Object.isElement(content))
        return element.update().insert(content);

      content = Object.toHTML(content);

      var tagName = element.tagName.toUpperCase();

      if (tagName === 'SCRIPT' && SCRIPT_ELEMENT_REJECTS_TEXTNODE_APPENDING) {
        element.text = content;
        return element;
      }

      if (SELECT_ELEMENT_INNERHTML_BUGGY || TABLE_ELEMENT_INNERHTML_BUGGY) {
        if (tagName in Element._insertionTranslations.tags) {
          while (element.firstChild) {
            element.removeChild(element.firstChild);
          }
          Element._getContentFromAnonymousElement(tagName, content.stripScripts())
            .each(function(node) {
              element.appendChild(node)
            });
        }
        else {
          element.innerHTML = content.stripScripts();
        }
      }
      else {
        element.innerHTML = content.stripScripts();
      }

      content.evalScripts.bind(content).defer();
      return element;
    }

    return update;
  })(),

  replace: function(element, content) {
    element = $(element);
    if (content && content.toElement) content = content.toElement();
    else if (!Object.isElement(content)) {
      content = Object.toHTML(content);
      var range = element.ownerDocument.createRange();
      range.selectNode(element);
      content.evalScripts.bind(content).defer();
      content = range.createContextualFragment(content.stripScripts());
    }
    element.parentNode.replaceChild(content, element);
    return element;
  },

  insert: function(element, insertions) {
    element = $(element);

    if (Object.isString(insertions) || Object.isNumber(insertions) ||
        Object.isElement(insertions) || (insertions && (insertions.toElement || insertions.toHTML)))
          insertions = {bottom:insertions};

    var content, insert, tagName, childNodes;

    for (var position in insertions) {
      content  = insertions[position];
      position = position.toLowerCase();
      insert = Element._insertionTranslations[position];

      if (content && content.toElement) content = content.toElement();
      if (Object.isElement(content)) {
        insert(element, content);
        continue;
      }

      content = Object.toHTML(content);

      tagName = ((position == 'before' || position == 'after')
        ? element.parentNode : element).tagName.toUpperCase();

      childNodes = Element._getContentFromAnonymousElement(tagName, content.stripScripts());

      if (position == 'top' || position == 'after') childNodes.reverse();
      childNodes.each(insert.curry(element));

      content.evalScripts.bind(content).defer();
    }

    return element;
  },

  wrap: function(element, wrapper, attributes) {
    element = $(element);
    if (Object.isElement(wrapper))
      $(wrapper).writeAttribute(attributes || { });
    else if (Object.isString(wrapper)) wrapper = new Element(wrapper, attributes);
    else wrapper = new Element('div', wrapper);
    if (element.parentNode)
      element.parentNode.replaceChild(wrapper, element);
    wrapper.appendChild(element);
    return wrapper;
  },

  inspect: function(element) {
    element = $(element);
    var result = '<' + element.tagName.toLowerCase();
    $H({'id': 'id', 'className': 'class'}).each(function(pair) {
      var property = pair.first(), attribute = pair.last();
      var value = (element[property] || '').toString();
      if (value) result += ' ' + attribute + '=' + value.inspect(true);
    });
    return result + '>';
  },

  recursivelyCollect: function(element, property) {
    element = $(element);
    var elements = [];
    while (element = element[property])
      if (element.nodeType == 1)
        elements.push(Element.extend(element));
    return elements;
  },

  ancestors: function(element) {
    return Element.recursivelyCollect(element, 'parentNode');
  },

  descendants: function(element) {
    return Element.select(element, "*");
  },

  firstDescendant: function(element) {
    element = $(element).firstChild;
    while (element && element.nodeType != 1) element = element.nextSibling;
    return $(element);
  },

  immediateDescendants: function(element) {
    if (!(element = $(element).firstChild)) return [];
    while (element && element.nodeType != 1) element = element.nextSibling;
    if (element) return [element].concat($(element).nextSiblings());
    return [];
  },

  previousSiblings: function(element) {
    return Element.recursivelyCollect(element, 'previousSibling');
  },

  nextSiblings: function(element) {
    return Element.recursivelyCollect(element, 'nextSibling');
  },

  siblings: function(element) {
    element = $(element);
    return Element.previousSiblings(element).reverse()
      .concat(Element.nextSiblings(element));
  },

  match: function(element, selector) {
    if (Object.isString(selector))
      selector = new Selector(selector);
    return selector.match($(element));
  },

  up: function(element, expression, index) {
    element = $(element);
    if (arguments.length == 1) return $(element.parentNode);
    var ancestors = Element.ancestors(element);
    return Object.isNumber(expression) ? ancestors[expression] :
      Selector.findElement(ancestors, expression, index);
  },

  down: function(element, expression, index) {
    element = $(element);
    if (arguments.length == 1) return Element.firstDescendant(element);
    return Object.isNumber(expression) ? Element.descendants(element)[expression] :
      Element.select(element, expression)[index || 0];
  },

  previous: function(element, expression, index) {
    element = $(element);
    if (arguments.length == 1) return $(Selector.handlers.previousElementSibling(element));
    var previousSiblings = Element.previousSiblings(element);
    return Object.isNumber(expression) ? previousSiblings[expression] :
      Selector.findElement(previousSiblings, expression, index);
  },

  next: function(element, expression, index) {
    element = $(element);
    if (arguments.length == 1) return $(Selector.handlers.nextElementSibling(element));
    var nextSiblings = Element.nextSiblings(element);
    return Object.isNumber(expression) ? nextSiblings[expression] :
      Selector.findElement(nextSiblings, expression, index);
  },


  select: function(element) {
    var args = Array.prototype.slice.call(arguments, 1);
    return Selector.findChildElements(element, args);
  },

  adjacent: function(element) {
    var args = Array.prototype.slice.call(arguments, 1);
    return Selector.findChildElements(element.parentNode, args).without(element);
  },

  identify: function(element) {
    element = $(element);
    var id = Element.readAttribute(element, 'id');
    if (id) return id;
    do { id = 'anonymous_element_' + Element.idCounter++ } while ($(id));
    Element.writeAttribute(element, 'id', id);
    return id;
  },

  readAttribute: function(element, name) {
    element = $(element);
    if (Prototype.Browser.IE) {
      var t = Element._attributeTranslations.read;
      if (t.values[name]) return t.values[name](element, name);
      if (t.names[name]) name = t.names[name];
      if (name.include(':')) {
        return (!element.attributes || !element.attributes[name]) ? null :
         element.attributes[name].value;
      }
    }
    return element.getAttribute(name);
  },

  writeAttribute: function(element, name, value) {
    element = $(element);
    var attributes = { }, t = Element._attributeTranslations.write;

    if (typeof name == 'object') attributes = name;
    else attributes[name] = Object.isUndefined(value) ? true : value;

    for (var attr in attributes) {
      name = t.names[attr] || attr;
      value = attributes[attr];
      if (t.values[attr]) name = t.values[attr](element, value);
      if (value === false || value === null)
        element.removeAttribute(name);
      else if (value === true)
        element.setAttribute(name, name);
      else element.setAttribute(name, value);
    }
    return element;
  },

  getHeight: function(element) {
    return Element.getDimensions(element).height;
  },

  getWidth: function(element) {
    return Element.getDimensions(element).width;
  },

  classNames: function(element) {
    return new Element.ClassNames(element);
  },

  hasClassName: function(element, className) {
    if (!(element = $(element))) return;
    var elementClassName = element.className;
    return (elementClassName.length > 0 && (elementClassName == className ||
      new RegExp("(^|\\s)" + className + "(\\s|$)").test(elementClassName)));
  },

  addClassName: function(element, className) {
    if (!(element = $(element))) return;
    if (!Element.hasClassName(element, className))
      element.className += (element.className ? ' ' : '') + className;
    return element;
  },

  removeClassName: function(element, className) {
    if (!(element = $(element))) return;
    element.className = element.className.replace(
      new RegExp("(^|\\s+)" + className + "(\\s+|$)"), ' ').strip();
    return element;
  },

  toggleClassName: function(element, className) {
    if (!(element = $(element))) return;
    return Element[Element.hasClassName(element, className) ?
      'removeClassName' : 'addClassName'](element, className);
  },

  cleanWhitespace: function(element) {
    element = $(element);
    var node = element.firstChild;
    while (node) {
      var nextNode = node.nextSibling;
      if (node.nodeType == 3 && !/\S/.test(node.nodeValue))
        element.removeChild(node);
      node = nextNode;
    }
    return element;
  },

  empty: function(element) {
    return $(element).innerHTML.blank();
  },

  descendantOf: function(element, ancestor) {
    element = $(element), ancestor = $(ancestor);

    if (element.compareDocumentPosition)
      return (element.compareDocumentPosition(ancestor) & 8) === 8;

    if (ancestor.contains)
      return ancestor.contains(element) && ancestor !== element;

    while (element = element.parentNode)
      if (element == ancestor) return true;

    return false;
  },

  scrollTo: function(element) {
    element = $(element);
    var pos = Element.cumulativeOffset(element);
    window.scrollTo(pos[0], pos[1]);
    return element;
  },

  getStyle: function(element, style) {
    element = $(element);
    style = style == 'float' ? 'cssFloat' : style.camelize();
    var value = element.style[style];
    if (!value || value == 'auto') {
      var css = document.defaultView.getComputedStyle(element, null);
      value = css ? css[style] : null;
    }
    if (style == 'opacity') return value ? parseFloat(value) : 1.0;
    return value == 'auto' ? null : value;
  },

  getOpacity: function(element) {
    return $(element).getStyle('opacity');
  },

  setStyle: function(element, styles) {
    element = $(element);
    var elementStyle = element.style, match;
    if (Object.isString(styles)) {
      element.style.cssText += ';' + styles;
      return styles.include('opacity') ?
        element.setOpacity(styles.match(/opacity:\s*(\d?\.?\d*)/)[1]) : element;
    }
    for (var property in styles)
      if (property == 'opacity') element.setOpacity(styles[property]);
      else
        elementStyle[(property == 'float' || property == 'cssFloat') ?
          (Object.isUndefined(elementStyle.styleFloat) ? 'cssFloat' : 'styleFloat') :
            property] = styles[property];

    return element;
  },

  setOpacity: function(element, value) {
    element = $(element);
    element.style.opacity = (value == 1 || value === '') ? '' :
      (value < 0.00001) ? 0 : value;
    return element;
  },

  getDimensions: function(element) {
    element = $(element);
    var display = Element.getStyle(element, 'display');
    if (display != 'none' && display != null) // Safari bug
      return {width: element.offsetWidth, height: element.offsetHeight};

    var els = element.style;
    var originalVisibility = els.visibility;
    var originalPosition = els.position;
    var originalDisplay = els.display;
    els.visibility = 'hidden';
    if (originalPosition != 'fixed') // Switching fixed to absolute causes issues in Safari
      els.position = 'absolute';
    els.display = 'block';
    var originalWidth = element.clientWidth;
    var originalHeight = element.clientHeight;
    els.display = originalDisplay;
    els.position = originalPosition;
    els.visibility = originalVisibility;
    return {width: originalWidth, height: originalHeight};
  },

  makePositioned: function(element) {
    element = $(element);
    var pos = Element.getStyle(element, 'position');
    if (pos == 'static' || !pos) {
      element._madePositioned = true;
      element.style.position = 'relative';
      if (Prototype.Browser.Opera) {
        element.style.top = 0;
        element.style.left = 0;
      }
    }
    return element;
  },

  undoPositioned: function(element) {
    element = $(element);
    if (element._madePositioned) {
      element._madePositioned = undefined;
      element.style.position =
        element.style.top =
        element.style.left =
        element.style.bottom =
        element.style.right = '';
    }
    return element;
  },

  makeClipping: function(element) {
    element = $(element);
    if (element._overflow) return element;
    element._overflow = Element.getStyle(element, 'overflow') || 'auto';
    if (element._overflow !== 'hidden')
      element.style.overflow = 'hidden';
    return element;
  },

  undoClipping: function(element) {
    element = $(element);
    if (!element._overflow) return element;
    element.style.overflow = element._overflow == 'auto' ? '' : element._overflow;
    element._overflow = null;
    return element;
  },

  cumulativeOffset: function(element) {
    var valueT = 0, valueL = 0;
    do {
      valueT += element.offsetTop  || 0;
      valueL += element.offsetLeft || 0;
      element = element.offsetParent;
    } while (element);
    return Element._returnOffset(valueL, valueT);
  },

  positionedOffset: function(element) {
    var valueT = 0, valueL = 0;
    do {
      valueT += element.offsetTop  || 0;
      valueL += element.offsetLeft || 0;
      element = element.offsetParent;
      if (element) {
        if (element.tagName.toUpperCase() == 'BODY') break;
        var p = Element.getStyle(element, 'position');
        if (p !== 'static') break;
      }
    } while (element);
    return Element._returnOffset(valueL, valueT);
  },

  absolutize: function(element) {
    element = $(element);
    if (Element.getStyle(element, 'position') == 'absolute') return element;

    var offsets = Element.positionedOffset(element);
    var top     = offsets[1];
    var left    = offsets[0];
    var width   = element.clientWidth;
    var height  = element.clientHeight;

    element._originalLeft   = left - parseFloat(element.style.left  || 0);
    element._originalTop    = top  - parseFloat(element.style.top || 0);
    element._originalWidth  = element.style.width;
    element._originalHeight = element.style.height;

    element.style.position = 'absolute';
    element.style.top    = top + 'px';
    element.style.left   = left + 'px';
    element.style.width  = width + 'px';
    element.style.height = height + 'px';
    return element;
  },

  relativize: function(element) {
    element = $(element);
    if (Element.getStyle(element, 'position') == 'relative') return element;

    element.style.position = 'relative';
    var top  = parseFloat(element.style.top  || 0) - (element._originalTop || 0);
    var left = parseFloat(element.style.left || 0) - (element._originalLeft || 0);

    element.style.top    = top + 'px';
    element.style.left   = left + 'px';
    element.style.height = element._originalHeight;
    element.style.width  = element._originalWidth;
    return element;
  },

  cumulativeScrollOffset: function(element) {
    var valueT = 0, valueL = 0;
    do {
      valueT += element.scrollTop  || 0;
      valueL += element.scrollLeft || 0;
      element = element.parentNode;
    } while (element);
    return Element._returnOffset(valueL, valueT);
  },

  getOffsetParent: function(element) {
    if (element.offsetParent) return $(element.offsetParent);
    if (element == document.body) return $(element);

    while ((element = element.parentNode) && element != document.body)
      if (Element.getStyle(element, 'position') != 'static')
        return $(element);

    return $(document.body);
  },

  viewportOffset: function(forElement) {
    var valueT = 0, valueL = 0;

    var element = forElement;
    do {
      valueT += element.offsetTop  || 0;
      valueL += element.offsetLeft || 0;

      if (element.offsetParent == document.body &&
        Element.getStyle(element, 'position') == 'absolute') break;

    } while (element = element.offsetParent);

    element = forElement;
    do {
      if (!Prototype.Browser.Opera || (element.tagName && (element.tagName.toUpperCase() == 'BODY'))) {
        valueT -= element.scrollTop  || 0;
        valueL -= element.scrollLeft || 0;
      }
    } while (element = element.parentNode);

    return Element._returnOffset(valueL, valueT);
  },

  clonePosition: function(element, source) {
    var options = Object.extend({
      setLeft:    true,
      setTop:     true,
      setWidth:   true,
      setHeight:  true,
      offsetTop:  0,
      offsetLeft: 0
    }, arguments[2] || { });

    source = $(source);
    var p = Element.viewportOffset(source);

    element = $(element);
    var delta = [0, 0];
    var parent = null;
    if (Element.getStyle(element, 'position') == 'absolute') {
      parent = Element.getOffsetParent(element);
      delta = Element.viewportOffset(parent);
    }

    if (parent == document.body) {
      delta[0] -= document.body.offsetLeft;
      delta[1] -= document.body.offsetTop;
    }

    if (options.setLeft)   element.style.left  = (p[0] - delta[0] + options.offsetLeft) + 'px';
    if (options.setTop)    element.style.top   = (p[1] - delta[1] + options.offsetTop) + 'px';
    if (options.setWidth)  element.style.width = source.offsetWidth + 'px';
    if (options.setHeight) element.style.height = source.offsetHeight + 'px';
    return element;
  }
};

Object.extend(Element.Methods, {
  getElementsBySelector: Element.Methods.select,

  childElements: Element.Methods.immediateDescendants
});

Element._attributeTranslations = {
  write: {
    names: {
      className: 'class',
      htmlFor:   'for'
    },
    values: { }
  }
};

if (Prototype.Browser.Opera) {
  Element.Methods.getStyle = Element.Methods.getStyle.wrap(
    function(proceed, element, style) {
      switch (style) {
        case 'left': case 'top': case 'right': case 'bottom':
          if (proceed(element, 'position') === 'static') return null;
        case 'height': case 'width':
          if (!Element.visible(element)) return null;

          var dim = parseInt(proceed(element, style), 10);

          if (dim !== element['offset' + style.capitalize()])
            return dim + 'px';

          var properties;
          if (style === 'height') {
            properties = ['border-top-width', 'padding-top',
             'padding-bottom', 'border-bottom-width'];
          }
          else {
            properties = ['border-left-width', 'padding-left',
             'padding-right', 'border-right-width'];
          }
          return properties.inject(dim, function(memo, property) {
            var val = proceed(element, property);
            return val === null ? memo : memo - parseInt(val, 10);
          }) + 'px';
        default: return proceed(element, style);
      }
    }
  );

  Element.Methods.readAttribute = Element.Methods.readAttribute.wrap(
    function(proceed, element, attribute) {
      if (attribute === 'title') return element.title;
      return proceed(element, attribute);
    }
  );
}

else if (Prototype.Browser.IE) {
  Element.Methods.getOffsetParent = Element.Methods.getOffsetParent.wrap(
    function(proceed, element) {
      element = $(element);
      try { element.offsetParent }
      catch(e) { return $(document.body) }
      var position = element.getStyle('position');
      if (position !== 'static') return proceed(element);
      element.setStyle({ position: 'relative' });
      var value = proceed(element);
      element.setStyle({ position: position });
      return value;
    }
  );

  $w('positionedOffset viewportOffset').each(function(method) {
    Element.Methods[method] = Element.Methods[method].wrap(
      function(proceed, element) {
        element = $(element);
        try { element.offsetParent }
        catch(e) { return Element._returnOffset(0,0) }
        var position = element.getStyle('position');
        if (position !== 'static') return proceed(element);
        var offsetParent = element.getOffsetParent();
        if (offsetParent && offsetParent.getStyle('position') === 'fixed')
          offsetParent.setStyle({ zoom: 1 });
        element.setStyle({ position: 'relative' });
        var value = proceed(element);
        element.setStyle({ position: position });
        return value;
      }
    );
  });

  Element.Methods.cumulativeOffset = Element.Methods.cumulativeOffset.wrap(
    function(proceed, element) {
      try { element.offsetParent }
      catch(e) { return Element._returnOffset(0,0) }
      return proceed(element);
    }
  );

  Element.Methods.getStyle = function(element, style) {
    element = $(element);
    style = (style == 'float' || style == 'cssFloat') ? 'styleFloat' : style.camelize();
    var value = element.style[style];
    if (!value && element.currentStyle) value = element.currentStyle[style];

    if (style == 'opacity') {
      if (value = (element.getStyle('filter') || '').match(/alpha\(opacity=(.*)\)/))
        if (value[1]) return parseFloat(value[1]) / 100;
      return 1.0;
    }

    if (value == 'auto') {
      if ((style == 'width' || style == 'height') && (element.getStyle('display') != 'none'))
        return element['offset' + style.capitalize()] + 'px';
      return null;
    }
    return value;
  };

  Element.Methods.setOpacity = function(element, value) {
    function stripAlpha(filter){
      return filter.replace(/alpha\([^\)]*\)/gi,'');
    }
    element = $(element);
    var currentStyle = element.currentStyle;
    if ((currentStyle && !currentStyle.hasLayout) ||
      (!currentStyle && element.style.zoom == 'normal'))
        element.style.zoom = 1;

    var filter = element.getStyle('filter'), style = element.style;
    if (value == 1 || value === '') {
      (filter = stripAlpha(filter)) ?
        style.filter = filter : style.removeAttribute('filter');
      return element;
    } else if (value < 0.00001) value = 0;
    style.filter = stripAlpha(filter) +
      'alpha(opacity=' + (value * 100) + ')';
    return element;
  };

  Element._attributeTranslations = (function(){

    var classProp = 'className';
    var forProp = 'for';

    var el = document.createElement('div');

    el.setAttribute(classProp, 'x');

    if (el.className !== 'x') {
      el.setAttribute('class', 'x');
      if (el.className === 'x') {
        classProp = 'class';
      }
    }
    el = null;

    el = document.createElement('label');
    el.setAttribute(forProp, 'x');
    if (el.htmlFor !== 'x') {
      el.setAttribute('htmlFor', 'x');
      if (el.htmlFor === 'x') {
        forProp = 'htmlFor';
      }
    }
    el = null;

    return {
      read: {
        names: {
          'class':      classProp,
          'className':  classProp,
          'for':        forProp,
          'htmlFor':    forProp
        },
        values: {
          _getAttr: function(element, attribute) {
            return element.getAttribute(attribute);
          },
          _getAttr2: function(element, attribute) {
            return element.getAttribute(attribute, 2);
          },
          _getAttrNode: function(element, attribute) {
            var node = element.getAttributeNode(attribute);
            return node ? node.value : "";
          },
          _getEv: (function(){

            var el = document.createElement('div');
            el.onclick = Prototype.emptyFunction;
            var value = el.getAttribute('onclick');
            var f;

            if (String(value).indexOf('{') > -1) {
              f = function(element, attribute) {
                attribute = element.getAttribute(attribute);
                if (!attribute) return null;
                attribute = attribute.toString();
                attribute = attribute.split('{')[1];
                attribute = attribute.split('}')[0];
                return attribute.strip();
              };
            }
            else if (value === '') {
              f = function(element, attribute) {
                attribute = element.getAttribute(attribute);
                if (!attribute) return null;
                return attribute.strip();
              };
            }
            el = null;
            return f;
          })(),
          _flag: function(element, attribute) {
            return $(element).hasAttribute(attribute) ? attribute : null;
          },
          style: function(element) {
            return element.style.cssText.toLowerCase();
          },
          title: function(element) {
            return element.title;
          }
        }
      }
    }
  })();

  Element._attributeTranslations.write = {
    names: Object.extend({
      cellpadding: 'cellPadding',
      cellspacing: 'cellSpacing'
    }, Element._attributeTranslations.read.names),
    values: {
      checked: function(element, value) {
        element.checked = !!value;
      },

      style: function(element, value) {
        element.style.cssText = value ? value : '';
      }
    }
  };

  Element._attributeTranslations.has = {};

  $w('colSpan rowSpan vAlign dateTime accessKey tabIndex ' +
      'encType maxLength readOnly longDesc frameBorder').each(function(attr) {
    Element._attributeTranslations.write.names[attr.toLowerCase()] = attr;
    Element._attributeTranslations.has[attr.toLowerCase()] = attr;
  });

  (function(v) {
    Object.extend(v, {
      href:        v._getAttr2,
      src:         v._getAttr2,
      type:        v._getAttr,
      action:      v._getAttrNode,
      disabled:    v._flag,
      checked:     v._flag,
      readonly:    v._flag,
      multiple:    v._flag,
      onload:      v._getEv,
      onunload:    v._getEv,
      onclick:     v._getEv,
      ondblclick:  v._getEv,
      onmousedown: v._getEv,
      onmouseup:   v._getEv,
      onmouseover: v._getEv,
      onmousemove: v._getEv,
      onmouseout:  v._getEv,
      onfocus:     v._getEv,
      onblur:      v._getEv,
      onkeypress:  v._getEv,
      onkeydown:   v._getEv,
      onkeyup:     v._getEv,
      onsubmit:    v._getEv,
      onreset:     v._getEv,
      onselect:    v._getEv,
      onchange:    v._getEv
    });
  })(Element._attributeTranslations.read.values);

  if (Prototype.BrowserFeatures.ElementExtensions) {
    (function() {
      function _descendants(element) {
        var nodes = element.getElementsByTagName('*'), results = [];
        for (var i = 0, node; node = nodes[i]; i++)
          if (node.tagName !== "!") // Filter out comment nodes.
            results.push(node);
        return results;
      }

      Element.Methods.down = function(element, expression, index) {
        element = $(element);
        if (arguments.length == 1) return element.firstDescendant();
        return Object.isNumber(expression) ? _descendants(element)[expression] :
          Element.select(element, expression)[index || 0];
      }
    })();
  }

}

else if (Prototype.Browser.Gecko && /rv:1\.8\.0/.test(navigator.userAgent)) {
  Element.Methods.setOpacity = function(element, value) {
    element = $(element);
    element.style.opacity = (value == 1) ? 0.999999 :
      (value === '') ? '' : (value < 0.00001) ? 0 : value;
    return element;
  };
}

else if (Prototype.Browser.WebKit) {
  Element.Methods.setOpacity = function(element, value) {
    element = $(element);
    element.style.opacity = (value == 1 || value === '') ? '' :
      (value < 0.00001) ? 0 : value;

    if (value == 1)
      if(element.tagName.toUpperCase() == 'IMG' && element.width) {
        element.width++; element.width--;
      } else try {
        var n = document.createTextNode(' ');
        element.appendChild(n);
        element.removeChild(n);
      } catch (e) { }

    return element;
  };

  Element.Methods.cumulativeOffset = function(element) {
    var valueT = 0, valueL = 0;
    do {
      valueT += element.offsetTop  || 0;
      valueL += element.offsetLeft || 0;
      if (element.offsetParent == document.body)
        if (Element.getStyle(element, 'position') == 'absolute') break;

      element = element.offsetParent;
    } while (element);

    return Element._returnOffset(valueL, valueT);
  };
}

if ('outerHTML' in document.documentElement) {
  Element.Methods.replace = function(element, content) {
    element = $(element);

    if (content && content.toElement) content = content.toElement();
    if (Object.isElement(content)) {
      element.parentNode.replaceChild(content, element);
      return element;
    }

    content = Object.toHTML(content);
    var parent = element.parentNode, tagName = parent.tagName.toUpperCase();

    if (Element._insertionTranslations.tags[tagName]) {
      var nextSibling = element.next();
      var fragments = Element._getContentFromAnonymousElement(tagName, content.stripScripts());
      parent.removeChild(element);
      if (nextSibling)
        fragments.each(function(node) { parent.insertBefore(node, nextSibling) });
      else
        fragments.each(function(node) { parent.appendChild(node) });
    }
    else element.outerHTML = content.stripScripts();

    content.evalScripts.bind(content).defer();
    return element;
  };
}

Element._returnOffset = function(l, t) {
  var result = [l, t];
  result.left = l;
  result.top = t;
  return result;
};

Element._getContentFromAnonymousElement = function(tagName, html) {
  var div = new Element('div'), t = Element._insertionTranslations.tags[tagName];
  if (t) {
    div.innerHTML = t[0] + html + t[1];
    t[2].times(function() { div = div.firstChild });
  } else div.innerHTML = html;
  return $A(div.childNodes);
};

Element._insertionTranslations = {
  before: function(element, node) {
    element.parentNode.insertBefore(node, element);
  },
  top: function(element, node) {
    element.insertBefore(node, element.firstChild);
  },
  bottom: function(element, node) {
    element.appendChild(node);
  },
  after: function(element, node) {
    element.parentNode.insertBefore(node, element.nextSibling);
  },
  tags: {
    TABLE:  ['<table>',                '</table>',                   1],
    TBODY:  ['<table><tbody>',         '</tbody></table>',           2],
    TR:     ['<table><tbody><tr>',     '</tr></tbody></table>',      3],
    TD:     ['<table><tbody><tr><td>', '</td></tr></tbody></table>', 4],
    SELECT: ['<select>',               '</select>',                  1]
  }
};

(function() {
  var tags = Element._insertionTranslations.tags;
  Object.extend(tags, {
    THEAD: tags.TBODY,
    TFOOT: tags.TBODY,
    TH:    tags.TD
  });
})();

Element.Methods.Simulated = {
  hasAttribute: function(element, attribute) {
    attribute = Element._attributeTranslations.has[attribute] || attribute;
    var node = $(element).getAttributeNode(attribute);
    return !!(node && node.specified);
  }
};

Element.Methods.ByTag = { };

Object.extend(Element, Element.Methods);

(function(div) {

  if (!Prototype.BrowserFeatures.ElementExtensions && div['__proto__']) {
    window.HTMLElement = { };
    window.HTMLElement.prototype = div['__proto__'];
    Prototype.BrowserFeatures.ElementExtensions = true;
  }

  div = null;

})(document.createElement('div'))

Element.extend = (function() {

  function checkDeficiency(tagName) {
    if (typeof window.Element != 'undefined') {
      var proto = window.Element.prototype;
      if (proto) {
        var id = '_' + (Math.random()+'').slice(2);
        var el = document.createElement(tagName);
        proto[id] = 'x';
        var isBuggy = (el[id] !== 'x');
        delete proto[id];
        el = null;
        return isBuggy;
      }
    }
    return false;
  }

  function extendElementWith(element, methods) {
    for (var property in methods) {
      var value = methods[property];
      if (Object.isFunction(value) && !(property in element))
        element[property] = value.methodize();
    }
  }

  var HTMLOBJECTELEMENT_PROTOTYPE_BUGGY = checkDeficiency('object');

  if (Prototype.BrowserFeatures.SpecificElementExtensions) {
    if (HTMLOBJECTELEMENT_PROTOTYPE_BUGGY) {
      return function(element) {
        if (element && typeof element._extendedByPrototype == 'undefined') {
          var t = element.tagName;
          if (t && (/^(?:object|applet|embed)$/i.test(t))) {
            extendElementWith(element, Element.Methods);
            extendElementWith(element, Element.Methods.Simulated);
            extendElementWith(element, Element.Methods.ByTag[t.toUpperCase()]);
          }
        }
        return element;
      }
    }
    return Prototype.K;
  }

  var Methods = { }, ByTag = Element.Methods.ByTag;

  var extend = Object.extend(function(element) {
    if (!element || typeof element._extendedByPrototype != 'undefined' ||
        element.nodeType != 1 || element == window) return element;

    var methods = Object.clone(Methods),
        tagName = element.tagName.toUpperCase();

    if (ByTag[tagName]) Object.extend(methods, ByTag[tagName]);

    extendElementWith(element, methods);

    element._extendedByPrototype = Prototype.emptyFunction;
    return element;

  }, {
    refresh: function() {
      if (!Prototype.BrowserFeatures.ElementExtensions) {
        Object.extend(Methods, Element.Methods);
        Object.extend(Methods, Element.Methods.Simulated);
      }
    }
  });

  extend.refresh();
  return extend;
})();

Element.hasAttribute = function(element, attribute) {
  if (element.hasAttribute) return element.hasAttribute(attribute);
  return Element.Methods.Simulated.hasAttribute(element, attribute);
};

Element.addMethods = function(methods) {
  var F = Prototype.BrowserFeatures, T = Element.Methods.ByTag;

  if (!methods) {
    Object.extend(Form, Form.Methods);
    Object.extend(Form.Element, Form.Element.Methods);
    Object.extend(Element.Methods.ByTag, {
      "FORM":     Object.clone(Form.Methods),
      "INPUT":    Object.clone(Form.Element.Methods),
      "SELECT":   Object.clone(Form.Element.Methods),
      "TEXTAREA": Object.clone(Form.Element.Methods)
    });
  }

  if (arguments.length == 2) {
    var tagName = methods;
    methods = arguments[1];
  }

  if (!tagName) Object.extend(Element.Methods, methods || { });
  else {
    if (Object.isArray(tagName)) tagName.each(extend);
    else extend(tagName);
  }

  function extend(tagName) {
    tagName = tagName.toUpperCase();
    if (!Element.Methods.ByTag[tagName])
      Element.Methods.ByTag[tagName] = { };
    Object.extend(Element.Methods.ByTag[tagName], methods);
  }

  function copy(methods, destination, onlyIfAbsent) {
    onlyIfAbsent = onlyIfAbsent || false;
    for (var property in methods) {
      var value = methods[property];
      if (!Object.isFunction(value)) continue;
      if (!onlyIfAbsent || !(property in destination))
        destination[property] = value.methodize();
    }
  }

  function findDOMClass(tagName) {
    var klass;
    var trans = {
      "OPTGROUP": "OptGroup", "TEXTAREA": "TextArea", "P": "Paragraph",
      "FIELDSET": "FieldSet", "UL": "UList", "OL": "OList", "DL": "DList",
      "DIR": "Directory", "H1": "Heading", "H2": "Heading", "H3": "Heading",
      "H4": "Heading", "H5": "Heading", "H6": "Heading", "Q": "Quote",
      "INS": "Mod", "DEL": "Mod", "A": "Anchor", "IMG": "Image", "CAPTION":
      "TableCaption", "COL": "TableCol", "COLGROUP": "TableCol", "THEAD":
      "TableSection", "TFOOT": "TableSection", "TBODY": "TableSection", "TR":
      "TableRow", "TH": "TableCell", "TD": "TableCell", "FRAMESET":
      "FrameSet", "IFRAME": "IFrame"
    };
    if (trans[tagName]) klass = 'HTML' + trans[tagName] + 'Element';
    if (window[klass]) return window[klass];
    klass = 'HTML' + tagName + 'Element';
    if (window[klass]) return window[klass];
    klass = 'HTML' + tagName.capitalize() + 'Element';
    if (window[klass]) return window[klass];

    var element = document.createElement(tagName);
    var proto = element['__proto__'] || element.constructor.prototype;
    element = null;
    return proto;
  }

  var elementPrototype = window.HTMLElement ? HTMLElement.prototype :
   Element.prototype;

  if (F.ElementExtensions) {
    copy(Element.Methods, elementPrototype);
    copy(Element.Methods.Simulated, elementPrototype, true);
  }

  if (F.SpecificElementExtensions) {
    for (var tag in Element.Methods.ByTag) {
      var klass = findDOMClass(tag);
      if (Object.isUndefined(klass)) continue;
      copy(T[tag], klass.prototype);
    }
  }

  Object.extend(Element, Element.Methods);
  delete Element.ByTag;

  if (Element.extend.refresh) Element.extend.refresh();
  Element.cache = { };
};


document.viewport = {

  getDimensions: function() {
    return { width: this.getWidth(), height: this.getHeight() };
  },

  getScrollOffsets: function() {
    return Element._returnOffset(
      window.pageXOffset || document.documentElement.scrollLeft || document.body.scrollLeft,
      window.pageYOffset || document.documentElement.scrollTop  || document.body.scrollTop);
  }
};

(function(viewport) {
  var B = Prototype.Browser, doc = document, element, property = {};

  function getRootElement() {
    if (B.WebKit && !doc.evaluate)
      return document;

    if (B.Opera && window.parseFloat(window.opera.version()) < 9.5)
      return document.body;

    return document.documentElement;
  }

  function define(D) {
    if (!element) element = getRootElement();

    property[D] = 'client' + D;

    viewport['get' + D] = function() { return element[property[D]] };
    return viewport['get' + D]();
  }

  viewport.getWidth  = define.curry('Width');

  viewport.getHeight = define.curry('Height');
})(document.viewport);


Element.Storage = {
  UID: 1
};

Element.addMethods({
  getStorage: function(element) {
    if (!(element = $(element))) return;

    var uid;
    if (element === window) {
      uid = 0;
    } else {
      if (typeof element._prototypeUID === "undefined")
        element._prototypeUID = [Element.Storage.UID++];
      uid = element._prototypeUID[0];
    }

    if (!Element.Storage[uid])
      Element.Storage[uid] = $H();

    return Element.Storage[uid];
  },

  store: function(element, key, value) {
    if (!(element = $(element))) return;

    if (arguments.length === 2) {
      Element.getStorage(element).update(key);
    } else {
      Element.getStorage(element).set(key, value);
    }

    return element;
  },

  retrieve: function(element, key, defaultValue) {
    if (!(element = $(element))) return;
    var hash = Element.getStorage(element), value = hash.get(key);

    if (Object.isUndefined(value)) {
      hash.set(key, defaultValue);
      value = defaultValue;
    }

    return value;
  },

  clone: function(element, deep) {
    if (!(element = $(element))) return;
    var clone = element.cloneNode(deep);
    clone._prototypeUID = void 0;
    if (deep) {
      var descendants = Element.select(clone, '*'),
          i = descendants.length;
      while (i--) {
        descendants[i]._prototypeUID = void 0;
      }
    }
    return Element.extend(clone);
  }
});
/* Portions of the Selector class are derived from Jack Slocum's DomQuery,
 * part of YUI-Ext version 0.40, distributed under the terms of an MIT-style
 * license.  Please see http://www.yui-ext.com/ for more information. */

var Selector = Class.create({
  initialize: function(expression) {
    this.expression = expression.strip();

    if (this.shouldUseSelectorsAPI()) {
      this.mode = 'selectorsAPI';
    } else if (this.shouldUseXPath()) {
      this.mode = 'xpath';
      this.compileXPathMatcher();
    } else {
      this.mode = "normal";
      this.compileMatcher();
    }

  },

  shouldUseXPath: (function() {

    var IS_DESCENDANT_SELECTOR_BUGGY = (function(){
      var isBuggy = false;
      if (document.evaluate && window.XPathResult) {
        var el = document.createElement('div');
        el.innerHTML = '<ul><li></li></ul><div><ul><li></li></ul></div>';

        var xpath = ".//*[local-name()='ul' or local-name()='UL']" +
          "//*[local-name()='li' or local-name()='LI']";

        var result = document.evaluate(xpath, el, null,
          XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);

        isBuggy = (result.snapshotLength !== 2);
        el = null;
      }
      return isBuggy;
    })();

    return function() {
      if (!Prototype.BrowserFeatures.XPath) return false;

      var e = this.expression;

      if (Prototype.Browser.WebKit &&
       (e.include("-of-type") || e.include(":empty")))
        return false;

      if ((/(\[[\w-]*?:|:checked)/).test(e))
        return false;

      if (IS_DESCENDANT_SELECTOR_BUGGY) return false;

      return true;
    }

  })(),

  shouldUseSelectorsAPI: function() {
    if (!Prototype.BrowserFeatures.SelectorsAPI) return false;

    if (Selector.CASE_INSENSITIVE_CLASS_NAMES) return false;

    if (!Selector._div) Selector._div = new Element('div');

    try {
      Selector._div.querySelector(this.expression);
    } catch(e) {
      return false;
    }

    return true;
  },

  compileMatcher: function() {
    var e = this.expression, ps = Selector.patterns, h = Selector.handlers,
        c = Selector.criteria, le, p, m, len = ps.length, name;

    if (Selector._cache[e]) {
      this.matcher = Selector._cache[e];
      return;
    }

    this.matcher = ["this.matcher = function(root) {",
                    "var r = root, h = Selector.handlers, c = false, n;"];

    while (e && le != e && (/\S/).test(e)) {
      le = e;
      for (var i = 0; i<len; i++) {
        p = ps[i].re;
        name = ps[i].name;
        if (m = e.match(p)) {
          this.matcher.push(Object.isFunction(c[name]) ? c[name](m) :
            new Template(c[name]).evaluate(m));
          e = e.replace(m[0], '');
          break;
        }
      }
    }

    this.matcher.push("return h.unique(n);\n}");
    eval(this.matcher.join('\n'));
    Selector._cache[this.expression] = this.matcher;
  },

  compileXPathMatcher: function() {
    var e = this.expression, ps = Selector.patterns,
        x = Selector.xpath, le, m, len = ps.length, name;

    if (Selector._cache[e]) {
      this.xpath = Selector._cache[e]; return;
    }

    this.matcher = ['.//*'];
    while (e && le != e && (/\S/).test(e)) {
      le = e;
      for (var i = 0; i<len; i++) {
        name = ps[i].name;
        if (m = e.match(ps[i].re)) {
          this.matcher.push(Object.isFunction(x[name]) ? x[name](m) :
            new Template(x[name]).evaluate(m));
          e = e.replace(m[0], '');
          break;
        }
      }
    }

    this.xpath = this.matcher.join('');
    Selector._cache[this.expression] = this.xpath;
  },

  findElements: function(root) {
    root = root || document;
    var e = this.expression, results;

    switch (this.mode) {
      case 'selectorsAPI':
        if (root !== document) {
          var oldId = root.id, id = $(root).identify();
          id = id.replace(/([\.:])/g, "\\$1");
          e = "#" + id + " " + e;
        }

        results = $A(root.querySelectorAll(e)).map(Element.extend);
        root.id = oldId;

        return results;
      case 'xpath':
        return document._getElementsByXPath(this.xpath, root);
      default:
       return this.matcher(root);
    }
  },

  match: function(element) {
    this.tokens = [];

    var e = this.expression, ps = Selector.patterns, as = Selector.assertions;
    var le, p, m, len = ps.length, name;

    while (e && le !== e && (/\S/).test(e)) {
      le = e;
      for (var i = 0; i<len; i++) {
        p = ps[i].re;
        name = ps[i].name;
        if (m = e.match(p)) {
          if (as[name]) {
            this.tokens.push([name, Object.clone(m)]);
            e = e.replace(m[0], '');
          } else {
            return this.findElements(document).include(element);
          }
        }
      }
    }

    var match = true, name, matches;
    for (var i = 0, token; token = this.tokens[i]; i++) {
      name = token[0], matches = token[1];
      if (!Selector.assertions[name](element, matches)) {
        match = false; break;
      }
    }

    return match;
  },

  toString: function() {
    return this.expression;
  },

  inspect: function() {
    return "#<Selector:" + this.expression.inspect() + ">";
  }
});

if (Prototype.BrowserFeatures.SelectorsAPI &&
 document.compatMode === 'BackCompat') {
  Selector.CASE_INSENSITIVE_CLASS_NAMES = (function(){
    var div = document.createElement('div'),
     span = document.createElement('span');

    div.id = "prototype_test_id";
    span.className = 'Test';
    div.appendChild(span);
    var isIgnored = (div.querySelector('#prototype_test_id .test') !== null);
    div = span = null;
    return isIgnored;
  })();
}

Object.extend(Selector, {
  _cache: { },

  xpath: {
    descendant:   "//*",
    child:        "/*",
    adjacent:     "/following-sibling::*[1]",
    laterSibling: '/following-sibling::*',
    tagName:      function(m) {
      if (m[1] == '*') return '';
      return "[local-name()='" + m[1].toLowerCase() +
             "' or local-name()='" + m[1].toUpperCase() + "']";
    },
    className:    "[contains(concat(' ', @class, ' '), ' #{1} ')]",
    id:           "[@id='#{1}']",
    attrPresence: function(m) {
      m[1] = m[1].toLowerCase();
      return new Template("[@#{1}]").evaluate(m);
    },
    attr: function(m) {
      m[1] = m[1].toLowerCase();
      m[3] = m[5] || m[6];
      return new Template(Selector.xpath.operators[m[2]]).evaluate(m);
    },
    pseudo: function(m) {
      var h = Selector.xpath.pseudos[m[1]];
      if (!h) return '';
      if (Object.isFunction(h)) return h(m);
      return new Template(Selector.xpath.pseudos[m[1]]).evaluate(m);
    },
    operators: {
      '=':  "[@#{1}='#{3}']",
      '!=': "[@#{1}!='#{3}']",
      '^=': "[starts-with(@#{1}, '#{3}')]",
      '$=': "[substring(@#{1}, (string-length(@#{1}) - string-length('#{3}') + 1))='#{3}']",
      '*=': "[contains(@#{1}, '#{3}')]",
      '~=': "[contains(concat(' ', @#{1}, ' '), ' #{3} ')]",
      '|=': "[contains(concat('-', @#{1}, '-'), '-#{3}-')]"
    },
    pseudos: {
      'first-child': '[not(preceding-sibling::*)]',
      'last-child':  '[not(following-sibling::*)]',
      'only-child':  '[not(preceding-sibling::* or following-sibling::*)]',
      'empty':       "[count(*) = 0 and (count(text()) = 0)]",
      'checked':     "[@checked]",
      'disabled':    "[(@disabled) and (@type!='hidden')]",
      'enabled':     "[not(@disabled) and (@type!='hidden')]",
      'not': function(m) {
        var e = m[6], p = Selector.patterns,
            x = Selector.xpath, le, v, len = p.length, name;

        var exclusion = [];
        while (e && le != e && (/\S/).test(e)) {
          le = e;
          for (var i = 0; i<len; i++) {
            name = p[i].name
            if (m = e.match(p[i].re)) {
              v = Object.isFunction(x[name]) ? x[name](m) : new Template(x[name]).evaluate(m);
              exclusion.push("(" + v.substring(1, v.length - 1) + ")");
              e = e.replace(m[0], '');
              break;
            }
          }
        }
        return "[not(" + exclusion.join(" and ") + ")]";
      },
      'nth-child':      function(m) {
        return Selector.xpath.pseudos.nth("(count(./preceding-sibling::*) + 1) ", m);
      },
      'nth-last-child': function(m) {
        return Selector.xpath.pseudos.nth("(count(./following-sibling::*) + 1) ", m);
      },
      'nth-of-type':    function(m) {
        return Selector.xpath.pseudos.nth("position() ", m);
      },
      'nth-last-of-type': function(m) {
        return Selector.xpath.pseudos.nth("(last() + 1 - position()) ", m);
      },
      'first-of-type':  function(m) {
        m[6] = "1"; return Selector.xpath.pseudos['nth-of-type'](m);
      },
      'last-of-type':   function(m) {
        m[6] = "1"; return Selector.xpath.pseudos['nth-last-of-type'](m);
      },
      'only-of-type':   function(m) {
        var p = Selector.xpath.pseudos; return p['first-of-type'](m) + p['last-of-type'](m);
      },
      nth: function(fragment, m) {
        var mm, formula = m[6], predicate;
        if (formula == 'even') formula = '2n+0';
        if (formula == 'odd')  formula = '2n+1';
        if (mm = formula.match(/^(\d+)$/)) // digit only
          return '[' + fragment + "= " + mm[1] + ']';
        if (mm = formula.match(/^(-?\d*)?n(([+-])(\d+))?/)) { // an+b
          if (mm[1] == "-") mm[1] = -1;
          var a = mm[1] ? Number(mm[1]) : 1;
          var b = mm[2] ? Number(mm[2]) : 0;
          predicate = "[((#{fragment} - #{b}) mod #{a} = 0) and " +
          "((#{fragment} - #{b}) div #{a} >= 0)]";
          return new Template(predicate).evaluate({
            fragment: fragment, a: a, b: b });
        }
      }
    }
  },

  criteria: {
    tagName:      'n = h.tagName(n, r, "#{1}", c);      c = false;',
    className:    'n = h.className(n, r, "#{1}", c);    c = false;',
    id:           'n = h.id(n, r, "#{1}", c);           c = false;',
    attrPresence: 'n = h.attrPresence(n, r, "#{1}", c); c = false;',
    attr: function(m) {
      m[3] = (m[5] || m[6]);
      return new Template('n = h.attr(n, r, "#{1}", "#{3}", "#{2}", c); c = false;').evaluate(m);
    },
    pseudo: function(m) {
      if (m[6]) m[6] = m[6].replace(/"/g, '\\"');
      return new Template('n = h.pseudo(n, "#{1}", "#{6}", r, c); c = false;').evaluate(m);
    },
    descendant:   'c = "descendant";',
    child:        'c = "child";',
    adjacent:     'c = "adjacent";',
    laterSibling: 'c = "laterSibling";'
  },

  patterns: [
    { name: 'laterSibling', re: /^\s*~\s*/ },
    { name: 'child',        re: /^\s*>\s*/ },
    { name: 'adjacent',     re: /^\s*\+\s*/ },
    { name: 'descendant',   re: /^\s/ },

    { name: 'tagName',      re: /^\s*(\*|[\w\-]+)(\b|$)?/ },
    { name: 'id',           re: /^#([\w\-\*]+)(\b|$)/ },
    { name: 'className',    re: /^\.([\w\-\*]+)(\b|$)/ },
    { name: 'pseudo',       re: /^:((first|last|nth|nth-last|only)(-child|-of-type)|empty|checked|(en|dis)abled|not)(\((.*?)\))?(\b|$|(?=\s|[:+~>]))/ },
    { name: 'attrPresence', re: /^\[((?:[\w-]+:)?[\w-]+)\]/ },
    { name: 'attr',         re: /\[((?:[\w-]*:)?[\w-]+)\s*(?:([!^$*~|]?=)\s*((['"])([^\4]*?)\4|([^'"][^\]]*?)))?\]/ }
  ],

  assertions: {
    tagName: function(element, matches) {
      return matches[1].toUpperCase() == element.tagName.toUpperCase();
    },

    className: function(element, matches) {
      return Element.hasClassName(element, matches[1]);
    },

    id: function(element, matches) {
      return element.id === matches[1];
    },

    attrPresence: function(element, matches) {
      return Element.hasAttribute(element, matches[1]);
    },

    attr: function(element, matches) {
      var nodeValue = Element.readAttribute(element, matches[1]);
      return nodeValue && Selector.operators[matches[2]](nodeValue, matches[5] || matches[6]);
    }
  },

  handlers: {
    concat: function(a, b) {
      for (var i = 0, node; node = b[i]; i++)
        a.push(node);
      return a;
    },

    mark: function(nodes) {
      var _true = Prototype.emptyFunction;
      for (var i = 0, node; node = nodes[i]; i++)
        node._countedByPrototype = _true;
      return nodes;
    },

    unmark: (function(){

      var PROPERTIES_ATTRIBUTES_MAP = (function(){
        var el = document.createElement('div'),
            isBuggy = false,
            propName = '_countedByPrototype',
            value = 'x'
        el[propName] = value;
        isBuggy = (el.getAttribute(propName) === value);
        el = null;
        return isBuggy;
      })();

      return PROPERTIES_ATTRIBUTES_MAP ?
        function(nodes) {
          for (var i = 0, node; node = nodes[i]; i++)
            node.removeAttribute('_countedByPrototype');
          return nodes;
        } :
        function(nodes) {
          for (var i = 0, node; node = nodes[i]; i++)
            node._countedByPrototype = void 0;
          return nodes;
        }
    })(),

    index: function(parentNode, reverse, ofType) {
      parentNode._countedByPrototype = Prototype.emptyFunction;
      if (reverse) {
        for (var nodes = parentNode.childNodes, i = nodes.length - 1, j = 1; i >= 0; i--) {
          var node = nodes[i];
          if (node.nodeType == 1 && (!ofType || node._countedByPrototype)) node.nodeIndex = j++;
        }
      } else {
        for (var i = 0, j = 1, nodes = parentNode.childNodes; node = nodes[i]; i++)
          if (node.nodeType == 1 && (!ofType || node._countedByPrototype)) node.nodeIndex = j++;
      }
    },

    unique: function(nodes) {
      if (nodes.length == 0) return nodes;
      var results = [], n;
      for (var i = 0, l = nodes.length; i < l; i++)
        if (typeof (n = nodes[i])._countedByPrototype == 'undefined') {
          n._countedByPrototype = Prototype.emptyFunction;
          results.push(Element.extend(n));
        }
      return Selector.handlers.unmark(results);
    },

    descendant: function(nodes) {
      var h = Selector.handlers;
      for (var i = 0, results = [], node; node = nodes[i]; i++)
        h.concat(results, node.getElementsByTagName('*'));
      return results;
    },

    child: function(nodes) {
      var h = Selector.handlers;
      for (var i = 0, results = [], node; node = nodes[i]; i++) {
        for (var j = 0, child; child = node.childNodes[j]; j++)
          if (child.nodeType == 1 && child.tagName != '!') results.push(child);
      }
      return results;
    },

    adjacent: function(nodes) {
      for (var i = 0, results = [], node; node = nodes[i]; i++) {
        var next = this.nextElementSibling(node);
        if (next) results.push(next);
      }
      return results;
    },

    laterSibling: function(nodes) {
      var h = Selector.handlers;
      for (var i = 0, results = [], node; node = nodes[i]; i++)
        h.concat(results, Element.nextSiblings(node));
      return results;
    },

    nextElementSibling: function(node) {
      while (node = node.nextSibling)
        if (node.nodeType == 1) return node;
      return null;
    },

    previousElementSibling: function(node) {
      while (node = node.previousSibling)
        if (node.nodeType == 1) return node;
      return null;
    },

    tagName: function(nodes, root, tagName, combinator) {
      var uTagName = tagName.toUpperCase();
      var results = [], h = Selector.handlers;
      if (nodes) {
        if (combinator) {
          if (combinator == "descendant") {
            for (var i = 0, node; node = nodes[i]; i++)
              h.concat(results, node.getElementsByTagName(tagName));
            return results;
          } else nodes = this[combinator](nodes);
          if (tagName == "*") return nodes;
        }
        for (var i = 0, node; node = nodes[i]; i++)
          if (node.tagName.toUpperCase() === uTagName) results.push(node);
        return results;
      } else return root.getElementsByTagName(tagName);
    },

    id: function(nodes, root, id, combinator) {
      var targetNode = $(id), h = Selector.handlers;

      if (root == document) {
        if (!targetNode) return [];
        if (!nodes) return [targetNode];
      } else {
        if (!root.sourceIndex || root.sourceIndex < 1) {
          var nodes = root.getElementsByTagName('*');
          for (var j = 0, node; node = nodes[j]; j++) {
            if (node.id === id) return [node];
          }
        }
      }

      if (nodes) {
        if (combinator) {
          if (combinator == 'child') {
            for (var i = 0, node; node = nodes[i]; i++)
              if (targetNode.parentNode == node) return [targetNode];
          } else if (combinator == 'descendant') {
            for (var i = 0, node; node = nodes[i]; i++)
              if (Element.descendantOf(targetNode, node)) return [targetNode];
          } else if (combinator == 'adjacent') {
            for (var i = 0, node; node = nodes[i]; i++)
              if (Selector.handlers.previousElementSibling(targetNode) == node)
                return [targetNode];
          } else nodes = h[combinator](nodes);
        }
        for (var i = 0, node; node = nodes[i]; i++)
          if (node == targetNode) return [targetNode];
        return [];
      }
      return (targetNode && Element.descendantOf(targetNode, root)) ? [targetNode] : [];
    },

    className: function(nodes, root, className, combinator) {
      if (nodes && combinator) nodes = this[combinator](nodes);
      return Selector.handlers.byClassName(nodes, root, className);
    },

    byClassName: function(nodes, root, className) {
      if (!nodes) nodes = Selector.handlers.descendant([root]);
      var needle = ' ' + className + ' ';
      for (var i = 0, results = [], node, nodeClassName; node = nodes[i]; i++) {
        nodeClassName = node.className;
        if (nodeClassName.length == 0) continue;
        if (nodeClassName == className || (' ' + nodeClassName + ' ').include(needle))
          results.push(node);
      }
      return results;
    },

    attrPresence: function(nodes, root, attr, combinator) {
      if (!nodes) nodes = root.getElementsByTagName("*");
      if (nodes && combinator) nodes = this[combinator](nodes);
      var results = [];
      for (var i = 0, node; node = nodes[i]; i++)
        if (Element.hasAttribute(node, attr)) results.push(node);
      return results;
    },

    attr: function(nodes, root, attr, value, operator, combinator) {
      if (!nodes) nodes = root.getElementsByTagName("*");
      if (nodes && combinator) nodes = this[combinator](nodes);
      var handler = Selector.operators[operator], results = [];
      for (var i = 0, node; node = nodes[i]; i++) {
        var nodeValue = Element.readAttribute(node, attr);
        if (nodeValue === null) continue;
        if (handler(nodeValue, value)) results.push(node);
      }
      return results;
    },

    pseudo: function(nodes, name, value, root, combinator) {
      if (nodes && combinator) nodes = this[combinator](nodes);
      if (!nodes) nodes = root.getElementsByTagName("*");
      return Selector.pseudos[name](nodes, value, root);
    }
  },

  pseudos: {
    'first-child': function(nodes, value, root) {
      for (var i = 0, results = [], node; node = nodes[i]; i++) {
        if (Selector.handlers.previousElementSibling(node)) continue;
          results.push(node);
      }
      return results;
    },
    'last-child': function(nodes, value, root) {
      for (var i = 0, results = [], node; node = nodes[i]; i++) {
        if (Selector.handlers.nextElementSibling(node)) continue;
          results.push(node);
      }
      return results;
    },
    'only-child': function(nodes, value, root) {
      var h = Selector.handlers;
      for (var i = 0, results = [], node; node = nodes[i]; i++)
        if (!h.previousElementSibling(node) && !h.nextElementSibling(node))
          results.push(node);
      return results;
    },
    'nth-child':        function(nodes, formula, root) {
      return Selector.pseudos.nth(nodes, formula, root);
    },
    'nth-last-child':   function(nodes, formula, root) {
      return Selector.pseudos.nth(nodes, formula, root, true);
    },
    'nth-of-type':      function(nodes, formula, root) {
      return Selector.pseudos.nth(nodes, formula, root, false, true);
    },
    'nth-last-of-type': function(nodes, formula, root) {
      return Selector.pseudos.nth(nodes, formula, root, true, true);
    },
    'first-of-type':    function(nodes, formula, root) {
      return Selector.pseudos.nth(nodes, "1", root, false, true);
    },
    'last-of-type':     function(nodes, formula, root) {
      return Selector.pseudos.nth(nodes, "1", root, true, true);
    },
    'only-of-type':     function(nodes, formula, root) {
      var p = Selector.pseudos;
      return p['last-of-type'](p['first-of-type'](nodes, formula, root), formula, root);
    },

    getIndices: function(a, b, total) {
      if (a == 0) return b > 0 ? [b] : [];
      return $R(1, total).inject([], function(memo, i) {
        if (0 == (i - b) % a && (i - b) / a >= 0) memo.push(i);
        return memo;
      });
    },

    nth: function(nodes, formula, root, reverse, ofType) {
      if (nodes.length == 0) return [];
      if (formula == 'even') formula = '2n+0';
      if (formula == 'odd')  formula = '2n+1';
      var h = Selector.handlers, results = [], indexed = [], m;
      h.mark(nodes);
      for (var i = 0, node; node = nodes[i]; i++) {
        if (!node.parentNode._countedByPrototype) {
          h.index(node.parentNode, reverse, ofType);
          indexed.push(node.parentNode);
        }
      }
      if (formula.match(/^\d+$/)) { // just a number
        formula = Number(formula);
        for (var i = 0, node; node = nodes[i]; i++)
          if (node.nodeIndex == formula) results.push(node);
      } else if (m = formula.match(/^(-?\d*)?n(([+-])(\d+))?/)) { // an+b
        if (m[1] == "-") m[1] = -1;
        var a = m[1] ? Number(m[1]) : 1;
        var b = m[2] ? Number(m[2]) : 0;
        var indices = Selector.pseudos.getIndices(a, b, nodes.length);
        for (var i = 0, node, l = indices.length; node = nodes[i]; i++) {
          for (var j = 0; j < l; j++)
            if (node.nodeIndex == indices[j]) results.push(node);
        }
      }
      h.unmark(nodes);
      h.unmark(indexed);
      return results;
    },

    'empty': function(nodes, value, root) {
      for (var i = 0, results = [], node; node = nodes[i]; i++) {
        if (node.tagName == '!' || node.firstChild) continue;
        results.push(node);
      }
      return results;
    },

    'not': function(nodes, selector, root) {
      var h = Selector.handlers, selectorType, m;
      var exclusions = new Selector(selector).findElements(root);
      h.mark(exclusions);
      for (var i = 0, results = [], node; node = nodes[i]; i++)
        if (!node._countedByPrototype) results.push(node);
      h.unmark(exclusions);
      return results;
    },

    'enabled': function(nodes, value, root) {
      for (var i = 0, results = [], node; node = nodes[i]; i++)
        if (!node.disabled && (!node.type || node.type !== 'hidden'))
          results.push(node);
      return results;
    },

    'disabled': function(nodes, value, root) {
      for (var i = 0, results = [], node; node = nodes[i]; i++)
        if (node.disabled) results.push(node);
      return results;
    },

    'checked': function(nodes, value, root) {
      for (var i = 0, results = [], node; node = nodes[i]; i++)
        if (node.checked) results.push(node);
      return results;
    }
  },

  operators: {
    '=':  function(nv, v) { return nv == v; },
    '!=': function(nv, v) { return nv != v; },
    '^=': function(nv, v) { return nv == v || nv && nv.startsWith(v); },
    '$=': function(nv, v) { return nv == v || nv && nv.endsWith(v); },
    '*=': function(nv, v) { return nv == v || nv && nv.include(v); },
    '~=': function(nv, v) { return (' ' + nv + ' ').include(' ' + v + ' '); },
    '|=': function(nv, v) { return ('-' + (nv || "").toUpperCase() +
     '-').include('-' + (v || "").toUpperCase() + '-'); }
  },

  split: function(expression) {
    var expressions = [];
    expression.scan(/(([\w#:.~>+()\s-]+|\*|\[.*?\])+)\s*(,|$)/, function(m) {
      expressions.push(m[1].strip());
    });
    return expressions;
  },

  matchElements: function(elements, expression) {
    var matches = $$(expression), h = Selector.handlers;
    h.mark(matches);
    for (var i = 0, results = [], element; element = elements[i]; i++)
      if (element._countedByPrototype) results.push(element);
    h.unmark(matches);
    return results;
  },

  findElement: function(elements, expression, index) {
    if (Object.isNumber(expression)) {
      index = expression; expression = false;
    }
    return Selector.matchElements(elements, expression || '*')[index || 0];
  },

  findChildElements: function(element, expressions) {
    expressions = Selector.split(expressions.join(','));
    var results = [], h = Selector.handlers;
    for (var i = 0, l = expressions.length, selector; i < l; i++) {
      selector = new Selector(expressions[i].strip());
      h.concat(results, selector.findElements(element));
    }
    return (l > 1) ? h.unique(results) : results;
  }
});

if (Prototype.Browser.IE) {
  Object.extend(Selector.handlers, {
    concat: function(a, b) {
      for (var i = 0, node; node = b[i]; i++)
        if (node.tagName !== "!") a.push(node);
      return a;
    }
  });
}

function $$() {
  return Selector.findChildElements(document, $A(arguments));
}

var Form = {
  reset: function(form) {
    form = $(form);
    form.reset();
    return form;
  },

  serializeElements: function(elements, options) {
    if (typeof options != 'object') options = { hash: !!options };
    else if (Object.isUndefined(options.hash)) options.hash = true;
    var key, value, submitted = false, submit = options.submit;

    var data = elements.inject({ }, function(result, element) {
      if (!element.disabled && element.name) {
        key = element.name; value = $(element).getValue();
        if (value != null && element.type != 'file' && (element.type != 'submit' || (!submitted &&
            submit !== false && (!submit || key == submit) && (submitted = true)))) {
          if (key in result) {
            if (!Object.isArray(result[key])) result[key] = [result[key]];
            result[key].push(value);
          }
          else result[key] = value;
        }
      }
      return result;
    });

    return options.hash ? data : Object.toQueryString(data);
  }
};

Form.Methods = {
  serialize: function(form, options) {
    return Form.serializeElements(Form.getElements(form), options);
  },

  getElements: function(form) {
    var elements = $(form).getElementsByTagName('*'),
        element,
        arr = [ ],
        serializers = Form.Element.Serializers;
    for (var i = 0; element = elements[i]; i++) {
      arr.push(element);
    }
    return arr.inject([], function(elements, child) {
      if (serializers[child.tagName.toLowerCase()])
        elements.push(Element.extend(child));
      return elements;
    })
  },

  getInputs: function(form, typeName, name) {
    form = $(form);
    var inputs = form.getElementsByTagName('input');

    if (!typeName && !name) return $A(inputs).map(Element.extend);

    for (var i = 0, matchingInputs = [], length = inputs.length; i < length; i++) {
      var input = inputs[i];
      if ((typeName && input.type != typeName) || (name && input.name != name))
        continue;
      matchingInputs.push(Element.extend(input));
    }

    return matchingInputs;
  },

  disable: function(form) {
    form = $(form);
    Form.getElements(form).invoke('disable');
    return form;
  },

  enable: function(form) {
    form = $(form);
    Form.getElements(form).invoke('enable');
    return form;
  },

  findFirstElement: function(form) {
    var elements = $(form).getElements().findAll(function(element) {
      return 'hidden' != element.type && !element.disabled;
    });
    var firstByIndex = elements.findAll(function(element) {
      return element.hasAttribute('tabIndex') && element.tabIndex >= 0;
    }).sortBy(function(element) { return element.tabIndex }).first();

    return firstByIndex ? firstByIndex : elements.find(function(element) {
      return /^(?:input|select|textarea)$/i.test(element.tagName);
    });
  },

  focusFirstElement: function(form) {
    form = $(form);
    form.findFirstElement().activate();
    return form;
  },

  request: function(form, options) {
    form = $(form), options = Object.clone(options || { });

    var params = options.parameters, action = form.readAttribute('action') || '';
    if (action.blank()) action = window.location.href;
    options.parameters = form.serialize(true);

    if (params) {
      if (Object.isString(params)) params = params.toQueryParams();
      Object.extend(options.parameters, params);
    }

    if (form.hasAttribute('method') && !options.method)
      options.method = form.method;

    return new Ajax.Request(action, options);
  }
};

/*--------------------------------------------------------------------------*/


Form.Element = {
  focus: function(element) {
    $(element).focus();
    return element;
  },

  select: function(element) {
    $(element).select();
    return element;
  }
};

Form.Element.Methods = {

  serialize: function(element) {
    element = $(element);
    if (!element.disabled && element.name) {
      var value = element.getValue();
      if (value != undefined) {
        var pair = { };
        pair[element.name] = value;
        return Object.toQueryString(pair);
      }
    }
    return '';
  },

  getValue: function(element) {
    element = $(element);
    var method = element.tagName.toLowerCase();
    return Form.Element.Serializers[method](element);
  },

  setValue: function(element, value) {
    element = $(element);
    var method = element.tagName.toLowerCase();
    Form.Element.Serializers[method](element, value);
    return element;
  },

  clear: function(element) {
    $(element).value = '';
    return element;
  },

  present: function(element) {
    return $(element).value != '';
  },

  activate: function(element) {
    element = $(element);
    try {
      element.focus();
      if (element.select && (element.tagName.toLowerCase() != 'input' ||
          !(/^(?:button|reset|submit)$/i.test(element.type))))
        element.select();
    } catch (e) { }
    return element;
  },

  disable: function(element) {
    element = $(element);
    element.disabled = true;
    return element;
  },

  enable: function(element) {
    element = $(element);
    element.disabled = false;
    return element;
  }
};

/*--------------------------------------------------------------------------*/

var Field = Form.Element;

var $F = Form.Element.Methods.getValue;

/*--------------------------------------------------------------------------*/

Form.Element.Serializers = {
  input: function(element, value) {
    switch (element.type.toLowerCase()) {
      case 'checkbox':
      case 'radio':
        return Form.Element.Serializers.inputSelector(element, value);
      default:
        return Form.Element.Serializers.textarea(element, value);
    }
  },

  inputSelector: function(element, value) {
    if (Object.isUndefined(value)) return element.checked ? element.value : null;
    else element.checked = !!value;
  },

  textarea: function(element, value) {
    if (Object.isUndefined(value)) return element.value;
    else element.value = value;
  },

  select: function(element, value) {
    if (Object.isUndefined(value))
      return this[element.type == 'select-one' ?
        'selectOne' : 'selectMany'](element);
    else {
      var opt, currentValue, single = !Object.isArray(value);
      for (var i = 0, length = element.length; i < length; i++) {
        opt = element.options[i];
        currentValue = this.optionValue(opt);
        if (single) {
          if (currentValue == value) {
            opt.selected = true;
            return;
          }
        }
        else opt.selected = value.include(currentValue);
      }
    }
  },

  selectOne: function(element) {
    var index = element.selectedIndex;
    return index >= 0 ? this.optionValue(element.options[index]) : null;
  },

  selectMany: function(element) {
    var values, length = element.length;
    if (!length) return null;

    for (var i = 0, values = []; i < length; i++) {
      var opt = element.options[i];
      if (opt.selected) values.push(this.optionValue(opt));
    }
    return values;
  },

  optionValue: function(opt) {
    return Element.extend(opt).hasAttribute('value') ? opt.value : opt.text;
  }
};

/*--------------------------------------------------------------------------*/


Abstract.TimedObserver = Class.create(PeriodicalExecuter, {
  initialize: function($super, element, frequency, callback) {
    $super(callback, frequency);
    this.element   = $(element);
    this.lastValue = this.getValue();
  },

  execute: function() {
    var value = this.getValue();
    if (Object.isString(this.lastValue) && Object.isString(value) ?
        this.lastValue != value : String(this.lastValue) != String(value)) {
      this.callback(this.element, value);
      this.lastValue = value;
    }
  }
});

Form.Element.Observer = Class.create(Abstract.TimedObserver, {
  getValue: function() {
    return Form.Element.getValue(this.element);
  }
});

Form.Observer = Class.create(Abstract.TimedObserver, {
  getValue: function() {
    return Form.serialize(this.element);
  }
});

/*--------------------------------------------------------------------------*/

Abstract.EventObserver = Class.create({
  initialize: function(element, callback) {
    this.element  = $(element);
    this.callback = callback;

    this.lastValue = this.getValue();
    if (this.element.tagName.toLowerCase() == 'form')
      this.registerFormCallbacks();
    else
      this.registerCallback(this.element);
  },

  onElementEvent: function() {
    var value = this.getValue();
    if (this.lastValue != value) {
      this.callback(this.element, value);
      this.lastValue = value;
    }
  },

  registerFormCallbacks: function() {
    Form.getElements(this.element).each(this.registerCallback, this);
  },

  registerCallback: function(element) {
    if (element.type) {
      switch (element.type.toLowerCase()) {
        case 'checkbox':
        case 'radio':
          Event.observe(element, 'click', this.onElementEvent.bind(this));
          break;
        default:
          Event.observe(element, 'change', this.onElementEvent.bind(this));
          break;
      }
    }
  }
});

Form.Element.EventObserver = Class.create(Abstract.EventObserver, {
  getValue: function() {
    return Form.Element.getValue(this.element);
  }
});

Form.EventObserver = Class.create(Abstract.EventObserver, {
  getValue: function() {
    return Form.serialize(this.element);
  }
});
(function() {

  var Event = {
    KEY_BACKSPACE: 8,
    KEY_TAB:       9,
    KEY_RETURN:   13,
    KEY_ESC:      27,
    KEY_LEFT:     37,
    KEY_UP:       38,
    KEY_RIGHT:    39,
    KEY_DOWN:     40,
    KEY_DELETE:   46,
    KEY_HOME:     36,
    KEY_END:      35,
    KEY_PAGEUP:   33,
    KEY_PAGEDOWN: 34,
    KEY_INSERT:   45,

    cache: {}
  };

  var docEl = document.documentElement;
  var MOUSEENTER_MOUSELEAVE_EVENTS_SUPPORTED = 'onmouseenter' in docEl
    && 'onmouseleave' in docEl;

  var _isButton;
  if (Prototype.Browser.IE) {
    var buttonMap = { 0: 1, 1: 4, 2: 2 };
    _isButton = function(event, code) {
      return event.button === buttonMap[code];
    };
  } else if (Prototype.Browser.WebKit) {
    _isButton = function(event, code) {
      switch (code) {
        case 0: return event.which == 1 && !event.metaKey;
        case 1: return event.which == 1 && event.metaKey;
        default: return false;
      }
    };
  } else {
    _isButton = function(event, code) {
      return event.which ? (event.which === code + 1) : (event.button === code);
    };
  }

  function isLeftClick(event)   { return _isButton(event, 0) }

  function isMiddleClick(event) { return _isButton(event, 1) }

  function isRightClick(event)  { return _isButton(event, 2) }

  function element(event) {
    event = Event.extend(event);

    var node = event.target, type = event.type,
     currentTarget = event.currentTarget;

    if (currentTarget && currentTarget.tagName) {
      if (type === 'load' || type === 'error' ||
        (type === 'click' && currentTarget.tagName.toLowerCase() === 'input'
          && currentTarget.type === 'radio'))
            node = currentTarget;
    }

    if (node.nodeType == Node.TEXT_NODE)
      node = node.parentNode;

    return Element.extend(node);
  }

  function findElement(event, expression) {
    var element = Event.element(event);
    if (!expression) return element;
    var elements = [element].concat(element.ancestors());
    return Selector.findElement(elements, expression, 0);
  }

  function pointer(event) {
    return { x: pointerX(event), y: pointerY(event) };
  }

  function pointerX(event) {
    var docElement = document.documentElement,
     body = document.body || { scrollLeft: 0 };

    return event.pageX || (event.clientX +
      (docElement.scrollLeft || body.scrollLeft) -
      (docElement.clientLeft || 0));
  }

  function pointerY(event) {
    var docElement = document.documentElement,
     body = document.body || { scrollTop: 0 };

    return  event.pageY || (event.clientY +
       (docElement.scrollTop || body.scrollTop) -
       (docElement.clientTop || 0));
  }


  function stop(event) {
    Event.extend(event);
    event.preventDefault();
    event.stopPropagation();

    event.stopped = true;
  }

  Event.Methods = {
    isLeftClick: isLeftClick,
    isMiddleClick: isMiddleClick,
    isRightClick: isRightClick,

    element: element,
    findElement: findElement,

    pointer: pointer,
    pointerX: pointerX,
    pointerY: pointerY,

    stop: stop
  };


  var methods = Object.keys(Event.Methods).inject({ }, function(m, name) {
    m[name] = Event.Methods[name].methodize();
    return m;
  });

  if (Prototype.Browser.IE) {
    function _relatedTarget(event) {
      var element;
      switch (event.type) {
        case 'mouseover': element = event.fromElement; break;
        case 'mouseout':  element = event.toElement;   break;
        default: return null;
      }
      return Element.extend(element);
    }

    Object.extend(methods, {
      stopPropagation: function() { this.cancelBubble = true },
      preventDefault:  function() { this.returnValue = false },
      inspect: function() { return '[object Event]' }
    });

    Event.extend = function(event, element) {
      if (!event) return false;
      if (event._extendedByPrototype) return event;

      event._extendedByPrototype = Prototype.emptyFunction;
      var pointer = Event.pointer(event);

      Object.extend(event, {
        target: event.srcElement || element,
        relatedTarget: _relatedTarget(event),
        pageX:  pointer.x,
        pageY:  pointer.y
      });

      return Object.extend(event, methods);
    };
  } else {
    Event.prototype = window.Event.prototype || document.createEvent('HTMLEvents').__proto__;
    Object.extend(Event.prototype, methods);
    Event.extend = Prototype.K;
  }

  function _createResponder(element, eventName, handler) {
    var registry = Element.retrieve(element, 'prototype_event_registry');

    if (Object.isUndefined(registry)) {
      CACHE.push(element);
      registry = Element.retrieve(element, 'prototype_event_registry', $H());
    }

    var respondersForEvent = registry.get(eventName);
    if (Object.isUndefined(respondersForEvent)) {
      respondersForEvent = [];
      registry.set(eventName, respondersForEvent);
    }

    if (respondersForEvent.pluck('handler').include(handler)) return false;

    var responder;
    if (eventName.include(":")) {
      responder = function(event) {
        if (Object.isUndefined(event.eventName))
          return false;

        if (event.eventName !== eventName)
          return false;

        Event.extend(event, element);
        handler.call(element, event);
      };
    } else {
      if (!MOUSEENTER_MOUSELEAVE_EVENTS_SUPPORTED &&
       (eventName === "mouseenter" || eventName === "mouseleave")) {
        if (eventName === "mouseenter" || eventName === "mouseleave") {
          responder = function(event) {
            Event.extend(event, element);

            var parent = event.relatedTarget;
            while (parent && parent !== element) {
              try { parent = parent.parentNode; }
              catch(e) { parent = element; }
            }

            if (parent === element) return;

            handler.call(element, event);
          };
        }
      } else {
        responder = function(event) {
          Event.extend(event, element);
          handler.call(element, event);
        };
      }
    }

    responder.handler = handler;
    respondersForEvent.push(responder);
    return responder;
  }

  function _destroyCache() {
    for (var i = 0, length = CACHE.length; i < length; i++) {
      Event.stopObserving(CACHE[i]);
      CACHE[i] = null;
    }
  }

  var CACHE = [];

  if (Prototype.Browser.IE)
    window.attachEvent('onunload', _destroyCache);

  if (Prototype.Browser.WebKit)
    window.addEventListener('unload', Prototype.emptyFunction, false);


  var _getDOMEventName = Prototype.K;

  if (!MOUSEENTER_MOUSELEAVE_EVENTS_SUPPORTED) {
    _getDOMEventName = function(eventName) {
      var translations = { mouseenter: "mouseover", mouseleave: "mouseout" };
      return eventName in translations ? translations[eventName] : eventName;
    };
  }

  function observe(element, eventName, handler) {
    element = $(element);

    var responder = _createResponder(element, eventName, handler);

    if (!responder) return element;

    if (eventName.include(':')) {
      if (element.addEventListener)
        element.addEventListener("dataavailable", responder, false);
      else {
        element.attachEvent("ondataavailable", responder);
        element.attachEvent("onfilterchange", responder);
      }
    } else {
      var actualEventName = _getDOMEventName(eventName);

      if (element.addEventListener)
        element.addEventListener(actualEventName, responder, false);
      else
        element.attachEvent("on" + actualEventName, responder);
    }

    return element;
  }

  function stopObserving(element, eventName, handler) {
    element = $(element);

    var registry = Element.retrieve(element, 'prototype_event_registry');

    if (Object.isUndefined(registry)) return element;

    if (eventName && !handler) {
      var responders = registry.get(eventName);

      if (Object.isUndefined(responders)) return element;

      responders.each( function(r) {
        Element.stopObserving(element, eventName, r.handler);
      });
      return element;
    } else if (!eventName) {
      registry.each( function(pair) {
        var eventName = pair.key, responders = pair.value;

        responders.each( function(r) {
          Element.stopObserving(element, eventName, r.handler);
        });
      });
      return element;
    }

    var responders = registry.get(eventName);

    if (!responders) return;

    var responder = responders.find( function(r) { return r.handler === handler; });
    if (!responder) return element;

    var actualEventName = _getDOMEventName(eventName);

    if (eventName.include(':')) {
      if (element.removeEventListener)
        element.removeEventListener("dataavailable", responder, false);
      else {
        element.detachEvent("ondataavailable", responder);
        element.detachEvent("onfilterchange",  responder);
      }
    } else {
      if (element.removeEventListener)
        element.removeEventListener(actualEventName, responder, false);
      else
        element.detachEvent('on' + actualEventName, responder);
    }

    registry.set(eventName, responders.without(responder));

    return element;
  }

  function fire(element, eventName, memo, bubble) {
    element = $(element);

    if (Object.isUndefined(bubble))
      bubble = true;

    if (element == document && document.createEvent && !element.dispatchEvent)
      element = document.documentElement;

    var event;
    if (document.createEvent) {
      event = document.createEvent('HTMLEvents');
      event.initEvent('dataavailable', true, true);
    } else {
      event = document.createEventObject();
      event.eventType = bubble ? 'ondataavailable' : 'onfilterchange';
    }

    event.eventName = eventName;
    event.memo = memo || { };

    if (document.createEvent)
      element.dispatchEvent(event);
    else
      element.fireEvent(event.eventType, event);

    return Event.extend(event);
  }


  Object.extend(Event, Event.Methods);

  Object.extend(Event, {
    fire:          fire,
    observe:       observe,
    stopObserving: stopObserving
  });

  Element.addMethods({
    fire:          fire,

    observe:       observe,

    stopObserving: stopObserving
  });

  Object.extend(document, {
    fire:          fire.methodize(),

    observe:       observe.methodize(),

    stopObserving: stopObserving.methodize(),

    loaded:        false
  });

  if (window.Event) Object.extend(window.Event, Event);
  else window.Event = Event;
})();

(function() {
  /* Support for the DOMContentLoaded event is based on work by Dan Webb,
     Matthias Miller, Dean Edwards, John Resig, and Diego Perini. */

  var timer;

  function fireContentLoadedEvent() {
    if (document.loaded) return;
    if (timer) window.clearTimeout(timer);
    document.loaded = true;
    document.fire('dom:loaded');
  }

  function checkReadyState() {
    if (document.readyState === 'complete') {
      document.stopObserving('readystatechange', checkReadyState);
      fireContentLoadedEvent();
    }
  }

  function pollDoScroll() {
    try { document.documentElement.doScroll('left'); }
    catch(e) {
      timer = pollDoScroll.defer();
      return;
    }
    fireContentLoadedEvent();
  }

  if (document.addEventListener) {
    document.addEventListener('DOMContentLoaded', fireContentLoadedEvent, false);
  } else {
    document.observe('readystatechange', checkReadyState);
    if (window == top)
      timer = pollDoScroll.defer();
  }

  Event.observe(window, 'load', fireContentLoadedEvent);
})();

Element.addMethods();

/*------------------------------- DEPRECATED -------------------------------*/

Hash.toQueryString = Object.toQueryString;

var Toggle = { display: Element.toggle };

Element.Methods.childOf = Element.Methods.descendantOf;

var Insertion = {
  Before: function(element, content) {
    return Element.insert(element, {before:content});
  },

  Top: function(element, content) {
    return Element.insert(element, {top:content});
  },

  Bottom: function(element, content) {
    return Element.insert(element, {bottom:content});
  },

  After: function(element, content) {
    return Element.insert(element, {after:content});
  }
};

var $continue = new Error('"throw $continue" is deprecated, use "return" instead');

var Position = {
  includeScrollOffsets: false,

  prepare: function() {
    this.deltaX =  window.pageXOffset
                || document.documentElement.scrollLeft
                || document.body.scrollLeft
                || 0;
    this.deltaY =  window.pageYOffset
                || document.documentElement.scrollTop
                || document.body.scrollTop
                || 0;
  },

  within: function(element, x, y) {
    if (this.includeScrollOffsets)
      return this.withinIncludingScrolloffsets(element, x, y);
    this.xcomp = x;
    this.ycomp = y;
    this.offset = Element.cumulativeOffset(element);

    return (y >= this.offset[1] &&
            y <  this.offset[1] + element.offsetHeight &&
            x >= this.offset[0] &&
            x <  this.offset[0] + element.offsetWidth);
  },

  withinIncludingScrolloffsets: function(element, x, y) {
    var offsetcache = Element.cumulativeScrollOffset(element);

    this.xcomp = x + offsetcache[0] - this.deltaX;
    this.ycomp = y + offsetcache[1] - this.deltaY;
    this.offset = Element.cumulativeOffset(element);

    return (this.ycomp >= this.offset[1] &&
            this.ycomp <  this.offset[1] + element.offsetHeight &&
            this.xcomp >= this.offset[0] &&
            this.xcomp <  this.offset[0] + element.offsetWidth);
  },

  overlap: function(mode, element) {
    if (!mode) return 0;
    if (mode == 'vertical')
      return ((this.offset[1] + element.offsetHeight) - this.ycomp) /
        element.offsetHeight;
    if (mode == 'horizontal')
      return ((this.offset[0] + element.offsetWidth) - this.xcomp) /
        element.offsetWidth;
  },


  cumulativeOffset: Element.Methods.cumulativeOffset,

  positionedOffset: Element.Methods.positionedOffset,

  absolutize: function(element) {
    Position.prepare();
    return Element.absolutize(element);
  },

  relativize: function(element) {
    Position.prepare();
    return Element.relativize(element);
  },

  realOffset: Element.Methods.cumulativeScrollOffset,

  offsetParent: Element.Methods.getOffsetParent,

  page: Element.Methods.viewportOffset,

  clone: function(source, target, options) {
    options = options || { };
    return Element.clonePosition(target, source, options);
  }
};

/*--------------------------------------------------------------------------*/

if (!document.getElementsByClassName) document.getElementsByClassName = function(instanceMethods){
  function iter(name) {
    return name.blank() ? null : "[contains(concat(' ', @class, ' '), ' " + name + " ')]";
  }

  instanceMethods.getElementsByClassName = Prototype.BrowserFeatures.XPath ?
  function(element, className) {
    className = className.toString().strip();
    var cond = /\s/.test(className) ? $w(className).map(iter).join('') : iter(className);
    return cond ? document._getElementsByXPath('.//*' + cond, element) : [];
  } : function(element, className) {
    className = className.toString().strip();
    var elements = [], classNames = (/\s/.test(className) ? $w(className) : null);
    if (!classNames && !className) return elements;

    var nodes = $(element).getElementsByTagName('*');
    className = ' ' + className + ' ';

    for (var i = 0, child, cn; child = nodes[i]; i++) {
      if (child.className && (cn = ' ' + child.className + ' ') && (cn.include(className) ||
          (classNames && classNames.all(function(name) {
            return !name.toString().blank() && cn.include(' ' + name + ' ');
          }))))
        elements.push(Element.extend(child));
    }
    return elements;
  };

  return function(className, parentElement) {
    return $(parentElement || document.body).getElementsByClassName(className);
  };
}(Element.Methods);

/*--------------------------------------------------------------------------*/

Element.ClassNames = Class.create();
Element.ClassNames.prototype = {
  initialize: function(element) {
    this.element = $(element);
  },

  _each: function(iterator) {
    this.element.className.split(/\s+/).select(function(name) {
      return name.length > 0;
    })._each(iterator);
  },

  set: function(className) {
    this.element.className = className;
  },

  add: function(classNameToAdd) {
    if (this.include(classNameToAdd)) return;
    this.set($A(this).concat(classNameToAdd).join(' '));
  },

  remove: function(classNameToRemove) {
    if (!this.include(classNameToRemove)) return;
    this.set($A(this).without(classNameToRemove).join(' '));
  },

  toString: function() {
    return $A(this).join(' ');
  }
};

Object.extend(Element.ClassNames.prototype, Enumerable);

/*--------------------------------------------------------------------------*/

var process = process || {env: {NODE_ENV: "development"}};
/**
  *
  *  Copyright 2005 Sabre Airline Solutions
  *
  *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
  *  file except in compliance with the License. You may obtain a copy of the License at
  *
  *         http://www.apache.org/licenses/LICENSE-2.0
  *
  *  Unless required by applicable law or agreed to in writing, software distributed under the
  *  License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
  *  either express or implied. See the License for the specific language governing permissions
  *  and limitations under the License.
  **/


//-------------------- rico.js
var Rico = {
  Version: '1.1-beta2'
}

Rico.ArrayExtensions = new Array();

if (Object.prototype.extend) {
   // in prototype.js...
   Rico.ArrayExtensions[ Rico.ArrayExtensions.length ] = Object.prototype.extend;
}

if (Array.prototype.push) {
   // in prototype.js...
   Rico.ArrayExtensions[ Rico.ArrayExtensions.length ] = Array.prototype.push;
}

if (!Array.prototype.remove) {
   Array.prototype.remove = function(dx) {
      if( isNaN(dx) || dx > this.length )
         return false;
      for( var i=0,n=0; i<this.length; i++ )
         if( i != dx )
            this[n++]=this[i];
      this.length-=1;
   };
  Rico.ArrayExtensions[ Rico.ArrayExtensions.length ] = Array.prototype.remove;
}

if (!Array.prototype.removeItem) {
   Array.prototype.removeItem = function(item) {
      for ( var i = 0 ; i < this.length ; i++ )
         if ( this[i] == item ) {
            this.remove(i);
            break;
         }
   };
  Rico.ArrayExtensions[ Rico.ArrayExtensions.length ] = Array.prototype.removeItem;
}

if (!Array.prototype.indices) {
   Array.prototype.indices = function() {
      var indexArray = new Array();
      for ( index in this ) {
         var ignoreThis = false;
         for ( var i = 0 ; i < Rico.ArrayExtensions.length ; i++ ) {
            if ( this[index] == Rico.ArrayExtensions[i] ) {
               ignoreThis = true;
               break;
            }
         }
         if ( !ignoreThis )
            indexArray[ indexArray.length ] = index;
      }
      return indexArray;
   }
  Rico.ArrayExtensions[ Rico.ArrayExtensions.length ] = Array.prototype.indices;
}

// Create the loadXML method and xml getter for Mozilla
if ( window.DOMParser &&
	  window.XMLSerializer &&
	  window.Node && Node.prototype && Node.prototype.__defineGetter__ ) {

   if (!Document.prototype.loadXML) {
      Document.prototype.loadXML = function (s) {
         var doc2 = (new DOMParser()).parseFromString(s, "text/xml");
         while (this.hasChildNodes())
            this.removeChild(this.lastChild);

         for (var i = 0; i < doc2.childNodes.length; i++) {
            this.appendChild(this.importNode(doc2.childNodes[i], true));
         }
      };
	}

	Document.prototype.__defineGetter__( "xml",
	   function () {
		   return (new XMLSerializer()).serializeToString(this);
	   }
	 );
}

document.getElementsByTagAndClassName = function(tagName, className) {
  if ( tagName == null )
     tagName = '*';

  var children = document.getElementsByTagName(tagName) || document.all;
  var elements = new Array();

  if ( className == null )
    return children;

  for (var i = 0; i < children.length; i++) {
    var child = children[i];
    var classNames = child.className.split(' ');
    for (var j = 0; j < classNames.length; j++) {
      if (classNames[j] == className) {
        elements.push(child);
        break;
      }
    }
  }

  return elements;
}


//-------------------- ricoAccordion.js

Rico.Accordion = Class.create();

Rico.Accordion.prototype = {

   initialize: function(container, options) {
      this.container            = $(container);
      this.lastExpandedTab      = null;
      this.accordionTabs        = new Array();
      this.setOptions(options);
      this._attachBehaviors();

      this.container.style.borderBottom = '1px solid ' + this.options.borderColor;

      // set the initial visual state...
      for ( var i=1 ; i < this.accordionTabs.length ; i++ )
      {
         this.accordionTabs[i].collapse();
         this.accordionTabs[i].content.style.display = 'none';
      }
      this.lastExpandedTab = this.accordionTabs[0];
      this.lastExpandedTab.content.style.height = this.options.panelHeight + "px";
      this.lastExpandedTab.showExpanded();
      this.lastExpandedTab.titleBar.style.fontWeight = this.options.expandedFontWeight;
   },

   setOptions: function(options) {
      this.options = {
         expandedBg          : '#63699c',
         hoverBg             : '#63699c',
         collapsedBg         : '#6b79a5',
         expandedTextColor   : '#ffffff',
         expandedFontWeight  : 'bold',
         hoverTextColor      : '#ffffff',
         collapsedTextColor  : '#ced7ef',
         collapsedFontWeight : 'normal',
         hoverTextColor      : '#ffffff',
         borderColor         : '#1f669b',
         panelHeight         : 200,
         onHideTab           : null,
         onShowTab           : null
      }.extend(options || {});
   },

   showTabByIndex: function( anIndex, animate ) {
      var doAnimate = arguments.length == 1 ? true : animate;
      this.showTab( this.accordionTabs[anIndex], doAnimate );
   },

   showTab: function( accordionTab, animate ) {

      var doAnimate = arguments.length == 1 ? true : animate;

      if ( this.options.onHideTab )
         this.options.onHideTab(this.lastExpandedTab);

      this.lastExpandedTab.showCollapsed(); 
      var accordion = this;
      var lastExpandedTab = this.lastExpandedTab;

      this.lastExpandedTab.content.style.height = (this.options.panelHeight - 1) + 'px';
      accordionTab.content.style.display = '';

      accordionTab.titleBar.style.fontWeight = this.options.expandedFontWeight;

      if ( doAnimate ) {
         new Effect.AccordionSize( this.lastExpandedTab.content,
                                   accordionTab.content,
                                   1,
                                   this.options.panelHeight,
                                   100, 10,
                                   { complete: function() {accordion.showTabDone(lastExpandedTab)} } );
         this.lastExpandedTab = accordionTab;
      }
      else {
         this.lastExpandedTab.content.style.height = "1px";
         accordionTab.content.style.height = this.options.panelHeight + "px";
         this.lastExpandedTab = accordionTab;
         this.showTabDone(lastExpandedTab);
      }
   },

   showTabDone: function(collapsedTab) {
      collapsedTab.content.style.display = 'none';
      this.lastExpandedTab.showExpanded();
      if ( this.options.onShowTab )
         this.options.onShowTab(this.lastExpandedTab);
   },

   _attachBehaviors: function() {
      var panels = this._getDirectChildrenByTag(this.container, 'DIV');
      for ( var i = 0 ; i < panels.length ; i++ ) {

         var tabChildren = this._getDirectChildrenByTag(panels[i],'DIV');
         if ( tabChildren.length != 2 )
            continue; // unexpected

         var tabTitleBar   = tabChildren[0];
         var tabContentBox = tabChildren[1];
         this.accordionTabs.push( new Rico.Accordion.Tab(this,tabTitleBar,tabContentBox) );
      }
   },

   _getDirectChildrenByTag: function(e, tagName) {
      var kids = new Array();
      var allKids = e.childNodes;
      for( var i = 0 ; i < allKids.length ; i++ )
         if ( allKids[i] && allKids[i].tagName && allKids[i].tagName == tagName )
            kids.push(allKids[i]);
      return kids;
   }

};

Rico.Accordion.Tab = Class.create();

Rico.Accordion.Tab.prototype = {

   initialize: function(accordion, titleBar, content) {
      this.accordion = accordion;
      this.titleBar  = titleBar;
      this.content   = content;
      this._attachBehaviors();
   },

   collapse: function() {
      this.showCollapsed();
      this.content.style.height = "1px";
   },

   showCollapsed: function() {
      this.expanded = false;
      this.titleBar.style.backgroundColor = this.accordion.options.collapsedBg;
      this.titleBar.style.color           = this.accordion.options.collapsedTextColor;
      this.titleBar.style.fontWeight      = this.accordion.options.collapsedFontWeight;
      this.content.style.overflow = "hidden";
   },

   showExpanded: function() {
      this.expanded = true;
      this.titleBar.style.backgroundColor = this.accordion.options.expandedBg;
      this.titleBar.style.color           = this.accordion.options.expandedTextColor;
      this.content.style.overflow         = "visible";
   },

   titleBarClicked: function(e) {
      if ( this.accordion.lastExpandedTab == this )
         return;
      this.accordion.showTab(this);
   },

   hover: function(e) {
		this.titleBar.style.backgroundColor = this.accordion.options.hoverBg;
		this.titleBar.style.color           = this.accordion.options.hoverTextColor;
   },

   unhover: function(e) {
      if ( this.expanded ) {
         this.titleBar.style.backgroundColor = this.accordion.options.expandedBg;
         this.titleBar.style.color           = this.accordion.options.expandedTextColor;
      }
      else {
         this.titleBar.style.backgroundColor = this.accordion.options.collapsedBg;
         this.titleBar.style.color           = this.accordion.options.collapsedTextColor;
      }
   },

   _attachBehaviors: function() {
      this.content.style.border = "1px solid " + this.accordion.options.borderColor;
      this.content.style.borderTopWidth    = "0px";
      this.content.style.borderBottomWidth = "0px";
      this.content.style.margin            = "0px";

      this.titleBar.onclick     = this.titleBarClicked.bindAsEventListener(this);
      this.titleBar.onmouseover = this.hover.bindAsEventListener(this);
      this.titleBar.onmouseout  = this.unhover.bindAsEventListener(this);
   }

};


//-------------------- ricoAjaxEngine.js

Rico.AjaxEngine = Class.create();

Rico.AjaxEngine.prototype = {

   initialize: function() {
      this.ajaxElements = new Array();
      this.ajaxObjects  = new Array();
      this.requestURLS  = new Array();
   },

   registerAjaxElement: function( anId, anElement ) {
      if ( arguments.length == 1 )
         anElement = $(anId);
      this.ajaxElements[anId] = anElement;
   },

   registerAjaxObject: function( anId, anObject ) {
      this.ajaxObjects[anId] = anObject;
   },

   registerRequest: function (requestLogicalName, requestURL) {
      this.requestURLS[requestLogicalName] = requestURL;
   },

   sendRequest: function(requestName) {
      var requestURL = this.requestURLS[requestName];
      if ( requestURL == null )
         return;

      var queryString = "";
      
      if ( arguments.length > 1 ) {
      	 if(typeof(arguments[1]) == "object" && arguments[1].length != undefined) {
	      	 queryString = this._createQueryString(arguments[1], 0);
      	 }
      	 else {
	         queryString = this._createQueryString(arguments, 1);
	     }         
       }
             
      new Ajax.Request(requestURL, this._requestOptions(queryString));
   },

   sendRequestWithData: function(requestName, xmlDocument) {
      var requestURL = this.requestURLS[requestName];
      if ( requestURL == null )
         return;

      var queryString = "";
      if ( arguments.length > 2 ) {
      	 if(typeof(arguments[2]) == "object" && arguments[2].length != undefined) {
	      	 queryString = this._createQueryString(arguments[2], 0);
      	 }
      	 else {
	         queryString = this._createQueryString(arguments, 2);
	     }         
       }             

      new Ajax.Request(requestURL + "?" + queryString, this._requestOptions(null,xmlDocument));
   },

   sendRequestAndUpdate: function(requestName,container,options) {
      var requestURL = this.requestURLS[requestName];
      if ( requestURL == null )
         return;

      var queryString = "";
      if ( arguments.length > 3 ) {
      	 if(typeof(arguments[3]) == "object" && arguments[3].length != undefined) {
	      	 queryString = this._createQueryString(arguments[3], 0);
      	 }
      	 else {
	         queryString = this._createQueryString(arguments, 3);
	     }         
       }  
             
      var updaterOptions = this._requestOptions(queryString);
      updaterOptions.onComplete = null;
      updaterOptions.extend(options);

      new Ajax.Updater(container, requestURL, updaterOptions);
   },

   sendRequestWithDataAndUpdate: function(requestName,xmlDocument,container,options) {
      var requestURL = this.requestURLS[requestName];
      if ( requestURL == null )
         return;

      var queryString = "";
      if ( arguments.length > 4 ) {
      	 if(typeof(arguments[4]) == "object" && arguments[4].length != undefined) {
	      	 queryString = this._createQueryString(arguments[4], 0);
      	 }
      	 else {
	         queryString = this._createQueryString(arguments, 4);
	     }         
       }


      var updaterOptions = this._requestOptions(queryString,xmlDocument);
      updaterOptions.onComplete = null;
      updaterOptions.extend(options);

      new Ajax.Updater(container, requestURL + "?" + queryString, updaterOptions);
   },

   // Private -- not part of intended engine API --------------------------------------------------------------------

   _requestOptions: function(queryString,xmlDoc) {
      var self = this;

      var requestHeaders = ['X-Rico-Version', Rico.Version ];
      var sendMethod = "post"
      if ( arguments[1] )
         requestHeaders.push( 'Content-type', 'text/xml' );
      else
         sendMethod = "get";

      return { requestHeaders: requestHeaders,
               parameters:     queryString,
               postBody:       arguments[1] ? xmlDoc : null,
               method:         sendMethod,
               onComplete:     self._onRequestComplete.bind(self) };
   },

   _createQueryString: function( theArgs, offset ) {
   	  var self = this;
      var queryString = ""
      for ( var i = offset ; i < theArgs.length ; i++ ) {
          if ( i != offset )
            queryString += "&";

          var anArg = theArgs[i];
                  
          if ( anArg.name != undefined && anArg.value != undefined ) {
            queryString += anArg.name +  "=" + escape(anArg.value);
          }
          else {
             var ePos  = anArg.indexOf('=');
             var argName  = anArg.substring( 0, ePos );
             var argValue = anArg.substring( ePos + 1 );
             queryString += argName + "=" + escape(argValue);
          }
      }

      return queryString;
   },
   _onRequestComplete : function(request) {

      //!!TODO: error handling infrastructure?? 
      if (request.status != 200)
        return;

      var response = request.responseXML.getElementsByTagName("ajax-response");
      if (response == null || response.length != 1)
         return;
      this._processAjaxResponse( response[0].childNodes );
   },

   _processAjaxResponse: function( xmlResponseElements ) {
      for ( var i = 0 ; i < xmlResponseElements.length ; i++ ) {
         var responseElement = xmlResponseElements[i];

         // only process nodes of type element.....
         if ( responseElement.nodeType != 1 )
            continue;

         var responseType = responseElement.getAttribute("type");
         var responseId   = responseElement.getAttribute("id");

         if ( responseType == "object" )
            this._processAjaxObjectUpdate( this.ajaxObjects[ responseId ], responseElement );
         else if ( responseType == "element" )
            this._processAjaxElementUpdate( this.ajaxElements[ responseId ], responseElement );
         else
            alert('unrecognized AjaxResponse type : ' + responseType );
      }
   },

   _processAjaxObjectUpdate: function( ajaxObject, responseElement ) {
      ajaxObject.ajaxUpdate( responseElement );
   },

   _processAjaxElementUpdate: function( ajaxElement, responseElement ) {
      ajaxElement.innerHTML = RicoUtil.getContentAsString(responseElement);
   }

}

var ajaxEngine = new Rico.AjaxEngine();


//-------------------- ricoColor.js
Rico.Color = Class.create();

Rico.Color.prototype = {

   initialize: function(red, green, blue) {
      this.rgb = { r: red, g : green, b : blue };
   },

   setRed: function(r) {
      this.rgb.r = r;
   },

   setGreen: function(g) {
      this.rgb.g = g;
   },

   setBlue: function(b) {
      this.rgb.b = b;
   },

   setHue: function(h) {

      // get an HSB model, and set the new hue...
      var hsb = this.asHSB();
      hsb.h = h;

      // convert back to RGB...
      this.rgb = Rico.Color.HSBtoRGB(hsb.h, hsb.s, hsb.b);
   },

   setSaturation: function(s) {
      // get an HSB model, and set the new hue...
      var hsb = this.asHSB();
      hsb.s = s;

      // convert back to RGB and set values...
      this.rgb = Rico.Color.HSBtoRGB(hsb.h, hsb.s, hsb.b);
   },

   setBrightness: function(b) {
      // get an HSB model, and set the new hue...
      var hsb = this.asHSB();
      hsb.b = b;

      // convert back to RGB and set values...
      this.rgb = Rico.Color.HSBtoRGB( hsb.h, hsb.s, hsb.b );
   },

   darken: function(percent) {
      var hsb  = this.asHSB();
      this.rgb = Rico.Color.HSBtoRGB(hsb.h, hsb.s, Math.max(hsb.b - percent,0));
   },

   brighten: function(percent) {
      var hsb  = this.asHSB();
      this.rgb = Rico.Color.HSBtoRGB(hsb.h, hsb.s, Math.min(hsb.b + percent,1));
   },

   blend: function(other) {
      this.rgb.r = Math.floor((this.rgb.r + other.rgb.r)/2);
      this.rgb.g = Math.floor((this.rgb.g + other.rgb.g)/2);
      this.rgb.b = Math.floor((this.rgb.b + other.rgb.b)/2);
   },

   isBright: function() {
      var hsb = this.asHSB();
      return this.asHSB().b > 0.5;
   },

   isDark: function() {
      return ! this.isBright();
   },

   asRGB: function() {
      return "rgb(" + this.rgb.r + "," + this.rgb.g + "," + this.rgb.b + ")";
   },

   asHex: function() {
      return "#" + this.rgb.r.toColorPart() + this.rgb.g.toColorPart() + this.rgb.b.toColorPart();
   },

   asHSB: function() {
      return Rico.Color.RGBtoHSB(this.rgb.r, this.rgb.g, this.rgb.b);
   },

   toString: function() {
      return this.asHex();
   }

};

Rico.Color.createFromHex = function(hexCode) {

   if ( hexCode.indexOf('#') == 0 )
      hexCode = hexCode.substring(1);
   var red   = hexCode.substring(0,2);
   var green = hexCode.substring(2,4);
   var blue  = hexCode.substring(4,6);
   return new Rico.Color( parseInt(red,16), parseInt(green,16), parseInt(blue,16) );
}

/**
 * Factory method for creating a color from the background of
 * an HTML element.
 */
Rico.Color.createColorFromBackground = function(elem) {

   var actualColor = RicoUtil.getElementsComputedStyle($(elem), "backgroundColor", "background-color");

   if ( actualColor == "transparent" && elem.parent )
      return Rico.Color.createColorFromBackground(elem.parent);

   if ( actualColor == null )
      return new Rico.Color(255,255,255);

   if ( actualColor.indexOf("rgb(") == 0 ) {
      var colors = actualColor.substring(4, actualColor.length - 1 );
      var colorArray = colors.split(",");
      return new Rico.Color( parseInt( colorArray[0] ),
                            parseInt( colorArray[1] ),
                            parseInt( colorArray[2] )  );

   }
   else if ( actualColor.indexOf("#") == 0 ) {
      var redPart   = parseInt(actualColor.substring(1,3), 16);
      var greenPart = parseInt(actualColor.substring(3,5), 16);
      var bluePart  = parseInt(actualColor.substring(5), 16);
      return new Rico.Color( redPart, greenPart, bluePart );
   }
   else
      return new Rico.Color(255,255,255);
}

Rico.Color.HSBtoRGB = function(hue, saturation, brightness) {

   var red   = 0;
	var green = 0;
	var blue  = 0;

   if (saturation == 0) {
      red = parseInt(brightness * 255.0 + 0.5);
	   green = red;
	   blue = red;
	}
	else {
      var h = (hue - Math.floor(hue)) * 6.0;
      var f = h - Math.floor(h);
      var p = brightness * (1.0 - saturation);
      var q = brightness * (1.0 - saturation * f);
      var t = brightness * (1.0 - (saturation * (1.0 - f)));

      switch (parseInt(h)) {
         case 0:
            red   = (brightness * 255.0 + 0.5);
            green = (t * 255.0 + 0.5);
            blue  = (p * 255.0 + 0.5);
            break;
         case 1:
            red   = (q * 255.0 + 0.5);
            green = (brightness * 255.0 + 0.5);
            blue  = (p * 255.0 + 0.5);
            break;
         case 2:
            red   = (p * 255.0 + 0.5);
            green = (brightness * 255.0 + 0.5);
            blue  = (t * 255.0 + 0.5);
            break;
         case 3:
            red   = (p * 255.0 + 0.5);
            green = (q * 255.0 + 0.5);
            blue  = (brightness * 255.0 + 0.5);
            break;
         case 4:
            red   = (t * 255.0 + 0.5);
            green = (p * 255.0 + 0.5);
            blue  = (brightness * 255.0 + 0.5);
            break;
          case 5:
            red   = (brightness * 255.0 + 0.5);
            green = (p * 255.0 + 0.5);
            blue  = (q * 255.0 + 0.5);
            break;
	    }
	}

   return { r : parseInt(red), g : parseInt(green) , b : parseInt(blue) };
}

Rico.Color.RGBtoHSB = function(r, g, b) {

   var hue;
   var saturaton;
   var brightness;

   var cmax = (r > g) ? r : g;
   if (b > cmax)
      cmax = b;

   var cmin = (r < g) ? r : g;
   if (b < cmin)
      cmin = b;

   brightness = cmax / 255.0;
   if (cmax != 0)
      saturation = (cmax - cmin)/cmax;
   else
      saturation = 0;

   if (saturation == 0)
      hue = 0;
   else {
      var redc   = (cmax - r)/(cmax - cmin);
    	var greenc = (cmax - g)/(cmax - cmin);
    	var bluec  = (cmax - b)/(cmax - cmin);

    	if (r == cmax)
    	   hue = bluec - greenc;
    	else if (g == cmax)
    	   hue = 2.0 + redc - bluec;
      else
    	   hue = 4.0 + greenc - redc;

    	hue = hue / 6.0;
    	if (hue < 0)
    	   hue = hue + 1.0;
   }

   return { h : hue, s : saturation, b : brightness };
}


//-------------------- ricoCorner.js

Rico.Corner = {

   round: function(e, options) {
      var e = $(e);
      this._setOptions(options);

      var color = this.options.color;
      if ( this.options.color == "fromElement" )
         color = this._background(e);

      var bgColor = this.options.bgColor;
      if ( this.options.bgColor == "fromParent" )
         bgColor = this._background(e.offsetParent);

      this._roundCornersImpl(e, color, bgColor);
   },

   _roundCornersImpl: function(e, color, bgColor) {
      if(this.options.border)
         this._renderBorder(e,bgColor);
      if(this._isTopRounded())
         this._roundTopCorners(e,color,bgColor);
      if(this._isBottomRounded())
         this._roundBottomCorners(e,color,bgColor);
   },

   _renderBorder: function(el,bgColor) {
      var borderValue = "1px solid " + this._borderColor(bgColor);
      var borderL = "border-left: "  + borderValue;
      var borderR = "border-right: " + borderValue;
      var style   = "style='" + borderL + ";" + borderR +  "'";
      el.innerHTML = "<div " + style + ">" + el.innerHTML + "</div>"
   },

   _roundTopCorners: function(el, color, bgColor) {
      var corner = this._createCorner(bgColor);
      for(var i=0 ; i < this.options.numSlices ; i++ )
         corner.appendChild(this._createCornerSlice(color,bgColor,i,"top"));
      el.style.paddingTop = 0;
      el.insertBefore(corner,el.firstChild);
   },

   _roundBottomCorners: function(el, color, bgColor) {
      var corner = this._createCorner(bgColor);
      for(var i=(this.options.numSlices-1) ; i >= 0 ; i-- )
         corner.appendChild(this._createCornerSlice(color,bgColor,i,"bottom"));
      el.style.paddingBottom = 0;
      el.appendChild(corner);
   },

   _createCorner: function(bgColor) {
      var corner = document.createElement("div");
      corner.style.backgroundColor = (this._isTransparent() ? "transparent" : bgColor);
      return corner;
   },

   _createCornerSlice: function(color,bgColor, n, position) {
      var slice = document.createElement("span");

      var inStyle = slice.style;
      inStyle.backgroundColor = color;
      inStyle.display  = "block";
      inStyle.height   = "1px";
      inStyle.overflow = "hidden";
      inStyle.fontSize = "1px";

      var borderColor = this._borderColor(color,bgColor);
      if ( this.options.border && n == 0 ) {
         inStyle.borderTopStyle    = "solid";
         inStyle.borderTopWidth    = "1px";
         inStyle.borderLeftWidth   = "0px";
         inStyle.borderRightWidth  = "0px";
         inStyle.borderBottomWidth = "0px";
         inStyle.height            = "0px"; // assumes css compliant box model
         inStyle.borderColor       = borderColor;
      }
      else if(borderColor) {
         inStyle.borderColor = borderColor;
         inStyle.borderStyle = "solid";
         inStyle.borderWidth = "0px 1px";
      }

      if ( !this.options.compact && (n == (this.options.numSlices-1)) )
         inStyle.height = "2px";

      this._setMargin(slice, n, position);
      this._setBorder(slice, n, position);

      return slice;
   },

   _setOptions: function(options) {
      this.options = {
         corners : "all",
         color   : "fromElement",
         bgColor : "fromParent",
         blend   : true,
         border  : false,
         compact : false
      }.extend(options || {});

      this.options.numSlices = this.options.compact ? 2 : 4;
      if ( this._isTransparent() )
         this.options.blend = false;
   },

   _whichSideTop: function() {
      if ( this._hasString(this.options.corners, "all", "top") )
         return "";

      if ( this.options.corners.indexOf("tl") >= 0 && this.options.corners.indexOf("tr") >= 0 )
         return "";

      if (this.options.corners.indexOf("tl") >= 0)
         return "left";
      else if (this.options.corners.indexOf("tr") >= 0)
          return "right";
      return "";
   },

   _whichSideBottom: function() {
      if ( this._hasString(this.options.corners, "all", "bottom") )
         return "";

      if ( this.options.corners.indexOf("bl")>=0 && this.options.corners.indexOf("br")>=0 )
         return "";

      if(this.options.corners.indexOf("bl") >=0)
         return "left";
      else if(this.options.corners.indexOf("br")>=0)
         return "right";
      return "";
   },

   _borderColor : function(color,bgColor) {
      if ( color == "transparent" )
         return bgColor;
      else if ( this.options.border )
         return this.options.border;
      else if ( this.options.blend )
         return this._blend( bgColor, color );
      else
         return "";
   },


   _setMargin: function(el, n, corners) {
      var marginSize = this._marginSize(n);
      var whichSide = corners == "top" ? this._whichSideTop() : this._whichSideBottom();

      if ( whichSide == "left" ) {
         el.style.marginLeft = marginSize + "px"; el.style.marginRight = "0px";
      }
      else if ( whichSide == "right" ) {
         el.style.marginRight = marginSize + "px"; el.style.marginLeft  = "0px";
      }
      else {
         el.style.marginLeft = marginSize + "px"; el.style.marginRight = marginSize + "px";
      }
   },

   _setBorder: function(el,n,corners) {
      var borderSize = this._borderSize(n);
      var whichSide = corners == "top" ? this._whichSideTop() : this._whichSideBottom();

      if ( whichSide == "left" ) {
         el.style.borderLeftWidth = borderSize + "px"; el.style.borderRightWidth = "0px";
      }
      else if ( whichSide == "right" ) {
         el.style.borderRightWidth = borderSize + "px"; el.style.borderLeftWidth  = "0px";
      }
      else {
         el.style.borderLeftWidth = borderSize + "px"; el.style.borderRightWidth = borderSize + "px";
      }
   },

   _marginSize: function(n) {
      if ( this._isTransparent() )
         return 0;

      var marginSizes          = [ 5, 3, 2, 1 ];
      var blendedMarginSizes   = [ 3, 2, 1, 0 ];
      var compactMarginSizes   = [ 2, 1 ];
      var smBlendedMarginSizes = [ 1, 0 ];

      if ( this.options.compact && this.options.blend )
         return smBlendedMarginSizes[n];
      else if ( this.options.compact )
         return compactMarginSizes[n];
      else if ( this.options.blend )
         return blendedMarginSizes[n];
      else
         return marginSizes[n];
   },

   _borderSize: function(n) {
      var transparentBorderSizes = [ 5, 3, 2, 1 ];
      var blendedBorderSizes     = [ 2, 1, 1, 1 ];
      var compactBorderSizes     = [ 1, 0 ];
      var actualBorderSizes      = [ 0, 2, 0, 0 ];

      if ( this.options.compact && (this.options.blend || this._isTransparent()) )
         return 1;
      else if ( this.options.compact )
         return compactBorderSizes[n];
      else if ( this.options.blend )
         return blendedBorderSizes[n];
      else if ( this.options.border )
         return actualBorderSizes[n];
      else if ( this._isTransparent() )
         return transparentBorderSizes[n];
      return 0;
   },

   _hasString: function(str) { for(var i=1 ; i<arguments.length ; i++) if (str.indexOf(arguments[i]) >= 0) return true; return false; },
   _blend: function(c1, c2) { var cc1 = Rico.Color.createFromHex(c1); cc1.blend(Rico.Color.createFromHex(c2)); return cc1; },
   _background: function(el) { try { return Rico.Color.createColorFromBackground(el).asHex(); } catch(err) { return "#ffffff"; } },
   _isTransparent: function() { return this.options.color == "transparent"; },
   _isTopRounded: function() { return this._hasString(this.options.corners, "all", "top", "tl", "tr"); },
   _isBottomRounded: function() { return this._hasString(this.options.corners, "all", "bottom", "bl", "br"); },
   _hasSingleTextChild: function(el) { return el.childNodes.length == 1 && el.childNodes[0].nodeType == 3; }
}


//-------------------- ricoDragAndDrop.js
Rico.DragAndDrop = Class.create();

Rico.DragAndDrop.prototype = {

   initialize: function() {
      this.dropZones                = new Array();
      this.draggables               = new Array();
      this.currentDragObjects       = new Array();
      this.dragElement              = null;
      this.lastSelectedDraggable    = null;
      this.currentDragObjectVisible = false;
      this.interestedInMotionEvents = false;
   },

   registerDropZone: function(aDropZone) {
      this.dropZones[ this.dropZones.length ] = aDropZone;
   },

   deregisterDropZone: function(aDropZone) {
      var newDropZones = new Array();
      var j = 0;
      for ( var i = 0 ; i < this.dropZones.length ; i++ ) {
         if ( this.dropZones[i] != aDropZone )
            newDropZones[j++] = this.dropZones[i];
      }

      this.dropZones = newDropZones;
   },

   clearDropZones: function() {
      this.dropZones = new Array();
   },

   registerDraggable: function( aDraggable ) {
      this.draggables[ this.draggables.length ] = aDraggable;
      this._addMouseDownHandler( aDraggable );
   },

   clearSelection: function() {
      for ( var i = 0 ; i < this.currentDragObjects.length ; i++ )
         this.currentDragObjects[i].deselect();
      this.currentDragObjects = new Array();
      this.lastSelectedDraggable = null;
   },

   hasSelection: function() {
      return this.currentDragObjects.length > 0;
   },

   setStartDragFromElement: function( e, mouseDownElement ) {
      this.origPos = RicoUtil.toDocumentPosition(mouseDownElement);
      this.startx = e.screenX - this.origPos.x
      this.starty = e.screenY - this.origPos.y
      //this.startComponentX = e.layerX ? e.layerX : e.offsetX;
      //this.startComponentY = e.layerY ? e.layerY : e.offsetY;
      //this.adjustedForDraggableSize = false;

      this.interestedInMotionEvents = this.hasSelection();
      this._terminateEvent(e);
   },

   updateSelection: function( draggable, extendSelection ) {
      if ( ! extendSelection )
         this.clearSelection();

      if ( draggable.isSelected() ) {
         this.currentDragObjects.removeItem(draggable);
         draggable.deselect();
         if ( draggable == this.lastSelectedDraggable )
            this.lastSelectedDraggable = null;
      }
      else {
         this.currentDragObjects[ this.currentDragObjects.length ] = draggable;
         draggable.select();
         this.lastSelectedDraggable = draggable;
      }
   },

   _mouseDownHandler: function(e) {
      if ( arguments.length == 0 )
         e = event;

      // if not button 1 ignore it...
      var nsEvent = e.which != undefined;
      if ( (nsEvent && e.which != 1) || (!nsEvent && e.button != 1))
         return;

      var eventTarget      = e.target ? e.target : e.srcElement;
      var draggableObject  = eventTarget.draggable;

      var candidate = eventTarget;
      while (draggableObject == null && candidate.parentNode) {
         candidate = candidate.parentNode;
         draggableObject = candidate.draggable;
      }
   
      if ( draggableObject == null )
         return;

      this.updateSelection( draggableObject, e.ctrlKey );

      // clear the drop zones postion cache...
      if ( this.hasSelection() )
         for ( var i = 0 ; i < this.dropZones.length ; i++ )
            this.dropZones[i].clearPositionCache();

      this.setStartDragFromElement( e, draggableObject.getMouseDownHTMLElement() );
   },


   _mouseMoveHandler: function(e) {
      var nsEvent = e.which != undefined;
      if ( !this.interestedInMotionEvents ) {
         this._terminateEvent(e);
         return;
      }

      if ( ! this.hasSelection() )
         return;

      if ( ! this.currentDragObjectVisible )
         this._startDrag(e);

      if ( !this.activatedDropZones )
         this._activateRegisteredDropZones();

      //if ( !this.adjustedForDraggableSize )
      //   this._adjustForDraggableSize(e);

      this._updateDraggableLocation(e);
      this._updateDropZonesHover(e);

      this._terminateEvent(e);
   },

   _makeDraggableObjectVisible: function(e)
   {
      if ( !this.hasSelection() )
         return;

      var dragElement;
      if ( this.currentDragObjects.length > 1 )
         dragElement = this.currentDragObjects[0].getMultiObjectDragGUI(this.currentDragObjects);
      else
         dragElement = this.currentDragObjects[0].getSingleObjectDragGUI();

      // go ahead and absolute position it...
      if ( RicoUtil.getElementsComputedStyle(dragElement, "position")  != "absolute" )
         dragElement.style.position = "absolute";

      // need to parent him into the document...
      if ( dragElement.parentNode == null || dragElement.parentNode.nodeType == 11 )
         document.body.appendChild(dragElement);

      this.dragElement = dragElement;
      this._updateDraggableLocation(e);

      this.currentDragObjectVisible = true;
   },

   /**
   _adjustForDraggableSize: function(e) {
      var dragElementWidth  = this.dragElement.offsetWidth;
      var dragElementHeight = this.dragElement.offsetHeight;
      if ( this.startComponentX > dragElementWidth )
         this.startx -= this.startComponentX - dragElementWidth + 2;
      if ( e.offsetY ) {
         if ( this.startComponentY > dragElementHeight )
            this.starty -= this.startComponentY - dragElementHeight + 2;
      }
      this.adjustedForDraggableSize = true;
   },
   **/

   _updateDraggableLocation: function(e) {
      var dragObjectStyle = this.dragElement.style;
      dragObjectStyle.left = (e.screenX - this.startx) + "px"
      dragObjectStyle.top  = (e.screenY - this.starty) + "px";
   },

   _updateDropZonesHover: function(e) {
      var n = this.dropZones.length;
      for ( var i = 0 ; i < n ; i++ ) {
         if ( ! this._mousePointInDropZone( e, this.dropZones[i] ) )
            this.dropZones[i].hideHover();
      }

      for ( var i = 0 ; i < n ; i++ ) {
         if ( this._mousePointInDropZone( e, this.dropZones[i] ) ) {
            if ( this.dropZones[i].canAccept(this.currentDragObjects) )
               this.dropZones[i].showHover();
         }
      }
   },

   _startDrag: function(e) {
      for ( var i = 0 ; i < this.currentDragObjects.length ; i++ )
         this.currentDragObjects[i].startDrag();

      this._makeDraggableObjectVisible(e);
   },

   _mouseUpHandler: function(e) {
      if ( ! this.hasSelection() )
         return;

      var nsEvent = e.which != undefined;
      if ( (nsEvent && e.which != 1) || (!nsEvent && e.button != 1))
         return;

      this.interestedInMotionEvents = false;

      if ( this.dragElement == null ) {
         this._terminateEvent(e);
         return;
      }

      if ( this._placeDraggableInDropZone(e) )
         this._completeDropOperation(e);
      else {
         this._terminateEvent(e);
         new Effect.Position( this.dragElement,
                              this.origPos.x,
                              this.origPos.y,
                              200,
                              20,
                              { complete : this._doCancelDragProcessing.bind(this) } );
      }
   },

   _completeDropOperation: function(e) {
      if ( this.dragElement != this.currentDragObjects[0].getMouseDownHTMLElement() ) {
         if ( this.dragElement.parentNode != null )
            this.dragElement.parentNode.removeChild(this.dragElement);
      }

      this._deactivateRegisteredDropZones();
      this._endDrag();
      this.clearSelection();
      this.dragElement = null;
      this.currentDragObjectVisible = false;
      this._terminateEvent(e);
   },

   _doCancelDragProcessing: function() {
      this._cancelDrag();

      if ( this.dragElement != this.currentDragObjects[0].getMouseDownHTMLElement() ) {
         if ( this.dragElement.parentNode != null ) {
            this.dragElement.parentNode.removeChild(this.dragElement);
         }
      }

      this._deactivateRegisteredDropZones();
      this.dragElement = null;
      this.currentDragObjectVisible = false;
   },

   _placeDraggableInDropZone: function(e) {
      var foundDropZone = false;
      var n = this.dropZones.length;
      for ( var i = 0 ; i < n ; i++ ) {
         if ( this._mousePointInDropZone( e, this.dropZones[i] ) ) {
            if ( this.dropZones[i].canAccept(this.currentDragObjects) ) {
               this.dropZones[i].hideHover();
               this.dropZones[i].accept(this.currentDragObjects);
               foundDropZone = true;
               break;
            }
         }
      }

      return foundDropZone;
   },

   _cancelDrag: function() {
      for ( var i = 0 ; i < this.currentDragObjects.length ; i++ )
         this.currentDragObjects[i].cancelDrag();
   },

   _endDrag: function() {
      for ( var i = 0 ; i < this.currentDragObjects.length ; i++ )
         this.currentDragObjects[i].endDrag();
   },

   _mousePointInDropZone: function( e, dropZone ) {

      var absoluteRect = dropZone.getAbsoluteRect();

      return e.clientX  > absoluteRect.left  &&
             e.clientX  < absoluteRect.right &&
             e.clientY  > absoluteRect.top   &&
             e.clientY  < absoluteRect.bottom;
   },

   _addMouseDownHandler: function( aDraggable )
   {
      var htmlElement = aDraggable.getMouseDownHTMLElement();
      if ( htmlElement != null ) {
         htmlElement.draggable = aDraggable;
         this._addMouseDownEvent( htmlElement );
      }
   },

   _activateRegisteredDropZones: function() {
      var n = this.dropZones.length;
      for ( var i = 0 ; i < n ; i++ ) {
         var dropZone = this.dropZones[i];
         if ( dropZone.canAccept(this.currentDragObjects) )
            dropZone.activate();
      }

      this.activatedDropZones = true;
   },

   _deactivateRegisteredDropZones: function() {
      var n = this.dropZones.length;
      for ( var i = 0 ; i < n ; i++ )
         this.dropZones[i].deactivate();
      this.activatedDropZones = false;
   },

   _addMouseDownEvent: function( htmlElement ) {
      if ( typeof document.implementation != "undefined" &&
         document.implementation.hasFeature("HTML",   "1.0") &&
         document.implementation.hasFeature("Events", "2.0") &&
         document.implementation.hasFeature("CSS",    "2.0") ) {
         htmlElement.addEventListener("mousedown", this._mouseDownHandler.bindAsEventListener(this), false);
      }
      else {
         htmlElement.attachEvent( "onmousedown", this._mouseDownHandler.bindAsEventListener(this) );
      }
   },

   _terminateEvent: function(e) {
      if ( e.stopPropagation != undefined )
         e.stopPropagation();
      else if ( e.cancelBubble != undefined )
         e.cancelBubble = true;

      if ( e.preventDefault != undefined )
         e.preventDefault();
      else
         e.returnValue = false;
   },

   initializeEventHandlers: function() {
      if ( typeof document.implementation != "undefined" &&
         document.implementation.hasFeature("HTML",   "1.0") &&
         document.implementation.hasFeature("Events", "2.0") &&
         document.implementation.hasFeature("CSS",    "2.0") ) {
         document.addEventListener("mouseup",   this._mouseUpHandler.bindAsEventListener(this),  false);
         document.addEventListener("mousemove", this._mouseMoveHandler.bindAsEventListener(this), false);
      }
      else {
         document.attachEvent( "onmouseup",   this._mouseUpHandler.bindAsEventListener(this) );
         document.attachEvent( "onmousemove", this._mouseMoveHandler.bindAsEventListener(this) );
      }
   }
}

//var dndMgr = new Rico.DragAndDrop();
//dndMgr.initializeEventHandlers();


//-------------------- ricoDraggable.js
Rico.Draggable = Class.create();

Rico.Draggable.prototype = {

   initialize: function( type, htmlElement ) {
      this.type          = type;
      this.htmlElement   = $(htmlElement);
      this.selected      = false;
   },

   /**
    *   Returns the HTML element that should have a mouse down event
    *   added to it in order to initiate a drag operation
    *
    **/
   getMouseDownHTMLElement: function() {
      return this.htmlElement;
   },

   select: function() {
      this.selected = true;

      if ( this.showingSelected )
         return;

      var htmlElement = this.getMouseDownHTMLElement();

      var color = Rico.Color.createColorFromBackground(htmlElement);
      color.isBright() ? color.darken(0.033) : color.brighten(0.033);

      this.saveBackground = RicoUtil.getElementsComputedStyle(htmlElement, "backgroundColor", "background-color");
      htmlElement.style.backgroundColor = color.asHex();
      this.showingSelected = true;
   },

   deselect: function() {
      this.selected = false;
      if ( !this.showingSelected )
         return;

      var htmlElement = this.getMouseDownHTMLElement();

      htmlElement.style.backgroundColor = this.saveBackground;
      this.showingSelected = false;
   },

   isSelected: function() {
      return this.selected;
   },

   startDrag: function() {
   },

   cancelDrag: function() {
   },

   endDrag: function() {
   },

   getSingleObjectDragGUI: function() {
      return this.htmlElement;
   },

   getMultiObjectDragGUI: function( draggables ) {
      return this.htmlElement;
   },

   getDroppedGUI: function() {
      return this.htmlElement;
   },

   toString: function() {
      return this.type + ":" + this.htmlElement + ":";
   }

}


//-------------------- ricoDropzone.js
Rico.Dropzone = Class.create();

Rico.Dropzone.prototype = {

   initialize: function( htmlElement ) {
      this.htmlElement  = $(htmlElement);
      this.absoluteRect = null;
   },

   getHTMLElement: function() {
      return this.htmlElement;
   },

   clearPositionCache: function() {
      this.absoluteRect = null;
   },

   getAbsoluteRect: function() {
      if ( this.absoluteRect == null ) {
         var htmlElement = this.getHTMLElement();
         var pos = RicoUtil.toViewportPosition(htmlElement);

         this.absoluteRect = {
            top:    pos.y,
            left:   pos.x,
            bottom: pos.y + htmlElement.offsetHeight,
            right:  pos.x + htmlElement.offsetWidth
         };
      }
      return this.absoluteRect;
   },

   activate: function() {
      var htmlElement = this.getHTMLElement();
      if (htmlElement == null  || this.showingActive)
         return;

      this.showingActive = true;
      this.saveBackgroundColor = htmlElement.style.backgroundColor;

      var fallbackColor = "#ffea84";
      var currentColor = Rico.Color.createColorFromBackground(htmlElement);
      if ( currentColor == null )
         htmlElement.style.backgroundColor = fallbackColor;
      else {
         currentColor.isBright() ? currentColor.darken(0.2) : currentColor.brighten(0.2);
         htmlElement.style.backgroundColor = currentColor.asHex();
      }
   },

   deactivate: function() {
      var htmlElement = this.getHTMLElement();
      if (htmlElement == null || !this.showingActive)
         return;

      htmlElement.style.backgroundColor = this.saveBackgroundColor;
      this.showingActive = false;
      this.saveBackgroundColor = null;
   },

   showHover: function() {
      var htmlElement = this.getHTMLElement();
      if ( htmlElement == null || this.showingHover )
         return;

      this.saveBorderWidth = htmlElement.style.borderWidth;
      this.saveBorderStyle = htmlElement.style.borderStyle;
      this.saveBorderColor = htmlElement.style.borderColor;

      this.showingHover = true;
      htmlElement.style.borderWidth = "1px";
      htmlElement.style.borderStyle = "solid";
      //htmlElement.style.borderColor = "#ff9900";
      htmlElement.style.borderColor = "#ffff00";
   },

   hideHover: function() {
      var htmlElement = this.getHTMLElement();
      if ( htmlElement == null || !this.showingHover )
         return;

      htmlElement.style.borderWidth = this.saveBorderWidth;
      htmlElement.style.borderStyle = this.saveBorderStyle;
      htmlElement.style.borderColor = this.saveBorderColor;
      this.showingHover = false;
   },

   canAccept: function(draggableObjects) {
      return true;
   },

   accept: function(draggableObjects) {
      var htmlElement = this.getHTMLElement();
      if ( htmlElement == null )
         return;

      n = draggableObjects.length;
      for ( var i = 0 ; i < n ; i++ )
      {
         var theGUI = draggableObjects[i].getDroppedGUI();
         if ( RicoUtil.getElementsComputedStyle( theGUI, "position" ) == "absolute" )
         {
            theGUI.style.position = "static";
            theGUI.style.top = "";
            theGUI.style.top = "";
         }
         htmlElement.appendChild(theGUI);
      }
   }
}


//-------------------- ricoEffects.js

/**
  *  Use the Effect namespace for effects.  If using scriptaculous effects
  *  this will already be defined, otherwise we'll just create an empty
  *  object for it...
 **/
if ( window.Effect == undefined )
   Effect = {};

Effect.SizeAndPosition = Class.create();
Effect.SizeAndPosition.prototype = {

   initialize: function(element, x, y, w, h, duration, steps, options) {
      this.element = $(element);
      this.x = x;
      this.y = y;
      this.w = w;
      this.h = h;
      this.duration = duration;
      this.steps    = steps;
      this.options  = arguments[7] || {};

      this.sizeAndPosition();
   },

   sizeAndPosition: function() {
      if (this.isFinished()) {
         if(this.options.complete) this.options.complete(this);
         return;
      }

      if (this.timer)
         clearTimeout(this.timer);

      var stepDuration = Math.round(this.duration/this.steps) ;

      // Get original values: x,y = top left corner;  w,h = width height
      var currentX = this.element.offsetLeft;
      var currentY = this.element.offsetTop;
      var currentW = this.element.offsetWidth;
      var currentH = this.element.offsetHeight;

      // If values not set, or zero, we do not modify them, and take original as final as well
      this.x = (this.x) ? this.x : currentX;
      this.y = (this.y) ? this.y : currentY;
      this.w = (this.w) ? this.w : currentW;
      this.h = (this.h) ? this.h : currentH;

      // how much do we need to modify our values for each step?
      var difX = this.steps >  0 ? (this.x - currentX)/this.steps : 0;
      var difY = this.steps >  0 ? (this.y - currentY)/this.steps : 0;
      var difW = this.steps >  0 ? (this.w - currentW)/this.steps : 0;
      var difH = this.steps >  0 ? (this.h - currentH)/this.steps : 0;

      this.moveBy(difX, difY);
      this.resizeBy(difW, difH);

      this.duration -= stepDuration;
      this.steps--;

      this.timer = setTimeout(this.sizeAndPosition.bind(this), stepDuration);
   },

   isFinished: function() {
      return this.steps <= 0;
   },

   moveBy: function( difX, difY ) {
      var currentLeft = this.element.offsetLeft;
      var currentTop  = this.element.offsetTop;
      var intDifX     = parseInt(difX);
      var intDifY     = parseInt(difY);

      var style = this.element.style;
      if ( intDifX != 0 )
         style.left = (currentLeft + intDifX) + "px";
      if ( intDifY != 0 )
         style.top  = (currentTop + intDifY) + "px";
   },

   resizeBy: function( difW, difH ) {
      var currentWidth  = this.element.offsetWidth;
      var currentHeight = this.element.offsetHeight;
      var intDifW       = parseInt(difW);
      var intDifH       = parseInt(difH);

      var style = this.element.style;
      if ( intDifW != 0 )
         style.width   = (currentWidth  + intDifW) + "px";
      if ( intDifH != 0 )
         style.height  = (currentHeight + intDifH) + "px";
   }
}

Effect.Size = Class.create();
Effect.Size.prototype = {

   initialize: function(element, w, h, duration, steps, options) {
      new Effect.SizeAndPosition(element, null, null, w, h, duration, steps, options);
  }
}

Effect.Position = Class.create();
Effect.Position.prototype = {

   initialize: function(element, x, y, duration, steps, options) {
      new Effect.SizeAndPosition(element, x, y, null, null, duration, steps, options);
  }
}

Effect.Round = Class.create();
Effect.Round.prototype = {

   initialize: function(tagName, className, options) {
      var elements = document.getElementsByTagAndClassName(tagName,className);
      for ( var i = 0 ; i < elements.length ; i++ )
         Rico.Corner.round( elements[i], options );
   }
};

Effect.FadeTo = Class.create();
Effect.FadeTo.prototype = {

   initialize: function( element, opacity, duration, steps, options) {
      this.element  = $(element);
      this.opacity  = opacity;
      this.duration = duration;
      this.steps    = steps;
      this.options  = arguments[4] || {};
      this.fadeTo();
   },

   fadeTo: function() {
      if (this.isFinished()) {
         if(this.options.complete) this.options.complete(this);
         return;
      }

      if (this.timer)
         clearTimeout(this.timer);

      var stepDuration = Math.round(this.duration/this.steps) ;
      var currentOpacity = this.getElementOpacity();
      var delta = this.steps > 0 ? (this.opacity - currentOpacity)/this.steps : 0;

      this.changeOpacityBy(delta);
      this.duration -= stepDuration;
      this.steps--;

      this.timer = setTimeout(this.fadeTo.bind(this), stepDuration);
   },

   changeOpacityBy: function(v) {
      var currentOpacity = this.getElementOpacity();
      var newOpacity = Math.max(0, Math.min(currentOpacity+v, 1));
      this.element.ricoOpacity = newOpacity;

      this.element.style.filter = "alpha(opacity:"+Math.round(newOpacity*100)+")";
      this.element.style.opacity = newOpacity; /*//*/;
   },

   isFinished: function() {
      return this.steps <= 0;
   },

   getElementOpacity: function() {
      if ( this.element.ricoOpacity == undefined ) {
         var opacity;
         if ( this.element.currentStyle ) {
            opacity = this.element.currentStyle.opacity;
         }
         else if ( document.defaultView.getComputedStyle != undefined ) {
            var computedStyle = document.defaultView.getComputedStyle;
            opacity = computedStyle(this.element, null).getPropertyValue('opacity');
         }

         this.element.ricoOpacity = opacity != undefined ? opacity : 1.0;
      }

      return parseFloat(this.element.ricoOpacity);
   }
}

Effect.AccordionSize = Class.create();

Effect.AccordionSize.prototype = {

   initialize: function(e1, e2, start, end, duration, steps, options) {
      this.e1       = $(e1);
      this.e2       = $(e2);
      this.start    = start;
      this.end      = end;
      this.duration = duration;
      this.steps    = steps;
      this.options  = arguments[6] || {};

      this.accordionSize();
   },

   accordionSize: function() {

      if (this.isFinished()) {
         // just in case there are round errors or such...
         this.e1.style.height = this.start + "px";
         this.e2.style.height = this.end + "px";

         if(this.options.complete)
            this.options.complete(this);
         return;
      }

      if (this.timer)
         clearTimeout(this.timer);

      var stepDuration = Math.round(this.duration/this.steps) ;

      var diff = this.steps > 0 ? (parseInt(this.e1.offsetHeight) - this.start)/this.steps : 0;
      this.resizeBy(diff);

      this.duration -= stepDuration;
      this.steps--;

      this.timer = setTimeout(this.accordionSize.bind(this), stepDuration);
   },

   isFinished: function() {
      return this.steps <= 0;
   },

   resizeBy: function(diff) {
      var h1Height = this.e1.offsetHeight;
      var h2Height = this.e2.offsetHeight;
      var intDiff = parseInt(diff);
      if ( diff != 0 ) {
         this.e1.style.height = (h1Height - intDiff) + "px";
         this.e2.style.height = (h2Height + intDiff) + "px";
      }
   }

};


//-------------------- ricoLiveGrid.js

// Rico.LiveGridMetaData -----------------------------------------------------

Rico.LiveGridMetaData = Class.create();

Rico.LiveGridMetaData.prototype = {

   initialize: function( pageSize, totalRows, columnCount, options ) {
      this.pageSize  = pageSize;
      this.totalRows = totalRows;
      this.setOptions(options);
      this.scrollArrowHeight = 16;
      this.columnCount = columnCount;
   },

   setOptions: function(options) {
      this.options = {
         largeBufferSize    : 7.0,   // 7 pages
         nearLimitFactor    : 0.2    // 20% of buffer
      }.extend(options || {});
   },

   getPageSize: function() {
      return this.pageSize;
   },

   getTotalRows: function() {
      return this.totalRows;
   },

   setTotalRows: function(n) {
      this.totalRows = n;
   },

   getLargeBufferSize: function() {
      return parseInt(this.options.largeBufferSize * this.pageSize);
   },

   getLimitTolerance: function() {
      return parseInt(this.getLargeBufferSize() * this.options.nearLimitFactor);
   }
};

// Rico.LiveGridScroller -----------------------------------------------------

Rico.LiveGridScroller = Class.create();

Rico.LiveGridScroller.prototype = {

   initialize: function(liveGrid, viewPort) {
      this.isIE = navigator.userAgent.toLowerCase().indexOf("msie") >= 0;
      this.liveGrid = liveGrid;
      this.metaData = liveGrid.metaData;
      this.createScrollBar();
      this.scrollTimeout = null;
      this.lastScrollPos = 0;
      this.viewPort = viewPort;
      this.rows = new Array();
   },

   isUnPlugged: function() {
      return this.scrollerDiv.onscroll == null;
   },

   plugin: function() {
      this.scrollerDiv.onscroll = this.handleScroll.bindAsEventListener(this);
   },

   unplug: function() {
      this.scrollerDiv.onscroll = null;
   },

   sizeIEHeaderHack: function() {
      if ( !this.isIE ) return;
      var headerTable = $(this.liveGrid.tableId + "_header");
      if ( headerTable )
         headerTable.rows[0].cells[0].style.width =
            (headerTable.rows[0].cells[0].offsetWidth + 1) + "px";
   },

   createScrollBar: function() {
      var visibleHeight = this.liveGrid.viewPort.visibleHeight();
      // create the outer div...
      this.scrollerDiv  = document.createElement("div");
      var scrollerStyle = this.scrollerDiv.style;
      scrollerStyle.borderRight = "1px solid #ababab"; // hard coded color!!!
      scrollerStyle.position    = "relative";
      scrollerStyle.left        = this.isIE ? "-6px" : "-3px";
      scrollerStyle.width       = "19px";
      scrollerStyle.height      = visibleHeight + "px";
      scrollerStyle.overflow    = "auto";

      // create the inner div...
      this.heightDiv = document.createElement("div");
      this.heightDiv.style.width  = "1px";

      this.heightDiv.style.height = parseInt(visibleHeight *
                        this.metaData.getTotalRows()/this.metaData.getPageSize()) + "px" ;
      this.scrollerDiv.appendChild(this.heightDiv);
      this.scrollerDiv.onscroll = this.handleScroll.bindAsEventListener(this);

     var table = this.liveGrid.table;
     table.parentNode.parentNode.insertBefore( this.scrollerDiv, table.parentNode.nextSibling );
   },

   updateSize: function() {
      var table = this.liveGrid.table;
      var visibleHeight = this.viewPort.visibleHeight();
      this.heightDiv.style.height = parseInt(visibleHeight *
                                  this.metaData.getTotalRows()/this.metaData.getPageSize()) + "px";
   },

   rowToPixel: function(rowOffset) {
      return (rowOffset / this.metaData.getTotalRows()) * this.heightDiv.offsetHeight
   },
   
   moveScroll: function(rowOffset) {
      this.scrollerDiv.scrollTop = this.rowToPixel(rowOffset);
      if ( this.metaData.options.onscroll )
         this.metaData.options.onscroll( this.liveGrid, rowOffset );    
   },

   handleScroll: function() {
     if ( this.scrollTimeout )
         clearTimeout( this.scrollTimeout );

      var contentOffset = parseInt(this.scrollerDiv.scrollTop / this.viewPort.rowHeight);
      this.liveGrid.requestContentRefresh(contentOffset);
      this.viewPort.scrollTo(this.scrollerDiv.scrollTop);
      
      if ( this.metaData.options.onscroll )
         this.metaData.options.onscroll( this.liveGrid, contentOffset );

      this.scrollTimeout = setTimeout( this.scrollIdle.bind(this), 1200 );
   },

   scrollIdle: function() {
      if ( this.metaData.options.onscrollidle )
         this.metaData.options.onscrollidle();
   }
};

// Rico.LiveGridBuffer -----------------------------------------------------

Rico.LiveGridBuffer = Class.create();

Rico.LiveGridBuffer.prototype = {

   initialize: function(metaData, viewPort) {
      this.startPos = 0;
      this.size     = 0;
      this.metaData = metaData;
      this.rows     = new Array();
      this.updateInProgress = false;
      this.viewPort = viewPort;
      this.maxBufferSize = metaData.getLargeBufferSize() * 2;
      this.maxFetchSize = metaData.getLargeBufferSize();
      this.lastOffset = 0;
   },

   getBlankRow: function() {
      if (!this.blankRow ) {
         this.blankRow = new Array();
         for ( var i=0; i < this.metaData.columnCount ; i++ ) 
            this.blankRow[i] = "&nbsp;";
     }
     return this.blankRow;
   },
   
   loadRows: function(ajaxResponse) {
      var rowsElement = ajaxResponse.getElementsByTagName('rows')[0];
      this.updateUI = rowsElement.getAttribute("update_ui") == "true"
      var newRows = new Array()
      var trs = rowsElement.getElementsByTagName("tr");
      for ( var i=0 ; i < trs.length; i++ ) {
         var row = newRows[i] = new Array(); 
         var cells = trs[i].getElementsByTagName("td");
         for ( var j=0; j < cells.length ; j++ ) {
            var cell = cells[j];
            var convertSpaces = cell.getAttribute("convert_spaces") == "true";
            var cellContent = RicoUtil.getContentAsString(cell);
            row[j] = convertSpaces ? this.convertSpaces(cellContent) : cellContent;
            if (!row[j]) 
               row[j] = '&nbsp;';
         }
      }
      return newRows;
   },
      
   update: function(ajaxResponse, start) {
     var newRows = this.loadRows(ajaxResponse);
      if (this.rows.length == 0) { // initial load
         this.rows = newRows;
         this.size = this.rows.length;
         this.startPos = start;
         return;
      }
      if (start > this.startPos) { //appending
         if (this.startPos + this.rows.length < start) {
            this.rows =  newRows;
            this.startPos = start;//
         } else {
              this.rows = this.rows.concat( newRows.slice(0, newRows.length));
            if (this.rows.length > this.maxBufferSize) {
               var fullSize = this.rows.length;
               this.rows = this.rows.slice(this.rows.length - this.maxBufferSize, this.rows.length)
               this.startPos = this.startPos +  (fullSize - this.rows.length);
            }
         }
      } else { //prepending
         if (start + newRows.length < this.startPos) {
            this.rows =  newRows;
         } else {
            this.rows = newRows.slice(0, this.startPos).concat(this.rows);
            if (this.rows.length > this.maxBufferSize) 
               this.rows = this.rows.slice(0, this.maxBufferSize)
         }
         this.startPos =  start;
      }
      this.size = this.rows.length;
   },
   
   clear: function() {
      this.rows = new Array();
      this.startPos = 0;
      this.size = 0;
   },

   isOverlapping: function(start, size) {
      return ((start < this.endPos()) && (this.startPos < start + size)) || (this.endPos() == 0)
   },

   isInRange: function(position) {
      return (position >= this.startPos) && (position + this.metaData.getPageSize() <= this.endPos()); 
             //&& this.size()  != 0;
   },

   isNearingTopLimit: function(position) {
      return position - this.startPos < this.metaData.getLimitTolerance();
   },

   endPos: function() {
      return this.startPos + this.rows.length;
   },
   
   isNearingBottomLimit: function(position) {
      return this.endPos() - (position + this.metaData.getPageSize()) < this.metaData.getLimitTolerance();
   },

   isAtTop: function() {
      return this.startPos == 0;
   },

   isAtBottom: function() {
      return this.endPos() == this.metaData.getTotalRows();
   },

   isNearingLimit: function(position) {
      return ( !this.isAtTop()    && this.isNearingTopLimit(position)) ||
             ( !this.isAtBottom() && this.isNearingBottomLimit(position) )
   },

   getFetchSize: function(offset) {
      var adjustedOffset = this.getFetchOffset(offset);
      var adjustedSize = 0;
      if (adjustedOffset >= this.startPos) { //apending
         var endFetchOffset = this.maxFetchSize  + adjustedOffset;
         if (endFetchOffset > this.metaData.totalRows)
            endFetchOffset = this.metaData.totalRows;
         adjustedSize = endFetchOffset - adjustedOffset;   
      } else {//prepending
         var adjustedSize = this.startPos - adjustedOffset;
         if (adjustedSize > this.maxFetchSize)
            adjustedSize = this.maxFetchSize;
      }
      return adjustedSize;
   }, 

   getFetchOffset: function(offset) {
      var adjustedOffset = offset;
      if (offset > this.startPos)  //apending
         adjustedOffset = (offset > this.endPos()) ? offset :  this.endPos(); 
      else { //prepending
         if (offset + this.maxFetchSize >= this.startPos) {
            var adjustedOffset = this.startPos - this.maxFetchSize;
            if (adjustedOffset < 0)
               adjustedOffset = 0;
         }
      }
      this.lastOffset = adjustedOffset;
      return adjustedOffset;
   },

   getRows: function(start, count) {
      var begPos = start - this.startPos
      var endPos = begPos + count

      // er? need more data...
      if ( endPos > this.size )
         endPos = this.size

      var results = new Array()
      var index = 0;
      for ( var i=begPos ; i < endPos; i++ ) {
         results[index++] = this.rows[i]
      }
      return results
   },

   convertSpaces: function(s) {
      return s.split(" ").join("&nbsp;");
   }

};


//Rico.GridViewPort --------------------------------------------------
Rico.GridViewPort = Class.create();

Rico.GridViewPort.prototype = {

   initialize: function(table, rowHeight, visibleRows, buffer, liveGrid) {
      this.lastDisplayedStartPos = 0;
      this.div = table.parentNode;
      this.table = table
      this.rowHeight = rowHeight;
      this.div.style.height = this.rowHeight * visibleRows;
      this.div.style.overflow = "hidden";
      this.buffer = buffer;
      this.liveGrid = liveGrid;
      this.visibleRows = visibleRows + 1;
      this.lastPixelOffset = 0;
      this.startPos = 0;
   },

   populateRow: function(htmlRow, row) {
      for (var j=0; j < row.length; j++) {
         htmlRow.cells[j].innerHTML = row[j]
      }
   },
   
   bufferChanged: function() {
      this.refreshContents( parseInt(this.lastPixelOffset / this.rowHeight));
   },
   
   clearRows: function() {
      if (!this.isBlank) {
         for (var i=0; i < this.visibleRows; i++)
            this.populateRow(this.table.rows[i], this.buffer.getBlankRow());
         this.isBlank = true;
      }
   },
   
   clearContents: function() {   
      this.clearRows();
      this.scrollTo(0);
      this.startPos = 0;
      this.lastStartPos = -1;   
   },
   
   refreshContents: function(startPos) {
      if (startPos == this.lastRowPos && !this.isPartialBlank && !this.isBlank) {
         return;
      }
      if ((startPos + this.visibleRows < this.buffer.startPos)  
          || (this.buffer.startPos + this.buffer.size < startPos) 
          || (this.buffer.size == 0)) {
         this.clearRows();
         return;
      }
      this.isBlank = false;
      var viewPrecedesBuffer = this.buffer.startPos > startPos
      var contentStartPos = viewPrecedesBuffer ? this.buffer.startPos: startPos;
   
      var contentEndPos = (this.buffer.startPos + this.buffer.size < startPos + this.visibleRows) 
                                 ? this.buffer.startPos + this.buffer.size
                                 : startPos + this.visibleRows;       
      var rowSize = contentEndPos - contentStartPos;
      var rows = this.buffer.getRows(contentStartPos, rowSize ); 
      var blankSize = this.visibleRows - rowSize;
      var blankOffset = viewPrecedesBuffer ? 0: rowSize;
      var contentOffset = viewPrecedesBuffer ? blankSize: 0;

      for (var i=0; i < rows.length; i++) {//initialize what we have
        this.populateRow(this.table.rows[i + contentOffset], rows[i]);
      }       
      for (var i=0; i < blankSize; i++) {// blank out the rest 
        this.populateRow(this.table.rows[i + blankOffset], this.buffer.getBlankRow());
      }
      this.isPartialBlank = blankSize > 0;
      this.lastRowPos = startPos;   
   },

   scrollTo: function(pixelOffset) {      
      if (this.lastPixelOffset == pixelOffset)
         return;

      this.refreshContents(parseInt(pixelOffset / this.rowHeight))
      this.div.scrollTop = pixelOffset % this.rowHeight        
      
      this.lastPixelOffset = pixelOffset;
   },
   
   visibleHeight: function() {
      return parseInt(this.div.style.height);
   }
   
};


Rico.LiveGridRequest = Class.create();
Rico.LiveGridRequest.prototype = {
   initialize: function( requestOffset, options ) {
      this.requestOffset = requestOffset;
   }
};

// Rico.LiveGrid -----------------------------------------------------

Rico.LiveGrid = Class.create();

Rico.LiveGrid.prototype = {

   initialize: function( tableId, visibleRows, totalRows, url, options ) {
      if ( options == null )
         options = {};

      this.tableId     = tableId; 
      this.table       = $(tableId);
      var columnCount  = this.table.rows[0].cells.length
      this.metaData    = new Rico.LiveGridMetaData(visibleRows, totalRows, columnCount, options);
      this.buffer      = new Rico.LiveGridBuffer(this.metaData);

      var rowCount = this.table.rows.length;
      this.viewPort =  new Rico.GridViewPort(this.table, 
                                            this.table.offsetHeight/rowCount,
                                            visibleRows,
                                            this.buffer, this);
      this.scroller    = new Rico.LiveGridScroller(this,this.viewPort);
      
      this.additionalParms       = options.requestParameters || [];
      
      options.sortHandler = this.sortHandler.bind(this);

      if ( $(tableId + '_header') )
         this.sort = new Rico.LiveGridSort(tableId + '_header', options)

      this.processingRequest = null;
      this.unprocessedRequest = null;

      this.initAjax(url);
      if ( options.prefetchBuffer || options.prefetchOffset > 0) {
         var offset = 0;
         if (options.offset ) {
            offset = options.offset;            
            this.scroller.moveScroll(offset);
            this.viewPort.scrollTo(this.scroller.rowToPixel(offset));            
         }
         if (options.sortCol) {
             this.sortCol = options.sortCol;
             this.sortDir = options.sortDir;
         }
         this.requestContentRefresh(offset);
      }
   },

   resetContents: function() {
      this.scroller.moveScroll(0);
      this.buffer.clear();
      this.viewPort.clearContents();
   },
   
   sortHandler: function(column) {
      this.sortCol = column.name;
      this.sortDir = column.currentSort;

      this.resetContents();
      this.requestContentRefresh(0) 
   },
   
   setRequestParams: function() {
      this.additionalParms = [];
      for ( var i=0 ; i < arguments.length ; i++ )
         this.additionalParms[i] = arguments[i];
   },

   setTotalRows: function( newTotalRows ) {
      this.resetContents();
      this.metaData.setTotalRows(newTotalRows);
      this.scroller.updateSize();
   },

   initAjax: function(url) {
      ajaxEngine.registerRequest( this.tableId + '_request', url );
      ajaxEngine.registerAjaxObject( this.tableId + '_updater', this );
   },

   invokeAjax: function() {
   },

   handleTimedOut: function() {
      //server did not respond in 4 seconds... assume that there could have been
      //an error or something, and allow requests to be processed again...
      this.processingRequest = null;
      this.processQueuedRequest();
   },

   fetchBuffer: function(offset) {
      if ( this.buffer.isInRange(offset) &&
         !this.buffer.isNearingLimit(offset)) {
         return;
      }
      if (this.processingRequest) {
          this.unprocessedRequest = new Rico.LiveGridRequest(offset);
         return;
      }
      var bufferStartPos = this.buffer.getFetchOffset(offset);
      this.processingRequest = new Rico.LiveGridRequest(offset);
      this.processingRequest.bufferOffset = bufferStartPos;   
      var fetchSize = this.buffer.getFetchSize(offset);
      var partialLoaded = false;
      var callParms = []; 
      callParms.push(this.tableId + '_request');
      callParms.push('id='        + this.tableId);
      callParms.push('page_size=' + fetchSize);
      callParms.push('offset='    + bufferStartPos);
      if ( this.sortCol) {
         callParms.push('sort_col='    + this.sortCol);
         callParms.push('sort_dir='    + this.sortDir);
      }
      
      for( var i=0 ; i < this.additionalParms.length ; i++ )
         callParms.push(this.additionalParms[i]);
      ajaxEngine.sendRequest.apply( ajaxEngine, callParms );
        
      this.timeoutHandler = setTimeout( this.handleTimedOut.bind(this), 20000 ); //todo: make as option
   },

   requestContentRefresh: function(contentOffset) {
      this.fetchBuffer(contentOffset);
   },

   ajaxUpdate: function(ajaxResponse) {
      try {
         clearTimeout( this.timeoutHandler );
         this.buffer.update(ajaxResponse,this.processingRequest.bufferOffset);
         this.viewPort.bufferChanged();
      }
      catch(err) {}
      finally {this.processingRequest = null; }
      this.processQueuedRequest();
   },

   processQueuedRequest: function() {
      if (this.unprocessedRequest != null) {
         this.requestContentRefresh(this.unprocessedRequest.requestOffset);
         this.unprocessedRequest = null
      }  
   }
 
};


//-------------------- ricoLiveGridSort.js
Rico.LiveGridSort = Class.create();

Rico.LiveGridSort.prototype = {

   initialize: function(headerTableId, options) {
      this.headerTableId = headerTableId;
      this.headerTable   = $(headerTableId);
      this.setOptions(options);
      this.applySortBehavior();

      if ( this.options.sortCol ) {
         this.setSortUI( this.options.sortCol, this.options.sortDir );
      }
   },

   setSortUI: function( columnName, sortDirection ) {
      var cols = this.options.columns;
      for ( var i = 0 ; i < cols.length ; i++ ) {
         if ( cols[i].name == columnName ) {
            this.setColumnSort(i, sortDirection);
            break;
         }
      }
   },

   setOptions: function(options) {
      this.options = {
         sortAscendImg:    'images/sort_asc.gif',
         sortDescendImg:   'images/sort_desc.gif',
         imageWidth:       9,
         imageHeight:      5,
         ajaxSortURLParms: []
      }.extend(options);

      // preload the images...
      new Image().src = this.options.sortAscendImg;
      new Image().src = this.options.sortDescendImg;

      this.sort = options.sortHandler;
      if ( !this.options.columns )
         this.options.columns = this.introspectForColumnInfo();
      else {
         // allow client to pass { columns: [ ["a", true], ["b", false] ] }
         // and convert to an array of Rico.TableColumn objs...
         this.options.columns = this.convertToTableColumns(this.options.columns);
      }
   },

   applySortBehavior: function() {
      var headerRow   = this.headerTable.rows[0];
      var headerCells = headerRow.cells;
      for ( var i = 0 ; i < headerCells.length ; i++ ) {
         this.addSortBehaviorToColumn( i, headerCells[i] );
      }
   },

   addSortBehaviorToColumn: function( n, cell ) {
      if ( this.options.columns[n].isSortable() ) {
         cell.id            = this.headerTableId + '_' + n;
         cell.style.cursor  = 'pointer';
         cell.onclick       = this.headerCellClicked.bindAsEventListener(this);
         cell.innerHTML     = cell.innerHTML + '<span id="' + this.headerTableId + '_img_' + n + '">'
                           + '&nbsp;&nbsp;&nbsp;</span>';
      }
   },

   // event handler....
   headerCellClicked: function(evt) {
      var eventTarget = evt.target ? evt.target : evt.srcElement;
      var cellId = eventTarget.id;
      var columnNumber = parseInt(cellId.substring( cellId.lastIndexOf('_') + 1 ));
      var sortedColumnIndex = this.getSortedColumnIndex();
      if ( sortedColumnIndex != -1 ) {
         if ( sortedColumnIndex != columnNumber ) {
            this.removeColumnSort(sortedColumnIndex);
            this.setColumnSort(columnNumber, Rico.TableColumn.SORT_ASC);
         }
         else
            this.toggleColumnSort(sortedColumnIndex);
      }
      else
         this.setColumnSort(columnNumber, Rico.TableColumn.SORT_ASC);

      if (this.options.sortHandler) {
         this.options.sortHandler(this.options.columns[columnNumber]);
      }
   },

   removeColumnSort: function(n) {
      this.options.columns[n].setUnsorted();
      this.setSortImage(n);
   },

   setColumnSort: function(n, direction) {
      this.options.columns[n].setSorted(direction);
      this.setSortImage(n);
   },

   toggleColumnSort: function(n) {
      this.options.columns[n].toggleSort();
      this.setSortImage(n);
   },

   setSortImage: function(n) {
      var sortDirection = this.options.columns[n].getSortDirection();

      var sortImageSpan = $( this.headerTableId + '_img_' + n );
      if ( sortDirection == Rico.TableColumn.UNSORTED )
         sortImageSpan.innerHTML = '&nbsp;&nbsp;';
      else if ( sortDirection == Rico.TableColumn.SORT_ASC )
         sortImageSpan.innerHTML = '&nbsp;&nbsp;<img width="'  + this.options.imageWidth    + '" ' +
                                                     'height="'+ this.options.imageHeight   + '" ' +
                                                     'src="'   + this.options.sortAscendImg + '"/>';
      else if ( sortDirection == Rico.TableColumn.SORT_DESC )
         sortImageSpan.innerHTML = '&nbsp;&nbsp;<img width="'  + this.options.imageWidth    + '" ' +
                                                     'height="'+ this.options.imageHeight   + '" ' +
                                                     'src="'   + this.options.sortDescendImg + '"/>';
   },

   getSortedColumnIndex: function() {
      var cols = this.options.columns;
      for ( var i = 0 ; i < cols.length ; i++ ) {
         if ( cols[i].isSorted() )
            return i;
      }

      return -1;
   },

   introspectForColumnInfo: function() {
      var columns = new Array();
      var headerRow   = this.headerTable.rows[0];
      var headerCells = headerRow.cells;
      for ( var i = 0 ; i < headerCells.length ; i++ )
         columns.push( new Rico.TableColumn( this.deriveColumnNameFromCell(headerCells[i],i), true ) );
      return columns;
   },

   convertToTableColumns: function(cols) {
      var columns = new Array();
      for ( var i = 0 ; i < cols.length ; i++ )
         columns.push( new Rico.TableColumn( cols[i][0], cols[i][1] ) );
   },

   deriveColumnNameFromCell: function(cell,columnNumber) {
      var cellContent = cell.innerText != undefined ? cell.innerText : cell.textContent;
      return cellContent ? cellContent.toLowerCase().split(' ').join('_') : "col_" + columnNumber;
   }
};

Rico.TableColumn = Class.create();

Rico.TableColumn.UNSORTED  = 0;
Rico.TableColumn.SORT_ASC  = "ASC";
Rico.TableColumn.SORT_DESC = "DESC";

Rico.TableColumn.prototype = {
   initialize: function(name, sortable) {
      this.name        = name;
      this.sortable    = sortable;
      this.currentSort = Rico.TableColumn.UNSORTED;
   },

   isSortable: function() {
      return this.sortable;
   },

   isSorted: function() {
      return this.currentSort != Rico.TableColumn.UNSORTED;
   },

   getSortDirection: function() {
      return this.currentSort;
   },

   toggleSort: function() {
      if ( this.currentSort == Rico.TableColumn.UNSORTED || this.currentSort == Rico.TableColumn.SORT_DESC )
         this.currentSort = Rico.TableColumn.SORT_ASC;
      else if ( this.currentSort == Rico.TableColumn.SORT_ASC )
         this.currentSort = Rico.TableColumn.SORT_DESC;
   },

   setUnsorted: function(direction) {
      this.setSorted(Rico.TableColumn.UNSORTED);
   },

   setSorted: function(direction) {
      // direction must by one of Rico.TableColumn.UNSORTED, .SORT_ASC, or .SET_DESC...
      this.currentSort = direction;
   }

};


//-------------------- ricoUtil.js

var RicoUtil = {

   getElementsComputedStyle: function ( htmlElement, cssProperty, mozillaEquivalentCSS) {
      if ( arguments.length == 2 )
         mozillaEquivalentCSS = cssProperty;

      var el = $(htmlElement);
      if ( el.currentStyle )
         return el.currentStyle[cssProperty];
      else
         return document.defaultView.getComputedStyle(el, null).getPropertyValue(mozillaEquivalentCSS);
   },

   createXmlDocument : function() {
      if (document.implementation && document.implementation.createDocument) {
         var doc = document.implementation.createDocument("", "", null);

         if (doc.readyState == null) {
            doc.readyState = 1;
            doc.addEventListener("load", function () {
               doc.readyState = 4;
               if (typeof doc.onreadystatechange == "function")
                  doc.onreadystatechange();
            }, false);
         }

         return doc;
      }

      if (window.ActiveXObject)
          return Try.these(
            function() { return new ActiveXObject('MSXML2.DomDocument')   },
            function() { return new ActiveXObject('Microsoft.DomDocument')},
            function() { return new ActiveXObject('MSXML.DomDocument')    },
            function() { return new ActiveXObject('MSXML3.DomDocument')   }
          ) || false;

      return null;
   },

   getContentAsString: function( parentNode ) {
      return parentNode.xml != undefined ? 
         this._getContentAsStringIE(parentNode) :
         this._getContentAsStringMozilla(parentNode);
   },

   _getContentAsStringIE: function(parentNode) {
      var contentStr = "";
      for ( var i = 0 ; i < parentNode.childNodes.length ; i++ )
         contentStr += parentNode.childNodes[i].xml;
      return contentStr;
   },

   _getContentAsStringMozilla: function(parentNode) {
      var xmlSerializer = new XMLSerializer();
      var contentStr = "";
      for ( var i = 0 ; i < parentNode.childNodes.length ; i++ )
         contentStr += xmlSerializer.serializeToString(parentNode.childNodes[i]);
      return contentStr;
   },

   toViewportPosition: function(element) {
      return this._toAbsolute(element,true);
   },

   toDocumentPosition: function(element) {
      return this._toAbsolute(element,false);
   },

   /**
    *  Compute the elements position in terms of the window viewport
    *  so that it can be compared to the position of the mouse (dnd)
    *  This is additions of all the offsetTop,offsetLeft values up the
    *  offsetParent hierarchy, ...taking into account any scrollTop,
    *  scrollLeft values along the way...
    *
    * IE has a bug reporting a correct offsetLeft of elements within a
    * a relatively positioned parent!!!
    **/
   _toAbsolute: function(element,accountForDocScroll) {

      if ( navigator.userAgent.toLowerCase().indexOf("msie") == -1 )
         return this._toAbsoluteMozilla(element,accountForDocScroll);

      var x = 0;
      var y = 0;
      var parent = element;
      while ( parent ) {

         var borderXOffset = 0;
         var borderYOffset = 0;
         if ( parent != element ) {
            var borderXOffset = parseInt(this.getElementsComputedStyle(parent, "borderLeftWidth" ));
            var borderYOffset = parseInt(this.getElementsComputedStyle(parent, "borderTopWidth" ));
            borderXOffset = isNaN(borderXOffset) ? 0 : borderXOffset;
            borderYOffset = isNaN(borderYOffset) ? 0 : borderYOffset;
         }

         x += parent.offsetLeft - parent.scrollLeft + borderXOffset;
         y += parent.offsetTop - parent.scrollTop + borderYOffset;
         parent = parent.offsetParent;
      }

      if ( accountForDocScroll ) {
         x -= this.docScrollLeft();
         y -= this.docScrollTop();
      }

      return { x:x, y:y };
   },

   /**
    *  Mozilla did not report all of the parents up the hierarchy via the
    *  offsetParent property that IE did.  So for the calculation of the
    *  offsets we use the offsetParent property, but for the calculation of
    *  the scrollTop/scrollLeft adjustments we navigate up via the parentNode
    *  property instead so as to get the scroll offsets...
    *
    **/
   _toAbsoluteMozilla: function(element,accountForDocScroll) {
      var x = 0;
      var y = 0;
      var parent = element;
      while ( parent ) {
         x += parent.offsetLeft;
         y += parent.offsetTop;
         parent = parent.offsetParent;
      }

      parent = element;
      while ( parent &&
              parent != document.body &&
              parent != document.documentElement ) {
         if ( parent.scrollLeft  )
            x -= parent.scrollLeft;
         if ( parent.scrollTop )
            y -= parent.scrollTop;
         parent = parent.parentNode;
      }

      if ( accountForDocScroll ) {
         x -= this.docScrollLeft();
         y -= this.docScrollTop();
      }

      return { x:x, y:y };
   },

   docScrollLeft: function() {
      if ( window.pageXOffset )
         return window.pageXOffset;
      else if ( document.documentElement && document.documentElement.scrollLeft )
         return document.documentElement.scrollLeft;
      else if ( document.body )
         return document.body.scrollLeft;
      else
         return 0;
   },

   docScrollTop: function() {
      if ( window.pageYOffset )
         return window.pageYOffset;
      else if ( document.documentElement && document.documentElement.scrollTop )
         return document.documentElement.scrollTop;
      else if ( document.body )
         return document.body.scrollTop;
      else
         return 0;
   }

};

var process = process || {env: {NODE_ENV: "development"}};
// script.aculo.us scriptaculous.js v1.8.3, Thu Oct 08 11:23:33 +0200 2009

// Copyright (c) 2005-2009 Thomas Fuchs (http://script.aculo.us, http://mir.aculo.us)
//
// Permission is hereby granted, free of charge, to any person obtaining
// a copy of this software and associated documentation files (the
// "Software"), to deal in the Software without restriction, including
// without limitation the rights to use, copy, modify, merge, publish,
// distribute, sublicense, and/or sell copies of the Software, and to
// permit persons to whom the Software is furnished to do so, subject to
// the following conditions:
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
// LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
// OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
// WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//
// For details, see the script.aculo.us web site: http://script.aculo.us/

var Scriptaculous = {
  Version: '1.8.3',
  require: function(libraryName) {
    try{
      // inserting via DOM fails in Safari 2.0, so brute force approach
      document.write('<script type="text/javascript" src="'+libraryName+'"><\/script>');
    } catch(e) {
      // for xhtml+xml served content, fall back to DOM methods
      var script = document.createElement('script');
      script.type = 'text/javascript';
      script.src = libraryName;
      document.getElementsByTagName('head')[0].appendChild(script);
    }
  },
  REQUIRED_PROTOTYPE: '1.6.0.3',
  load: function() {
    function convertVersionString(versionString) {
      var v = versionString.replace(/_.*|\./g, '');
      v = parseInt(v + '0'.times(4-v.length));
      return versionString.indexOf('_') > -1 ? v-1 : v;
    }

    if((typeof Prototype=='undefined') ||
       (typeof Element == 'undefined') ||
       (typeof Element.Methods=='undefined') ||
       (convertVersionString(Prototype.Version) <
        convertVersionString(Scriptaculous.REQUIRED_PROTOTYPE)))
       throw("script.aculo.us requires the Prototype JavaScript framework >= " +
        Scriptaculous.REQUIRED_PROTOTYPE);

    var js = /scriptaculous\.js(\?.*)?$/;
    $$('head script[src]').findAll(function(s) {
      return s.src.match(js);
    }).each(function(s) {
      var path = s.src.replace(js, ''),
      includes = s.src.match(/\?.*load=([a-z,]*)/);
      (includes ? includes[1] : 'builder,effects,dragdrop,controls,slider,sound').split(',').each(
       function(include) { Scriptaculous.require(path+include+'.js') });
    });
  }
};

Scriptaculous.load();
var process = process || {env: {NODE_ENV: "development"}};
// script.aculo.us slider.js v1.8.3, Thu Oct 08 11:23:33 +0200 2009

// Copyright (c) 2005-2009 Marty Haught, Thomas Fuchs
//
// script.aculo.us is freely distributable under the terms of an MIT-style license.
// For details, see the script.aculo.us web site: http://script.aculo.us/

if (!Control) var Control = { };

// options:
//  axis: 'vertical', or 'horizontal' (default)
//
// callbacks:
//  onChange(value)
//  onSlide(value)
Control.Slider = Class.create({
  initialize: function(handle, track, options) {
    var slider = this;

    if (Object.isArray(handle)) {
      this.handles = handle.collect( function(e) { return $(e) });
    } else {
      this.handles = [$(handle)];
    }

    this.track   = $(track);
    this.options = options || { };

    this.axis      = this.options.axis || 'horizontal';
    this.increment = this.options.increment || 1;
    this.step      = parseInt(this.options.step || '1');
    this.range     = this.options.range || $R(0,1);

    this.value     = 0; // assure backwards compat
    this.values    = this.handles.map( function() { return 0 });
    this.spans     = this.options.spans ? this.options.spans.map(function(s){ return $(s) }) : false;
    this.options.startSpan = $(this.options.startSpan || null);
    this.options.endSpan   = $(this.options.endSpan || null);

    this.restricted = this.options.restricted || false;

    this.maximum   = this.options.maximum || this.range.end;
    this.minimum   = this.options.minimum || this.range.start;

    // Will be used to align the handle onto the track, if necessary
    this.alignX = parseInt(this.options.alignX || '0');
    this.alignY = parseInt(this.options.alignY || '0');

    this.trackLength = this.maximumOffset() - this.minimumOffset();

    this.handleLength = this.isVertical() ?
      (this.handles[0].offsetHeight != 0 ?
        this.handles[0].offsetHeight : this.handles[0].style.height.replace(/px$/,"")) :
      (this.handles[0].offsetWidth != 0 ? this.handles[0].offsetWidth :
        this.handles[0].style.width.replace(/px$/,""));

    this.active   = false;
    this.dragging = false;
    this.disabled = false;

    if (this.options.disabled) this.setDisabled();

    // Allowed values array
    this.allowedValues = this.options.values ? this.options.values.sortBy(Prototype.K) : false;
    if (this.allowedValues) {
      this.minimum = this.allowedValues.min();
      this.maximum = this.allowedValues.max();
    }

    this.eventMouseDown = this.startDrag.bindAsEventListener(this);
    this.eventMouseUp   = this.endDrag.bindAsEventListener(this);
    this.eventMouseMove = this.update.bindAsEventListener(this);

    // Initialize handles in reverse (make sure first handle is active)
    this.handles.each( function(h,i) {
      i = slider.handles.length-1-i;
      slider.setValue(parseFloat(
        (Object.isArray(slider.options.sliderValue) ?
          slider.options.sliderValue[i] : slider.options.sliderValue) ||
         slider.range.start), i);
      h.makePositioned().observe("mousedown", slider.eventMouseDown);
    });

    this.track.observe("mousedown", this.eventMouseDown);
    document.observe("mouseup", this.eventMouseUp);
    document.observe("mousemove", this.eventMouseMove);

    this.initialized = true;
  },
  dispose: function() {
    var slider = this;
    Event.stopObserving(this.track, "mousedown", this.eventMouseDown);
    Event.stopObserving(document, "mouseup", this.eventMouseUp);
    Event.stopObserving(document, "mousemove", this.eventMouseMove);
    this.handles.each( function(h) {
      Event.stopObserving(h, "mousedown", slider.eventMouseDown);
    });
  },
  setDisabled: function(){
    this.disabled = true;
  },
  setEnabled: function(){
    this.disabled = false;
  },
  getNearestValue: function(value){
    if (this.allowedValues){
      if (value >= this.allowedValues.max()) return(this.allowedValues.max());
      if (value <= this.allowedValues.min()) return(this.allowedValues.min());

      var offset = Math.abs(this.allowedValues[0] - value);
      var newValue = this.allowedValues[0];
      this.allowedValues.each( function(v) {
        var currentOffset = Math.abs(v - value);
        if (currentOffset <= offset){
          newValue = v;
          offset = currentOffset;
        }
      });
      return newValue;
    }
    if (value > this.range.end) return this.range.end;
    if (value < this.range.start) return this.range.start;
    return value;
  },
  setValue: function(sliderValue, handleIdx){
    if (!this.active) {
      this.activeHandleIdx = handleIdx || 0;
      this.activeHandle    = this.handles[this.activeHandleIdx];
      this.updateStyles();
    }
    handleIdx = handleIdx || this.activeHandleIdx || 0;
    if (this.initialized && this.restricted) {
      if ((handleIdx>0) && (sliderValue<this.values[handleIdx-1]))
        sliderValue = this.values[handleIdx-1];
      if ((handleIdx < (this.handles.length-1)) && (sliderValue>this.values[handleIdx+1]))
        sliderValue = this.values[handleIdx+1];
    }
    sliderValue = this.getNearestValue(sliderValue);
    this.values[handleIdx] = sliderValue;
    this.value = this.values[0]; // assure backwards compat

    this.handles[handleIdx].style[this.isVertical() ? 'top' : 'left'] =
      this.translateToPx(sliderValue);

    this.drawSpans();
    if (!this.dragging || !this.event) this.updateFinished();
  },
  setValueBy: function(delta, handleIdx) {
    this.setValue(this.values[handleIdx || this.activeHandleIdx || 0] + delta,
      handleIdx || this.activeHandleIdx || 0);
  },
  translateToPx: function(value) {
    return Math.round(
      ((this.trackLength-this.handleLength)/(this.range.end-this.range.start)) *
      (value - this.range.start)) + "px";
  },
  translateToValue: function(offset) {
    return ((offset/(this.trackLength-this.handleLength) *
      (this.range.end-this.range.start)) + this.range.start);
  },
  getRange: function(range) {
    var v = this.values.sortBy(Prototype.K);
    range = range || 0;
    return $R(v[range],v[range+1]);
  },
  minimumOffset: function(){
    return(this.isVertical() ? this.alignY : this.alignX);
  },
  maximumOffset: function(){
    return(this.isVertical() ?
      (this.track.offsetHeight != 0 ? this.track.offsetHeight :
        this.track.style.height.replace(/px$/,"")) - this.alignY :
      (this.track.offsetWidth != 0 ? this.track.offsetWidth :
        this.track.style.width.replace(/px$/,"")) - this.alignX);
  },
  isVertical:  function(){
    return (this.axis == 'vertical');
  },
  drawSpans: function() {
    var slider = this;
    if (this.spans)
      $R(0, this.spans.length-1).each(function(r) { slider.setSpan(slider.spans[r], slider.getRange(r)) });
    if (this.options.startSpan)
      this.setSpan(this.options.startSpan,
        $R(0, this.values.length>1 ? this.getRange(0).min() : this.value ));
    if (this.options.endSpan)
      this.setSpan(this.options.endSpan,
        $R(this.values.length>1 ? this.getRange(this.spans.length-1).max() : this.value, this.maximum));
  },
  setSpan: function(span, range) {
    if (this.isVertical()) {
      span.style.top = this.translateToPx(range.start);
      span.style.height = this.translateToPx(range.end - range.start + this.range.start);
    } else {
      span.style.left = this.translateToPx(range.start);
      span.style.width = this.translateToPx(range.end - range.start + this.range.start);
    }
  },
  updateStyles: function() {
    this.handles.each( function(h){ Element.removeClassName(h, 'selected') });
    Element.addClassName(this.activeHandle, 'selected');
  },
  startDrag: function(event) {
    if (Event.isLeftClick(event)) {
      if (!this.disabled){
        this.active = true;

        var handle = Event.element(event);
        var pointer  = [Event.pointerX(event), Event.pointerY(event)];
        var track = handle;
        if (track==this.track) {
          var offsets  = this.track.cumulativeOffset();
          this.event = event;
          this.setValue(this.translateToValue(
           (this.isVertical() ? pointer[1]-offsets[1] : pointer[0]-offsets[0])-(this.handleLength/2)
          ));
          var offsets  = this.activeHandle.cumulativeOffset();
          this.offsetX = (pointer[0] - offsets[0]);
          this.offsetY = (pointer[1] - offsets[1]);
        } else {
          // find the handle (prevents issues with Safari)
          while((this.handles.indexOf(handle) == -1) && handle.parentNode)
            handle = handle.parentNode;

          if (this.handles.indexOf(handle)!=-1) {
            this.activeHandle    = handle;
            this.activeHandleIdx = this.handles.indexOf(this.activeHandle);
            this.updateStyles();

            var offsets  = this.activeHandle.cumulativeOffset();
            this.offsetX = (pointer[0] - offsets[0]);
            this.offsetY = (pointer[1] - offsets[1]);
          }
        }
      }
      Event.stop(event);
    }
  },
  update: function(event) {
   if (this.active) {
      if (!this.dragging) this.dragging = true;
      this.draw(event);
      if (Prototype.Browser.WebKit) window.scrollBy(0,0);
      Event.stop(event);
   }
  },
  draw: function(event) {
    var pointer = [Event.pointerX(event), Event.pointerY(event)];
    var offsets = this.track.cumulativeOffset();
    pointer[0] -= this.offsetX + offsets[0];
    pointer[1] -= this.offsetY + offsets[1];
    this.event = event;
    this.setValue(this.translateToValue( this.isVertical() ? pointer[1] : pointer[0] ));
    if (this.initialized && this.options.onSlide)
      this.options.onSlide(this.values.length>1 ? this.values : this.value, this);
  },
  endDrag: function(event) {
    if (this.active && this.dragging) {
      this.finishDrag(event, true);
      Event.stop(event);
    }
    this.active = false;
    this.dragging = false;
  },
  finishDrag: function(event, success) {
    this.active = false;
    this.dragging = false;
    this.updateFinished();
  },
  updateFinished: function() {
    if (this.initialized && this.options.onChange)
      this.options.onChange(this.values.length>1 ? this.values : this.value, this);
    this.event = null;
  }
});
var process = process || {env: {NODE_ENV: "development"}};
// script.aculo.us sound.js v1.8.3, Thu Oct 08 11:23:33 +0200 2009

// Copyright (c) 2005-2009 Thomas Fuchs (http://script.aculo.us, http://mir.aculo.us)
//
// Based on code created by Jules Gravinese (http://www.webveteran.com/)
//
// script.aculo.us is freely distributable under the terms of an MIT-style license.
// For details, see the script.aculo.us web site: http://script.aculo.us/

Sound = {
  tracks: {},
  _enabled: true,
  template:
    new Template('<embed style="height:0" id="sound_#{track}_#{id}" src="#{url}" loop="false" autostart="true" hidden="true"/>'),
  enable: function(){
    Sound._enabled = true;
  },
  disable: function(){
    Sound._enabled = false;
  },
  play: function(url){
    if(!Sound._enabled) return;
    var options = Object.extend({
      track: 'global', url: url, replace: false
    }, arguments[1] || {});

    if(options.replace && this.tracks[options.track]) {
      $R(0, this.tracks[options.track].id).each(function(id){
        var sound = $('sound_'+options.track+'_'+id);
        sound.Stop && sound.Stop();
        sound.remove();
      });
      this.tracks[options.track] = null;
    }

    if(!this.tracks[options.track])
      this.tracks[options.track] = { id: 0 };
    else
      this.tracks[options.track].id++;

    options.id = this.tracks[options.track].id;
    $$('body')[0].insert(
      Prototype.Browser.IE ? new Element('bgsound',{
        id: 'sound_'+options.track+'_'+options.id,
        src: options.url, loop: 1, autostart: true
      }) : Sound.template.evaluate(options));
  }
};

if(Prototype.Browser.Gecko && navigator.userAgent.indexOf("Win") > 0){
  if(navigator.plugins && $A(navigator.plugins).detect(function(p){ return p.name.indexOf('QuickTime') != -1 }))
    Sound.template = new Template('<object id="sound_#{track}_#{id}" width="0" height="0" type="audio/mpeg" data="#{url}"/>');
  else if(navigator.plugins && $A(navigator.plugins).detect(function(p){ return p.name.indexOf('Windows Media') != -1 }))
    Sound.template = new Template('<object id="sound_#{track}_#{id}" type="application/x-mplayer2" data="#{url}"></object>');
  else if(navigator.plugins && $A(navigator.plugins).detect(function(p){ return p.name.indexOf('RealPlayer') != -1 }))
    Sound.template = new Template('<embed type="audio/x-pn-realaudio-plugin" style="height:0" id="sound_#{track}_#{id}" src="#{url}" loop="false" autostart="true" hidden="true"/>');
  else
    Sound.play = function(){};
} 
var process = process || {env: {NODE_ENV: "development"}};
// script.aculo.us unittest.js v1.8.3, Thu Oct 08 11:23:33 +0200 2009

// Copyright (c) 2005-2009 Thomas Fuchs (http://script.aculo.us, http://mir.aculo.us)
//           (c) 2005-2009 Jon Tirsen (http://www.tirsen.com)
//           (c) 2005-2009 Michael Schuerig (http://www.schuerig.de/michael/)
//
// script.aculo.us is freely distributable under the terms of an MIT-style license.
// For details, see the script.aculo.us web site: http://script.aculo.us/

// experimental, Firefox-only
Event.simulateMouse = function(element, eventName) {
  var options = Object.extend({
    pointerX: 0,
    pointerY: 0,
    buttons:  0,
    ctrlKey:  false,
    altKey:   false,
    shiftKey: false,
    metaKey:  false
  }, arguments[2] || {});
  var oEvent = document.createEvent("MouseEvents");
  oEvent.initMouseEvent(eventName, true, true, document.defaultView, 
    options.buttons, options.pointerX, options.pointerY, options.pointerX, options.pointerY, 
    options.ctrlKey, options.altKey, options.shiftKey, options.metaKey, 0, $(element));
  
  if(this.mark) Element.remove(this.mark);
  this.mark = document.createElement('div');
  this.mark.appendChild(document.createTextNode(" "));
  document.body.appendChild(this.mark);
  this.mark.style.position = 'absolute';
  this.mark.style.top = options.pointerY + "px";
  this.mark.style.left = options.pointerX + "px";
  this.mark.style.width = "5px";
  this.mark.style.height = "5px;";
  this.mark.style.borderTop = "1px solid red;";
  this.mark.style.borderLeft = "1px solid red;";
  
  if(this.step)
    alert('['+new Date().getTime().toString()+'] '+eventName+'/'+Test.Unit.inspect(options));
  
  $(element).dispatchEvent(oEvent);
};

// Note: Due to a fix in Firefox 1.0.5/6 that probably fixed "too much", this doesn't work in 1.0.6 or DP2.
// You need to downgrade to 1.0.4 for now to get this working
// See https://bugzilla.mozilla.org/show_bug.cgi?id=289940 for the fix that fixed too much
Event.simulateKey = function(element, eventName) {
  var options = Object.extend({
    ctrlKey: false,
    altKey: false,
    shiftKey: false,
    metaKey: false,
    keyCode: 0,
    charCode: 0
  }, arguments[2] || {});

  var oEvent = document.createEvent("KeyEvents");
  oEvent.initKeyEvent(eventName, true, true, window, 
    options.ctrlKey, options.altKey, options.shiftKey, options.metaKey,
    options.keyCode, options.charCode );
  $(element).dispatchEvent(oEvent);
};

Event.simulateKeys = function(element, command) {
  for(var i=0; i<command.length; i++) {
    Event.simulateKey(element,'keypress',{charCode:command.charCodeAt(i)});
  }
};

var Test = {};
Test.Unit = {};

// security exception workaround
Test.Unit.inspect = Object.inspect;

Test.Unit.Logger = Class.create();
Test.Unit.Logger.prototype = {
  initialize: function(log) {
    this.log = $(log);
    if (this.log) {
      this._createLogTable();
    }
  },
  start: function(testName) {
    if (!this.log) return;
    this.testName = testName;
    this.lastLogLine = document.createElement('tr');
    this.statusCell = document.createElement('td');
    this.nameCell = document.createElement('td');
    this.nameCell.className = "nameCell";
    this.nameCell.appendChild(document.createTextNode(testName));
    this.messageCell = document.createElement('td');
    this.lastLogLine.appendChild(this.statusCell);
    this.lastLogLine.appendChild(this.nameCell);
    this.lastLogLine.appendChild(this.messageCell);
    this.loglines.appendChild(this.lastLogLine);
  },
  finish: function(status, summary) {
    if (!this.log) return;
    this.lastLogLine.className = status;
    this.statusCell.innerHTML = status;
    this.messageCell.innerHTML = this._toHTML(summary);
    this.addLinksToResults();
  },
  message: function(message) {
    if (!this.log) return;
    this.messageCell.innerHTML = this._toHTML(message);
  },
  summary: function(summary) {
    if (!this.log) return;
    this.logsummary.innerHTML = this._toHTML(summary);
  },
  _createLogTable: function() {
    this.log.innerHTML =
    '<div id="logsummary"></div>' +
    '<table id="logtable">' +
    '<thead><tr><th>Status</th><th>Test</th><th>Message</th></tr></thead>' +
    '<tbody id="loglines"></tbody>' +
    '</table>';
    this.logsummary = $('logsummary');
    this.loglines = $('loglines');
  },
  _toHTML: function(txt) {
    return txt.escapeHTML().replace(/\n/g,"<br/>");
  },
  addLinksToResults: function(){ 
    $$("tr.failed .nameCell").each( function(td){ // todo: limit to children of this.log
      td.title = "Run only this test";
      Event.observe(td, 'click', function(){ window.location.search = "?tests=" + td.innerHTML;});
    });
    $$("tr.passed .nameCell").each( function(td){ // todo: limit to children of this.log
      td.title = "Run all tests";
      Event.observe(td, 'click', function(){ window.location.search = "";});
    });
  }
};

Test.Unit.Runner = Class.create();
Test.Unit.Runner.prototype = {
  initialize: function(testcases) {
    this.options = Object.extend({
      testLog: 'testlog'
    }, arguments[1] || {});
    this.options.resultsURL = this.parseResultsURLQueryParameter();
    this.options.tests      = this.parseTestsQueryParameter();
    if (this.options.testLog) {
      this.options.testLog = $(this.options.testLog) || null;
    }
    if(this.options.tests) {
      this.tests = [];
      for(var i = 0; i < this.options.tests.length; i++) {
        if(/^test/.test(this.options.tests[i])) {
          this.tests.push(new Test.Unit.Testcase(this.options.tests[i], testcases[this.options.tests[i]], testcases["setup"], testcases["teardown"]));
        }
      }
    } else {
      if (this.options.test) {
        this.tests = [new Test.Unit.Testcase(this.options.test, testcases[this.options.test], testcases["setup"], testcases["teardown"])];
      } else {
        this.tests = [];
        for(var testcase in testcases) {
          if(/^test/.test(testcase)) {
            this.tests.push(
               new Test.Unit.Testcase(
                 this.options.context ? ' -> ' + this.options.titles[testcase] : testcase, 
                 testcases[testcase], testcases["setup"], testcases["teardown"]
               ));
          }
        }
      }
    }
    this.currentTest = 0;
    this.logger = new Test.Unit.Logger(this.options.testLog);
    setTimeout(this.runTests.bind(this), 1000);
  },
  parseResultsURLQueryParameter: function() {
    return window.location.search.parseQuery()["resultsURL"];
  },
  parseTestsQueryParameter: function(){
    if (window.location.search.parseQuery()["tests"]){
        return window.location.search.parseQuery()["tests"].split(',');
    };
  },
  // Returns:
  //  "ERROR" if there was an error,
  //  "FAILURE" if there was a failure, or
  //  "SUCCESS" if there was neither
  getResult: function() {
    var hasFailure = false;
    for(var i=0;i<this.tests.length;i++) {
      if (this.tests[i].errors > 0) {
        return "ERROR";
      }
      if (this.tests[i].failures > 0) {
        hasFailure = true;
      }
    }
    if (hasFailure) {
      return "FAILURE";
    } else {
      return "SUCCESS";
    }
  },
  postResults: function() {
    if (this.options.resultsURL) {
      new Ajax.Request(this.options.resultsURL, 
        { method: 'get', parameters: 'result=' + this.getResult(), asynchronous: false });
    }
  },
  runTests: function() {
    var test = this.tests[this.currentTest];
    if (!test) {
      // finished!
      this.postResults();
      this.logger.summary(this.summary());
      return;
    }
    if(!test.isWaiting) {
      this.logger.start(test.name);
    }
    test.run();
    if(test.isWaiting) {
      this.logger.message("Waiting for " + test.timeToWait + "ms");
      setTimeout(this.runTests.bind(this), test.timeToWait || 1000);
    } else {
      this.logger.finish(test.status(), test.summary());
      this.currentTest++;
      // tail recursive, hopefully the browser will skip the stackframe
      this.runTests();
    }
  },
  summary: function() {
    var assertions = 0;
    var failures = 0;
    var errors = 0;
    var messages = [];
    for(var i=0;i<this.tests.length;i++) {
      assertions +=   this.tests[i].assertions;
      failures   +=   this.tests[i].failures;
      errors     +=   this.tests[i].errors;
    }
    return (
      (this.options.context ? this.options.context + ': ': '') + 
      this.tests.length + " tests, " + 
      assertions + " assertions, " + 
      failures   + " failures, " +
      errors     + " errors");
  }
};

Test.Unit.Assertions = Class.create();
Test.Unit.Assertions.prototype = {
  initialize: function() {
    this.assertions = 0;
    this.failures   = 0;
    this.errors     = 0;
    this.messages   = [];
  },
  summary: function() {
    return (
      this.assertions + " assertions, " + 
      this.failures   + " failures, " +
      this.errors     + " errors" + "\n" +
      this.messages.join("\n"));
  },
  pass: function() {
    this.assertions++;
  },
  fail: function(message) {
    this.failures++;
    this.messages.push("Failure: " + message);
  },
  info: function(message) {
    this.messages.push("Info: " + message);
  },
  error: function(error) {
    this.errors++;
    this.messages.push(error.name + ": "+ error.message + "(" + Test.Unit.inspect(error) +")");
  },
  status: function() {
    if (this.failures > 0) return 'failed';
    if (this.errors > 0) return 'error';
    return 'passed';
  },
  assert: function(expression) {
    var message = arguments[1] || 'assert: got "' + Test.Unit.inspect(expression) + '"';
    try { expression ? this.pass() : 
      this.fail(message); }
    catch(e) { this.error(e); }
  },
  assertEqual: function(expected, actual) {
    var message = arguments[2] || "assertEqual";
    try { (expected == actual) ? this.pass() :
      this.fail(message + ': expected "' + Test.Unit.inspect(expected) + 
        '", actual "' + Test.Unit.inspect(actual) + '"'); }
    catch(e) { this.error(e); }
  },
  assertInspect: function(expected, actual) {
    var message = arguments[2] || "assertInspect";
    try { (expected == actual.inspect()) ? this.pass() :
      this.fail(message + ': expected "' + Test.Unit.inspect(expected) + 
        '", actual "' + Test.Unit.inspect(actual) + '"'); }
    catch(e) { this.error(e); }
  },
  assertEnumEqual: function(expected, actual) {
    var message = arguments[2] || "assertEnumEqual";
    try { $A(expected).length == $A(actual).length && 
      expected.zip(actual).all(function(pair) { return pair[0] == pair[1] }) ?
        this.pass() : this.fail(message + ': expected ' + Test.Unit.inspect(expected) + 
          ', actual ' + Test.Unit.inspect(actual)); }
    catch(e) { this.error(e); }
  },
  assertNotEqual: function(expected, actual) {
    var message = arguments[2] || "assertNotEqual";
    try { (expected != actual) ? this.pass() : 
      this.fail(message + ': got "' + Test.Unit.inspect(actual) + '"'); }
    catch(e) { this.error(e); }
  },
  assertIdentical: function(expected, actual) { 
    var message = arguments[2] || "assertIdentical"; 
    try { (expected === actual) ? this.pass() : 
      this.fail(message + ': expected "' + Test.Unit.inspect(expected) +  
        '", actual "' + Test.Unit.inspect(actual) + '"'); } 
    catch(e) { this.error(e); } 
  },
  assertNotIdentical: function(expected, actual) { 
    var message = arguments[2] || "assertNotIdentical"; 
    try { !(expected === actual) ? this.pass() : 
      this.fail(message + ': expected "' + Test.Unit.inspect(expected) +  
        '", actual "' + Test.Unit.inspect(actual) + '"'); } 
    catch(e) { this.error(e); } 
  },
  assertNull: function(obj) {
    var message = arguments[1] || 'assertNull';
    try { (obj==null) ? this.pass() : 
      this.fail(message + ': got "' + Test.Unit.inspect(obj) + '"'); }
    catch(e) { this.error(e); }
  },
  assertMatch: function(expected, actual) {
    var message = arguments[2] || 'assertMatch';
    var regex = new RegExp(expected);
    try { (regex.exec(actual)) ? this.pass() :
      this.fail(message + ' : regex: "' +  Test.Unit.inspect(expected) + ' did not match: ' + Test.Unit.inspect(actual) + '"'); }
    catch(e) { this.error(e); }
  },
  assertHidden: function(element) {
    var message = arguments[1] || 'assertHidden';
    this.assertEqual("none", element.style.display, message);
  },
  assertNotNull: function(object) {
    var message = arguments[1] || 'assertNotNull';
    this.assert(object != null, message);
  },
  assertType: function(expected, actual) {
    var message = arguments[2] || 'assertType';
    try { 
      (actual.constructor == expected) ? this.pass() : 
      this.fail(message + ': expected "' + Test.Unit.inspect(expected) +  
        '", actual "' + (actual.constructor) + '"'); }
    catch(e) { this.error(e); }
  },
  assertNotOfType: function(expected, actual) {
    var message = arguments[2] || 'assertNotOfType';
    try { 
      (actual.constructor != expected) ? this.pass() : 
      this.fail(message + ': expected "' + Test.Unit.inspect(expected) +  
        '", actual "' + (actual.constructor) + '"'); }
    catch(e) { this.error(e); }
  },
  assertInstanceOf: function(expected, actual) {
    var message = arguments[2] || 'assertInstanceOf';
    try { 
      (actual instanceof expected) ? this.pass() : 
      this.fail(message + ": object was not an instance of the expected type"); }
    catch(e) { this.error(e); } 
  },
  assertNotInstanceOf: function(expected, actual) {
    var message = arguments[2] || 'assertNotInstanceOf';
    try { 
      !(actual instanceof expected) ? this.pass() : 
      this.fail(message + ": object was an instance of the not expected type"); }
    catch(e) { this.error(e); } 
  },
  assertRespondsTo: function(method, obj) {
    var message = arguments[2] || 'assertRespondsTo';
    try {
      (obj[method] && typeof obj[method] == 'function') ? this.pass() : 
      this.fail(message + ": object doesn't respond to [" + method + "]"); }
    catch(e) { this.error(e); }
  },
  assertReturnsTrue: function(method, obj) {
    var message = arguments[2] || 'assertReturnsTrue';
    try {
      var m = obj[method];
      if(!m) m = obj['is'+method.charAt(0).toUpperCase()+method.slice(1)];
      m() ? this.pass() : 
      this.fail(message + ": method returned false"); }
    catch(e) { this.error(e); }
  },
  assertReturnsFalse: function(method, obj) {
    var message = arguments[2] || 'assertReturnsFalse';
    try {
      var m = obj[method];
      if(!m) m = obj['is'+method.charAt(0).toUpperCase()+method.slice(1)];
      !m() ? this.pass() : 
      this.fail(message + ": method returned true"); }
    catch(e) { this.error(e); }
  },
  assertRaise: function(exceptionName, method) {
    var message = arguments[2] || 'assertRaise';
    try { 
      method();
      this.fail(message + ": exception expected but none was raised"); }
    catch(e) {
      ((exceptionName == null) || (e.name==exceptionName)) ? this.pass() : this.error(e); 
    }
  },
  assertElementsMatch: function() {
    var expressions = $A(arguments), elements = $A(expressions.shift());
    if (elements.length != expressions.length) {
      this.fail('assertElementsMatch: size mismatch: ' + elements.length + ' elements, ' + expressions.length + ' expressions');
      return false;
    }
    elements.zip(expressions).all(function(pair, index) {
      var element = $(pair.first()), expression = pair.last();
      if (element.match(expression)) return true;
      this.fail('assertElementsMatch: (in index ' + index + ') expected ' + expression.inspect() + ' but got ' + element.inspect());
    }.bind(this)) && this.pass();
  },
  assertElementMatches: function(element, expression) {
    this.assertElementsMatch([element], expression);
  },
  benchmark: function(operation, iterations) {
    var startAt = new Date();
    (iterations || 1).times(operation);
    var timeTaken = ((new Date())-startAt);
    this.info((arguments[2] || 'Operation') + ' finished ' + 
       iterations + ' iterations in ' + (timeTaken/1000)+'s' );
    return timeTaken;
  },
  _isVisible: function(element) {
    element = $(element);
    if(!element.parentNode) return true;
    this.assertNotNull(element);
    if(element.style && Element.getStyle(element, 'display') == 'none')
      return false;
    
    return this._isVisible(element.parentNode);
  },
  assertNotVisible: function(element) {
    this.assert(!this._isVisible(element), Test.Unit.inspect(element) + " was not hidden and didn't have a hidden parent either. " + ("" || arguments[1]));
  },
  assertVisible: function(element) {
    this.assert(this._isVisible(element), Test.Unit.inspect(element) + " was not visible. " + ("" || arguments[1]));
  },
  benchmark: function(operation, iterations) {
    var startAt = new Date();
    (iterations || 1).times(operation);
    var timeTaken = ((new Date())-startAt);
    this.info((arguments[2] || 'Operation') + ' finished ' + 
       iterations + ' iterations in ' + (timeTaken/1000)+'s' );
    return timeTaken;
  }
};

Test.Unit.Testcase = Class.create();
Object.extend(Object.extend(Test.Unit.Testcase.prototype, Test.Unit.Assertions.prototype), {
  initialize: function(name, test, setup, teardown) {
    Test.Unit.Assertions.prototype.initialize.bind(this)();
    this.name           = name;
    
    if(typeof test == 'string') {
      test = test.gsub(/(\.should[^\(]+\()/,'#{0}this,');
      test = test.gsub(/(\.should[^\(]+)\(this,\)/,'#{1}(this)');
      this.test = function() {
        eval('with(this){'+test+'}');
      }
    } else {
      this.test = test || function() {};
    }
    
    this.setup          = setup || function() {};
    this.teardown       = teardown || function() {};
    this.isWaiting      = false;
    this.timeToWait     = 1000;
  },
  wait: function(time, nextPart) {
    this.isWaiting = true;
    this.test = nextPart;
    this.timeToWait = time;
  },
  run: function() {
    try {
      try {
        if (!this.isWaiting) this.setup.bind(this)();
        this.isWaiting = false;
        this.test.bind(this)();
      } finally {
        if(!this.isWaiting) {
          this.teardown.bind(this)();
        }
      }
    }
    catch(e) { this.error(e); }
  }
});

// *EXPERIMENTAL* BDD-style testing to please non-technical folk
// This draws many ideas from RSpec http://rspec.rubyforge.org/

Test.setupBDDExtensionMethods = function(){
  var METHODMAP = {
    shouldEqual:     'assertEqual',
    shouldNotEqual:  'assertNotEqual',
    shouldEqualEnum: 'assertEnumEqual',
    shouldBeA:       'assertType',
    shouldNotBeA:    'assertNotOfType',
    shouldBeAn:      'assertType',
    shouldNotBeAn:   'assertNotOfType',
    shouldBeNull:    'assertNull',
    shouldNotBeNull: 'assertNotNull',
    
    shouldBe:        'assertReturnsTrue',
    shouldNotBe:     'assertReturnsFalse',
    shouldRespondTo: 'assertRespondsTo'
  };
  var makeAssertion = function(assertion, args, object) { 
   	this[assertion].apply(this,(args || []).concat([object]));
  };
  
  Test.BDDMethods = {};   
  $H(METHODMAP).each(function(pair) { 
    Test.BDDMethods[pair.key] = function() { 
       var args = $A(arguments); 
       var scope = args.shift(); 
       makeAssertion.apply(scope, [pair.value, args, this]); }; 
  });
  
  [Array.prototype, String.prototype, Number.prototype, Boolean.prototype].each(
    function(p){ Object.extend(p, Test.BDDMethods) }
  );
};

Test.context = function(name, spec, log){
  Test.setupBDDExtensionMethods();
  
  var compiledSpec = {};
  var titles = {};
  for(specName in spec) {
    switch(specName){
      case "setup":
      case "teardown":
        compiledSpec[specName] = spec[specName];
        break;
      default:
        var testName = 'test'+specName.gsub(/\s+/,'-').camelize();
        var body = spec[specName].toString().split('\n').slice(1);
        if(/^\{/.test(body[0])) body = body.slice(1);
        body.pop();
        body = body.map(function(statement){ 
          return statement.strip()
        });
        compiledSpec[testName] = body.join('\n');
        titles[testName] = specName;
    }
  }
  new Test.Unit.Runner(compiledSpec, { titles: titles, testLog: log || 'testlog', context: name });
};
