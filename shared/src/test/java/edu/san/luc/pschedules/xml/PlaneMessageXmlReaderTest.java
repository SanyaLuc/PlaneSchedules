package edu.san.luc.pschedules.xml;

import edu.san.luc.pschedules.message.PlaneMessage;
import edu.san.luc.pschedules.xml.PlaneMessageXmlReader;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PlaneMessageXmlReaderTest {
    private PlaneMessageXmlReader fixture = new PlaneMessageXmlReader();

    @Test
    public void shouldReturnPlaneMessageListWhenReadWithValidXmlFile(){
        File xmlFile = new File(getClass().getClassLoader().getResource("flight-shedule.xml").getFile());

        List<PlaneMessage> result = fixture.read(xmlFile);

        assertEquals(4, result.size());

        PlaneMessage planeMessage = result.get(0);

        assertEquals("R154", planeMessage.getId());
        assertEquals("Turkish Airline", planeMessage.getName());
        assertEquals("12.08.2014", planeMessage.getDate());
        assertEquals("12:50", planeMessage.getTime());
        assertEquals("Kiev", planeMessage.getArrivalTo());
        assertEquals("Antalia", planeMessage.getDepartureFrom());
    }

}