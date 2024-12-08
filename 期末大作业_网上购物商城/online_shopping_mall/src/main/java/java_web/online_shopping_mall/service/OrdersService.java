package java_web.online_shopping_mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java_web.online_shopping_mall.POJO.DTO.OrdersDTO;
import java_web.online_shopping_mall.POJO.DTO.OrdersNewStatusDTO;
import java_web.online_shopping_mall.entity.Order;
import java.util.List;
import java_web.online_shopping_mall.POJO.DTO.VO.OrdersVO;

public interface OrdersService extends IService<Order> {

    // 根据用户id查询用户所有订单
    List<OrdersVO> selectAllOrdersByUserId(Long userId);

    // 更新订单状态(由未支付转为支付)
    void updateOrderStatus(OrdersNewStatusDTO ordersNewStatusDTO);

    // 查询所有订单
    List<OrdersVO> selectAllOrders();

    // 删除订单记录
    void deleteOrder(Long orderId);

    // 根据订单ID和地址ID更改订单地址
    void updateOrderAddress(Long orderId, Long addressId);

    // 创建订单，根据DTO创建订单
    void insertOrder(OrdersDTO orderT,Long User_ID);

}
