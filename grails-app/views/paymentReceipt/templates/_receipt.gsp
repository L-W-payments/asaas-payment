<%@ page import="com.miniasaaslw.entity.enums.PersonType" %>

<atlas-panel class="js-receipt-content">
    <atlas-layout inline justify="space-between">
        <atlas-heading size="h5">
            Comprovante de pagamento
        </atlas-heading>

        <atlas-text muted>
            Gerado em <g:formatDate date="${new Date()}" format="dd/MM/yyyy 'às' HH:mm"/>
        </atlas-text>
    </atlas-layout>

    <atlas-layout inline justify="content-space-between" alignment="center">
        <atlas-layout gap="2">
            <atlas-text muted size="sm">${receipt.payment.payer.name}</atlas-text>
            <atlas-text muted size="sm">${receipt.payment.payer.email}</atlas-text>
            <atlas-text muted size="sm">${formatterTagLib.phone(phone: receipt.payment.payer.phone)}</atlas-text>
        </atlas-layout>

        <atlas-image src="https://atlas.asaas.com/assets/images/logos/asaas-small-logo.svg"
                     height="50" style="margin-top: 16px; margin-bottom: 32px"></atlas-image>
    </atlas-layout>

    <atlas-divider spacing="5"></atlas-divider>

    <atlas-layout gap="2">
        <atlas-text bold muted size="sm">
            Valor pago: <atlas-text muted
                                    size="sm">${formatNumber(number: receipt.payment.value, type: 'currency', locale: 'pt_BR')}</atlas-text>
        </atlas-text>

        <atlas-text bold muted size="sm">
            Forma de pagamento: <atlas-text muted
                                            size="sm">${message(code: "billingType.${receipt.payment.billingType}.label")}</atlas-text>
        </atlas-text>

        <atlas-text bold muted size="sm">
            Data do vencimento: <atlas-text muted
                                            size="sm">${formatterTagLib.dateTime(date: receipt.payment.dueDate)}</atlas-text>
        </atlas-text>

        <atlas-text bold muted size="sm">
            Data de pagamento: <atlas-text muted
                                           size="sm">${formatterTagLib.dateTime(date: receipt.dateCreated, longDate: true)}</atlas-text>
        </atlas-text>

        <atlas-text bold muted size="sm">
            Rastreabilidade: <atlas-text muted size="sm">#${receipt.id}</atlas-text>
        </atlas-text>
    </atlas-layout>

    <atlas-divider spacing="5"></atlas-divider>

    <atlas-layout gap="2">
        <atlas-text bold muted size="sm">
            Nome do recebedor: <atlas-text muted
                                           size="sm">${receipt.payment.customer.name}</atlas-text>
        </atlas-text>
        <g:if test="${receipt.payment.customer.personType.isNatural()}">
            <atlas-text bold muted size="sm">
                CPF: <atlas-text muted size="sm">${formatterTagLib.cpf(cpf: receipt.payment.customer.cpfCnpj)}</atlas-text>
            </atlas-text>
        </g:if>
        <g:else>
            <atlas-text bold muted size="sm">
                CNPJ: <atlas-text muted size="sm">${formatterTagLib.cnpj(cnpj: receipt.payment.customer.cpfCnpj)}</atlas-text>
            </atlas-text>
        </g:else>

        <atlas-text bold muted size="sm">
            Instituição: <atlas-text muted
                                     size="sm">ASAAS GESTÃO FINANCEIRA INSTITUIÇÃO DE PAGAMENTO S.A.</atlas-text>
        </atlas-text>
    </atlas-layout>

    <atlas-divider spacing="5"></atlas-divider>

    <atlas-text size="sm" alignment="center" theme="warning" theme-variation="400">
        Este documento e cobrança não possuem valor fiscal e são de responsabilidade única e exclusiva
        de ${receipt.payment.payer.name.split(" ")[0]}.
    </atlas-text>
</atlas-panel>