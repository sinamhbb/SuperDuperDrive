package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<File> getFiles(Integer userid) {
        return fileMapper.getUserFiles(userid);
    }

    public File getFile(String fileId) {
        return fileMapper.getFile(Integer.parseInt(fileId));
    }

    public int upsertFile(MultipartFile fileUpload, Integer userid) throws IOException, IllegalArgumentException {
        if (fileMapper.existingFileCount(fileUpload.getOriginalFilename()) > 0) {
            throw new IllegalArgumentException("You have uploaded the same file Before");
        }
        return fileMapper.insert(new File(
                null,
                fileUpload.getOriginalFilename(),
                fileUpload.getContentType(),
                fileUpload.getSize(),
                userid,
                fileUpload.getInputStream()));
    }


    public int deleteFile(String fileId) {
        return fileMapper.deleteById(Integer.parseInt(fileId));
    }
}
