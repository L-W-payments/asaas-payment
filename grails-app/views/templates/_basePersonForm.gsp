<%@ page import="com.miniasaaslw.utils.entity.enums.PersonType" %>

<atlas-section header="${ tittle }">
  <atlas-grid>
    <atlas-row>
      <atlas-col>
        <atlas-input label="Nome" name="name" required="true" placeholder="Maria Bernadete">
        </atlas-input>
      </atlas-col>
    </atlas-row>
    <atlas-row>
      <atlas-col>
        <atlas-masked-input label="Email" name="email" mask-alias="email" required="true"
                            placeholder="maria.bernadete@asaas.com.br">
        </atlas-masked-input>
      </atlas-col>
    </atlas-row>
    <atlas-row>
      <atlas-col>
        <atlas-layout gap="3">
          <atlas-text>Tipo de pessoa</atlas-text>
          <atlas-toggle>
            <g:each var="personType" in="${ PersonType.getPersonTypeList() }">
              <atlas-toggle-item
                      value="${personType}"
                      label="${message(code: "personType.${personType}.label")}"
                ${personType==PersonType.NATURAL ? 'checked' : '' }
              ></atlas-toggle-item>
            </g:each>
          </atlas-toggle>
        </atlas-layout>
      </atlas-col>
    </atlas-row>
    <atlas-row>
      <atlas-col lg="6" md="6">
        <atlas-masked-input label="CPF ou CNPJ" name="cpfCnpj" mask-alias="cpf-cnpj"
                            required="true">
        </atlas-masked-input>
      </atlas-col>
      <atlas-col lg="6" md="6">
        <atlas-masked-input label="Celular" name="phone" mask-alias="cell-phone"
                            required="true">
        </atlas-masked-input>
      </atlas-col>
    </atlas-row>
  </atlas-grid>
</atlas-section>
<atlas-section header="Endereço">
  <atlas-grid>
    <atlas-row>
      <atlas-col lg="4" md="4">
        <atlas-postal-code label="CEP" disable-search></atlas-postal-code>
      </atlas-col>
      <atlas-col lg="4" md="4">
        <atlas-input label="Estado" id="state" name="state" required="true" placeholder="SC"
                     maxlength="2">
        </atlas-input>
      </atlas-col>
      <atlas-col lg="4" md="4">
        <atlas-input label="Cidade" id="city" name="city" required="true"
                     placeholder="Joinville">
        </atlas-input>
      </atlas-col>
    </atlas-row>
    <atlas-row>
      <atlas-col lg="4" md="4">
        <atlas-input label="Bairro" id="district" name="district" required="true"
                     placeholder="Bom Retiro">
        </atlas-input>
      </atlas-col>
      <atlas-col lg="4" md="4">
        <atlas-input label="Número" id="number" name="number" required="true"
                     placeholder="A2024">
        </atlas-input>
      </atlas-col>
      <atlas-col lg="4" md="4">
        <atlas-input label="Complemento" id="complement" name="complement"
                     placeholder="Lado ímpar">
        </atlas-input>
      </atlas-col>
    </atlas-row>
    <atlas-row>
      <atlas-col>
        <atlas-input label="Rua" id="street" name="street" required="true"
                     placeholder="Avenida Rolf Wiest">
        </atlas-input>
      </atlas-col>
    </atlas-row>
  </atlas-grid>
</atlas-section>