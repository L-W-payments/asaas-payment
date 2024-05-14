<%@ page import="com.miniasaaslw.utils.entity.enums.PersonType" %>

<h2>Dados Pessoais</h2>

<div>
    <label for="name">Nome</label>
    <br>
    <input type="text" id="name" name="name" placeholder="Insira o nome" value="${person?.name ?: ''}">
</div>

<div>
    <label for="email">Email</label>
    <br>
    <input type="text" id="email" name="email" placeholder="Insira o email" value="${person?.email ?: ''}">
</div>

<div>
    <label for="phone">Telefone</label>
    <br>
    <input type="text" id="phone" name="phone" placeholder="Insira o telefone" minlength="10" maxlength="11"
           value="${person?.phone ?: ''}">
</div>

<div>
    <label for="personType">Pessoa física ou jurídica?</label>
    <br>
    <select id="personType" name="personType">
        <option value="natural" ${person?.personType == PersonType.NATURAL ? 'selected' : ''}>Física</option>
        <option value="legal" ${person?.personType == PersonType.LEGAL ? 'selected' : ''}>Jurídica</option>
    </select>
</div>

<div>
    <label for="cpfCnpj">Cpf/Cnpj</label>
    <br>
    <input type="text" id="cpfCnpj" name="cpfCnpj" placeholder="Insira o cpf/cnpj" minlength="11" maxlength="14"
           value="${person?.cpfCnpj ?: ''}">
</div>

<h2>Endereço</h2>

<div>
    <label for="cep">CEP</label>
    <br>
    <input type="text" id="cep" name="cep" placeholder="Insira o cep" value="${person?.cep ?: ''}">
</div>

<div>
    <label for="country">País</label>
    <br>
    <input type="text" id="country" name="country" placeholder="Insira o país" value="${person?.country ?: ''}">
</div>

<div>
    <label for="state">Estado</label>
    <br>
    <input type="text" id="state" name="state" placeholder="Insira o estado" value="${person?.state ?: ''}">
</div>

<div>
    <label for="city">Cidade</label>
    <br>
    <input type="text" id="city" name="city" placeholder="Insira a cidade" value="${person?.city ?: ''}">
</div>

<div>
    <label for="district">Bairro</label>
    <br>
    <input type="text" id="district" name="district" placeholder="Insira o bairro" value="${person?.district ?: ''}">
</div>

<div>
    <label for="street">Rua</label>
    <br>
    <input type="text" id="street" name="street" placeholder="Insira a rua" value="${person?.street ?: ''}">
</div>

<div>
    <label for="number">Número</label>
    <br>
    <input type="text" id="number" name="number" placeholder="Insira o número" value="${person?.number ?: ''}">
</div>

<div>
    <label for="complement">Complemento</label>
    <br>
    <input type="text" id="complement" name="complement" placeholder="Insira o complemento"
           value="${person?.complement ?: ''}">
</div>