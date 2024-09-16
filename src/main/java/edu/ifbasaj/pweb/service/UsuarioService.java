package edu.ifbasaj.pweb.service;

import edu.ifbasaj.pweb.model.Usuario;
import edu.ifbasaj.pweb.model.Todo;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListSet;

@Service
public class UsuarioService {
    private final Set<Usuario> usuarioSet = new ConcurrentSkipListSet<>(Comparator.comparing(Usuario::getNome));

    public Set<Usuario> findAll(){
        return usuarioSet;

    }

    public void save(Usuario usuario){
        if(Objects.isNull(usuario.getId())){
            usuario.setId(UUID.randomUUID());
        }
        usuarioSet.add(usuario);
    }

    public void delete(Usuario usuario){
        usuarioSet.stream()
                .filter(u -> u.getId().equals(usuario.getId()))
                .forEach(usuarioSet::remove);
    }

    public Optional<Usuario> findById(UUID id) {
        return usuarioSet.stream()
                .filter(usuario -> usuario.getId().equals(id))
                .findFirst();
    }

}
