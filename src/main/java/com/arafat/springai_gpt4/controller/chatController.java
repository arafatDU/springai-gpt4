package com.arafat.springai_gpt4.controller;

import com.arafat.springai_gpt4.dto.ImageDTO;
import com.arafat.springai_gpt4.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/ai")
public class chatController {

    @Autowired
    ChatService chatService;
    

    @PostMapping("/chat")
    public String generateResponse(@RequestParam String category, String year){
        //return chatService.gentChatResponse(category, year);
        return chatService.gentChatResponse(category, year).getResult().getOutput().getContent();
    }

    @PostMapping("/image/chat")
    public String generateRespImage(@RequestParam String query, @RequestBody ImageDTO imageDTO) throws IOException {
        return chatService.getImageChatReader(query, imageDTO.imageBase64());
    }


}