package edu.ifbasaj.pweb.cadastro_de_funcionarios.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
public class DefaultControllerAdvice {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public RedirectView handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, RedirectAttributes reAtt) {
        
        List<String> erros = new ArrayList<>();

        for (var e : ex.getFieldErrors()) {

            if(e.getField().equals("salario") && e.getRejectedValue() != null){
                erros.add("O campo salario deve conter apenas números.");
            }
            else if (e.getField().equals("dataNascimento") && e.getRejectedValue() != null) {
                erros.add("O campo data de nascimento deve ser válido, ex: yyyy-mm-dd");
            }
            else if (e.getField().equals("dataAdmissao") && e.getRejectedValue() != null) {
                erros.add("O campo data de admissão deve ser válido, ex: yyyy-mm-dd");
            }
            else {
                erros.add(e.getDefaultMessage());
            }
            
        }

        reAtt.addFlashAttribute("messageStyle", "fun-message fun-error");
        reAtt.addFlashAttribute("messageText", erros);

        return new RedirectView("cadastrar_funcionario");
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public RedirectView handleIllegalArgumentException(IllegalArgumentException ex, RedirectAttributes reAtt) {
        
        List<String> erros = new ArrayList<>();

        erros.add(ex.getMessage());

        reAtt.addFlashAttribute("messageStyle", "fun-message fun-error");
        reAtt.addFlashAttribute("messageText", erros);

        return new RedirectView("cadastrar_funcionario");
    }

}
