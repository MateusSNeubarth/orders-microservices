package com.mateusneubarth.icompras.faturamento.api;

import com.mateusneubarth.icompras.faturamento.bucket.BucketFile;
import com.mateusneubarth.icompras.faturamento.bucket.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Objects;

@RestController
@RequestMapping("/bucket")
@RequiredArgsConstructor
public class BucketController {

    private final BucketService bucketService;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try (InputStream is = file.getInputStream()) {
            MediaType type = MediaType.parseMediaType(Objects.requireNonNull(file.getContentType()));
            var bucketFile = new BucketFile(file.getOriginalFilename(), is, type, file.getSize());

            bucketService.upload(bucketFile);
            return ResponseEntity.ok("Arquivo salvo no bucket com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao salvar arquivo no bucket: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<String> getUrl(@RequestParam("filename") String filename) {
        try {
            return ResponseEntity.ok(bucketService.getUrl(filename));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao obter URL do arquivo: " + e.getMessage());
        }
    }
}
