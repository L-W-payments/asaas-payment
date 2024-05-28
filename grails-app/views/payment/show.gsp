<%@ page import="com.miniasaaslw.entity.enums.payment.PaymentType" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta name="layout" content="main" />
  <title>Cobranças - Asaas</title>
</head>
<body page-title="Visualizar cobrança">
      <atlas-form-panel>
        <atlas-button slot="actions"
                      href="${createLink(
                              controller: 'payment',
                              action: 'delete',
                              id: payment.getId())}"
                      description="Apagar"></atlas-button>
        <atlas-layout gap="4">
          <atlas-section header="Pagador">
                <atlas-input required value="${payment.getPayer().getName()} — ${payment.getPayer().getEmail()}" ></atlas-input>
          </atlas-section>
          <atlas-section header="Dados da cobrança">
            <atlas-grid>
              <atlas-row>
                <atlas-col lg="4" md="4">
                  <atlas-input required label="Status da cobrança" value="${message(code: "paymentStatus.${payment.getPaymentStatus()}.label")}" ></atlas-input>
                </atlas-col>
                <atlas-col lg="4" md="4">
                  <atlas-money label="Valor da cobrança" name="value" min-value="10" max-value="10000"
                               min-value-error-message="O valor mínimo é de R$ 10,00"
                               max-value-error-message="O valor máximo é de R$ 10.000,00" required maxlength="9" value="${payment.getValue()}"></atlas-money>
                </atlas-col>
                <atlas-col lg="4" md="4">
                  <atlas-datepicker name="dueDate" label="Vencimento da cobrança" prevent-past-date
                                    required value="<g:formatDate date="${payment.getDueDate()}" format="dd/MM/yyyy"/>"></atlas-datepicker>
                </atlas-col>
              </atlas-row>
            </atlas-grid>
          </atlas-section>
          <atlas-textarea name="description" label="Descrição da cobrança"
                          placeholder="Informações relevantes da cobrança." rows="3" maxlength="500" value="${payment.getDescription()}"></atlas-textarea>
          <atlas-element-group disabled required-fields="1" required-fields-error-message="Selecione 1 opção para continuar">
            <atlas-grid>
              <atlas-row>
                <atlas-col lg="6" md="6">
                  <atlas-selection-card name="paymentType" label="Pix" value="${PaymentType.PIX}" collapsible type="radio" ${payment.getPaymentType()==PaymentType.PIX ? 'checked' : '' } >O pagamento realizado através do sistema Pix é creditado na conta do destinatário de forma imediata, proporcionando uma transferência instantânea e segura dos valores, sem a necessidade de aguardar prazos adicionais para a disponibilização dos recursos.</atlas-selection-card>
                </atlas-col>
                <atlas-col lg="6" md="6">
                  <atlas-selection-card name="paymentType" label="Boleto" value="${PaymentType.BANK_SLIP}" collapsible type="radio" ${payment.getPaymentType()==PaymentType.BANK_SLIP ? 'checked' : '' } >O pagamento efetuado por meio de boleto bancário, após a sua quitação, pode levar até três dias úteis para ser processado e compensado, momento em que o valor será creditado na conta do destinatário, conforme os prazos estabelecidos pelas instituições financeiras.</atlas-selection-card>
                </atlas-col>
              </atlas-row>
              <atlas-row>
                <atlas-col lg="6" md="6">
                  <atlas-selection-card name="paymentType" label="Cartão de Crédito" value="${PaymentType.CREDIT_CARD}" collapsible type="radio" ${payment.getPaymentType()==PaymentType.CREDIT_CARD ? 'checked' : '' }>O pagamento realizado com cartão de crédito é processado pela administradora e o valor pode ser creditado na conta do destinatário em um prazo de 1 a 30 dias, conforme as condições acordadas entre as partes.</atlas-selection-card>
                </atlas-col>
                <atlas-col lg="6" md="6">
                  <atlas-selection-card name="paymentType" label="Cartão de Débito" value="${PaymentType.DEBIT_CARD}" collapsible type="radio" ${payment.getPaymentType()==PaymentType.DEBIT_CARD ? 'checked' : '' }>O pagamento efetuado com cartão de débito é processado imediatamente, com o valor sendo debitado diretamente da conta do pagador e creditado na conta do destinatário em tempo real ou dentro de alguns minutos, garantindo uma transferência rápida e segura dos fundos.</atlas-selection-card>
                </atlas-col>
              </atlas-row>
            </atlas-grid>
          </atlas-element-group>
        </atlas-layout>
      </atlas-form-panel>
</body>
</html>