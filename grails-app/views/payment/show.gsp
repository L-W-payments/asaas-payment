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
                              id: payment.id)}"
                      description="Apagar"></atlas-button>
        <atlas-layout gap="4">
          <atlas-section header="Pagador">
                <atlas-input required value="${payment.payer.name} — ${payment.payer.email}" ></atlas-input>
          </atlas-section>
          <atlas-section header="Dados da cobrança">
            <atlas-grid>
              <atlas-row>
                <atlas-col lg="4" md="4">
                  <atlas-input required label="Status da cobrança" value="${message(code: "paymentStatus.${payment.paymentStatus}.label")}" ></atlas-input>
                </atlas-col>
                <atlas-col lg="4" md="4">
                  <atlas-money label="Valor da cobrança" name="value" min-value="10" max-value="10000"
                               min-value-error-message="O valor mínimo é de R$ 10,00"
                               max-value-error-message="O valor máximo é de R$ 10.000,00" required maxlength="9" value="${payment.value}"></atlas-money>
                </atlas-col>
                <atlas-col lg="4" md="4">
                  <atlas-datepicker name="dueDate" label="Vencimento da cobrança" prevent-past-date
                                    required value="<g:formatDate date="${payment.dueDate}" format="dd/MM/yyyy"/>"></atlas-datepicker>
                </atlas-col>
              </atlas-row>
            </atlas-grid>
          </atlas-section>
          <atlas-textarea name="description" label="Descrição da cobrança"
                          placeholder="Informações relevantes da cobrança." rows="3" maxlength="500" value="${payment.description}"></atlas-textarea>
          <atlas-element-group disabled required-fields="1" required-fields-error-message="Selecione 1 opção para continuar">
            <atlas-grid>
              <atlas-row>
                <atlas-col lg="6" md="6">
                  <atlas-selection-card name="paymentType" label="Pix" value="${PaymentType.PIX}" type="radio" ${payment.paymentType==PaymentType.PIX ? 'checked' : '' } ></atlas-selection-card>
                </atlas-col>
                <atlas-col lg="6" md="6">
                  <atlas-selection-card name="paymentType" label="Boleto" value="${PaymentType.BANK_SLIP}" type="radio" ${payment.paymentType==PaymentType.BANK_SLIP ? 'checked' : '' } ></atlas-selection-card>
                </atlas-col>
              </atlas-row>
              <atlas-row>
                  <atlas-col lg="6" md="6">
                    <atlas-selection-card name="paymentType" label="Cartão de Crédito" value="${PaymentType.CREDIT_CARD}" type="radio" ${payment.paymentType==PaymentType.CREDIT_CARD ? 'checked' : '' }></atlas-selection-card>
                  </atlas-col>
                <atlas-col lg="6" md="6">
                  <atlas-selection-card name="paymentType" label="Cartão de Débito" value="${PaymentType.DEBIT_CARD}" type="radio" ${payment.paymentType==PaymentType.DEBIT_CARD ? 'checked' : '' }></atlas-selection-card>
                </atlas-col>
              </atlas-row>
            </atlas-grid>
          </atlas-element-group>
        </atlas-layout>
      </atlas-form-panel>
</body>
</html>