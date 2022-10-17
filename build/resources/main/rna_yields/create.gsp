

<%@ page import="ca.ubc.gpec.tmadb.Rna_yields" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'rna_yields.label', default: 'Rna_yields')}" />
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
            <g:hasErrors bean="${rna_yieldsInstance}">
            <div class="errors">
                <g:renderErrors bean="${rna_yieldsInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="concentration_ug_per_ul"><g:message code="rna_yields.concentration_ug_per_ul.label" default="Concentrationugperul" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: rna_yieldsInstance, field: 'concentration_ug_per_ul', 'errors')}">
                                    <g:textField name="concentration_ug_per_ul" value="${fieldValue(bean: rna_yieldsInstance, field: 'concentration_ug_per_ul')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="yield_ug"><g:message code="rna_yields.yield_ug.label" default="Yieldug" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: rna_yieldsInstance, field: 'yield_ug', 'errors')}">
                                    <g:textField name="yield_ug" value="${fieldValue(bean: rna_yieldsInstance, field: 'yield_ug')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="comment"><g:message code="rna_yields.comment.label" default="Comment" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: rna_yieldsInstance, field: 'comment', 'errors')}">
                                    <g:textField name="comment" value="${rna_yieldsInstance?.comment}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="record_date"><g:message code="rna_yields.record_date.label" default="Recorddate" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: rna_yieldsInstance, field: 'record_date', 'errors')}">
                                    <g:datePicker name="record_date" precision="day" value="${rna_yieldsInstance?.record_date}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="rna_extraction"><g:message code="rna_yields.rna_extraction.label" default="Rnaextraction" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: rna_yieldsInstance, field: 'rna_extraction', 'errors')}">
                                    <g:select name="rna_extraction.id" from="${ca.ubc.gpec.tmadb.Rna_extractions.list()}" optionKey="id" value="${rna_yieldsInstance?.rna_extraction?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="source_description"><g:message code="rna_yields.source_description.label" default="Sourcedescription" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: rna_yieldsInstance, field: 'source_description', 'errors')}">
                                    <g:textField name="source_description" value="${rna_yieldsInstance?.source_description}" />
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
