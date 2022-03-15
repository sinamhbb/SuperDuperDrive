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

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getUserNotes(Integer userid) {
        return noteMapper.getUserNotes(userid);
    }

    public Note getNoteById(String noteid) {
        return noteMapper.getNoteById(Integer.parseInt(noteid));
    };

    public int upsertNote(NoteForm receivedNote, Integer userid) {
        if (receivedNote.getNoteid() != null) {
           return noteMapper.update(new Note(receivedNote.getNoteid(), receivedNote.getNotetitle(), receivedNote.getNotedescription(), userid));
        }
        return noteMapper.insert(new Note(null, receivedNote.getNotetitle(), receivedNote.getNotedescription(), userid));
    }

    public int deleteNote(String noteid) {
        return noteMapper.delete(Integer.parseInt(noteid));
    }

}
