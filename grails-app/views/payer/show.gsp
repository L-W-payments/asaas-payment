<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <title>Dados do Pagador</title>
</head>

<body>
<h1>Dados de ${payer.name}</h1>

<form action="${createLink(controller: 'payer', action: 'update')}" method="POST">
    <input type="hidden" name="id" value="${payer.id}"/>

    <g:render template="/layouts/basePersonForm" model="${[person: payer]}"/>
    <button type="submit">Salvar</button>
</form>
</body>

</html>