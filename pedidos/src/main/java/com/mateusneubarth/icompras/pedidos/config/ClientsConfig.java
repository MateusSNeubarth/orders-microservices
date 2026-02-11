package com.mateusneubarth.icompras.pedidos.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.mateusneubarth.icompras.pedidos.client")
public class ClientsConfig {
}
