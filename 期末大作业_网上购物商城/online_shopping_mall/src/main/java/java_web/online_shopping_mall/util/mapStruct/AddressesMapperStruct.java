package java_web.online_shopping_mall.util.mapStruct;

import java_web.online_shopping_mall.POJO.DTO.AddressDto;
import java_web.online_shopping_mall.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface AddressesMapperStruct {
    AddressesMapperStruct INSTANCE = Mappers.getMapper(AddressesMapperStruct.class);

    Address toEntity(AddressDto addressDto);


    AddressDto toDto(Address address);





}
