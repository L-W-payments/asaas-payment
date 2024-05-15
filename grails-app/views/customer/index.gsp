<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <title>Registrar Cliente</title>
</head>
<body>
<g:if test="errors">
  <div>
    <g:each var="error" in="${errors}">
      <p>${error}</p>
    </g:each>
  </div>
</g:if>
<form action="${createLink(controller: 'customer', action: 'save')}" method="POST">
  <g:render template="/layouts/basePersonForm" />
  <button type="submit">Enviar</button>
</form>
</body>
</html>