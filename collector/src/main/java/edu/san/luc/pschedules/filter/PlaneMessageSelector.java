package edu.san.luc.pschedules.filter;

import edu.san.luc.pschedules.csv.GenericCsvReader;
import edu.san.luc.pschedules.message.PlaneMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.integration.core.MessageSelector;
import org.springframework.messaging.Message;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by sanya on 18.08.14.
 */
public class PlaneMessageSelector implements MessageSelector{
    private File mergingFile;

    public void setMergingFile(File mergingFile) {
        this.mergingFile = mergingFile;
    }

    @Autowired
    private GenericCsvReader genericCsvReader;

    @Override
    public boolean accept(Message<?> message) {
        if(mergingFile.exists()){
            try {
                PlaneMessage planeMessage = (PlaneMessage)message.getPayload();
                Set<PlaneMessage> oldMessages = new HashSet<PlaneMessage>(genericCsvReader.read(PlaneMessage.class, mergingFile));
                return !oldMessages.contains(planeMessage);
            } catch (Exception e) {
                return true;
            }
        }

        return true;
    }
}
