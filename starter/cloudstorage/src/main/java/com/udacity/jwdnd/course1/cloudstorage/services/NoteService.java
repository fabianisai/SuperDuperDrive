package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.FormCredential;
import com.udacity.jwdnd.course1.cloudstorage.model.FormNote;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final UserMapper userMapper;
    private final NoteMapper noteMapper;

    public NoteService(UserMapper userMapper, NoteMapper noteMapper) {
        this.userMapper = userMapper;
        this.noteMapper = noteMapper;
    }


    public void handlerSave(FormNote note, Authentication authentication){

        String noteIdStr = note.getNoteId();

        if (noteIdStr.isEmpty()) {
            Integer userId = userMapper.getByUsername(authentication.getName()).getUserId();
            add(note.getTitle(), note.getDescription(), userId);
        } else {
            Note noteTemp = getById(Integer.parseInt(noteIdStr));
            update(noteTemp.getNoteId(), note.getTitle(), note.getDescription());
        }
    }

    private void add(String title, String description, Integer userId) {

        Note note = new Note(0, title, description, userId);
        noteMapper.add(note);
    }

    private void update(Integer noteId, String title, String description) {
        noteMapper.update(noteId, title, description);
    }

    public void delete(Integer noteId) {
        noteMapper.delete(noteId);
    }

    public Note getById(Integer noteId) {
        return noteMapper.getById(noteId);
    }

    public List<Note> getByUser(Integer userId) {
        return noteMapper.getByUser(userId);
    }

}
