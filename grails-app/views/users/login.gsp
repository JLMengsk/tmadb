<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="ca.ubc.gpec.tmadb.util.MiscUtil"%>
<html>
  <head>
    <meta name="layout" content="main" />
    <title>Login</title>         
  </head>
  <body>
    <div class="body">
      <g:if test="${session.user==null}">
        <!-- [<g:link url="${grailsApplication.config.grails.serverURL}">back</g:link>] -->
        [<g:link url="${grails.serverURL}">back</g:link>]
      </g:if>
      <g:else>
        <!-- [<g:link url="${grailsApplication.config.grails.serverSecureURL}">back</g:link>] -->
        [<g:link url="${grails.serverSecureURL}">back</g:link>]
      </g:else>s
      <h1>Sign in</h1>
      <div class="dialog" style="width: 300px">
        <g:showFlashMessage />
        <g:form action="authenticate" method="post" >
          <p></p>
          <table>
            <tbody>            
              <tr class="prop">
                <td class="name">
                  <label for="login">Login or email:</label>
                </td>
                <td>
                  <input type="text" id="login" name="login"/>
                </td>
              </tr> 

              <tr class="prop">
                <td class="name">
                  <label for="password">Password:</label>
                </td>
                <td>
                  <input type="password" id="password" name="password"/>
                </td>
              </tr> 
            </tbody>
          </table>
          <g:each var="c" in="${MiscUtil.getHiddenParams(params)}">
            <input type="hidden" id="${c.key}" name="${c.key}" value="${c.value}"/>
          </g:each>
          <div class="buttons">
            <span class="button">
              <input class="save" type="submit" value="Login" />
            </span>
          </div>
        </g:form>
      </div>
    </div>
  </body>
</html>