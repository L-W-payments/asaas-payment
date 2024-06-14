<g:set var="securityConfig" value="${applicationContext.springSecurityService.securityConfig}"/>

<html>
<head>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

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

                <g:if test="${params.registered}">
                    <h1 class="title success">Sua conta foi criada! 🚀</h1>

                    <p class="description">
                        Entre com seu <strong>e-mail</strong> e <strong>senha</strong> cadastrado para começarmos.
                    </p>

                </g:if>
                <g:else>
                    <h1 class="title">Acesse sua conta Asaas! 🚀</h1>

                    <p class="description">
                        Estamos felizes em te ver por aqui! Faça login para acessar sua conta.
                    </p>
                </g:else>

                <form class="form" action="${postUrl ?: '/login/authenticate'}" method="post" autocomplete="off">
                    <div class="form-group">
                        <label for="email">E-mail</label>
                        <input id="email" name="${securityConfig.apf.usernameParameter}"
                               placeholder="Digite seu e-mail"/>
                    </div>

                    <div class="form-group">
                        <label for="password">Senha</label>
                        <input id="password" name="${securityConfig.apf.passwordParameter}" type="password"
                               placeholder="Digite sua senha"/>
                    </div>

                    <button type="submit">Entrar</button>

                    <span class="description">Não possui uma conta? <a class="link" href="/customer">Registre-se</a>
                    </span>

                    <g:if test="${flash.message}">
                        <div class="error">${flash.message}</div>
                    </g:if>
                </form>
            </div>
        </main>
    </div>

    <asset:javascript src="LoginController.js"/>
</body>
</html>