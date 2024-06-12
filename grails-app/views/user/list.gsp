<html>
<head>
    <title>Usuários - Asaas</title>
    <meta name="layout" content="main">
</head>

<body page-title="Lista dos seus usuários">
    <atlas-panel class="js-user-panel">
        <g:if test="${userList}">
            <atlas-toolbar>
                <atlas-search-input
                        class="js-user-search-input"
                        placeholder="Pesquisar"
                        icon="magnifier"
                        slot="search"></atlas-search-input>

                <atlas-button description="Adicionar" icon="plus" href="/user" slot="actions"></atlas-button>

                <atlas-filter class="js-payer-filter-input" slot="filter">
                    <atlas-filter-form slot="simple-filter">
                        <atlas-filter-group header="Listagem" name="includeDeleted" slot="col-1">
                            <atlas-checkbox value="true">Exibir deletados</atlas-checkbox>
                        </atlas-filter-group>
                    </atlas-filter-form>
                </atlas-filter>
            </atlas-toolbar>

            <g:render template="/user/templates/table"/>
            <asset:javascript src="UserListController.js"/>
        </g:if>
        <g:else>
            <atlas-empty-state
                    illustration="schedule-user-avatar"
                    header="Sem usuários cadastrados">
                Aqui você pode cadastrar os usuários que deseja gerênciar suas transações.

                <atlas-button
                        icon="plus"
                        description="Adicionar usuário"
                        href="/user"
                        slot="button"></atlas-button>
            </atlas-empty-state>
        </g:else>
    </atlas-panel>

</body>
</html>
