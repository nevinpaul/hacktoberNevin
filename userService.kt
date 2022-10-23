package com.example.kotlindemo.service

import com.example.kotlindemo.entities.User
import com.example.kotlindemo.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

fun saveUser(user: User) : User{
    return userRepository.save(user)
}

    fun findByEmail(email:String) : User?{
        return userRepository.findByUserName(email)
    }
}
