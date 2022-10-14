
<%@ page import="ca.ubc.gpec.tmadb.Staining_details" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'staining_details.label', default: 'Staining_details')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'staining_details.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="antibody_concentration" title="${message(code: 'staining_details.antibody_concentration.label', default: 'Antibodyconcentration')}" />
                        
                            <g:sortableColumn property="antigen_retrieval_method" title="${message(code: 'staining_details.antigen_retrieval_method.label', default: 'Antigenretrievalmethod')}" />
                        
                            <th><g:message code="staining_details.biomarker.label" default="Biomarker" /></th>
                        
                            <g:sortableColumn property="detection_method" title="${message(code: 'staining_details.detection_method.label', default: 'Detectionmethod')}" />
                        
                            <g:sortableColumn property="dilution" title="${message(code: 'staining_details.dilution.label', default: 'Dilution')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${staining_detailsInstanceList}" status="i" var="staining_detailsInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${staining_detailsInstance.id}">${fieldValue(bean: staining_detailsInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: staining_detailsInstance, field: "antibody_concentration")}</td>
                        
                            <td>${fieldValue(bean: staining_detailsInstance, field: "antigen_retrieval_method")}</td>
                        
                            <td>${fieldValue(bean: staining_detailsInstance, field: "biomarker")}</td>
                        
                            <td>${fieldValue(bean: staining_detailsInstance, field: "detection_method")}</td>
                        
                            <td>${fieldValue(bean: staining_detailsInstance, field: "dilution")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${staining_detailsInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
