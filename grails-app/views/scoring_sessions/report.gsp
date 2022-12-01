<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="ca.ubc.gpec.tmadb.Scoring_sessions"%>
<%@ page import="ca.ubc.gpec.tmadb.util.ViewConstants" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Scoring session report: ${scoring_sessionsInstance.getName()}</title>

    <asset:javascript src="application.js"/>

    <asset:javascript src="ca/ubc/gpec/tmadb/scoring_sessions/scoring_sessions.js"/>

    
    <style type="text/css">
        @import "https://ajax.googleapis.com/ajax/libs/dojo/1.14.1/dojox/grid/resources/claroGrid.css";
        /*Grid needs an explicit height by default*/
        #scoring_sessionReportGrid {
            height: 20em;
        }
    </style>
  </head>
  <body>
    <div class="body">
      <h1>Scoring session report: ${scoring_sessionsInstance.getName()}</h1>
      <div style="width: 600px">
        <g:showFlashMessage />
        <table>
          <tr class="odd"><td>Scorer</td><td>${scoring_sessionsInstance.getTma_scorer()}</td></tr>
          <tr class="even"><td>Description</td><td>${scoring_sessionsInstance.getDescription()}</td></tr>
          <tr class="odd">
            <td>Start date</td>
            <td><g:formatDate date="${scoring_sessionsInstance.getStart_date()}" type="date" style="LONG"/></td>
          </tr>
          <tr class="even">
            <td style="white-space: nowrap">Date/time of first scored image</td>
            <td><g:formatDate date="${scoring_sessionsInstance.showMinScoring_date()}" type="datetime" style="LONG" timeStyle="SHORT" /></td>
          </tr>
          <tr class="odd">
            <td style="white-space: nowrap">Date/time of last scored image</td>
            <td><g:formatDate date="${scoring_sessionsInstance.showMaxScoring_date()}" type="datetime" style="LONG" timeStyle="SHORT" /></td>
          </tr>         
        </table>
      </div>

      <!-- statistics for ki67-QC project -->
      <g:if test="${showKi67QcStats}">
        <h1>Statistics</h1>
        <div style="width: 250px">
          <table>
            <tr class="odd"><td>rMSE <i>(ideal value: <0.6)</i></td><td>${rMSE}</td></tr>
            <tr class="even"><td>maxdev <i>(ideal value: <1.0)</i></td><td>${maxdev}</td></tr>
          </table>
        </div>
      </g:if>

      <h2>Scoring result</h2>
      [
      <g:link 
        controller="downloadData" 
        action="downloadScoringSessionScores" 
        id="${scoring_sessionsInstance.getId()}"
        title="Click me to download scores in tab-delimited text format">download scores in tab-delimited text format</g:link>
      ]
      <g:if test="${scoring_sessionsInstance?.showHasNucleiSelection() || scoring_sessionsInstance?.showHasNucleiCountNoCoord()}">
        | [<g:link 
          controller="downloadData" 
          action="downloadScoringSessionNucleiSelections" 
          id="${scoring_sessionsInstance.getId()}"
          title="Click me to download nuclei selections in tab-delimited text format">download nuclei selections in tab-delimited text format</g:link>
        ]
      </g:if>
      <g:if test="${scoring_sessionsInstance?.showCompleted() && !scoring_sessionsInstance?.showSubmitted()}">
        | [
        <div style="display: inline-block; background-color: yellow"><g:link 
            controller="scoring_sessions" 
            action="submitScores" 
            id="${scoring_sessionsInstance.getId()}"
            title="WARNING: You will not be able to modify scores once submitted."
            onclick="if(confirm('You will not be able to modify scores once submitted.  Continue to submit?')){showWaitDialogMsg(1,'Please wait ...','Please wait for the page to load ...');return true;} else {return false;}">submit scores</g:link></div>
        ]
      </g:if>
      <div id="scoring_sessionReportGrid">
        ... loading ... please wait
      </div>

    </div>

  <asset:script type="text/javascript">
    require(['dojo/_base/window', 'dojo/on', "dojo/_base/xhr","dojo/domReady!"], function (baseWin, on, xhr) {
    on(window, 'load', function() {

    //showWaitDialog(1);
    
    xhr.get({
    url:'${createLink(controller:"scoring_sessions", action:"ajax_get_report_table", id:scoring_sessionsInstance.getId())}',
    handleAs:"json",
    load:function(e){
    buildScoring_sessionReportTable(e,"scoring_sessionReportGrid");
    setInitialPageBodyHeight();
    setInitialFooterPosition();
    resizeDojoDataGrid_table('scoring_sessionReportGrid');
    //closeWaitDialog();
    },
    onError:function(e){alert("${ViewConstants.UNKNOWN_SERVER_ERROR_MESSAGE }");}
    }); // xhr.get		

    window.onresize=function(){
    setInitialPageBodyHeight();
    setInitialFooterPosition();
    resizeDojoDataGrid_table('scoring_sessionReportGrid'); // set window resize event handler
    }
    });
    }); // function (xhr)
  </asset:script>
</body>
</html>