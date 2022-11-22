
<%@ page import="ca.ubc.gpec.tmadb.Clinical_infos" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'clinical_infos.label', default: 'Clinical_infos')}" />
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
            <td valign="top" class="name"><g:message code="clinical_infos.id.label" default="Id" /></td>

        <td valign="top" class="value">${fieldValue(bean: clinical_infosInstance, field: "id")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="clinical_infos.received_date.label" default="Receiveddate" /></td>

        <td valign="top" class="value"><g:formatDate date="${clinical_infosInstance?.received_date}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="clinical_infos.bcou4543.label" default="Bcou4543" /></td>

        <td valign="top" style="text-align: left;" class="value">
          <ul>
            <g:each in="${clinical_infosInstance.bcou4543}" var="b">
              <li><g:link controller="bcou4543" action="show" id="${b.id}">${b?.encodeAsHTML()}</g:link></li>
            </g:each>
          </ul>
        </td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="clinical_infos.bcou4620.label" default="Bcou4620" /></td>

        <td valign="top" style="text-align: left;" class="value">
          <ul>
            <g:each in="${clinical_infosInstance.bcou4620}" var="b">
              <li><g:link controller="bcou4620" action="show" id="${b.id}">${b?.encodeAsHTML()}</g:link></li>
            </g:each>
          </ul>
        </td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="clinical_infos.comment.label" default="Comment" /></td>

        <td valign="top" class="value">${fieldValue(bean: clinical_infosInstance, field: "comment")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="clinical_infos.patient.label" default="Patient" /></td>

        <td valign="top" class="value"><g:link controller="patients" action="show" id="${clinical_infosInstance?.patient?.id}">${clinical_infosInstance?.patient?.encodeAsHTML()}</g:link></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="clinical_infos.specs2009.label" default="Specs2009" /></td>

        <td valign="top" style="text-align: left;" class="value">
          <ul>
            <g:each in="${clinical_infosInstance.specs2009}" var="s">
              <li><g:link controller="specs2009" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
            </g:each>
          </ul>
        </td>

        </tr>

        </tbody>
      </table>
    </div>
  </div>
</body>
</html>
