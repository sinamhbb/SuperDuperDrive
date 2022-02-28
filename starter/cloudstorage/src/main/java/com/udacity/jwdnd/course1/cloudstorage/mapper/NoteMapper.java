package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.mapper.provider.NoteProvider;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE userid = ${userid}")
    List<Note> getUserNotes(int userid);

    @Select("SELECT * FROM NOTES WHERE noteid = ${noteid}")
    Note getNoteById(int noteid);

    @SelectProvider(type = NoteProvider.class, method = "existingNoteCount")
    int existingNoteCount(String notetitle);

    @InsertProvider(type = NoteProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(Note note);

    @DeleteProvider(type = NoteProvider.class, method = "delete")
    int delete(int noteid);

    @UpdateProvider(type = NoteProvider.class, method = "update")
    int update(Note note);
}
