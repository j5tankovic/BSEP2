package com.groot.education.controller.exception.resolver;

import com.groot.education.controller.exception.AuthenticationException;
import com.groot.education.controller.exception.NotFoundException;
import com.groot.education.controller.exception.UnprocessableEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity notFoundException(HttpServletRequest request, NotFoundException exception) {
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity unprocessableEntityException(HttpServletRequest request, UnprocessableEntityException exception) {
        return new ResponseEntity<>(exception, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity authenticationException(HttpServletRequest request, UnprocessableEntityException exception) {
        return new ResponseEntity<>(exception, HttpStatus.UNAUTHORIZED);
    }
}
