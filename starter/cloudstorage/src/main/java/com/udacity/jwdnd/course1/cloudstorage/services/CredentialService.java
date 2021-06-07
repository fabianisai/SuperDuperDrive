package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.FormCredential;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private final UserService userService;
    private final CredentialMapper credentialMapper;

    public CredentialService(UserService userService, CredentialMapper credentialMapper) {
        this.userService = userService;
        this.credentialMapper = credentialMapper;
    }

    public void handlerSave(FormCredential credential, Authentication authentication){

        String encodedKey = userService.encodeKey();
        String encryptedPassword =userService.encryptedPassword(credential.getPassword(),encodedKey);

        if (credential.getCredentialId().isEmpty()) {
            add(credential.getUrl(), authentication.getName(), credential.getUsername(), encodedKey, encryptedPassword);
        } else {
            Credential credentialTemp = getById(Integer.parseInt(credential.getCredentialId()));
            update(credentialTemp.getCredentialId(), credential.getUsername(), credential.getUrl(), encodedKey, encryptedPassword);
        }

    }
    
    public void delete(Integer credenbtialId) {
        credentialMapper.delete(credenbtialId);
    }

    public Credential getById(Integer credenbtialId) {
        return credentialMapper.getById(credenbtialId);
    }

    public List<Credential> getByUser(Integer userId) {
        return credentialMapper.getByUser(userId);
    }

    private void add(String url, String userName, String credentialUserName, String key, String password) {
        Integer userId = userService.getByUsername(userName).getUserId();
        Credential credential = new Credential(0,url, credentialUserName, key, password, userId);
        credentialMapper.add(credential);
    }

    private void update(Integer credentialId, String userName, String url, String key, String password) {
        credentialMapper.update(credentialId, userName, url, key, password);
    }




}
