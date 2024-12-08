package java_web.online_shopping_mall.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java_web.online_shopping_mall.POJO.DTO.userUpdateDTO;
import java_web.online_shopping_mall.entity.User;
import org.apache.ibatis.annotations.*;
import java.util.List;


@Mapper
public interface UserMapper extends BaseMapper<User> {

    // 查询所有用户
    @Select("SELECT * FROM users")
    List<User> findAll();

    // 根据用户名查询用户
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    // 插入新用户
    @Insert("INSERT INTO users (username, password, createdtime, updatedtime) " +
            "VALUES (#{username}, #{HashPassword}, NOW(), NOW())")
    int insertUser(String username,String HashPassword);

    // 根据 ID 删除用户
    @Delete("DELETE FROM users WHERE id = #{id}")
    int deleteById(@Param("id") Long id);

    // **根据用户名删除用户**
    @Delete("DELETE FROM users WHERE username = #{username}")
    int deleteByUsername(@Param("username") String username);

    // 根据用户ID更新用户信息（支持动态更新多个字段）
    int updateUserByUserID(@Param("userUpdateDTO") userUpdateDTO userUpdateDTO, @Param("userId") Long userId);

    // 更新用户头像
    @Update("UPDATE users SET avatar = #{avatarUrl} WHERE id = #{userId}")
    int updateAvatar(@Param("userId") Long userId,@Param("avatarUrl") String avatarUrl);

    // 根据ID查询用户名
    @Select("SELECT username FROM users WHERE id = #{userId}")
    String findUsernameById(@Param("userId") Long userId);

}

