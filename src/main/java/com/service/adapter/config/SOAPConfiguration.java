package com.service.adapter.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.tempuri.CalculatorSoap;

/**
 *  Configuration Soap and Rabbit
 */
@Configuration
public class SOAPConfiguration {
    private static final Log LOGGER = LogFactory.getLog(SOAPConfiguration.class);

    private static final String URI = "http://www.dneonline.com/calculator.asmx";

    private static final String SERVICE_ADAPTER_WSDL = "org.tempuri";

    public static final String topicExchangeName = "spring-boot-exchange";

    public static final String queueName = "strefilov";

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
    }

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath(SERVICE_ADAPTER_WSDL);
        return marshaller;
    }

    @Bean
    public CalculatorSoap CalculatorSoapCLient() {

        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(CalculatorSoap.class);
        factory.setAddress(URI);

        CalculatorSoap service = (CalculatorSoap) factory.create();
        try {
            final Client client = ClientProxy.getClient(service);

        } catch (Exception e) {
            LOGGER.error(e, e);
        }
        return service;
    }
}
