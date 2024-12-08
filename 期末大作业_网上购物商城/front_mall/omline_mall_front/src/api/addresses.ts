// 引入request
import request from '@/utils/request'

// 修改 Address 接口以匹配后端返回的数据结构
export interface Address {
  id?: number;
  receiver_name: string;
  phone: string;
  province: string;
  city: string;
  district: string;
  detail: string;
  is_default: boolean;
}

// 新增地址的DTO接口
export interface AddressDto {
  id?: number;
  receiver_name: string;
  phone: string;
  province: string;
  city: string;
  district: string;
  detail: string;
  is_default: boolean;
}

// 获取地址列表
export function getUserAddresses(): Promise<{
  code: number;
  data: Address[];
  message?: string;
}> {
  return request.get('/addresses');
}

// 新增地址
export function addAddress(addressDto: AddressDto): Promise<any> {
  return request.post('/addresses/add', addressDto);
}

// 更新地址
export function updateAddress(addressDto: AddressDto): Promise<any> {
  return request.put('/addresses/update', addressDto);
}

// 删除地址
export function deleteAddress(addressId: number): Promise<any> {
  return request.delete('/addresses/delete', { params: { addressId } });
}

// 设置默认地址
export function setDefaultAddress(addressId: number): Promise<any> {
  return request.put('/addresses/set-default', { addressId });
}
