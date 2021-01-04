package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.FileManagement;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileManagementMapper {

    @Insert("INSERT into FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int uploadFile(FileManagement file);

    @Select("SELECT * FROM FILES WHERE userid=#{userid}")
    List<FileManagement> getUploadedFilesList(Integer userid);

    @Select("SELECT * FROM FILES WHERE filename=#{filename}")
    FileManagement getUploadedFile(String filename);

    @Select("SELECT filename FROM FILES WHERE filename=#{filename} AND userid=#{userid}")
    String isFileExists(String filename, Integer userid);

    @Delete("DELETE FROM FILES WHERE filename=#{filename}")
    int deleteFile(String filename);

}
