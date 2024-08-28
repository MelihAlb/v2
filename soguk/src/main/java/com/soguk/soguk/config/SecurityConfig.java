package com.soguk.soguk.config;

import com.soguk.soguk.utils.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        /*
        Bu method, kullanıcı doğrulaması yapmak için bir UserDetailsService bean'i döner.
        Ancak şu an loadUserByUsername(String nick) metodu içinde bir kullanıcı yükleme işlemi bulunmamakta,
        sadece null döndürmektedir. Gerçek bir uygulamada bu metod, kullanıcıyı veritabanından nick ile arar ve geri döner
         */
        return nick -> null;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf ->csrf.disable())
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/users","/entries","http://localhost:8080/topics","/topics/**","/entries/topic/**","/entries/{id}/like").permitAll()
                                .requestMatchers("/users/login","/user").permitAll()
                                .requestMatchers(HttpMethod.POST, "/topics/post").authenticated()
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Stateless oturum yönetimi
                );

        http.addFilterBefore(new JwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}