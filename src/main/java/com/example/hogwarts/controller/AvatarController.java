package com.example.hogwarts.controller;

import com.example.hogwarts.dto.AvatarDto;
import com.example.hogwarts.model.Avatar;
import com.example.hogwarts.service.AvatarService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/avatar")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @GetMapping("/from-disk/{id}")
    public void fromDisk(@PathVariable Long id, HttpServletResponse response){
        Avatar avatar = avatarService.getById(id);
        response.setContentType(avatar.getMediaType());
        response.setContentLength((int) avatar.getFileSize());
        try (FileInputStream fis = new FileInputStream(avatar.getFilePath())){
            fis.transferTo(response.getOutputStream());
        } catch (IOException e){
            throw new RuntimeException();
        }
    }
    @GetMapping("/from-db/{id}")
    public ResponseEntity<byte[]> fromDb(@PathVariable Long id){
        Avatar avatar = avatarService.getById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getFileSize());
        return ResponseEntity.status(200).headers(headers).body(avatar.getData());
    }
    @GetMapping("/page")
    public List<AvatarDto> getAll(@RequestParam("offset") Integer offset,
                                    @RequestParam("limit") Integer limit){
        return avatarService.getPage(offset, limit);
    }
}
