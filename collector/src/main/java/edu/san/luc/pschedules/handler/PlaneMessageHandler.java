package edu.san.luc.pschedules.handler;

import edu.san.luc.pschedules.csv.GenericCsvReader;
import edu.san.luc.pschedules.csv.GenericCsvWriter;
import edu.san.luc.pschedules.message.PlaneMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

/**
 * Created by sanya on 18.08.14.
 */
public class PlaneMessageHandler {
    private static final Logger logger = LoggerFactory.getLogger(PlaneMessageHandler.class);

    private String outputFile;


    private GenericCsvReader genericCsvReader;

    private GenericCsvWriter genericCsvWriter;

    public void handle(List<PlaneMessage> messages){
        File file = new File(outputFile);
        mergeScheduleToCSVFile(messages, file);
    }

    private synchronized void mergeScheduleToCSVFile(List<PlaneMessage> messages, File file){
        Set<PlaneMessage> messageSet = new TreeSet<PlaneMessage>(new Comparator<PlaneMessage>() {
            @Override
            public int compare(PlaneMessage o1, PlaneMessage o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });

        if(file.exists()){
            logger.info("Merging plane schedules in existing file: "+file.getAbsolutePath());

            List<PlaneMessage> oldMessages = genericCsvReader.read(PlaneMessage.class, file);
            messageSet.addAll(oldMessages);
        } else {
            file.getParentFile().mkdirs();
        }

        messageSet.addAll(messages);

        genericCsvWriter.write(new ArrayList<Object>(messageSet), file);

        logger.info("Writing plane schedule to file: "+file.getAbsolutePath());
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }
}
