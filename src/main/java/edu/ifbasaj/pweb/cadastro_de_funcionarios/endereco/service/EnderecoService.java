package edu.ifbasaj.pweb.cadastro_de_funcionarios.endereco.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.endereco.model.EnderecoDTO;
import reactor.core.publisher.Mono;

@Service
public class EnderecoService {
    
    private final WebClient webClient;

    public EnderecoService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    public Mono<List<EnderecoDTO>> findAll() {
        return webClient.get()
            .uri("/endereco/mockapi/get")
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<EnderecoDTO>>() {});
    }

    public Mono<EnderecoDTO> findById(Long enderecoId) {
        return webClient.get()
            .uri("/endereco/mockapi/get/{enderecoId}", enderecoId)
            .retrieve()
            .bodyToMono(EnderecoDTO.class);
    }

}
