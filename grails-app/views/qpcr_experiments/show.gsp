
<%@ page import="ca.ubc.gpec.tmadb.Qpcr_experiments" %>
<%@ page import="ca.ubc.gpec.tmadb.Qpcr_results" %>
<%@ page import="ca.ubc.gpec.tmadb.DisplayConstant" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'qpcr_experiments.label', default: 'Qpcr_experiments')}" />
  <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
  <div class="body">
    <h1>qPCR experiment</h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <div class="dialog">
      <table>
        <tbody>

          <tr class="prop">
            <td valign="top" class="name"><g:message code="qpcr_experiments.date.label" default="Date:" /></td>

        <td valign="top" class="value"><g:formatDate format="${DisplayConstant.DATE_FORMAT}" date="${qpcr_experimentsInstance?.date}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="qpcr_experiments.experiment_sample_id.label" default="Experiment sample ID:" /></td>

        <td valign="top" class="value">${fieldValue(bean: qpcr_experimentsInstance, field: "experiment_sample_id")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="qpcr_experiments.institution.label" default="Institution:" /></td>

        <td valign="top" class="value"><g:link controller="institutions" action="show" id="${qpcr_experimentsInstance?.institution?.id}">${qpcr_experimentsInstance?.institution?.encodeAsHTML()}</g:link></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="qpcr_experiments.surgical_block.label" default="Surgical block:" /></td>

        <td valign="top" class="value"><g:link controller="surgical_blocks" action="show" id="${qpcr_experimentsInstance?.surgical_block?.id}">${qpcr_experimentsInstance?.surgical_block?.encodeAsHTML()}</g:link></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="qpcr_experiments.comment.label" default="Comment:" /></td>

        <td valign="top" class="value">${qpcr_experimentsInstance.comment}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name">Result(s):</td>

          <td valign="top" style="text-align: left;" class="value">
            <ul>
              <g:each in="${qpcr_experimentsInstance?.qpcr_results}" var="s">
                <g:if test="${s?.showContainsPrecalculatedValue()}">
                  <li><g:link controller="qpcr_results" action="show" id="${s.id}" title="${s?.comment}">${s?.toString()}</g:link></li>
                </g:if>
              </g:each>
            </ul>
          </td>

        </tr>

        </tbody>
      </table>
    </div>
  </div>
</body>
</html>
