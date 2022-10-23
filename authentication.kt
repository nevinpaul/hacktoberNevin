package com.nev.kotlindemo.controller

import com.example.kotlindemo.entities.User
import com.example.kotlindemo.model.UserCredentials
import com.example.kotlindemo.repository.UserRepository
import com.example.kotlindemo.service.UserService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/public")
class AuthenticationController(
    @Autowired
    val userService: UserService
) {

    @PostMapping("sign-up")
    fun signUp(@RequestBody userCredentials : UserCredentials) {
        val user : User = User(userCredentials.userName,userCredentials.password);
        userService.saveUser(user)
    }

    @PostMapping("login")
    fun logIn(@RequestBody userCredentials : UserCredentials) : ResponseEntity<Any>{
        val user = userService.findByEmail(userCredentials.userName)
        if(user == null){
            return ResponseEntity.badRequest().body("Invalid User")
        }

        if(!user.isPasswordValid(userCredentials.password)){
            return ResponseEntity.badRequest().body("Invalid User")
        }
        val issuer = user.id.toString()
        val claims = Jwts.claims().setSubject(user.userName)
        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setClaims(claims)
            .setExpiration(Date(System.currentTimeMillis() + 60 * 24 * 1000)) // 1 day
            .signWith(SignatureAlgorithm.HS512, "secret").compact()

    return ResponseEntity.ok(jwt)
    }
}
