

<%@ page import="ca.ubc.gpec.tmadb.Bcou4543" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'bcou4543.label', default: 'Bcou4543')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${bcou4543Instance}">
            <div class="errors">
                <g:renderErrors bean="${bcou4543Instance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="gpec_id"><g:message code="bcou4543.gpec_id.label" default="Gpecid" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'gpec_id', 'errors')}">
                                    <g:textField name="gpec_id" value="${fieldValue(bean: bcou4543Instance, field: 'gpec_id')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="a_o_2"><g:message code="bcou4543.a_o_2.label" default="Ao2" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'a_o_2', 'errors')}">
                                    <g:textField name="a_o_2" value="${bcou4543Instance?.a_o_2}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="a_o_3"><g:message code="bcou4543.a_o_3.label" default="Ao3" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'a_o_3', 'errors')}">
                                    <g:textField name="a_o_3" value="${bcou4543Instance?.a_o_3}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="age_at_diagnosis"><g:message code="bcou4543.age_at_diagnosis.label" default="Ageatdiagnosis" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'age_at_diagnosis', 'errors')}">
                                    <g:textField name="age_at_diagnosis" value="${fieldValue(bean: bcou4543Instance, field: 'age_at_diagnosis')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="bcca_chemo"><g:message code="bcou4543.bcca_chemo.label" default="Bccachemo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'bcca_chemo', 'errors')}">
                                    <g:textField name="bcca_chemo" value="${bcou4543Instance?.bcca_chemo}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="bcca_cod"><g:message code="bcou4543.bcca_cod.label" default="Bccacod" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'bcca_cod', 'errors')}">
                                    <g:textField name="bcca_cod" value="${bcou4543Instance?.bcca_cod}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="bcca_cod_desc"><g:message code="bcou4543.bcca_cod_desc.label" default="Bccacoddesc" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'bcca_cod_desc', 'errors')}">
                                    <g:textField name="bcca_cod_desc" value="${bcou4543Instance?.bcca_cod_desc}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="bcca_horm"><g:message code="bcou4543.bcca_horm.label" default="Bccahorm" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'bcca_horm', 'errors')}">
                                    <g:textField name="bcca_horm" value="${bcou4543Instance?.bcca_horm}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="behavior"><g:message code="bcou4543.behavior.label" default="Behavior" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'behavior', 'errors')}">
                                    <g:textField name="behavior" value="${bcou4543Instance?.behavior}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="clinical_info"><g:message code="bcou4543.clinical_info.label" default="Clinicalinfo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'clinical_info', 'errors')}">
                                    <g:select name="clinical_info.id" from="${ca.ubc.gpec.tmadb.Clinical_infos.list()}" optionKey="id" value="${bcou4543Instance?.clinical_info?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="death_cause_orig_desc"><g:message code="bcou4543.death_cause_orig_desc.label" default="Deathcauseorigdesc" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'death_cause_orig_desc', 'errors')}">
                                    <g:textField name="death_cause_orig_desc" value="${bcou4543Instance?.death_cause_orig_desc}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="death_cause_original"><g:message code="bcou4543.death_cause_original.label" default="Deathcauseoriginal" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'death_cause_original', 'errors')}">
                                    <g:textField name="death_cause_original" value="${bcou4543Instance?.death_cause_original}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="death_date"><g:message code="bcou4543.death_date.label" default="Deathdate" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'death_date', 'errors')}">
                                    <g:datePicker name="death_date" precision="day" value="${bcou4543Instance?.death_date}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="death_sec_cause"><g:message code="bcou4543.death_sec_cause.label" default="Deathseccause" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'death_sec_cause', 'errors')}">
                                    <g:textField name="death_sec_cause" value="${bcou4543Instance?.death_sec_cause}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="death_sec_cause_desc"><g:message code="bcou4543.death_sec_cause_desc.label" default="Deathseccausedesc" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'death_sec_cause_desc', 'errors')}">
                                    <g:textField name="death_sec_cause_desc" value="${bcou4543Instance?.death_sec_cause_desc}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="diagnosis_date"><g:message code="bcou4543.diagnosis_date.label" default="Diagnosisdate" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'diagnosis_date', 'errors')}">
                                    <g:datePicker name="diagnosis_date" precision="day" value="${bcou4543Instance?.diagnosis_date}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="distant_completeness"><g:message code="bcou4543.distant_completeness.label" default="Distantcompleteness" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'distant_completeness', 'errors')}">
                                    <g:textField name="distant_completeness" value="${bcou4543Instance?.distant_completeness}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="distcat1"><g:message code="bcou4543.distcat1.label" default="Distcat1" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'distcat1', 'errors')}">
                                    <g:select name="distcat1.id" from="${ca.ubc.gpec.tmadb.Site_of_mets_codings.list()}" optionKey="id" value="${bcou4543Instance?.distcat1?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="distcat2"><g:message code="bcou4543.distcat2.label" default="Distcat2" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'distcat2', 'errors')}">
                                    <g:select name="distcat2.id" from="${ca.ubc.gpec.tmadb.Site_of_mets_codings.list()}" optionKey="id" value="${bcou4543Instance?.distcat2?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="distcat3"><g:message code="bcou4543.distcat3.label" default="Distcat3" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'distcat3', 'errors')}">
                                    <g:select name="distcat3.id" from="${ca.ubc.gpec.tmadb.Site_of_mets_codings.list()}" optionKey="id" value="${bcou4543Instance?.distcat3?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="distcat4"><g:message code="bcou4543.distcat4.label" default="Distcat4" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'distcat4', 'errors')}">
                                    <g:select name="distcat4.id" from="${ca.ubc.gpec.tmadb.Site_of_mets_codings.list()}" optionKey="id" value="${bcou4543Instance?.distcat4?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="distcat5"><g:message code="bcou4543.distcat5.label" default="Distcat5" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'distcat5', 'errors')}">
                                    <g:select name="distcat5.id" from="${ca.ubc.gpec.tmadb.Site_of_mets_codings.list()}" optionKey="id" value="${bcou4543Instance?.distcat5?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="distdate1"><g:message code="bcou4543.distdate1.label" default="Distdate1" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'distdate1', 'errors')}">
                                    <g:datePicker name="distdate1" precision="day" value="${bcou4543Instance?.distdate1}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="distdate2"><g:message code="bcou4543.distdate2.label" default="Distdate2" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'distdate2', 'errors')}">
                                    <g:datePicker name="distdate2" precision="day" value="${bcou4543Instance?.distdate2}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="distdate3"><g:message code="bcou4543.distdate3.label" default="Distdate3" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'distdate3', 'errors')}">
                                    <g:datePicker name="distdate3" precision="day" value="${bcou4543Instance?.distdate3}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="distdate4"><g:message code="bcou4543.distdate4.label" default="Distdate4" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'distdate4', 'errors')}">
                                    <g:datePicker name="distdate4" precision="day" value="${bcou4543Instance?.distdate4}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="distdate5"><g:message code="bcou4543.distdate5.label" default="Distdate5" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'distdate5', 'errors')}">
                                    <g:datePicker name="distdate5" precision="day" value="${bcou4543Instance?.distdate5}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="distsite1"><g:message code="bcou4543.distsite1.label" default="Distsite1" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'distsite1', 'errors')}">
                                    <g:textField name="distsite1" value="${bcou4543Instance?.distsite1}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="distsite2"><g:message code="bcou4543.distsite2.label" default="Distsite2" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'distsite2', 'errors')}">
                                    <g:textField name="distsite2" value="${bcou4543Instance?.distsite2}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="distsite3"><g:message code="bcou4543.distsite3.label" default="Distsite3" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'distsite3', 'errors')}">
                                    <g:textField name="distsite3" value="${bcou4543Instance?.distsite3}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="distsite4"><g:message code="bcou4543.distsite4.label" default="Distsite4" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'distsite4', 'errors')}">
                                    <g:textField name="distsite4" value="${bcou4543Instance?.distsite4}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="distsite5"><g:message code="bcou4543.distsite5.label" default="Distsite5" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'distsite5', 'errors')}">
                                    <g:textField name="distsite5" value="${bcou4543Instance?.distsite5}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="distsitedesc1"><g:message code="bcou4543.distsitedesc1.label" default="Distsitedesc1" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'distsitedesc1', 'errors')}">
                                    <g:textField name="distsitedesc1" value="${bcou4543Instance?.distsitedesc1}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="distsitedesc2"><g:message code="bcou4543.distsitedesc2.label" default="Distsitedesc2" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'distsitedesc2', 'errors')}">
                                    <g:textField name="distsitedesc2" value="${bcou4543Instance?.distsitedesc2}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="distsitedesc3"><g:message code="bcou4543.distsitedesc3.label" default="Distsitedesc3" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'distsitedesc3', 'errors')}">
                                    <g:textField name="distsitedesc3" value="${bcou4543Instance?.distsitedesc3}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="distsitedesc4"><g:message code="bcou4543.distsitedesc4.label" default="Distsitedesc4" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'distsitedesc4', 'errors')}">
                                    <g:textField name="distsitedesc4" value="${bcou4543Instance?.distsitedesc4}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="distsitedesc5"><g:message code="bcou4543.distsitedesc5.label" default="Distsitedesc5" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'distsitedesc5', 'errors')}">
                                    <g:textField name="distsitedesc5" value="${bcou4543Instance?.distsitedesc5}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="er"><g:message code="bcou4543.er.label" default="Er" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'er', 'errors')}">
                                    <g:textField name="er" value="${bcou4543Instance?.er}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="er_result"><g:message code="bcou4543.er_result.label" default="Erresult" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'er_result', 'errors')}">
                                    <g:textField name="er_result" value="${fieldValue(bean: bcou4543Instance, field: 'er_result')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="erposneg"><g:message code="bcou4543.erposneg.label" default="Erposneg" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'erposneg', 'errors')}">
                                    <g:textField name="erposneg" value="${bcou4543Instance?.erposneg}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="grade"><g:message code="bcou4543.grade.label" default="Grade" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'grade', 'errors')}">
                                    <g:textField name="grade" value="${bcou4543Instance?.grade}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="hist1"><g:message code="bcou4543.hist1.label" default="Hist1" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'hist1', 'errors')}">
                                    <g:textField name="hist1" value="${bcou4543Instance?.hist1}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="hist1_desc"><g:message code="bcou4543.hist1_desc.label" default="Hist1desc" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'hist1_desc', 'errors')}">
                                    <g:textField name="hist1_desc" value="${bcou4543Instance?.hist1_desc}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="hist2"><g:message code="bcou4543.hist2.label" default="Hist2" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'hist2', 'errors')}">
                                    <g:textField name="hist2" value="${bcou4543Instance?.hist2}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="hist2_desc"><g:message code="bcou4543.hist2_desc.label" default="Hist2desc" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'hist2_desc', 'errors')}">
                                    <g:textField name="hist2_desc" value="${bcou4543Instance?.hist2_desc}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="hist3"><g:message code="bcou4543.hist3.label" default="Hist3" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'hist3', 'errors')}">
                                    <g:textField name="hist3" value="${bcou4543Instance?.hist3}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="hist3_desc"><g:message code="bcou4543.hist3_desc.label" default="Hist3desc" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'hist3_desc', 'errors')}">
                                    <g:textField name="hist3_desc" value="${bcou4543Instance?.hist3_desc}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="immuno_stains"><g:message code="bcou4543.immuno_stains.label" default="Immunostains" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'immuno_stains', 'errors')}">
                                    <g:textField name="immuno_stains" value="${bcou4543Instance?.immuno_stains}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="locdate"><g:message code="bcou4543.locdate.label" default="Locdate" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'locdate', 'errors')}">
                                    <g:datePicker name="locdate" precision="day" value="${bcou4543Instance?.locdate}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="locind"><g:message code="bcou4543.locind.label" default="Locind" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'locind', 'errors')}">
                                    <g:textField name="locind" value="${bcou4543Instance?.locind}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="locsite"><g:message code="bcou4543.locsite.label" default="Locsite" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'locsite', 'errors')}">
                                    <g:textField name="locsite" value="${bcou4543Instance?.locsite}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="locsitedesc"><g:message code="bcou4543.locsitedesc.label" default="Locsitedesc" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'locsitedesc', 'errors')}">
                                    <g:textField name="locsitedesc" value="${bcou4543Instance?.locsitedesc}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="lvn"><g:message code="bcou4543.lvn.label" default="Lvn" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'lvn', 'errors')}">
                                    <g:textField name="lvn" value="${bcou4543Instance?.lvn}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="lvnnew"><g:message code="bcou4543.lvnnew.label" default="Lvnnew" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'lvnnew', 'errors')}">
                                    <g:textField name="lvnnew" value="${bcou4543Instance?.lvnnew}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="lymph"><g:message code="bcou4543.lymph.label" default="Lymph" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'lymph', 'errors')}">
                                    <g:textField name="lymph" value="${bcou4543Instance?.lymph}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="marg_at_init_dx"><g:message code="bcou4543.marg_at_init_dx.label" default="Margatinitdx" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'marg_at_init_dx', 'errors')}">
                                    <g:textField name="marg_at_init_dx" value="${bcou4543Instance?.marg_at_init_dx}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="meno_status"><g:message code="bcou4543.meno_status.label" default="Menostatus" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'meno_status', 'errors')}">
                                    <g:textField name="meno_status" value="${bcou4543Instance?.meno_status}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="missing_er_result"><g:message code="bcou4543.missing_er_result.label" default="Missingerresult" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'missing_er_result', 'errors')}">
                                    <g:textField name="missing_er_result" value="${bcou4543Instance?.missing_er_result}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="missing_num_neg_nod_init_dx"><g:message code="bcou4543.missing_num_neg_nod_init_dx.label" default="Missingnumnegnodinitdx" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'missing_num_neg_nod_init_dx', 'errors')}">
                                    <g:textField name="missing_num_neg_nod_init_dx" value="${bcou4543Instance?.missing_num_neg_nod_init_dx}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="missing_num_pos_nod_init_dx"><g:message code="bcou4543.missing_num_pos_nod_init_dx.label" default="Missingnumposnodinitdx" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'missing_num_pos_nod_init_dx', 'errors')}">
                                    <g:textField name="missing_num_pos_nod_init_dx" value="${bcou4543Instance?.missing_num_pos_nod_init_dx}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="num_neg_nod_init_dx"><g:message code="bcou4543.num_neg_nod_init_dx.label" default="Numnegnodinitdx" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'num_neg_nod_init_dx', 'errors')}">
                                    <g:textField name="num_neg_nod_init_dx" value="${fieldValue(bean: bcou4543Instance, field: 'num_neg_nod_init_dx')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="num_pos_nod_init_dx"><g:message code="bcou4543.num_pos_nod_init_dx.label" default="Numposnodinitdx" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'num_pos_nod_init_dx', 'errors')}">
                                    <g:textField name="num_pos_nod_init_dx" value="${fieldValue(bean: bcou4543Instance, field: 'num_pos_nod_init_dx')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="pat_status"><g:message code="bcou4543.pat_status.label" default="Patstatus" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'pat_status', 'errors')}">
                                    <g:textField name="pat_status" value="${bcou4543Instance?.pat_status}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="regdate"><g:message code="bcou4543.regdate.label" default="Regdate" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'regdate', 'errors')}">
                                    <g:datePicker name="regdate" precision="day" value="${bcou4543Instance?.regdate}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="regind"><g:message code="bcou4543.regind.label" default="Regind" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'regind', 'errors')}">
                                    <g:textField name="regind" value="${bcou4543Instance?.regind}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="registry_group"><g:message code="bcou4543.registry_group.label" default="Registrygroup" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'registry_group', 'errors')}">
                                    <g:textField name="registry_group" value="${bcou4543Instance?.registry_group}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="regsite"><g:message code="bcou4543.regsite.label" default="Regsite" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'regsite', 'errors')}">
                                    <g:textField name="regsite" value="${bcou4543Instance?.regsite}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="regsitedesc"><g:message code="bcou4543.regsitedesc.label" default="Regsitedesc" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'regsitedesc', 'errors')}">
                                    <g:textField name="regsitedesc" value="${bcou4543Instance?.regsitedesc}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="sex"><g:message code="bcou4543.sex.label" default="Sex" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'sex', 'errors')}">
                                    <g:textField name="sex" value="${bcou4543Instance?.sex}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="site"><g:message code="bcou4543.site.label" default="Site" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'site', 'errors')}">
                                    <g:textField name="site" value="${bcou4543Instance?.site}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="site_admit_date"><g:message code="bcou4543.site_admit_date.label" default="Siteadmitdate" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'site_admit_date', 'errors')}">
                                    <g:datePicker name="site_admit_date" precision="day" value="${bcou4543Instance?.site_admit_date}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="site_desc"><g:message code="bcou4543.site_desc.label" default="Sitedesc" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'site_desc', 'errors')}">
                                    <g:textField name="site_desc" value="${bcou4543Instance?.site_desc}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="size_lesion"><g:message code="bcou4543.size_lesion.label" default="Sizelesion" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'size_lesion', 'errors')}">
                                    <g:textField name="size_lesion" value="${fieldValue(bean: bcou4543Instance, field: 'size_lesion')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="status_at_referral"><g:message code="bcou4543.status_at_referral.label" default="Statusatreferral" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'status_at_referral', 'errors')}">
                                    <g:textField name="status_at_referral" value="${bcou4543Instance?.status_at_referral}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tnm_clin_m"><g:message code="bcou4543.tnm_clin_m.label" default="Tnmclinm" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'tnm_clin_m', 'errors')}">
                                    <g:textField name="tnm_clin_m" value="${bcou4543Instance?.tnm_clin_m}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tnm_clin_n"><g:message code="bcou4543.tnm_clin_n.label" default="Tnmclinn" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'tnm_clin_n', 'errors')}">
                                    <g:textField name="tnm_clin_n" value="${bcou4543Instance?.tnm_clin_n}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tnm_clin_t"><g:message code="bcou4543.tnm_clin_t.label" default="Tnmclint" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'tnm_clin_t', 'errors')}">
                                    <g:textField name="tnm_clin_t" value="${bcou4543Instance?.tnm_clin_t}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tnm_clin_yr"><g:message code="bcou4543.tnm_clin_yr.label" default="Tnmclinyr" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'tnm_clin_yr', 'errors')}">
                                    <g:textField name="tnm_clin_yr" value="${bcou4543Instance?.tnm_clin_yr}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tnm_surg_m"><g:message code="bcou4543.tnm_surg_m.label" default="Tnmsurgm" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'tnm_surg_m', 'errors')}">
                                    <g:textField name="tnm_surg_m" value="${bcou4543Instance?.tnm_surg_m}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tnm_surg_n"><g:message code="bcou4543.tnm_surg_n.label" default="Tnmsurgn" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'tnm_surg_n', 'errors')}">
                                    <g:textField name="tnm_surg_n" value="${bcou4543Instance?.tnm_surg_n}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tnm_surg_t"><g:message code="bcou4543.tnm_surg_t.label" default="Tnmsurgt" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'tnm_surg_t', 'errors')}">
                                    <g:textField name="tnm_surg_t" value="${bcou4543Instance?.tnm_surg_t}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tnm_surg_yr"><g:message code="bcou4543.tnm_surg_yr.label" default="Tnmsurgyr" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'tnm_surg_yr', 'errors')}">
                                    <g:textField name="tnm_surg_yr" value="${bcou4543Instance?.tnm_surg_yr}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="type_init_chemo"><g:message code="bcou4543.type_init_chemo.label" default="Typeinitchemo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'type_init_chemo', 'errors')}">
                                    <g:textField name="type_init_chemo" value="${bcou4543Instance?.type_init_chemo}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="type_init_horm"><g:message code="bcou4543.type_init_horm.label" default="Typeinithorm" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'type_init_horm', 'errors')}">
                                    <g:textField name="type_init_horm" value="${bcou4543Instance?.type_init_horm}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="veins"><g:message code="bcou4543.veins.label" default="Veins" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4543Instance, field: 'veins', 'errors')}">
                                    <g:textField name="veins" value="${bcou4543Instance?.veins}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
