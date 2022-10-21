
<%@ page import="ca.ubc.gpec.tmadb.Tma_slices" %>
<%@ page import="ca.ubc.gpec.tmadb.util.ViewConstants" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>TMA slices available to ${user.getName()}</title>
        <asset:stylesheet src="application.css"/>
        <asset:javascript src="application.js"/>
    </head>
    <body>
        <div class="body">
            <h1>TMA slices available to ${user.getName()} (<div id="slide_count" style='display: inline'></div>)</h1>
            <g:showFlashMessage />
            <div class="dialog">
                <p>${user.getDescription()}</p>

                <div id="tma_slicesGrid" 
                style="width: ${ViewConstants.TMA_SLICES_GRID_WIDTH}px; height: ${ViewConstants.TMA_SLICES_GRID_HEIGHT}px;">
                ... loading ... please wait</div>
        </div>

    </div>
</div>

<asset:script type="text/javascript">
    // require(["dojo/_base/xhr"], 
    
    function (xhr) {

    var waitDialogObj = getWaitDialog();

    // get some data, convert to JSON
    xhr.get({
    url:"${createLink(controller:"tma_slices", action:"ajaxGetAvailableTma_slices")}",
    handleAs:"json",
    load: function(e){
    buildTma_slicesBrowserTable(
    "<g:createLink controller="tma_slices"   action="show"/>",
    "<g:createLink controller="tma_blocks"   action="show"/>",
    "<g:createLink controller="tma_arrays"   action="show"/>",
    "<g:createLink controller="tma_projects" action="show"/>",
    "<g:createLink controller="biomarkers"   action="show"/>",
    e,
    "tma_slicesGrid","slide_count");
    resizeDojoDataGrid_table('tma_slicesGrid');
    waitDialogObj.destroy();
    },onError:function(e){alert("${ViewConstants.UNKNOWN_SERVER_ERROR_MESSAGE }");}
    }); // xhr.get

    window.onresize=function(){
    setInitialPageBodyHeight();
    setInitialFooterPosition();
    resizeDojoDataGrid_table('tma_slicesGrid'); // set window resize event handler
    }

    }); // function (xhr)

</asset:script>

<asset:deferredScripts/>
</body>
</html>
