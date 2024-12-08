package java_web.online_shopping_mall.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java_web.online_shopping_mall.POJO.DTO.AddressDto;
import java_web.online_shopping_mall.entity.Address;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

/**
 * 收货地址表 Mapper 接口
 */
@Mapper
public interface AddressMapper extends BaseMapper<Address> {
    // 新增收货地址
    @Insert("INSERT INTO addresses(user_id, receiver_name, phone, province, city, district, detail, is_default) " +
            "VALUES (#{user_id}, #{receiver_name}, #{phone}, #{province}, #{city}, #{district}, #{detail}, #{is_default})")
    int addAddress(Address address);

    // 获取当前用户所有收货地址
    @Select("SELECT * FROM addresses WHERE user_id = #{user_Id}")
    List<AddressDto> getAddressesByUserId(Long user_Id);

    // 根据地址ID和用户ID查询地址
    @Select("SELECT * FROM addresses WHERE id = #{id} AND user_id = #{user_Id}")
    Address getAddressByIdAndUserId(Long id, Long user_Id);

    // 根据地址ID和用户ID删除地址
    @Delete("DELETE FROM addresses WHERE id = #{addressId} AND user_id = #{user_Id}")
    int deleteAddressByIdAndUserId(Long addressId, Long user_Id);

    // 根据地址ID和用户ID修改地址
    @Update("UPDATE addresses " +
            "SET receiver_name = #{receiver_name}, phone = #{phone}, " +
            "province = #{province}, city = #{city}, district = #{district}, detail = #{detail}, is_default = #{is_default} " +
            "WHERE id = #{id} AND user_id = #{user_id}")
    int updateAddressByIdAndUserId(Address address);

    // 根据地址ID获取地址
    @Select("SELECT id,province, city, district,detail FROM addresses WHERE id = #{id}")
    Address getAddressById(Long id);
}
