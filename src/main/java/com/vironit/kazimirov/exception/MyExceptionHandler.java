package com.vironit.kazimirov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@RestControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler({ClientNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView absentOfClient() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tryLogin");
        return modelAndView;
    }

    @ExceptionHandler({RepeatitionException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void presentElement() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("tryLogin");
//        return modelAndView;
    }

    @ExceptionHandler({GoodException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void goodHandle() {
    }

    @ExceptionHandler({GoodNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView goodNotFoundHandle() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tryLogin");
        return modelAndView;
    }

    @ExceptionHandler({SubsectionNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView subsectionNotFoundHandle() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tryLogin");
        return modelAndView;
    }

    @ExceptionHandler({PurposeNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void purposeNotFoundHandle() {

    }

    @ExceptionHandler({PurchaseException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void purchaseHandle() {

    }
}
