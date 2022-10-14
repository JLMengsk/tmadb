

<%@ page import="ca.ubc.gpec.tmadb.Qpcr_experiments" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'qpcr_experiments.label', default: 'Qpcr_experiments')}" />
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
            <g:hasErrors bean="${qpcr_experimentsInstance}">
            <div class="errors">
                <g:renderErrors bean="${qpcr_experimentsInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${qpcr_experimentsInstance?.id}" />
                <g:hiddenField name="version" value="${qpcr_experimentsInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="date"><g:message code="qpcr_experiments.date.label" default="Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: qpcr_experimentsInstance, field: 'date', 'errors')}">
                                    <g:datePicker name="date" precision="day" value="${qpcr_experimentsInstance?.date}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="experiment_sample_id"><g:message code="qpcr_experiments.experiment_sample_id.label" default="Experimentsampleid" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: qpcr_experimentsInstance, field: 'experiment_sample_id', 'errors')}">
                                    <g:textField name="experiment_sample_id" value="${qpcr_experimentsInstance?.experiment_sample_id}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="institution"><g:message code="qpcr_experiments.institution.label" default="Institution" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: qpcr_experimentsInstance, field: 'institution', 'errors')}">
                                    <g:select name="institution.id" from="${ca.ubc.gpec.tmadb.Institutions.list()}" optionKey="id" value="${qpcr_experimentsInstance?.institution?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="surgical_block"><g:message code="qpcr_experiments.surgical_block.label" default="Surgicalblock" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: qpcr_experimentsInstance, field: 'surgical_block', 'errors')}">
                                    <g:select name="surgical_block.id" from="${ca.ubc.gpec.tmadb.Surgical_blocks.list()}" optionKey="id" value="${qpcr_experimentsInstance?.surgical_block?.id}"  />
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
