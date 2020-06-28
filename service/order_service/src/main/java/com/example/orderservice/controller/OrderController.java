package com.example.orderservice.controller;


import com.example.commonutils.JwtUtils;
import com.example.commonutils.Result;
import com.example.orderservice.client.EduServiceClient;
import com.example.orderservice.client.UcenterServiceClient;
import com.example.orderservice.entity.Order;
import com.example.orderservice.service.OrderService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author jiaxq
 * @since 2020-06-28
 */
@RestController
@RequestMapping("/order/")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     */
    @PostMapping("/create/{courseId}")
    public Result saveOrder(@PathVariable String courseId, HttpServletRequest request) {
        String orderNo = orderService.createOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return Result.ok().data("item", orderNo);
    }

    @PostMapping("/create/{courseId}/{memberId}")
    public Result saveOrder2(@PathVariable String courseId, @PathVariable String memberId) {
        String orderNo = orderService.createOrder(courseId, memberId);
        return Result.ok();
    }

    /**
     * 根据订单id查询订单信息
     */
    @GetMapping("/{orderNo}")
    public Result getInfo(@PathVariable String orderNo) {
        Order order = orderService.getOrderByNo(orderNo);
        return Result.ok().data("item", order);
    }

    /**
     * 查询某个用户是否购买/获取课程
     * @param courseId 课程id
     * @param memberId 用户id
     */
    @GetMapping("/api/isGet/{courseId}/{memberId}")
    public boolean getStatus(@PathVariable String courseId, @PathVariable String memberId) {
        return orderService.isAcq(courseId, memberId);
    }
}

