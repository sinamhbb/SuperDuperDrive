package com.udacity.jwdnd.course1.cloudstorage.mapper.provider;


import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.jdbc.SQL;


public class CredentialProvider {
    private static final String TABLE_NAME = "CREDENTIALS";

    public String getUserCredentials(Integer userid){
        return new SQL () {{
            SELECT("*").FROM(TABLE_NAME).WHERE("userid=#{userid}");
        }}.toString();
    }

    public String getCredentialById(Integer credentialid) {
        return new SQL() {{
            SELECT("*").FROM(TABLE_NAME).WHERE("credentialid=#{credentialid}");
        }}.toString();
    }

    public String insert(Credential credential) {
        return new SQL() {{
            INSERT_INTO(TABLE_NAME)
                    .VALUES("url", "#{url}")
                    .VALUES("username", "#{username}")
                    .VALUES("key", "#{key}")
                    .VALUES("password", "#{password}")
                    .VALUES("userid", "#{userid}");
        }}.toString();
    }

    public String update(Credential credential) {
        return new SQL() {{
            UPDATE(TABLE_NAME)
                    .SET("url=#{url}")
                    .SET("username=#{username}")
                    .SET("key=#{key}")
                    .SET("password=#{password}")
                    .WHERE("credentialid=#{credentialid}");
        }}.toString();
    }

    public String delete(Integer credentialid) {
        return new SQL() {{
            DELETE_FROM(TABLE_NAME).WHERE("credentialid=#{credentialid}");
        }}.toString();
    }

}
