

<%@ page import="ca.ubc.gpec.tmadb.Ihc_score_categories" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'ihc_score_categories.label', default: 'Ihc_score_categories')}" />
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
            <g:hasErrors bean="${ihc_score_categoriesInstance}">
            <div class="errors">
                <g:renderErrors bean="${ihc_score_categoriesInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${ihc_score_categoriesInstance?.id}" />
                <g:hiddenField name="version" value="${ihc_score_categoriesInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="ihc_score_categories.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ihc_score_categoriesInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${ihc_score_categoriesInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="ihc_score_category_group"><g:message code="ihc_score_categories.ihc_score_category_group.label" default="Ihcscorecategorygroup" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ihc_score_categoriesInstance, field: 'ihc_score_category_group', 'errors')}">
                                    <g:select name="ihc_score_category_group.id" from="${ca.ubc.gpec.tmadb.Ihc_score_category_groups.list()}" optionKey="id" value="${ihc_score_categoriesInstance?.ihc_score_category_group?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="ihc_score_categories.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ihc_score_categoriesInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${ihc_score_categoriesInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="numeric_code"><g:message code="ihc_score_categories.numeric_code.label" default="Numericcode" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ihc_score_categoriesInstance, field: 'numeric_code', 'errors')}">
                                    <g:textField name="numeric_code" value="${fieldValue(bean: ihc_score_categoriesInstance, field: 'numeric_code')}" />
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
