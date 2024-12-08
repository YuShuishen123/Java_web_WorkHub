package jdbc.cursoe_test.mapper;

import jdbc.cursoe_test.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User findByUsername(String username);
    User findByEmail(String email);
    User findById(Long id);
    void insert(User user);
    void update(User user);
    void updatePassword(@Param("id") Long id, @Param("newPassword") String newPassword);
} 