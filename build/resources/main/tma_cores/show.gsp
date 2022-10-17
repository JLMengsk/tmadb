
<%@ page import="ca.ubc.gpec.tmadb.ImageViewerMethods"%>
<%@ page import="ca.ubc.gpec.tmadb.Tma_cores"%>
<%@ page import="ca.ubc.gpec.tmadb.util.ViewConstants" %>
<%@ page import="ca.ubc.gpec.tmadb.Keywords" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName"
         value="${message(code: 'tma_cores.label', default: 'TMA core')}" />
  <title><g:message code="default.show.label" args="[entityName]" />
  </title>
</head>
<body>

  <div class="body">
    <div style='display: inline'>
      <h1></h1>
      <g:link controller="tma_projects" action="show" id="${tma_coresInstance.getTma_block().getTma_array().getTma_project().getId()}" title="click me to view TMA project">TMA project: ${tma_coresInstance.getTma_block().getTma_array().getTma_project().getName()}</g:link>
      &#8594;
      <g:link controller="tma_arrays" action="show" id="${tma_coresInstance.getTma_block().getTma_array().getId()}" title="click me to view TMA array">array version ${tma_coresInstance.getTma_block().getTma_array().getArray_version()}</g:link>
      &#8594;
      <g:link controller="tma_blocks" action="show" id="${tma_coresInstance.getTma_block().getId()}" title="click me to view TMA block">block ${tma_coresInstance.getTma_block().getName()}</g:link>
      &#8594;
      <h1 style='display: inline'>row ${tma_coresInstance.getRow()}, column ${tma_coresInstance.getCol()}</h1>
    </div>
    <g:showFlashMessage />
    <g:set var="keyword" value="${tma_coresInstance.showFirstKeyword()}"/>
    <g:if test="${keyword!=null}">
        <p>Keyword: <a title="${keyword.description==null?:keyword.description}">${keyword}</a></p>
    </g:if>
    <div class="dialog">
      <p></p>
      <table>
        <tbody>
          <tr>
            <td>      
              <table>
                <tbody>
                  <tr class="${oddEvenRowFlag.showFlag()}">
                    <td valign="top" class="name">Core ID:</td>
                    <td valign="top" class="value">${tma_coresInstance.getCore_id()}</td>
                  </tr>
                  <tr class="${oddEvenRowFlag.showFlag()}">
                    <td valign="top" class="name">Diameter:</td>
                    <td valign="top" class="value">${tma_coresInstance.getDiameter()} mm</td>
                  </tr>
                  <tr class="${oddEvenRowFlag.showFlag()}">
                    <td valign="top" class="name">Surgical block:</td>
                    <td valign="top" class="value"><g:link controller="surgical_blocks" action="show" id="${tma_coresInstance?.surgical_block?.id}">${tma_coresInstance?.surgical_block?.encodeAsHTML()}</g:link></td>
          </tr>
          <tr class="prop">
            <td valign="top" style="text-align: left;" class="value" colspan="2">
              <div style="width: ${ImageViewerMethods.IMAGE_PREVIEW_WIDTH_LARGE+50}px; height: ${ImageViewerMethods.IMAGE_PREVIEW_HEIGHT_LARGE+50}px">
                <div dojoType="dijit.layout.TabContainer" style="width: 100%; height: 100%;">
                  <g:each in="${availableTmaCoreImages}" var="t">
                    <div dojoType="dijit.layout.ContentPane" title="${t.showBiomarker()}">
                      <g:link controller="tma_core_images" action="show" id="${t.getId()}" title="click me to view core image details"><img src="${t.showPreviewImageURL()}" width="${ImageViewerMethods.IMAGE_PREVIEW_WIDTH_LARGE}px"/></g:link>
                      <br>
                      <div style="width: ${ImageViewerMethods.IMAGE_PREVIEW_WIDTH_LARGE}px">
                        <g:displayTma_core_imageTma_results tma_core_image="${t}" />
                      </div>
                    </div>
                  </g:each>
                </div>     
              </div>
            </td>
          </tr>
        </tbody>
      </table>
      </td>
      <td>
      <g:link controller="tma_blocks" action="show" id="${tma_coresInstance.getTma_block().getId()}" title="${tma_coresInstance.getTma_block().getDescription()}">TMA block ${tma_coresInstance.getTma_block().getName()}</g:link><br>
      <g:displayTma_blockSectorMapInHtmlTable id="${tma_coresInstance.getTma_block().getId()}" mode="tma_core_nav" selectedTma_core="${tma_coresInstance}"/>
      </td>
      </tr>
      </tbody>
      </table>
    </div>

  </div>

</body>
</html>
