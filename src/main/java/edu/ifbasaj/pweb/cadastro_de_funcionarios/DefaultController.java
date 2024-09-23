package edu.ifbasaj.pweb.cadastro_de_funcionarios;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
class DefaultController {

    @GetMapping({"", "index"})
    public String getPageIndex(){

        return "index";
    }
}