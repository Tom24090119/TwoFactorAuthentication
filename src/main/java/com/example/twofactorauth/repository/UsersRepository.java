package com.example.twofactorauth.repository;


import com.example.twofactorauth.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,Integer> {

    Optional<Users> findByUsername(String username);

}
