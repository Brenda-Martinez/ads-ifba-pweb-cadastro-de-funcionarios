package edu.ifbasaj.pweb.service;

import org.springframework.stereotype.Service;

import edu.ifbasaj.pweb.model.Todo;

import java.util.Comparator;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListSet;

@Service
public class TodoService {
    private final Set<Todo> todoSet = new ConcurrentSkipListSet<>(Comparator.comparing(Todo::title));

    public Set<Todo> findAll(){
        return todoSet;

    }

    public void add(Todo todo){
        todoSet.add(todo);
    }

    public void remove(UUID id){
        todoSet.stream()
                .filter(todo -> todo.id().equals(id))
                .forEach(todoSet::remove);
    }

}
