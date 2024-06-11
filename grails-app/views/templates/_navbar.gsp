<%@ page import="com.miniasaaslw.utils.LoggedCustomer" %>

<atlas-navbar slot="navbar">
    <div slot="actions">
        <atlas-icon-button
                icon="bell"
                data-atlas-dropdown="notifications-dropdown"
                tooltip="Notificações"
                hoverable></atlas-icon-button>
        <atlas-dropdown
                header="Notificações"
                id="notifications-dropdown"
                placement="bottom-start"
                trigger="click"
                width="400"
                max-height="400"
                auto-close
                auto-close-trigger="outside">
            <atlas-empty-state
                    illustration="airplane-error"
                    header="Nenhuma notificação">
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
                    href="${createLink(controller: 'customer', action: 'show', id: LoggedCustomer.CUSTOMER.id)}">
                Meu perfil
            </atlas-dropdown-item>
            <atlas-dropdown-item href="/logout" icon="power" theme="danger">Sair</atlas-dropdown-item>
        </atlas-dropdown>
    </div>
</atlas-navbar>