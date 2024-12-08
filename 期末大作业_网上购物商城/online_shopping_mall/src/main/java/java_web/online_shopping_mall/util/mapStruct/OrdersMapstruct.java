package java_web.online_shopping_mall.util.mapStruct;

import java_web.online_shopping_mall.POJO.DTO.OrdersDTO;
import java_web.online_shopping_mall.POJO.DTO.VO.OrdersVO;
import java_web.online_shopping_mall.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrdersMapstruct {
    OrdersMapstruct INSTANCE = Mappers.getMapper(OrdersMapstruct.class);

    // 实体转VO
    OrdersVO toVO(Order order);

    // DTO转实体
    Order toEntity(OrdersDTO ordersDTO);


}
