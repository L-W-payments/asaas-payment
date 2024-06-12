package com.miniasaaslw.service.user

import com.miniasaaslw.domain.security.User
import com.miniasaaslw.repository.user.UserRepository
import com.miniasaaslw.security.CustomUserDetails

import grails.gorm.transactions.Transactional

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

@Transactional
class CustomUserDetailsService implements UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = UserRepository.query([email: email]).get()

        if (!user) {
            throw new UsernameNotFoundException("Usuário não encontrado")
        }

        if (user.deleted) {
            throw new UsernameNotFoundException("Usuário não encontrado")
        }

        return new CustomUserDetails(user)
    }
}
