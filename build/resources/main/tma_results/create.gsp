

<%@ page import="ca.ubc.gpec.tmadb.Tma_results" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tma_results.label', default: 'Tma_results')}" />
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
            <g:hasErrors bean="${tma_resultsInstance}">
            <div class="errors">
                <g:renderErrors bean="${tma_resultsInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="total_nuclei_count"><g:message code="tma_results.total_nuclei_count.label" default="Totalnucleicount" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_resultsInstance, field: 'total_nuclei_count', 'errors')}">
                                    <g:textField name="total_nuclei_count" value="${fieldValue(bean: tma_resultsInstance, field: 'total_nuclei_count')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="positive_nuclei_count"><g:message code="tma_results.positive_nuclei_count.label" default="Positivenucleicount" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_resultsInstance, field: 'positive_nuclei_count', 'errors')}">
                                    <g:textField name="positive_nuclei_count" value="${fieldValue(bean: tma_resultsInstance, field: 'positive_nuclei_count')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="positive_membrane_count"><g:message code="tma_results.positive_membrane_count.label" default="Positivemembranecount" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_resultsInstance, field: 'positive_membrane_count', 'errors')}">
                                    <g:textField name="positive_membrane_count" value="${fieldValue(bean: tma_resultsInstance, field: 'positive_membrane_count')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="positive_cytoplasmic_count"><g:message code="tma_results.positive_cytoplasmic_count.label" default="Positivecytoplasmiccount" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_resultsInstance, field: 'positive_cytoplasmic_count', 'errors')}">
                                    <g:textField name="positive_cytoplasmic_count" value="${fieldValue(bean: tma_resultsInstance, field: 'positive_cytoplasmic_count')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="visual_percent_positive_nuclei_estimate"><g:message code="tma_results.visual_percent_positive_nuclei_estimate.label" default="Visualpercentpositivenucleiestimate" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_resultsInstance, field: 'visual_percent_positive_nuclei_estimate', 'errors')}">
                                    <g:textField name="visual_percent_positive_nuclei_estimate" value="${fieldValue(bean: tma_resultsInstance, field: 'visual_percent_positive_nuclei_estimate')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="visual_percent_positive_cytoplasmic_estimate"><g:message code="tma_results.visual_percent_positive_cytoplasmic_estimate.label" default="Visualpercentpositivecytoplasmicestimate" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_resultsInstance, field: 'visual_percent_positive_cytoplasmic_estimate', 'errors')}">
                                    <g:textField name="visual_percent_positive_cytoplasmic_estimate" value="${fieldValue(bean: tma_resultsInstance, field: 'visual_percent_positive_cytoplasmic_estimate')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="visual_percent_positive_membrane_estimate"><g:message code="tma_results.visual_percent_positive_membrane_estimate.label" default="Visualpercentpositivemembraneestimate" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_resultsInstance, field: 'visual_percent_positive_membrane_estimate', 'errors')}">
                                    <g:textField name="visual_percent_positive_membrane_estimate" value="${fieldValue(bean: tma_resultsInstance, field: 'visual_percent_positive_membrane_estimate')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="positive_itil_count"><g:message code="tma_results.positive_itil_count.label" default="Positiveitilcount" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_resultsInstance, field: 'positive_itil_count', 'errors')}">
                                    <g:textField name="positive_itil_count" value="${fieldValue(bean: tma_resultsInstance, field: 'positive_itil_count')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="positive_stil_count"><g:message code="tma_results.positive_stil_count.label" default="Positivestilcount" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_resultsInstance, field: 'positive_stil_count', 'errors')}">
                                    <g:textField name="positive_stil_count" value="${fieldValue(bean: tma_resultsInstance, field: 'positive_stil_count')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tma_scorer1"><g:message code="tma_results.tma_scorer1.label" default="Tmascorer1" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_resultsInstance, field: 'tma_scorer1', 'errors')}">
                                    <g:select name="tma_scorer1.id" from="${ca.ubc.gpec.tmadb.Tma_scorers.list()}" optionKey="id" value="${tma_resultsInstance?.tma_scorer1?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tma_scorer2"><g:message code="tma_results.tma_scorer2.label" default="Tmascorer2" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_resultsInstance, field: 'tma_scorer2', 'errors')}">
                                    <g:select name="tma_scorer2.id" from="${ca.ubc.gpec.tmadb.Tma_scorers.list()}" optionKey="id" value="${tma_resultsInstance?.tma_scorer2?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tma_scorer3"><g:message code="tma_results.tma_scorer3.label" default="Tmascorer3" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_resultsInstance, field: 'tma_scorer3', 'errors')}">
                                    <g:select name="tma_scorer3.id" from="${ca.ubc.gpec.tmadb.Tma_scorers.list()}" optionKey="id" value="${tma_resultsInstance?.tma_scorer3?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="ihc_score_category"><g:message code="tma_results.ihc_score_category.label" default="Ihcscorecategory" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_resultsInstance, field: 'ihc_score_category', 'errors')}">
                                    <g:select name="ihc_score_category.id" from="${ca.ubc.gpec.tmadb.Ihc_score_categories.list()}" optionKey="id" value="${tma_resultsInstance?.ihc_score_category?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fish_amplification_ratio"><g:message code="tma_results.fish_amplification_ratio.label" default="Fishamplificationratio" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_resultsInstance, field: 'fish_amplification_ratio', 'errors')}">
                                    <g:textField name="fish_amplification_ratio" value="${fieldValue(bean: tma_resultsInstance, field: 'fish_amplification_ratio')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fish_average_signal"><g:message code="tma_results.fish_average_signal.label" default="Fishaveragesignal" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_resultsInstance, field: 'fish_average_signal', 'errors')}">
                                    <g:textField name="fish_average_signal" value="${fieldValue(bean: tma_resultsInstance, field: 'fish_average_signal')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="scoring_date"><g:message code="tma_results.scoring_date.label" default="Scoringdate" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_resultsInstance, field: 'scoring_date', 'errors')}">
                                    <g:datePicker name="scoring_date" precision="day" value="${tma_resultsInstance?.scoring_date}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="received_date"><g:message code="tma_results.received_date.label" default="Receiveddate" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_resultsInstance, field: 'received_date', 'errors')}">
                                    <g:datePicker name="received_date" precision="day" value="${tma_resultsInstance?.received_date}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="comment"><g:message code="tma_results.comment.label" default="Comment" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_resultsInstance, field: 'comment', 'errors')}">
                                    <g:textField name="comment" value="${tma_resultsInstance?.comment}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tma_core_image"><g:message code="tma_results.tma_core_image.label" default="Tmacoreimage" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_resultsInstance, field: 'tma_core_image', 'errors')}">
                                    <g:select name="tma_core_image.id" from="${ca.ubc.gpec.tmadb.Tma_core_images.list()}" optionKey="id" value="${tma_resultsInstance?.tma_core_image?.id}"  />
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
