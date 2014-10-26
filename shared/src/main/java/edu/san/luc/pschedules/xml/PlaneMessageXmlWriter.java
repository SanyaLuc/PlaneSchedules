package edu.san.luc.pschedules.xml;

import com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter;
import edu.san.luc.pschedules.PlaneMessageWriter;
import edu.san.luc.pschedules.message.PlaneMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanya on 19.07.14.
 */
public class PlaneMessageXmlWriter implements PlaneMessageWriter {
    private static final Logger logger = LoggerFactory.getLogger(PlaneMessageXmlWriter.class);
    private XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
    private PlaneMessageXmlReader reader = new PlaneMessageXmlReader();

    public <T> boolean append(List<T> list, File file) {
        boolean result;
        List<T> fullList = new ArrayList<>();

        if(file.exists()){
            fullList.addAll((List<T>)reader.read(file));
        }
        fullList.addAll(list);
        result = write(fullList, file);

        return result;
    }

    public <T> boolean write(List<T> list, File file) {
        boolean result = false;

        try(FileOutputStream outputStream = new FileOutputStream(file)){
            XMLStreamWriter xmlStreamWriter = new IndentingXMLStreamWriter(xmlOutputFactory.createXMLStreamWriter(outputStream, "UTF-8"));
                do {
                    try {
                        // Lock it!
                        FileLock lock = outputStream.getChannel().lock();
                        try {
                            // Write the bytes.
                            writeList(list, xmlStreamWriter);
                            outputStream.flush();
                            result = true;
                        } finally {
                            // Release the lock.
                            lock.release();
                        }
                    } catch ( OverlappingFileLockException ofle ) {
                        try {
                            // Wait a bit
                            Thread.sleep(0);
                        } catch (InterruptedException ex) {
                            throw new InterruptedIOException ("Interrupted waiting for a file lock.");
                        }
                    }
                } while (!result);

        }catch(XMLStreamException | IOException e){
            logger.error("Can't write file "+file.getAbsolutePath(), e);
        }

        return result;
    }

    private <T> void writeList(List<T> list, XMLStreamWriter xmlStreamWriter) throws XMLStreamException {
        xmlStreamWriter.writeStartElement("PlaneMessages");
        if(list != null && list.size() > 0){
            for (T obj : list) {
                if(obj != null){
                    writePlaneMessage((PlaneMessage)obj, xmlStreamWriter);
                }
            }
        }
        xmlStreamWriter.writeEndElement();
    }

    private void writePlaneMessage(PlaneMessage message, XMLStreamWriter xmlStreamWriter) throws XMLStreamException {
        xmlStreamWriter.writeStartElement("PlaneMessage");

        xmlStreamWriter.writeStartElement("id");
        xmlStreamWriter.writeCharacters(message.getId());
        xmlStreamWriter.writeEndElement();
        xmlStreamWriter.writeStartElement("name");
        xmlStreamWriter.writeCharacters(message.getName());
        xmlStreamWriter.writeEndElement();
        xmlStreamWriter.writeStartElement("date");
        xmlStreamWriter.writeCharacters(message.getDate());
        xmlStreamWriter.writeEndElement();
        xmlStreamWriter.writeStartElement("time");
        xmlStreamWriter.writeCharacters(message.getTime());
        xmlStreamWriter.writeEndElement();
        xmlStreamWriter.writeStartElement("departureFrom");
        xmlStreamWriter.writeCharacters(message.getDepartureFrom());
        xmlStreamWriter.writeEndElement();
        xmlStreamWriter.writeStartElement("arrivalTo");
        xmlStreamWriter.writeCharacters(message.getArrivalTo());
        xmlStreamWriter.writeEndElement();

        xmlStreamWriter.writeEndElement();
    }
}
