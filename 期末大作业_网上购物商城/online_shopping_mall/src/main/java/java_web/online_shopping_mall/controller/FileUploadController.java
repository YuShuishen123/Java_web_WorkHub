package java_web.online_shopping_mall.controller;

import java_web.online_shopping_mall.util.AliOssUtil;
import java_web.online_shopping_mall.util.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class FileUploadController {

    private final  AliOssUtil aliOssUtil;  // 添加依赖注入

    public FileUploadController(AliOssUtil aliOssUtil) {
        this.aliOssUtil = aliOssUtil;
    }

    /**
     * 处理文件上传请求，并将文件存储到阿里云OSS
     *
     * @param file 上传的文件
     * @return 包含文件公网访问地址的响应对象
     * @throws IOException 如果文件读取失败
     */
    @PostMapping
    public Response<String> upload(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return Response.fail("上传的图片为空");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
            return Response.fail("图片名称无效，必须包含扩展名");
        }

        // 生成唯一文件名，避免命名冲突
        String filename = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));

        // 使用注入的实例调用方法，而不是静态方法
        String url = aliOssUtil.uploadFile(filename, file.getInputStream());

        return Response.success(url);
    }
}