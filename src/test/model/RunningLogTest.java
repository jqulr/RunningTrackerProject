package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RunningLogTest {
    private RunningLog testLog;
    private Date janDateOne;
    private Date janDateTwo;
    private Date janDateThird;
    private Date janDateFourth;
    private Date febDateFirst;
    private Entry janEntry;
    private Entry janEntrySecond;
    private Entry janEntryThird;
    private Entry janEntryFourth;
    private Entry febEntryFirst;

    @BeforeEach
    void setUp() {
        testLog = new RunningLog();
        janDateOne = new Date(1,Month.JAN,2024);
        janDateTwo = new Date(15,Month.JAN,2024);
        janDateThird = new Date(7,Month.JAN,2024);
        janDateFourth = new Date(8,Month.JAN,2024);
        febDateFirst = new Date(10,Month.FEB,2024);
        janEntry = new Entry(janDateOne, 1, 10, 140);
        janEntrySecond = new Entry(janDateTwo, 10, 20, 150);
        janEntryThird = new Entry(janDateThird, 20, 20, 150);
        janEntryFourth = new Entry(janDateFourth, 20, 60, 150);
        febEntryFirst = new Entry(febDateFirst, 10, 60, 150);
    }

    @Test
    void testAddEntry() {
        assertEquals(0, testLog.getJan().size());
        addEntries();
        assertEquals(4, testLog.getJan().size());
    }

    @Test
    void testTotalMonthlyDistance() {
        addEntries();
        assertEquals(51, testLog.totalMonthlyDistance(Month.JAN));

        //no entry added for feb
        assertEquals(0, testLog.totalMonthlyDistance(Month.FEB));

        //add one entry to feb
        testLog.addEntry(febEntryFirst);
        assertEquals(10, testLog.totalMonthlyDistance(Month.FEB));


    }

    @Test
    void testTotalWeeklyDistance() {
        addEntries();
        //only includes entry 1 and entry 7
        assertEquals(21, testLog.totalWeeklyDistance(Month.JAN, 1));

        //only includes entry 6
        assertEquals(40, testLog.totalWeeklyDistance(Month.JAN, 6));

        //only includes entry 6
        assertEquals(40, testLog.totalWeeklyDistance(Month.JAN, 7));

        assertEquals(0, testLog.totalWeeklyDistance(Month.JAN, 16));

    }

    @Test
    public void testSelectMonth() {
        assertEquals(testLog.getJan(), testLog.selectMonth(Month.JAN));
        assertEquals(testLog.getMarch(), testLog.selectMonth(Month.MARCH));
        assertEquals(testLog.getOctober(), testLog.selectMonth(Month.OCT));
    }

    public void addEntries() {
        testLog.addEntry(janEntry);
        testLog.addEntry(janEntrySecond);
        testLog.addEntry(janEntryThird);
        testLog.addEntry(janEntryFourth);
    }
}