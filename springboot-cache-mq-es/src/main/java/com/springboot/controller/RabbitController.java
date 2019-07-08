package com.springboot.controller;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@EnableRabbit
@RestController
public class RabbitController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    /**
     * 发送
     */
    @GetMapping("/send")
    public void sendSessage() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "joe");
        map.put("age", 24);
        rabbitTemplate.convertAndSend("exc.direct", "queuefordirect", map);
    }

    /**
     * 接收
     */
    @GetMapping("/get")
    public void getSessage() {
        Object o = rabbitTemplate.receiveAndConvert("queuefordirect");
        System.out.println(o);
    }

    /**
     * 监听消息队列，一旦有消息进入执行方法
     *
     * @param m
     */
    @RabbitListener(queues = "queuefordirect")
    public void listenMessage(Message m) {
        System.out.println(m.getBody());
    }

    /**
     * 创建exchange，queue，binding
     */
    @GetMapping("/create")
    public void createExc() {
        amqpAdmin.declareExchange(new DirectExchange("amqpadmin.exc"));
        amqpAdmin.declareQueue(new Queue("amqpadmin.queue", true));
        amqpAdmin.declareBinding(new Binding("amqpadmin.queue", Binding.DestinationType.QUEUE, "amqpadmin.exc", "firstkey", null));
    }
}
