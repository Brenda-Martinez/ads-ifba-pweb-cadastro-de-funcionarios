package edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.controller;

import java.util.List;

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

import edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.model.dto.CargoDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.service.CargoService;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.model.dto.DepartamentoDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.service.DepartamentoService;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.endereco.service.EnderecoService;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.model.dto.FuncionarioDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.service.FuncionarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("funcionario/")
@RequiredArgsConstructor
public class FuncionarioController {

    private final EnderecoService enderecoService;
    private final CargoService cargoService;
    private final DepartamentoService departamentoService;
    private final FuncionarioService service;
    
    @GetMapping({"cadastrar_funcionario", ""})
    String getPageCadastrarFuncionario(Model model){
        
        model.addAttribute("enderecos", enderecoService.findAll());
        model.addAttribute("cargos", cargoService.findAll());
        model.addAttribute("departamentos", departamentoService.findAll());
        model.addAttribute("funcionarioDTO", new FuncionarioDTO());

        return "funcionario/cadastrar_funcionario";
    }

    @GetMapping("gerenciar_funcionario")
    String getPageGerenciarFuncionario(@RequestParam(value = "id", required = false) String id, Model model){

        var funcionarioLista = service.findAll();
        model.addAttribute("funcionarioLista", funcionarioLista);
        
        if(id != null){
            model.addAttribute("enderecos", enderecoService.findAll());
            model.addAttribute("cargos", cargoService.findAll());
            model.addAttribute("departamentos", departamentoService.findAll());
            model.addAttribute("funcionarioSelecionado", service.findById(Long.parseLong(id)).get());
        }

        return "funcionario/gerenciar_funcionario";
    }

    @GetMapping("gerenciar_funcionario/{id}")
    String getPageEditarFuncionario(@PathVariable Long id, Model model){

        List<DepartamentoDTO> departamentos = departamentoService.findAll();
        model.addAttribute("departamentos", departamentos);
        
        List<CargoDTO> cargos = cargoService.findAll();
        model.addAttribute("cargos", cargos);

        model.addAttribute("funcionarioSelecionado", service.findById(id).orElseThrow(() -> new RuntimeException("Funcionario nao encontrado")));

        return "funcionario/edit_form";
    }

    @PostMapping("cadastrar_funcionario")
    String postDataCadastrarFuncionario(@ModelAttribute @Valid FuncionarioDTO funcionarioDTO, RedirectAttributes reAtt){
        
        var optional = service.create(funcionarioDTO);

        if(optional.isPresent()){
            reAtt.addFlashAttribute("messageStyle", "fun-message fun-sucess");
            reAtt.addFlashAttribute("messageText", optional.get().getNome() + " foi cadastrado(a) com sucesso.");
        }

        return "redirect:cadastrar_funcionario";
    }

    @DeleteMapping("gerenciar_funcionario/{id}")
    String deleteDataGerenciarFuncionario(@PathVariable Long id, RedirectAttributes reAtt){

        service.remove(id);

        reAtt.addFlashAttribute("messageStyle", "fun-message fun-sucess");
        reAtt.addFlashAttribute("messageText", "Funcionário foi removido com sucesso.");

        return "redirect:/funcionario/gerenciar_funcionario";
    }

    @PostMapping("gerenciar_funcionario/{id}")
    String putDataGerenciarFuncionario(@ModelAttribute @Valid FuncionarioDTO funcionarioDTO,
        RedirectAttributes reAtt) {
        
        var optional = service.update(funcionarioDTO);

        if(optional.isPresent()){
            reAtt.addFlashAttribute("messageStyle", "fun-message fun-sucess");
            reAtt.addFlashAttribute("messageText", optional.get().getNome() + " foi editao(a) com sucesso.");
        }

        return "redirect:/funcionario/gerenciar_funcionario";
    }
}
