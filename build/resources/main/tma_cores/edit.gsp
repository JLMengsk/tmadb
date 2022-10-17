

<%@ page import="ca.ubc.gpec.tmadb.Tma_cores" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tma_cores.label', default: 'Tma_cores')}" />
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
            <g:hasErrors bean="${tma_coresInstance}">
            <div class="errors">
                <g:renderErrors bean="${tma_coresInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${tma_coresInstance?.id}" />
                <g:hiddenField name="version" value="${tma_coresInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="row"><g:message code="tma_cores.row.label" default="Row" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_coresInstance, field: 'row', 'errors')}">
                                    <g:textField name="row" value="${fieldValue(bean: tma_coresInstance, field: 'row')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="col"><g:message code="tma_cores.col.label" default="Col" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_coresInstance, field: 'col', 'errors')}">
                                    <g:textField name="col" value="${fieldValue(bean: tma_coresInstance, field: 'col')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="core_id"><g:message code="tma_cores.core_id.label" default="Coreid" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_coresInstance, field: 'core_id', 'errors')}">
                                    <g:textField name="core_id" value="${tma_coresInstance?.core_id}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="tma_cores.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_coresInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${tma_coresInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="surgical_block"><g:message code="tma_cores.surgical_block.label" default="Surgicalblock" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_coresInstance, field: 'surgical_block', 'errors')}">
                                    <g:select name="surgical_block.id" from="${ca.ubc.gpec.tmadb.Surgical_blocks.list()}" optionKey="id" value="${tma_coresInstance?.surgical_block?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tma_block"><g:message code="tma_cores.tma_block.label" default="Tmablock" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_coresInstance, field: 'tma_block', 'errors')}">
                                    <g:select name="tma_block.id" from="${ca.ubc.gpec.tmadb.Tma_blocks.list()}" optionKey="id" value="${tma_coresInstance?.tma_block?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tma_core_images"><g:message code="tma_cores.tma_core_images.label" default="Tmacoreimages" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_coresInstance, field: 'tma_core_images', 'errors')}">
                                    
<ul>
<g:each in="${tma_coresInstance?.tma_core_images?}" var="t">
    <li><g:link controller="tma_core_images" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="tma_core_images" action="create" params="['tma_cores.id': tma_coresInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'tma_core_images.label', default: 'Tma_core_images')])}</g:link>

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
