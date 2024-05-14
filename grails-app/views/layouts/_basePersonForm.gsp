<h2>Dados Pessoais</h2>
<div>
  <label for="name">Nome</label>
  <br>
  <input type="text" id="name" name="name" placeholder="Insira o nome" ${person ? 'value=' + person.name : ''}>
</div>

<div>
  <label for="email">Email</label>
  <br>
  <input type="text" id="email" name="email" placeholder="Insira o email" ${person ? 'value=' + person.email : ''}>
</div>

<div>
  <label for="phone">Telefone</label>
  <br>
  <input type="text" id="phone" name="phone" placeholder="Insira o telefone" minlength="10" maxlength="11" ${person ? 'value=' + person.phone : ''}>
</div>

<div>
  <label for="personType">Pessoa física ou jurídica?</label>
  <br>
  <select id="personType" name="personType" ${person ? 'value=' + person.personType : ''}>
    <option value="natural">Física</option>
    <option value="legal">Jurídica</option>
  </select>
</div>

<div>
  <label for="cpfCnpj">Cpf/Cnpj</label>
  <br>
  <input type="text" id="cpfCnpj" name="cpfCnpj" placeholder="Insira o cpf/cnpj" minlength="11" maxlength="14" ${person ? 'value=' + person.cpfCnpj : ''}>
</div>

<h2>Endereço</h2>

<div>
  <label for="cep">CEP</label>
  <br>
  <input type="text" id="cep" name="cep" placeholder="Insira o cep" ${person ? 'value=' + person.cep : ''}>
</div>

<div>
  <label for="country">País</label>
  <br>
  <input type="text" id="country" name="country" placeholder="Insira o país" ${person ? 'value=' + person.country : ''}>
</div>

<div>
  <label for="state">Estado</label>
  <br>
  <input type="text" id="state" name="state" placeholder="Insira o estado" ${person ? 'value=' + person.state : ''}>
</div>

<div>
  <label for="city">Cidade</label>
  <br>
  <input type="text" id="city" name="city" placeholder="Insira a cidade" ${person ? 'value=' + person.city : ''}>
</div>

<div>
  <label for="district">Bairro</label>
  <br>
  <input type="text" id="district" name="district" placeholder="Insira o bairro" ${person ? 'value=' + person.district : ''}>
</div>

<div>
  <label for="street">Rua</label>
  <br>
  <input type="text" id="street" name="street" placeholder="Insira a rua" ${person ? 'value=' + person.street : ''}>
</div>

<div>
  <label for="number">Número</label>
  <br>
  <input type="text" id="number" name="number" placeholder="Insira o número" ${person ? 'value=' + person.number : ''}>
</div>

<div>
  <label for="complement">Complemento</label>
  <br>
  <input type="text" id="complement" name="complement" placeholder="Insira o complemento" ${person ? 'value=' + (person.complement ?:'') : ''}>
</div>