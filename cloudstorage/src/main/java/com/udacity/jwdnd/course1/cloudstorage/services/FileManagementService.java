package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.FileManagementMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.FileManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileManagementService {


    @Autowired
    FileManagementMapper fileManagementMapper;

    @Autowired
    UserMapper usermapper;

    public int uploadFile(MultipartFile file, Authentication auth){
        try{
            byte[] fileBytes = file.getBytes();

            return fileManagementMapper.uploadFile(new FileManagement(null, file.getOriginalFilename(), file.getContentType(), Long.toString(file.getSize()), usermapper.getUser(auth.getName()).getUserid(), fileBytes));
        }
        catch(Exception e){
            return 0;
        }
    }

    public boolean isFileExists(String filename, Authentication auth){
        String file = fileManagementMapper.isFileExists(filename, usermapper.getUser(auth.getName()).getUserid());
        if(file == null){
            return false;
        }
        else{
            return true;
        }
    }

    public List<String> getUploadedFilesList(Authentication auth){
        List<FileManagement> fileRecs = fileManagementMapper.getUploadedFilesList(usermapper.getUser(auth.getName()).getUserid());
        List<String> fileNames = new ArrayList<String>();
        for(FileManagement fileRec : fileRecs){
            fileNames.add(fileRec.getFilename());
        }
        return fileNames;
    }

    public FileManagement getUploadedFile(String filename){
        return fileManagementMapper.getUploadedFile(filename);
    }

    public int deleteFile(String filename){
        return fileManagementMapper.deleteFile(filename);
    }
}
