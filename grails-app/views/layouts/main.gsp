<!doctype html>
<html lang="pt-BR" class="no-js">

<%
    String pageTitle = pageProperty(name: 'body.page-title')
    String breadcrumbTitle = pageProperty(name: 'body.breadcrumb-title', default: pageTitle)
%>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <title>
    <g:layoutTitle default="Grails"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>

    <asset:stylesheet src="application.css"/>
    <asset:stylesheet src="new-theme.scss"/>

    <link rel="stylesheet" href="https://atlas.asaas.com/v15.18.0/atlas.min.css" crossorigin="anonymous">
    <script defer src="https://atlas.asaas.com/v15.18.0/atlas.min.js" crossorigin="anonymous"></script>

    <g:layoutHead/>
</head>

<body>
    <atlas-screen>
        <g:render template="/templates/sidebar"/>

        <g:render template="/templates/navbar"/>

        <atlas-page>
            <atlas-page-header slot="header"
                               page-name="${pageTitle}">
                <atlas-breadcrumb slot="breadcrumb">
                    <atlas-breadcrumb-item
                            text="${breadcrumbTitle}"
                            icon="${pageProperty(name: 'body.page-icon', default: 'home')}"></atlas-breadcrumb-item>
                </atlas-breadcrumb>
            </atlas-page-header>

            <atlas-page-content slot="content" class="js-atlas-content">
                <g:layoutBody/>
            </atlas-page-content>
        </atlas-page>
    </atlas-screen>

    <asset:javascript src="application.js"/>
    <asset:javascript src="NotificationController.js"/>
</body>

</html>