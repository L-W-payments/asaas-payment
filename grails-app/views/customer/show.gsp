<html>
<head>
    <title>Cliente</title>
</head>
<body>
    <form action="${createLink(controller: 'customer', action: 'update')}" method="POST">
    <input type="hidden" name="id" value="${customer.id}">
      <g:render template="/layouts/basePersonForm" model="${[person: customer]}"/>
      <button value="submit">Salvar</button>
    </form>
</body>
</html>