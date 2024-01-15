package com.example.twofactorauth.security.manager;

import com.example.twofactorauth.security.provider.OtpAuthProvider;
import com.example.twofactorauth.security.provider.UsernamePasswordAuthProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


@Component
public class CustomAuthenticationManager implements AuthenticationManager {


    private final UsernamePasswordAuthProvider usernamePasswordAuthProvider;

    private final OtpAuthProvider otpAuthProvider;

    public CustomAuthenticationManager(UsernamePasswordAuthProvider usernamePasswordAuthProvider, OtpAuthProvider otpAuthProvider) {
        this.usernamePasswordAuthProvider = usernamePasswordAuthProvider;
        this.otpAuthProvider = otpAuthProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if(usernamePasswordAuthProvider.supports(authentication.getClass())){
            return usernamePasswordAuthProvider.authenticate(authentication);
        }

        if(otpAuthProvider.supports(authentication.getClass()))
        {
            return otpAuthProvider.authenticate(authentication);
        }

        throw new BadCredentialsException("Did not find authentication provider");
    }
}
