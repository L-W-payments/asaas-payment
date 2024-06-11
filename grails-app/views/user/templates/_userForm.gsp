<atlas-section header="${ title }">
    <atlas-grid>
        <atlas-row>
            <atlas-col>
                <atlas-masked-input label="Email" name="email" mask-alias="email" required="true"
                                    placeholder="maria.bernadete@asaas.com.br" value="${user.email}">
                </atlas-masked-input>
            </atlas-col>
        </atlas-row>
        <atlas-row>
            <atlas-col lg="6" md="6">
                <atlas-password-input label="Senha" name="password" placeholder="********" required></atlas-password-input>
            </atlas-col>
            <atlas-col lg="6" md="6">
                <atlas-password-input label="Confirmar senha" name="confirmPassword" placeholder="********" required></atlas-password-input>
            </atlas-col>
        </atlas-row>
    </atlas-grid>
</atlas-section>