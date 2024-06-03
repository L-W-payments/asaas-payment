<%@ page import="com.miniasaaslw.entity.enums.NotificationPriority" %>

<g:if test="${notifications}">
    <g:each in="${notifications}" var="notification">
        <atlas-notification-card
                icon="hand-holding-money"
                overlay-icon="${notification.priority.isHigh() ? 'alert-circle' : notification.priority.isMedium() ? 'alert-triangle' : 'money-notes'}"
                overlay-theme="${notification.priority.isHigh() ? 'danger' : notification.priority.isMedium() ? 'warning' : 'success'}"
                header="${notification.title}"
                description="${notification.message}">
        </atlas-notification-card>
    </g:each>
</g:if>
<g:else>
    <atlas-empty-state
            illustration="airplane-error"
            header="Nenhuma notificação"
    >
        Aqui você pode visualizar todas as notificações importantes do seu negócio.
    </atlas-empty-state>
</g:else>