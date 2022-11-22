
<%@ page import="ca.ubc.gpec.tmadb.Institutions" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'institutions.label', default: 'Institutions')}" />
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
            <td valign="top" class="name"><g:message code="institutions.id.label" default="Id" /></td>

        <td valign="top" class="value">${fieldValue(bean: institutionsInstance, field: "id")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="institutions.country.label" default="Country" /></td>

        <td valign="top" class="value">${fieldValue(bean: institutionsInstance, field: "country")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="institutions.description.label" default="Description" /></td>

        <td valign="top" class="value">${fieldValue(bean: institutionsInstance, field: "description")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="institutions.name.label" default="Name" /></td>

        <td valign="top" class="value">${fieldValue(bean: institutionsInstance, field: "name")}</td>

        </tr>

        </tbody>
      </table>
    </div>

  </div>
</body>
</html>
