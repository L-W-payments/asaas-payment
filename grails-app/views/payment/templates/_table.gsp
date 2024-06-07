<%
    String columnsAttribute = tableTagLib.buildAtlasTable(attribute: "columns", value: [
            ["name": "name", "label": "Nome", "size": "md", "ellipsis": "true"],
            ["name": "value", "label": "Valor", "size": "md", "ellipsis": "true"],
            ["name": "billingType", "label": "Forma de Pagamento", "size": "md"],
            ["name": "paymentStatus", "label": "Situação do Pagamento", "size": "md"],
            ["name": "dueDate", "label": "Data de vencimento", "size": "md"]
    ])
%>
<atlas-easy-table
    ${raw(columnsAttribute)}
        class="js-payment-list-table"
        url="${createLink(controller: 'payment', action: 'loadTableContent')}"
        total-records="${paymentList.totalCount}"
        current-page="1"
        has-actions>
    <g:render template="/payment/templates/tableContent"/>
    <atlas-empty-state illustration="telescope-files-stars" header="Nenhuma cobrança foi encontrada"
                       slot="empty-state-template">
        <atlas-text>Não foi possível encontrar nada! Tente novamente</atlas-text>
    </atlas-empty-state>
</atlas-easy-table>