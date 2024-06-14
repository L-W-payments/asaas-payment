package com.miniasaaslw.controller

import com.miniasaaslw.entity.enums.MessageType
import com.miniasaaslw.exception.BusinessException
import com.miniasaaslw.utils.MessageUtils

import grails.validation.ValidationException

class ExceptionController {

    protected Boolean hasMessages() {
        return flash.messageInfo != null
    }

    protected getMessagesObject() {
        return flash.messageInfo
    }

    protected void addMessage(List<String> errors, MessageType messageType) {
        flash.messageInfo = [messages: errors, messageType: messageType.toString()]
    }

    protected void addMessage(String message, MessageType messageType) {
        flash.messageInfo = [messages: [message], messageType: messageType.toString()]
    }

    protected void addMessageCode(String code, MessageType messageType) {
        flash.messageInfo = [messages: [MessageUtils.getMessage(code)], messageType: messageType.toString()]
    }

    def handleException(Exception exception) {
        if (exception instanceof ValidationException) {
            List<String> errors = exception.errors.allErrors.collect { it.defaultMessage }

            addMessage(errors, MessageType.ERROR)
            return true
        }

        if (exception instanceof BusinessException) {
            addMessage(exception.message, MessageType.ERROR)
            return true
        }

        return false
    }
}
