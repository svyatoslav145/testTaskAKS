package com.example.TestTaskOvsiyAurosKS.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NameExistsException.class)
    public ModelAndView handleNameExistenceException(Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("nameExistsException.html");
        modelAndView.addObject("message", e.getMessage());

        return modelAndView;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleEntityNotFoundException(Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("entityNotFoundException.html");
        modelAndView.addObject("message", e.getMessage());

        return modelAndView;
    }
}
