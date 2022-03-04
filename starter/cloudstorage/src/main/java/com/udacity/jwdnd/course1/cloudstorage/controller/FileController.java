package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller()
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/file-upload")
    public ModelAndView handleFileUpload(
            @RequestParam("fileUpload") MultipartFile fileUpload,
            Model model,
            Authentication authentication,
            HttpServletRequest request
    ) {
        request.setAttribute("tab", "files");

        if (fileUpload.isEmpty()) {
            request.setAttribute("errorMessage", "No File Found");
            return new ModelAndView("/result");
        } else {

            try {
                int fileId = fileService.saveFile(fileUpload, authentication.getName());
                model.addAttribute("Files", fileService.getFiles(authentication.getName()));
                request.setAttribute("errorMessage", "null");
                return new ModelAndView("/result");
            } catch (Exception e) {
                request.setAttribute("errorMessage", e.getMessage());
                return new ModelAndView("/result");
            }
        }

    }

    @GetMapping("/file-download")
    public ResponseEntity<Resource> handleFileDownload(@RequestParam(value = "fileId") String fileId) throws IOException {
        File file = fileService.getFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(new ByteArrayResource(file.getFiledata()));

    }

    @GetMapping("/file-delete")
    public ModelAndView handleFileDelete(@RequestParam(value="fileId") String fileId, Authentication authentication, Model model, HttpServletRequest request) {
        request.setAttribute("tab", "files");
        try {
            var deleteId = fileService.deleteFile(fileId);
            model.addAttribute("Files", fileService.getFiles(authentication.getName()));
            request.setAttribute("errorMessage", "null");
            return new ModelAndView("/result");
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            return new ModelAndView("/result");
        }
    }

}
