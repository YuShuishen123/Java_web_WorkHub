package java_web.online_shopping_mall.service;


import com.baomidou.mybatisplus.extension.service.IService;
import java_web.online_shopping_mall.POJO.DTO.userUpdateDTO;
import java_web.online_shopping_mall.entity.User;
import java_web.online_shopping_mall.exception.CustomException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务接口，定义了用户相关的业务逻辑方法。
 */
@Service
public interface UserService extends IService<User> {

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
     */
    void insertUser(String username,String HashPassword) throws CustomException;

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

    // 根据用户 ID 更新用户信息
    void updateUserByUserID(userUpdateDTO userUpdateDTO, Long userId);

    // 根据用户ID和url更新用户头像
    void updateAvatar(String url,Long userId);

    // 根据用户ID获取用户名
    String findUsernameById(Long userId);
}

