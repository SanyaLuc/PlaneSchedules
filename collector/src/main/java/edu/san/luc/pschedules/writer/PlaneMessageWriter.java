package edu.san.luc.pschedules.writer;

import edu.san.luc.pschedules.csv.GenericCsvReader;
import edu.san.luc.pschedules.csv.GenericCsvWriter;
import edu.san.luc.pschedules.message.PlaneMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.*;

/**
 * Created by sanya on 18.08.14.
 */
public class PlaneMessageWriter {
    private static final Logger logger = LoggerFactory.getLogger(PlaneMessageWriter.class);

    private String outputFile;

    @Autowired
    private GenericCsvWriter genericCsvWriter;

    public void write(List<PlaneMessage> messages){
        File file = new File(outputFile);
        genericCsvWriter.append(messages, file);

        logger.info("Appending plane schedule to file: "+file.getAbsolutePath());
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }
}
