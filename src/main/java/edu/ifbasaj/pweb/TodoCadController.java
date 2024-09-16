package edu.ifbasaj.pweb;


import edu.ifbasaj.pweb.model.Todo;
import edu.ifbasaj.pweb.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/addTODO")
public class TodoCadController {

    private final TodoService todoService;

    public TodoCadController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public String todos(Model model){
        model.addAttribute("todos", todoService.findAll());
        return "cadTodo";
    }
    @PostMapping
    public  String save(@RequestParam("title") String title, Model model){
        todoService.add(new Todo(title));
        model.addAttribute("todos", todoService.findAll());
        return "cadTodo";
    }

}
