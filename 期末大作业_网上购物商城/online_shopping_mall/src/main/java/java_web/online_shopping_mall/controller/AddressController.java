package java_web.online_shopping_mall.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java_web.online_shopping_mall.POJO.DTO.AddressDto;
import java_web.online_shopping_mall.entity.Address;
import java_web.online_shopping_mall.service.AddressService;
import java_web.online_shopping_mall.util.Response;
import java_web.online_shopping_mall.util.mapStruct.AddressesMapperStruct;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Validated
@Valid
@RestController
@RequestMapping("/addresses")
public class AddressController {

    private  final AddressService addressService;
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * 新增收货地址
     */
    @PostMapping("/add")
    public Response<String> addAddress(@Valid @RequestBody AddressDto addressDto, HttpServletRequest request) {
        Long user_Id = (Long) request.getAttribute("userId");
        // 将DTO转换为实体对象
        Address address = AddressesMapperStruct.INSTANCE.toEntity(addressDto);
        address.setUser_id(user_Id);
        addressService.addAddress(address);
        return Response.success("添加成功");
    }

    /**
     * 根据 用户ID和地址id 更新地址
     */
    @PutMapping("/update")
    public Response<String> updateAddress(@Valid @RequestBody AddressDto addressDto, HttpServletRequest request) {
        Long user_Id = (Long) request.getAttribute("userId");
        // 转为Address实体对象
        Address address = AddressesMapperStruct.INSTANCE.toEntity(addressDto);
        address.setUser_id(user_Id );
        addressService.updateAddressByIdAndUserId(address);
        return Response.success("更新成功");
    }

    /**
     * 获取当前用户所有地址
     */
     @GetMapping
     public Response<List<AddressDto>> getMyAddress(HttpServletRequest request){
         Long userId = (Long) request.getAttribute("userId");
         return Response.success(addressService.getAddressesByUserId(userId));
     }

    /*
      根据 用户ID和地址id 删除地址
     */
    @DeleteMapping("/delete")
    public Response<String> deleteAddressById(HttpServletRequest request, @RequestParam Long addressId) {
        Long userId = (Long) request.getAttribute("userId");
        addressService.deleteAddressByIdAndUserId(addressId,userId);
        return Response.success("删除成功");
    }
}

