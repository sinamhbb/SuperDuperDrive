package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;



    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getUserCredentials(Integer userid) {
        return credentialMapper.getUserCredentials(userid);
    }

    public int upsertCredential(CredentialForm credentialForm, Integer userid) {

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(),encodedKey);

        if (credentialForm.getCredentialid() != null) {
                return credentialMapper.update(new Credential(credentialForm.getCredentialid(), credentialForm.getUrl(), credentialForm.getUsername(), encodedKey, encryptedPassword, userid));
        }
        return credentialMapper.insert(new Credential(null, credentialForm.getUrl(), credentialForm.getUsername(), encodedKey, encryptedPassword, userid));
    }

    public int deleteCredential(String credentialid) {
            return credentialMapper.delete(Integer.parseInt(credentialid));
    }
}
