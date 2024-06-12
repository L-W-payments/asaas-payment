package com.miniasaaslw.security

import com.miniasaaslw.domain.security.User

import grails.gorm.transactions.Transactional

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Transactional
class CustomUserDetails implements UserDetails {

    private final User user

    CustomUserDetails(User user) {
        this.user = user
    }

    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
        return user.authorities.collect { new SimpleGrantedAuthority(it.authority) }
    }

    @Override
    String getPassword() {
        return user.password
    }

    @Override
    String getUsername() {
        return user.email
    }

    String getEmail() {
        return user.email
    }

    @Override
    boolean isAccountNonExpired() {
        return true
    }

    @Override
    boolean isAccountNonLocked() {
        return !user.accountLocked
    }

    @Override
    boolean isCredentialsNonExpired() {
        return true
    }

    @Override
    boolean isEnabled() {
        return user.enabled
    }
}
