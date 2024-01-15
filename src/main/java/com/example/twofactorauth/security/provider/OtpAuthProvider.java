package com.example.twofactorauth.security.provider;

import com.example.twofactorauth.entities.Users;
import com.example.twofactorauth.repository.UsersRepository;
import com.example.twofactorauth.security.authentication.CustomOtpAuthentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OtpAuthProvider implements AuthenticationProvider {

    private final UsersRepository usersRepository;

    public OtpAuthProvider(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional<Users> optionalUsers = usersRepository.findByUsername(authentication.getName());
        if(optionalUsers.isPresent()){

            Users user = optionalUsers.get();
            if(user.getOtp() == (int)authentication.getCredentials()){
                return new CustomOtpAuthentication(true,0, authentication.getName());
            }
        }
        throw new BadCredentialsException("Invalid OTP sent");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomOtpAuthentication.class.equals(authentication);
    }
}
