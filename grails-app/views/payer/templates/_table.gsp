<%
    String columnsAttribute = tableTagLib.buildAtlasTable(attribute: "columns", value: [
            ["name": "name", "label": "Pagador", "size": "md", "ellipsis": "true"],
            ["name": "email", "label": "E-mail", "size": "md", "ellipsis": "true"],
            ["name": "dateCreated", "label": "Data de criação", "size": "md"]

    ])
%>
<atlas-easy-table
    ${raw(columnsAttribute)}
        class="js-payer-list-table"
        url="${createLink(controller: 'payer', action: 'loadTableContent')}"
        total-records="${payerList.totalCount}"
        current-page="1"
        has-actions>
    <g:render template="/payer/templates/tableContent"/>
    <atlas-empty-state illustration="telescope-files-stars" header="Nenhum pagador foi encontrado"
                       slot="empty-state-template">
        <atlas-text>Não foi possível encontrar nada! Tente novamente</atlas-text>
    </atlas-empty-state>
</atlas-easy-table>