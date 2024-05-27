<g:each var="payment" in="${paymentList}">
    <atlas-table-row
            data-delete-url="${createLink(controller: 'payment', action: 'fetchDelete', id: payment.getId())}"
    >
        <atlas-table-col>
            ${payment.getPayer().getName()}
        </atlas-table-col>
        <atlas-table-col>
            ${formatNumber(number: payment.getValue(), type: "currency", locale: "pt_BR")}
        </atlas-table-col>
        <atlas-table-col>
            ${message(code: "paymentType.${payment.getPaymentType()}.label")}
        </atlas-table-col>
        <atlas-table-col>
            ${message(code: "paymentStatus.${payment.getPaymentStatus()}.label")}
        </atlas-table-col>
        <atlas-table-col>
            <g:formatDate date="${payment.getDueDate()}" format="dd/MM/yyyy"/>
        </atlas-table-col>
        <atlas-button-group slot="actions" group-after="2">
            <atlas-icon-button
                    data-action="delete"
                    tooltip="Remover cobrança"
                    icon="trash"
                    theme="danger"
                    description="Remover cobrança">
            </atlas-icon-button>
        </atlas-button-group>
    </atlas-table-row>
</g:each>