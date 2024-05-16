<atlas-sidebar slot="sidebar" home-path="${createLink(controller: 'customer', action: 'index')}">
</atlas-dropdown-button>

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
  </atlas-sidebar-menu>
</atlas-sidebar>