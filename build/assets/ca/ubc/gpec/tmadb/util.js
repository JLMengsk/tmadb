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
