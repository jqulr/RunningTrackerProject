package persistence;

import model.RunningLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static model.Month.JAN;
import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {
    private JsonReader jsonReader;

    @Test
    public void testNoFileReader() {
        jsonReader = new JsonReader("./data/noSuchFile.json");
        try {
            RunningLog runningLog = jsonReader.read();
            fail("IOException excepted");
        } catch (IOException e) {
            //
        }
    }

    @Test
    public void testEmptyFileReader() {
        jsonReader = new JsonReader("./data/EmptyRunningLog.json");

        try {
            RunningLog runningLog = jsonReader.read();
            assertTrue(runningLog.getJan().isEmpty());
            assertTrue(runningLog.getFeb().isEmpty());
            assertTrue(runningLog.getMarch().isEmpty());
            assertTrue(runningLog.getApril().isEmpty());
            assertTrue(runningLog.getMay().isEmpty());
            assertTrue(runningLog.getJune().isEmpty());
            assertTrue(runningLog.getJuly().isEmpty());

        } catch (IOException e) {
            fail("IOException not expected");
        }
    }

    @Test
    public void testAddedJanEntryReader() {
        jsonReader = new JsonReader("./data/JanEntryAddedTest.json");

        try {
            RunningLog runningLog = jsonReader.read();
            assertEquals(1, runningLog.getJan().size());
            assertEquals("10/JAN/2024", runningLog.getJan().get(0).getDate().toString());
            assertEquals(10, runningLog.getJan().get(0).getDistance());
            assertEquals(60, runningLog.getJan().get(0).getTime());
            assertEquals(150, runningLog.getJan().get(0).getHeartRate());
            assertEquals("6.00 min/km", runningLog.getJan().get(0).getPace());
            assertEquals(" / good weather", runningLog.getJan().get(0).getNotes());

            assertEquals(10, runningLog.totalMonthlyDistance(JAN));

            assertTrue(runningLog.getFeb().isEmpty());

        } catch (IOException e) {
            fail("IOException not expected");
        }
    }

}
