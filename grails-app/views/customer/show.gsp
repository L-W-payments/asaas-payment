<html>
<head>
    <title>Cliente</title>
</head>
<body>
    <form action="">
      <g:render template="/layouts/basePersonForm" model="${[person: customer]}"/>
      <button value="submit">Salvar</button>
    </form>
</body>
</html>