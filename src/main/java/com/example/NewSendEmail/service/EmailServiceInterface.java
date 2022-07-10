package com.example.NewSendEmail.service;

import com.example.NewSendEmail.DTO.CustomerInfoDTO;
import java.nio.file.Path;

public interface EmailServiceInterface {
    void sendEmailWithAttachment(CustomerInfoDTO customerInfo, Path path);
}
