package com.example.twofactorauth.security.filter;

import com.example.twofactorauth.repository.UsersRepository;
import com.example.twofactorauth.security.authentication.CustomUsernamePasswordAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class UsernamePasswordAuthFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    public UsernamePasswordAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = request.getHeader("username");
        String password = request.getHeader("password");

        String otp = request.getHeader("otp");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null){

            Authentication authenticate = authenticationManager.authenticate(new CustomUsernamePasswordAuthentication(false, username, password));

            if(otp != null && checkFormat(otp)){
                filterChain.doFilter(request,response);
            }

            response.getWriter().write("Otp has been sent");
        }

    }

    private boolean checkFormat(String otp){
        try{
            if(otp.trim().length() == 4) {
                Integer.parseInt(otp);
                return true;
            }
            else{
                throw new IllegalArgumentException("Invalid OTP");
            }
        }
        catch (IllegalArgumentException e){
            return false;
        }
    }

}
