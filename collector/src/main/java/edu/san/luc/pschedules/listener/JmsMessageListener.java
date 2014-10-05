package edu.san.luc.pschedules.listener;

import edu.san.luc.pschedules.message.PlaneMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.messaging.MessageChannel;

import javax.jms.*;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by sanya on 16.08.14.
 */
public class JmsMessageListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(JmsMessageListener.class);

    @Autowired
    private AtomicInteger counter = null;

    @Autowired
    @Qualifier("inputChannel")
    private MessageChannel messageChannel;

    private SimpleMessageConverter simpleMessageConverter = new SimpleMessageConverter();

    /**
     * Implementation of <code>MessageListener</code>.
     */
    public void onMessage(Message message) {
        try {
            PlaneMessage planeMessage = (PlaneMessage) simpleMessageConverter.fromMessage(message);

            logger.info("Processed message '{}'.", planeMessage);

            messageChannel.send(MessageBuilder.withPayload(Collections.singletonList(planeMessage)).build());

            counter.incrementAndGet();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
