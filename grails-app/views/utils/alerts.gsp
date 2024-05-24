<g:if test="${alerts && alertType}">
  <g:each in="${alerts}" var="alert">
    <atlas-alert message="${alert}" type="${alertType}"></atlas-alert>
  </g:each>
</g:if>