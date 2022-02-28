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

    public Note getNoteById(String noteid) {
        return noteMapper.getNoteById(Integer.parseInt(noteid));
    };

    public int upsertNote(NoteForm receivedNote, String username) {
        if (receivedNote.getNoteid() != null) {
           return noteMapper.update(new Note(receivedNote.getNoteid(), receivedNote.getNotetitle(), receivedNote.getNotedescription(), userMapper.getUser(username).getUserid()));
        }
        return noteMapper.insert(new Note(null, receivedNote.getNotetitle(), receivedNote.getNotedescription(), userMapper.getUser(username).getUserid()));
    }

    public int deleteNote(String noteid) {
        return noteMapper.delete(Integer.parseInt(noteid));
    }

}
