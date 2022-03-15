package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller()
@RequestMapping("/home")
public class HomeController {

 private final FileService fileService;
 private final NoteService noteService;
 private final CredentialService credentialService;
 private final UserService userService;

    public HomeController(FileService fileService, NoteService noteService, CredentialService credentialService, UserService userService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @GetMapping()
    public String homeView(@ModelAttribute("newCredential") CredentialForm credentialForm, @ModelAttribute("newNote") NoteForm newNote, Model model, Authentication authentication) {
        User user = userService.getUser(authentication.getName());
        model.addAttribute("user", user.getUsername());
        model.addAttribute("userid", user.getUserid().toString());
        model.addAttribute("Files", fileService.getFiles(user.getUserid()));
        model.addAttribute("Notes", noteService.getUserNotes(user.getUserid()));
        model.addAttribute("Credentials", credentialService.getUserCredentials(user.getUserid()));
        return "home";
    }
}
