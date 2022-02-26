package com.udacity.jwdnd.course1.cloudstorage.mapper.provider;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.jdbc.SQL;

public class FileProvider {

    private static final String TABLE_NAME = "FILES";

    public String countFile(String filename) {
        return new SQL() {{
            SELECT("COUNT(*)").FROM(TABLE_NAME).WHERE("filename = #{filename}");
        }}.toString();
    }

    public String insert(File file) {
        return new SQL() {{
            INSERT_INTO(TABLE_NAME)
                    .VALUES("filename", "#{filename}")
                    .VALUES("contenttype", "#{contenttype}")
                    .VALUES("filesize", "#{filesize}")
                    .VALUES("userid", "#{userid}")
                    .VALUES("filedata","#{filedata}");
        }}.toString();
    }

    public String deleteById(int fileId) {
        return new SQL() {{
           DELETE_FROM(TABLE_NAME).WHERE("fileId = ${fileId}");
        }}.toString();
    }
}
