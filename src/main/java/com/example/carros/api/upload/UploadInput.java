package com.example.carros.api.upload;

import lombok.Data;

@Data
class UploadInput {
    private String fileName;
    private String base64;
    private String mimeType;
}