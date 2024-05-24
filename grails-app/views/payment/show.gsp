<%@ page import="com.miniasaaslw.entity.enums.payment.PaymentStatus" %>

<html>
<head>
    <title>Pagamento - Asaas</title>

    <asset:stylesheet src="new-theme.scss"/>

    <link rel="stylesheet" href="https://atlas.asaas.com/v15.18.0/atlas.min.css" crossorigin="anonymous">
    <script defer src="https://atlas.asaas.com/v15.18.0/atlas.min.js" crossorigin="anonymous"></script>
</head>

<body>
<atlas-screen>
    <atlas-page>
        <atlas-page-content slot="content" class="js-atlas-content">
            <atlas-grid container>
                <atlas-layout alignment="center">
                    <atlas-image src="https://atlas.asaas.com/assets/images/logos/asaas-logo.7POEJYYY.svg"
                                 height="100" style="margin-top: 16px; margin-bottom: 32px"></atlas-image>
                </atlas-layout>

                <atlas-panel header="Informações da cobrança">
                    <atlas-text slot="actions" muted>
                        Rastreabilidade #${payment.getId()}
                    </atlas-text>

                    <atlas-layout justify="space-between" inline>
                        <atlas-summary-item
                                label="Data de vencimento"
                                description="${formatDate(date: payment.getDueDate(), format: 'dd/MM/yyyy')}"></atlas-summary-item>

                        <atlas-summary-item
                                label="Situação"
                                description="${message(code: "paymentStatus.${payment.getPaymentStatus()}.label")}"></atlas-summary-item>

                        <atlas-summary-item
                                label="Método de pagamento"
                                description="${message(code: "paymentType.${payment.getPaymentType()}.label")}"></atlas-summary-item>

                        <atlas-summary-item
                                label="Destinatário"
                                description="${payment.getCustomer().getName()}"></atlas-summary-item>
                    </atlas-layout>

                    <atlas-summary-item
                            label="Descrição"
                            description="${payment.getDescription() ?: 'Seu fornecedor não informou descrição para este serviço/produto.'}">
                    </atlas-summary-item>

                    <atlas-divider spacing="16"></atlas-divider>

                    <atlas-summary-item
                            label="Valor total"
                            description="${formatNumber(number: payment.getValue(), type: 'currency', locale: 'pt_BR')}"
                            horizontal></atlas-summary-item>

                    <atlas-divider spacing="16"></atlas-divider>


                    <form action="${createLink(controller: 'payment', action: 'pay')}">
                        <g:if test="${payment.getPaymentStatus() == PaymentStatus.PENDING}">
                            <atlas-input hidden name="id" value="${payment.getId()}"></atlas-input>
                            <atlas-button description="Pagar" theme="success" submit block></atlas-button>
                        </g:if>
                        <g:else>
                            <atlas-button
                                    disabled
                                    description="${payment.getPaymentStatus() == PaymentStatus.APPROVED ? 'Fatura paga' : 'Fatura expirada'}"
                                    theme="${payment.getPaymentStatus() == PaymentStatus.APPROVED ? 'highlight' : 'danger'}"
                                    block></atlas-button>
                        </g:else>
                    </form>
                </atlas-panel>
            </atlas-grid>
        </atlas-page-content>
    </atlas-page>
</atlas-screen>
</body>
</html>