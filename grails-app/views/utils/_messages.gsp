<g:if test="${messageInfo}">
    <atlas-alert message="${messageInfo.messages?.join(" ")}" type="${messageInfo.messageType}"></atlas-alert>
</g:if>