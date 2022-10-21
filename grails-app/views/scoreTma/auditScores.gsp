<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="ca.ubc.gpec.tmadb.Users" %>
<%@ page import="ca.ubc.gpec.tmadb.Scoring_sessions" %>
<%@ page import="ca.ubc.gpec.tmadb.util.ViewConstants" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Score TMA</title>
  </head>
  <body>
    <div class="body">
      <h1>Welcome to TMA score audit, ${session.user.name}.</h1>
      <g:if test="${user.showIsAdministrator()}">[<g:link controller="scoring_sessions" action="setup">setup</g:link>]</g:if>
      <h2>Submitted scoring session</h2>

      <div id="submitted_scoring_sessionsGrid" 
           style="width: ${ViewConstants.SCORING_SESSIONS_GRID_WIDTH}px; height: ${ViewConstants.SCORING_SESSIONS_GRID_HEIGHT}px;">
        ... loading ... please wait
      </div>   
    </div>
  <asset:stylesheet src="application.css"/>
  <asset:javascript src="application.js"/>
  <asset:script type="text/javascript" disposition="head">
    dojo.ready(function() {
    showWaitDialog(1);
    new Ajax.Request('${createLink(controller:"scoreTma", action:"ajax_get_submitted_scoring_sessions_for_score_audit")}',{
    asynchronous:true,
    evalScripts:true,
    onComplete:function(e){
    buildSubmittedScoring_sessionsBrowserTable(
    "<g:createLink controller="scoring_sessions" action="report"/>",
    "<g:createLink controller='downloadData' action='downloadScoringSessionScores'/>",
    e,
    "submitted_scoring_sessionsGrid");
    setInitialPageBodyHeight();
    setInitialFooterPosition();
    resizeDojoDataGrid_table('submitted_scoring_sessionsGrid');
    closeWaitDialog();
    },onError:function(e){alert("${ViewConstants.UNKNOWN_SERVER_ERROR_MESSAGE }");}});		

    window.onresize=function(){
    setInitialPageBodyHeight();
    setInitialFooterPosition();
    resizeDojoDataGrid_table('submitted_scoring_sessionsGrid'); // set window resize event handler
    }
    });
  </asset:script>
</body>
</html>