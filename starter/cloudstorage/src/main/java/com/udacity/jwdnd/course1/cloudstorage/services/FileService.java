package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;
    private final UserMapper userMapper;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    public int saveFile(MultipartFile fileUpload,String username) throws IOException {
        User user = userMapper.getUser(username);
        return fileMapper.insert(new File(
                null,
                fileUpload.getOriginalFilename(),
                fileUpload.getContentType(),
                fileUpload.getSize(),
                user.getUserid(),
                fileUpload.getInputStream()));
    }

    public List<File> getFiles(String username) {

        User user = userMapper.getUser(username);
        return fileMapper.getUserFiles(user.getUserid());
    }
}
