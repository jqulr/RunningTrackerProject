package persistence;

import exception.DuplicateEntryException;
import model.Date;
import model.Entry;
import model.Month;
import model.RunningLog;
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

        Entry entry = createEntry(10, JAN, 2024, 10, 60, 150);
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
            assertEquals("  good weather", runningLog.getJan().get(0).getNotes());
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

    @Test
    public void testMultipleEntriesDiffMonth() {
        RunningLog runningLog = addToMayAugNovEntries();
        jsonWriter = new JsonWriter("./data/MayAugMovEntriesAddedTest.json");

        try {
            jsonWriter.openWriter();
            jsonWriter.write(runningLog);
            jsonWriter.close();

            jsonReader = new JsonReader("./data/MayAugMovEntriesAddedTest.json");
            runningLog = jsonReader.read();

            // check size of list for each month
            assertEquals(1, runningLog.getMay().size());
            assertEquals(2, runningLog.getAugust().size());
            assertEquals(1, runningLog.getNovember().size());

            // check dates
            assertEquals("10/MAY/2024", runningLog.getMay().get(0).getDate().toString());
            assertEquals("10/AUG/2024", runningLog.getAugust().get(0).getDate().toString());
            assertEquals("11/AUG/2024", runningLog.getAugust().get(1).getDate().toString());
            assertEquals("10/NOV/2024", runningLog.getNovember().get(0).getDate().toString());

            // total aug distance, monthly and weekly
            assertEquals(25, runningLog.totalWeeklyDistance(Month.AUG, 10));
            assertEquals(25, runningLog.totalMonthlyDistance(Month.AUG));

        } catch (FileNotFoundException fne) {
            fail("FileNotFoundException not expected");
        } catch (IOException e) {
            fail("IOException not expected");
        }
    }

    // EFFECTS: adding one entry for may, two for aug and one for nov into running log, and returns the log
    public RunningLog addToMayAugNovEntries() {
        RunningLog runningLog = new RunningLog();

        Entry MayEntry = createEntry(10,Month.MAY, 2024,10,60,150);
        Entry AugEntry = createEntry(10,Month.AUG, 2024,10,60,150);
        Entry AugEntry2 = createEntry(11,Month.AUG, 2024,15,60,150);
        Entry NovEntry = createEntry(10,Month.NOV, 2024,10,60,150);

        try {
            runningLog.addEntry(MayEntry);
            runningLog.addEntry(AugEntry);
            runningLog.addEntry(AugEntry2);
            runningLog.addEntry(NovEntry);

        } catch (DuplicateEntryException dee) {
            fail("shouldn't have any duplicated entries");
        }

        return runningLog;

    }

    // EFFECTS: creates and returns an entry with specified information
    public Entry createEntry(int day, Month month, int year, double distance, int time, int avgHR) {
        Date Date = new Date(day, month, year);
        Entry Entry = new Entry(Date, distance, time, avgHR);
        Entry.addNotes(month + " :)");

        return Entry;
    }
}
