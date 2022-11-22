
<%@ page import="ca.ubc.gpec.tmadb.Qpcr_results" %>
<%@ page import="ca.ubc.gpec.tmadb.DisplayConstant" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'qpcr_results.label', default: 'Qpcr_results')}" />
  <title>Show qPCR result</title>
</head>
<body>
  <div class="body">
    <h1>qPCR result</h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <div class="dialog">
      <table>
        <tbody>

          <tr class="prop">
            <td valign="top" class="name"><g:message code="qpcr_results.biomarker.label" default="Biomarker:" /></td>

        <td valign="top" class="value"><g:link controller="biomarkers" action="show" id="${qpcr_resultsInstance?.biomarker?.id}">${qpcr_resultsInstance?.biomarker?.encodeAsHTML()}</g:link></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="qpcr_results.comment.label" default="Comment:" /></td>

        <td valign="top" class="value">${fieldValue(bean: qpcr_resultsInstance, field: "comment")}</td>

        </tr>

        <g:if test="${qpcr_resultsInstance.copy_number != null}">
          <tr class="prop">
            <td valign="top" class="name"><g:message code="qpcr_results.copy_number.label" default="Copy number:" /></td>
          <td valign="top" class="value">${fieldValue(bean: qpcr_resultsInstance, field: "copy_number")}</td>
          </tr>
        </g:if>

        <g:if test="${qpcr_resultsInstance.crossing_point != null}">
          <tr class="prop">
            <td valign="top" class="name"><g:message code="qpcr_results.crossing_point.label" default="Crossing point:" /></td>
          <td valign="top" class="value">${fieldValue(bean: qpcr_resultsInstance, field: "crossing_point")}</td>
          </tr>
        </g:if>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="qpcr_results.date_received.label" default="Date received:" /></td>

        <td valign="top" class="value"><g:formatDate format="${DisplayConstant.DATE_FORMAT}" date="${qpcr_resultsInstance?.date_received}" /></td>

        </tr>

        <g:if test="${qpcr_resultsInstance.normalized_expression != null}">
          <tr class="prop">
            <td valign="top" class="name"><g:message code="qpcr_results.normalized_expression.label" default="Normalized expression:" /></td>
          <td valign="top" class="value">${fieldValue(bean: qpcr_resultsInstance, field: "normalized_expression")}</td>
          </tr>
        </g:if>

        <g:if test="${qpcr_resultsInstance.precalculated_numeric != null}">
          <tr class="prop">
            <td valign="top" class="name"><g:message code="qpcr_results.precalculated_numeric.label" default="Pre-calculated value (numeric):" /></td>
          <td valign="top" class="value">${fieldValue(bean: qpcr_resultsInstance, field: "precalculated_numeric")}</td>
          </tr>
        </g:if>

        <g:if test="${qpcr_resultsInstance.precalculated_text != null}">
          <tr class="prop">
            <td valign="top" class="name"><g:message code="qpcr_results.precalculated_text.label" default="Pre-calculated value (text)" /></td>
          <td valign="top" class="value">${fieldValue(bean: qpcr_resultsInstance, field: "precalculated_text")}</td>
          </tr>
        </g:if>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="qpcr_results.qpcr_experiment.label" default="qPCR experiment:" /></td>

        <td valign="top" class="value"><g:link controller="qpcr_experiments" action="show" id="${qpcr_resultsInstance?.qpcr_experiment?.id}">${qpcr_resultsInstance?.qpcr_experiment?.encodeAsHTML()}</g:link></td>

        </tr>

        <g:if test="${qpcr_resultsInstance.status != null}">
          <tr class="prop">
            <td valign="top" class="name"><g:message code="qpcr_results.status.label" default="Status:" /></td>
          <td valign="top" class="value">${fieldValue(bean: qpcr_resultsInstance, field: "status")}</td>
          </tr>
        </g:if>

        </tbody>
      </table>
    </div>

  </div>
</body>
</html>
