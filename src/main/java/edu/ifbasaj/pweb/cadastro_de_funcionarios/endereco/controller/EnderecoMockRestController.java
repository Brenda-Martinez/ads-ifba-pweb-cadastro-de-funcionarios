package edu.ifbasaj.pweb.cadastro_de_funcionarios.endereco.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.endereco.model.EnderecoDTO;

@RestController
@RequestMapping("/endereco/mockapi")
public class EnderecoMockRestController {
    
    @GetMapping("/get")
    public ResponseEntity<List<EnderecoDTO>> findAll(){

        return ResponseEntity.ok().body(getEnderecos());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<EnderecoDTO> findById(@PathVariable String id){

        return getEnderecos().stream()
        .filter(endereco -> endereco.getId() == Long.parseLong(id))
        .findFirst()
        .map(endereco -> ResponseEntity.ok().body(endereco))
        .orElse(ResponseEntity.notFound().build()); 
    }

    private List<EnderecoDTO> getEnderecos(){

        List<EnderecoDTO> listaEndereco = new ArrayList<>();

        listaEndereco
        .add(EnderecoDTO.builder()
        .id(1L)
        .logradouro("Rua 2 de Julho")
        .numero("234")
        .complemento(null)
        .bairro("Centro")
        .cidade("Santo Antônio de Jesus")
        .estado("Bahia")
        .cep("4444-000")
        .pais("Brasil").build());

        listaEndereco
        .add(EnderecoDTO.builder()
        .id(2L)
        .logradouro("Avenida Alberto Passos")
        .numero("456")
        .complemento(null)
        .bairro("São Benedito")
        .cidade("Santo Antônio de Jesus")
        .estado("Bahia")
        .cep("2222-000")
        .pais("Brasil").build());

        listaEndereco
        .add(EnderecoDTO.builder()
        .id(3L)
        .logradouro("Praça João Almeida")
        .numero("321")
        .complemento(null)
        .bairro("Centro")
        .cidade("Santo Antônio de Jesus")
        .estado("Bahia")
        .cep("3333-000")
        .pais("Brasil").build());

        return listaEndereco;
    }
}
