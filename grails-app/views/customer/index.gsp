<!DOCTYPE html>
<html lang="pt-br">
<head>
    <title>Registrar-se - Asaas</title>
    <asset:stylesheet src="new-theme.scss"/>

    <link rel="stylesheet" href="https://atlas.asaas.com/v15.18.0/atlas.min.css" crossorigin="anonymous">
    <script defer src="https://atlas.asaas.com/v15.18.0/atlas.min.js" crossorigin="anonymous"></script>
</head>

<body page-title="Cadastro de Cliente">
    <g:if test="${messageInfo}">
        <g:render template="/utils/messages" model="${messageInfo}"/>
    </g:if>
    <atlas-grid container>
        <atlas-layout alignment="center">
            <atlas-image src="https://atlas.asaas.com/assets/images/logos/asaas-logo.7POEJYYY.svg"
                         height="100" style="margin-top: 32px;"></atlas-image>
            <atlas-text>A solução mais completa e segura em emissão de cobranças e serviços financeiros.</atlas-text>
        </atlas-layout>
            <atlas-form class="js-person-form" action="${createLink(controller: 'customer', action: 'save')}">
                <atlas-input hidden name="country" value="Brasil"></atlas-input>
                <g:render template="/templates/basePersonForm"
                          model="${[title: "Criando sua conta"]}"></g:render>
                <atlas-layout inline gap="2" alignment="center" justify="space-between">
                    <atlas-button submit description="Criar conta" ></atlas-button>
                    <atlas-text>Já possui uma conta? <atlas-link href="/login/auth">Entrar</atlas-link></atlas-text>
                </atlas-layout>

            </atlas-form>
    </atlas-grid>

</body>
</html>