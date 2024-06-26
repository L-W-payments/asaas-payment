<%@ page import="com.miniasaaslw.entity.enums.payment.PaymentStatus" %>

<html>
<head>
    <title>Pagamento - Asaas</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>

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
                            <g:if test="${paymentReceiptId}">
                                <atlas-button is-external-link
                                              icon="arrow-up-right"
                                              href="/receipt/${paymentReceiptId}"
                                              description="Comprovante de pagamento"
                                              type="ghost"
                                              theme="highlight"
                                              slot="actions">
                                </atlas-button>
                            </g:if>
                        <atlas-layout justify="space-between" inline>
                            <atlas-summary-item
                                    label="Data de vencimento"
                                    description="${formatterTagLib.dateTime(date: payment.dateCreated)}"></atlas-summary-item>

                            <atlas-summary-item
                                    label="Situação"
                                    description="${message(code: "paymentStatus.${payment.paymentStatus}.label")}"></atlas-summary-item>

                            <atlas-summary-item
                                    label="Método de pagamento"
                                    description="${message(code: "billingType.${payment.billingType}.label")}"></atlas-summary-item>

                            <atlas-summary-item
                                    label="Destinatário"
                                    description="${payment.customer.name}"></atlas-summary-item>
                        </atlas-layout>

                        <atlas-summary-item
                                label="Descrição"
                                description="${payment.description ?: 'Seu fornecedor não informou descrição para este serviço/produto.'}"></atlas-summary-item>

                        <atlas-divider spacing="16"></atlas-divider>

                        <atlas-summary-item
                                label="Valor total"
                                description="${formatNumber(number: payment.value, type: 'currency', locale: 'pt_BR')}"
                                horizontal></atlas-summary-item>

                        <atlas-divider spacing="16"></atlas-divider>

                        <atlas-form action="${createLink(controller: 'payment', action: 'updateToReceived')}">
                            <g:if test="${payment.paymentStatus.isPending() || payment.paymentStatus.isOverdue()}">
                                <atlas-input hidden name="id" value="${payment.id}"></atlas-input>
                                <atlas-input hidden name="publicId" value="${payment.publicId}"></atlas-input>
                                <atlas-button description="Pagar" theme="success" submit block></atlas-button>
                            </g:if>
                            <g:else>
                                <atlas-button
                                        disabled
                                        icon="celebrate"
                                        description="Fatura paga"
                                        theme="highlight"
                                        block></atlas-button>
                            </g:else>
                        </atlas-form>
                    </atlas-panel>
                </atlas-grid>
            </atlas-page-content>
        </atlas-page>
    </atlas-screen>
</body>
</html>