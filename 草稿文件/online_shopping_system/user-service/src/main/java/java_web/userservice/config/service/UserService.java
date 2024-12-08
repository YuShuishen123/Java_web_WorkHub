package java_web.userservice.config.service;


import java_web.userservice.entity.User;
import java_web.common.exception.CustomException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务接口，定义了用户相关的业务逻辑方法。
 */
@Service
public interface UserService {

    /**
     * 查询所有用户
     * @return 所有用户的列表
     */
    List<User> findAll();

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户实体
     */
    User findByUsername(String username);

    /**
     * 插入新用户
     * @param user 用户信息
     * @throws CustomException 插入失败时抛出自定义异常
     */
    void insertUser(User user) throws CustomException;

    /**
     * 根据 ID 删除用户
     * @param id 用户 ID
     * @throws CustomException 删除失败时抛出自定义异常
     */
    void deleteById(Long id) throws CustomException;

    /**
     * 根据用户名删除用户
     * @param username 用户名
     * @throws CustomException 删除失败时抛出自定义异常
     */
    void deleteByUsername(String username) throws CustomException;

    /**
     * 更新用户信息
     * @param username 用户名
     * @param email 新的邮箱
     * @param phone 新的手机号
     * @param nickname 新的昵称
     * @throws CustomException 更新失败时抛出自定义异常
     */
    void updateUserByUsername(String username, String email, String phone, String nickname) throws CustomException;
}

