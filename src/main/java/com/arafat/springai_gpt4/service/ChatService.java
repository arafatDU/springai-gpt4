package com.arafat.springai_gpt4.service;


import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ChatService {

    @Autowired
    ChatModel chatClient;

    @Autowired
    GlobalImage globalImage;

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    public ChatResponse gentChatResponse(String category, String year){

        PromptTemplate promptTemplate = new PromptTemplate(
                """
                Please provide me best book for the given {category} and the {year}.
                Please do provide a summary of the book as well, the information should be 
                limited and not much in depth. Please provide the details in the JSON format
                containing this information : category, book, year, review, author, summary
                """
        );

        promptTemplate.add("category", category);
        promptTemplate.add("year", year);

        Prompt prompt = promptTemplate.create();

        return chatClient.call(prompt);

    }


    public String getImageChatReader(String query, String imageBase64) {
        // Decode the base64 image to byte array
        logger.info("Query: {}", query);
        logger.info("Image Base64: {}", imageBase64);

        byte[] imageData = Base64.getDecoder().decode(imageBase64);

        UserMessage userMessage = new UserMessage(query,
                List.of(new Media(MimeTypeUtils.IMAGE_PNG, imageData)));

        ChatResponse response = chatClient.call(new Prompt(userMessage));
        return response.getResult().getOutput().getContent();
    }

//    public String getImageChatReader(String query) {
//        // Get the latest image base64 from GlobalImageStorage
//        String imageBase64 = globalImage.getImageBase64();
//
//        if (imageBase64 == null || imageBase64.isEmpty()) {
//            throw new IllegalArgumentException("Image data not provided.");
//        }
//
//        byte[] imageData = decodeBase64(imageBase64);
//
//        // Create a UserMessage with the query and the image as Media
//        UserMessage userMessage = new UserMessage(query,
//                List.of(new Media(MimeTypeUtils.IMAGE_PNG, imageData)));
//
//        var response = chatClient.call(new Prompt(userMessage));
//        return response.getResult().getOutput().getContent();
//    }

//    private String inferMimeType(String base64) {
//        if (base64.startsWith("/9j/")) {
//            return "image/jpeg";
//        } else if (base64.startsWith("iVBORw0KGgo")) {
//            return "image/png";
//        } else if (base64.startsWith("R0lGODlh")) {
//            return "image/gif";
//        } else {
//            // Default to PNG if the type can't be determined
//            return "image/png";
//        }
//    }

    private byte[] decodeBase64(String base64) {
        // Remove the data URL prefix if it exists
        if (base64.contains(",")) {
            base64 = base64.split(",")[1];
        }
        return java.util.Base64.getDecoder().decode(base64);
    }
}

