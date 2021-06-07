package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Insert("insert into notes (notetitle, notedescription, userid) values(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer add(Note note);

    @Update("update notes set notetitle = #{title}, notedescription = #{description} WHERE noteid = #{noteId}")
    void update(Integer noteId, String title, String description);

    @Delete("delete from notes where noteid = #{noteId}")
    void delete(Integer noteId);

    @Select("select * from notes where noteid = #{noteId}")
    Note getById(Integer noteId);

    @Select("select * from notes where userid = #{userId}")
    List<Note> getByUser(Integer userId);


}


