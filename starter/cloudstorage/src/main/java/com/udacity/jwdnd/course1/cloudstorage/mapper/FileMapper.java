package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("insert into files (filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer add(File file);

    @Delete("delete from files where filename = #{fileName}")
    void delete(String fileName);

    @Select("select * from files where filename = #{fileName}")
    File getByFileName(String fileName);

    @Select("select filename from FILES WHERE userid = #{userId}")
    List<String> getFilesByUser(Integer userId);


}
