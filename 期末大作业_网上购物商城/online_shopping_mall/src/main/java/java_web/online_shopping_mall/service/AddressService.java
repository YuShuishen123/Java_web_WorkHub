package java_web.online_shopping_mall.service;

import java_web.online_shopping_mall.POJO.DTO.AddressDto;
import java_web.online_shopping_mall.entity.Address;

import java.util.List;

/**
 * 收货地址表业务接口
 */
public interface AddressService{
    // 新增收货地址
    void addAddress(Address address);
    // 获取当前用户所有收货地址
    List<AddressDto> getAddressesByUserId(Long user_Id);
    // 根据用户id和地址id删除收货地址
    void deleteAddressByIdAndUserId(Long addressId, Long user_Id);
    // 根据用户id和地址id获取收货地址
    Address getAddressByIdAndUserId(Long id, Long user_Id);
    // 更新收货地址
    void updateAddressByIdAndUserId(Address address);
    // 根据地址id获取地址信息
    Address getAddressById(Long id);

}
