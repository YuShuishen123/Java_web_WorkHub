package java_web.online_shopping_mall.filter;

import io.jsonwebtoken.Claims;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletResponse;
import java_web.online_shopping_mall.util.JwtUtil;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * JWT 过滤器，用于对需要认证的请求进行 Token 验证。
 * 继承 OncePerRequestFilter，确保每个请求只执行一次过滤逻辑。
 */
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    private static final List<String> WHITE_LIST = Arrays.asList(
            "/users/login",           // 登陆
            "/users/register",        // 注册
            "/categories/byId/*",     // 根据 ID 获取分类
            "/categories",            // 获取所有分类
            "/categories/byId/*",
            "/products/**",           // 根据分类 ID 获取商品
            "/reviews/avg_rating/*",  // 获取商品的平均评分
            "/reviews/all_reviews/*"  // 获取所有评论
    );

    // 构造函数注入 JwtUtil
    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(@NonNull jakarta.servlet.http.HttpServletRequest request,
                                    @NonNull jakarta.servlet.http.HttpServletResponse response,
                                    @NonNull jakarta.servlet.FilterChain filterChain)
            throws jakarta.servlet.ServletException, IOException {

        // 获取请求的 URI
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        // 添加调试日志
        System.out.println("当前请求URI: " + requestURI);

        // 对 OPTIONS 请求直接放行
        if ("OPTIONS".equalsIgnoreCase(method)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // 如果URI以/api开头，去掉这个前缀
        if (requestURI.startsWith("/api")) {
            requestURI = requestURI.substring(4);
        }

        System.out.println("处理后的URI: " + requestURI);
        System.out.println("是否在白名单中: " + isWhitelisted(requestURI));

        // 检查请求路径是否在白名单中
        if (isWhitelisted(requestURI)) {
            // 放行，不进行 JWT 校验
            filterChain.doFilter(request, response);
            return;
        }

        // 从请求头中获取 Authorization 字段
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // 提取 Token（移除 "Bearer " 前缀）
            String token = authorizationHeader.substring(7);

            try {
                // 检查 Token 是否过期或者是否在黑名单中
                if (jwtUtil.isTokenExpired(token)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token expired or in blacklist");
                    return;
                }

                // 解析并验证 Token
                Claims claims = jwtUtil.verifyToken(token);

                // 打印 Claims 信息（仅用于调试）
                System.out.println("Token Claims: " + claims);

                // 将解析后的信息存储到请求属性中，供后续处理使用
                request.setAttribute("username", claims.getSubject());  // 用户名
                request.setAttribute("token", token);                   // 原始 Token
                request.setAttribute("expirationTime", claims.getExpiration()); // Token 过期时间
                request.setAttribute("userId", claims.get("userId", Long.class)); // 用户 ID

            } catch (Exception e) {
                // 如果验证失败，返回 401 未授权错误
                System.out.println("Token验证失败: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("TokenProblem");
                return;
            }
        } else {
            // 如果没有提供 Authorization 头或者格式不正确，返回 401 未授权错误
            System.out.println("缺少Token或格式不正确");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // 未授权
            response.getWriter().write("TokenLose");
            return;
        }

        // 如果验证通过，继续过滤链，交由后续的过滤器或控制器处理
        filterChain.doFilter(request, response);
    }

    /**
     * 检查请求路径是否在白名单中。
     * @param requestURI 当前请求的 URI
     * @return 如果在白名单中返回 true，否则返回 false
     */
    private boolean isWhitelisted(String requestURI) {
        return WHITE_LIST.stream().anyMatch(pattern -> PATH_MATCHER.match(pattern, requestURI));
    }
}