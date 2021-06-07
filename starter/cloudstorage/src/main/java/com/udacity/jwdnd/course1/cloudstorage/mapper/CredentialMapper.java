package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Insert("insert into credentials (url, username, key, password, userid) values(#{url}, #{userName}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer add(Credential credential);

    @Update("update credentials set url = #{url}, key = #{key}, password = #{password}, username = #{userName} WHERE credentialid = #{credentialId}")
    void update(Integer credentialId, String userName, String url, String key, String password);

    @Delete("delete from credentials where credentialid = #{credentialId}")
    void delete(Integer credentialId);

    @Select("select * from credentials where credentialid = #{credentialId}")
    Credential getById(Integer credentialId);

    @Select("select * from credentials where userid = #{userId}")
    List<Credential> getByUser(Integer userId);




}
