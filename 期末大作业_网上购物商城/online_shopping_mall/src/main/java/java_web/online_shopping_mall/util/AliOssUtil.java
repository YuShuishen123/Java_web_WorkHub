package java_web.online_shopping_mall.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class AliOssUtil {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.access-key-id}")
    private String accessKeyId;

    @Value("${aliyun.oss.secret-access-key}")
    private String secretAccessKey;

    @Value("${aliyun.oss.bucket-name}")
    private String bucketName;

    /**
     * 上传文件到阿里云OSS，并返回文件的公网访问URL
     *
     * @param objectName  文件在OSS中的名称
     * @param inputStream 文件流
     * @return 公网访问地址
     */
    public String uploadFile(String objectName, InputStream inputStream) {
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, secretAccessKey);
        String url = ""; // 用于存储文件的公网访问地址
        try {
            // 上传文件到指定Bucket中
            ossClient.putObject(bucketName, objectName, inputStream);

            // 构建文件的公网访问地址
            url = "https://" + bucketName + "." + endpoint.replace("https://", "") + "/" + objectName;
        } catch (OSSException oe) {
            // 捕获OSS服务端异常并打印日志
            System.out.println("OSSException: " + oe.getErrorMessage());
        } catch (ClientException ce) {
            // 捕获客户端异常并打印日志
            System.out.println("ClientException: " + ce.getMessage());
        } finally {
            // 关闭OSSClient连接
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }
}