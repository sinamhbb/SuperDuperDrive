package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final UserMapper userMapper;
    private final HashService hashService;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, UserMapper userMapper, HashService hashService, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.userMapper = userMapper;
        this.hashService = hashService;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getUserCredentials(String username) {
        User user = userMapper.getUser(username);
        return credentialMapper.getUserCredentials(user.getUserid());
    }

    public int upsertCredential(CredentialForm credentialForm, String username) {

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encriptedPassword = encryptionService.encryptValue(credentialForm.getPassword(),encodedKey);

        if (credentialForm.getCredentialid() != null) {
            return credentialMapper.update(new Credential(credentialForm.getCredentialid(), credentialForm.getUrl(), credentialForm.getUsername(), encodedKey, encriptedPassword, userMapper.getUser(username).getUserid()));
        }
        return credentialMapper.insert(new Credential(null, credentialForm.getUrl(), credentialForm.getUsername(), encodedKey, encriptedPassword, userMapper.getUser(username).getUserid()));
    }

    public int deleteCredential(String credentialid) {
        return credentialMapper.delete(Integer.parseInt(credentialid));
    }

}
