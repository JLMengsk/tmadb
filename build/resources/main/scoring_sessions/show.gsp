
<%@ page import="ca.ubc.gpec.tmadb.Tma_core_images"%>
<%@ page import="ca.ubc.gpec.tmadb.DisplayConstant"%>
<%@ page import="ca.ubc.gpec.tmadb.ImageViewerMethods"%>
<%@ page import="ca.ubc.gpec.tmadb.Scoring_sessions"%>
<%@ page import="ca.ubc.gpec.tmadb.util.Scoring_sessionsConstants"%>
<%@ page import="ca.ubc.gpec.tmadb.util.ViewConstants"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName"
        value="${message(code: 'scoring_sessions.label', default: 'Scoring_sessions')}" />
        <title>Scoring session: ${fieldValue(bean: scoring_sessionInstance, field: "name")}</title>
    <r:require modules="image_helpers, scoring_sessions"/>
</head>
<body>
    <div class="body">
        <g:if test="${!showReference && !showPageBodyOnly}">
            <h1>Scoring session: ${scoring_sessionInstance.name}</h1>
            <g:showFlashMessage />
            <p><g:display_scoring_session_progress id="${scoring_sessionInstance.id}"/></p>
            <!-- show timer if user has not submitted the results yet ...-->
            <g:if test="${tma_scoringInstance?.showIsAllowedToUpdateScore() || whole_section_scoring?.showIsAllowedToUpdateScore()}"><r:script>timeMsg();</r:script></g:if>
            </g:if>
            <g:elseif test="${showReference}"><h1>${referenceTitle}</h1></g:elseif>
        <g:elseif test="${showHE}"><h1>H&E image</h1></g:elseif>
        <g:elseif test="${showMM}"><h1>Myoepithelial marker image</h1></g:elseif>
        <g:display_scoring_image_option scoring="${scoring}"  showReference="${showReference}"  showPageBodyOnly="${showPageBodyOnly}" showHE="${showHE}" showMM="${showMM}" />
        <br>
        <div id="scoring_session_form" dojoType='dijit.form.Form' action="${createLink(base:grailsApplication.config.grails.serverSecureURL, controller:"scoring_sessions", action:"saveScore")}" method='POST'>
            <g:display_scoring_objects_ids scoring_session="${scoring_sessionInstance}" tma_scoring="${tma_scoringInstance}" whole_section_scoring="${whole_section_scoring}"/>
            <div class="dialog" style="width: ${ImageViewerMethods.IMAGE_VIEWER_WIDTH+50}px">
                <g:display_scoring_objects_description_and_navigation scoring_session="${scoring_sessionInstance}" tma_scoring="${tma_scoringInstance}" whole_section_scoring="${whole_section_scoring}" showPageBodyOnly="${showPageBodyOnly}" showHE="${showHE}" showMM="${showMM}"/>
                <g:if test="${showHE}">
                    <g:display_scoring_object_he scoring_session="${scoring_sessionInstance}" tma_scoring="${tma_scoringInstance}" whole_section_scoring="${whole_section_scoring}"/>
                </g:if> 
                <g:elseif test="${showMM}">
                    <g:display_scoring_object_mm scoring_session="${scoring_sessionInstance}" tma_scoring="${tma_scoringInstance}" whole_section_scoring="${whole_section_scoring}"/>
                </g:elseif>
                <g:else>
                    <table>
                        <tbody>
                            <tr class="prop">
                                <g:if test="${tma_scoringInstance.showIsScoringTypeNucleiCount() || tma_scoringInstance.showIsScoringTypeNucleiCountNoCoord()}">
                                    <g:display_scoring_commands tma_scoring="${tma_scoringInstance}" />
                                </tr>
                                <g:if test="${!showHE && !showMM}">                
                                    <tr class="prop">
                                        <g:display_nuclei_count_status 
                                        numNeg_id="${Scoring_sessionsConstants.NUCLEI_COUNT_STATUS_NUM_NEG_ID}" 
                                        numPos_id="${Scoring_sessionsConstants.NUCLEI_COUNT_STATUS_NUM_POS_ID}" 
                                        numTotal_id="${Scoring_sessionsConstants.NUCLEI_COUNT_STATUS_NUM_TOTAL_ID}"/>
                                    </tr>  
                                    <g:if test="${tma_scoringInstance.showIsScoringTypeNucleiCount()}"><tr class="prop"><g:display_tma_scoring_image_zoom_pan_command /></tr></g:if>
                                </g:if>    
                                <tr class="prop"> 
                                    <td colspan=2 style="width:${tma_scoringInstance.showIsScoringTypeNucleiCountNoCoord()?"500":ImageViewerMethods.IMAGE_VIEWER_WIDTH}px">
                                        <g:if test="${tma_scoringInstance.showIsScoringTypeNucleiCount()}"><g:display_nuclei_selector_applet tma_scoring="${tma_scoringInstance}"/></g:if>
                                        <g:elseif test="${tma_scoringInstance.showIsScoringTypeNucleiCountNoCoord()}"><g:display_nuclei_counter_with_sector_map tma_scoring="${tma_scoringInstance}"/></g:elseif>
                                    </g:if>
                                    <g:else>
                                    <td colspan=2>${tma_scoringInstance.tma_core_image.showImageHtml(createLink(uri: '/').toString(),ImageViewerMethods.IMAGE_VIEWER_WIDTH,ImageViewerMethods.IMAGE_VIEWER_HEIGHT)}
                                    </g:else>
                                </g:else>
                            </td>
                        </tr>
                        <tr class="prop">
                            <g:if test="${!tma_scoringInstance?.showIsScoringTypeNucleiCount() && !tma_scoringInstance?.showIsScoringTypeNucleiCountNoCoord() && !showHE && !showMM && !showPageBodyOnly}">
                                <td valign="top" class="name">${tma_scoringInstance.showIsAllowedToUpdateScore()?"Please enter score:":"Score entered:"}</td>
                                <td valign="top" class="value">
                                    <g:if test="${tma_scoringInstance.showIsAllowedToUpdateScore()}"><g:textField id="inputScore" name="inputScore" value="${tma_scoringInstance.showScore()}"/></g:if>
                                    <g:else>${tma_scoringInstance.showScore()}</g:else>
                                    </td>
                            </g:if>
                            <g:else>
                                <g:if test="${!showHE && !showMM && tma_scoringInstance?.showIsScoringTypeNucleiCount()}">
                                  <!-- no need to repeat display of commands/nuclei count for Nuclei count no-coord -->
                                    <g:display_tma_scoring_image_zoom_pan_command /></tr>
                                <tr class="prop">
                                    <g:display_nuclei_count_status 
                                    numNeg_id="${Scoring_sessionsConstants.NUCLEI_COUNT_STATUS_NUM_NEG_BOTTOM_ID}" 
                                    numPos_id="${Scoring_sessionsConstants.NUCLEI_COUNT_STATUS_NUM_POS_BOTTOM_ID}" 
                                    numTotal_id="${Scoring_sessionsConstants.NUCLEI_COUNT_STATUS_NUM_TOTAL_BOTTOM_ID}"/>
                                </g:if>
                            </g:else>
                        </tr>
                        <g:if test="${tma_scoringInstance?.showIsAllowedToUpdateScore() && !showHE && !showMM && !showPageBodyOnly}">
                            <tr>
                                <td></td><td><g:display_nuclei_counter_undo_save_button tma_scoring="${tma_scoringInstance}"/></td>
                            </tr>
                            <tr>
                                <td valign="top" class="name">Please enter any comment (optional):</td>
                                <td valign="top" class="value"><g:textArea id="inputComment" name="inputComment" dojoType="dijit.form.Textarea" value="${tma_scoringInstance.showScoringComment()}" style="width:300px;"/></td>
                            </tr>
                        </g:if>
                    </tbody>
                </table>
            </div>

            <g:if test="${tma_scoringInstance?.showIsAllowedToUpdateScore() && !showPageBodyOnly}">
                <g:if test="${showHE || showMM}">
                    <i>please go back to non-H&E view to save score and to continue scoring</i>
                </g:if>
                <g:else>
                    <div class="buttons_large_font">
                        <span class="button">
                            <g:if test="${tma_scoringInstance?.showIsScoringTypeNucleiCount() || tma_scoringInstance?.showIsScoringTypeNucleiCountNoCoord()}">
                                <button 
                                dojoType='dijit.form.Button' 
                                type="submit" 
                                value="save" 
                                onclick="cleanUp(); _tempSubmitFormAfterUploadNucleiSelection=true; nucleiCounter.uploadNucleiSelection(); return false">save score and go to next image</button>
                            </g:if>
                            <g:else>
                                <button 
                                dojoType='dijit.form.Button' 
                                type="submit" 
                                value="save" 
                                onclick="cleanUp(); return confirm('Scored entered = '+document.getElementById('inputScore').value+'.\\nContinue to save score?'">save score and go to next image</button>	
                            </g:else>
                        </span>
                    </div>
                </g:else>
            </g:if>


        </div> <!-- dijit.form.Form -->

    </div>

<r:script>
    <g:if test="${!showHE && !showMM}">
        var _tempFirstTimeLoading = true;
        var _tempResetInProgress = false;
        var _tempSubmitFormAfterUploadNucleiSelection = false;
        function updateNucleiSelectionCount(numPos, numNeg, numTotal) {
        updateNucleiSelectionCountHelper(numPos, numNeg, numTotal, 
        ${tma_scoringInstance?.showIsScoringTypeNucleiCount()?"true":"false"},//isScoringTypeNucleiCount, 
        <g:tma_scoring_nuclei_selection_notification_nuclei_counts id="${tma_scoringInstance?.getId()}"/>, //nuclei_selection_notification_nuclei_count_array
        <g:tma_scoring_nuclei_selection_notification_messages id="${tma_scoringInstance?.getId()}"/> //nuclei_selection_notification_message_array
        );}

        function uploadNucleiSelection(nucleiSelectionParamString) {
        var inputComment = document.getElementById('inputComment').value;

        showWaitDialogMsg(1,"uploading ...","uploading nuclei selections ... please wait")

        require(["dojo/_base/xhr"], function(xhr){
        xhr.post({
            url: "${createLink(controller:"tma_scorings", action:"uploadNucleiSelection")}", // read the url: from the action="" of the <form>
            timeout: 120000, // give up after 120 seconds
            content: { id:"${tma_scoringInstance?.getId()}", nucleiSelectionParamString:nucleiSelectionParamString, inputComment:inputComment },
            load: function(x){
                closeWaitDialog();
                checkUpdateNucleiSelection(x,window.location);
                if (_tempSaveStatusOK) {
                    if (_tempSubmitFormAfterUploadNucleiSelection) {
                        showWaitDialogMsg(1, "Nuclei selection saved", "Nuclei selection saved successfully.  Loading next page ... please wait");
                        // submit form!!!
                        dijit.byId("scoring_session_form").submit();
                    } else {
                        showMessageDialog("Nuclei selection saved", "Nuclei selection saved successfully.");
                    }
                }
                nucleiCounter.nucleiSelection.resetUpdateCount(); // NEED TO RESET COUNTERS!!!        
                nucleiCounter.repaint();
            }
            }); // xhr.post
        }); // function(xhr)

        } // function uploadNucleiSelection(nucleiSelectionParamString)
        
        var heWindowHandle = null;
        var refWindowHandle = null;

        function cleanUp() {
            cleanUpBeforeChangeMainWindow([heWindowHandle,refWindowHandle]);
            return true;
        }

            function openHeWindow() {heWindowHandle=window.open("<g:display_tma_scoring_he_link id="${tma_scoringInstance?.getId()}"/>","${DisplayConstant.H_AND_E_WINDOW}",JAVASCRIPT_OPEN_POPUP_WINDOW_SPECS);}
        </g:if>
</r:script>

</body>
</html>
