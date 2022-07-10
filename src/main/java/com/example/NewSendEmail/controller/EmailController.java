package com.example.NewSendEmail.controller;


import com.example.NewSendEmail.DTO.CustomerInfoDTO;
import com.example.NewSendEmail.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/test")
    @CrossOrigin(origins = "*")
    public String check()
    {
        return "test";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/sendEmail", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendMailWithAttachment(@RequestParam(value = "file") MultipartFile file,@RequestParam String customerInfo)  {
        if (null == file.getOriginalFilename() || null == customerInfo) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(file.getOriginalFilename());
            Files.write(path, bytes);
            ObjectMapper mapper = new ObjectMapper();
            CustomerInfoDTO customerInfoDTO = mapper.readValue(customerInfo, CustomerInfoDTO.class);
            emailService.sendEmailWithAttachment(customerInfoDTO, path);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return ResponseEntity.ok("Email Sent ");
    }
}
