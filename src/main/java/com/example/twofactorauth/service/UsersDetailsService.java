package com.example.twofactorauth.service;

import com.example.twofactorauth.entities.Users;
import com.example.twofactorauth.repository.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UsersDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    public UsersDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Users> user = usersRepository.findByUsername(username);

        if(user.isEmpty()){
            throw new UsernameNotFoundException("user with username not found");
        }

        return new UsersDetails(user.get());
    }
}
