package edu.san.luc.pschedules.csv;

import edu.san.luc.pschedules.message.PlaneMessage;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class GenericCsvReaderTest {
    private GenericCsvReader fixture = new GenericCsvReader();

    @Test
    public void shouldReturnPlaneMessageListWhenReadWithValidCsvFile(){
        File csvFile = new File(getClass().getClassLoader().getResource("flight-shedule.csv").getFile());

        List<PlaneMessage> result = fixture.read(PlaneMessage.class, csvFile);

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