package com.self.esteem.generators;

import org.springframework.stereotype.Component;

import java.util.Random;
@Component
public class OtpGenerator {
    public String generateOtp() {
        Random random = new Random();
        int otp = 1000 + random.nextInt(9000);
        return String.valueOf(otp);
    }
}
