package com.miniasaaslw.controller.user

import com.miniasaaslw.adapters.user.UserAdapter
import com.miniasaaslw.controller.BaseController
import com.miniasaaslw.domain.security.Role
import com.miniasaaslw.domain.security.User
import com.miniasaaslw.entity.enums.MessageType
import com.miniasaaslw.exception.GenericException
import com.miniasaaslw.service.user.UserService
import com.miniasaaslw.utils.MessageUtils

import grails.compiler.GrailsCompileStatic
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

import groovy.transform.CompileDynamic

@GrailsCompileStatic
@Secured(['ROLE_ADMIN'])
class UserController extends BaseController {

    UserService userService

    def index() {
        if (hasMessages()) {
            return [messageInfo: getMessagesObject()]
        }
    }

    @CompileDynamic
    @Secured(['isAuthenticated()'])
    def show() {
        try {
            User user

            Boolean isAdmin = getCurrentUser().getAuthorities().stream().any { it.authority == 'ROLE_ADMIN' }

            if (isAdmin && params.id) {
                user = userService.find(getCurrentCustomerId(), params.long("id"))
            } else {
                user = getCurrentUser()
            }

            if (hasMessages()) {
                return [user: user, messageInfo: getMessagesObject()]
            }

            return [user: user]
        } catch (Exception exception) {
            if (!handleException(exception)) addMessageCode("user.errors.show.unknown", MessageType.ERROR)

            redirect(action: "show")
        }
    }

    def save() {
        try {
            userService.save(new UserAdapter(getCurrentCustomer(), Role.findByAuthority('ROLE_MEMBER'), params))

            addMessageCode("user.success.save", MessageType.SUCCESS)
        } catch (Exception exception) {
            if (!handleException(exception)) addMessageCode("user.errors.save.unknown", MessageType.ERROR)
        }

        redirect(action: "index")
    }

    @Secured(['ROLE_ADMIN'])
    def delete() {
        try {
            Long id = params.long("id")

            userService.delete(getCurrentCustomerId(), id)

            render([success: true] as JSON)
        } catch (GenericException genericException) {
            render([success: false, alert: genericException.getMessage()] as JSON)
        } catch (Exception exception) {
            render([success: false, alert: MessageUtils.getMessage("user.errors.delete.unknown")] as JSON)
        }
    }

    @Secured(['ROLE_ADMIN'])
    def restore() {
        try {
            Long id = params.long("id")

            userService.restore(getCurrentCustomerId(), id)

            render([success: true] as JSON)
        } catch (GenericException genericException) {
            render([success: false, alert: genericException.getMessage()] as JSON)
        } catch (Exception exception) {
            render([success: false, alert: MessageUtils.getMessage("user.errors.restore.unknown")] as JSON)
        }
    }

    @Secured(['isAuthenticated()'])
    def updatePassword() {
        try {
            userService.updatePassword(new UserAdapter(getCurrentCustomer(), params))

            addMessageCode("user.success.update", MessageType.SUCCESS)
        } catch (Exception exception) {
            if (!handleException(exception)) addMessageCode("user.errors.update.unknown", MessageType.ERROR)
        }

        redirect(action: "show")
    }

    def list() {
        return [userList: userService.list([customerId: getCurrentCustomerId()], getLimitPerPage(), getOffset())]
    }

    @CompileDynamic
    def loadTableContent() {
        Map search = [customerId: getCurrentCustomerId()]

        if (params.includeDeleted) search.includeDeleted = Boolean.valueOf(params.includeDeleted)
        if (params.email) search."email[like]" = params.email

        List<User> userList = userService.list(search, getLimitPerPage(), getOffset())
        Integer totalRecords = userList.totalCount
        String content = g.render(template: "/user/templates/tableContent", model: [userList: userList])

        render([totalRecords: totalRecords, content: content, success: true] as JSON)
    }
}
