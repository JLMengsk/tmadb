
<%@ page import="ca.ubc.gpec.tmadb.Tma_results" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tma_results.label', default: 'Tma_results')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'tma_results.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="total_nuclei_count" title="${message(code: 'tma_results.total_nuclei_count.label', default: 'Totalnucleicount')}" />
                        
                            <g:sortableColumn property="positive_nuclei_count" title="${message(code: 'tma_results.positive_nuclei_count.label', default: 'Positivenucleicount')}" />
                        
                            <g:sortableColumn property="positive_membrane_count" title="${message(code: 'tma_results.positive_membrane_count.label', default: 'Positivemembranecount')}" />
                        
                            <g:sortableColumn property="positive_cytoplasmic_count" title="${message(code: 'tma_results.positive_cytoplasmic_count.label', default: 'Positivecytoplasmiccount')}" />
                        
                            <g:sortableColumn property="visual_percent_positive_nuclei_estimate" title="${message(code: 'tma_results.visual_percent_positive_nuclei_estimate.label', default: 'Visualpercentpositivenucleiestimate')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${tma_resultsInstanceList}" status="i" var="tma_resultsInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${tma_resultsInstance.id}">${fieldValue(bean: tma_resultsInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: tma_resultsInstance, field: "total_nuclei_count")}</td>
                        
                            <td>${fieldValue(bean: tma_resultsInstance, field: "positive_nuclei_count")}</td>
                        
                            <td>${fieldValue(bean: tma_resultsInstance, field: "positive_membrane_count")}</td>
                        
                            <td>${fieldValue(bean: tma_resultsInstance, field: "positive_cytoplasmic_count")}</td>
                        
                            <td>${fieldValue(bean: tma_resultsInstance, field: "visual_percent_positive_nuclei_estimate")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${tma_resultsInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
