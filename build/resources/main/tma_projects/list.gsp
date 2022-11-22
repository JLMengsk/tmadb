
<%@ page import="ca.ubc.gpec.tmadb.Tma_projects" %>
<%@ page import="ca.ubc.gpec.tmadb.util.ViewConstants" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>TMA projects available to ${user.getName()}</title>
        <asset:stylesheet src="application.css"/>
        <asset:javascript src="application.js"/>
        <style type="text/css">
        @import "https://ajax.googleapis.com/ajax/libs/dojo/1.14.1/dojox/grid/resources/claroGrid.css";
        /*Grid needs an explicit height by default*/
        #tma_projectsGrid {
            height: 20em;
        }
        </style>
    </head>
    <body>
        <div class="body">
            <h1 id="greeting">TMA projects available to ${user.getName()} (<div id="project_count" style='display: inline'></div>)</h1>
            <g:showFlashMessage />
            <div class="dialog">
                <p>${user.getDescription()}</p>

                <div id="tma_projectsGrid">
                ... loading ... please wait</div>
        </div>
    </div>

<asset:script type="text/javascript" disposition="head">    
    require([
        'dojo/_base/xhr'
    ], function (xhr) {

    // showWaitDialog(1);

    xhr.get({
    url: '${createLink(controller:"tma_projects", action:"ajaxGetTma_projects")}',
    handleAs:"json",
    load:function(e){
    buildTma_projectsBrowserTable(
    "<g:createLink controller="tma_projects" action="show"/>",
    e,
    "tma_projectsGrid","project_count");
    setInitialPageBodyHeight();
    setInitialFooterPosition();
    resizeDojoDataGrid_table('tma_projectsGrid');
    closeWaitDialog();
    },
    onError:function(e){alert("${ViewConstants.UNKNOWN_SERVER_ERROR_MESSAGE }");}
    });	// xhr.get	

    window.onresize=function(){
    setInitialPageBodyHeight();
    setInitialFooterPosition();
    resizeDojoDataGrid_table('tma_projectsGrid'); // set window resize event handler
    }
    }); // function (xhr)
</asset:script>
</body>
</html>
