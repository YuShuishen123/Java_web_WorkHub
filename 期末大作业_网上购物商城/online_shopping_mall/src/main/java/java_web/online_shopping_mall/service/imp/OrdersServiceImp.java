package java_web.online_shopping_mall.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java_web.online_shopping_mall.POJO.DTO.OrdersDTO;
import java_web.online_shopping_mall.POJO.DTO.OrdersNewStatusDTO;
import java_web.online_shopping_mall.POJO.DTO.VO.OrdersVO;
import java_web.online_shopping_mall.entity.Address;
import java_web.online_shopping_mall.entity.Order;
import java_web.online_shopping_mall.service.OrdersService;
import java_web.online_shopping_mall.mapper.OrdersMapper;
import java_web.online_shopping_mall.util.mapStruct.OrdersMapstruct;
import java_web.online_shopping_mall.service.AddressService;
import java_web.online_shopping_mall.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServiceImp extends ServiceImpl<OrdersMapper, Order> implements OrdersService {

    private final OrdersMapper ordersMapper;
    private final AddressService addressService;
    private final ProductService productService;

    public OrdersServiceImp(OrdersMapper ordersMapper, AddressService addressService, ProductService productService) {
        this.ordersMapper = ordersMapper;
        this.addressService = addressService;
        this.productService = productService;
    }

    /**
     * 根据用户 ID 查询用户所有订单
     *
     * @param userId 用户 ID
     * @return 订单列表
     */
    @Override
    public List<OrdersVO> selectAllOrdersByUserId(Long userId) {
        // 使用手动定义的查询方法
        List<Order> orders = ordersMapper.selectOrdersByUserId(userId);
        System.out.println(orders);
        return getOrdersVOS(orders);
    }

    // 更新订单状态
    @Override
    public void updateOrderStatus(OrdersNewStatusDTO ordersNewStatusDTO) {
        // 更新订单状态
        Long orderId = ordersNewStatusDTO.getOrder_id();
        // 判断是否由未支付转为已支付，如果是则更新商品库存
        if (ordersNewStatusDTO.getOrder_status().equals("已支付")) {
            // 先根据当前订单id查询出订单
            Order order = ordersMapper.selectOrderById(orderId);
            // 更新商品库存
            updateProductStockIfPaid(order);
            System.out.println("更新状态的库存更新成功");
        }
        String status = ordersNewStatusDTO.getOrder_status();
        int rows = ordersMapper.updateOrderStatus(orderId, status);
        if (rows == 0) {
            throw new RuntimeException("订单状态更新失败，订单ID不存在或状态未改变");
        }
    }

    @Override
    public List<OrdersVO> selectAllOrders() {
        // 查询所有订单
        List<Order> orders = ordersMapper.selectAllOrders();
        return getOrdersVOS(orders);
    }

    private List<OrdersVO> getOrdersVOS(List<Order> orders) {
        List<OrdersVO> ordersVOS = new java.util.ArrayList<>();
        for (Order order : orders) {
            // 使用 mapstruct 将 Orders 对象转换为 OrdersVO 对象
            OrdersVO ordersVO = OrdersMapstruct.INSTANCE.toVO(order);

            // 查询订单对应的地址信息
            Address address = addressService.getAddressById(order.getAddress_id());
            if (address != null) {
                ordersVO.setAddress(address.getProvince() + address.getCity() + address.getDistrict() + address.getDetail());
            } else {
                ordersVO.setAddress("地址信息不存在");
            }
            // 查询订单对应的商品信息
            ordersVO.setProductName(productService.getProductById(order.getProduct_id()).getName());
            if (ordersVO.getProductName() == null) {
                ordersVO.setProductName("商品信息不存在");
            }
            // 将 OrdersVO 对象添加到 OrdersVO 列表中
            ordersVOS.add(ordersVO);
        }
        return ordersVOS;
    }

    @Override
    public void deleteOrder(Long orderId) {
        // 删除订单
        int rows = ordersMapper.deleteOrder(orderId);
        if (rows == 0) {
            throw new RuntimeException("删除失败，订单ID不存在");
        }
    }

    @Override
    public void updateOrderAddress(Long orderId, Long addressId) {
        // 更新订单地址
        int rows = ordersMapper.updateOrderAddress(orderId, addressId);
        if (rows == 0) {
            throw new RuntimeException("更新失败，订单ID不存在");
        }
    }

    // 创建订单
    @Override
    public void insertOrder(OrdersDTO ordersDTO, Long userId) {
        // 使用 mapstruct 将 OrdersDTO 对象转换为 Order 对象
        Order order = OrdersMapstruct.INSTANCE.toEntity(ordersDTO);// }
        // 设置用户 ID
        order.setUser_id(userId);
        // 生成单号，根据5位随机数字+8位时间戳+当前用户ID(最后一位)生成
        String orderNumber = String.format("%05d%08d%01d", (int) (Math.random() * 100000), System.currentTimeMillis(), userId % 10);
        order.setOrder_number(orderNumber);
        // 判断订单的状态是否为已支付
        if (order.getOrder_status().equals("已支付")) {
            // 更新商品库存
            updateProductStockIfPaid(order);
        }
        // 插入订单
        if (ordersMapper.insertOrder(order) == 0) {
            throw new RuntimeException("创建失败");
        }
    }

    // 库存更新代码封装
    private void updateProductStockIfPaid(Order order) {
        if (order.getOrder_status().equals("已支付")) {
            Long productId = order.getProduct_id();
            Integer quantity = order.getQuantity();
            Integer productStock = productService.getProductById(productId).getStock();
            if (productStock < quantity) {
                throw new RuntimeException("商品库存不足");
            }
            productService.updateProductStock(productId, productStock - quantity);
            System.out.println("库存更新成功");
        }
    }

}
