package jdbc.cursoe_test.controller;

import jdbc.cursoe_test.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private static final List<String> ALLOWED_VIDEO_TYPES = Arrays.asList(
        "video/mp4", "video/webm"
    );
    
    private static final List<String> ALLOWED_DOCUMENT_TYPES = Arrays.asList(
        "application/pdf", 
        "application/msword", 
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    );

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
                                      @RequestParam("directory") String directory,
                                      @RequestParam("type") String contentType) {
        try {
            // 验证文件类型
            String mimeType = file.getContentType();
            if (contentType.equals("VIDEO") && !ALLOWED_VIDEO_TYPES.contains(mimeType)) {
                throw new IllegalArgumentException("不支持的视频格式，仅支持MP4和WebM格式");
            }
            if (contentType.equals("DOCUMENT") && !ALLOWED_DOCUMENT_TYPES.contains(mimeType)) {
                throw new IllegalArgumentException("不支持的文档格式，仅支持PDF和Word格式");
            }

            // 验证文件大小（已在配置中设置，这里可以添加更具体的限制）
            if (file.getSize() > 10 * 1024 * 1024) { // 10MB
                throw new IllegalArgumentException("文件大小不能超过10MB");
            }

            String filePath = fileService.uploadFile(file, directory);
            String fileUrl = fileService.getFileUrl(filePath);
            
            Map<String, String> response = new HashMap<>();
            response.put("url", fileUrl);
            response.put("path", filePath);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "文件上传失败：" + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{directory}/{filename}")
    public ResponseEntity<?> deleteFile(@PathVariable String directory,
                                      @PathVariable String filename) {
        try {
            fileService.deleteFile(directory + "/" + filename);
            Map<String, String> response = new HashMap<>();
            response.put("message", "文件删除成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "文件删除失败：" + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
} 