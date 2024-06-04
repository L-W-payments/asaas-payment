<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta name="layout" content="main"/>
    <title>Pagadores - Asaas</title>
</head>

<body page-title="Cadastro de Pagador">
<atlas-panel class="js-person-form">
    <g:if test="${messageInfo}">
        <g:render template="/utils/messages" model="${messageInfo}"/>
    </g:if>
    <atlas-form action="${createLink(controller: 'payer', action: 'save')}">
        <atlas-section header="Cliente">
            <atlas-select name="customerId" label="Selecione um cliente" placeholder="Selecione uma opção" required>
                <g:each var="customer" in="${customers}">
                    <atlas-option label="${customer.name}" value="${customer.id}"></atlas-option>
                </g:each>
            </atlas-select>
        </atlas-section>
        <atlas-input hidden name="country" value="Brasil"></atlas-input>
        <g:render template="/templates/basePersonForm"
                  model="${[title: "Cadastro de Pagador"]}"></g:render>
        <atlas-button submit description="Salvar"></atlas-button>
    </atlas-form>
</atlas-panel>

</body>
</html>