package edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.controller;

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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("cargo/")
@RequiredArgsConstructor
public class CargoController {

    private final CargoService service;
    
    @GetMapping({"cadastrar_cargo"})
    String getPageCadastrarCargo(Model model){
        
        model.addAttribute("cargoDTO", new CargoDTO());

        return "cargo/cadastrar_cargo";
    }

    @GetMapping("gerenciar_cargo")
    String getPageGerenciarCargo(@RequestParam(value = "id", required = false) String id, Model model){

        var cargoLista = service.findAll();
        model.addAttribute("cargoLista", cargoLista);
        
        if(id != null){
            model.addAttribute("cargoSelecionado", service.findById(Long.parseLong(id)).get());
        }

        return "cargo/gerenciar_cargo";
    }

    @GetMapping("gerenciar_cargo/{id}")
    String getPageEditarCargo(@PathVariable Long id, Model model) {
        model.addAttribute("cargoSelecionado", service.findById(id).orElseThrow(() -> new RuntimeException("Cargo não encontrado")));
        return "cargo/edit_form_cargo";
    }

    @PostMapping("cadastrar_cargo")
    String postDataCadastrarCargo(@ModelAttribute @Valid CargoDTO cargoDTO, RedirectAttributes reAtt){
        
        var optional = service.create(cargoDTO);

        if(optional.isPresent()){
            reAtt.addFlashAttribute("messageStyle", "fun-message fun-sucess");
            reAtt.addFlashAttribute("messageText", optional.get().getNome() + " foi cadastrado(a) com sucesso.");
        }

        return "redirect:cadastrar_cargo";
    }

    @DeleteMapping("gerenciar_cargo/{id}")
    String deleteDataGerenciarCargo(@PathVariable Long id, RedirectAttributes reAtt){

        service.remove(id);

        reAtt.addFlashAttribute("messageStyle", "fun-message fun-sucess");
        reAtt.addFlashAttribute("messageText", "Removido com sucesso.");

        return "redirect:/cargo/gerenciar_cargo";
    }

    @PostMapping("gerenciar_cargo/{id}")
    String putDataGerenciarCargo(@PathVariable Long id, @ModelAttribute @Valid CargoDTO cargoDTO, RedirectAttributes reAtt) {
        cargoDTO.setId(id);
        var optional = service.update(cargoDTO);

        if(optional.isPresent()){
            reAtt.addFlashAttribute("messageStyle", "fun-message fun-sucess");
            reAtt.addFlashAttribute("messageText", optional.get().getNome() + " foi editado com sucesso.");
        }

        return "redirect:/cargo/gerenciar_cargo";
    }

}
