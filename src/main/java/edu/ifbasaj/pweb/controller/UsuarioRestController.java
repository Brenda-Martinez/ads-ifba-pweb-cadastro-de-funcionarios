package edu.ifbasaj.pweb.controller;

import java.util.List;
import java.util.Set;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ifbasaj.pweb.model.Usuario;
import edu.ifbasaj.pweb.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {

    private final UsuarioService userRepository;

    public UsuarioRestController(UsuarioService userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public Set<Usuario> showUserList() {
        return userRepository.findAll();
    }



}
