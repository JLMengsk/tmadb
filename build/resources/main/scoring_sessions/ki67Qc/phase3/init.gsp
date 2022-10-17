<!--
  Ki67 QC phase 3 init
-->
<%@ page import="ca.ubc.gpec.tmadb.DisplayConstant"%>
<%@ page import="ca.ubc.gpec.tmadb.ImageViewerMethods"%>
<%@ page import="ca.ubc.gpec.tmadb.util.MiscUtil"%>
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
        <div dojoType='dijit.form.Form' action="${createLink(base:grailsApplication.config.grails.serverSecureURL, controller:"scoring_sessions", action:"save_ki67_qc_phase3_init")}" method='POST' id="${ViewConstants.HTML_FORM_NAME_KI67_QC_PHASE3_INIT}">
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
                    <div style="background-color: yellow; width: 100px"><b>Action items ... </b></div>
                    <ol>
                        <li>Please verify that the slide label and image matches the physical glass slide.
                        <li>Please enter the 40x field diameter (mm) of your microscope.
                    </ol>
                    <table>
                        <tr>
                            <td>
                                <div style="width: 400px; height: 400px">
                                    <g:display_all_whole_section_preview_images id="${whole_section_scoring.getWhole_section_image().getId()}" width="${400}" mode="${Whole_section_images.WHOLE_SECTION_IMAGE_VIEW_MODE_TEST}"/>
                                </div>
                            </td>
                            <td>
                                <g:display_ki67_qc_phase3_init_input whole_section_scoring="${whole_section_scoring}" /><br><br>
                                <g:if test="${whole_section_scoring.showIsAllowedToUpdateScore()}">
                                    Please enter any comment (optional):<br>
                                    <textarea id='${ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_WHOLE_SECTION_SCORING_COMMENT}' 
                                              name='${ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_WHOLE_SECTION_SCORING_COMMENT}' 
                                              dojoType='dijit.form.Textarea' 
                                              style='width:30em;'>${whole_section_scoring.showScoringComment()}</textarea>
                                  </g:if><!-- init.gsp will NOT be shown for users not allowed to update score -->
                            </td>
                        </tr>
                    </table>
                    </td>
                    </tr>
                    </table>
                    <!-- display submit button -->
                    <div class="buttons_large_font">
                        <span class="button">
                            <div style="display: inline-block; float: right">
                                <button 
                                dojoType='dijit.form.Button' 
                                type="submit" 
                                value="save" 
                                onclick="return (checkFormFields() ? (confirm('ok to save field diameter?') ? cleanUp() : false) : false);">save and continue</button>
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

    function checkFormFields() {
    return true; // nothing to check
    }

    function reset() {
    // special treatement for 40x field diameter field because there is a default value 
    dijit.byId("html_param_name_ki67_qc_phase3_40x_field_diameter_mm").set("value",${Whole_section_scorings.DEFAULT_FIELD_DIAMETER_40X_MM});
    }
    <g:if test="${whole_section_scoring?.showIsAllowedToUpdateScore() && !showHE && !showMM}">
        //dojo.ready(function() {alert("Please load the following glass slide on your microscope: ${whole_section_scoring.getWhole_section_image().getWhole_section_slice().toStringWithParaffin_blockName()}");});
        dojo.ready(function() {
        showMessageDialog("Please load glass slide", "Please load the following glass slide on your microscope: ${whole_section_scoring.getWhole_section_image().getWhole_section_slice().toStringWithParaffin_blockName()}");
        });
    </g:if>
</r:script>

</body>
</html>
