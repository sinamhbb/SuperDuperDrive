package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
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

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/upsert-note")
    public ModelAndView handleNewNote(@ModelAttribute("newNote") NoteForm newNote, Model model, Authentication authentication, HttpServletRequest request) {
        request.setAttribute("tab", "notes");
        try {
            noteService.upsertNote(newNote, authentication.getName());
            model.addAttribute("Notes", noteService.getUserNotes(authentication.getName()));
            request.setAttribute("errorMessage", "null");
            return new ModelAndView("/result");
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            return new ModelAndView("/result");
        }
    }

    @GetMapping("/delete-note")
    public ModelAndView handleDeleteNote(@RequestParam("noteid") String noteid, Model model, Authentication authentication, HttpServletRequest request) {
        request.setAttribute("tab", "notes");
        try {
            noteService.deleteNote(noteid);
            model.addAttribute("Notes", noteService.getUserNotes(authentication.getName()));
            request.setAttribute("errorMessage", "null");
            return new ModelAndView("/result");
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            return new ModelAndView("/result");
        }
    }
}