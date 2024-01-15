package com.example.twofactorauth.security.filter;

import com.example.twofactorauth.security.authentication.CustomOtpAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class OtpAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    public OtpAuthenticationFilter(AuthenticationManager manager) {
        this.authenticationManager = manager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        int otp = Integer.parseInt(request.getHeader("otp"));

        Authentication authenticate = authenticationManager.authenticate(new CustomOtpAuthentication(false, otp, request.getHeader("username")));

        if(authenticate != null){
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            filterChain.doFilter(request,response);
        }
        throw new BadCredentialsException("Something went wrong while authenticating OTP");

    }
}
