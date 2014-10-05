package edu.san.luc.pschedules.csv;

import edu.san.luc.pschedules.message.PlaneMessage;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GenericCsvWriterTest {
    private GenericCsvWriter fixture = new GenericCsvWriter();

    private GenericCsvReader genericCsvReader = new GenericCsvReader();

    @Test
    public void shouldWriteCsvFileWhenWriteWithNotEmptyListAndExistingCsvFile(){
        File resultFile = new File("result.csv");

        boolean result = writeCsv(resultFile);

        assertTrue(result);

        List<PlaneMessage> resultList = genericCsvReader.read(PlaneMessage.class, resultFile);

        assertEquals(4, resultList.size());

        PlaneMessage planeMessage = resultList.get(0);

        assertEquals("R154", planeMessage.getId());
        assertEquals("Turkish Airline", planeMessage.getName());
        assertEquals("12.08.2014", planeMessage.getDate());
        assertEquals("12:50", planeMessage.getTime());
        assertEquals("Kiev", planeMessage.getArrivalTo());
        assertEquals("Antalia", planeMessage.getDepartureFrom());

        resultFile.delete();
    }

    private boolean writeCsv(File resultFile) {
        File inputFile = new File(getClass().getClassLoader().getResource("flight-shedule.csv").getFile());
        List<PlaneMessage> list = genericCsvReader.read(PlaneMessage.class, inputFile);

        return fixture.write(list, resultFile);
    }

    @Test
    public void shouldAppendToCsvFileWhenAppendWithNotEmptyListAndExistingCsvFile(){
        File resultFile = new File("result.csv");
        writeCsv(resultFile);

        PlaneMessage message = new PlaneMessage();
        message.setId("1");
        message.setArrivalTo("Dubai");
        message.setDepartureFrom("New York");
        message.setName("US Airlines");
        message.setTime("12:20");
        message.setDate("30.10.2014");
        List<PlaneMessage> additionalList = Arrays.asList(message);

        boolean result = fixture.append(additionalList, resultFile);

        assertTrue(result);

        List<PlaneMessage> resultList = genericCsvReader.read(PlaneMessage.class, resultFile);

        assertEquals(5, resultList.size());

        resultFile.delete();
    }
}