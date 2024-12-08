package java_web.online_shopping_mall.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java_web.online_shopping_mall.POJO.DTO.UserDTO;
import java_web.online_shopping_mall.POJO.DTO.userUpdateDTO;
import java_web.online_shopping_mall.POJO.DTO.VO.userVO;
import java_web.online_shopping_mall.entity.User;
import java_web.online_shopping_mall.service.UserService;
import java_web.online_shopping_mall.util.AliOssUtil;
import java_web.online_shopping_mall.util.JwtUtil;
import java_web.online_shopping_mall.util.PasswordEncryption;
import java_web.online_shopping_mall.util.Response;
import java_web.online_shopping_mall.util.mapStruct.UserMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/users")
public class usersController {
    // 注入 service
    private final UserService userService;
    private final PasswordEncryption passwordEncryption;
    private final JwtUtil jwtUtil;
    private final AliOssUtil aliOssUtil;
    public usersController(UserService userService, PasswordEncryption passwordEncryption, JwtUtil jwtUtil, AliOssUtil aliOssUtil) {
        this.userService = userService;
        this.passwordEncryption = passwordEncryption;
        this.jwtUtil = jwtUtil;
        this.aliOssUtil = aliOssUtil;
    }

    // 用户注册功能
    @PostMapping("/register")
    public Response<String> createUser(@Valid @ModelAttribute UserDTO userDTO) {
        // 检查用户名是否已存在
        if (userService.findByUsername(userDTO.getUsername()) != null) {
            return Response.fail(400, "用户名已存在");
        }
        // 对密码进行加密
        String hashPassword = passwordEncryption.passwordEncryption(userDTO.getPassword());
        userService.insertUser(userDTO.getUsername(), hashPassword);
        return Response.success("注册成功");
    }

    // 登陆验证功能
    @PostMapping("/login")
    public Response<Map<String, String>> login(@Valid @ModelAttribute UserDTO userDTO) {
        // 检查用户名是否正确
        User user = userService.findByUsername(userDTO.getUsername());
        if (user == null) {
            return Response.fail(400, "用户名不存在");
        }
        // 检查密码是否正确
        if (!passwordEncryption.verifyPassword(userDTO.getPassword(), user.getPassword())) {
            return Response.fail(400, "密码错误");
        }
        // 获取id
        Long id = user.getId();
        // 登录成功，生成 token
        String token = jwtUtil.generateToken(user.getUsername(), id);
        // 构建返回的响应数据
        Map<String, String> data = new HashMap<>();
        data.put("token", token);
        return Response.success("登录成功", data);
    }

    //注销账号，JWT 加入黑名单。
    @DeleteMapping("/delete")
    public Response<String> deleteUser(HttpServletRequest request) {
        // 从请求中获取加工后的 token（在过滤器中已存储）
        String token = (String) request.getAttribute("token");
        // 从请求中获取用户名（之前过滤器中已经设置了）
        String username = (String) request.getAttribute("username");
        // 从数据库中删除用户
        userService.deleteByUsername(username);
        jwtUtil.destroyToken(token);
        return (Response.success("注销成功"));
    }


    // 获取用户信息
    @GetMapping("/info")
    public Response<userVO> getUserInfo(HttpServletRequest request) {
        // 从请求中获取用户名（之前过滤器中已经设置了）
        String username = (String) request.getAttribute("username");
        System.out.println(username);
        // 从数据库中获取用户信息
        User user = userService.findByUsername(username);
        System.out.println(user);
        if (user == null) {
            return Response.fail(400, "用户不存在");
        }
        // 返回用户信息
        userVO vo = UserMapper.INSTANCE.toUserVO(user);
        System.out.println(vo);
        return Response.success("获取用户信息成功", vo);
    }

    // 修改用户个人信息
    @PutMapping("/update")
    public Response<String> updateUserInfo(@Valid @RequestBody userUpdateDTO userUpdateDTO, HttpServletRequest request) {
        // 从token中获取用户id
        Long userId = (Long) request.getAttribute("userId");
        // 如果有传入密码，则加密密码
        if(userUpdateDTO.getPassword() != null){
            userUpdateDTO.setPassword(passwordEncryption.passwordEncryption(userUpdateDTO.getPassword()));
        }
        userService.updateUserByUserID(userUpdateDTO, userId);
        return Response.success("修改用户信息成功");
    }

    // 更新用户个人头像
    @PostMapping("/change_avatar")
    public Response<String> changeAvatar(MultipartFile file, HttpServletRequest request){
        if (file == null || file.isEmpty()) {
            return Response.fail("上传的图片为空");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
            return Response.fail("图片名称无效，必须包含扩展名");
        }

        // 检查扩展名
        String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        if (!List.of("jpg", "jpeg", "png", "gif").contains(ext)) {
            return Response.fail("不支持的文件类型");
        }

        // 限制文件大小
        if (file.getSize() > 5 * 1024 * 1024) {
            return Response.fail("文件大小超过限制（5MB）");
        }
        // 生成唯一文件名，避免命名冲突
        String filename = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));

        // 调用工具类上传文件到OSS
        String url;
        try (InputStream inputStream = file.getInputStream()) {
            url = aliOssUtil.uploadFile(filename, inputStream);
        } catch (Exception e) {
            return Response.fail("文件上传失败：" + e.getMessage());
        }

        // 获取用户ID
        Long userId = (Long) request.getAttribute("userId");

        // 更新用户头像
        userService.updateAvatar(url, userId);

        return Response.success(url);
    }


    // 退出登陆，将token加入黑名单
    @PostMapping("/logout")
    public Response<String> logout(HttpServletRequest request) {
        // 从请求中获取加工后的 token（在过滤器中已存储）
        String token = (String) request.getAttribute("token");
        jwtUtil.destroyToken(token);
        return Response.success("退出成功");
    }
}




