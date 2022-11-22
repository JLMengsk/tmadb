<%@ page contentType="text/html;charset=UTF-8"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Download data</title>
  </head>
  <body>
    <div class="body">
      <h1>What would you like to download, ${session.user.name}?</h1>
      <g:form action="tmaScores">
        <div class="buttons">
          <span class="button"><g:submitButton name="tmaScores"
                                               class="tmaScores" value="Download TMA scores" /> </span>
        </div>
      </g:form>
      <g:form action="tmaImages">
        <div class="buttons">
          <span class="button"><g:submitButton name="tmaImages"
                                               class="tmaImages" value="Download TMA image copy script" /> </span>
        </div>
      </g:form>
    </div>
  </body>
</html>