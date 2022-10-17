<%@ page contentType="text/html;charset=UTF-8" %>
<h1>Database contents</h1>
<ul>
<li><g:if test="${controllerName=='biomarkers'}"  ><g:link controller="biomarkers"  ><strong>Biomarkers</strong></g:link></g:if><g:else><g:link controller="biomarkers"  >Biomarkers</g:link></g:else>
<li><g:if test="${controllerName=='tma_projects'}"><g:link controller="tma_projects"><strong>TMA projects</strong></g:link></g:if><g:else><g:link controller="tma_projects">TMA projects</g:link></g:else>
</ul>

<h1>Database queries</h1>
<ul>
<li><g:if test="${controllerName=='downloadData'}"><g:link controller="downloadData"><strong>Download TMA related data</strong></g:link></g:if><g:else><g:link controller="downloadData">Download TMA related data</g:link></g:else>
</ul>

<h1>Import/input data tasks</h1>
<ul>
<li><g:if test="${controllerName=='scoreTma'|controllerName=='scoring_sessions'}"><g:link controller="scoreTma"><strong>Score TMA</strong></g:link></g:if><g:else><g:link controller="scoreTma">Score TMA</g:link></g:else>
<li><g:if test="${controllerName=='uploadTma'}"><g:link controller="uploadTma"><strong>Upload TMA related data</strong></g:link></g:if><g:else><g:link controller="uploadTma">Upload TMA related data</g:link></g:else>
</ul>