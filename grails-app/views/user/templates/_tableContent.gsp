<g:each var="user" in="${userList}">
    <atlas-table-row
            href="${createLink(controller: "user", action: "show", id: user.id)}"
            data-action-url="${createLink(controller: "user", action: "delete", id: user.id)}">
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
            <atlas-icon-button
                    data-action="delete"
                    tooltip="Remover usuário"
                    icon="trash"
                    theme="danger"
                    description="Remover pagador">
            </atlas-icon-button>
        </atlas-button-group>
    </atlas-table-row>
</g:each>