<html>
<head>
    <title>Comprovante de pagamento - Asaas</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
    <asset:stylesheet src="new-theme.scss"/>
    <asset:stylesheet src="receipt.scss"/>

    <link rel="stylesheet" href="https://atlas.asaas.com/v15.18.0/atlas.min.css" crossorigin="anonymous">
    <script defer src="https://atlas.asaas.com/v15.18.0/atlas.min.js" crossorigin="anonymous"></script>
</head>

<body>
    <atlas-screen>
        <atlas-page>
            <atlas-page-content slot="content" class="js-atlas-content">
                <atlas-grid container style="max-width: 1000px; margin: 0 auto">
                    <atlas-layout alignment="center">
                        <atlas-image src="https://atlas.asaas.com/assets/images/logos/asaas-logo.7POEJYYY.svg"
                                     height="100" style="margin-top: 16px; margin-bottom: 16px"></atlas-image>
                    </atlas-layout>

                    <g:render template="/paymentReceipt/templates/receipt" model="${[receipt: receipt]}"/>

                    <atlas-layout alignment="bottom">
                        <atlas-button class="js-download-button" description="Baixar como PDF"></atlas-button>
                    </atlas-layout>
                </atlas-grid>
            </atlas-page-content>
        </atlas-page>
    </atlas-screen>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
    <asset:javascript src="PaymentReceiptController.js"/>
</body>
</html>