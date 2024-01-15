package com.example.twofactorauth.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpGeneratorService {
    public int generateOtp(){
        Random random = new Random();
        return 1000 + random.nextInt(9000);
    }
}
