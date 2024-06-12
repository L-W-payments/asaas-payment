<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta name="layout" content="main"/>
    <title>Pagador - Asaas</title>
    <asset:javascript src="PayerController.js"/>
</head>

<body page-title="Visualizar pagador">
    <g:if test="${messageInfo}">
        <g:render template="/utils/messages" model="${messageInfo}"/>
    </g:if>

    <atlas-form-panel header="Dados de ${payer.name}" class="js-person-form"
                      action="${createLink(controller: 'payer', action: 'update', id: payer.id)}">

        <atlas-button slot="actions" data-panel-start-editing icon="pencil" description="Editar"></atlas-button>
        <atlas-button slot="actions"
                      class="js-delete-button"
                      data-action="delete"
                      data-delete-url="${createLink(controller: 'payer', action: 'delete', id: payer.id)}"
                      data-redirect-url="${createLink(controller: 'payer', action: 'list')}"
                      type="outlined"
                      description="Apagar"></atlas-button>

        <atlas-input hidden name="customerId" value="${payer.customerId}"></atlas-input>
        <atlas-input hidden name="country" value="Brasil"></atlas-input>

        <g:render template="/templates/basePersonForm" model="${[person: payer]}"/>
    </atlas-form-panel>
</body>

</html>