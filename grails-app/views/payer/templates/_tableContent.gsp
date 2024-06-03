<g:each var="payer" in="${payerList}">
    <atlas-table-row
            href="${createLink(controller: "payer", action: "show", id: payer.id)}"
            data-delete-url="${createLink(controller: "payer", action: "fetchDelete", id: payer.id)}"
    >
        <atlas-table-col>
            ${payer.name}
        </atlas-table-col>
        <atlas-table-col>
            ${payer.email}
        </atlas-table-col>
        <atlas-table-col>
            <g:formatDate date="${payer.dateCreated}" format="dd/MM/yyyy"/>
        </atlas-table-col>
        <atlas-button-group slot="actions" group-after="2">
            <atlas-icon-button
                    data-action="edit"
                    tooltip="Editar pagador"
                    icon="pencil"
                    theme="primary"
                    description="Editar pagador">
            </atlas-icon-button>
            <atlas-icon-button
                    data-action="delete"
                    tooltip="Remover pagador"
                    icon="trash"
                    theme="danger"
                    description="Remover pagador">
            </atlas-icon-button>
        </atlas-button-group>
    </atlas-table-row>
</g:each>