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
            <atlas-filter description="Filtro" slot="filter">
                <atlas-filter-form slot="simple-filter">
                    <atlas-filter-group header="Category 1" name="category1" required-fields="1">
                        <atlas-checkbox value="1">Label 1.1</atlas-checkbox>
                        <atlas-checkbox value="2">Label 1.2</atlas-checkbox>
                        <atlas-checkbox value="3">Label 1.3</atlas-checkbox>
                    </atlas-filter-group>

                    <atlas-filter-group header="Category 2" name="category2">
                        <atlas-radio value="1">Label 2.1</atlas-radio>
                        <atlas-radio value="2">Label 2.2</atlas-radio>
                    </atlas-filter-group>
                </atlas-filter-form>
            </atlas-filter>
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
