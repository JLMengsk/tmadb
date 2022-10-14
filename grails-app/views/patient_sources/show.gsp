
<%@ page import="ca.ubc.gpec.tmadb.Patient_sources"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName"
         value="${message(code: 'patient_sources.label', default: 'Patient_sources')}" />
  <title><g:message code="default.show.label" args="[entityName]" />
  </title>
</head>
<body>
  <div class="body">
    <h1>Patient source: ${patient_sourcesInstance.getName()}</h1>
    <g:showFlashMessage />
    <div class="dialog">
      <table>
        <tbody>
          <tr class="prop">
            <td valign="top" class="name"><g:message
          code="patient_sources.institution.label" default="Institution" />
        </td>

        <td valign="top" class="value"><g:link
          controller="institutions" action="show"
          id="${patient_sourcesInstance?.institution?.id}">
${patient_sourcesInstance?.institution?.encodeAsHTML()}
        </g:link>
        </td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message
          code="patient_sources.notes.label" default="Notes" />
        </td>

        <td valign="top" class="value">
${fieldValue(bean: patient_sourcesInstance, field: "notes")}
        </td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message
          code="patient_sources.patients.label" default="Patients" />
        </td>

        <td valign="top" style="text-align: left;" class="value">

          <div class="list">
            <table>
              <thead>
                <tr>

              <g:sortableColumn property="id"
                                title="${message(code: 'patients.id.label', default: 'Id')}" />

              <g:sortableColumn property="comment"
                                title="${message(code: 'patients.comment.label', default: 'Comment')}" />

              <g:sortableColumn property="patient_id_txt"
                                title="${message(code: 'patients.patient_id_txt.label', default: 'Patientidtxt')}" />

              <g:sortableColumn property="patient_id_txt2"
                                title="${message(code: 'patients.patient_id_txt2.label', default: 'Patientidtxt2')}" />

              </tr>
              </thead>
              <tbody>
              <g:each in="${patient_sourcesInstance?.patients}" status="i"
                      var="patientsInstance">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                  <td><g:link controller="patients" action="show" id="${patientsInstance.id}">
${fieldValue(bean: patientsInstance, field: "id")}
                </g:link>
                </td>

                <td>
${fieldValue(bean: patientsInstance, field: "comment")}
                </td>

                <td>
${fieldValue(bean: patientsInstance, field: "patient_id_txt")}
                </td>

                <td>
${fieldValue(bean: patientsInstance, field: "patient_id_txt2")}
                </td>

                </tr>
              </g:each>
              </tbody>
            </table>
          </div>
          <div class="paginateButtons">
            <g:paginate controller="Patient_sources" action="show" total="${patient_sourcesInstance?.patients.size()}" />
          </div></td>

        </tr>

        </tbody>
      </table>
    </div>

  </div>
</body>
</html>
