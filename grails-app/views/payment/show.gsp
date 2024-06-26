<%@ page import="com.miniasaaslw.entity.enums.payment.BillingType" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta name="layout" content="main"/>
    <title>Cobranças - Asaas</title>
    <asset:javascript src="PaymentController.js"/>
</head>

<body page-title="Visualizar cobrança">
    <g:if test="${messageInfo}">
        <g:render template="/utils/messages" model="${messageInfo}"/>
    </g:if>

    <atlas-form-panel class="js-show-payment-panel">
        <atlas-button slot="actions"
                      icon="arrow-up-right"
                      is-external-link
                      description="Acessar tela de pagamento"
                      href="${createLink(
                              controller: 'payment',
                              action: 'checkout',
                              id: payment.publicId)}">
        </atlas-button>

        <g:if test="${payment.paymentStatus.isPending()}">
            <atlas-button slot="actions"
                          icon="hand-holding-money"
                          theme="success"
                          type="outlined"
                          href="${createLink(
                                  controller: 'payment',
                                  action: 'updateToReceivedInCash',
                                  id: payment.id)}"
                          description="Confirmar recebimento em dinheiro">
            </atlas-button>
        </g:if>

        <atlas-button slot="actions"
                      icon="trash"
                      class="js-delete-button"
                      data-delete-url="${createLink(controller: 'payment', action: 'delete', id: payment.id)}"
                      data-redirect-url="${createLink(controller: 'payment', action: 'list')}"
                      type="outlined"
                      theme="danger"
                      data-action="delete"
                      description="Apagar">
        </atlas-button>
        <atlas-layout gap="4">
            <atlas-section header="Pagador">
                <atlas-layout gap="3" inline mobile-inline>
                    <atlas-layout>
                        <atlas-input required value="${payment.payer.name} — ${payment.payer.email}"></atlas-input>
                    </atlas-layout>
                    <atlas-button icon="arrow-up-right"
                                  is-external-link
                                  tooltip="Exibir pagador"
                                  type="outlined"
                                  href="${createLink(
                                          controller: 'payer',
                                          action: 'show',
                                          id: payment.payerId)}">
                    </atlas-button>
                </atlas-layout>
            </atlas-section>
            <atlas-section header="Dados da cobrança">
                <atlas-grid>
                    <atlas-row>
                        <atlas-col lg="4" md="4">
                            <atlas-input required label="Status da cobrança"
                                         value="${message(code: "paymentStatus.${payment.paymentStatus}.label")}"></atlas-input>
                        </atlas-col>
                        <atlas-col lg="4" md="4">
                            <atlas-money label="Valor da cobrança" name="value" min-value="10" max-value="10000"
                                         min-value-error-message="O valor mínimo é de R$ 10,00"
                                         max-value-error-message="O valor máximo é de R$ 10.000,00" required
                                         maxlength="9" value="${payment.value}"></atlas-money>
                        </atlas-col>
                        <atlas-col lg="4" md="4">
                            <atlas-datepicker name="dueDate" label="Vencimento da cobrança" prevent-past-date
                                              required
                                              value="${formatterTagLib.dateTime(date: payment.dueDate)}"></atlas-datepicker>
                        </atlas-col>
                    </atlas-row>
                </atlas-grid>
            </atlas-section>
            <atlas-textarea name="description" label="Descrição da cobrança"
                            placeholder="Informações relevantes da cobrança." rows="3" maxlength="500"
                            value="${payment.description}"></atlas-textarea>
            <atlas-element-group disabled required-fields="1"
                                 required-fields-error-message="Selecione 1 opção para continuar">
                <atlas-grid>
                    <atlas-row>
                        <atlas-col lg="6" md="6">
                            <atlas-selection-card name="billingType" label="Pix" value="${BillingType.PIX}"
                                                  type="radio" ${payment.billingType.isPix() ? 'checked' : ''}></atlas-selection-card>
                        </atlas-col>
                        <atlas-col lg="6" md="6">
                            <atlas-selection-card name="billingType" label="Boleto" value="${BillingType.BANK_SLIP}"
                                                  type="radio" ${payment.billingType.isBankSlip() ? 'checked' : ''}></atlas-selection-card>
                        </atlas-col>
                    </atlas-row>
                    <atlas-row>
                        <atlas-col lg="6" md="6">
                            <atlas-selection-card name="billingType" label="Cartão de Crédito"
                                                  value="${BillingType.CREDIT_CARD}"
                                                  type="radio" ${payment.billingType.isCreditCard() ? 'checked' : ''}></atlas-selection-card>
                        </atlas-col>
                        <atlas-col lg="6" md="6">
                            <atlas-selection-card name="billingType" label="Cartão de Débito"
                                                  value="${BillingType.DEBIT_CARD}"
                                                  type="radio" ${payment.billingType.isDebitCard() ? 'checked' : ''}></atlas-selection-card>
                        </atlas-col>
                    </atlas-row>
                </atlas-grid>
            </atlas-element-group>
        </atlas-layout>
    </atlas-form-panel>
</body>
</html>