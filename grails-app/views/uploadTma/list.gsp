<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<title>Import TMA data</title>
</head>
<body>
	<div class="body">

		<div class="body">
			<h1>Upload TMA data</h1>
			<g:form action="cores">
				<div class="buttons">
					<span class="button"><g:submitButton name="cores"
							class="cores" value="Create TMA cores" /> </span>
				</div>
			</g:form>
			<g:form action="coreImages">
				<div class="buttons">
					<span class="button"><g:submitButton name="coreImages"
							class="coreImages" value="Upload TMA core images" /> </span>
				</div>
			</g:form>
			<g:form action="coreScores">
				<div class="buttons">
					<span class="button"><g:submitButton name="coreScores"
							class="coreScores" value="Upload TMA core scores" /> </span>
				</div>
			</g:form>
		</div>


	</div>
</body>
</html>