package jdbc.cursoe_test.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface FileService {
    String uploadFile(MultipartFile file, String directory) throws IOException;
    void deleteFile(String filePath) throws IOException;
    String getFileUrl(String filePath);
} 