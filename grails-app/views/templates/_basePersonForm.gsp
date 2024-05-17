<%@ page import="com.miniasaaslw.entity.enums.PersonType" %>

<atlas-section header="${ tittle }">
    <atlas-grid>
        <atlas-row>
            <atlas-col>
                <atlas-input label="Nome" name="name" required="true" placeholder="Maria Bernadete" value="${person?.name}">
                </atlas-input>
            </atlas-col>
        </atlas-row>
        <atlas-row>
            <atlas-col>
                <atlas-masked-input label="Email" name="email" mask-alias="email" required="true"
                                    placeholder="maria.bernadete@asaas.com.br" value="${person?.email}">
                </atlas-masked-input>
            </atlas-col>
        </atlas-row>
        <atlas-row>
            <atlas-col>
                <atlas-layout gap="3">
                    <atlas-text>Tipo de pessoa</atlas-text>
                    <atlas-toggle name="personType">
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
                                    required="true" value="${person?.cpfCnpj}">
                </atlas-masked-input>
            </atlas-col>
            <atlas-col lg="6" md="6">
                <atlas-masked-input label="Celular" name="phone" mask-alias="cell-phone"
                                    required="true" value="${person?.phone}">
                </atlas-masked-input>
            </atlas-col>
        </atlas-row>
    </atlas-grid>
</atlas-section>
<atlas-section header="Endereço">
    <atlas-grid>
        <atlas-row>
            <atlas-col lg="4" md="4">
                <atlas-postal-code name="cep" label="CEP" disable-search value="${person?.cep}"></atlas-postal-code>
            </atlas-col>
            <atlas-col lg="4" md="4">
                <atlas-input label="Estado" id="state" name="state" required="true" placeholder="SC"
                             maxlength="2" value="${person?.state}">
                </atlas-input>
            </atlas-col>
            <atlas-col lg="4" md="4">
                <atlas-input label="Cidade" id="city" name="city" required="true"
                             placeholder="Joinville" value="${person?.city}">
                </atlas-input>
            </atlas-col>
        </atlas-row>
        <atlas-row>
            <atlas-col lg="4" md="4">
                <atlas-input label="Bairro" id="district" name="district" required="true"
                             placeholder="Bom Retiro" value="${person?.district}">
                </atlas-input>
            </atlas-col>
            <atlas-col lg="4" md="4">
                <atlas-input label="Número" id="number" name="number" required="true"
                             placeholder="A2024" value="${person?.number}">
                </atlas-input>
            </atlas-col>
            <atlas-col lg="4" md="4">
                <atlas-input label="Complemento" id="complement" name="complement"
                             placeholder="Lado ímpar" value="${person?.complement}">
                </atlas-input>
            </atlas-col>
        </atlas-row>
        <atlas-row>
            <atlas-col>
                <atlas-input label="Rua" id="street" name="street" required="true"
                             placeholder="Avenida Rolf Wiest" value="${person?.street}">
                </atlas-input>
            </atlas-col>
        </atlas-row>
    </atlas-grid>
</atlas-section>
<asset:javascript src="PersonController.js"/>