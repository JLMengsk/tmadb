
<%@ page import="ca.ubc.gpec.tmadb.Biomarkers" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'biomarkers.label', default: 'Biomarkers')}" />
  <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
  <div class="body">
    <h1><g:message code="default.list.label" args="[entityName]" /><g:displayAvailableBiomarkerActions/></h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:each in="${user?.showNotice_messages()}" var="msg">
      <div class="message">${msg}</div>
    </g:each>
    <div class="list">
      <table>
        <thead>
          <tr>
        <g:sortableColumn property="name" title="${message(code: 'biomarkers.name.label', default: 'Name')}" />
        <g:sortableColumn property="biomarker_type" title="${message(code: 'biomarkers.biomarker_type.label', default: 'Type')}" />
        <g:sortableColumn property="description" title="${message(code: 'biomarkers.description.label', default: 'Description')}" />
        </tr>
        </thead>
        <tbody>
        <g:each in="${biomarkersInstanceList}" status="i" var="biomarkersInstance">
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td><g:link action="show" id="${biomarkersInstance.id}">${fieldValue(bean: biomarkersInstance, field: "name")}</g:link></td>
          <td>${fieldValue(bean: biomarkersInstance, field: "biomarker_type")}</td>
          <td>${fieldValue(bean: biomarkersInstance, field: "description")}</td>
          </tr>
        </g:each>
        </tbody>
      </table>
    </div>
    <div class="paginateButtons">
      <g:paginate total="${biomarkersInstanceTotal}" />
    </div>
  </div>
</body>
</html>
