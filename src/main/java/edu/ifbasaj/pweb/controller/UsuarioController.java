package edu.ifbasaj.pweb.controller;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.ifbasaj.pweb.model.Usuario;
import edu.ifbasaj.pweb.service.UsuarioService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService userRepository;

    public UsuarioController(UsuarioService userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/list")
    public String showUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "userList";
    }

    @GetMapping("/add")
    public String showAddUser(Usuario usuario) {
        return "userAdd";
    }

    @PostMapping("/add")
    public String addUser(@Valid Usuario usuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "userAdd";
        }

        userRepository.save(usuario);
        return "redirect:/usuario/list";
    }
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") UUID id, Model model) {
        Usuario user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("user", user);
        return "userEdit";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") UUID id, @Valid Usuario user,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "userEdit";
        }

        Usuario userSalvo = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        userSalvo.setNome(user.getNome());

        // userRepository.save(user);
        return "redirect:/usuario/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") UUID id, Model model) {
        Usuario user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        return "redirect:/usuario/list";
    }

}
