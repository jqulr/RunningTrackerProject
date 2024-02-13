package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EntryTest {

    private Entry testEntry;
    private Entry testEntryTwo;
    private Date testDate;

    @BeforeEach
    void setUp() {
        testDate = new Date(1, Month.JAN, 2024);
        testEntry = new Entry(testDate, 1, 10, 140);
        testEntryTwo = new Entry(testDate, 10.8, 60, 140);
    }

    @Test
    void testConstructor() {
        assertEquals(Month.JAN, testEntry.getMonth());
        assertEquals(1, testEntry.getDistance());
        assertEquals(10, testEntry.getTime());
        assertEquals(140, testEntry.getHeartRate());
    }

    @Test
    void testSetPace() {
        testEntry.setPace();
        testEntryTwo.setPace();
        assertEquals("10.00 min/km", testEntry.getPace());
        assertEquals("5.56 min/km", testEntryTwo.getPace());
    }

    @Test
    void testAddNotes() {
        testEntry.addNotes("sunny day");
        assertEquals("Notes: " + " sunny day", testEntry.getNotes());
        testEntry.addNotes("warm");
        assertEquals("Notes: " + " sunny day" + " warm"
                                , testEntry.getNotes());
    }
}