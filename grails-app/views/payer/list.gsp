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
        <atlas-filter class="js-payer-filter-input">
            <atlas-filter-form slot="simple-filter">
                <atlas-filter-group header="Listagem" name="list" slot="col-1">
                    <atlas-checkbox value="includeDeleted">Exibir deletados</atlas-checkbox>
                </atlas-filter-group>
            </atlas-filter-form>
        </atlas-filter>

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
