<g:if test="${alertInfo}">
    <atlas-alert message="${alertInfo.alerts?.join(" ")}" type="${alertInfo.alertType}"></atlas-alert>
</g:if>