package java_web.online_shopping_mall.util.mapStruct;

import java_web.online_shopping_mall.POJO.DTO.VO.userVO;
import java_web.online_shopping_mall.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    userVO toUserVO(User user);
}
