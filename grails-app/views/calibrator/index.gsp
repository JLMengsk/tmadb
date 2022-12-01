<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="ca.ubc.gpec.tmadb.util.MiscUtil"%>
<%@ page import="ca.ubc.gpec.tmadb.util.ViewConstants" %>
<!--
Ki67-QC calibrator intro page
-->
<html>
  <head>
    <meta name="layout" content="main"/>
    <title>Welcome to Ki67-QC calibrator</title>
  </head>
  <body>
    <div class="body">
      <h1><img src="${resource(dir:'images/skin',file:'calibration.png')}"/> Welcome to Ki67-QC calibrator</h1>
      <p>Polley <i>et al.</i> An international study to increase concordance in 
        Ki67 scoring.</p>
      <p>This web-based calibration exercise was designed to calibrate pathologists 
        to a prescribed scoring method. Nine “training” and nine 
        “test” tissue microarray cases representing a wide range of Ki67 scores were 
        chosen to serve as the calibration cases.</p>
      <p>This website interface allows pathologists to score the web-image cores 
        via computer mouse click on individual cells. The website software 
        tracks which cells were scored, where those cells were located within 
        the core, whether each cell was scored positive or negative, and the 
        overall Ki67 score for each core. Images of H&E-stained sections from 
        the cores were also uploaded for reference. </p>
      <p>The specific Ki67 scoring procedure is described in detail <a href='http://www.gpec.ubc.ca/calibrator_doc/nuclei_count_method.pdf'>here <img src='${resource(dir:'images/skin',file:'pdf_icon.jpg')}'/></a>.</p>
      <h2>Try the calibration exercise yourself ...</h2>
      <g:if test="${session.user?.showIsScorer()}">
        <ul>
          <li><a href="${MiscUtil.showIsLoggedIn(session)?(session.user.preferredHomeControllerName()!=null?createLink(controller:session.user.preferredHomeControllerName()):grailsApplication.config.grails.serverSecureURL):grailsApplication.config.grails.serverURL}">click here to start/continue calibration exercise</a></li>
        </ul>
      </g:if>
      <g:else>
        <g:if test="${session.user==null}">
          <p>Please follow the instructions below to try the calibration exercise.</p>
          <ol>
            <li>If you haven't done so already, please <g:createAccountLink label="${"create an user account"}"/></li>
            <li><a href="${g.signInLink()}" title="click me to sign in">sign in</a> and follow the instructions</li>
          </ol>
        </g:if>
        <g:else>
          <p>Sorry, calibration exercise is not available for you.  Please sign out and create an user account or sign in as another user.</p>
        </g:else>
      </g:else>
    </div>
  </body>
</html>
