package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;
    private final UserMapper userMapper;

    public NoteService(NoteMapper noteMapper, UserMapper userMapper) {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
    }

    public List<Note> getUserNotes(String username) {
        User user = userMapper.getUser(username);
        return noteMapper.getUserNotes(user.getUserid());
    }

    public int saveNewNote(NoteForm newNote, String username) {
        if (noteMapper.existingNoteCount(newNote.getNotetitle()) > 0) {
            throw new IllegalArgumentException("You have uploaded the same file Before");
        }
        return noteMapper.insert(new Note(null, newNote.getNotetitle(), newNote.getNotedescription(), userMapper.getUser(username).getUserid()));
    }
}
