package com.example.orderservice.service;

import com.example.orderservice.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author jiaxq
 * @since 2020-06-28
 */
public interface PayLogService extends IService<PayLog> {

    Map createPay(String orderNo);

    Map<String, String> queryPayStatus(String orderNo);

    void updateOrderStatus(Map<String, String> map);
}
