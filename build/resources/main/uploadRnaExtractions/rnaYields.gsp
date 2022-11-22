<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<title>Upload RNA yield records</title>
<g:javascript library="prototype"/>
</head>
<body>

	<div class="nav">
		<span class="menuButton"><a class="home"
			href="${createLink(uri: '/')}"><g:message
					code="default.home.label" /> </a> </span>
	</div>
	<div class="body">
		<h1>Upload RNA yield records</h1>
		<g:form action="uploadRnaYields" method="post"
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
							<td valign="top" class="name"><label>RNA yields record date</label>
							</td>
							<td valign="top"><g:datePicker name="yield_record_date" 
									id="yield_record_date" value="${new Date()}" precision="day"/>
							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label>RNA yields source description <br><i>i.e. what is the initial record</i></label>
							</td>
							<td valign="top"><textarea name="source_description" id="source_description"></textarea>
							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label>RNA yields
									information file<br><i>NOTE: use empty concentration/yield<br>to indicate missing value</i></label></td>
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
