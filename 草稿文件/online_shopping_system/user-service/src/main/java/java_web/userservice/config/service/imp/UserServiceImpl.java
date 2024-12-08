package java_web.userservice.config.service.imp;

import java_web.userservice.config.service.UserService;
import java_web.userservice.mapper.UserMapper;
import java_web.userservice.entity.User;
import java_web.common.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户服务实现类，实现了 UserService 接口定义的业务逻辑。
 */
@Service
public class UserServiceImpl implements UserService {

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
    public void insertUser(User user) throws CustomException {
        // 插入新用户
        int rows = userMapper.insertUser(user);
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
            throw new CustomException("删除用户失败", "USER_DELETE_FAILED");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务，操作失败时会回滚
    public void updateUserByUsername(String username, String email, String phone, String nickname) throws CustomException {
        // 更新用户信息
        int rows = userMapper.updateUserByUsernameStatic(username, email, phone, nickname);
        // 如果更新失败，抛出自定义异常
        if (rows == 0) {
            throw new CustomException("更新用户信息失败", "USER_UPDATE_FAILED");
        }
    }
}

