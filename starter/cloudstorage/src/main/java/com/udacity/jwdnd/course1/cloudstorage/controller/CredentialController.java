package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CredentialController {
    private final CredentialService credentialService;

    public CredentialController(CredentialService credentialService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
    }

    @PostMapping("/upsert-credential")
    public ModelAndView handleUpsertCredential(@ModelAttribute("newCredential") CredentialForm credentialForm, Model model, Authentication authentication, HttpServletRequest request) {
        request.setAttribute("tab", "credentials");
        try {
            credentialService.upsertCredential(credentialForm, authentication.getName());
            model.addAttribute("Credentials", credentialService.getUserCredentials(authentication.getName()));
            request.setAttribute("errorMessage", "null");

            return new ModelAndView("/result");

        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            return new ModelAndView("/result");
        }
    }

    @GetMapping("/delete-credential")
    public ModelAndView handleDeleteCredential(@RequestParam("credentialid") String credentialid, Model model, Authentication authentication, HttpServletRequest request) {
        request.setAttribute("tab", "credentials");
        try {
            credentialService.deleteCredential(credentialid);
            model.addAttribute("Credentials", credentialService.getUserCredentials(authentication.getName()));
            request.setAttribute("errorMessage", "null");
            return new ModelAndView("result");

        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            return new ModelAndView("result");
        }
    }

}
