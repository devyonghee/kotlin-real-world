package me.devyonghee.kotlinrealworld.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfiguration {

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity.httpBasic()
            .and()
            .csrf().disable()
            .cors().disable()
            .formLogin()
            .usernameParameter("email")
            .and()
            .authorizeHttpRequests()
            .requestMatchers("").permitAll()
            .and()
            .build()
    }
}
