package com.example.carros.api.upload;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

/**
 * Classe responsável por fazer a implementação do upload para a nuvem Google Storage
 * Referência: https://firebase.google.com/docs/storage/admin/start
 */
@Service
public class FirebaseStorageService {

    @PostConstruct // Para rodar apenas uma vez esse construtor
    private void init() throws IOException {
        // Verifica se realmente está vazia a instância
        if (FirebaseApp.getApps().isEmpty()) {
            // Lê o arquivo de configurações/chave privada
            InputStream in = FirebaseStorageService.class.getResourceAsStream("/serviceAccountKey.json");
            // Imprime resultado
            System.out.println(in);
            if (in != null) {
                // Configurações para autenticar na nuvem
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(in))
                        .setStorageBucket("curso-carros.appspot.com")
                        .setDatabaseUrl("https://curso-carros.firebaseio.com")
                        .build();
                // Inicializa instância
                FirebaseApp.initializeApp(options);
            } else {
                // Se ocorreu erro na leitura do arquivo de configurações imprime o erro
                System.err.println("Configure o arquivo serviceAccountKey.json do Firebase NOT FOUND!");
            }
        }
    }

    public String upload(UploadInput uploadInput) {

        Bucket bucket = StorageClient.getInstance().bucket();
        System.out.println(bucket);

//        Blob blob = bucket.create("nome.txt","Ricardo Ninja Lecheta".getBytes(), "text/html");

        byte[] bytes = Base64.getDecoder().decode(uploadInput.getBase64());

        String fileName = uploadInput.getFileName();
        Blob blob = bucket.create(fileName, bytes, uploadInput.getMimeType());

        // Assina URL válida por N dias
        //URL signedUrl = blob.signUrl(1, TimeUnit.DAYS);

        // Deixa URL pública
        blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

        return String.format("https://storage.googleapis.com/%s/%s", bucket.getName(), fileName);
    }
}

/**
 * service firebase.storage {
 * match /b/{bucket}/o {
 * match /{allPaths=**} {
 * allow read, write: if request.auth != null;
 * }
 * }
 * }
 * <p>
 * service firebase.storage {
 * match /b/{bucket}/o {
 * match /{allPaths=**} {
 * allow write: if request.auth != null;
 * }
 * match /{allPaths=**} {
 * allow read;
 * }
 * }
 * }
 * <p>
 * service firebase.storage {
 * match /b/{bucket}/o {
 * match /{allPaths=**} {
 * allow write: if request.auth != null;
 * }
 * match /{allPaths=**} {
 * allow read;
 * }
 * }
 * }
 **/

/**
 service firebase.storage {
 match /b/{bucket}/o {
 match /{allPaths=**} {
 allow write: if request.auth != null;
 }
 match /{allPaths=**} {
 allow read;
 }
 }
 }
 **/