<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <title>Registrar Cliente</title>

<body>
<form action="${createLink(controller: "customer", action: "save")}" method="POST">
  <div>
    <label for="name">Nome</label>
    <br>
    <input type="text" id="name" name="name" placeholder="Insira seu nome"/>
  </div>
  <div>
    <label for="email">Email</label>
    <br>
    <input type="text" id="email" name="email" placeholder="Insira seu email">
  </div>

  <div>
    <label for="phone">Telefone</label>
    <br>
    <input type="text" id="phone" name="phone" placeholder="Insira seu telefone" minlength="10" maxlength="11">
  </div>

  <div>
    <label for="personType">Você é uma pessoa física ou juridica?</label>
    <br>
    <select id="personType" name="personType">
      <option value="cpf">Física</option>
      <option value="cnpj">Jurídica</option>
    </select>
  </div>
  <div>
    <label for="cpfCnpj">Cpf/Cnpj</label>
    <br>
    <input type="text" id="cpfCnpj" name="cpfCnpj" placeholder="Insira seu cpf/cnpj" minlength="11" maxlength="14">
  </div>

  <button type="submit">Enviar</button>
</form>
</body>
</html>