<!DOCTYPE html>
<%@ page import="ca.ubc.gpec.tmadb.util.MiscUtil" %>
<%@ page import="ca.ubc.gpec.tmadb.util.Scoring_sessionsConstants" %>
<%@ page import="ca.ubc.gpec.tmadb.util.ViewConstants" %>
<%@ page import="ca.ubc.gpec.tmadb.DisplayConstant" %>
<html>
  <head>
    <title><g:layoutTitle default="Grails" />
    </title>
    <!-- need mymain.css since Grails will rewrite its own main.css -->
    <link rel="stylesheet" href="${resource(dir:'css',file:'mymain.css')}" type="text/css"/>
    <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
    <link rel="stylesheet" type="text/css" href="https://ajax.googleapis.com/ajax/libs/dojo/1.14.1/dijit/themes/claro/claro.css">
    <script>dojoConfig = {parseOnLoad: true}</script>
    <script src="https://ajax.googleapis.com/ajax/libs/dojo/1.14.1/dojo/dojo.js"></script>
    <script>
  dojo.require("dijit.Menu");
  dojo.require("dijit.MenuItem");
  dojo.require("dijit.PopupMenuItem");
  dojo.require("dojo.parser");
  dojo.require("dojo.domReady!");
  dojo.require("dojo/ready");
  dojo.require("dojo/dom");
  dojo.require("dojo/_base/fx");
  dojo.require("dojo/_base/xhr");
  dojo.require("dojo/_base/lang");
  dojo.require("dojo/data/ItemFileWriteStore");
  dojo.require("dojo/query!css2");
  dojo.require("dijit.Dialog");
  dojo.require("dijit.form.Button");
  dojo.require("dojox.grid.DataGrid");
  dojo.require("dojo.data.ItemFileReadStore");
  dojo.require("dojo.data.ObjectStore");
  dojo.require("dojo.store.JsonRest");
  dojo.require("dojo.store.Observable");
  dojo.require("dijit.Tree");
  dojo.require("dijit.tree.dndSource");
  dojo.require("dijit.form.Form");
  dojo.require("dijit.form.FilteringSelect");
  dojo.require("dijit.form.Select");
  dojo.require("dijit.form.MultiSelect");
  dojo.require("dijit.form.TextBox");
  dojo.require("dijit.form.Textarea");
  dojo.require("dijit.form.ValidationTextBox");
  dojo.require("dijit.form.CheckBox");
  dojo.require("dijit.form.RadioButton");
  dojo.require("dijit.form.NumberTextBox");
  dojo.require("dijit.form.DropDownButton");
  dojo.require("dijit.form.DateTextBox");
  dojo.require("dojo.fx");
  dojo.require("dijit.layout.TabContainer");
  dojo.require("dijit.MenuBar");
  dojo.require("dijit.PopupMenuBarItem");
    </script>
    <asset:stylesheet src="application.css"/>
    <asset:javascript src="application.js"/>
    <asset:script type="text/javascript" disposition="head">
      // some global variables for all ... need to declare them here so that 
      //
      var HEADER_HEIGHT = ${MiscUtil.showIsLoggedIn(session)?135:120}; // assume height of header is approx. 120-135px ... header is taller when logged in because need to display log in info.
      var FOOTER_HEIGHT = 40; // assume height of footer is approx. 40px
      var PAGEBODY_MARGIN_LEFT_FOR_IE7_AND_ABOVE = 20; //280;
      var PAGEBODY_MARGIN_LEFT = PAGEBODY_MARGIN_LEFT_FOR_IE7_AND_ABOVE; // ignore IE 6 for now
      var PAGEBODY_MARGIN_RIGHT = 20;
      //
      // links to media
      var MEDIA_SOUND_FILE_BEEP     = "${resource(dir:'sounds',file:'beep_09.wav')}"; // negative sound
      var MEDIA_SOUND_FILE_CLICK1   = "${resource(dir:'sounds',file:'Click01.wav')}"; // positive sound
      var MEDIA_SOUND_FILE_CLICK2   = "${resource(dir:'sounds',file:'Click02.wav')}"; // undo sound
      var MEDIA_SOUND_FILE_DINGLING = "${resource(dir:'sounds',file:'DingLing.wav')}";
      var MEDIA_IMAGE_NUCLEI_COUNTER_URL = "${resource(dir:'images',file:'BCC2PICM_rotated_fit.jpeg')}";
      //
      // misc constants ...
      var JAVASCRIPT_OPEN_POPUP_WINDOW_SPECS="menubar=no,status=no,toolbar=no,location=no";
      var IMAGE_SRC_SPINNER = "${resource(dir:'images',file:'spinner.gif')}"
      var AJAX_RESPONSE_DELIMITER   = "${ViewConstants.AJAX_RESPONSE_DELIMITER}";
      var AJAX_RESPONSE_DELIMITER_2 = "${ViewConstants.AJAX_RESPONSE_DELIMITER_2}";
      var AJAX_RESPONSE_NA              = "${ViewConstants.AJAX_RESPONSE_NA}";
      var AJAX_RESPONSE_ERROR           = "${ViewConstants.AJAX_RESPONSE_ERROR}";
      var AJAX_RESPONSE_SAVE_SUCCESSFUL = "${ViewConstants.AJAX_RESPONSE_SAVE_SUCCESSFUL}";
      var AJAX_RESPONSE_OK              = "${ViewConstants.AJAX_RESPONSE_OK}";
      var SCORING_SESSION_SCORING_TYPE                       = "${ViewConstants.SCORING_SESSION_SCORING_TYPE}";
      var SCORING_SESSION_SCORING_TYPE_TMA_SCORING           = "${ViewConstants.SCORING_SESSION_SCORING_TYPE_TMA_SCORING}";
      var SCORING_SESSION_SCORING_TYPE_WHOLE_SECTION_SCORING = "${ViewConstants.SCORING_SESSION_SCORING_TYPE_WHOLE_SECTION_SCORING}";
      //
      // constants for scoring session ...
      var SCORING_SESSION_TIMEOUT_IN_SECONDS = ${Scoring_sessionsConstants.SCORING_SESSION_TIMEOUT_IN_SECONDS};
      var NUCLEI_COUNT_KEY_POSITIVE_LOWER_CASE = "${Scoring_sessionsConstants.NUCLEI_COUNT_KEY_POSITIVE.toLowerCase() }";
      var NUCLEI_COUNT_KEY_NEGATIVE_LOWER_CASE = "${Scoring_sessionsConstants.NUCLEI_COUNT_KEY_NEGATIVE.toLowerCase() }";
      var NUCLEI_COUNT_KEY_UNDO_LOWER_CASE     = "${Scoring_sessionsConstants.NUCLEI_COUNT_KEY_UNDO.toLowerCase() }"; 
      var NUCLEI_COUNT_KEY_POSITIVE_UPPER_CASE = "${Scoring_sessionsConstants.NUCLEI_COUNT_KEY_POSITIVE.toUpperCase() }";
      var NUCLEI_COUNT_KEY_NEGATIVE_UPPER_CASE = "${Scoring_sessionsConstants.NUCLEI_COUNT_KEY_NEGATIVE.toUpperCase() }";
      var NUCLEI_COUNT_KEY_UNDO_UPPER_CASE     = "${Scoring_sessionsConstants.NUCLEI_COUNT_KEY_UNDO.toUpperCase() }";    
      var NUCLEI_COUNT_STATUS_NUM_POS_ID   = "${Scoring_sessionsConstants.NUCLEI_COUNT_STATUS_NUM_POS_ID}";
      var NUCLEI_COUNT_STATUS_NUM_NEG_ID   = "${Scoring_sessionsConstants.NUCLEI_COUNT_STATUS_NUM_NEG_ID}";
      var NUCLEI_COUNT_STATUS_NUM_TOTAL_ID = "${Scoring_sessionsConstants.NUCLEI_COUNT_STATUS_NUM_TOTAL_ID}";
      var NUCLEI_COUNT_STATUS_NUM_POS_BOTTOM_ID   = "${Scoring_sessionsConstants.NUCLEI_COUNT_STATUS_NUM_POS_BOTTOM_ID}";
      var NUCLEI_COUNT_STATUS_NUM_NEG_BOTTOM_ID   = "${Scoring_sessionsConstants.NUCLEI_COUNT_STATUS_NUM_NEG_BOTTOM_ID}";
      var NUCLEI_COUNT_STATUS_NUM_TOTAL_BOTTOM_ID = "${Scoring_sessionsConstants.NUCLEI_COUNT_STATUS_NUM_TOTAL_BOTTOM_ID}";
      var NUCLEI_COUNTER_JAR_NAME = "${DisplayConstant.NUCLEI_COUNTER_JAR_NAME}";
      var HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_HIGHEST    = "${ViewConstants.HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_HIGHEST}";
      var HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_HIGH       = "${ViewConstants.HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_HIGH}";
      var HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_MEDIUM     = "${ViewConstants.HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_MEDIUM}";
      var HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_LOW        = "${ViewConstants.HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_LOW}";
      var HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_NEGLIGIBLE = "${ViewConstants.HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_NEGLIGIBLE}";
      var HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DELETE = "${ViewConstants.HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DELETE}";
      var HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_HIGHEST    = "${ViewConstants.HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_HIGHEST}";
      var HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_HIGH       = "${ViewConstants.HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_HIGH}";
      var HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_MEDIUM     = "${ViewConstants.HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_MEDIUM}";
      var HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_LOW        = "${ViewConstants.HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_LOW}";
      var HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_NEGLIGIBLE = "${ViewConstants.HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_NEGLIGIBLE}";
      var HTML_PARAM_NAME_KI67_QC_PHASE3_SELECT_REGION_PARAMSTRING = "${ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_SELECT_REGION_PARAMSTRING}";
      var KI67_QC_PHASE3_FIELD_SELECTOR_ID = "${ViewConstants.JAVASCRIPT_OBJECT_NAME_FIELD_SELECTOR}";
      var HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_SCORING_ID              = "${ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_SCORING_ID}";
      var HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_REGION_SCORING_X        = "${ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_REGION_SCORING_X}";
      var HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_REGION_SCORING_Y        = "${ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_REGION_SCORING_Y}";
      var HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_REGION_SCORING_DIAMETER = "${ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_REGION_SCORING_DIAMETER}";
      var HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_REGION_SCORING_KI67STATE= "${ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_REGION_SCORING_KI67STATE}";
      //
      // global variable for wait and message dialog
      var wait_dialog_object=null;
      var wait_dialog_object_close_count=null;
      var message_dialog_object=null;
    </asset:script>
  <g:layoutHead />
</head>
<body class="claro">
<g:if test="${params.containsKey(ViewConstants.SHOW_BODY_ONLY_PARAM_FLAG) || actionName?.endsWith(ViewConstants.SHOW_BODY_ONLY_CONTROLLER_ACTION_SUFFIX)}">
  <g:layoutBody />
</g:if>
<g:else>
  <g:render template="/common/header" />
  <div id="${MiscUtil.needFixedPositionForPageBody(controllerName, actionName) ? "pageBody" : "pageBodyPositionNotFixed"}">
    <g:layoutBody />
  </div>
  <g:render template="/common/footer" />
</g:else>
<g:if test="${MiscUtil.needFixedPositionForPageBody(controllerName, actionName) || MiscUtil.needRepositionFooter(controllerName, actionName)}">
  <asset:script type="text/javascript"> 
    ////////////////////////////////////////////////////////////////////////
    // layout footer position - just in case some pages don't do this     //
    // by calling setInitialFooterPosition() explicitly                   //
    // - needed for pageBody being fixed position
    dojo.ready(function() {setInitialFooterPosition();});
    ///////////////////////////////////////////////////////////////////////
  </asset:script>
</g:if>
<asset:deferredScripts/>
</body>
</html>