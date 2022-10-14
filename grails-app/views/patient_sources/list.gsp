
<%@ page import="ca.ubc.gpec.tmadb.Patient_sources" %>
<html>
  <head>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Available patient sources</title>
  </head>
  <body>
    <div class="body">
      <h1>Available patient sources</h1>
      <g:showFlashMessage />

      <div class="list">
        <table>
          <thead>
            <tr>

          <g:sortableColumn property="id" title="${message(code: 'patient_sources.id.label', default: 'Id')}" />

          <g:sortableColumn property="name" title="${message(code: 'patient_sources.name.label', default: 'Name')}" />

          <th><g:message code="patient_sources.institution.label" default="Institution" /></th>

          <g:sortableColumn property="notes" title="${message(code: 'patient_sources.notes.label', default: 'Notes')}" />

          </tr>
          </thead>
          <tbody>
          <g:each in="${patient_sourcesInstanceList}" status="i" var="patient_sourcesInstance">
            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

              <td><g:link action="show" id="${patient_sourcesInstance.id}">${fieldValue(bean: patient_sourcesInstance, field: "id")}</g:link></td>

            <td>${fieldValue(bean: patient_sourcesInstance, field: "name")}</td>

            <td>${fieldValue(bean: patient_sourcesInstance, field: "institution")}</td>

            <td>${fieldValue(bean: patient_sourcesInstance, field: "notes")}</td>

            </tr>
          </g:each>
          </tbody>
        </table>
      </div>
      <div class="paginateButtons">
        <g:paginate total="${patient_sourcesInstanceTotal}" />
      </div>
    </div>
  </body>
</html>
