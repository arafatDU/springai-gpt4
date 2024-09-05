package com.arafat.springai_gpt4.controller;

import com.arafat.springai_gpt4.dto.ImageDTO;
import com.arafat.springai_gpt4.service.ChatService;
import com.arafat.springai_gpt4.service.GlobalImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/ai")
public class chatController {

    @Autowired
    ChatService chatService;

    @Autowired
    GlobalImage globalImage;

    @PostMapping("/chat")
    public String generateResponse(@RequestParam String category, String year){
        //return chatService.gentChatResponse(category, year);
        return chatService.gentChatResponse(category, year).getResult().getOutput().getContent();
    }

    @PostMapping("/image/chat")
    public String generateRespImage(@RequestParam String query, @RequestBody ImageDTO imageDTO) throws IOException {
        return chatService.getImageChatReader(query, imageDTO.imageBase64());
    }

//    @PostMapping("/image/chat")
//    public String generateRespImage(@RequestBody Map<String, String> requestBody) throws IOException {
//        String query = requestBody.get("query");
//        String imageBase64 = requestBody.get("imageBase64");
//
//        // Update the global image base64
//        if(imageBase64 != null)
//            globalImage.setImageBase64(imageBase64);
//
//        return chatService.getImageChatReader(query);
//    }

}