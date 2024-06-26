package com.miniasaaslw.controller


import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.domain.security.User

class BaseController extends ExceptionController {

    protected Integer getLimitPerPage() {
        String itemsPerPage = params.itemsPerPage?.toString()

        if (!itemsPerPage?.isNumber()) params.itemsPerPage = null

        return Math.min(Integer.valueOf(params.itemsPerPage ? params.itemsPerPage : 10), 50)
    }

    protected Integer getPage() {
        String page = params.page?.toString()

        if (!page?.isNumber()) params.page = null

        return Math.max(Integer.valueOf(params.page ? params.page : 0) - 1, 0)
    }

    protected Integer getOffset() {
        return getPage() * getLimitPerPage()
    }

    protected User getCurrentUser() {
        return getAuthenticatedUser() as User
    }

    protected Customer getCurrentCustomer() {
        return getCurrentUser().customer
    }

    protected Long getCurrentCustomerId() {
        return getCurrentCustomer().id
    }
}
