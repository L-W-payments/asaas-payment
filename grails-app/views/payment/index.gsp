<%@ page import="com.miniasaaslw.entity.enums.payment.BillingType" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta name="layout" content="main" />
  <title>Cobranças - Asaas</title>
</head>
<body page-title="Criar cobrança">
<atlas-panel>
  <g:if test="${messageInfo}">
    <g:render template="/utils/messages" model="${messageInfo}"/>
  </g:if>
      <atlas-section header="${payers ? 'Cadastro de Cobrança' : ''}">
        <g:if test="${payers}">

          <atlas-form action="${createLink(controller: 'payment', action: 'save' )}">
          <atlas-layout gap="4">
            <atlas-section header="Quem você vai cobrar?">
            <atlas-select name="payerId" label="Selecione um Pagador" placeholder="Selecione uma Opção" required>
              <g:each in="${payers}" var="payer">
                <atlas-option label="${payer.name} — ${payer.email}" value="${payer.id}"></atlas-option>
              </g:each>
            </atlas-select>
            </atlas-section>
            <atlas-section header="Dados da cobrança">
              <atlas-grid>
                <atlas-row>
                  <atlas-col lg="6" md="6">
                    <atlas-money label="Valor da cobrança" name="value" min-value="10" max-value="10000"
                                 min-value-error-message="O valor mínimo é de R$ 10,00"
                                 max-value-error-message="O valor máximo é de R$ 10.000,00" required maxlength="9"></atlas-money>
                  </atlas-col>
                  <atlas-col lg="6" md="6">
                    <atlas-datepicker name="dueDate" label="Vencimento da cobrança" prevent-past-date
                                      required></atlas-datepicker>
                  </atlas-col>
                </atlas-row>
              </atlas-grid>
            </atlas-section>
            <atlas-textarea name="description" label="Descrição da cobrança"
                            placeholder="Informações relevantes da cobrança." rows="3" maxlength="500"></atlas-textarea>
            <atlas-element-group required-fields="1" required-fields-error-message="Selecione 1 opção para continuar">
              <atlas-grid>
                <atlas-row>
                  <atlas-col lg="6" md="6">
                    <atlas-selection-card name="billingType" label="Pix" value="${BillingType.PIX}" collapsible type="radio">O pagamento realizado através do sistema Pix é creditado na conta do destinatário de forma imediata, proporcionando uma transferência instantânea e segura dos valores, sem a necessidade de aguardar prazos adicionais para a disponibilização dos recursos.</atlas-selection-card>
                  </atlas-col>
                  <atlas-col lg="6" md="6">
                    <atlas-selection-card name="billingType" label="Boleto" value="${BillingType.BANK_SLIP}" collapsible type="radio">O pagamento efetuado por meio de boleto bancário, após a sua quitação, pode levar até três dias úteis para ser processado e compensado, momento em que o valor será creditado na conta do destinatário, conforme os prazos estabelecidos pelas instituições financeiras.</atlas-selection-card>
                  </atlas-col>
                </atlas-row>
                <atlas-row>
                  <atlas-col lg="6" md="6">
                    <atlas-selection-card name="billingType" label="Cartão de Crédito" value="${BillingType.CREDIT_CARD}" collapsible type="radio">O pagamento realizado com cartão de crédito é processado pela administradora e o valor pode ser creditado na conta do destinatário em um prazo de 1 a 30 dias, conforme as condições acordadas entre as partes.</atlas-selection-card>
                  </atlas-col>
                  <atlas-col lg="6" md="6">
                    <atlas-selection-card name="billingType" label="Cartão de Débito" value="${BillingType.DEBIT_CARD}" collapsible type="radio">O pagamento efetuado com cartão de débito é processado imediatamente, com o valor sendo debitado diretamente da conta do pagador e creditado na conta do destinatário em tempo real ou dentro de alguns minutos, garantindo uma transferência rápida e segura dos fundos.</atlas-selection-card>
                  </atlas-col>
                </atlas-row>
              </atlas-grid>
            </atlas-element-group>
            <atlas-button submit description="Criar Cobrança"></atlas-button>
          </atlas-layout>
          </atlas-form>
        </g:if>
        <g:else>
          <atlas-empty-state
                  illustration="schedule-user-avatar"
                  header="Sem pagadores cadastrados">
            Aqui você pode cadastrar os pagadores que deseja utilizar em suas transações.

            <atlas-button
                    icon="plus"
                    description="Adicionar pagador"
                    href="/payer"
                    slot="button"></atlas-button>
          </atlas-empty-state>
        </g:else>
      </atlas-section>

</atlas-panel>
</body>
</html>