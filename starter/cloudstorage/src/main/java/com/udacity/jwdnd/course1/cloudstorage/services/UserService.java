package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final HashService hashService;
    private final EncryptionService encryptionService;

    public UserService(UserMapper userMapper, HashService hashService, EncryptionService encryptionService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
        this.encryptionService = encryptionService;
    }

    public int create(User user) {
        String encodeKey=encodeKey();
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodeKey);
        return userMapper.add(new User(null, user.getUsername(), encodeKey, hashedPassword, user.getFirstName(), user.getLastName()));
    }

    public boolean exists(String username) {
        return userMapper.getByUsername(username) == null;
    }



    public User getByUsername(String username) {
        return userMapper.getByUsername(username);
    }

    public String encodeKey(){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        return encodedKey;
    }

    public String encryptedPassword(String password,String encodeKey){

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encrypPass = encryptionService.encryptValue(password, encodeKey);

        return encrypPass;
    }
}
