<%
    String columnsAttribute = tableTagLib.buildAtlasTable(attribute: "columns", value: [
            ["name": "email", "label": "E-mail", "size": "md", "ellipsis": "true"],
            ["name": "dateCreated", "label": "Data de criação", "size": "md"]

    ])
%>
<atlas-easy-table
    ${raw(columnsAttribute)}
        class="js-user-list-table"
        url="${createLink(controller: 'user', action: 'loadTableContent')}"
        total-records="${userList.totalCount}"
        current-page="1"
        has-actions>

    <g:render template="/user/templates/tableContent"/>

    <atlas-empty-state
            illustration="telescope-files-stars"
            header="Nenhum usuário foi encontrado"
            slot="empty-state-template">
        <atlas-text>Não foi possível encontrar nada! Tente novamente</atlas-text>
    </atlas-empty-state>
</atlas-easy-table>