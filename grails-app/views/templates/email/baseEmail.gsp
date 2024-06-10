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

    .footer {
        background-color: #f9f9f9;
        text-align: center;
        padding: 20px;
        border-top: 1px solid #ddd;
    }

    .footer .info {
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
        <img src="https://ci3.googleusercontent.com/meips/ADKq_Na3rzr-X1iaRzx_vziBtmsyPBx2XQuka7kT_QAZiuufZ1a13igtStihxTU84j6MvdLhEBJCNfpJuFVgsjSz5mCpuu1HeVRcwnro155iKLwX9qup-Q7bO2XZY6jvkLMK4ZX7rhIh4DkUEEx7IKia5A=s0-d-e1-ft#https://www.asaas.com/assets/emails/asaas-name-white-11af35809d5ae4dcd235671ff4bf79e1.png" crossorigin="anonymous" alt="Asaas Logo">
        <h1>${emailNotification.subject}</h1>
    </div>

    <div class="content">
        <p>${emailNotification.body}</p>
        <p>Para visualizar o pagamento em questão <a href="${url}">clique aqui</a>.</p>
    </div>
    <div class="footer">
        <p>Abraços, Equipe Asaas 💙</p>
        <p class="info">Enviado por <strong>L&W Payments - ASAAS Payment.</strong></p>
    </div>
</div>
</body>
</html>
