package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
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
    private final UserService userService;

    public CredentialController(CredentialService credentialService, EncryptionService encryptionService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping("/upsert-credential")
    public ModelAndView handleUpsertCredential(@RequestParam("userid") String userid,
                                               @ModelAttribute("newCredential") CredentialForm credentialForm,
                                               Model model,
                                               Authentication authentication,
                                               HttpServletRequest request) {

        request.setAttribute("tab", "credentials");
        try {
            User user = userService.getUser(authentication.getName());
            if (Integer.parseInt(userid) == user.getUserid()) {
                credentialService.upsertCredential(credentialForm, user.getUserid());
                model.addAttribute("Credentials", credentialService.getUserCredentials(user.getUserid()));
                request.setAttribute("errorMessage", "null");

                return new ModelAndView("/result");
            } else {
                throw new SecurityException("You are not permitted to perform this action!");
            }

        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            return new ModelAndView("/result");
        }
    }

    @GetMapping("/delete-credential")
    public ModelAndView handleDeleteCredential(@RequestParam("userid") String userid,
                                               @RequestParam("credentialid") String credentialid,
                                               Model model,
                                               Authentication authentication,
                                               HttpServletRequest request) {

        request.setAttribute("tab", "credentials");
            try {
                User user = userService.getUser(authentication.getName());
                if (Integer.parseInt(userid) == user.getUserid()) {
                    credentialService.deleteCredential(credentialid);
                    model.addAttribute("Credentials", credentialService.getUserCredentials(user.getUserid()));
                    request.setAttribute("errorMessage", "null");
                    return new ModelAndView("result");
                } else {
                    throw new SecurityException("You are not permitted to perform this action!");
                }

            } catch (Exception e) {
                request.setAttribute("errorMessage", e.getMessage());
                return new ModelAndView("result");
            }
    }

}
