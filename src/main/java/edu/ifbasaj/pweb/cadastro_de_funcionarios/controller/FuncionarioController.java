package edu.ifbasaj.pweb.cadastro_de_funcionarios.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.model.dto.FuncionarioDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.service.funcionario.FuncionarioService;
import static edu.ifbasaj.pweb.cadastro_de_funcionarios.constants.PathConstants.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService service;
    
    @GetMapping(CADASTRAR_FUNCIONARIO)
    String getCadastrarFuncionario(Model model){
        
        model.addAttribute("funcionarioDTO", new FuncionarioDTO());

        return CADASTRAR_FUNCIONARIO;
    }

    @PostMapping(CADASTRAR_FUNCIONARIO)
    String postCadastrarFuncionario(@ModelAttribute @Valid FuncionarioDTO funcionarioDTO, RedirectAttributes reAtt){
        
        var optional = service.create(funcionarioDTO);

        if(optional.isPresent()){
            reAtt.addFlashAttribute("messageStyle", "fun-message fun-sucess");
            reAtt.addFlashAttribute("messageText", optional.get().getNome() + " foi cadastrado(a) com sucesso.");
        }

        return String.format("redirect:%s", CADASTRAR_FUNCIONARIO);
    }

    @GetMapping(GERENCIAR_FUNCIONARIO)
    String getGerenciarFuncionario(Model model){

        var funcionarioLista = service.findAll();

        model.addAttribute("funcionarioLista", funcionarioLista);

        return GERENCIAR_FUNCIONARIO;
    }
}
