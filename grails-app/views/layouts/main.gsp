<!doctype html>
<html lang="pt-BR" class="no-js">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
    <g:layoutTitle default="Grails"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <asset:stylesheet src="application.css"/>
    <asset:stylesheet src="new-theme.scss"/>

    <link rel="stylesheet" href="https://atlas.asaas.com/v15.18.0/atlas.min.css" crossorigin="anonymous">
    <script defer src="https://atlas.asaas.com/v15.18.0/atlas.min.js" crossorigin="anonymous"></script>

    <g:layoutHead/>
</head>

<body>

<atlas-screen>
    <g:render template="/templates/sidebar"/>

    <atlas-navbar slot="navbar">
        <div slot="actions">
            <atlas-icon-button
                    icon="bell"
                    data-atlas-dropdown="notifications-dropdown"
                    hoverable></atlas-icon-button>
            <atlas-dropdown
                    id="notifications-dropdown"
                    placement="bottom-start"
                    trigger="click"
                    width="300"
                    auto-close
                    auto-close-trigger="outside">
                <atlas-empty-state
                        illustration="airplane-error"
                        header="Nenhuma notificação"
                >
                    Aqui você pode visualizar todas as notificações importantes do seu negócio.
                </atlas-empty-state>
            </atlas-dropdown>
        </div>

        <div slot="actions">
            <atlas-avatar data-atlas-dropdown="profile-dropdown" hoverable show-carret></atlas-avatar>
            <atlas-dropdown
                    id="profile-dropdown"
                    placement="bottom-start"
                    trigger="click"
                    width="300"
                    auto-close
                    auto-close-trigger="outside">
                <atlas-dropdown-item
                        icon="cog"
                        theme="secondary"
                        href="${createLink(controller: 'customer', action: 'show', id: 1)}"
                >
                    Meu perfil
                </atlas-dropdown-item>
                <atlas-dropdown-item icon="power" theme="danger">Sair</atlas-dropdown-item>
            </atlas-dropdown>
        </div>
    </atlas-navbar>

    <atlas-page>
        <atlas-page-header slot="header" page-name="${pageProperty(name: 'body.page-title')}">
            <atlas-breadcrumb slot="breadcrumb">
                <atlas-breadcrumb-item text="${pageProperty(name: 'body.page-title')}"
                                       icon="home"></atlas-breadcrumb-item>
            </atlas-breadcrumb>
        </atlas-page-header>

        <atlas-page-content slot="content" class="js-atlas-content">
            <g:layoutBody/>
        </atlas-page-content>
    </atlas-page>
</atlas-screen>


<asset:javascript src="application.js"/>

</body>

</html>