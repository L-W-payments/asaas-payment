<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <title>Registrar Pagador</title>
</head>

<body>
    <h1>Registrar Pagador</h1>

    <form action="${createLink(controller: 'payer', action: 'save')}" method="POST">
        <g:render template="/layouts/basePersonForm" />
        <button type="submit">Enviar</button>
    </form>
</body>

</html>