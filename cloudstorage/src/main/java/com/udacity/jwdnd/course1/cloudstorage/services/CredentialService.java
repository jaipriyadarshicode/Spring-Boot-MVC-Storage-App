package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    @Autowired
    CredentialsMapper credentialMapper;

    @Autowired
    EncryptionService encryptionService;

    @Autowired
    UserMapper userMapper;

    public int addCredential(String url, String username, String key, String password, Authentication auth){
        return credentialMapper.insertCredential(new Credentials(null, url, username, key, password, userMapper.getUser(auth.getName()).getUserid()));
    }

    public List<Credentials> getCredentails(Authentication auth){
        List<Credentials> creds = credentialMapper.getCredentials(userMapper.getUser(auth.getName()).getUserid());
        return creds;
    }

    public int updateCredential(Integer credentialid, String url, String username, String key, String password){
        return credentialMapper.updateCredential(credentialid, url, username, key, password);
    }

    public int  deleteCredential(Integer credential){

        return credentialMapper.deleteCredential(credential);
    }

    public String getEncryptedPassword(Integer userid){
        return credentialMapper.getEncryptedPassword(userid);
    }

    public String decryptPassword(Integer credentialid){
        Credentials credential = credentialMapper.getCredential(credentialid);
        return encryptionService.decryptValue(credential.getPassword(), credential.getKey());
    }

}
