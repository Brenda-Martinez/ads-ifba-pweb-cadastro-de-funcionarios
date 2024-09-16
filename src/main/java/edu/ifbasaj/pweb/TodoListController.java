package edu.ifbasaj.pweb;


import edu.ifbasaj.pweb.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping({"/","/listTODO"})
@RequestMapping("/listTODO")
public class TodoListController {

    private final TodoService todoService;

    public TodoListController(TodoService todoService) {
        this.todoService = todoService;
    }


    @GetMapping
    public String todos(Model model) {
        model.addAttribute("todos", todoService.findAll());
        return "listTodo";
    }
}
