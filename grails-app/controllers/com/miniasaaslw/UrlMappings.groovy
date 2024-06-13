package com.miniasaaslw

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/receipt/$id"(controller: "paymentReceipt", action: "show")
        "/"(controller: "index", action: "index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
