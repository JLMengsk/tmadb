<!--
  ask some questions specific for a particular scoring session
-->
<%@ page import="ca.ubc.gpec.tmadb.Scoring_session_questions"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Scoring session: ${fieldValue(bean: scoring_sessionInstance, field: "name")}</title>
  </head>
  <body>
    <h1>Scoring session: ${scoring_sessionInstance.name}</h1>
    <p>
      Before you begin this scoring session, please kindly answer the following questions:
    </p>
    <div style="width:400px">
      <g:form>
        <input type="hidden" name="id" value="${scoring_sessionInstance.id}"/>
        <table>
          <g:each in="${scoring_sessionInstance.scoring_session_questions}">
            <tr><td>${it.question}:</td><td><g:textField name="${Scoring_session_questions.ANSWER_HTML_INPUT_PARAM_NAME}${it.display_order}" value="${it.answer}" /></td></tr>
          </g:each>
        </table>
        <i>please note: all fields required</i>
        <div class="buttons">
          <span class="button"><g:actionSubmit 
              class="save"
              action="save_scoring_session_questions_answers" 
              value="submit" 
              onclick="return checkAnswer();" 
              /></span>
        </div>
      </g:form>
    </div>
  <g:javascript>
    function checkAnswer() {
    // first check to make sure no empty answer
    <g:each in="${scoring_sessionInstance.scoring_session_questions}">
      if (!document.getElementById('${Scoring_session_questions.ANSWER_HTML_INPUT_PARAM_NAME}${it.display_order}').value) {
      alert("${it.question}");
      return false;
      }
    </g:each>

    return confirm(
    <g:each in='${scoring_sessionInstance.scoring_session_questions}'>
      '${it.question}: '+document.getElementById('${Scoring_session_questions.ANSWER_HTML_INPUT_PARAM_NAME}${it.display_order}').value+'\n'+
    </g:each>
    'Continue to submit?');
    }
  </g:javascript>

</body>
</html>
