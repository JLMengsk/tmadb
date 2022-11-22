<!--
  create new biomnarker record
-->
<%@ page import="ca.ubc.gpec.tmadb.util.ViewConstants" %>
<%@ page import="ca.ubc.gpec.tmadb.Biomarkers" %>
<%@ page import="ca.ubc.gpec.tmadb.Biomarker_types" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Create new biomarker record</title>
        <asset:stylesheet src="application.css"/>
        <asset:javascript src="application.js"/>
</head>
<body>
    <div class="body">
        <h1>Create new biomarker record</h1>
        <g:showFlashMessage />
        <div class="dialog">
            <g:form>
                <table>
                    <tbody>
                        <tr class="prop">
                            <td valign="top" class="name">Type*:</td>
                            <td valign="top" class="value">
                                <g:select name="${ViewConstants.HTML_PARAM_NAME_EDIT_BIOMARKER_INPUT_NAME_BIOMARKER_TYPE_ID}"
                                from="${Biomarker_types.list()}"
                                    value="1"
                                    optionKey="id" /></td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Name*:</td><td><g:textField name="${ViewConstants.HTML_PARAM_NAME_EDIT_BIOMARKER_INPUT_NAME}" value="" /></td>
                        <tr>
                        <tr class="prop">
                            <td valign="top" class="name">Description:</td><td><g:textArea name="${ViewConstants.HTML_PARAM_NAME_EDIT_BIOMARKER_INPUT_NAME_DESCRIPTION}" value="" /></td>
                        <tr>
                    </tbody>
                </table>
                *Required fields<br>
                <div class="buttons">
                    <span class="button"><g:actionSubmit 
                            class="save"
                            action="update" 
                            value="save" 
                            onclick="return check();" 
                            /></span>
                </div>
            </g:form>
        </div>
    </div>

    <g:javascript>
        function check () {
        return checkNewBiomarkerInput(
        '${ViewConstants.HTML_PARAM_NAME_EDIT_BIOMARKER_INPUT_NAME}',
        '${ViewConstants.HTML_PARAM_NAME_EDIT_BIOMARKER_INPUT_NAME_DESCRIPTION}',
        '${ViewConstants.HTML_PARAM_NAME_EDIT_BIOMARKER_INPUT_NAME_BIOMARKER_TYPE_ID}',
        '${createLink(controller:"biomarkers", action:"ajax_get_biomarker_by_name", params:[(ViewConstants.HTML_PARAM_NAME_EDIT_BIOMARKER_INPUT_NAME) : ""])}'
        );
        }
    </g:javascript>

</body>
</html>
