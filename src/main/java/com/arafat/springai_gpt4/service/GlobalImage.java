package com.arafat.springai_gpt4.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

@Service
public class GlobalImage implements InitializingBean {

    private String imageBase64;

    @Override
    public void afterPropertiesSet() throws IOException {
        // Load the image from the classpath and convert it to Base64
        byte[] imageData = new ClassPathResource("/image/azure.png").getContentAsByteArray();
        this.imageBase64 = Base64.getEncoder().encodeToString(imageData);
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
