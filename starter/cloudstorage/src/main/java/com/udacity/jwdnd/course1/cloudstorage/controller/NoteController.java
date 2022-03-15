package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
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

@Controller()
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/upsert-note")
    public ModelAndView handleNewNote(@RequestParam("userid") String userid,
                                      @ModelAttribute("newNote") NoteForm newNote,
                                      Model model, Authentication authentication,
                                      HttpServletRequest request) {
        request.setAttribute("tab", "notes");
        try {
            User user = userService.getUser(authentication.getName());
            if (Integer.parseInt(userid) == user.getUserid()) {
                noteService.upsertNote(newNote, user.getUserid());
                model.addAttribute("Notes", noteService.getUserNotes(user.getUserid()));
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

    @GetMapping("/delete-note")
    public ModelAndView handleDeleteNote(@RequestParam("userid") String userid,
                                         @RequestParam("noteid") String noteid,
                                         Model model,
                                         Authentication authentication,
                                         HttpServletRequest request) {
        request.setAttribute("tab", "notes");
        try {
            User user = userService.getUser(authentication.getName());
            if (Integer.parseInt(userid) == user.getUserid()) {
                noteService.deleteNote(noteid);
                model.addAttribute("Notes", noteService.getUserNotes(user.getUserid()));
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
}
