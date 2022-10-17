

<%@ page import="ca.ubc.gpec.tmadb.Rna_extractions" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'rna_extractions.label', default: 'Rna_extractions')}" />
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
            <g:hasErrors bean="${rna_extractionsInstance}">
            <div class="errors">
                <g:renderErrors bean="${rna_extractionsInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${rna_extractionsInstance?.id}" />
                <g:hiddenField name="version" value="${rna_extractionsInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="comment"><g:message code="rna_extractions.comment.label" default="Comment" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: rna_extractionsInstance, field: 'comment', 'errors')}">
                                    <g:textField name="comment" value="${rna_extractionsInstance?.comment}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="coring"><g:message code="rna_extractions.coring.label" default="Coring" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: rna_extractionsInstance, field: 'coring', 'errors')}">
                                    <g:select name="coring.id" from="${ca.ubc.gpec.tmadb.Coring.list()}" optionKey="id" value="${rna_extractionsInstance?.coring?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="record_date"><g:message code="rna_extractions.record_date.label" default="Recorddate" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: rna_extractionsInstance, field: 'record_date', 'errors')}">
                                    <g:datePicker name="record_date" precision="day" value="${rna_extractionsInstance?.record_date}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="rna_yields"><g:message code="rna_extractions.rna_yields.label" default="Rnayields" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: rna_extractionsInstance, field: 'rna_yields', 'errors')}">
                                    
<ul>
<g:each in="${rna_extractionsInstance?.rna_yields?}" var="r">
    <li><g:link controller="rna_yields" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="rna_yields" action="create" params="['rna_extractions.id': rna_extractionsInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'rna_yields.label', default: 'Rna_yields')])}</g:link>

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
