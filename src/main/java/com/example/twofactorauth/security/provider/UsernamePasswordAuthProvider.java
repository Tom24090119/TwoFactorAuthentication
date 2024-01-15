package com.example.twofactorauth.security.provider;

import com.example.twofactorauth.entities.Users;
import com.example.twofactorauth.repository.UsersRepository;
import com.example.twofactorauth.security.authentication.CustomUsernamePasswordAuthentication;
import com.example.twofactorauth.service.OtpGeneratorService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;



@Component
public class UsernamePasswordAuthProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final OtpGeneratorService otpGeneratorService;

    public UsernamePasswordAuthProvider(UserDetailsService userDetailsService, @Lazy PasswordEncoder passwordEncoder, UsersRepository usersRepository, OtpGeneratorService otpGeneratorService) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
        this.otpGeneratorService = otpGeneratorService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());

        String password = authentication.getCredentials().toString();

        if(userDetails.getUsername().equals(authentication.getName()) && passwordEncoder.matches(password, userDetails.getPassword()) && userDetails.isEnabled())
        {
            CustomUsernamePasswordAuthentication customUsernamePasswordAuthentication = new CustomUsernamePasswordAuthentication(true, authentication.getName(), null);

            Users user = usersRepository.findByUsername(authentication.getName()).get();

            if(user.getOtp() == 0) {
                user.setOtp(otpGeneratorService.generateOtp());
                usersRepository.save(user);
            }

            return customUsernamePasswordAuthentication;
        }


        throw new BadCredentialsException("Invalid username and password ");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(CustomUsernamePasswordAuthentication.class);
    }
}
