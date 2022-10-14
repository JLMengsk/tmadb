<!--
  Ki67 QC phase 3 init
-->
<%@ page import="ca.ubc.gpec.tmadb.DisplayConstant"%>
<%@ page import="ca.ubc.gpec.tmadb.ImageViewerMethods"%>
<%@ page import="ca.ubc.gpec.tmadb.util.ViewConstants"%>
<%@ page import="ca.ubc.gpec.tmadb.Whole_section_images"%>
<%@ page import="ca.ubc.gpec.tmadb.Whole_section_preview_images"%>
<%@ page import="ca.ubc.gpec.tmadb.Whole_section_scorings"%>
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
    <div dojoType='dijit.form.Form' action="${createLink(base:grailsApplication.config.grails.serverSecureURL, controller:"scoring_sessions", action:"save_ki67_qc_phase3_estimate_percent")}" method='POST' id="${ViewConstants.HTML_FORM_NAME_KI67_QC_PHASE3_INIT}">
      <g:display_scoring_objects_ids scoring_session="${scoring_sessionInstance}" tma_scoring="${null}" whole_section_scoring="${whole_section_scoring}"/>
      <div class="dialog">
        <g:display_scoring_objects_description_and_navigation scoring_session="${scoring_sessionInstance}" tma_scoring="${null}" whole_section_scoring="${whole_section_scoring}" showPageBodyOnly="${showPageBodyOnly}"/>
        <g:if test="${showHE}">
          <g:display_scoring_object_he scoring_session="${scoring_sessionInstance}" tma_scoring="${null}" whole_section_scoring="${whole_section_scoring}"/>
        </g:if>
        <g:elseif test="${showMM}">
          <g:display_scoring_object_mm scoring_session="${scoring_sessionInstance}" tma_scoring="${null}" whole_section_scoring="${whole_section_scoring}"/>
        </g:elseif>
        <g:else>
          <div style="background-color: yellow; width: 100px"><b>Action item ... </b></div>
          <ul>
            <li>Please estimate (from the glass slide) the percentages of the invasive tumour that exhibit the following Ki67 scores: ${ViewConstants.NEGLIGIBLE_TEXT}, low, medium and high. 
          </ul>
          <table>
            <tr>
              <td>
                <div style="width: 400px; height: 400px">
                  <g:display_all_whole_section_preview_images id="${whole_section_scoring.getWhole_section_image().getId()}" width="${400}" mode="${Whole_section_images.WHOLE_SECTION_IMAGE_VIEW_MODE_TEST}"/>
                </div>
              </td>
              <td>
            <g:display_ki67_qc_phase3_estimate_percent_input whole_section_scoring="${whole_section_scoring}" /><br><br>
            </td>
            </tr>
          </table>
          </td>
          </tr>
          </table>
          <!-- display submit button -->
          <div class="buttons_large_font">
            <span class="button">
              <button
                dojoType='dijit.form.Button' 
                type="submit" 
                title="click me to go back to previous step i.e. count nuclei on hot-spot"
                onclick="go_back(); return false;"
                >back</button>
              <div style="display: inline-block; float: right">
                <button 
                  dojoType='dijit.form.Button' 
                  type="submit" 
                  value="save" 
                  onclick="return (checkFormFields() ? (confirm('ok to save percentages of Ki67 scores?') ? cleanUp() : false) : false);">save and continue</button>
                <button
                  dojoType='dijit.form.Button' 
                  type="submit" 
                  onclick="reset(); return false;"
                  >reset</button>
              </div>
            </span>
          </div>
        </g:else>
      </div> <!-- <div class="dialog"> -->
    </div> <!-- <div dojoType='dijit.form.Form'> -->
  </div>
<r:script>  
  <g:display_whole_section_scoring_he_mm_javascripts id="${whole_section_scoring?.getId()}"/>

  // function to go back to previous step i.e. count hotspot
  function go_back() {
  ki67_qc_phase3_go_back_one_step(
  "${createLink(controller:"scoring_sessions", action:"ajax_ki67_qc_phase3_go_back",params:[id:scoring_sessionInstance.getId(), whole_section_scoring_id:whole_section_scoring.getId()])}", 
  "${createLink(controller:"scoring_sessions", action:"score",                      params:[id:scoring_sessionInstance.getId(), whole_section_scoring_id:whole_section_scoring.getId()])}");
  // clean up
  cleanUp(); // cleanUp() is defined by display_whole_section_scoring_he_mm_javascripts
  return false; // prevent page refresh
  }

  function checkFormFields() {
  return dijit.byId("${ViewConstants.HTML_FORM_NAME_KI67_QC_PHASE3_INIT}").validate() && checkSumOfField(
  ["${ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_ESTIMATE_LEVEL_HIGH}",
  "${ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_ESTIMATE_LEVEL_MEDIUM}",
  "${ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_ESTIMATE_LEVEL_LOW}",
  "${ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_ESTIMATE_LEVEL_NEGLIGIBLE}"],
  100,
  "Sum of percentage of Ki67 ${ViewConstants.NEGLIGIBLE_TEXT}/low/medium/high is not 100%.  Please make sure fields add up to 100%.");
  }

  function reset() {
  resetFormFields(
  ["${ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_ESTIMATE_LEVEL_HIGH}",
  "${ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_ESTIMATE_LEVEL_MEDIUM}",
  "${ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_ESTIMATE_LEVEL_LOW}",
  "${ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_ESTIMATE_LEVEL_NEGLIGIBLE}"]
  );
  }

</r:script>

</body>
</html>
