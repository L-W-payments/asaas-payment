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

                    <atlas-summary-card header="Informações da cobrança">
                        <atlas-layout justify="space-between" inline>
                            <atlas-summary-item
                                    label="Data de vencimento"
                                    description="${formatDate(date: payment.dueDate, format: 'dd/MM/yyyy')}"></atlas-summary-item>

                            <atlas-summary-item
                                    label="Situação"
                                    description="${message(code: "paymentStatus.${payment.paymentStatus}.label")}">
                            </atlas-summary-item>

                            <atlas-summary-item
                                    label="Forma de pagamento"
                                    description="${message(code: "paymentType.${payment.paymentType}.label")}">
                            </atlas-summary-item>

                            <atlas-summary-item
                                    label="Destinatário"
                                    description="${payment.customer.name}">
                            </atlas-summary-item>
                        </atlas-layout>

                        <atlas-divider spacing="32"></atlas-divider>

                        <atlas-summary-item
                                label="Valor total"
                                description="${formatNumber(number: payment.value, type: 'currency', locale: 'pt_BR')}" horizontal>
                        </atlas-summary-item>

                        <atlas-divider spacing="32"></atlas-divider>

                        <atlas-form action="">
                            <atlas-button description="Pagar" theme="success" block></atlas-button>
                        </atlas-form>
                    </atlas-summary-card>
                </atlas-grid>
            </atlas-page-content>
        </atlas-page>
    </atlas-screen>
    </body>
</html>