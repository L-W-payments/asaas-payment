<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Página Inicial</title>
</head>
<body page-title="Página inicial">
    <atlas-panel header="Situação das cobranças do mês" items-per-page="4" carousel>
        <g:render template="/templates/atlasCard" model="${cardData}"/>
    </atlas-panel>

    <atlas-panel header="Para você" items-per-page="4" carousel>
        <atlas-card background="light">
            <atlas-layout alignment="center" gap="4">
                <atlas-image src="https://storybook.atlas.asaas.com/assets/illustrations/happy-woman-notebook.svg" height="200"></atlas-image>
                <atlas-layout gap="3">
                    <atlas-heading size="h7">Adicione pagadores</atlas-heading>
                    <atlas-text line-clamp="5" size="sm">
                        Gerencie seus pagadores de forma eficiente e fácil! Adicione novos pagadores para controlar melhor suas transações e mantenha tudo organizado em um só lugar.
                    </atlas-text>
                </atlas-layout>
            </atlas-layout>
            <atlas-button block type="outlined" description="Adicionar pagador" href="/payer" slot="actions"></atlas-button>
        </atlas-card>
        <atlas-card background="light">
            <atlas-layout alignment="center" gap="4">
                <atlas-image src="https://storybook.atlas.asaas.com/assets/illustrations/papers.svg" height="200"></atlas-image>
                <atlas-layout gap="3">
                    <atlas-heading size="h7">Visualize os pagadores</atlas-heading>
                    <atlas-text line-clamp="5" size="sm">
                        Tenha uma visão clara de todos os seus pagadores! Acompanhe os detalhes e o histórico de pagamentos de cada um, facilitando a gestão e o relacionamento com seus clientes.
                    </atlas-text>
                </atlas-layout>
            </atlas-layout>
            <atlas-button block type="outlined" description="Visualizar pagadores" href="/payer/list" slot="actions"></atlas-button>
        </atlas-card>
        <atlas-card background="light">
            <atlas-layout alignment="center" gap="4">
                <atlas-image src="https://storybook.atlas.asaas.com/assets/illustrations/man-coin-money.svg" height="200"></atlas-image>
                <atlas-layout gap="3">
                    <atlas-heading size="h7">Crie cobranças</atlas-heading>
                    <atlas-text line-clamp="5" size="sm">
                        Simplifique sua gestão financeira criando cobranças de forma rápida e prática! As cobranças são enviadas por email para seus clientes automaticamente e acompanhe os pagamentos em um só lugar.
                    </atlas-text>
                </atlas-layout>
            </atlas-layout>
            <atlas-button block type="outlined" description="Criar cobrança" href="/payment" slot="actions"></atlas-button>
        </atlas-card>
        <atlas-card background="light">
            <atlas-layout alignment="center" gap="4">
                <atlas-image src="https://storybook.atlas.asaas.com/assets/illustrations/flow-money-coins.svg" height="200"></atlas-image>
                <atlas-layout gap="3">
                    <atlas-heading size="h7">Visualize suas cobranças</atlas-heading>
                    <atlas-text line-clamp="5" size="sm">
                        Acompanhe todas as suas cobranças em um só lugar! Visualize o status de cada cobrança e mantenha o controle de suas finanças de maneira eficiente e organizada.
                    </atlas-text>
                </atlas-layout>
            </atlas-layout>
            <atlas-button block type="outlined" description="Visualizar suas cobranças" href="/payment/list" slot="actions"></atlas-button>
        </atlas-card>
    </atlas-panel>
</body>
</html>
