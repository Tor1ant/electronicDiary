package com.sberbank.may.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ExceptionController implements ErrorController {

    private static final String PATH = "/error";
    @RequestMapping(value = PATH)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundPage() {
        return "exception_pages/error";
    }
}
