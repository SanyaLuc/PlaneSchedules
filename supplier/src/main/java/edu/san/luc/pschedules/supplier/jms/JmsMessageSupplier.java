package edu.san.luc.pschedules.supplier.jms;

import edu.san.luc.pschedules.csv.GenericCsvReader;
import edu.san.luc.pschedules.message.PlaneMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.annotation.PostConstruct;
import javax.jms.*;
import java.io.File;
import java.util.List;

/**
 * Created by sanya on 16.08.14.
 */
public class JmsMessageSupplier {

    private static final Logger logger = LoggerFactory.getLogger(JmsMessageSupplier.class);

    @Autowired
    private JmsTemplate template = null;

    private GenericCsvReader genericCsvReader = new GenericCsvReader();

    /**
     * Generates JMS messages
     */
    @PostConstruct
    public void generateMessages() throws JMSException {
        File inputFile = new File(getClass().getClassLoader().getResource("flight-shedule.csv").getFile());

        List<PlaneMessage> planeMessages = genericCsvReader.read(PlaneMessage.class, inputFile);

        int i = 0;
        for (final PlaneMessage planeMessage : planeMessages) {
            final int index = i;
            planeMessage.setMessageCount(i);

            template.send(new MessageCreator() {
                public Message createMessage(Session session) throws JMSException {
                    ObjectMessage message = session.createObjectMessage(planeMessage);

                    logger.info("Sending message: " + planeMessage);

                    return message;
                }
            });

            i++;
        }
    }

}

