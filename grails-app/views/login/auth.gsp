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
                <img class="logo" src="https://atlas.asaas.com/assets/images/logos/asaas-logo.7POEJYYY.svg" alt=""
                     height="150"/>

                <p class="description">
                    A solução mais completa e segura em emissão de cobranças e serviços financeiros.
                </p>

                <svg id="hero" width="600" height="479" viewBox="0 0 1217 479" fill="none"
                     xmlns="http://www.w3.org/2000/svg">
                    <defs>
                        <linearGradient id="pulse-1" gradientUnits="userSpaceOnUse">
                            <stop stop-color="#000" stop-opacity="0" offset="0"/>
                            <stop stop-color="#0030b9" offset="0"/>
                            <stop offset="1" stop-color="#000" stop-opacity="0.1"/>
                        </linearGradient>
                    </defs>

                    <path d="M632 168L632 122C632 113.163 639.163 106 648 106L661 106" stroke="url(#pulse-1)"
                          stroke-width="0.5" stroke-dasharray="8 8"/>
                    <path d="M690 0L690 90C690 98.8366 682.837 106 674 106L661 106" stroke="url(#pulse-1)"
                          stroke-width="0.5" stroke-dasharray="8 8"/>
                    <line x1="584.75" y1="0" x2="584.75" y2="166" stroke="url(#pulse-1)" stroke-width="0.5"
                          stroke-dasharray="8 8"/>
                    <path d="M455 156L455 177C455 185.837 462.163 193 471 193L554 193" stroke="url(#pulse-1)"
                          stroke-width="0.5" stroke-dasharray="8 8"/>
                    <path d="M165 0L165 124C165 132.837 172.163 140 181 140L240 140" stroke="url(#pulse-1)"
                          stroke-width="0.5" stroke-dasharray="8 8"/>
                    <path d="M455 156V156C455 147.163 447.837 140 439 140L225 140" stroke="url(#pulse-1)"
                          stroke-width="0.5" stroke-dasharray="8 8"/>
                    <path d="M108 266L108 245C108 236.163 115.163 229 124 229L554 229" stroke="url(#pulse-1)"
                          stroke-width="0.5" stroke-dasharray="8 8"/>
                    <path d="M108 264L108 280C108 288.837 100.837 296 92 296L1 296" stroke="url(#pulse-1)"
                          stroke-width="0.5" stroke-dasharray="8 8"/>
                    <path d="M231 398L231 422C231 430.837 223.837 438 215 438L0 438" stroke="url(#pulse-1)"
                          stroke-width="0.5" stroke-dasharray="8 8"/>
                    <path d="M504 295L504 274C504 265.163 511.163 258 520 258L554 258" stroke="url(#pulse-1)"
                          stroke-width="0.5" stroke-dasharray="8 8"/>
                    <path d="M231 398L231 327C231 318.163 238.163 311 247 311L281 311" stroke="url(#pulse-1)"
                          stroke-width="0.5" stroke-dasharray="8 8"/>
                    <path d="M504 295V295C504 303.837 496.837 311 488 311L281 311" stroke="url(#pulse-1)"
                          stroke-width="0.5" stroke-dasharray="8 8"/>
                    <line x1="609.75" y1="279" x2="609.75" y2="431" stroke="url(#pulse-1)" stroke-width="0.5"
                          stroke-dasharray="8 8"/>
                    <path d="M993 430L993 358C993 349.163 985.837 342 977 342L933 342" stroke="url(#pulse-1)"
                          stroke-width="0.5" stroke-dasharray="8 8"/>
                    <path d="M993 430L993 443C993 451.837 1000.16 459 1009 459L1217 459" stroke="url(#pulse-1)"
                          stroke-width="0.5" stroke-dasharray="8 8"/>
                    <path d="M759 295L759 274C759 265.163 751.837 258 743 258L664 258" stroke="url(#pulse-1)"
                          stroke-width="0.5" stroke-dasharray="8 8"/>
                    <path d="M759 295L759 326C759 334.837 766.163 342 775 342L933 342" stroke="url(#pulse-1)"
                          stroke-width="0.5" stroke-dasharray="8 8"/>
                    <path d="M1016 269L1016 245C1016 236.163 1008.84 229 1000 229L664 229" stroke="url(#pulse-1)"
                          stroke-width="0.5" stroke-dasharray="8 8"/>
                    <path d="M1016 267L1016 280C1016 288.837 1023.16 296 1032 296L1113 296" stroke="url(#pulse-1)"
                          stroke-width="0.5" stroke-dasharray="8 8"/>
                    <path d="M1160 223L1160 210C1160 201.163 1167.16 194 1176 194L1217 194" stroke="url(#pulse-1)"
                          stroke-width="0.5" stroke-dasharray="8 8"/>
                    <path d="M1160 223L1160 280C1160 288.837 1152.84 296 1144 296L1094 296" stroke="url(#pulse-1)"
                          stroke-width="0.5" stroke-dasharray="8 8"/>
                    <path d="M1113 0L1113 116C1113 124.837 1105.84 132 1097 132L1050 132" stroke="url(#pulse-1)"
                          stroke-width="0.5" stroke-dasharray="8 8"/>
                    <path d="M726 148L726 169C726 177.837 718.837 185 710 185L664 185" stroke="url(#pulse-1)"
                          stroke-width="0.5" stroke-dasharray="8 8"/>
                    <path d="M726 148V148C726 139.163 733.163 132 742 132L1050 132" stroke="url(#pulse-1)"
                          stroke-width="0.5" stroke-dasharray="8 8"/>
                </svg>
            </div>

            <div class="right">
                <h1 class="title">Acesse sua conta Asaas! 🚀</h1>

                <p class="description">
                    Estamos felizes em te ver por aqui! Faça login para acessar sua conta.
                </p>

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

                    <g:if test="${flash.message}">
                        <div class="error">${flash.message}</div>
                    </g:if>
                </form>
            </div>
        </main>
    </div>
</body>
</html>