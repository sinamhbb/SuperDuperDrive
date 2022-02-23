package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller()
//@RequestMapping("/home")
public class HomeController {
 private final FileService fileService;

    public HomeController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/home")
    public String homeView(Model model,Authentication authentication) {

        model.addAttribute("Files", fileService.getFiles(authentication.getName()));

        return "home";
    }

    @PostMapping("/file-upload")
    public String handleFileUpload(
            @RequestParam("fileUpload")MultipartFile fileUpload,
            Model model,
            Authentication authentication) throws IOException {

        int fileId = fileService.saveFile(fileUpload, authentication.getName());

        model.addAttribute("Files", fileService.getFiles(authentication.getName()));
        return "home";
    }

    @GetMapping("/file-delete")
    public String handleFileDelete(@RequestParam(value="fileId") String fileId, Authentication authentication, Model model) {
        var deleteId = fileService.deleteFile(fileId);
        model.addAttribute("Files", fileService.getFiles(authentication.getName()));
        return "home";
    }

}
