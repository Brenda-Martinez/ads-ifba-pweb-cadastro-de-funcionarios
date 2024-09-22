package edu.ifbasaj.pweb.cadastro_de_funcionarios.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class DefaultControllerAdvice {

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public RedirectView handleEntityNotFoundException(EntityNotFoundException ex, RedirectAttributes reAtt,
        HttpServletRequest request){

        reAtt.addFlashAttribute("messageStyle", "fun-message fun-error");
        reAtt.addFlashAttribute("messageText", "Não foi possível encontrar este usuário no sistema.");

        return new RedirectView(request.getRequestURI().toString());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public RedirectView handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
        RedirectAttributes reAtt, HttpServletRequest request) {
        
        List<String> erros = new ArrayList<>();

        for (var e : ex.getFieldErrors()) {

            if(e.getField().equals("salario") && e.getRejectedValue() != null){
                erros.add("O campo salario deve conter apenas números.");
            }
            else if (e.getField().equals("dataNascimento") && e.getRejectedValue() != null) {
                erros.add("O campo data de nascimento deve ser válido, ex: yyyy-mm-dd");
            }
            else if (e.getField().equals("salarioBase") && e.getRejectedValue() != null) {
                erros.add("O campo salario deve conter apenas números.");
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

        return new RedirectView(request.getRequestURI().toString());
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public RedirectView handleIllegalArgumentException(IllegalArgumentException ex, RedirectAttributes reAtt,
        HttpServletRequest request) {
        
        List<String> erros = new ArrayList<>();

        erros.add(ex.getMessage());

        reAtt.addFlashAttribute("messageStyle", "fun-message fun-error");
        reAtt.addFlashAttribute("messageText", erros);

        return new RedirectView(request.getRequestURI().toString());
    }

}