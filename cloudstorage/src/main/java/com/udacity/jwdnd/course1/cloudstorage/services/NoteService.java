package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    NoteMapper noteMapper;

    @Autowired
    UserMapper userMapper;

    public int addNote(String noteTitle, String noteDescription, Authentication auth){

        return noteMapper.insertNote(new Note(null, noteTitle, noteDescription, userMapper.getUser(auth.getName()).getUserid()));
    }

    public int updateNote(Integer noteId, String noteTitle, String noteDescription){

        return noteMapper.updateNote(noteId, noteTitle, noteDescription);
    }

    public List<Note>  getNotes(Authentication auth) {

        return noteMapper.getNotes(userMapper.getUser(auth.getName()).getUserid());
    }

    public int deleteNote(Integer noteId){

        return noteMapper.deleteNote(noteId);
    }
}
