package com.example.hogwarts.service;

import com.example.hogwarts.dto.AvatarDto;
import com.example.hogwarts.model.Avatar;
import com.example.hogwarts.model.Student;
import com.example.hogwarts.repository.AvatarRepository;
import com.example.hogwarts.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvatarService {
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;
    @Value("${path.to.avatars.folder}")
    private Path avatarPath;

    public AvatarService(AvatarRepository avatarRepository, StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    public Avatar getById(Long id){
        return avatarRepository.findById(id).orElseThrow();
    }
    public Long save(Long studentId, MultipartFile multipartFile) throws IOException {
        // Step 1. Save at disk
        Files.createDirectories(avatarPath);
        int dotIndex = multipartFile.getOriginalFilename().lastIndexOf(".");
        String fileExtension = multipartFile.getOriginalFilename().substring(dotIndex + 1);
        Path path = avatarPath.resolve(studentId + "." + fileExtension);
        byte[] data = multipartFile.getBytes();
        Files.write(path, data, StandardOpenOption.CREATE);

        // Step 2. Save Database
        Student studentReference = studentRepository.getReferenceById(studentId);
        Avatar avatar = avatarRepository.findFirstByStudent(studentReference).orElse(new Avatar());
        avatar.setMediaType(multipartFile.getContentType());
        avatar.setFileSize(multipartFile.getSize());
        avatar.setData(data);
        avatar.setFilePath(path.toAbsolutePath().toString());
        avatarRepository.save(avatar);
        return avatar.getId();
    }
    public List<AvatarDto> getPage(Integer offset, Integer limit){
        return avatarRepository.findAll(PageRequest.of(offset, limit))
                .getContent()
                .stream()
                .map(a-> new AvatarDto(a.getId(), a.getStudent().getId(),a.getStudent().getName()))
                .collect(Collectors.toList());
    }
}
