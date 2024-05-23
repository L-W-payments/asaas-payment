<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta name="layout" content="main" />
    <title>Pagadores - Asaas</title>
</head>

<body page-title="Cadastro do pagador" >
<atlas-form-panel header="Dados de ${payer.name}" class="js-person-form" action="${createLink(controller: 'payer', action: 'update', id: payer.id)}">

    <atlas-button slot="actions" data-panel-start-editing icon="pencil" description="Editar"></atlas-button>
    <atlas-button type="outlined" slot="actions" href="${createLink(controller: 'payer', action: 'delete', id: payer.id)}"
                  description="Apagar"></atlas-button>
    <atlas-input hidden name="customerId" value="${payer.customerId}"></atlas-input>
    <atlas-input hidden name="country" value="Brasil"></atlas-input>
        <g:render template="/templates/basePersonForm"
                  model="${[person: payer]}"></g:render>

</atlas-form-panel>
</body>

</html>