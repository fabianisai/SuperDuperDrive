package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;
    private final UserMapper userMapper;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    public void add(MultipartFile multipartFile, Authentication authentication) throws IOException {


        byte[] fileBlob = getBytes(multipartFile);

        Integer userId = userMapper.getByUsername(authentication.getName()).getUserId();
        File file = new File(0,
                multipartFile.getOriginalFilename(),
                multipartFile.getContentType(),
                String.valueOf(multipartFile.getSize()),
                userId,
                fileBlob);

        fileMapper.add(file);
    }

    public boolean existsName(String fileName){

        File file=getByFileName(fileName);

        if (file==null) return false;

        if(file.getFileName().equals(fileName)) return true;

        return false;
    }

    private byte[] getBytes(MultipartFile multipartFile) throws IOException {
        InputStream is = multipartFile.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();

        return buffer.toByteArray();

    }

    public void delete(String fileName) {
        fileMapper.delete(fileName);
    }

    public List<String> getFilesByUser(Integer userId) {
        return fileMapper.getFilesByUser(userId);
    }

    public File getByFileName(String fileName) {
        return fileMapper.getByFileName(fileName);
    }




}
