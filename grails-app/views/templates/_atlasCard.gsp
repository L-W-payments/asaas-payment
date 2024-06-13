<g:each in="${cardData}" var="card">
    <atlas-card header="${card.title}">
        <atlas-layout gap="6">
            <atlas-heading theme="${card.theme}">
                <atlas-private-content placeholder-prefix="R$">${formatNumber(number: card.total ?: BigDecimal.ZERO , type: "currency", locale: "pt_BR")}</atlas-private-content>
            </atlas-heading>
        </atlas-layout>
        <atlas-card-button slot="actions" theme="${card.theme}" description="${card.totalPayers} pagadores" icon="user" disabled></atlas-card-button>
        <atlas-card-button slot="actions" theme="${card.theme}" description="${card.totalPayments} cobranças" icon="money" disabled></atlas-card-button>
    </atlas-card>
</g:each>
