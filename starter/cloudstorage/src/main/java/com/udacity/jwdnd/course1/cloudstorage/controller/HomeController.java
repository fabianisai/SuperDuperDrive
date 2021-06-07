package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final CredentialService credentialService;
    private final UserService userService;
    private final FileService fileService;
    private final NoteService noteService;
    private final EncryptionService encryptionService;

    public HomeController(CredentialService credentialService, UserService userService, FileService fileService, NoteService noteService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.encryptionService = encryptionService;
    }

    @GetMapping()
    public String homeView(Authentication authentication,
                           @ModelAttribute("addFile") FormFile addFile,
                           @ModelAttribute("addNote") FormNote addNote,
                           @ModelAttribute("addCredential") FormCredential addCredential,
                           Model model) {

        Integer userId = getUserId(authentication);
        model.addAttribute("credentials", credentialService.getByUser(userId));
        model.addAttribute("files", this.fileService.getFilesByUser(userId));
        model.addAttribute("notes", noteService.getByUser(userId));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }

    private Integer getUserId(Authentication authentication) {
        String userName = authentication.getName();
        User user = userService.getByUsername(userName);
        return user.getUserId();
    }

    //files

    @PostMapping("/file/add")
    public String newFile(
            Authentication authentication,
            @ModelAttribute("addFile") FormFile addFile,
            Model model) throws IOException {

        MultipartFile multipartFile = addFile.getFile();

        if(fileService.existsName(multipartFile.getOriginalFilename())){
            model.addAttribute("result", "error");
            model.addAttribute("message", "The name of the file is not avaliable");
        } else{

            fileService.add(multipartFile,authentication);
            model.addAttribute("result", "success");
          //  User user = userService.getByUsername(authentication.getName());
          //  Integer userId = user.getUserId();
          //  model.addAttribute("files", fileService.getFilesByUser(userId));
        }



        return "result";
    }

    @GetMapping(value = "/file/delete/{fileName}")
    public String deleteFile(@PathVariable String fileName,
                             Model model) {
        fileService.delete(fileName);

        model.addAttribute("result", "success");

        return "result";
    }

    @GetMapping(value = "/file/get/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getFile(@PathVariable String fileName) {
        return fileService.getByFileName(fileName).getFileData();
    }

    //notes

    @PostMapping("/note/add")
    public String newNote(
            Authentication authentication,
            @ModelAttribute("addNote") FormNote newNote,
            Model model) {

        noteService.handlerSave(newNote,authentication);

        Integer userId = getUserId(authentication);
        model.addAttribute("notes", noteService.getByUser(userId));
        model.addAttribute("result", "success");

        return "result";
    }

    @GetMapping(value = "/delete/note/{noteId}")
    public String deleteNote(
            Authentication authentication,
            @PathVariable Integer noteId,
            Model model) {
        noteService.delete(noteId);
        model.addAttribute("result", "success");

        return "result";
    }

    private Note getNote(Integer noteId) {
        return noteService.getById(noteId);
    }

    //credentials

    @PostMapping("credential/add")
    public String newCredential(
            Authentication authentication,
            @ModelAttribute("addCredential") FormCredential addCredential, Model model) {

        credentialService.handlerSave(addCredential,authentication);

        User user = userService.getByUsername(authentication.getName());
        model.addAttribute("credentials", credentialService.getByUser(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("result", "success");

        return "result";
    }

    @GetMapping(value = "/delete/credential/{credentialId}")
    public String deleteCredential(@PathVariable Integer credentialId, Model model) {
        credentialService.delete(credentialId);

        model.addAttribute("result", "success");

        return "result";
    }

}
