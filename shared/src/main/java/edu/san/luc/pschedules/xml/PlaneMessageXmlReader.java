package edu.san.luc.pschedules.xml;

import edu.san.luc.pschedules.PlaneMessageReader;
import edu.san.luc.pschedules.message.PlaneMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanya on 06.10.14.
 */
public class PlaneMessageXmlReader implements PlaneMessageReader {
    private static final Logger logger = LoggerFactory.getLogger(PlaneMessageXmlReader.class);
    private XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

    public List<PlaneMessage> read(File file) {
        List<PlaneMessage> elements = new ArrayList<>();
        ;
        try (
                FileInputStream fileInputStream = new FileInputStream(file);
        ) {
            XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(fileInputStream);
            PlaneMessage obj, element = null;
            String localName = null;
            String value;
            int event;
            boolean result = false;
            do {
                try {
                    // Lock it!
                    FileLock lock = fileInputStream.getChannel().tryLock(0L, Long.MAX_VALUE, true);
                    try {
                        while (xmlStreamReader.hasNext()) {
                            event = xmlStreamReader.next();
                            switch (event) {
                                case XMLStreamConstants.START_ELEMENT:
                                    localName = xmlStreamReader.getLocalName();
                                    obj = createObject(localName);
                                    if (obj != null) {
                                        element = obj;
                                        elements.add(element);
                                        localName = null;
                                    }
                                    break;
                                case XMLStreamConstants.CHARACTERS:
                                    if (element != null && localName != null) {
                                        value = xmlStreamReader.getText();
                                        bindProperties(element, localName, value);
                                        localName = null;
                                    }
                                    break;
                            }
                        }
                        result = true;
                    } finally {
                        // Release the lock.
                        if (lock.channel().isOpen())
                            lock.release();
                    }
                } catch (OverlappingFileLockException ofle) {
                    try {
                        // Wait a bit
                        Thread.sleep(0);
                    } catch (InterruptedException ex) {
                        throw new InterruptedIOException("Interrupted waiting for a file lock.");
                    }
                }
            } while (!result);


        } catch (IOException | XMLStreamException e) {
            logger.error("Can not read " + file.getAbsolutePath(), e);
        }

        return elements;
    }

    private PlaneMessage createObject(String localName) {
        return localName.equals("PlaneMessage") ? new PlaneMessage() : null;
    }

    private void bindProperties(PlaneMessage element, String localName, String value) {
        switch (localName) {
            case "id":
                element.setId(value);
                break;
            case "name":
                element.setName(value);
                break;
            case "date":
                element.setDate(value);
                break;
            case "time":
                element.setTime(value);
                break;
            case "departureFrom":
                element.setDepartureFrom(value);
                break;
            case "arrivalTo":
                element.setArrivalTo(value);
        }
    }
}
