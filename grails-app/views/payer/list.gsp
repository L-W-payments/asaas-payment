<html>
<head>
    <title>Pagadores - Asaas</title>
    <meta name="layout" content="main">
</head>

<body page-title="Lista de pagadores">
<atlas-panel class="js-payer-panel">
    <g:if test="${payerList}">
        <atlas-toolbar>
            <atlas-search-input class="js-payer-search-input" placeholder="Pesquisar" icon="magnifier"
                                slot="search"></atlas-search-input>
            <atlas-button description="Adicionar" icon="plus" slot="actions"></atlas-button>
        </atlas-toolbar>

        <g:render template="/payer/templates/table"/>
        <asset:javascript src="PayerListController.js"/>
    </g:if>
    <g:else>
        <atlas-empty-state
                illustration="schedule-user-avatar"
                header="Sem pagadores cadastrados">
            Aqui você pode cadastrar os pagadores que deseja utilizar em suas transações.

            <atlas-button
                    icon="plus"
                    description="Adicionar pagador"
                    href="/payer"
                    slot="button"></atlas-button>
        </atlas-empty-state>
    </g:else>
</atlas-panel>

</body>
</html>
