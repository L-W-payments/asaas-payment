<g:if test="${alerts && alertType}">
    <atlas-alert message="${alerts.join(" ")}" type="${alertType}"></atlas-alert>
</g:if>