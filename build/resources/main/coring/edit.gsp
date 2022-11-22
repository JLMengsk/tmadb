

<%@ page import="ca.ubc.gpec.tmadb.Coring" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'coring.label', default: 'Coring')}" />
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
            <g:hasErrors bean="${coringInstance}">
            <div class="errors">
                <g:renderErrors bean="${coringInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${coringInstance?.id}" />
                <g:hiddenField name="version" value="${coringInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="checked_date"><g:message code="coring.checked_date.label" default="Checkeddate" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: coringInstance, field: 'checked_date', 'errors')}">
                                    <g:datePicker name="checked_date" precision="day" value="${coringInstance?.checked_date}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="comment"><g:message code="coring.comment.label" default="Comment" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: coringInstance, field: 'comment', 'errors')}">
                                    <g:textField name="comment" value="${coringInstance?.comment}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="cored_date"><g:message code="coring.cored_date.label" default="Coreddate" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: coringInstance, field: 'cored_date', 'errors')}">
                                    <g:datePicker name="cored_date" precision="day" value="${coringInstance?.cored_date}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="coring_project"><g:message code="coring.coring_project.label" default="Coringproject" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: coringInstance, field: 'coring_project', 'errors')}">
                                    <g:select name="coring_project.id" from="${ca.ubc.gpec.tmadb.Coring_projects.list()}" optionKey="id" value="${coringInstance?.coring_project?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="coring_type"><g:message code="coring.coring_type.label" default="Coringtype" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: coringInstance, field: 'coring_type', 'errors')}">
                                    <g:select name="coring_type.id" from="${ca.ubc.gpec.tmadb.Coring_types.list()}" optionKey="id" value="${coringInstance?.coring_type?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="rna_extractions"><g:message code="coring.rna_extractions.label" default="Rnaextractions" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: coringInstance, field: 'rna_extractions', 'errors')}">
                                    
<ul>
<g:each in="${coringInstance?.rna_extractions?}" var="r">
    <li><g:link controller="rna_extractions" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="rna_extractions" action="create" params="['coring.id': coringInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'rna_extractions.label', default: 'Rna_extractions')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="surgical_block"><g:message code="coring.surgical_block.label" default="Surgicalblock" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: coringInstance, field: 'surgical_block', 'errors')}">
                                    <g:select name="surgical_block.id" from="${ca.ubc.gpec.tmadb.Surgical_blocks.list()}" optionKey="id" value="${coringInstance?.surgical_block?.id}"  />
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
