package com.poshist.common.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TTLQueueConfig {
    public static final String ALERT_QUEUE="alert.queue";
    public static final String ALERT_EXCHANGE="alert.exchange";
    //public static final String ALERT_TIMEOUT_QUEUE="alert.timeOut.queue";

    @Bean
    public Queue queue() {
        return new Queue(ALERT_QUEUE);
    }

    // 配置默认的交换机
    @Bean
    CustomExchange customExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        //参数二为类型：必须是x-delayed-message
        return new CustomExchange(ALERT_EXCHANGE, "x-delayed-message", true, false, args);
    }
    // 绑定队列到交换器
    @Bean
    Binding binding(Queue queue, CustomExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ALERT_QUEUE).noargs();
    }


//    /**
//     * 警告exchange
//     */
//    @Bean
//    public DirectExchange orderExchange(){
//        return new DirectExchange(ALERT_EXCHANGE,true,false,null);
//    }
//
//    /**
//     * 警告队列
//     */
//    @Bean
//    public Queue orderQueue() {
//        // 设置超时转发策略 超时后消息会通过x-dead-letter-exchange 转发到x-dead-letter-routing-key绑定的队列中
//        Map<String, Object> arguments = new HashMap<>(2);
//        arguments.put("x-dead-letter-exchange", ALERT_EXCHANGE);
//        arguments.put("x-dead-letter-routing-key", ALERT_TIMEOUT_QUEUE);
//        Queue queue = new Queue(ALERT_QUEUE,true,false,false,arguments);
//        return queue;
//    }
//
//    /**
//     * 警告超时队列
//     * @return
//     */
//    @Bean
//    public Queue orderTimeoutQueue() {
//        Queue queue = new Queue(ALERT_TIMEOUT_QUEUE,true,false,false);
//        return queue;
//    }
//
//    /**
//     * 警告队列绑定exchange
//     * @return
//     */
//    @Bean
//    public Binding orderQueueBinding() {
//        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with(ALERT_QUEUE);
//    }
//
//
//    /**
//     * 警告订单队列绑定exchange
//     * @return
//     */
//    @Bean
//    public Binding  orderTimeoutQueueBinding() {
//        return BindingBuilder.bind(orderTimeoutQueue()).to(orderExchange()).with(ALERT_TIMEOUT_QUEUE);
//    }

}
