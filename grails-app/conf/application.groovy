// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.miniasaaslw.domain.security.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.miniasaaslw.domain.security.UserRole'
grails.plugin.springsecurity.authority.className = 'com.miniasaaslw.domain.security.Role'
grails.plugin.springsecurity.userLookup.usernamePropertyName='email'
grails.plugin.springsecurity.logout.postOnly = false
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
		[pattern: '/',               access: 'isAuthenticated()'],
		[pattern: '/error',          access: ['permitAll']],
		[pattern: '/index',          access: 'isAuthenticated()'],
		[pattern: '/index.gsp',      access: 'isAuthenticated()'],
		[pattern: '/assets/**',      access: ['permitAll']],
		[pattern: '/**/js/**',       access: ['permitAll']],
		[pattern: '/**/css/**',      access: ['permitAll']],
		[pattern: '/**/images/**',   access: ['permitAll']],
		[pattern: '/**/favicon.ico', access: ['permitAll']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
		[pattern: '/assets/**',      filters: 'none'],
		[pattern: '/**/js/**',       filters: 'none'],
		[pattern: '/**/css/**',      filters: 'none'],
		[pattern: '/**/images/**',   filters: 'none'],
		[pattern: '/**/favicon.ico', filters: 'none'],
		[pattern: '/**',             filters: 'JOINED_FILTERS']
]

