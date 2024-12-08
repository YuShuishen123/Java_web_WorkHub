package java_web.online_shopping_mall.mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface JwtBlacklistMapper {

    // 将 token 插入黑名单
    @SuppressWarnings("SqlInsertValues")
    @Insert("INSERT INTO jwt_blacklist (token) VALUES (#{token})")
    void insertBlacklist(String jwt);

    // 查询 token 是否在黑名单中
    @Select("SELECT COUNT(1) FROM jwt_blacklist WHERE token = #{token}")
    int selectByToken(String token);

}

