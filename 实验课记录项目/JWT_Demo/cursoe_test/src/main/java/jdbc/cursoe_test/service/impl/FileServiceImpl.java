package jdbc.cursoe_test.service.impl;

import jdbc.cursoe_test.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.upload.path}")
    private String uploadPath;

    @Value("${file.access.url}")
    private String accessUrl;

    @Override
    public String uploadFile(MultipartFile file, String directory) throws IOException {
        // 创建目录
        String fullPath = uploadPath + File.separator + directory;
        Files.createDirectories(Paths.get(fullPath));

        // 生成唯一文件名
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filename = UUID.randomUUID().toString() + extension;

        // 保存文件
        Path filePath = Paths.get(fullPath, filename);
        Files.copy(file.getInputStream(), filePath);

        // 返回相对路径
        return directory + "/" + filename;
    }

    @Override
    public void deleteFile(String filePath) throws IOException {
        Path path = Paths.get(uploadPath, filePath);
        Files.deleteIfExists(path);
    }

    @Override
    public String getFileUrl(String filePath) {
        return accessUrl + "/" + filePath;
    }
} 