package java_web.online_shopping_mall.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java_web.online_shopping_mall.POJO.DTO.userUpdateDTO;
import java_web.online_shopping_mall.service.UserService;
import java_web.online_shopping_mall.entity.User;
import java_web.online_shopping_mall.exception.CustomException;
import java_web.online_shopping_mall.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户服务实现类，实现了 UserService 接口定义的业务逻辑。
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    public final UserMapper userMapper;
    public UserServiceImpl (UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> findAll() {
        // 查询所有用户
        return userMapper.findAll();
    }

    @Override
    public User findByUsername(String username) {
        // 根据用户名查询用户
        return userMapper.findByUsername(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务，操作失败时会回滚
    public void insertUser(String username,String HashPassword) throws CustomException {
        // 插入新用户
        int rows = userMapper.insertUser(username,HashPassword);
        // 如果插入失败，抛出自定义异常
        if (rows == 0) {
            throw new CustomException("插入用户失败", "USER_INSERT_FAILED");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务，操作失败时会回滚
    public void deleteById(Long id) throws CustomException {
        // 根据 ID 删除用户
        int rows = userMapper.deleteById(id);
        // 如果删除失败，抛出自定义异常
        if (rows == 0) {
            throw new CustomException("删除用户失败", "USER_DELETE_FAILED");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务，操作失败时会回滚
    public void deleteByUsername(String username) throws CustomException {
        // 根据用户名删除用户
        int rows = userMapper.deleteByUsername(username);
        // 如果删除失败，抛出自定义异常
        if (rows == 0) {
            throw new CustomException("注销用户失败", "USER_DELETE_FAILED");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务，操作失败时会回滚
    public void updateUserByUserID(userUpdateDTO userUpdateDTO, Long userId){
        int result = userMapper.updateUserByUserID(userUpdateDTO, userId);
        if (result == 0) {
            throw new CustomException("修改用户信息失败", "USER_UPDATE_FAILED");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAvatar(String avatarUrl, Long userId) {
        int result = userMapper.updateAvatar(userId,avatarUrl);
        if (result == 0) {
            throw new CustomException("修改头像失败", "AVATAR_UPDATE_FAILED");
        }
    }

    // 根据ID获取用户名
    @Override
    public String findUsernameById(Long userId) {
        return userMapper.findUsernameById(userId);
    }
}

