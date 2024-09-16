package edu.ifbasaj.pweb.model;

import java.util.UUID;

public record Todo(UUID id, String title) {
    public Todo(String title){
        this(UUID.randomUUID(), title);
    }
}
