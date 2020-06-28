package com.example.orderservice.controller;


import com.example.commonutils.Result;
import com.example.orderservice.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author jiaxq
 * @since 2020-06-28
 */
@RestController
@RequestMapping("/order/pay")
@CrossOrigin
public class PayLogController {
    @Autowired
    PayLogService payService;

    /**
     * 生成微信支付二维码
     */
    @GetMapping("/{orderNo}")
    public Result createPay(@PathVariable String orderNo){
        System.out.println(orderNo);
        Map payMap = payService.createPay(orderNo);
        System.out.println(payMap);
        return Result.ok().data(payMap);
    }

    /**
     * 获取支付状态，支付完成后操作
     */
    @GetMapping("/status/{orderNo}")
    public Result getPayStatus(@PathVariable String orderNo){
        Map<String, String> map = payService.queryPayStatus(orderNo);
        if (map == null) {
            return Result.error().message("支付出错");
        }
        if (map.get("trade_state").equals("SUCCESS")) {
            // 更改订单状态
            payService.updateOrderStatus(map);
            return Result.ok().message("支付成功");
        }
        return Result.ok().code(25000).message("支付中");
    }
}

