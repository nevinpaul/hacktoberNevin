package com.example.kotlindemo.filter

import com.example.kotlindemo.service.AuthenticationService
import com.example.kotlindemo.service.UserService
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import java.lang.Exception
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtFilter @Autowired constructor(
                                        val authenticationService: AuthenticationService
                                        ) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest,
                                  response: HttpServletResponse,
                                  filter: FilterChain) {
        try {
            val PREFIX: String = "Bearer "
            val header = request.getHeader("Authorization")
            val token = header.replace(PREFIX, "")
            val value = Jwts.parser().setSigningKey("secret").parseClaimsJws(token).body
            val userDetails: UserDetails = authenticationService.loadUserByUsername(value.subject)
            val authentication = UsernamePasswordAuthenticationToken(userDetails, null, listOf())
            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authentication
        }catch(ex : Exception){
            ex.printStackTrace()
            response.sendError(401)
            return
        }
        try{
            filter.doFilter(request, response)
        }catch(ex : Exception){
            response.sendError(401)
            ex.printStackTrace()
            return
        }
        }

}
