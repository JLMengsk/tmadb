

<%@ page import="ca.ubc.gpec.tmadb.Ihc_score_category_groups" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'ihc_score_category_groups.label', default: 'Ihc_score_category_groups')}" />
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
            <g:hasErrors bean="${ihc_score_category_groupsInstance}">
            <div class="errors">
                <g:renderErrors bean="${ihc_score_category_groupsInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${ihc_score_category_groupsInstance?.id}" />
                <g:hiddenField name="version" value="${ihc_score_category_groupsInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="ihc_score_category_groups.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ihc_score_category_groupsInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${ihc_score_category_groupsInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="ihc_score_category_groups.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ihc_score_category_groupsInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${ihc_score_category_groupsInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="ihc_score_categories"><g:message code="ihc_score_category_groups.ihc_score_categories.label" default="Ihcscorecategories" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ihc_score_category_groupsInstance, field: 'ihc_score_categories', 'errors')}">
                                    
<ul>
<g:each in="${ihc_score_category_groupsInstance?.ihc_score_categories?}" var="i">
    <li><g:link controller="ihc_score_categories" action="show" id="${i.id}">${i?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="ihc_score_categories" action="create" params="['ihc_score_category_groups.id': ihc_score_category_groupsInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'ihc_score_categories.label', default: 'Ihc_score_categories')])}</g:link>

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
