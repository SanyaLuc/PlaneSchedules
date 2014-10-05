package edu.san.luc.pschedules.client;

import edu.san.luc.pschedules.message.PlaneMessage;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.MessagingException;

import java.util.Collection;
import java.util.Map;

/**
 * Created by sanya on 03.09.14.
 */
public class PlaneMessageEnricher {
    Map<String, String> countryPerCompanyMap;

    public String determineCountry(PlaneMessage message){
        return countryPerCompanyMap.get(message.getName());
    }

    public void setCountryPerCompanyMap(Map<String, String> countryPerCompanyMap) {
        this.countryPerCompanyMap = countryPerCompanyMap;
    }
}
