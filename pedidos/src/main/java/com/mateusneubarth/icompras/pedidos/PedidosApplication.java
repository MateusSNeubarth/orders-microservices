package com.mateusneubarth.icompras.pedidos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class PedidosApplication {

	// @Bean
	public CommandLineRunner commandLineRunner(KafkaTemplate<String, String> template) {
		return args -> {
			Thread.sleep(5000);
			template.send("icompras.pedidos-pagos", "dados", "{ json com os dados do pedido }");
			System.out.println("Mensagem enviada!");
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(PedidosApplication.class, args);
	}

}
