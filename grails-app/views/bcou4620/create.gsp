

<%@ page import="ca.ubc.gpec.tmadb.Bcou4620" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'bcou4620.label', default: 'Bcou4620')}" />
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
            <g:hasErrors bean="${bcou4620Instance}">
            <div class="errors">
                <g:renderErrors bean="${bcou4620Instance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="studynum"><g:message code="bcou4620.studynum.label" default="Studynum" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'studynum', 'errors')}">
                                    <g:textField name="studynum" value="${fieldValue(bean: bcou4620Instance, field: 'studynum')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="gpec_id"><g:message code="bcou4620.gpec_id.label" default="Gpecid" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'gpec_id', 'errors')}">
                                    <g:textField name="gpec_id" value="${fieldValue(bean: bcou4620Instance, field: 'gpec_id')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="ad"><g:message code="bcou4620.ad.label" default="Ad" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'ad', 'errors')}">
                                    <g:textField name="ad" value="${bcou4620Instance?.ad}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="age_at_diagnosis"><g:message code="bcou4620.age_at_diagnosis.label" default="Ageatdiagnosis" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'age_at_diagnosis', 'errors')}">
                                    <g:textField name="age_at_diagnosis" value="${fieldValue(bean: bcou4620Instance, field: 'age_at_diagnosis')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="anyenddt"><g:message code="bcou4620.anyenddt.label" default="Anyenddt" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'anyenddt', 'errors')}">
                                    <g:datePicker name="anyenddt" precision="day" value="${bcou4620Instance?.anyenddt}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="anyevndt"><g:message code="bcou4620.anyevndt.label" default="Anyevndt" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'anyevndt', 'errors')}">
                                    <g:datePicker name="anyevndt" precision="day" value="${bcou4620Instance?.anyevndt}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="anyreldt"><g:message code="bcou4620.anyreldt.label" default="Anyreldt" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'anyreldt', 'errors')}">
                                    <g:datePicker name="anyreldt" precision="day" value="${bcou4620Instance?.anyreldt}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="anystat"><g:message code="bcou4620.anystat.label" default="Anystat" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'anystat', 'errors')}">
                                    <g:textField name="anystat" value="${bcou4620Instance?.anystat}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="anysurv"><g:message code="bcou4620.anysurv.label" default="Anysurv" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'anysurv', 'errors')}">
                                    <g:textField name="anysurv" value="${fieldValue(bean: bcou4620Instance, field: 'anysurv')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="bcca_cod_desc"><g:message code="bcou4620.bcca_cod_desc.label" default="Bccacoddesc" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'bcca_cod_desc', 'errors')}">
                                    <g:textField name="bcca_cod_desc" value="${bcou4620Instance?.bcca_cod_desc}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="bccacod"><g:message code="bcou4620.bccacod.label" default="Bccacod" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'bccacod', 'errors')}">
                                    <g:textField name="bccacod" value="${bcou4620Instance?.bccacod}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="behavior"><g:message code="bcou4620.behavior.label" default="Behavior" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'behavior', 'errors')}">
                                    <g:textField name="behavior" value="${bcou4620Instance?.behavior}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="boost"><g:message code="bcou4620.boost.label" default="Boost" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'boost', 'errors')}">
                                    <g:textField name="boost" value="${bcou4620Instance?.boost}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="brchwrt"><g:message code="bcou4620.brchwrt.label" default="Brchwrt" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'brchwrt', 'errors')}">
                                    <g:textField name="brchwrt" value="${bcou4620Instance?.brchwrt}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="brdeath"><g:message code="bcou4620.brdeath.label" default="Brdeath" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'brdeath', 'errors')}">
                                    <g:textField name="brdeath" value="${bcou4620Instance?.brdeath}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="brdthdat"><g:message code="bcou4620.brdthdat.label" default="Brdthdat" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'brdthdat', 'errors')}">
                                    <g:datePicker name="brdthdat" precision="day" value="${bcou4620Instance?.brdthdat}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="chemflag"><g:message code="bcou4620.chemflag.label" default="Chemflag" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'chemflag', 'errors')}">
                                    <g:textField name="chemflag" value="${bcou4620Instance?.chemflag}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="chemtype"><g:message code="bcou4620.chemtype.label" default="Chemtype" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'chemtype', 'errors')}">
                                    <g:textField name="chemtype" value="${bcou4620Instance?.chemtype}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="clinical_info"><g:message code="bcou4620.clinical_info.label" default="Clinicalinfo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'clinical_info', 'errors')}">
                                    <g:select name="clinical_info.id" from="${ca.ubc.gpec.tmadb.Clinical_infos.list()}" optionKey="id" value="${bcou4620Instance?.clinical_info?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="complete"><g:message code="bcou4620.complete.label" default="Complete" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'complete', 'errors')}">
                                    <g:textField name="complete" value="${bcou4620Instance?.complete}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="death_cause_desc"><g:message code="bcou4620.death_cause_desc.label" default="Deathcausedesc" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'death_cause_desc', 'errors')}">
                                    <g:textField name="death_cause_desc" value="${bcou4620Instance?.death_cause_desc}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="death_da"><g:message code="bcou4620.death_da.label" default="Deathda" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'death_da', 'errors')}">
                                    <g:datePicker name="death_da" precision="day" value="${bcou4620Instance?.death_da}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="death_sec_cause_desc"><g:message code="bcou4620.death_sec_cause_desc.label" default="Deathseccausedesc" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'death_sec_cause_desc', 'errors')}">
                                    <g:textField name="death_sec_cause_desc" value="${bcou4620Instance?.death_sec_cause_desc}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="diagnosi"><g:message code="bcou4620.diagnosi.label" default="Diagnosi" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'diagnosi', 'errors')}">
                                    <g:datePicker name="diagnosi" precision="day" value="${bcou4620Instance?.diagnosi}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="disenddt"><g:message code="bcou4620.disenddt.label" default="Disenddt" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'disenddt', 'errors')}">
                                    <g:datePicker name="disenddt" precision="day" value="${bcou4620Instance?.disenddt}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="distdate"><g:message code="bcou4620.distdate.label" default="Distdate" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'distdate', 'errors')}">
                                    <g:datePicker name="distdate" precision="day" value="${bcou4620Instance?.distdate}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="distind"><g:message code="bcou4620.distind.label" default="Distind" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'distind', 'errors')}">
                                    <g:textField name="distind" value="${bcou4620Instance?.distind}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="distnarr"><g:message code="bcou4620.distnarr.label" default="Distnarr" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'distnarr', 'errors')}">
                                    <g:textField name="distnarr" value="${bcou4620Instance?.distnarr}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="distsite"><g:message code="bcou4620.distsite.label" default="Distsite" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'distsite', 'errors')}">
                                    <g:textField name="distsite" value="${bcou4620Instance?.distsite}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="diststat"><g:message code="bcou4620.diststat.label" default="Diststat" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'diststat', 'errors')}">
                                    <g:textField name="diststat" value="${bcou4620Instance?.diststat}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="distsurv"><g:message code="bcou4620.distsurv.label" default="Distsurv" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'distsurv', 'errors')}">
                                    <g:textField name="distsurv" value="${fieldValue(bean: bcou4620Instance, field: 'distsurv')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dvsprim"><g:message code="bcou4620.dvsprim.label" default="Dvsprim" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'dvsprim', 'errors')}">
                                    <g:textField name="dvsprim" value="${bcou4620Instance?.dvsprim}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dvssec"><g:message code="bcou4620.dvssec.label" default="Dvssec" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'dvssec', 'errors')}">
                                    <g:textField name="dvssec" value="${bcou4620Instance?.dvssec}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="er"><g:message code="bcou4620.er.label" default="Er" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'er', 'errors')}">
                                    <g:textField name="er" value="${bcou4620Instance?.er}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="er1"><g:message code="bcou4620.er1.label" default="Er1" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'er1', 'errors')}">
                                    <g:textField name="er1" value="${fieldValue(bean: bcou4620Instance, field: 'er1')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="er2"><g:message code="bcou4620.er2.label" default="Er2" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'er2', 'errors')}">
                                    <g:textField name="er2" value="${fieldValue(bean: bcou4620Instance, field: 'er2')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="er3"><g:message code="bcou4620.er3.label" default="Er3" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'er3', 'errors')}">
                                    <g:textField name="er3" value="${fieldValue(bean: bcou4620Instance, field: 'er3')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="erposneg"><g:message code="bcou4620.erposneg.label" default="Erposneg" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'erposneg', 'errors')}">
                                    <g:textField name="erposneg" value="${bcou4620Instance?.erposneg}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="erresult"><g:message code="bcou4620.erresult.label" default="Erresult" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'erresult', 'errors')}">
                                    <g:textField name="erresult" value="${fieldValue(bean: bcou4620Instance, field: 'erresult')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="evnenddt"><g:message code="bcou4620.evnenddt.label" default="Evnenddt" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'evnenddt', 'errors')}">
                                    <g:datePicker name="evnenddt" precision="day" value="${bcou4620Instance?.evnenddt}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="evntstat"><g:message code="bcou4620.evntstat.label" default="Evntstat" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'evntstat', 'errors')}">
                                    <g:textField name="evntstat" value="${bcou4620Instance?.evntstat}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="evntsurv"><g:message code="bcou4620.evntsurv.label" default="Evntsurv" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'evntsurv', 'errors')}">
                                    <g:textField name="evntsurv" value="${fieldValue(bean: bcou4620Instance, field: 'evntsurv')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="finrt"><g:message code="bcou4620.finrt.label" default="Finrt" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'finrt', 'errors')}">
                                    <g:textField name="finrt" value="${bcou4620Instance?.finrt}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="finsurg"><g:message code="bcou4620.finsurg.label" default="Finsurg" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'finsurg', 'errors')}">
                                    <g:textField name="finsurg" value="${bcou4620Instance?.finsurg}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="grade"><g:message code="bcou4620.grade.label" default="Grade" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'grade', 'errors')}">
                                    <g:textField name="grade" value="${bcou4620Instance?.grade}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="hist1"><g:message code="bcou4620.hist1.label" default="Hist1" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'hist1', 'errors')}">
                                    <g:textField name="hist1" value="${bcou4620Instance?.hist1}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="hist1_desc"><g:message code="bcou4620.hist1_desc.label" default="Hist1desc" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'hist1_desc', 'errors')}">
                                    <g:textField name="hist1_desc" value="${bcou4620Instance?.hist1_desc}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="hist2"><g:message code="bcou4620.hist2.label" default="Hist2" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'hist2', 'errors')}">
                                    <g:textField name="hist2" value="${bcou4620Instance?.hist2}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="hist2_desc"><g:message code="bcou4620.hist2_desc.label" default="Hist2desc" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'hist2_desc', 'errors')}">
                                    <g:textField name="hist2_desc" value="${bcou4620Instance?.hist2_desc}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="hist3"><g:message code="bcou4620.hist3.label" default="Hist3" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'hist3', 'errors')}">
                                    <g:textField name="hist3" value="${bcou4620Instance?.hist3}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="hist3_desc"><g:message code="bcou4620.hist3_desc.label" default="Hist3desc" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'hist3_desc', 'errors')}">
                                    <g:textField name="hist3_desc" value="${bcou4620Instance?.hist3_desc}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="histcat"><g:message code="bcou4620.histcat.label" default="Histcat" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'histcat', 'errors')}">
                                    <g:textField name="histcat" value="${bcou4620Instance?.histcat}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="hormflag"><g:message code="bcou4620.hormflag.label" default="Hormflag" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'hormflag', 'errors')}">
                                    <g:textField name="hormflag" value="${bcou4620Instance?.hormflag}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="hormtype"><g:message code="bcou4620.hormtype.label" default="Hormtype" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'hormtype', 'errors')}">
                                    <g:textField name="hormtype" value="${bcou4620Instance?.hormtype}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="hx_bilatca_fst_deg_rel"><g:message code="bcou4620.hx_bilatca_fst_deg_rel.label" default="Hxbilatcafstdegrel" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'hx_bilatca_fst_deg_rel', 'errors')}">
                                    <g:textField name="hx_bilatca_fst_deg_rel" value="${bcou4620Instance?.hx_bilatca_fst_deg_rel}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="immuno"><g:message code="bcou4620.immuno.label" default="Immuno" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'immuno', 'errors')}">
                                    <g:textField name="immuno" value="${bcou4620Instance?.immuno}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="laterality"><g:message code="bcou4620.laterality.label" default="Laterality" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'laterality', 'errors')}">
                                    <g:textField name="laterality" value="${bcou4620Instance?.laterality}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="localtx"><g:message code="bcou4620.localtx.label" default="Localtx" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'localtx', 'errors')}">
                                    <g:textField name="localtx" value="${bcou4620Instance?.localtx}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="locdate"><g:message code="bcou4620.locdate.label" default="Locdate" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'locdate', 'errors')}">
                                    <g:datePicker name="locdate" precision="day" value="${bcou4620Instance?.locdate}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="locenddt"><g:message code="bcou4620.locenddt.label" default="Locenddt" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'locenddt', 'errors')}">
                                    <g:datePicker name="locenddt" precision="day" value="${bcou4620Instance?.locenddt}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="locind"><g:message code="bcou4620.locind.label" default="Locind" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'locind', 'errors')}">
                                    <g:textField name="locind" value="${bcou4620Instance?.locind}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="locnarr"><g:message code="bcou4620.locnarr.label" default="Locnarr" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'locnarr', 'errors')}">
                                    <g:textField name="locnarr" value="${bcou4620Instance?.locnarr}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="locregdt"><g:message code="bcou4620.locregdt.label" default="Locregdt" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'locregdt', 'errors')}">
                                    <g:datePicker name="locregdt" precision="day" value="${bcou4620Instance?.locregdt}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="locsite"><g:message code="bcou4620.locsite.label" default="Locsite" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'locsite', 'errors')}">
                                    <g:textField name="locsite" value="${bcou4620Instance?.locsite}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="locstat"><g:message code="bcou4620.locstat.label" default="Locstat" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'locstat', 'errors')}">
                                    <g:textField name="locstat" value="${bcou4620Instance?.locstat}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="locsurv"><g:message code="bcou4620.locsurv.label" default="Locsurv" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'locsurv', 'errors')}">
                                    <g:textField name="locsurv" value="${fieldValue(bean: bcou4620Instance, field: 'locsurv')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="lrgenddt"><g:message code="bcou4620.lrgenddt.label" default="Lrgenddt" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'lrgenddt', 'errors')}">
                                    <g:datePicker name="lrgenddt" precision="day" value="${bcou4620Instance?.lrgenddt}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="lrgstat"><g:message code="bcou4620.lrgstat.label" default="Lrgstat" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'lrgstat', 'errors')}">
                                    <g:textField name="lrgstat" value="${bcou4620Instance?.lrgstat}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="lrgsurv"><g:message code="bcou4620.lrgsurv.label" default="Lrgsurv" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'lrgsurv', 'errors')}">
                                    <g:textField name="lrgsurv" value="${fieldValue(bean: bcou4620Instance, field: 'lrgsurv')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="lvnnew"><g:message code="bcou4620.lvnnew.label" default="Lvnnew" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'lvnnew', 'errors')}">
                                    <g:textField name="lvnnew" value="${bcou4620Instance?.lvnnew}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="m1"><g:message code="bcou4620.m1.label" default="M1" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'm1', 'errors')}">
                                    <g:textField name="m1" value="${bcou4620Instance?.m1}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="marg_at_init_dx"><g:message code="bcou4620.marg_at_init_dx.label" default="Margatinitdx" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'marg_at_init_dx', 'errors')}">
                                    <g:textField name="marg_at_init_dx" value="${bcou4620Instance?.marg_at_init_dx}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="meno_status"><g:message code="bcou4620.meno_status.label" default="Menostatus" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'meno_status', 'errors')}">
                                    <g:textField name="meno_status" value="${bcou4620Instance?.meno_status}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="missing_erresult"><g:message code="bcou4620.missing_erresult.label" default="Missingerresult" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'missing_erresult', 'errors')}">
                                    <g:textField name="missing_erresult" value="${bcou4620Instance?.missing_erresult}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="missing_negnodes"><g:message code="bcou4620.missing_negnodes.label" default="Missingnegnodes" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'missing_negnodes', 'errors')}">
                                    <g:textField name="missing_negnodes" value="${bcou4620Instance?.missing_negnodes}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="missing_posnodes"><g:message code="bcou4620.missing_posnodes.label" default="Missingposnodes" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'missing_posnodes', 'errors')}">
                                    <g:textField name="missing_posnodes" value="${bcou4620Instance?.missing_posnodes}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="negnodes"><g:message code="bcou4620.negnodes.label" default="Negnodes" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'negnodes', 'errors')}">
                                    <g:textField name="negnodes" value="${fieldValue(bean: bcou4620Instance, field: 'negnodes')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="nodalrt"><g:message code="bcou4620.nodalrt.label" default="Nodalrt" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'nodalrt', 'errors')}">
                                    <g:textField name="nodalrt" value="${bcou4620Instance?.nodalrt}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="nodestat"><g:message code="bcou4620.nodestat.label" default="Nodestat" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'nodestat', 'errors')}">
                                    <g:textField name="nodestat" value="${bcou4620Instance?.nodestat}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="partial"><g:message code="bcou4620.partial.label" default="Partial" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'partial', 'errors')}">
                                    <g:textField name="partial" value="${bcou4620Instance?.partial}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="pat_stat"><g:message code="bcou4620.pat_stat.label" default="Patstat" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'pat_stat', 'errors')}">
                                    <g:textField name="pat_stat" value="${bcou4620Instance?.pat_stat}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="posnodes"><g:message code="bcou4620.posnodes.label" default="Posnodes" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'posnodes', 'errors')}">
                                    <g:textField name="posnodes" value="${fieldValue(bean: bcou4620Instance, field: 'posnodes')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="regdate"><g:message code="bcou4620.regdate.label" default="Regdate" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'regdate', 'errors')}">
                                    <g:datePicker name="regdate" precision="day" value="${bcou4620Instance?.regdate}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="regenddt"><g:message code="bcou4620.regenddt.label" default="Regenddt" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'regenddt', 'errors')}">
                                    <g:datePicker name="regenddt" precision="day" value="${bcou4620Instance?.regenddt}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="regind"><g:message code="bcou4620.regind.label" default="Regind" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'regind', 'errors')}">
                                    <g:textField name="regind" value="${bcou4620Instance?.regind}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="registry_group"><g:message code="bcou4620.registry_group.label" default="Registrygroup" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'registry_group', 'errors')}">
                                    <g:textField name="registry_group" value="${bcou4620Instance?.registry_group}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="regnarr"><g:message code="bcou4620.regnarr.label" default="Regnarr" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'regnarr', 'errors')}">
                                    <g:textField name="regnarr" value="${bcou4620Instance?.regnarr}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="regsite"><g:message code="bcou4620.regsite.label" default="Regsite" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'regsite', 'errors')}">
                                    <g:textField name="regsite" value="${bcou4620Instance?.regsite}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="regstat"><g:message code="bcou4620.regstat.label" default="Regstat" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'regstat', 'errors')}">
                                    <g:textField name="regstat" value="${bcou4620Instance?.regstat}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="regsurv"><g:message code="bcou4620.regsurv.label" default="Regsurv" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'regsurv', 'errors')}">
                                    <g:textField name="regsurv" value="${fieldValue(bean: bcou4620Instance, field: 'regsurv')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="rtintent"><g:message code="bcou4620.rtintent.label" default="Rtintent" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'rtintent', 'errors')}">
                                    <g:textField name="rtintent" value="${bcou4620Instance?.rtintent}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="sex"><g:message code="bcou4620.sex.label" default="Sex" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'sex', 'errors')}">
                                    <g:textField name="sex" value="${bcou4620Instance?.sex}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="site"><g:message code="bcou4620.site.label" default="Site" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'site', 'errors')}">
                                    <g:textField name="site" value="${bcou4620Instance?.site}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="site_admit_date"><g:message code="bcou4620.site_admit_date.label" default="Siteadmitdate" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'site_admit_date', 'errors')}">
                                    <g:datePicker name="site_admit_date" precision="day" value="${bcou4620Instance?.site_admit_date}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="site_desc"><g:message code="bcou4620.site_desc.label" default="Sitedesc" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'site_desc', 'errors')}">
                                    <g:textField name="site_desc" value="${bcou4620Instance?.site_desc}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="size_lesion"><g:message code="bcou4620.size_lesion.label" default="Sizelesion" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'size_lesion', 'errors')}">
                                    <g:textField name="size_lesion" value="${fieldValue(bean: bcou4620Instance, field: 'size_lesion')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="statjn04"><g:message code="bcou4620.statjn04.label" default="Statjn04" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'statjn04', 'errors')}">
                                    <g:textField name="statjn04" value="${bcou4620Instance?.statjn04}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="status_at_referral"><g:message code="bcou4620.status_at_referral.label" default="Statusatreferral" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'status_at_referral', 'errors')}">
                                    <g:textField name="status_at_referral" value="${bcou4620Instance?.status_at_referral}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="subbrca"><g:message code="bcou4620.subbrca.label" default="Subbrca" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'subbrca', 'errors')}">
                                    <g:textField name="subbrca" value="${bcou4620Instance?.subbrca}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="subbrdat"><g:message code="bcou4620.subbrdat.label" default="Subbrdat" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'subbrdat', 'errors')}">
                                    <g:datePicker name="subbrdat" precision="day" value="${bcou4620Instance?.subbrdat}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="subgroup"><g:message code="bcou4620.subgroup.label" default="Subgroup" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'subgroup', 'errors')}">
                                    <g:textField name="subgroup" value="${bcou4620Instance?.subgroup}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="survyrs"><g:message code="bcou4620.survyrs.label" default="Survyrs" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'survyrs', 'errors')}">
                                    <g:textField name="survyrs" value="${fieldValue(bean: bcou4620Instance, field: 'survyrs')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="sys"><g:message code="bcou4620.sys.label" default="Sys" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'sys', 'errors')}">
                                    <g:textField name="sys" value="${bcou4620Instance?.sys}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="systemic"><g:message code="bcou4620.systemic.label" default="Systemic" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'systemic', 'errors')}">
                                    <g:textField name="systemic" value="${bcou4620Instance?.systemic}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="testvsvalid"><g:message code="bcou4620.testvsvalid.label" default="Testvsvalid" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'testvsvalid', 'errors')}">
                                    <g:textField name="testvsvalid" value="${bcou4620Instance?.testvsvalid}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tnm_clin_m"><g:message code="bcou4620.tnm_clin_m.label" default="Tnmclinm" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'tnm_clin_m', 'errors')}">
                                    <g:textField name="tnm_clin_m" value="${bcou4620Instance?.tnm_clin_m}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tnm_clin_n"><g:message code="bcou4620.tnm_clin_n.label" default="Tnmclinn" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'tnm_clin_n', 'errors')}">
                                    <g:textField name="tnm_clin_n" value="${bcou4620Instance?.tnm_clin_n}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tnm_clin_t"><g:message code="bcou4620.tnm_clin_t.label" default="Tnmclint" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'tnm_clin_t', 'errors')}">
                                    <g:textField name="tnm_clin_t" value="${bcou4620Instance?.tnm_clin_t}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tnm_clin_yr"><g:message code="bcou4620.tnm_clin_yr.label" default="Tnmclinyr" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'tnm_clin_yr', 'errors')}">
                                    <g:textField name="tnm_clin_yr" value="${bcou4620Instance?.tnm_clin_yr}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tnm_surg_m"><g:message code="bcou4620.tnm_surg_m.label" default="Tnmsurgm" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'tnm_surg_m', 'errors')}">
                                    <g:textField name="tnm_surg_m" value="${bcou4620Instance?.tnm_surg_m}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tnm_surg_n"><g:message code="bcou4620.tnm_surg_n.label" default="Tnmsurgn" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'tnm_surg_n', 'errors')}">
                                    <g:textField name="tnm_surg_n" value="${bcou4620Instance?.tnm_surg_n}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tnm_surg_t"><g:message code="bcou4620.tnm_surg_t.label" default="Tnmsurgt" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'tnm_surg_t', 'errors')}">
                                    <g:textField name="tnm_surg_t" value="${bcou4620Instance?.tnm_surg_t}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tnm_surg_yr"><g:message code="bcou4620.tnm_surg_yr.label" default="Tnmsurgyr" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'tnm_surg_yr', 'errors')}">
                                    <g:textField name="tnm_surg_yr" value="${bcou4620Instance?.tnm_surg_yr}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="totnodes"><g:message code="bcou4620.totnodes.label" default="Totnodes" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'totnodes', 'errors')}">
                                    <g:textField name="totnodes" value="${fieldValue(bean: bcou4620Instance, field: 'totnodes')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tumloc"><g:message code="bcou4620.tumloc.label" default="Tumloc" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bcou4620Instance, field: 'tumloc', 'errors')}">
                                    <g:textField name="tumloc" value="${bcou4620Instance?.tumloc}" />
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
