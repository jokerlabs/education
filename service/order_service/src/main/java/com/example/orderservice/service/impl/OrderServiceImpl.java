package com.example.orderservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.commonutils.orderVo.EduCourseVo;
import com.example.commonutils.orderVo.UcenterMemberVo;
import com.example.orderservice.client.EduServiceClient;
import com.example.orderservice.client.UcenterServiceClient;
import com.example.orderservice.entity.Order;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.orderservice.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author jiaxq
 * @since 2020-06-28
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    EduServiceClient eduServiceClient;

    @Autowired
    UcenterServiceClient ucenterServiceClient;

    /**
     * 创建订单
     *
     * @param courseId 课程id
     * @param memberId 用户id
     */
    @Override
    public String createOrder(String courseId, String memberId) {
        EduCourseVo info = eduServiceClient.getInfo(courseId);

        // 把订单加入数据库
        UcenterMemberVo member = ucenterServiceClient.getMemberInfoById(memberId);
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(info.getTitle());
        order.setCourseCover(info.getCover());
        order.setTeacherName(info.getTeacherName());
        order.setTotalFee(info.getPrice());
        order.setMemberId(memberId);
        order.setMobile(member.getMobile());
        order.setNickname(member.getNickname());
        order.setStatus(0);  // 订单状态(0; 未支付， 1： 已支付)
        order.setPayType(1); // 支付类型，微信

        // 创建order对象
        baseMapper.insert(order);
        return order.getOrderNo();
    }

    /**
     * 根据orderNo订单号查询订单信息
     *
     * @param orderNo 订单号
     */
    @Override
    public Order getOrderByNo(String orderNo) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        return baseMapper.selectOne(wrapper);
    }

    /**
     * 查询课程是否获取
     * @param courseId 课程id
     * @param memberId 用户id
     */
    @Override
    public boolean isAcq(String courseId, String memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberId);
        Integer count = baseMapper.selectCount(wrapper);
        return count > 0;
    }
}
