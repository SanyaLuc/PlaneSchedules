package edu.san.luc.pschedules.filter;

import edu.san.luc.pschedules.csv.GenericCsvReader;
import edu.san.luc.pschedules.message.PlaneMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.core.MessageSelector;
import org.springframework.messaging.Message;
import org.springframework.util.StringUtils;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sanya on 18.08.14.
 */
public class PlaneMessageSelector implements MessageSelector{
    private static final String ID_XPATH = "/PlaneMessages/PlaneMessage[id = '%s']/id";

    private File mergingFile;
    private XPath xPath;

    public PlaneMessageSelector() {
        xPath = XPathFactory.newInstance().newXPath();
    }

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
                if(mergingFile.getName().endsWith("csv"))
                    return !existsInCSV(planeMessage);
                if(mergingFile.getName().endsWith("xml"))
                    return !existsInXml(planeMessage);
            } catch (Exception e) {
                e.printStackTrace();
                return true;
            }
        }

        return true;
    }

    private boolean existsInCSV(PlaneMessage planeMessage){
        Set<PlaneMessage> oldMessages = new HashSet<>(genericCsvReader.read(PlaneMessage.class, mergingFile));
        return oldMessages.contains(planeMessage);
    }

    private boolean existsInXml(PlaneMessage planeMessage) throws FileNotFoundException, XPathExpressionException {
        String id = planeMessage.getId();
        String expr = String.format(ID_XPATH, id);
        InputSource inputSource = new InputSource(new FileInputStream(mergingFile));
        String result = (String)xPath.evaluate(expr, inputSource, XPathConstants.STRING);
        return !StringUtils.isEmpty(result);
    }
}
