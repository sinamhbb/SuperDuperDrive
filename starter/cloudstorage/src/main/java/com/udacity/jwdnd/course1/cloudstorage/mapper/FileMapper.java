package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.mapper.provider.FileProvider;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE userid = ${userid}")
    List<File> getUserFiles(int userid);

    @Select("SELECT * FROM FILES WHERE fileId = ${fileId}")
    File getFile(Integer fileId);

    @SelectProvider(type = FileProvider.class, method = "countFile")
    int existingFileCount(String filename);

    @InsertProvider(type = FileProvider.class, method = "insert")
    @Options(useGeneratedKeys = true,keyProperty = "fileId")
    int insert(File file);

    @DeleteProvider(type = FileProvider.class, method = "deleteById")
    int deleteById(int fileId);

}