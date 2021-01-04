package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/note")
public class NoteController {

    NoteService noteService;

    public NoteController(NoteService noteService){
        this.noteService = noteService;
    }

    @RequestMapping("/addnote")
    public ModelAndView addNote(@RequestParam("noteId") Integer noteId, @RequestParam("noteTitle") String noteTitle,
                                @RequestParam("noteDescription") String noteDescription, Authentication auth){
        if(noteId > 0){
            noteService.updateNote(noteId, noteTitle, noteDescription);
        }
        else{
            noteService.addNote(noteTitle, noteDescription, auth);
        }
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping("/delete")
    public ModelAndView addNote(@RequestParam("noteid") Integer noteId){
        noteService.deleteNote(noteId);
        return new ModelAndView("redirect:/home");
    }
}
