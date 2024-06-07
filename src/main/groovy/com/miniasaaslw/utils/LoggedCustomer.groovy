package com.miniasaaslw.utils

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.repository.customer.CustomerRepository

/**
 * Essa classe é uma solução temporária para ser utilizada em pontos da aplicação representando o usuário logado na aplicação.
 */
class LoggedCustomer {

    public static final Customer CUSTOMER = CustomerRepository.query([:]).get()
}
