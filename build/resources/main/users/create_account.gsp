<%@ page import="ca.ubc.gpec.tmadb.util.ViewConstants"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Create account ...</title>
  </head>
  <body>
    [<g:link url="${createLink(controller: 'users', action: 'logout')}">back</g:link>]
  <h1>Create account to access GPEC TMA database resources</h1>
  <br>
  <div class="body" style="width:400px">
    <g:showFlashMessage />

    <g:form action="save_create_account" >
      <div class="dialog">
        <table>
          <tbody>
            <tr class="prop">
              <td valign="top" class="name"><label for="${ViewConstants.HTML_PARAM_NAME_CREATE_ACCOUNT_INPUT_NAME}">name</label></td>
              <td valign="top" class="value"><g:textField name="${ViewConstants.HTML_PARAM_NAME_CREATE_ACCOUNT_INPUT_NAME}" /></td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name"><label for="${ViewConstants.HTML_PARAM_NAME_CREATE_ACCOUNT_INPUT_NAME_EMAIL}">email</label></td>
            <td valign="top" class="value"><g:textField name="${ViewConstants.HTML_PARAM_NAME_CREATE_ACCOUNT_INPUT_NAME_EMAIL}" /></td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name"><label for="${ViewConstants.HTML_PARAM_NAME_CREATE_ACCOUNT_INPUT_NAME_PASSWORD}">desired password</label></td>
            <td valign="top" class="value"><g:passwordField title="please DO NOT use personal password e.g. DO NOT use password you use for your bank account." name="${ViewConstants.HTML_PARAM_NAME_CREATE_ACCOUNT_INPUT_NAME_PASSWORD}"/></td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name"><label for="${ViewConstants.HTML_PARAM_NAME_CREATE_ACCOUNT_INPUT_NAME_PASSWORDCHECK}">re-enter password</label></td>
            <td valign="top" class="value"><g:passwordField title="please re-enter your password" name="${ViewConstants.HTML_PARAM_NAME_CREATE_ACCOUNT_INPUT_NAME_PASSWORDCHECK}"/></td>
          </tr>

          </tbody>
        </table>
      </div>
      <br>
      <img src="${createLink(controller: 'simpleCaptcha', action: 'captcha')}"/><br>
      <label for="captcha">Please type the letters above in the box below:</label><br>
      <g:textField name="captcha"/>     
      <br>
      <br>
      <div class="buttons">
        <span class="button">
          <g:submitButton name="save_create_account" 
                          class="save" 
                          value="Create account" 
                          onclick="return checkFieldsForCreatePassword('${ViewConstants.HTML_PARAM_NAME_CREATE_ACCOUNT_INPUT_NAME_PASSWORD}','${ViewConstants.HTML_PARAM_NAME_CREATE_ACCOUNT_INPUT_NAME_PASSWORDCHECK}','${ViewConstants.HTML_PARAM_NAME_CREATE_ACCOUNT_INPUT_NAME}','${ViewConstants.HTML_PARAM_NAME_CREATE_ACCOUNT_INPUT_NAME_EMAIL}')"/>
        </span>
      </div>
    </g:form>
  </div>
  <g:javascript src="ca/ubc/gpec/tmadb/create_account_helper.js"/>
</body>
</html>
