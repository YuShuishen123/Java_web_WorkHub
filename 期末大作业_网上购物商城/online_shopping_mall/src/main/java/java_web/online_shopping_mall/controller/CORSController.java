package java_web.online_shopping_mall.controller;

import java_web.online_shopping_mall.util.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CORSController {
//     处理浏览器预检请求

    @RequestMapping(value = "/**", method = RequestMethod.OPTIONS)
    public ResponseEntity<Response<String>> handleOptions() {
        return ResponseEntity.ok(Response.success("成功"));

    }
}
