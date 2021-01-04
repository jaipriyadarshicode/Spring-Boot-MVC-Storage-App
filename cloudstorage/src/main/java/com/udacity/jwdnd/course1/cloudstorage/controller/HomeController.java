package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileManagementService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    FileManagementService fileManagementService;

    @Autowired
    NoteService noteService;

    @Autowired
    CredentialService credentialService;

    @GetMapping()
    public String homeView(Model model, Authentication auth){
        model.addAttribute("fileNames", fileManagementService.getUploadedFilesList(auth));
        model.addAttribute("notes", noteService.getNotes(auth));
        model.addAttribute("credential", credentialService.getCredentails(auth));
        return "home";
    }


}
