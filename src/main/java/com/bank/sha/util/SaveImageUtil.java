package com.bank.sha.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class SaveImageUtil {

    private static final String IMAGE_DIRECTORY = "src/main/resources/static/images/";

    public void saveBase64Image(String base64String, String fileName) throws IOException {
        // Ensure directory exists
        File directory = new File(IMAGE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        if (base64String == null || base64String.isEmpty()) {
            throw new IllegalArgumentException("Base64 string is empty");
        }
        byte[] imageBytes = Base64.getDecoder().decode(base64String);
        try (FileOutputStream fos = new FileOutputStream(IMAGE_DIRECTORY + fileName)) {
            fos.write(imageBytes);
        }
    }

}
