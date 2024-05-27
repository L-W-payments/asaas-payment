<html>
<head>
  <title>Cobranças - Asaas</title>
  <meta name="layout" content="main">
</head>

<body page-title="Lista de cobranças">
<atlas-panel class="js-payment-panel">
  <g:if test="${paymentList}">
    <atlas-toolbar>
      <atlas-search-input class="js-payment-search-input" placeholder="Pesquisar" icon="magnifier"
                          slot="search"></atlas-search-input>
      <atlas-button href="/payment" description="Adicionar" icon="plus" slot="actions"></atlas-button>
    </atlas-toolbar>

    <g:render template="/payment/templates/table"/>
    <asset:javascript src="PaymentListController.js"/>
  </g:if>
  <g:else>
    <atlas-empty-state
            illustration="schedule-user-avatar"
            header="Nenhuma Cobrança Cadastrada">
      Aqui você pode cadastrar uma nova cobrança.

      <atlas-button
              icon="plus"
              description="Adicionar Cobrança"
              href="/payment"
              slot="button"></atlas-button>
    </atlas-empty-state>
  </g:else>
</atlas-panel>

</body>
</html>
