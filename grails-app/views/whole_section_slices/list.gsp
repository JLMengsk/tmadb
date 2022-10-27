
<%@ page import="ca.ubc.gpec.tmadb.Whole_section_slices" %>
<%@ page import="ca.ubc.gpec.tmadb.util.ViewConstants" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Whole section slices available to ${user.getName()}</title>
    <asset:stylesheet src="application.css"/>
    <asset:javascript src="application.js"/>
  </head>
  <body>
    <div class="body">
      <h1>Whole section slices available to ${user.getName()} (<div id="slide_count" style='display: inline'></div>)</h1>
      <g:showFlashMessage />
      <div class="dialog">
        <p>${user.getDescription()}</p>

        <div id="whole_section_slicesGrid" 
             style="width: ${ViewConstants.WHOLE_SECTION_SLICES_GRID_WIDTH}px; height: ${ViewConstants.WHOLE_SECTION_SLICES_GRID_HEIGHT}px;">
          ... loading ... please wait
        </div>

      </div>
    </div>

<asset:script type="text/javascript" disposition="head">    

    require(["dojo/_base/xhr"], function (xhr) {

    //var waitDialogObj = getWaitDialog();

    // get some data, convert to JSON
    xhr.get({
    url:"${createLink(controller:"whole_section_slices", action:"ajaxGetAvailableWhole_section_slices")}",
    handleAs:"json",
    load: function(e){
    buildWhole_section_slicesBrowserTable(
    e,
    "whole_section_slicesGrid",
    "slide_count");
    resizeDojoDataGrid_table('whole_section_slicesGrid');
    //waitDialogObj.destroy();
    closeWaitDialog();
    },onError:function(e){alert("${ViewConstants.UNKNOWN_SERVER_ERROR_MESSAGE }");}
    }); // xhr.get

    window.onresize=function(){
    setInitialPageBodyHeight();
    setInitialFooterPosition();
    resizeDojoDataGrid_table('whole_section_slicesGrid'); // set window resize event handler
    }

    }); // function (xhr)
</asset:script>
</body>
</html>
