<g:set var="securityConfig" value="${applicationContext.springSecurityService.securityConfig}"/>

<html>
<head>
    <title>Entrar - Asaas</title>
    <asset:stylesheet src="login.scss"/>
</head>

<body>
    <div class="container">
        <main>
            <div class="left">
                <img class="logo" src="https://atlas.asaas.com/assets/images/logos/asaas-logo.7POEJYYY.svg" alt="Asaas"
                     height="150"/>

                <p class="description">
                    A solução mais completa e segura em emissão de cobranças e serviços financeiros.
                </p>

                <g:render template="/templates/lines"/>
            </div>

            <div class="right">
                <h1 class="title">Acesse sua conta Asaas! 🚀</h1>

                <p class="description">
                    Estamos felizes em te ver por aqui! Faça login para acessar sua conta.
                </p>

                <form class="form" action="${postUrl ?: '/login/authenticate'}" method="post" autocomplete="off">
                    <div class="form-group">
                        <label for="username">Nome</label>
                        <input id="username" name="${securityConfig.apf.usernameParameter}"
                               placeholder="Digite seu nome"/>
                    </div>

                    <div class="form-group">
                        <label for="password">Senha</label>
                        <input id="password" name="${securityConfig.apf.passwordParameter}" type="password"
                               placeholder="Digite sua senha"/>
                    </div>

                    <button type="submit">Entrar</button>

                    <g:if test="${flash.message}">
                        <div class="error">${flash.message}</div>
                    </g:if>
                </form>
            </div>
        </main>
    </div>
</body>
</html>