

<%@ page import="ca.ubc.gpec.tmadb.Qpcr_results" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'qpcr_results.label', default: 'Qpcr_results')}" />
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
            <g:hasErrors bean="${qpcr_resultsInstance}">
            <div class="errors">
                <g:renderErrors bean="${qpcr_resultsInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="biomarker"><g:message code="qpcr_results.biomarker.label" default="Biomarker" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: qpcr_resultsInstance, field: 'biomarker', 'errors')}">
                                    <g:select name="biomarker.id" from="${ca.ubc.gpec.tmadb.Biomarkers.list()}" optionKey="id" value="${qpcr_resultsInstance?.biomarker?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="comment"><g:message code="qpcr_results.comment.label" default="Comment" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: qpcr_resultsInstance, field: 'comment', 'errors')}">
                                    <g:textField name="comment" value="${qpcr_resultsInstance?.comment}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="copy_number"><g:message code="qpcr_results.copy_number.label" default="Copynumber" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: qpcr_resultsInstance, field: 'copy_number', 'errors')}">
                                    <g:textField name="copy_number" value="${fieldValue(bean: qpcr_resultsInstance, field: 'copy_number')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="crossing_point"><g:message code="qpcr_results.crossing_point.label" default="Crossingpoint" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: qpcr_resultsInstance, field: 'crossing_point', 'errors')}">
                                    <g:textField name="crossing_point" value="${fieldValue(bean: qpcr_resultsInstance, field: 'crossing_point')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="date_received"><g:message code="qpcr_results.date_received.label" default="Datereceived" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: qpcr_resultsInstance, field: 'date_received', 'errors')}">
                                    <g:datePicker name="date_received" precision="day" value="${qpcr_resultsInstance?.date_received}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="normalized_expression"><g:message code="qpcr_results.normalized_expression.label" default="Normalizedexpression" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: qpcr_resultsInstance, field: 'normalized_expression', 'errors')}">
                                    <g:textField name="normalized_expression" value="${fieldValue(bean: qpcr_resultsInstance, field: 'normalized_expression')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="precalculated_numeric"><g:message code="qpcr_results.precalculated_numeric.label" default="Precalculatednumeric" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: qpcr_resultsInstance, field: 'precalculated_numeric', 'errors')}">
                                    <g:textField name="precalculated_numeric" value="${fieldValue(bean: qpcr_resultsInstance, field: 'precalculated_numeric')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="precalculated_text"><g:message code="qpcr_results.precalculated_text.label" default="Precalculatedtext" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: qpcr_resultsInstance, field: 'precalculated_text', 'errors')}">
                                    <g:textField name="precalculated_text" value="${qpcr_resultsInstance?.precalculated_text}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="qpcr_experiment"><g:message code="qpcr_results.qpcr_experiment.label" default="Qpcrexperiment" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: qpcr_resultsInstance, field: 'qpcr_experiment', 'errors')}">
                                    <g:select name="qpcr_experiment.id" from="${ca.ubc.gpec.tmadb.Qpcr_experiments.list()}" optionKey="id" value="${qpcr_resultsInstance?.qpcr_experiment?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="status"><g:message code="qpcr_results.status.label" default="Status" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: qpcr_resultsInstance, field: 'status', 'errors')}">
                                    <g:textField name="status" value="${qpcr_resultsInstance?.status}" />
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
