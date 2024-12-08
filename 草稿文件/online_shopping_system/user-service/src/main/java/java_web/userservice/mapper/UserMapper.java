package java_web.userservice.mapper;


import java_web.userservice.entity.User;
import org.apache.ibatis.annotations.*;
import java.util.List;


@Mapper
public interface UserMapper{

    // 查询所有用户
    @Select("SELECT * FROM users")
    List<User> findAll();

    // 根据用户名查询用户
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    // 插入新用户
    @Insert("INSERT INTO users (username, password, email, phone, nickname, created_at, updated_at) " +
            "VALUES (#{username}, #{password}, #{email}, #{phone}, #{nickname}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id") // 返回主键
    int insertUser(User user);

    // 根据 ID 删除用户
    @Delete("DELETE FROM users WHERE id = #{id}")
    int deleteById(@Param("id") Long id);

    // **根据用户名删除用户**
    @Delete("DELETE FROM users WHERE username = #{username}")
    int deleteByUsername(@Param("username") String username);

    // 更新用户信息（支持动态更新多个字段）
    @Update("UPDATE users SET email = #{email}, phone = #{phone}, nickname = #{nickname}, updated_at = NOW() WHERE username = #{username}")
    int updateUserByUsernameStatic(@Param("username") String username,
                                   @Param("email") String email,
                                   @Param("phone") String phone,
                                   @Param("nickname") String nickname);
}

