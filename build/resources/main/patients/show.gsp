
<%@ page import="ca.ubc.gpec.tmadb.Patients" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'patients.label', default: 'Patients')}" />
  <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
  <div class="body">
    <h1>Patient: ${patientsInstance.getPatient_id_txt()}</h1>
    <g:showFlashMessage />
    <div class="dialog">
      <table>
        <tbody>

        <g:if test="${!patientsInstance.getClinical_infos().isEmpty()}">
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name">Clinical infos:</td>
            <td valign="top" style="text-align: left;" class="value">
              <ul>
                <g:each in="${patientsInstance.clinical_infos}" var="c">
                  <li><g:link controller="clinical_infos" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
                </g:each>
              </ul>
            </td>
          </tr>
        </g:if>

        <g:if test="${patientsInstance.getComment()!=null && patientsInstance.getComment()!=""}">
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name">Comment:</td>
            <td valign="top" class="value">${patientsInstance.getComment()}</td>
          </tr>
        </g:if>

        <g:if test="${patientsInstance.getPatient_id_txt2()!=null && patientsInstance.getPatient_id_txt2()!=""}">
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name">Additional patient ID (text):</td>
            <td valign="top" class="value">${patientsInstance.getPatient_id_txt2()}</td>
          </tr>
        </g:if>

        <tr class="${oddEvenRowFlag.showFlag()}">
          <td valign="top" class="name">Patient source:</td>
          <td valign="top" class="value"><g:link controller="patient_sources" action="show" id="${patientsInstance?.patient_source?.id}">${patientsInstance?.patient_source?.encodeAsHTML()}</g:link></td>
        </tr>

        <tr class="${oddEvenRowFlag.showFlag()}">
          <td valign="top" class="name">Paraffin blocks:</td>

          <td valign="top" style="text-align: left;" class="value">
            <ul>
              <g:each in="${patientsInstance.getParaffin_blocks()}" var="s">
                <li>
                <g:if test="${s.showIsSurgical_block()}">
                  <g:link controller="surgical_blocks" action="show" id="${s.getSurgical_block().getId()}">${s?.encodeAsHTML()}</g:link>
                </g:if>
                <g:elseif test="${s.showIsCore_biopsy_block()}">
                  <g:link controller="core_biopsy_blocks" action="show" id="${s.getCore_biopsy_block().getId()}">${s?.encodeAsHTML()}</g:link>
                </g:elseif>
                <g:else>
                  <g:link controller="paraffin_blocks" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link>
                </g:else>
                </li>
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
