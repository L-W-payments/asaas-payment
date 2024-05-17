<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta name="layout" content="main" />
  <title>Registrar Cliente</title>
</head>
<body page-title="Cadastro de Cliente">
<atlas-panel class="js-person-form">
  <atlas-form action="${createLink(controller: 'customer', action: 'save' )}">
    <atlas-input hidden name="country" value="Brasil"></atlas-input>
    <g:render template="/templates/basePersonForm"
              model="${[tittle: "Cadastro de Cliente"]}" ></g:render>

    <atlas-button submit description="Salvar"></atlas-button>
  </atlas-form>
</atlas-panel>
<asset:javascript src="PersonController.js"/>
</body>
</html>