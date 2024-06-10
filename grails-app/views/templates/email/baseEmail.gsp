<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Notificação de Cobrança - Asaas</title>
    <style>
    body {
        margin: 0;
        padding: 0;
        background-color: #f5f5f5;
        color: #333;
    }

    .container {
        max-width: 600px;
        margin: 20px auto;
        background-color: #fff;
        border: 1px solid #ddd;
        border-radius: 8px;
        overflow: hidden;
    }

    .header {
        background-color: #0044cc;
        color: white;
        text-align: center;
        padding: 20px;
    }

    .header img {
        max-width: 100px;
        margin-bottom: 10px;
    }

    .header h1 {
        margin: 0;
        font-size: 20px;
    }

    .content {
        padding: 20px;
    }

    .content p {
        line-height: 1.6;
    }

    .content a {
        color: #0044cc;
        text-decoration: none;
    }

    .content a:hover {
        text-decoration: underline;
    }

    .footer {
        background-color: #f9f9f9;
        text-align: center;
        padding: 20px;
        border-top: 1px solid #ddd;
    }

    .footer .app-links img,
    .footer .social-links img {
        max-width: 100px;
        margin: 10px;
    }

    .footer .address {
        font-size: 12px;
        color: #666;
    }

    .footer strong {
        color: #333;
    }

    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <img src="https://atlas.asaas.com/assets/images/logos/asaas-logo.7POEJYYY.svg" alt="Asaas Logo">
        <h1>${emailNotification.subject}</h1>
    </div>

    <div class="content">
        <p>${emailNotification.body}</p>
        <p>Para visualizar o pagamento em questão <a href="${url}">clique aqui</a>.</p>
    </div>
    <div class="footer">
        <p>Abraços, Equipe Asaas 💙</p>
        <p class="address">Enviado por <strong>L&W Payments - ASAAS Payment. </strong><br>Av. Rolf Wiest, 277, Sl. 820 - Bom Retiro, Joinville - SC, 89223-005.</p>
    </div>
</div>
</body>
</html>
