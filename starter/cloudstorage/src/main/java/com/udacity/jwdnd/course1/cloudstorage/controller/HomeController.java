package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller()
@RequestMapping("/home")
public class HomeController {

 private final FileService fileService;
 private final NoteService noteService;

    public HomeController(FileService fileService, NoteService noteService) {
        this.fileService = fileService;
        this.noteService = noteService;
    }

    @GetMapping()
    public String homeView(@ModelAttribute("newNote") NoteForm newNote, Model model, Authentication authentication) {
        model.addAttribute("Files", fileService.getFiles(authentication.getName()));
        model.addAttribute("Notes", noteService.getUserNotes(authentication.getName()));
        return "home";
    }


}
