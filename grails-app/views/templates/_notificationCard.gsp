<%@ page import="com.miniasaaslw.entity.enums.NotificationPriority" %>

<atlas-notification-card
        icon="hand-holding-money"
        overlay-icon="${notification.priority.isHigh() ? 'alert-circle' : notification.priority.isMedium() ? 'alert-triangle' : 'money-notes'}"
        overlay-theme="${notification.priority.isHigh() ? 'danger' : notification.priority.isMedium() ? 'warning' : 'success'}"
        header="${notification.title}"
        description="${notification.message}">
</atlas-notification-card>