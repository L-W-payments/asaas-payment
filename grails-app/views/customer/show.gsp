<html>
<head>
    <title>Cliente</title>
</head>
<body>
    <g:if test="errors">
        <div>
            <g:each var="error" in="${errors}">
                <p>${error}</p>
            </g:each>
        </div>
    </g:if>
    <form action="${createLink(controller: 'customer', action: 'update')}" method="POST">
    <input type="hidden" name="id" value="${customer.id}">
      <g:render template="/templates/basePersonForm" model="${[person: customer]}"/>
      <button value="submit">Salvar</button>
    </form>
    <a href="${createLink(controller: 'customer', action: 'delete', id: customer.id)}">Apagar</a>

</body>
</html>