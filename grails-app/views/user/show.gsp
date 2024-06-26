<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta name="layout" content="main"/>
    <title>Usuários - Asaas</title>
</head>

<body page-title="Visualizar Usuário">
        <g:if test="${messageInfo}">
            <g:render template="/utils/messages" model="${messageInfo}"/>
        </g:if>
        <atlas-form-panel header="Dados do usuário" action="${createLink(controller: 'user', action: 'updatePassword', id: user.id)}">
        <atlas-button slot="actions" data-panel-start-editing icon="pencil" description="Editar"></atlas-button>
            <g:render template="/user/templates/userForm" model="${[user: user]}"/>
        </atlas-form-panel>
</body>
</html>