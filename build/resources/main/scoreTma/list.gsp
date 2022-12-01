<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="ca.ubc.gpec.tmadb.Users" %>
<%@ page import="ca.ubc.gpec.tmadb.Scoring_sessions" %>
<%@ page import="ca.ubc.gpec.tmadb.util.ViewConstants" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Score TMA</title>
    <asset:stylesheet src="application.css"/>
    <asset:javascript src="application.js"/>
    <style type="text/css">
        @import "https://ajax.googleapis.com/ajax/libs/dojo/1.14.1/dojox/grid/resources/claroGrid.css";
        /*Grid needs an explicit height by default*/
        #scoring_sessionsGrid {
            height: 20em;
        }
    </style>
  </head>
  <body>
    <div class="body">
      <h1><img src="${resource(dir:'images/skin',file:'scoring.png')}"/> Welcome to scoring session, ${session.user.name}.</h1>
      <g:showFlashMessage />
      <g:each in="${user.showNotice_messages()}" var="msg">
        <div class="message">${msg}</div>
      </g:each>
      <g:if test="${user.showIsAdministrator()}">[<g:link controller="scoring_sessions" action="setup">setup</g:link>]</g:if>
      <h2>Available scoring session</h2>      
      <g:if test="${!msgs.isEmpty()}">
        <img src="${resource(dir:'images/skin',file:'exclamation.png')}"/> Please note ...<br>
        <g:each in="${msgs}" var="msg">
          &nbsp;&nbsp;<img src="${resource(dir:'images/skin',file:'point.jpg')}"/> ${msg}<br>
        </g:each>
        <br>
      </g:if>
      <g:else>
        <p><img src="${resource(dir:'images/skin',file:'check.png')}"/> All scoring sessions completed and results submitted.  Thank you for your participation.</p>
      </g:else>   

      <div id="scoring_sessionsGrid">
        ... loading ... please wait
      </div>

    </div>
  <asset:script type="text/javascript">
    require(['dojo/_base/window', 'dojo/on', "dojo/_base/xhr","dojo/domReady!"], function (baseWin, on, xhr) {
    on(window, 'load', function() {

    //showWaitDialog(1);
    
    xhr.get({
        url: '${createLink(controller:"scoreTma", action:"ajax_get_available_scoring_session")}'+"?dummy="+(new Date()).getTime(), // dummy param added to prevent IE from caching the ajax response
        handleAs:"json",
        load:function(e){
    buildAvailableScoring_sessionsBrowserTable(
    "<g:createLink controller="scoring_sessions" action="score"/>",
    e,
    "scoring_sessionsGrid");
    setInitialPageBodyHeight();
    setInitialFooterPosition();
    resizeDojoDataGrid_table('scoring_sessionsGrid');
    //closeWaitDialog();
    },
    onError:function(e){alert("${ViewConstants.UNKNOWN_SERVER_ERROR_MESSAGE }");}
    }); // xhr.get  

    window.onresize=function(){
    setInitialPageBodyHeight();
    setInitialFooterPosition();
    resizeDojoDataGrid_table('scoring_sessionsGrid'); // set window resize event handler
    }
  });
    }); // function (xhr)
  </asset:script>
</body>
</html>