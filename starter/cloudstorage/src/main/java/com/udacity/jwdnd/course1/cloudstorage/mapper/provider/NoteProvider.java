package com.udacity.jwdnd.course1.cloudstorage.mapper.provider;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.jdbc.SQL;

public class NoteProvider {

    private static final String TABLE_NAME = "NOTES";

    public String existingNoteCount(String notetitle) {
        return new SQL() {{
            SELECT("COUNT(*)").FROM(TABLE_NAME).WHERE("notetitle=#{notetitle}");
        }}.toString();
    }

    public String insert(Note note) {

        return new SQL() {{
            INSERT_INTO(TABLE_NAME)
                    .VALUES("notetitle", "#{notetitle}")
                    .VALUES("notedescription", "#{notedescription}")
                    .VALUES("userid", "#{userid}");
        }}.toString();
    }

}
