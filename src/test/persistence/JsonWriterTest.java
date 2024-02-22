package persistence;

import exception.DuplicateEntryException;
import model.Date;
import model.Entry;
import model.Month;
import model.RunningLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static model.Month.JAN;
import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    @Test
    public void testNoDestinationWriter() {
        jsonWriter = new JsonWriter("./data/my\0illegal:fileName.json");

        try {
            jsonWriter.openWriter();
            jsonWriter.write(new RunningLog());
            jsonWriter.close();
            fail("FileNotFoundException expected");
        } catch (FileNotFoundException fne) {
            //
        }
    }

    @Test
    public void testEmptyFileWriter() {
        jsonWriter = new JsonWriter("./data/EmptyRunningLog.json");

        try {
            jsonWriter.openWriter();
            jsonWriter.write(new RunningLog());
            jsonWriter.close();

            jsonReader = new JsonReader("./data/EmptyRunningLog.json");
            RunningLog runningLog = jsonReader.read();
            assertTrue(runningLog.getJan().isEmpty());
            assertTrue(runningLog.getFeb().isEmpty());
            assertTrue(runningLog.getMarch().isEmpty());
            assertTrue(runningLog.getApril().isEmpty());
            assertTrue(runningLog.getMay().isEmpty());
            assertTrue(runningLog.getJune().isEmpty());
            assertTrue(runningLog.getJuly().isEmpty());

        } catch (FileNotFoundException fne) {
            fail("FileNotFoundException not expected");
        } catch (IOException e) {
            fail("IOException not expected");
        }
    }

    @Test
    public void testJanEntryAddedWriter() {
        jsonWriter = new JsonWriter("./data/JanEntryAddedTest.json");

        Date date = new Date(10, Month.JAN, 2024);
        Entry entry = new Entry(date, 10, 60, 150);
        entry.addNotes("good weather");
        RunningLog runningLog = new RunningLog();
        try {
            runningLog.addEntry(entry);
            jsonWriter.openWriter();
            jsonWriter.write(runningLog);
            jsonWriter.close();

            jsonReader = new JsonReader("./data/JanEntryAddedTest.json");
            runningLog = jsonReader.read();
            assertEquals(1, runningLog.getJan().size());
            assertEquals("10/JAN/2024", runningLog.getJan().get(0).getDate().toString());
            assertEquals(10, runningLog.getJan().get(0).getDistance());
            assertEquals(60, runningLog.getJan().get(0).getTime());
            assertEquals(150, runningLog.getJan().get(0).getHeartRate());
            assertEquals("6.00 min/km", runningLog.getJan().get(0).getPace());
            assertEquals(" - good weather", runningLog.getJan().get(0).getNotes());
            assertEquals(10, runningLog.totalMonthlyDistance(JAN));

            assertTrue(runningLog.getFeb().isEmpty());
        } catch (DuplicateEntryException dee) {
            fail("DuplicateEntryException not expected");
        } catch (FileNotFoundException fne) {
            fail("FileNotFoundException not expected");
        } catch (IOException e) {
            fail("IOException not expected");
        }
    }
}
