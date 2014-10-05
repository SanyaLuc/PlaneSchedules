package edu.san.luc.pschedules.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by sanya on 19.07.14.
 */
public class StartCollectorApp {

    public static void main(String[] args) {
        runIntCollector();
    }

    public static ClassPathXmlApplicationContext runJMSCollector() {
        return new ClassPathXmlApplicationContext("/common.xml", "/common-collector-config.xml", "/jms-collector-config.xml");
    }

    public static ClassPathXmlApplicationContext runIntCollector() {
        return new ClassPathXmlApplicationContext("/common.xml", "/common-collector-config.xml", "/int-collector-config.xml");
    }
}
