package com.miniasaaslw.service.user

import com.miniasaaslw.adapters.user.UserAdapter
import com.miniasaaslw.domain.security.User
import com.miniasaaslw.domain.security.UserRole
import com.miniasaaslw.repository.user.UserRepository
import com.miniasaaslw.utils.EmailUtils
import com.miniasaaslw.utils.MessageUtils
import com.miniasaaslw.utils.PasswordUtils

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

@Transactional
@GrailsCompileStatic
class UserService {

    public List<User> list(Map search, Integer max, Integer offset) {
        return UserRepository.query(search).list(max: max, offset: offset)
    }

    public User save(UserAdapter userAdapter) {
        User user = validateUser(userAdapter)

        if (user.hasErrors()) {
            throw new ValidationException(MessageUtils.getMessage("general.errors.validation"), user.errors)
        }

        buildUserProperties(user, userAdapter)
        user.save(failOnError: true)
        UserRole.create(user, userAdapter.role, true)

        return user
    }

    public void delete(Long customerId, Long id) {
        User user = find(customerId, id)

        if (!user.enabled) throw new RuntimeException(MessageUtils.getMessage("user.errors.notFound"))

        user.enabled = false
        user.save(failOnError: true)
    }

    public void updatePassword(UserAdapter userAdapter) {
        User user = validateUserUpdate(userAdapter)

        if (user.hasErrors()) {
            throw new ValidationException(MessageUtils.getMessage("general.errors.validation"), user.errors)
        }

        user = find(userAdapter.customer.id, userAdapter.id)

        user.password = userAdapter.password

        user.save(failOnError: true)
    }


    public User find(Long customerId, Long id) {
        User user = UserRepository.query([customerId: customerId, id: id]).get()

        if (!user) {
            throw new RuntimeException(MessageUtils.getMessage("user.errors.notFound"))
        }

        return user
    }

    private User validateUser(UserAdapter userAdapter) {
        User user = new User()

        if (!userAdapter.customer) {
            user.errors.rejectValue("customer", null, MessageUtils.getMessage("customer.errors.notFound"))
        }

        if (!userAdapter.role) {
            user.errors.rejectValue("role", null, MessageUtils.getMessage("role.errors.notFound"))
        }

        if (!userAdapter.email) {
            user.errors.rejectValue("email", null, MessageUtils.getMessage("general.errors.email.required"))
        }

        if (!EmailUtils.validateEmail(userAdapter.email)) {
            user.errors.rejectValue("email", null, MessageUtils.getMessage("general.errors.email.invalid"))
        }

        validatePassword(userAdapter, user)

        if(UserRepository.query(["email": userAdapter.email]).exists()){
            user.errors.rejectValue("email", null, MessageUtils.getMessage("general.errors.email.duplicated"))
        }

        return user
    }

    private void buildUserProperties(User user, UserAdapter userAdapter) {
        user.customer = userAdapter.customer
        user.email = userAdapter.email
        user.password = userAdapter.password
    }

    User validateUserUpdate(UserAdapter userAdapter) {
        User user = new User()

        if (!userAdapter.customer) {
            user.errors.rejectValue("customer", null, MessageUtils.getMessage("customer.errors.notFound"))
        }

        validatePassword(userAdapter, user)

        return user
    }

    private void validatePassword(UserAdapter userAdapter, User user) {
        if (!userAdapter.password) {
            user.errors.rejectValue("password", null, MessageUtils.getMessage("general.errors.password.required"))
        }

        if (!userAdapter.confirmPassword) {
            user.errors.rejectValue("confirmPassword", null, MessageUtils.getMessage("general.errors.password.confirmation.required"))
        }

        if (!PasswordUtils.matches(userAdapter.password, userAdapter.confirmPassword)) {
            user.errors.rejectValue("password", null, MessageUtils.getMessage("general.errors.password.confirmation"))
        } else if (!PasswordUtils.isStrong(userAdapter.password)) {
            user.errors.rejectValue("password", null, MessageUtils.getMessage("general.errors.password.weak"))
        }
    }
}
