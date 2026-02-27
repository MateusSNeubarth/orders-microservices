package com.mateusneubarth.icompras.faturamento.subscriber;

import com.mateusneubarth.icompras.faturamento.bucket.BucketFile;
import com.mateusneubarth.icompras.faturamento.bucket.BucketService;
import com.mateusneubarth.icompras.faturamento.model.Pedido;
import com.mateusneubarth.icompras.faturamento.service.NotaFiscalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@Component
@Slf4j
@RequiredArgsConstructor
public class GeradorNotaFiscalService {

    private final NotaFiscalService notaFiscalService;
    private final BucketService bucketService;

    public void gerar(Pedido pedido) {
        log.info("Gerando nota fiscal do pedido: {}", pedido.codigo());

        try {
            byte[] byteArray = notaFiscalService.gerarNota(pedido);

            String nomeArquivo = String.format("notafiscal_pedido_%d.pdf", pedido.codigo());

            var file = BucketFile.builder()
                    .name(nomeArquivo)
                    .is(new ByteArrayInputStream(byteArray))
                    .type(MediaType.APPLICATION_PDF)
                    .size(byteArray.length)
                    .build();

            bucketService.upload(file);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
