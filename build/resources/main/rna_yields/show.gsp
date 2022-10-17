
<%@ page import="ca.ubc.gpec.tmadb.Rna_yields" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'rna_yields.label', default: 'Rna_yields')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="rna_yields.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: rna_yieldsInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="rna_yields.concentration_ug_per_ul.label" default="Concentrationugperul" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: rna_yieldsInstance, field: "concentration_ug_per_ul")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="rna_yields.yield_ug.label" default="Yieldug" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: rna_yieldsInstance, field: "yield_ug")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="rna_yields.comment.label" default="Comment" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: rna_yieldsInstance, field: "comment")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="rna_yields.record_date.label" default="Recorddate" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${rna_yieldsInstance?.record_date}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="rna_yields.rna_extraction.label" default="Rnaextraction" /></td>
                            
                            <td valign="top" class="value"><g:link controller="rna_extractions" action="show" id="${rna_yieldsInstance?.rna_extraction?.id}">${rna_yieldsInstance?.rna_extraction?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="rna_yields.source_description.label" default="Sourcedescription" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: rna_yieldsInstance, field: "source_description")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${rna_yieldsInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
