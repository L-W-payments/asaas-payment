<g:each var="payer" in="${payerList}">
    <atlas-table-row
            href="${createLink(controller: "payer", action: "show", id: payer.id)}"
            data-action-url="${createLink(controller: "payer", action: "${payer.deleted ? 'restore' : 'delete'}", id: payer.id)}">
        <atlas-table-col>
            ${payer.name}
        </atlas-table-col>
        <atlas-table-col>
            ${payer.email}
        </atlas-table-col>
        <atlas-table-col>
            ${formatterTagLib.dateTime(date: payer.dateCreated)}
        </atlas-table-col>
        <atlas-button-group slot="actions" group-after="2">
            <atlas-icon-button
                    data-action="edit"
                    tooltip="Editar pagador"
                    icon="pencil"
                    theme="primary"
                    description="Editar pagador">
            </atlas-icon-button>
            <g:if test="${payer.deleted}">
                <atlas-icon-button
                        data-action="restore"
                        tooltip="Restaurar pagador"
                        icon="refresh"
                        theme="warning"
                        description="Restaurar pagador">
                </atlas-icon-button>
            </g:if>
            <g:else>
                <atlas-icon-button
                        data-action="delete"
                        tooltip="Remover pagador"
                        icon="trash"
                        theme="danger"
                        description="Remover pagador">
                </atlas-icon-button>
            </g:else>
        </atlas-button-group>
    </atlas-table-row>
</g:each>