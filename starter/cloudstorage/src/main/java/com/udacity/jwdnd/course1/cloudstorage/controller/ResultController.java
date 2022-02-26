package com.udacity.jwdnd.course1.cloudstorage.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ResultController {

    @GetMapping("/result")
    public RedirectView forwardedWithParams(final RedirectAttributes redirectAttributes, HttpServletRequest request) {
        redirectAttributes.addAttribute("errorMessage", request.getAttribute("errorMessage"));
        return new RedirectView("redirect:");
    }
}
