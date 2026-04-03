package com.codesquad.cafe.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LoginFailedException.class)
    public String handleLoginFailed(LoginFailedException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/users/login";
    }

    @ExceptionHandler(NotOwnerException.class)
    public String handleNotOwner(NotOwnerException e, HttpServletRequest request,
                                 RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());

        String referer = request.getHeader("Referer");
        if (referer == null || referer.isEmpty()) {
            referer = "/";
        }

        return "redirect:" + referer;
    }
}
