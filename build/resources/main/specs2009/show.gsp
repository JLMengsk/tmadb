
<%@ page import="ca.ubc.gpec.tmadb.Specs2009" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'specs2009.label', default: 'Specs2009')}" />
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
            <td valign="top" class="name"><g:message code="specs2009.id.label" default="Id" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "id")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.adjuvant_chemo.label" default="Adjuvantchemo" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "adjuvant_chemo")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.adjuvant_hormone.label" default="Adjuvanthormone" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "adjuvant_hormone")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.age_at_dx.label" default="Ageatdx" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "age_at_dx")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.architectual_grade.label" default="Architectualgrade" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "architectual_grade")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.cancer_status.label" default="Cancerstatus" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "cancer_status")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.clinical_info.label" default="Clinicalinfo" /></td>

        <td valign="top" class="value"><g:link controller="clinical_infos" action="show" id="${specs2009Instance?.clinical_info?.id}">${specs2009Instance?.clinical_info?.encodeAsHTML()}</g:link></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.er.label" default="Er" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "er")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.frozen_grade.label" default="Frozengrade" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "frozen_grade")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.gender.label" default="Gender" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "gender")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.her2.label" default="Her2" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "her2")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.histology.label" default="Histology" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "histology")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.ls_paraffin_grade.label" default="Lsparaffingrade" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "ls_paraffin_grade")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.m_stage.label" default="Mstage" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "m_stage")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.mitotic_grade.label" default="Mitoticgrade" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "mitotic_grade")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.months_at_last_contact.label" default="Monthsatlastcontact" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "months_at_last_contact")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.months_to_recurrance.label" default="Monthstorecurrance" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "months_to_recurrance")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.n_stage.label" default="Nstage" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "n_stage")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.nuclear_grade.label" default="Nucleargrade" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "nuclear_grade")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.paraffin_grade.label" default="Paraffingrade" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "paraffin_grade")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.percent_tumor.label" default="Percenttumor" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "percent_tumor")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.pr.label" default="Pr" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "pr")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.race.label" default="Race" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "race")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.recurrence_site_1.label" default="Recurrencesite1" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "recurrence_site_1")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.recurrence_site_2.label" default="Recurrencesite2" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "recurrence_site_2")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.rna_number.label" default="Rnanumber" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "rna_number")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.stage.label" default="Stage" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "stage")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.t_stage.label" default="Tstage" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "t_stage")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.tissue_number.label" default="Tissuenumber" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "tissue_number")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="specs2009.vital_status.label" default="Vitalstatus" /></td>

        <td valign="top" class="value">${fieldValue(bean: specs2009Instance, field: "vital_status")}</td>

        </tr>

        </tbody>
      </table>
    </div>

  </div>
</body>
</html>
