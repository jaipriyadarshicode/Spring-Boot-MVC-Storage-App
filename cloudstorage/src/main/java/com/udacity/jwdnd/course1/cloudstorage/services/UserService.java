package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    @Autowired
    public HashService hashService;

    @Autowired
    public UserMapper userMapper;

    public int createUser(User user){

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        return userMapper.insertUser(new User (null, user.getUsername(), encodedSalt, hashPassword, user.getFirstname(), user.getLastname()));

    }

    public User checkUsernameAvailability(User user){
        return userMapper.getUser(user.getUsername());
    }
}
