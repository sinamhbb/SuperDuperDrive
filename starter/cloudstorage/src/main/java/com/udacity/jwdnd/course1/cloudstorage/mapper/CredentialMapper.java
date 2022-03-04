package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.mapper.provider.CredentialProvider;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @SelectProvider(type = CredentialProvider.class, method = "getUserCredentials")
    List<Credential> getUserCredentials(Integer userid);

    @SelectProvider(type = CredentialProvider.class, method = "getCredentialById")
    Credential getCredentialById(Integer credentialid);

    @InsertProvider(type = CredentialProvider.class, method="insert")
    Integer insert(Credential credential);

    @UpdateProvider(type = CredentialProvider.class, method="update")
    Integer update(Credential credential);

    @DeleteProvider(type = CredentialProvider.class, method="delete")
    Integer delete(Integer credentialid);

}
