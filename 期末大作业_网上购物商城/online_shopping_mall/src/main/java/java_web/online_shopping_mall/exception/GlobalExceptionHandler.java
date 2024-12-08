package java_web.online_shopping_mall.exception;

import jakarta.validation.ConstraintViolationException;
import java_web.online_shopping_mall.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.validation.ConstraintViolation;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice  // 统一处理 Controller 层的异常
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 处理参数校验异常（单个参数：@RequestParam, @PathVariable, @RequestHeader）
    @ExceptionHandler(ConstraintViolationException.class)
    public Response<?> handleConstraintViolationException(ConstraintViolationException ex) {
        logger.error("ConstraintViolationException: {}", ex.getMessage(), ex);

        // 获取所有校验失败的信息
        String errorMessage = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; "));

        return Response.fail(400, errorMessage);
    }

    // 处理参数校验异常（请求体对象：@RequestBody）
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        logger.error("参数校验失败: {}", ex.getMessage(), ex);

        // 提取字段错误信息
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return Response.fail(400, "参数校验失败", errors);
    }

    // 处理表单绑定异常（如 @RequestParam 绑定到 DTO）
    @ExceptionHandler(BindException.class)
    public Response<?> handleBindException(BindException ex) {
        logger.error("参数绑定失败: {}", ex.getMessage(), ex);

        // 提取字段错误信息
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return Response.fail(400, "参数绑定失败", errors);
    }


    // 处理自定义异常
    @ExceptionHandler(CustomException.class)
    public Response<?> handleCustomException(CustomException ex) {
        logger.error("CustomException: {}", ex.getMessage(), ex);
        return Response.fail(400, ex.getMessage());
    }

    // 处理空指针异常
    @ExceptionHandler(NullPointerException.class)
    public Response<?> handleNullPointerException(NullPointerException ex) {
        logger.error("请求中缺少必要的参数: {}", ex.getMessage(), ex);
        return Response.fail(400, "请求中缺少必要的参数");
    }

    // 处理非法参数异常
    @ExceptionHandler(IllegalArgumentException.class)
    public Response<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("非法参数: {}", ex.getMessage(), ex);
        return Response.fail(400, "非法参数");
    }

    // 处理数据库异常（如：数据库连接失败，SQL语法错误等）
    @ExceptionHandler({DataAccessException.class, SQLException.class})
    public Response<?> handleDatabaseException(Exception ex) {
        logger.error("数据库操作异常: {}", ex.getMessage(), ex);
        return Response.fail(500, "数据库操作异常，请稍后重试");
    }

    // 统一处理其他所有未被捕获的异常（服务器级错误）
    @ExceptionHandler(Exception.class)
    public Response<?> handleGeneralException(Exception ex) {
        logger.error("服务器发生错误: {}", ex.getMessage(), ex);
        return Response.fail(500, "服务器发生错误，请稍后再试");
    }
}
