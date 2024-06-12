<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta name="layout" content="main"/>
  <title>Usuários - Asaas</title>
</head>

<body page-title="Cadastro de Usuário">
  <atlas-panel>
    <g:if test="${messageInfo}">
      <g:render template="/utils/messages" model="${messageInfo}"/>
    </g:if>
    <atlas-form action="${createLink(controller: 'user', action: 'save')}">
      <g:render template="/user/templates/userForm"
                model="${[title: "Cadastrar Usuário"]}"/>
      <atlas-button submit description="Salvar"></atlas-button>
    </atlas-form>
    <atlas-text muted>O usuário será adicionado a sua conta e não poderá adicionar novos usuários.</atlas-text>
  </atlas-panel>

</body>
</html>