package com.example.carros.api.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Class controller para o endpoint de upload
 */
@RestController
@RequestMapping("/api/v1/upload")
public class UploadController {

    /**
     * Injenção de dependência do serviço que realizará o upload para a nuvem
     */
    @Autowired
    private FirebaseStorageService uploadService;

//    @PostMapping("/upload")
//    public ResponseEntity upload(@RequestParam String fileName, @RequestParam String base64) {
//        String s = "Filename: " + fileName + " >> base64 > " + base64;
//        return ResponseEntity.ok(s);
//    }

    @PostMapping
    public ResponseEntity upload(@RequestBody UploadInput uploadInput) throws IOException {
        // String url = "Filename: " + uploadInput.getFileName() + " >> base64 > " + uploadInput.getBase64();
        // Executa upload
        String url = uploadService.upload(uploadInput);
        // Retorna o objeto output com a URL do arquivo na nuvem
        return ResponseEntity.ok(new UploadOutput(url));
    }
}