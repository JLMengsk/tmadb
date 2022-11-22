
<%@ page import="ca.ubc.gpec.tmadb.Tma_results" %>
<%@ page import="ca.ubc.gpec.tmadb.ScoreType" %>
<%@ page import="ca.ubc.gpec.tmadb.DisplayConstant" %>
<%@ page import="ca.ubc.gpec.tmadb.util.OddEvenRowFlag" %>
<%@ page import="ca.ubc.gpec.tmadb.util.ViewConstants" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'tma_results.label', default: 'Tma_results')}" />
  <title>TMA score result</title>
</head>
<body>
  <div class="body">
    <h1>TMA score result</h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <div class="dialog">
      <table>
        <tbody>

        <g:set var="oddEvenRowFlag" value="${new OddEvenRowFlag(ViewConstants.CSS_CLASS_NAME_ROW_ODD,ViewConstants.CSS_CLASS_NAME_ROW_EVEN)}"/>
            
        <tr class="${oddEvenRowFlag.showFlag()}">
          <td valign="top" class="name"><g:message code="tma_results.tma_result_name.label" default="tma result variable name" /></td>

        <td valign="top" class="value"><g:link controller="tma_result_names" action="show" id="${tma_resultsInstance?.tma_result_name?.id}">${tma_resultsInstance?.tma_result_name?.encodeAsHTML()}</g:link></td>

        </tr>

        <g:if test="${tma_resultsInstance?.total_nuclei_count != null}">
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name"><g:message code="tma_results.total_nuclei_count.label" default="${ScoreType.TOTAL_NUCLEI_COUNT}" /></td>

          <td valign="top" class="value">${fieldValue(bean: tma_resultsInstance, field: "total_nuclei_count")}</td>

          </tr>
        </g:if>

        <g:if test="${tma_resultsInstance?.positive_nuclei_count != null}">
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name"><g:message code="tma_results.positive_nuclei_count.label" default="${ScoreType.POSITIVE_NUCLEI_COUNT}" /></td>

          <td valign="top" class="value">${fieldValue(bean: tma_resultsInstance, field: "positive_nuclei_count")}</td>

          </tr>
        </g:if>

        <g:if test="${tma_resultsInstance?.positive_membrane_count != null}">
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name"><g:message code="tma_results.positive_membrane_count.label" default="${ScoreType.POSITIVE_MEMBRANE_COUNT}" /></td>

          <td valign="top" class="value">${fieldValue(bean: tma_resultsInstance, field: "positive_membrane_count")}</td>

          </tr>
        </g:if>

        <g:if test="${tma_resultsInstance?.positive_cytoplasmic_count != null}">
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name"><g:message code="tma_results.positive_cytoplasmic_count.label" default="${ScoreType.POSITIVE_CYTOPLASMIC_COUNT}" /></td>

          <td valign="top" class="value">${fieldValue(bean: tma_resultsInstance, field: "positive_cytoplasmic_count")}</td>

          </tr>
        </g:if>

        <g:if test="${tma_resultsInstance?.visual_percent_positive_estimate != null}">
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name"><g:message code="tma_results.visual_percent_positive_nuclei_estimate.label" default="${ScoreType.VISUAL_PERCENT_POSITIVE_ESTIMATE}" /></td>

          <td valign="top" class="value">${fieldValue(bean: tma_resultsInstance, field: "visual_percent_positive_nuclei_estimate")}</td>

          </tr>
        </g:if>
        
        <g:if test="${tma_resultsInstance?.visual_percent_positive_nuclei_estimate != null}">
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name"><g:message code="tma_results.visual_percent_positive_nuclei_estimate.label" default="${ScoreType.VISUAL_PERCENT_POSITIVE_NUCLEI_ESTIMATE}" /></td>

          <td valign="top" class="value">${fieldValue(bean: tma_resultsInstance, field: "visual_percent_positive_nuclei_estimate")}</td>

          </tr>
        </g:if>

        <g:if test="${tma_resultsInstance?.visual_percent_positive_cytoplasmic_estimate != null}">
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name"><g:message code="tma_results.visual_percent_positive_cytoplasmic_estimate.label" default="${ScoreType.VISUAL_PERCENT_POSITIVE_CYTOPLASMIC_ESTIMATE}" /></td>

          <td valign="top" class="value">${fieldValue(bean: tma_resultsInstance, field: "visual_percent_positive_cytoplasmic_estimate")}</td>

          </tr>
        </g:if>

        <g:if test="${tma_resultsInstance?.visual_percent_positive_membrane_estimate != null}">
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name"><g:message code="tma_results.visual_percent_positive_membrane_estimate.label" default="${ScoreType.VISUAL_PERCENT_POSITIVE_MEMBRANE_ESTIMATE}" /></td>

          <td valign="top" class="value">${fieldValue(bean: tma_resultsInstance, field: "visual_percent_positive_membrane_estimate")}</td>

          </tr>
        </g:if>

         <g:if test="${tma_resultsInstance?.visual_percent_positive_cytoplasmic_and_or_membrane_estimate != null}">
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name"><g:message code="tma_results.visual_percent_positive_cytoplasmic_and_or_membrane_estimate.label" default="${ScoreType.VISUAL_PERCENT_POSITIVE_CYTOPLASMIC_AND_OR_MEMBRANE_ESTIMATE}" /></td>

          <td valign="top" class="value">${fieldValue(bean: tma_resultsInstance, field: "visual_percent_positive_cytoplasmic_and_or_membrane_estimate")}</td>

          </tr>
        </g:if>
        
        <g:if test="${tma_resultsInstance?.positive_itil_count != null}">
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name"><g:message code="tma_results.positive_itil_count.label" default="${ScoreType.POSITIVE_ITIL_COUNT}" /></td>

          <td valign="top" class="value">${fieldValue(bean: tma_resultsInstance, field: "positive_itil_count")}</td>

          </tr>
        </g:if>

        <g:if test="${tma_resultsInstance?.positive_stil_count != null}">
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name"><g:message code="tma_results.positive_stil_count.label" default="${ScoreType.POSITIVE_STIL_COUNT}" /></td>

          <td valign="top" class="value">${fieldValue(bean: tma_resultsInstance, field: "positive_stil_count")}</td>

          </tr>
        </g:if>

        <tr class="${oddEvenRowFlag.showFlag()}">
          <td valign="top" class="name"><g:message code="tma_results.tma_scorer1.label" default="TMA scorer" /></td>

        <td valign="top" class="value">
          <ul>
            <g:if test="${tma_resultsInstance?.tma_scorer1 != null}">
              <li><g:link controller="tma_scorers" action="show" id="${tma_resultsInstance?.tma_scorer1?.id}">${tma_resultsInstance?.tma_scorer1?.encodeAsHTML()}</g:link></li>
            </g:if>
            <g:if test="${tma_resultsInstance?.tma_scorer2 != null}">
              <li><g:link controller="tma_scorers" action="show" id="${tma_resultsInstance?.tma_scorer2?.id}">${tma_resultsInstance?.tma_scorer2?.encodeAsHTML()}</g:link></li>
            </g:if>
            <g:if test="${tma_resultsInstance?.tma_scorer3 != null}">
              <li><g:link controller="tma_scorers" action="show" id="${tma_resultsInstance?.tma_scorer3?.id}">${tma_resultsInstance?.tma_scorer3?.encodeAsHTML()}</g:link></li>
            </g:if>
          </ul>
        </td>
        </tr>

        <g:if test="${tma_resultsInstance?.ihc_score_category != null}">
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name"><g:message code="tma_results.ihc_score_category.label" default="${ScoreType.CATEGORICAL_SCORE}" /></td>

          <td valign="top" class="value"><g:link controller="ihc_score_categories" action="show" id="${tma_resultsInstance?.ihc_score_category?.id}">${tma_resultsInstance?.ihc_score_category?.encodeAsHTML()}</g:link></td>

          </tr>
        </g:if>

        <g:if test="${tma_resultsInstance?.fish_amplification_ratio != null}">
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name"><g:message code="tma_results.fish_amplification_ratio.label" default="${ScoreType.FISH_AMPLIFICATION_RATIO}" /></td>

          <td valign="top" class="value">${fieldValue(bean: tma_resultsInstance, field: "fish_amplification_ratio")}</td>

          </tr>
        </g:if>

        <g:if test="${tma_resultsInstance?.fish_average_signal != null}">
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name"><g:message code="tma_results.fish_average_signal.label" default="${ScoreType.FISH_AVERAGE_SIGNAL}" /></td>

          <td valign="top" class="value">${fieldValue(bean: tma_resultsInstance, field: "fish_average_signal")}</td>

          </tr>
        </g:if>

        <tr class="${oddEvenRowFlag.showFlag()}">
          <td valign="top" class="name"><g:message code="tma_results.scoring_date.label" default="Scoring date" /></td>

        <td valign="top" class="value"><g:formatDate format="${DisplayConstant.DATE_FORMAT}" date="${tma_resultsInstance?.scoring_date}" /></td>

        </tr>

        <tr class="${oddEvenRowFlag.showFlag()}">
          <td valign="top" class="name"><g:message code="tma_results.received_date.label" default="Received date" /></td>

        <td valign="top" class="value"><g:formatDate format="${DisplayConstant.DATE_FORMAT}" date="${tma_resultsInstance?.received_date}" /></td>

        </tr>

        <tr class="${oddEvenRowFlag.showFlag()}">
          <td valign="top" class="name"><g:message code="tma_results.comment.label" default="Comment" /></td>

        <td valign="top" class="value">${fieldValue(bean: tma_resultsInstance, field: "comment")}</td>

        </tr>

        <tr class="${oddEvenRowFlag.showFlag()}">
          <td valign="top" class="name"><g:message code="tma_results.tma_core_image.label" default="Tma core image" /></td>

        <td valign="top" class="value"><g:link controller="tma_core_images" action="show" id="${tma_resultsInstance?.tma_core_image?.id}">${tma_resultsInstance?.tma_core_image?.encodeAsHTML()}</g:link></td>

        </tr>

        </tbody>
      </table>
    </div>

  </div>
</body>
</html>
