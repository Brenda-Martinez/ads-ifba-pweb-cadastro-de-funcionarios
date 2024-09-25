package edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.model.dto.FuncionarioDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.service.FuncionarioService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("funcionario/api")
@RequiredArgsConstructor
public class FuncionarioAPIRestController {
    
    private final FuncionarioService service;

    @GetMapping("/get")
    public ResponseEntity<List<FuncionarioDTO>> findAll(){

        var list = service.findAll();

        if(!list.isEmpty()){
            return ResponseEntity.ok().body(list);
        }
        
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<FuncionarioDTO> findById(@PathVariable String id){

        var opt = service.findById(UUID.fromString(id));
        if(opt.isPresent()){
            return ResponseEntity.ok().body(opt.get());
        }
        
        return ResponseEntity.notFound().build();
    }
}
