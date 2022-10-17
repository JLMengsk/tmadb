<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="ca.ubc.gpec.tmadb.util.MiscUtil"%>
<div data-dojo-type="dijit.Menu" id="mainMenu" contextMenuForWindow="false" style="display: none;" leftClickToOpen="true" targetNodeIds="gpecLogo">
  <div data-dojo-type="dijit.MenuItem" iconClass='homeIcon' onClick="cleanUp(); window.location = '${MiscUtil.showIsLoggedIn(session)?(session.user.preferredHomeControllerName()!=null?createLink(controller:session.user.preferredHomeControllerName()):grailsApplication.config.grails.serverSecureURL):grailsApplication.config.grails.serverURL}'">Home</div>
  <div data-dojo-type="dijit.PopupMenuItem">
    <span>Database contents</span>
    <div data-dojo-type="dijit.Menu" id='databaseContentsMenu'>
      <div data-dojo-type="dijit.MenuItem" onClick="cleanUp(); window.location = '${createLink(controller:"biomarkers")}'">Biomarkers</div>
      <div data-dojo-type="dijit.MenuItem" onClick="cleanUp(); window.location = '${createLink(controller:"tma_projects")}'">TMA projects</div>
      <div data-dojo-type="dijit.MenuItem" iconClass='tmaIcon' onClick="cleanUp(); window.location = '${createLink(controller:"tma_slices")}'">TMA slices</div>
      <div data-dojo-type="dijit.MenuItem" iconClass='whole_sectionIcon' onClick="cleanUp(); window.location = '${createLink(controller:"whole_section_slices")}'">Whole section slices</div>
    </div>
  </div>
  <div data-dojo-type="dijit.PopupMenuItem">
    <span>Database queries</span>
    <div data-dojo-type="dijit.Menu" id='databaseQueriessMenu'>
      <div data-dojo-type="dijit.MenuItem" onClick="cleanUp(); window.location = '${createLink(controller:"downloadData")}'">Download TMA related data</div>
    </div>
  </div>
  <div data-dojo-type="dijit.PopupMenuItem">
    <span>Import/input data tasks</span>
    <div data-dojo-type="dijit.Menu" id='databaseInputMenu'>
      <div data-dojo-type="dijit.MenuItem" iconClass='scoringIcon' onClick="cleanUp(); window.location = '${createLink(controller:"scoreTma")}'">Score TMA</div>
      <div data-dojo-type="dijit.MenuItem" onClick="cleanUp(); window.location = '${createLink(controller:"uploadTma")}'">Upload TMA related data</div>
      <div data-dojo-type="dijit.MenuItem" iconClass='calibrationIcon' onClick="cleanUp(); window.location = '${createLink(controller:"calibrator")}'">Ki67-QC Calibrator</div>
    </div>
  </div>
  <g:if test="${MiscUtil.showIsLoggedIn(session)}">
    <div data-dojo-type="dijit.MenuItem" iconClass='logOutIcon' onClick="cleanUp(); window.location = '${createLink(action:"logout", controller:"users")}'">Sign out</div>
  </g:if>
</div>
<div><img 
    id="gpecLogo" 
    src="${resource(dir:'images',file:'gpec_logo.png')}" 
    onmouseover="this.src='${resource(dir:'images',file:'gpec_logo_glow.png')}'" 
    onmouseout="this.src='${resource(dir:'images',file:'gpec_logo.png')}'"
    alt="GPEC" 
    border="0" 
    title="Please click me for available options"/></div>


<div id="loginHeader"><g:loginControl /></div>
