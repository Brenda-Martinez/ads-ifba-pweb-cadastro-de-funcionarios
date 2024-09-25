package edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.model.dto.DepartamentoDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.service.DepartamentoService;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.model.dto.FuncionarioDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.service.FuncionarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@RequestMapping("departamento/")
@RequiredArgsConstructor
public class DepartamentoController {

    private final FuncionarioService funcionarioService;

    private final DepartamentoService service;
    
    @GetMapping({"cadastrar_departamento"})
    String getPageCadastrarDepartamento(Model model){

        List<FuncionarioDTO> funcionarios = funcionarioService.findAll();

        model.addAttribute("funcionarios", funcionarios);
        model.addAttribute("departamentoDTO", new DepartamentoDTO());

        return "departamento/cadastrar_departamento";
    }

    @GetMapping("gerenciar_departamento")
    String getPageGerenciarDepartamento(@RequestParam(value = "id", required = false) String id, Model model) {

        var departamentoLista = service.findAll();
        model.addAttribute("departamentoLista", departamentoLista);

        

        if (id != null) {
            List<FuncionarioDTO> funcionarios = funcionarioService.findAll();
            model.addAttribute("funcionarios", funcionarios);
            
            var departamentoSelecionado = service.findById(UUID.fromString(id)).get();
            model.addAttribute("departamentoSelecionado", departamentoSelecionado);
        }

        return "departamento/gerenciar_departamento";
    }

    @GetMapping("gerenciar_departamento/{id}")
    String getPageEditarDepartamento(@PathVariable UUID id, RedirectAttributes reAtt, Model model){

        reAtt.addAttribute("id", id);

        return "redirect:/departamento/gerenciar_departamento";
    }

    @PostMapping("cadastrar_departamento")
    String postDataCadastrarDepartamento(@ModelAttribute @Valid DepartamentoDTO departamentoDTO, RedirectAttributes reAtt){
        
        var optional = service.create(departamentoDTO);

        if(optional.isPresent()){
            reAtt.addFlashAttribute("messageStyle", "fun-message fun-sucess");
            reAtt.addFlashAttribute("messageText", optional.get().getNome() + " foi cadastrado(a) com sucesso.");
        }

        return "redirect:cadastrar_departamento";
    }

    @DeleteMapping("gerenciar_departamento/{id}")
    String deleteDataGerenciarDepartamento(@PathVariable UUID id, RedirectAttributes reAtt){

        service.remove(id);

        reAtt.addFlashAttribute("messageStyle", "fun-message fun-sucess");
        reAtt.addFlashAttribute("messageText", "O departamento foi removido com sucesso.");

        return "redirect:/departamento/gerenciar_departamento";
    }

    @PutMapping("gerenciar_departamento")
    String putDataGerenciarDepartamento(@ModelAttribute @Valid DepartamentoDTO departamentoDTO,
        RedirectAttributes reAtt) {
        
        var optional = service.update(departamentoDTO);

        if(optional.isPresent()){
            reAtt.addFlashAttribute("messageStyle", "fun-message fun-sucess");
            reAtt.addFlashAttribute("messageText", optional.get().getNome() + " foi editado com sucesso.");
        }

        return "redirect:/departamento/gerenciar_departamento";
    }

}
