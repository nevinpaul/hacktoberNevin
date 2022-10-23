package com.example.kotlindemo.service

import com.example.kotlindemo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthenticationService(private val userRepository: UserRepository)
    :UserDetailsService {
    override fun loadUserByUsername(userName: String?): UserDetails {
        val user = userRepository.findByUserName(userName!!)
        return User(user.userName, user.password, listOf())
    }
}
