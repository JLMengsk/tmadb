
<%@ page import="ca.ubc.gpec.tmadb.Tma_core_images" %>
<%@ page import="ca.ubc.gpec.tmadb.DisplayConstant" %>
<%@ page import="ca.ubc.gpec.tmadb.ScoreType" %>
<%@ page import="ca.ubc.gpec.tmadb.util.ViewConstants" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'tma_core_images.label', default: 'TMA core image')}" />
  <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
  <div class="body">
    <div style='display: inline'>
      <h1></h1>
      <g:link controller="tma_projects" action="show" id="${tma_core_imagesInstance.getTma_core().getTma_block().getTma_array().getTma_project().getId()}" title="click me to view TMA project">TMA project: ${tma_core_imagesInstance.getTma_core().getTma_block().getTma_array().getTma_project().getName()}</g:link>
      &#8594;
      <g:link controller="tma_arrays" action="show" id="${tma_core_imagesInstance.getTma_core().getTma_block().getTma_array().getId()}" title="click me to view TMA array">array version ${tma_core_imagesInstance.getTma_core().getTma_block().getTma_array().getArray_version()}</g:link>
      &#8594;
      <g:link controller="tma_blocks" action="show" id="${tma_core_imagesInstance.getTma_core().getTma_block().getId()}" title="click me to view TMA block">block ${tma_core_imagesInstance.getTma_core().getTma_block().getName()}</g:link>
      &#8594;
      <g:link controller="tma_cores" action="show" id="${tma_core_imagesInstance.getTma_core().getId()}" title="click me to view TMA core">row ${tma_core_imagesInstance.getTma_core().getRow()}, column ${tma_core_imagesInstance.getTma_core().getCol()}</g:link>
      &#8594;
      <h1 style='display: inline'>${tma_core_imagesInstance.showBiomarker()}</h1>
    </div>
    <g:showFlashMessage />
    <div class="dialog">
      <p></p>
      <g:displayTma_core_image 
      id="${tma_core_imagesInstance.getId()}" 
      tma_result_rep="${tma_result_rep==null?null:tma_result_rep}"/> 
    </div>
  </div>
<r:require module="image_helpers"/>
</body>
</html>
