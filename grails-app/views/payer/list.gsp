<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta name="layout" content="main"/>
    <title>Pagadores - Asaas</title>
</head>

<body page-title="Lista de pagadores">

<atlas-panel header="${payers ? 'Seus pagadores' : ''}">
    <g:if test="${payers}">
        <atlas-button
                icon="plus"
                description="Adicionar pagador"
                href="/payer"
                slot="actions"></atlas-button>

        <atlas-table has-actions>
            <atlas-table-header slot="header">
                <atlas-table-col>
                    Nome
                </atlas-table-col>
                <atlas-table-col>
                    E-mail
                </atlas-table-col>

                <atlas-table-col>
                    Data de criação
                </atlas-table-col>
            </atlas-table-header>
            <atlas-table-body slot="body">
                <g:each var="payer" in="${payers}">
                    <atlas-table-row href="${createLink(controller: "payer", action: "show", id: payer.id)}">
                        <atlas-table-col>
                            ${payer.name}
                        </atlas-table-col>

                        <atlas-table-col>
                            ${payer.email}
                        </atlas-table-col>

                        <atlas-table-col>
                            <g:formatDate date="${payer.dateCreated}" format="dd/MM/yyyy"/>
                        </atlas-table-col>

                        <atlas-button-group slot="actions" group-all>
                            <atlas-icon-button
                                    icon="pencil"
                                    theme="primary"
                                    description="Editar pagador">
                            </atlas-icon-button>
                        </atlas-button-group>
                    </atlas-table-row>
                </g:each>
            </atlas-table-body>
        </atlas-table>
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