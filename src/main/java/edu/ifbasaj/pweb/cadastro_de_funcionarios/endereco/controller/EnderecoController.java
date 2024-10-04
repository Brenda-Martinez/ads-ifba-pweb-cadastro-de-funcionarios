package edu.ifbasaj.pweb.cadastro_de_funcionarios.endereco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.endereco.service.EnderecoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("endereco")
@RequiredArgsConstructor
public class EnderecoController {
    
    private final EnderecoService enderecoService;

    @GetMapping("visualizar_endereco")
    String getPageGerenciarFuncionario(@RequestParam(value = "id", required = false) String id, Model model){
        
        if(id == null){
            var enderecoLista = enderecoService.findAll();
            model.addAttribute("enderecos", enderecoLista);
        } else {
            var enderecoLista = enderecoService.findById(Long.parseLong(id));
            model.addAttribute("enderecos", enderecoLista);
        }

        return "endereco/visualizar_endereco";
    }

}
