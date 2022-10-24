package com.example.kotlindemo.security

import com.example.kotlindemo.filter.JwtFilter
import com.example.kotlindemo.service.AuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@EnableWebSecurity
class SecurityConfig @Autowired constructor(val authenticationService: AuthenticationService)
    : WebSecurityConfigurerAdapter() {

        val jwtFilter = JwtFilter(authenticationService)

    override fun configure(http : HttpSecurity) {
        http?.cors()?.and()?.csrf()?.disable()?.authorizeRequests()
            ?.antMatchers()?.permitAll()
            ?.anyRequest()?.authenticated()
            ?.and()?.httpBasic()?.and()
            ?.sessionManagement()
            ?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            http?.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/public/**")
    }

    @Bean
    fun encoder() : BCryptPasswordEncoder?{
        return BCryptPasswordEncoder()
    }

}
