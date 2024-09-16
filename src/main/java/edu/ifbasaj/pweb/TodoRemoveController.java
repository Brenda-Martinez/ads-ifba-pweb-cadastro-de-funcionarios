package edu.ifbasaj.pweb;

import edu.ifbasaj.pweb.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequestMapping("/removeTODO")
public class TodoRemoveController {

    private final TodoService todoService;

    public TodoRemoveController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public String save(@RequestParam("id") UUID id, Model model) {
        todoService.remove(id);
        model.addAttribute("todos", todoService.findAll());
        return "cadTodo";
    }

    @GetMapping("/{id}")
    public String getMethodName(@PathVariable UUID id, Model model) {
        todoService.remove(id);
        model.addAttribute("todos", todoService.findAll());
        return "listTodo";
    }

}
