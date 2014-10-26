package edu.san.luc.pschedules.writer;

import edu.san.luc.pschedules.message.PlaneMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.File;
import java.util.*;

/**
 * Created by sanya on 18.08.14.
 */
public class PlaneMessageWriter {
    private static final Logger logger = LoggerFactory.getLogger(PlaneMessageWriter.class);

    private String outputFile;

    @Autowired
    @Qualifier("planeMessageXmlWriter")
    private edu.san.luc.pschedules.PlaneMessageWriter planeMessageWriter;

    public void write(List<PlaneMessage> messages){
        File file = new File(outputFile);
        planeMessageWriter.append(messages, file);

        logger.info("Appending plane schedule to file: "+file.getAbsolutePath());
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }
}
