package edu.san.luc.pschedules.filter;

import edu.san.luc.pschedules.csv.GenericCsvReader;
import edu.san.luc.pschedules.message.PlaneMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlaneMessageSelectorTest {

    @Mock
    private GenericCsvReader genericCsvReader;

    @Mock
    private File mergingFile;

    @InjectMocks
    private PlaneMessageSelector fixture;

    @Test
    public void shouldReturnFalseWhenAcceptMessageThatExistsInOutputXmlFile() throws Exception {
        File file = new ClassPathResource("flight-shedule.xml").getFile();
        fixture.setMergingFile(file);
        PlaneMessage planeMessage = new PlaneMessage();
        planeMessage.setId("R777");
        Message<PlaneMessage> message = MessageBuilder.withPayload(planeMessage).build();

        boolean result = fixture.accept(message);

        assertFalse(result);
    }

    @Test
    public void shouldReturnTrueWhenAcceptMessageThatExistsInOutputXmlFile() throws Exception {
        File file = new ClassPathResource("flight-shedule.xml").getFile();
        fixture.setMergingFile(file);
        PlaneMessage planeMessage = new PlaneMessage();
        planeMessage.setId("R666");
        Message<PlaneMessage> message = MessageBuilder.withPayload(planeMessage).build();

        boolean result = fixture.accept(message);

        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseWhenAcceptMessageThatExistsInOutputFile() throws Exception {
        Message<PlaneMessage> message = MessageBuilder.withPayload(new PlaneMessage()).build();
        doReturn(true).when(mergingFile).exists();
        doReturn("test.csv").when(mergingFile).getName();
        doReturn(Arrays.asList(message.getPayload()))
                .when(genericCsvReader).read(eq(PlaneMessage.class), any(File.class));

        boolean result = fixture.accept(message);

        assertFalse(result);
    }

    @Test
    public void shouldReturnTrueWhenAcceptMessageThatExistsInOutputFile() throws Exception {
        Message<PlaneMessage> message = MessageBuilder.withPayload(new PlaneMessage()).build();
        doReturn(true).when(mergingFile).exists();
        doReturn(Collections.emptyList())
                .when(genericCsvReader).read(eq(PlaneMessage.class), any(File.class));

        boolean result = fixture.accept(message);

        assertTrue(result);
    }

    @Test
    public void shouldReturnTrueWhenAcceptMessageAndOutputFileNotExists() throws Exception {
        Message<PlaneMessage> message = MessageBuilder.withPayload(new PlaneMessage()).build();
        doReturn(true).when(mergingFile).exists();
        doThrow(new NullPointerException())
                .when(genericCsvReader).read(eq(PlaneMessage.class), any(File.class));

        boolean result = fixture.accept(message);

        assertTrue(result);
    }

    @Test
    public void shouldReturnTrueWhenAcceptMessageAndReadingOutputFileThrowsException() throws Exception {
        Message<PlaneMessage> message = MessageBuilder.withPayload(new PlaneMessage()).build();
        doReturn(false).when(mergingFile).exists();

        boolean result = fixture.accept(message);

        assertTrue(result);
    }
}