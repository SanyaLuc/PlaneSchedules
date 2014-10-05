package edu.san.luc.pschedules.supplier;

import edu.san.luc.pschedules.client.StartCollectorApp;
import edu.san.luc.pschedules.csv.GenericCsvReader;
import edu.san.luc.pschedules.message.PlaneMessage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by sanya on 19.07.14.
 */
public class StartSupplierApp {

    public static void main(String[] args) throws InterruptedException, IOException {
        ApplicationContext applicationContext = runIntSupplier();

        StartCollectorApp.runIntCollector();
//        synchronized (applicationContext){
//            applicationContext.wait();
//        }
    }

    private static ApplicationContext runJmsSupplier() {
        return new ClassPathXmlApplicationContext("/common.xml","/embed_broker.xml","/jms-supplier-config.xml");
    }

    private static ApplicationContext runIntSupplier() throws IOException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/common.xml","/embed_broker.xml","/int-supplier-config.xml");

        MessageChannel messageChannel = applicationContext.getBean("inputChannel", MessageChannel.class);
        GenericCsvReader genericCsvReader = applicationContext.getBean("genericCsvReader", GenericCsvReader.class);
        File inputFile = applicationContext.getResource("classpath:flight-shedule.csv").getFile();
        List<PlaneMessage> planeMessages = genericCsvReader.read(PlaneMessage.class, inputFile);
        messageChannel.send(MessageBuilder.withPayload(planeMessages).build());

        return applicationContext;
    }
}
