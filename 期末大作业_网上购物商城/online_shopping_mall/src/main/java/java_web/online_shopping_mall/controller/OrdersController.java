package java_web.online_shopping_mall.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java_web.online_shopping_mall.POJO.DTO.OrdersDTO;
import java_web.online_shopping_mall.POJO.DTO.OrdersNewStatusDTO;
import java_web.online_shopping_mall.POJO.DTO.VO.OrdersVO;
import java_web.online_shopping_mall.service.OrdersService;
import org.springframework.web.bind.annotation.*;
import java_web.online_shopping_mall.util.Response;

import java.util.List;

//商品订单控制器

@Valid
@RestController
@RequestMapping("/Orders")
public class OrdersController {
    private  final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    // 根据用户id查询用户所有订单
    @GetMapping
    public Response<List<OrdersVO>> getOrders(HttpServletRequest request) {
        // 从request中获取用户id
        Long userId = (Long) request.getAttribute("userId");
        return Response.success(ordersService.selectAllOrdersByUserId(userId));

    }

    // 查询所有订单
    @GetMapping("/all")
    public Response<List<OrdersVO>> getAllOrders() {
        return Response.success(ordersService.selectAllOrders());
    }

    // 更新订单状态
    @PutMapping("/updateStatus")
    public Response<String> updateOrderStatus(@Valid @ModelAttribute OrdersNewStatusDTO ordersNewStatusDTO) {
        ordersService.updateOrderStatus(ordersNewStatusDTO);
        return Response.success("更新成功");
    }

    // 更新订单地址
    @PutMapping("/updateAddress")
    public Response<String> updateOrderAddress(Long orderId, Long addressId) {
        ordersService.updateOrderAddress(orderId, addressId);
        return Response.success("更新成功");
    }

    // 删除订单记录
    @PutMapping("/delete")
    public Response<String> deleteOrder(Long orderId) {
        ordersService.deleteOrder(orderId);
        return Response.success("删除成功");
    }

    // 创建订单
    @PostMapping("/create")
    public Response<String> insertOrder(@Valid @RequestBody OrdersDTO ordersDTO,HttpServletRequest request) {
        System.out.println(ordersDTO);
        // 从request中获取用户id
        Long userId = (Long) request.getAttribute("userId");
        ordersService.insertOrder(ordersDTO,userId);
        return Response.success("创建成功");
    }
}
