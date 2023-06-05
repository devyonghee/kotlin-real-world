package me.devyonghee.kotlinrealworld.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfiguration(
    private val jsonWebTokenFilter: JsonWebTokenSecurity,
    private val accountUserDetailsService: AccountUserDetailsService,
) {

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity.httpBasic()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .csrf().disable()
            .cors().disable()
            .userDetailsService(accountUserDetailsService)
            .authorizeHttpRequests()
            .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/users/login").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/profiles/*").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/tags").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/articles/*/comments").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterAt(jsonWebTokenFilter, BasicAuthenticationFilter::class.java)
            .build()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
