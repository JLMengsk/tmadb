<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<title>Import TMA data</title>
</head>
<body>
	<div class="nav">
		<span class="menuButton"><a class="home"
			href="${createLink(uri: '/')}"><g:message
					code="default.home.label" /> </a> </span>
	</div>
	<div class="body">
		<div class="body">
			<h1>Upload RNA extraction records</h1>
			<g:form action="rnaExtractions">
				<div class="buttons">
					<span class="button"><g:submitButton name="rnaExtractions"
							class="cores" value="Upload RNA extraction records" /> </span>
				</div>
			</g:form>
			<g:form action="rnaYields">
				<div class="buttons">
					<span class="button"><g:submitButton name="rnaYields"
							class="cores" value="Upload RNA yield records" /> </span>
				</div>
			</g:form>
		</div>
	</div>
</body>
</html>