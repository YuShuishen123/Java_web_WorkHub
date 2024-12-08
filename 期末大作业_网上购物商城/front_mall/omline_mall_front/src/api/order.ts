//  引入request
import request from '@/utils/request'

// 定义通用的API响应接口
interface ApiResponse<T> {
    code: number;
    data: T;
    message?: string;
}

// 定义订单记录接口
export interface PurchaseRecord {
    orderId: string;
    productName: string;
    quantity: number;
    totalPrice: number;
    status: 'unpaid' | 'paid' | 'cancelled';
    remark?: string;
    createTime: string;
}

// 创建订单接口
interface OrderDTO {
    address_id: number;
    product_id: number;
    quantity: number;
    total_amount: string | number;  // 支持字符串或数字类型
    order_status: string;
    order_note?: string;
}

export function createOrder(orderData: OrderDTO): Promise<ApiResponse<any>> {
    return request.post('/Orders/create', JSON.stringify(orderData), {
        headers: {
            'Content-Type': 'application/json'
        }
    });
}

// 获取当前用户的所有订单接口
export function getOrders(): Promise<ApiResponse<PurchaseRecord[]>> {
    return request.get('/Orders');
}

// 获取所有订单接口
export function getAllOrders(): Promise<ApiResponse<PurchaseRecord[]>> {
    return request.get('/Orders/all');
}

// 更新订单状态接口
export function updateOrderStatus(orderId: string, newStatus: string): Promise<ApiResponse<PurchaseRecord>> {
    const params = new URLSearchParams();
    params.append('order_id', orderId);
    params.append('order_status', newStatus);
    
    return request.put('/Orders/updateStatus', params, {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    });
}

// 更新订单地址接口
export function updateOrderAddress(orderId: string, addressId: number): Promise<ApiResponse<PurchaseRecord>> {
    const params = new URLSearchParams();
    params.append('orderId', orderId);
    params.append('addressId', addressId.toString());
    
    return request.put('/Orders/updateAddress', params, {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    });
}

// 删除订单接口，application/x-www-form-urlencoded
export function deleteOrder(orderId: string): Promise<ApiResponse<PurchaseRecord>> {
    return request.put(`/Orders/delete?orderId=${orderId}`);
}
