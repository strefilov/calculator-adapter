package com.service.adapter.component;

import com.service.adapter.config.SOAPConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.AmqpIOException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Component
public class MQComponent {
    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final Log LOGGER = LogFactory.getLog(MQComponent.class);
    public void send(String correlationId, String body) {
        try {

            MessageProperties props = MessagePropertiesBuilder.newInstance()
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .setCorrelationId("" + correlationId)
                .build();
            Message message = MessageBuilder.withBody(body.getBytes())
                .andProperties(props)
                .build();

            amqpAdmin.declareQueue(new Queue(SOAPConfiguration.queueName + correlationId, false));
            Binding binding = BindingBuilder.bind(new Queue(SOAPConfiguration.queueName + correlationId, false))
                .to(new TopicExchange(SOAPConfiguration.topicExchangeName)).with("" + correlationId);
           // binding.getArguments().put("CorrelationId",correlationId);
            amqpAdmin.declareBinding(
                binding
            );
            rabbitTemplate.convertAndSend(SOAPConfiguration.topicExchangeName, "" + correlationId,
                message);
        } catch (AmqpConnectException e) {
            LOGGER.error(e,e);
        }
    }

    public Integer receiver(String correlationId) {
        Message result;
        try {
            result = rabbitTemplate.receive(SOAPConfiguration.queueName + correlationId, 5000);
            if (result != null) {
                amqpAdmin.deleteQueue(SOAPConfiguration.queueName + correlationId);
                return Integer.valueOf(new String(result.getBody()));
            }
        } catch (Exception e) {
            LOGGER.error(e,e);
            String message = e.getMessage();
            if(e.getCause() != null && e.getCause().getCause() != null){
                message =  e.getCause().getCause().getLocalizedMessage();
            }
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND," not found correlationId = "+correlationId+" " + message, e);
        }
        return null;
    }
}
