package com.mateusneubarth.icompras.faturamento.bucket;

import lombok.Builder;
import org.springframework.http.MediaType;

import java.io.InputStream;

@Builder
public record BucketFile(
        String name,
        InputStream is,
        MediaType type,
        long size
) {
}
