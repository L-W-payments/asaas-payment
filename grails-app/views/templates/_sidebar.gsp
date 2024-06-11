<atlas-sidebar slot="sidebar" home-path="/">

    <atlas-sidebar-menu slot="body">
        <atlas-sidebar-menu-item icon="users" value="clients-group" text="Pagadores" ${controllerName=='payer'
                ? 'active' : '' }>

            <atlas-sidebar-menu-item icon="users" value="clients-group" text="Cadastrar pagador"
                                     href="/payer/" ${controllerName=='payer' &&
                    actionName=='index' ? 'active' : '' }>
            </atlas-sidebar-menu-item>


            <atlas-sidebar-menu-item icon="users" value="clients-group" text="Lista de Pagadores"
                                     href="/payer/list" ${ controllerName=='payer' &&
                    actionName=='list' ? 'active' : '' }></atlas-sidebar-menu-item>

        </atlas-sidebar-menu-item>

        <atlas-sidebar-menu-item icon="money" value="payment-group" text="Cobranças" ${controllerName=='payment'
                ? 'active' : '' }>

            <atlas-sidebar-menu-item icon="money" value="payment-group" text="Cadastrar cobrança"
                                     href="/payment/" ${controllerName=='payment' &&
                    actionName=='index' ? 'active' : '' }>
            </atlas-sidebar-menu-item>


            <atlas-sidebar-menu-item icon="money-notes" value="payment-group" text="Lista de cobranças"
                                     href="/payment/list" ${ controllerName=='payment' &&
                    actionName=='list' ? 'active' : '' }></atlas-sidebar-menu-item>

    </atlas-sidebar-menu-item>
</atlas-sidebar-menu>
</atlas-sidebar>