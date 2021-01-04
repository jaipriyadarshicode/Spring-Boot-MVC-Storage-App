package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {


    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insertCredential(Credentials credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userid=#{userid}")
    List<Credentials> getCredentials(Integer userid);

    @Select("SELECT password FROM CREDENTIALS WHERE userid=#{userid}")
    String getEncryptedPassword(Integer userid);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid=#{credentialid}")
    Credentials getCredential(Integer credentialid);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, key=#{key}, password=#{password} WHERE credentialid=#{credentialid}")
    int updateCredential(Integer credentialid, String url, String username, String key, String password);


    @Delete("DELETE FROM CREDENTIALS WHERE credentialid=#{credentialid}")
    int deleteCredential(Integer credentialid);
}
