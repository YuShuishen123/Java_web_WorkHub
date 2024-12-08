package java_web.online_shopping_mall.service.imp;

import java_web.online_shopping_mall.POJO.DTO.AddressDto;
import java_web.online_shopping_mall.entity.Address;
import java_web.online_shopping_mall.exception.CustomException;
import java_web.online_shopping_mall.mapper.AddressMapper;
import java_web.online_shopping_mall.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收货地址表业务实现类
 */
@Service
public class AddressServiceImpl implements AddressService {
    private final AddressMapper addressMapper;
    public AddressServiceImpl(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Override
    public void addAddress(Address address) {
        int result = addressMapper.addAddress(address);
        if (result == 0){
            throw new CustomException("添加地址失败");
        }
    }

    @Override
    public List<AddressDto> getAddressesByUserId(Long userId) {
        return addressMapper.getAddressesByUserId(userId);
    }

    @Override
    public void deleteAddressByIdAndUserId(Long addressId, Long userId) {
        int result = addressMapper.deleteAddressByIdAndUserId(addressId, userId);
        if (result == 0){
            throw new CustomException("删除地址失败");
        }
    }
    // 根据地址id 查询地址
    @Override
    public Address getAddressByIdAndUserId(Long id, Long userId) {
        return addressMapper.getAddressByIdAndUserId(id, userId);
    }

    // 更新地址
    @Override
    public void updateAddressByIdAndUserId(Address address) {
        int result = addressMapper.updateAddressByIdAndUserId(address);
        if (result == 0){
            throw new CustomException("更新地址失败");
        }
    }

    // 根据地址id 获取地址
    @Override
    public Address getAddressById(Long id) {
        return addressMapper.getAddressById(id);
    }
}

