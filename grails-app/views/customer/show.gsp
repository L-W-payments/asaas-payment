<html>
<head>
    <meta name="layout" content="main"/>
    <title>Cliente - Asaas</title>
</head>

<body page-title="Dados do cliente">
    <g:if test="${messageInfo}">
        <g:render template="/utils/messages" model="${messageInfo}"/>
    </g:if>

    <atlas-form-panel
            header="Seus dados"
            class="js-person-form"
            action="${createLink(controller: 'customer', action: 'update')}">
        <atlas-button slot="actions" data-panel-start-editing icon="pencil" description="Editar"></atlas-button>

        <atlas-input hidden name="country" value="Brasil"></atlas-input>

        <g:render template="/templates/basePersonForm" model="${[person: customer, isCustomer: false]}"/>
    </atlas-form-panel>

    <asset:javascript src="CustomerController.js"/>
</body>
</html>