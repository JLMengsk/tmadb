
<%@ page import="ca.ubc.gpec.tmadb.Qpcr_experiments" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'qpcr_experiments.label', default: 'Qpcr_experiments')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'qpcr_experiments.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="date" title="${message(code: 'qpcr_experiments.date.label', default: 'Date')}" />
                        
                            <g:sortableColumn property="experiment_sample_id" title="${message(code: 'qpcr_experiments.experiment_sample_id.label', default: 'Experimentsampleid')}" />
                        
                            <th><g:message code="qpcr_experiments.institution.label" default="Institution" /></th>
                        
                            <th><g:message code="qpcr_experiments.surgical_block.label" default="Surgicalblock" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${qpcr_experimentsInstanceList}" status="i" var="qpcr_experimentsInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${qpcr_experimentsInstance.id}">${fieldValue(bean: qpcr_experimentsInstance, field: "id")}</g:link></td>
                        
                            <td><g:formatDate date="${qpcr_experimentsInstance.date}" /></td>
                        
                            <td>${fieldValue(bean: qpcr_experimentsInstance, field: "experiment_sample_id")}</td>
                        
                            <td>${fieldValue(bean: qpcr_experimentsInstance, field: "institution")}</td>
                        
                            <td>${fieldValue(bean: qpcr_experimentsInstance, field: "surgical_block")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${qpcr_experimentsInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
