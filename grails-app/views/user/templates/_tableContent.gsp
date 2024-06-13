<g:each var="user" in="${userList}">
    <atlas-table-row
            href="${createLink(controller: "user", action: "show", id: user.id)}"
            data-action-url="${createLink(controller: "user", action: "${!user.enabled ? 'restore' : 'delete'}", id: user.id)}">
        <atlas-table-col>
            ${user.email}
        </atlas-table-col>
        <atlas-table-col>
            ${formatterTagLib.dateTime(date: user.dateCreated)}
        </atlas-table-col>
        <atlas-button-group slot="actions" group-after="2">
            <atlas-icon-button
                    data-action="edit"
                    tooltip="Editar usuário"
                    icon="pencil"
                    theme="primary"
                    description="Editar usuário">
            </atlas-icon-button>
            <g:if test="${user.enabled}">
                <atlas-icon-button
                        data-action="delete"
                        tooltip="Remover usuário"
                        icon="trash"
                        theme="danger"
                        description="Remover usuário">
                </atlas-icon-button>
            </g:if>
            <g:else>
                <atlas-icon-button
                        data-action="restore"
                        tooltip="Restaurar usuário"
                        icon="refresh"
                        theme="warning"
                        description="Restaurar usuário">
                </atlas-icon-button>
            </g:else>
        </atlas-button-group>
    </atlas-table-row>
</g:each>