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


