<g:each var="payment" in="${paymentList}">
    <atlas-table-row
            data-delete-url="${createLink(controller: 'payment', action: 'delete', id: payment.id)}"
    >
        <atlas-table-col>
            ${payment.payer.name}
        </atlas-table-col>
        <atlas-table-col>
            ${formatNumber(number: payment.value, type: "currency", locale: "pt_BR")}
        </atlas-table-col>
        <atlas-table-col>
            ${message(code: "paymentType.${payment.paymentType}.label")}
        </atlas-table-col>
        <atlas-table-col>
            ${message(code: "paymentStatus.${payment.paymentStatus}.label")}
        </atlas-table-col>
        <atlas-table-col>
            ${dateTimeTagLib.dateTime(date: payment.dueDate)}
        </atlas-table-col>
        <atlas-button-group slot="actions" group-after="2">
            <g:if test="${payment.deleted}">
                <atlas-icon-button
                        data-action=""
                        tooltip="Restaurar cobrança"
                        icon="refresh"
                        theme="warning"
                        description="Restaurar cobrança">
                </atlas-icon-button>
            </g:if>
            <g:else>
            <atlas-icon-button
                    data-action="delete"
                    tooltip="Remover cobrança"
                    icon="trash"
                    theme="danger"
                    description="Remover cobrança">
            </atlas-icon-button>
            </g:else>
        </atlas-button-group>
    </atlas-table-row>
</g:each>