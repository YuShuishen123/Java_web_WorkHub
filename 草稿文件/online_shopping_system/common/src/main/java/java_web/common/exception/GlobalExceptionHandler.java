package java_web.common.exception;

import java_web.common.util.Response;  // 引入统一响应类
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice  // 统一处理 Controller 层的异常
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 处理自定义异常
    @ExceptionHandler(CustomException.class)
    public Response<?> handleCustomException(CustomException ex) {
        logger.error("CustomException: {}", ex.getMessage(), ex);
        return Response.fail(400, ex.getMessage());
    }

    // 处理空指针异常
    @ExceptionHandler(NullPointerException.class)
    public Response<?> handleNullPointerException(NullPointerException ex) {
        logger.error("NullPointerException: {}", ex.getMessage(), ex);
        return Response.fail(400, "请求中缺少必要的参数");
    }

    // 处理非法参数异常
    @ExceptionHandler(IllegalArgumentException.class)
    public Response<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("IllegalArgumentException: {}", ex.getMessage(), ex);
        return Response.fail(400, "非法参数");
    }

    // 处理数据库异常（如：数据库连接失败，SQL语法错误等）
    @ExceptionHandler({DataAccessException.class, SQLException.class})
    public Response<?> handleDatabaseException(Exception ex) {
        logger.error("Database error: {}", ex.getMessage(), ex);
        return Response.fail(500, "数据库操作异常，请稍后重试");
    }

    // 统一处理其他所有未被捕获的异常（服务器级错误）
    @ExceptionHandler(Exception.class)
    public Response<?> handleGeneralException(Exception ex) {
        logger.error("Unexpected error: {}", ex.getMessage(), ex);
        return Response.fail(500, "服务器发生错误，请稍后再试");
    }
}
