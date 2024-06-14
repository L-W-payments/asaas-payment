<!doctype html>
<html>
<head>
    <title>Erro - Asaas</title>
    <meta name="layout" content="main">
</head>

<%
    String currentPath = createLink(controller: params.controller, action: params.action)
%>

<body page-icon="alert-triangle" page-title="Ocorreu um erro!" breadcrumb-title="Oooops!">
    <atlas-panel>
        <atlas-layout alignment="center" gap="5">
            <atlas-illustration name="triangle-exclamation-mark-siren"></atlas-illustration>

            <atlas-layout alignment="center" gap="1">
                <atlas-heading size="h4">
                    Ficamos tristes em lhe informar isso, mas algo de errado aconteceu...
                </atlas-heading>

                <atlas-text>
                    Enquanto nós estávamos tentando realizar a sua operação, algo deu errado.
                </atlas-text>
            </atlas-layout>

            <atlas-layout justify="center" gap="3" inline>
                <atlas-button icon="refresh" description="Recarregar" href="${currentPath}"></atlas-button>

                <g:if test="${currentPath != "/"}">
                    <atlas-button icon="arrow-up-right" description="Tela inicial" href="/"></atlas-button>
                </g:if>
            </atlas-layout>
        </atlas-layout>
    </atlas-panel>
</body>
</html>
