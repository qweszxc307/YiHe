package org.crown.service.task;

import org.crown.enums.OrderStatusEnum;
import org.crown.model.order.entity.Order;
import org.crown.service.order.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 定时任务
 * </p>
 *
 * @author ykMa
 */
@Transactional(readOnly = false)

@Component
@Configuration
@EnableScheduling
public class OrderTask {
    @Autowired
    private IOrderService orderService;
    @Scheduled(cron = "0 0 3 1/1 * ? ")
    public void configureTasks() {
        System.out.println("定时任务执行");
        List<Order> list = orderService.query().eq(Order::getStatus, OrderStatusEnum.INIT.value()).le(Order::getCloseTime, LocalDateTime.now()).list();
        if (list.size() > 0) {
            list.forEach(e -> {
                orderService.deleteOrder(e.getId());
            });
        }
    }
}