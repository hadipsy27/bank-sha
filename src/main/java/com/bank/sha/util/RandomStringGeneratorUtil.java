package com.bank.sha.util;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class RandomStringGeneratorUtil {
    public static String generateRandomUppercaseStringLetterAndNumber(int length) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        return sb.toString();
    }
}
