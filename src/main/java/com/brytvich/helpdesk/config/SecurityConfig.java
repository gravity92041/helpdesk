package com.brytvich.helpdesk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.brytvich.helpdesk.util.myAccessDeniedHandler;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private final JWTFilter jwtFilter;
    private final myAccessDeniedHandler myAccessDeniedHandler;

    public SecurityConfig(JWTFilter jwtFilter, com.brytvich.helpdesk.util.myAccessDeniedHandler myAccessDeniedHandler) {
        this.jwtFilter = jwtFilter;

        this.myAccessDeniedHandler = myAccessDeniedHandler;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(requests->requests
                        .requestMatchers("/v1/api/auth/login","/swagger-ui/swagger-config"
                                ,"/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html").permitAll()
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN","MAIN")
                        .anyRequest().hasAnyRole("USER","ADMIN")
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf->csrf.disable())
//                .formLogin(form->form
////                                .loginPage("http://localhost:63343/front/LoginPage.html")
//                                .loginPage("/auth/login")
//                                .defaultSuccessUrl("/monkey",true)
//                                .failureUrl("/auth/login?error")
//                )
                .logout(logout->logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login")
                )
                .exceptionHandling(exception->exception.accessDeniedHandler(myAccessDeniedHandler))
                .sessionManagement(sessionManagement->sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        http
//                .authorizeHttpRequests(requests->requests
//                        .requestMatchers("/v1/api/auth/sigma","/sigma").permitAll())
//                .csrf(csrf->csrf.disable());
//        return http.build();
//    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

}
