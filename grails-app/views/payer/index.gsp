<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <title>Registrar Pagador</title>

<body>
<form action="${createLink(controller: "payer", action: "save")}" method="POST">
  <h1>Registrar novo pagador</h1>
  <div>
    <label for="name">Nome</label>
    <br>
    <input type="text" id="name" name="name" placeholder="Insira o Nome"/>
  </div>
  <div>
    <label for="email">Email</label>
    <br>
    <input type="text" id="email" name="email" placeholder="Insira o email">
  </div>

  <div>
    <label for="phone">Telefone</label>
    <br>
    <input type="text" id="phone" name="phone" placeholder="Insira o telefone" minlength="10" maxlength="11">
  </div>

  <div>
    <label for="personType">Pessoa física ou juridica?</label>
    <br>
    <select id="personType" name="personType">
      <option value="fisica">Física</option>
      <option value="juridica">Jurídica</option>
    </select>
  </div>
  <div>
    <label for="cpfCnpj">Cpf/Cnpj</label>
    <br>
    <input type="text" id="cpfCnpj" name="cpfCnpj" placeholder="Insira o cpf/cnpj" minlength="11" maxlength="14">
  </div>

  <button type="submit">Enviar</button>
</form>
</body>
</html>