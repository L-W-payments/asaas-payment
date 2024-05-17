<html>
<head>
    <meta name="layout" content="main" />
    <title>Cliente - asaas</title>
</head>
<body page-title="Dados do cliente">
<atlas-form-panel header="Seus dados" class="js-person-form" action="${createLink(controller: 'customer', action: 'update', id: customer.id)}">

    <atlas-button slot="actions" data-panel-start-editing icon="pencil" description="Editar"></atlas-button>
    <atlas-button type="outlined" slot="actions" href="${createLink(controller: 'customer', action: 'delete', id: customer.id)}"
                  description="Apagar"></atlas-button>
    <atlas-input hidden name="country" value="Brasil"></atlas-input>
    <g:render template="/templates/basePersonForm"
              model="${[person: customer]}"></g:render>

</atlas-form-panel>
</body>
</html>