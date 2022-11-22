
<%@ page import="ca.ubc.gpec.tmadb.Tma_scorers" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'tma_scorers.label', default: 'Tma_scorers')}" />
  <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>

  <div class="body">
    <h1><g:message code="default.show.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <div class="dialog">
      <table>
        <tbody>

          <tr class="prop">
            <td valign="top" class="name"><g:message code="tma_scorers.id.label" default="Id" /></td>

        <td valign="top" class="value">${fieldValue(bean: tma_scorersInstance, field: "id")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="tma_scorers.name.label" default="Name" /></td>

        <td valign="top" class="value">${fieldValue(bean: tma_scorersInstance, field: "name")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="tma_scorers.human.label" default="Human" /></td>

        <td valign="top" class="value">${fieldValue(bean: tma_scorersInstance, field: "human")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="tma_scorers.description.label" default="Description" /></td>

        <td valign="top" class="value">${fieldValue(bean: tma_scorersInstance, field: "description")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="tma_scorers.institution.label" default="Institution" /></td>

        <td valign="top" class="value"><g:link controller="institutions" action="show" id="${tma_scorersInstance?.institution?.id}">${tma_scorersInstance?.institution?.encodeAsHTML()}</g:link></td>

        </tr>

        </tbody>
      </table>
    </div>

  </div>
</body>
</html>
