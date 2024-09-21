package edu.ifbasaj.pweb.cadastro_de_funcionarios.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.model.dto.FuncionarioDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.service.funcionario.FuncionarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("funcionario/")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService service;
    
    @GetMapping({"cadastrar_funcionario", "/"})
    String getCadastrarFuncionario(Model model){
        
        model.addAttribute("funcionarioDTO", new FuncionarioDTO());

        return "funcionario/cadastrar_funcionario";
    }

    @PostMapping("cadastrar_funcionario")
    String postCadastrarFuncionario(@ModelAttribute @Valid FuncionarioDTO funcionarioDTO, RedirectAttributes reAtt){
        
        var optional = service.create(funcionarioDTO);

        if(optional.isPresent()){
            reAtt.addFlashAttribute("messageStyle", "fun-message fun-sucess");
            reAtt.addFlashAttribute("messageText", optional.get().getNome() + " foi cadastrado(a) com sucesso.");
        }

        return "redirect:cadastrar_funcionario";
    }

    @GetMapping("gerenciar_funcionario")
    String getGerenciarFuncionario(Model model){

        var funcionarioLista = service.findAll();

        model.addAttribute("funcionarioLista", funcionarioLista);

        return "funcionario/gerenciar_funcionario";
    }
}
