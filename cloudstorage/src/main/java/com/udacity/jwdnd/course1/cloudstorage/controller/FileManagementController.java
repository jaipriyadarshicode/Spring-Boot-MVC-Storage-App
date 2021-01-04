package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.FileManagement;
import com.udacity.jwdnd.course1.cloudstorage.services.FileManagementService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/uploadFile")
public class FileManagementController{

    FileManagementService fileManagementService;

    public FileManagementController(FileManagementService fileManagementService){
        this.fileManagementService = fileManagementService;
    }

    @PostMapping()
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Authentication auth, RedirectAttributes redirectAttr){
        if(file.isEmpty() || fileManagementService.isFileExists(file.getOriginalFilename(), auth)){
            redirectAttr.addFlashAttribute("FileUploadError","Error Uploading File");
            return "redirect:/error";
        }
        else{
            fileManagementService.uploadFile(file, auth);
        }
        return "redirect:/home";
    }

    @RequestMapping("/download")
    public ResponseEntity<Resource> downloadFile(HttpServletRequest request, @RequestParam("filename") String filename){
        FileManagement fileUpload = fileManagementService.getUploadedFile(filename);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileUpload.getFilename());
        ByteArrayResource resource = new ByteArrayResource(fileUpload.getFiledata());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(Integer.parseInt(fileUpload.getFilesize()))
                .contentType(MediaType.parseMediaType(fileUpload.getContenttype()))
                .body(resource);

    }

    @RequestMapping("/delete")
    public ModelAndView deleteFile(@RequestParam("filename") String filename){
        int delrec = fileManagementService.deleteFile(filename);
        return new ModelAndView("redirect:/home");
    }
}
