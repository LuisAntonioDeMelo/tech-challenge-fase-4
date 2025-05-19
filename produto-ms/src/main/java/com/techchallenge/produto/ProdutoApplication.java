package com.techchallenge.produto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCouchbaseRepositories(basePackages = "com.techchallenge.produto.infrastructure.persistence")
public class ProdutoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProdutoApplication.class, args);
    }
}

