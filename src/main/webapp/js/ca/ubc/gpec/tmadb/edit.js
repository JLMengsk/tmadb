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



