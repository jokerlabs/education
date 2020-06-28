package com.example.orderservice.service;

import com.example.orderservice.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author jiaxq
 * @since 2020-06-28
 */
public interface OrderService extends IService<Order> {

    String createOrder(String courseId, String memberIdByJwtToken);

    Order getOrderByNo(String orderNo);

    boolean isAcq(String courseId, String memberId);
}
