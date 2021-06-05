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
        model.addAttribute("credentials", credentialService.getCredentialsByUser(userId));
        model.addAttribute("files", this.fileService.getFilesByUser(userId));
        model.addAttribute("notes", noteService.getNotesByUser(userId));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }

    private Integer getUserId(Authentication authentication) {
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        return user.getUserId();
    }

    //files

    @PostMapping("/file/add")
    public String newFile(
            Authentication authentication,
            @ModelAttribute("addFile") FormFile addFile,
            Model model) throws IOException {
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        Integer userId = user.getUserId();
        String[] files = fileService.getFilesByUser(userId);
        MultipartFile multipartFile = addFile.getFile();
        String fileName = multipartFile.getOriginalFilename();
        boolean duplicate = false;
        for (String file: files) {
            if (file.equals(fileName)) {
                duplicate = true;
                break;
            }
        }
        if (!duplicate) {
            fileService.addFile(multipartFile, userName);
            model.addAttribute("result", "success");
        } else {
            model.addAttribute("result", "error");
            model.addAttribute("message", "You tried to add a duplicate file.");
        }
        model.addAttribute("files", fileService.getFilesByUser(userId));

        return "result";
    }

    @GetMapping(value = "/file/delete/{fileName}")
    public String deleteFile(@PathVariable String fileName,
                             Model model) {
        fileService.deleteFile(fileName);

        model.addAttribute("result", "success");

        return "result";
    }

    @GetMapping(value = "/file/get/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getFile(@PathVariable String fileName) {
        return fileService.getFile(fileName).getFileData();
    }

    //notes

    @PostMapping("/note/add")
    public String newNote(
            Authentication authentication,
            @ModelAttribute("addNote") FormNote newNote,
            Model model) {
        String userName = authentication.getName();
        String newTitle = newNote.getTitle();
        String noteIdStr = newNote.getNoteId();
        String newDescription = newNote.getDescription();
        if (noteIdStr.isEmpty()) {
            noteService.addNote(newTitle, newDescription, userName);
        } else {
            Note existingNote = getNote(Integer.parseInt(noteIdStr));
            noteService.updateNote(existingNote.getNoteId(), newTitle, newDescription);
        }
        Integer userId = getUserId(authentication);
        model.addAttribute("notes", noteService.getNotesByUser(userId));
        model.addAttribute("result", "success");

        return "result";
    }

    @GetMapping(value = "/delete/note/{noteId}")
    public String deleteNote(
            Authentication authentication,
            @PathVariable Integer noteId,
            Model model) {
        noteService.deleteNote(noteId);
        model.addAttribute("result", "success");

        return "result";
    }

    private Note getNote(Integer noteId) {
        return noteService.getNote(noteId);
    }

    //credentials

    @PostMapping("credential/add")
    public String newCredential(
            Authentication authentication,
            @ModelAttribute("addCredential") FormCredential addCredential, Model model) {
        String userName = authentication.getName();
        String newUrl = addCredential.getUrl();
        String credentialIdStr = addCredential.getCredentialId();
        String password = addCredential.getPassword();

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);

        if (credentialIdStr.isEmpty()) {
            credentialService.addCredential(newUrl, userName, addCredential.getUsername(), encodedKey, encryptedPassword);
        } else {
            Credential existingCredential = credentialService.getCredential(Integer.parseInt(credentialIdStr));
            credentialService.updateCredential(existingCredential.getCredentialId(), addCredential.getUsername(), newUrl, encodedKey, encryptedPassword);
        }
        User user = userService.getUser(userName);
        model.addAttribute("credentials", credentialService.getCredentialsByUser(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("result", "success");

        return "result";
    }

    @GetMapping(value = "/delete/credential/{credentialId}")
    public String deleteCredential(@PathVariable Integer credentialId, Model model) {
        credentialService.deleteCredential(credentialId);

        model.addAttribute("result", "success");

        return "result";
    }

}
