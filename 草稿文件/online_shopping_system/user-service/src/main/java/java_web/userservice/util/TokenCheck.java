package java_web.userservice.util;

import java_web.common.util.Response;
import org.springframework.stereotype.Component;

@Component
public class TokenCheck {

    private final JwtUtil jwtUtil;

    public TokenCheck(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // 验证token是否有效
    public <T> Response<T> checkToken(String token) {
        // 验证 token 是否无效
        if (jwtUtil.isTokenExpired(token)) {
            return Response.fail(400, "Token无效");
        }

        // 返回 null 或者一个空的成功响应，表示 token 有效
        return Response.success(null); // token 有效
    }
}
