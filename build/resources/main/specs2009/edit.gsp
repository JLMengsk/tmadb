

<%@ page import="ca.ubc.gpec.tmadb.Specs2009" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'specs2009.label', default: 'Specs2009')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${specs2009Instance}">
            <div class="errors">
                <g:renderErrors bean="${specs2009Instance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${specs2009Instance?.id}" />
                <g:hiddenField name="version" value="${specs2009Instance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="adjuvant_chemo"><g:message code="specs2009.adjuvant_chemo.label" default="Adjuvantchemo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 'adjuvant_chemo', 'errors')}">
                                    <g:textField name="adjuvant_chemo" value="${specs2009Instance?.adjuvant_chemo}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="adjuvant_hormone"><g:message code="specs2009.adjuvant_hormone.label" default="Adjuvanthormone" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 'adjuvant_hormone', 'errors')}">
                                    <g:textField name="adjuvant_hormone" value="${specs2009Instance?.adjuvant_hormone}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="age_at_dx"><g:message code="specs2009.age_at_dx.label" default="Ageatdx" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 'age_at_dx', 'errors')}">
                                    <g:textField name="age_at_dx" value="${fieldValue(bean: specs2009Instance, field: 'age_at_dx')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="architectual_grade"><g:message code="specs2009.architectual_grade.label" default="Architectualgrade" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 'architectual_grade', 'errors')}">
                                    <g:textField name="architectual_grade" value="${specs2009Instance?.architectual_grade}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="cancer_status"><g:message code="specs2009.cancer_status.label" default="Cancerstatus" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 'cancer_status', 'errors')}">
                                    <g:textField name="cancer_status" value="${specs2009Instance?.cancer_status}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="clinical_info"><g:message code="specs2009.clinical_info.label" default="Clinicalinfo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 'clinical_info', 'errors')}">
                                    <g:select name="clinical_info.id" from="${ca.ubc.gpec.tmadb.Clinical_infos.list()}" optionKey="id" value="${specs2009Instance?.clinical_info?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="er"><g:message code="specs2009.er.label" default="Er" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 'er', 'errors')}">
                                    <g:textField name="er" value="${specs2009Instance?.er}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="frozen_grade"><g:message code="specs2009.frozen_grade.label" default="Frozengrade" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 'frozen_grade', 'errors')}">
                                    <g:textField name="frozen_grade" value="${specs2009Instance?.frozen_grade}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="gender"><g:message code="specs2009.gender.label" default="Gender" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 'gender', 'errors')}">
                                    <g:textField name="gender" value="${specs2009Instance?.gender}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="her2"><g:message code="specs2009.her2.label" default="Her2" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 'her2', 'errors')}">
                                    <g:textField name="her2" value="${specs2009Instance?.her2}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="histology"><g:message code="specs2009.histology.label" default="Histology" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 'histology', 'errors')}">
                                    <g:textField name="histology" value="${specs2009Instance?.histology}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="ls_paraffin_grade"><g:message code="specs2009.ls_paraffin_grade.label" default="Lsparaffingrade" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 'ls_paraffin_grade', 'errors')}">
                                    <g:textField name="ls_paraffin_grade" value="${specs2009Instance?.ls_paraffin_grade}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="m_stage"><g:message code="specs2009.m_stage.label" default="Mstage" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 'm_stage', 'errors')}">
                                    <g:textField name="m_stage" value="${specs2009Instance?.m_stage}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="mitotic_grade"><g:message code="specs2009.mitotic_grade.label" default="Mitoticgrade" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 'mitotic_grade', 'errors')}">
                                    <g:textField name="mitotic_grade" value="${specs2009Instance?.mitotic_grade}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="months_at_last_contact"><g:message code="specs2009.months_at_last_contact.label" default="Monthsatlastcontact" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 'months_at_last_contact', 'errors')}">
                                    <g:textField name="months_at_last_contact" value="${fieldValue(bean: specs2009Instance, field: 'months_at_last_contact')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="months_to_recurrance"><g:message code="specs2009.months_to_recurrance.label" default="Monthstorecurrance" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 'months_to_recurrance', 'errors')}">
                                    <g:textField name="months_to_recurrance" value="${fieldValue(bean: specs2009Instance, field: 'months_to_recurrance')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="n_stage"><g:message code="specs2009.n_stage.label" default="Nstage" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 'n_stage', 'errors')}">
                                    <g:textField name="n_stage" value="${specs2009Instance?.n_stage}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="nuclear_grade"><g:message code="specs2009.nuclear_grade.label" default="Nucleargrade" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 'nuclear_grade', 'errors')}">
                                    <g:textField name="nuclear_grade" value="${specs2009Instance?.nuclear_grade}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="paraffin_grade"><g:message code="specs2009.paraffin_grade.label" default="Paraffingrade" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 'paraffin_grade', 'errors')}">
                                    <g:textField name="paraffin_grade" value="${specs2009Instance?.paraffin_grade}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="percent_tumor"><g:message code="specs2009.percent_tumor.label" default="Percenttumor" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 'percent_tumor', 'errors')}">
                                    <g:textField name="percent_tumor" value="${fieldValue(bean: specs2009Instance, field: 'percent_tumor')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="pr"><g:message code="specs2009.pr.label" default="Pr" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 'pr', 'errors')}">
                                    <g:textField name="pr" value="${specs2009Instance?.pr}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="race"><g:message code="specs2009.race.label" default="Race" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 'race', 'errors')}">
                                    <g:textField name="race" value="${specs2009Instance?.race}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="recurrence_site_1"><g:message code="specs2009.recurrence_site_1.label" default="Recurrencesite1" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 'recurrence_site_1', 'errors')}">
                                    <g:textField name="recurrence_site_1" value="${specs2009Instance?.recurrence_site_1}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="recurrence_site_2"><g:message code="specs2009.recurrence_site_2.label" default="Recurrencesite2" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 'recurrence_site_2', 'errors')}">
                                    <g:textField name="recurrence_site_2" value="${specs2009Instance?.recurrence_site_2}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="rna_number"><g:message code="specs2009.rna_number.label" default="Rnanumber" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 'rna_number', 'errors')}">
                                    <g:textField name="rna_number" value="${fieldValue(bean: specs2009Instance, field: 'rna_number')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="stage"><g:message code="specs2009.stage.label" default="Stage" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 'stage', 'errors')}">
                                    <g:textField name="stage" value="${specs2009Instance?.stage}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="t_stage"><g:message code="specs2009.t_stage.label" default="Tstage" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 't_stage', 'errors')}">
                                    <g:textField name="t_stage" value="${specs2009Instance?.t_stage}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tissue_number"><g:message code="specs2009.tissue_number.label" default="Tissuenumber" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 'tissue_number', 'errors')}">
                                    <g:textField name="tissue_number" value="${fieldValue(bean: specs2009Instance, field: 'tissue_number')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="vital_status"><g:message code="specs2009.vital_status.label" default="Vitalstatus" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: specs2009Instance, field: 'vital_status', 'errors')}">
                                    <g:textField name="vital_status" value="${specs2009Instance?.vital_status}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
