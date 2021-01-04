package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/credentials")
public class CrendentialsController {

    CredentialService credentialService;

    @Autowired
    EncryptionService encryptionService;

    public CrendentialsController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @RequestMapping("/addcredential")
    public ModelAndView addCredential(@RequestParam("credentialId") Integer credentialid, @RequestParam("url") String url,
                                      @RequestParam("username") String username, @RequestParam("password") String password,
                                      Authentication auth){

        SecureRandom secureRandom  = new SecureRandom();
        byte[] key = new byte[16];
        secureRandom.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);

        if(credentialid > 0){
            credentialService.updateCredential(credentialid, url, username, encodedKey, encryptedPassword);
        }
        else{
            credentialService.addCredential(url, username, encodedKey, encryptedPassword, auth);
        }

        return new ModelAndView("redirect:/home");
    }

    @RequestMapping("/delete")
    public ModelAndView deletecredential(Integer credentialid){

        credentialService.deleteCredential(credentialid);

        return new ModelAndView("redirect:/home");
    }

    @RequestMapping("/decrypt-credential/{credentialid}")
    @ResponseBody
    public List<String> decryptCredential(@PathVariable("credentialid") Integer credentialid){
        return Arrays.asList(credentialService.decryptPassword(credentialid));
    }
}
