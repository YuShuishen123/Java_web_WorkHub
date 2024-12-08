package java_web.online_shopping_mall.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import java_web.online_shopping_mall.entity.Order;
import java.util.List;

@Mapper
public interface OrdersMapper extends BaseMapper<Order> {

    // 根据用户ID查询订单
    @Select("SELECT * FROM `orders` WHERE user_id = #{userId}")
    List<Order> selectOrdersByUserId(Long userId);

    // 查询所有订单
    @Select("SELECT * FROM `orders`")
    List<Order> selectAllOrders();

    // 根据订单ID查询订单
    @Select("SELECT * FROM `orders` WHERE order_id = #{orderId}")
    Order selectOrderById(Long orderId);

    // 根据订单ID更新订单状态
    @Update("UPDATE `orders` SET order_status = #{orderStatus} WHERE order_id = #{orderId}")
    int updateOrderStatus(@Param("orderId") Long orderId, @Param("orderStatus") String orderStatus);

    // 删除订单
    @Delete("DELETE FROM `orders` WHERE order_id = #{orderId}")
    int deleteOrder(Long orderId);

    // 更新订单地址
    @Update("UPDATE `orders` SET address_id = #{addressId} WHERE order_id = #{orderId}")
    int updateOrderAddress(@Param("orderId") Long orderId, @Param("addressId") Long addressId);

    // 创建订单
    @Insert("INSERT INTO `orders` (user_id, address_id, product_id, quantity, total_amount, order_status, order_note,order_number) " +
            "VALUES (#{user_id}, #{address_id}, #{product_id}, #{quantity}, #{total_amount}, #{order_status}, #{order_note},#{order_number})")
    int insertOrder(Order order);

}

