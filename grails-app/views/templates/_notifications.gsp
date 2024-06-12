<g:if test="${notifications}">
    <g:each in="${notifications}" var="notification">
        <atlas-notification-card
                icon="hand-holding-money"
                overlay-icon="${notification.type.isError() ? 'alert-circle' : notification.type.isWarning() ? 'alert-triangle' : 'money-notes'}"
                overlay-theme="${notification.type.isError() ? 'danger' : notification.type.isWarning() ? 'warning' : 'success'}"
                header="${notification.title}"
                description="${notification.message}"
                link-text="${notification.url ? 'Ver detalhes...' : ''}"
                link-path="${notification.url ?: ''}">
        </atlas-notification-card>
    </g:each>
</g:if>
<g:else>
    <atlas-empty-state
            illustration="airplane-error"
            header="Nenhuma notificação">
        Aqui você pode visualizar todas as notificações importantes do seu negócio.
    </atlas-empty-state>
</g:else>