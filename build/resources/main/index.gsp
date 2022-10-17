<html>
  <head>
    <title>Welcome to GPEC TMA Database</title>
    <meta name="layout" content="main" />
  </head>
  <body>
    <div class="body">
      <h1>Welcome to GPEC TMA Database</h1>
      <g:showFlashMessage />
      <p>Please ${session.user == null ? "login or " : ""}click on the GPEC logo <img src="${resource(dir:'images',file:'gpec_logo_only_small.jpg')}" alt="GPEC logo" /> for available options.</p>
    </div>
    <h1> Hello "${grails.serverURL}" </h1>

  </body>
</html>
