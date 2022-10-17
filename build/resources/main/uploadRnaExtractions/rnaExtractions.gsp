<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<title>Upload RNA extraction records</title>
<g:javascript library="prototype"/>
</head>
<body>

	<div class="nav">
		<span class="menuButton"><a class="home"
			href="${createLink(uri: '/')}"><g:message
					code="default.home.label" /> </a> </span>
	</div>
	<div class="body">
		<h1>Upload RNA extraction records</h1>
		<g:form action="uploadRnaExtractions" method="post"
			enctype="multipart/form-data">
			<div class="dialog">
				<table>
					<tbody>
						<tr class="prop">
							<td valign="top" class="name"><label>Patient source</label></td>
							<td valign="top"><g:select optionKey="id"
									name="patient_source_id" id="patient_source_id"
									from="${ca.ubc.gpec.tmadb.Patient_sources.list()}"/>
							</td>
						</tr>
						
						<tr class="prop">
							<td valign="top" class="name"><label>Coring project</label></td>
							<td valign="top"><g:select optionKey="id"
									name="coring_project_id" id="coring_project_id"
									from="${ca.ubc.gpec.tmadb.Coring_projects.list()}"/>
							</td>
						</tr>
						
						<tr class="prop">
							<td valign="top" class="name"><label>RNA extractions record date</label>
							</td>
							<td valign="top"><g:datePicker name="record_date" 
									id="record_date" value="${new Date()}" precision="day"/>
							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label>RNA extractions
									information file</label></td>
							<td valign="top"><input type="file" name="myFile" /></td>
						</tr>

					</tbody>
				</table>
			</div>
			<div class="buttons">
				<span class="button"><g:submitButton name="upload"
						class="upload" value="Upload" /> </span>
			</div>
		</g:form>
	</div>
	
</body>
</html>
