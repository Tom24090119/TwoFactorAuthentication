package com.example.twofactorauth.securityconfig;


import com.example.twofactorauth.entities.Users;
import com.example.twofactorauth.repository.UsersRepository;
import com.example.twofactorauth.security.filter.OtpAuthenticationFilter;
import com.example.twofactorauth.security.filter.UsernamePasswordAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.List;

@Configuration
public class SecurityConfig {

//    private final UsernamePasswordAuthFilter usernamePasswordAuthFilter;
//
//    public SecurityConfig(UsernamePasswordAuthFilter usernamePasswordAuthFilter) {
//        this.usernamePasswordAuthFilter = usernamePasswordAuthFilter;
//    }

    private final AuthenticationManager authenticationManager;

    @Autowired
    private UsersRepository usersRepository;

    public SecurityConfig(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .httpBasic(Customizer.withDefaults())
                .addFilterAt(new UsernamePasswordAuthFilter(authenticationManager),BasicAuthenticationFilter.class)
                .addFilterAfter(new OtpAuthenticationFilter(authenticationManager),BasicAuthenticationFilter.class)
                .authorizeHttpRequests(req -> req.anyRequest().authenticated())
                .build();
    }


    @Bean
    public CommandLineRunner commandLineRunner(){
        return args ->{
                usersRepository.save(new Users("tom", passwordEncoder().encode("1234"), true));
        };
    }

}
