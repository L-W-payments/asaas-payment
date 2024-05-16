<!doctype html>
<html lang="pt-BR" class="no-js">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title>
    <g:layoutTitle default="Grails" />
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />

    <asset:stylesheet src="application.css" />

    <link rel="stylesheet" href="https://atlas.asaas.com/v15.18.0/atlas.min.css" crossorigin="anonymous">
    <script defer src="https://atlas.asaas.com/v15.18.0/atlas.min.js" crossorigin="anonymous"></script>

    <g:layoutHead />
</head>

<body>

<atlas-screen>
    <g:render template="/templates/sidebar" />

    <atlas-navbar slot="navbar"></atlas-navbar>

    <atlas-page>
        <atlas-page-header slot="header" page-name="${pageProperty(name: 'body.page-title')}">
            <atlas-breadcrumb slot="breadcrumb">
                <atlas-breadcrumb-item text="${pageProperty(name: 'body.page-title')}"
                                       icon="home"></atlas-breadcrumb-item>
            </atlas-breadcrumb>
        </atlas-page-header>

        <atlas-page-content slot="content" class="js-atlas-content">
            <g:layoutBody />
        </atlas-page-content>
    </atlas-page>
</atlas-screen>


<asset:javascript src="application.js" />

</body>

</html>