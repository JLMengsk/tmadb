<!--
  Ki67 QC phase 3 select scoring regions
-->
<%@ page import="ca.ubc.gpec.tmadb.DisplayConstant"%>
<%@ page import="ca.ubc.gpec.tmadb.util.ViewConstants"%>
<%@ page import="ca.ubc.gpec.fieldselector.FieldSelectorConstants"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Scoring session: ${scoring_sessionInstance.getName()}</title>
  <r:require modules="image_helpers, ki67_qc_phase3"/>
</head>
<body>
  <div class="body">
    <g:if test="${!showPageBodyOnly}">
      <h1>Scoring session: ${scoring_sessionInstance.getName()}</h1>
      <g:showFlashMessage />
      <p><g:display_scoring_session_progress id="${scoring_sessionInstance.getId()}"/></p>
      <!-- show timer if user has not submitted the results yet ...-->
      <g:if test="${whole_section_scoring?.showIsAllowedToUpdateScore()}"><r:script>timeMsg();</r:script></g:if>
    </g:if>
    <g:display_scoring_image_option scoring="${scoring}"  showReference="${showReference}"  showPageBodyOnly="${showPageBodyOnly}" showHE="${showHE}" showMM="${showMM}" />
    <br>
    <div dojoType='dijit.form.Form' action="${createLink(base:grailsApplication.config.grails.serverSecureURL, controller:"scoring_sessions", action:"save_ki67_qc_phase3_select_region")}" method='POST' id="${ViewConstants.HTML_FORM_NAME_KI67_QC_PHASE3_SELECT_REGION}">
      <g:display_scoring_objects_ids scoring_session="${scoring_sessionInstance}" tma_scoring="${null}" whole_section_scoring="${whole_section_scoring}"/>
      <div class="dialog">
        <input id="${ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_SELECT_REGION_PARAMSTRING}" name="${ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_SELECT_REGION_PARAMSTRING}" type="hidden"/>
        <g:display_scoring_objects_description_and_navigation scoring_session="${scoring_sessionInstance}" tma_scoring="${null}" whole_section_scoring="${whole_section_scoring}" showPageBodyOnly="${showPageBodyOnly}"/>
        <g:if test="${showHE}">
          <g:display_scoring_object_he scoring_session="${scoring_sessionInstance}" tma_scoring="${null}" whole_section_scoring="${whole_section_scoring}"/>
        </g:if> 
        <g:elseif test="${showMM}">
          <g:display_scoring_object_mm scoring_session="${scoring_sessionInstance}" tma_scoring="${null}" whole_section_scoring="${whole_section_scoring}"/>
        </g:elseif>
        <g:else>
          <div style="background-color: yellow; width: 100px"><b>Action items ... </b></div>
          <ol>
            <g:if test="${selectHotspot}">
              <li>Please examine the glass slide and select <strong>1 hotspot</strong>.
              <li>Please indicate the approximate location of your hotspot selection (based on your examination of the glass slide) by doing the following:<br>
                <i>Please DO NOT select hotspot based on your examination on the low resolution digital image.</i>
                <ol>
                  <li>pan (drag) & zoom (scroll up/down) to the approximate location on the low resolution image on the left.
                  <li>shift-double-click on the image to indicate your hotspot selection.
                </ol>
              </li>
            </g:if>
            <g:else>
              <li>Please examine the glass slide and select <strong>${scoringFieldsAllocator.getNumNonHotspot()} fields</strong> according to the Ki67 levels indicated by the buttons on the right.
              <li>Please indicate the approximate location of your field selections (based on your examination of the glass slide) by doing the following:<br>
                <i>Please DO NOT select fields based on your examination on the low resolution digital image.</i>
                <ol>
                  <li>click on a button on the right. <i>(initially, the "Hot-spot" button is clicked already)</i>
                  <li>pan (drag) & zoom (scroll up/down) to the approximate location on the low resolution image on the left.
                  <li>shift-double-click on the image to indicate your field selection.
                </ol>
              </li>
            </g:else>
          </ol>
          <table>
            <tr>
              <td><g:display_field_selector_applet whole_section_scoring="${whole_section_scoring}" scoringFieldsAllocator="${scoringFieldsAllocator}" width="550" height="550" state="${FieldSelectorConstants.FIELD_SELECTOR_STATE_SELECTING}"/></td>
            <td><g:display_field_selector_buttons scoringFieldsAllocator="${scoringFieldsAllocator}" selectHotspot="${selectHotspot}"/></td>
            </tr>
          </table>
          <!-- display submit button -->
          <div class="buttons_large_font">
            <span class="button">
              <button
                dojoType='dijit.form.Button' 
                type="submit" 
                title="click me to go back to previous step i.e. estimate percentage of Ki67 levels"
                onclick="go_back(); return false;"
                >back</button>
              <div style="display: inline; float: right">
                <button 
                  dojoType='dijit.form.Button' 
                  type="submit" 
                  value="save" 
                  onclick="return (checkBeforeSavingFieldSelections('${selectHotspot?"ok to save hotspot selection?":"ok to save field selections?"}') ? cleanUp() : false);">save and continue</button>
                <button
                  dojoType='dijit.form.Button' 
                  type="submit" 
                  onclick="resetSelections(); return false;"
                  >reset</button>
            </span>
          </div>
        </g:else>
      </div> <!-- <div class="dialog"> -->
    </div> <!-- <div dojoType='dijit.form.Form'> -->
  </div>
<r:script>  
  <g:display_whole_section_scoring_he_mm_javascripts id="${whole_section_scoring?.getId()}"/>

  // function to go back to previous step i.e. select fields
  function go_back() {
  ki67_qc_phase3_go_back_one_step(
  "${createLink(controller:"scoring_sessions", action:"ajax_ki67_qc_phase3_go_back",params:[id:scoring_sessionInstance.getId(), whole_section_scoring_id:whole_section_scoring.getId()])}", 
  "${createLink(controller:"scoring_sessions", action:"score",                      params:[id:scoring_sessionInstance.getId(), whole_section_scoring_id:whole_section_scoring.getId()])}");
  // clean up
  cleanUp(); // cleanUp() is defined by display_whole_section_scoring_he_mm_javascripts
  return false; // prevent page refresh
  }

  // function called by the field selector to initialize the selection state
  // AT FIRST MOUSE CLICK!!!
  function initializeFieldSelectorSelectionState() {
  <g:if test="${whole_section_scoring?.showIsAllowedToUpdateScore()}">
    // function called by fieldSelector to initialize the first field to select
    <g:if test="${selectHotspot}">
      ${ViewConstants.JAVASCRIPT_OBJECT_NAME_FIELD_SELECTOR}.setKi67SelectionStateToHighest();
    </g:if>
    <g:else>
      <g:if test="${scoringFieldsAllocator.getNumHigh()>0}">          ${ViewConstants.JAVASCRIPT_OBJECT_NAME_FIELD_SELECTOR}.setKi67SelectionStateToHigh();</g:if>
      <g:elseif test="${scoringFieldsAllocator.getNumMedium()>0}">    ${ViewConstants.JAVASCRIPT_OBJECT_NAME_FIELD_SELECTOR}.setKi67SelectionStateToMedium();</g:elseif>
      <g:elseif test="${scoringFieldsAllocator.getNumLow()>0}">       ${ViewConstants.JAVASCRIPT_OBJECT_NAME_FIELD_SELECTOR}.setKi67SelectionStateToLow();</g:elseif>
      <g:elseif test="${scoringFieldsAllocator.getNumNegligible()>0}">${ViewConstants.JAVASCRIPT_OBJECT_NAME_FIELD_SELECTOR}.setKi67SelectionStateToNegligible();</g:elseif>
    </g:else>
  </g:if>
  }
</r:script>
</body>
</html>
