package model;

import exception.DuplicateEntryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RunningLogTest {
    private RunningLog testLog;
    private Entry janEntry;
    private Entry janEntryDuplicate;
    private Entry janEntryDupYear;
    private Entry janEntryDupDay;
    private Entry janEntrySecond;
    private Entry janEntryThird;
    private Entry janEntryFourth;
    private Entry febEntryFirst;
    private List<Month> monthList;
    private List<Date> dayList;
    private List<Entry> entryList;


    @BeforeEach
    void setUp() {
        testLog = new RunningLog();
        Date janDateOne = new Date(1, Month.JAN, 2024);
        Date janDateOneDupYear = new Date(2, Month.JAN, 2024);
        Date janDateOneDupDay = new Date(1, Month.JAN, 2023);
        Date janDateTwo = new Date(15, Month.JAN, 2024);
        Date janDateThird = new Date(7, Month.JAN, 2024);
        Date janDateFourth = new Date(8, Month.JAN, 2024);
        Date febDateFirst = new Date(10, Month.FEB, 2024);
        janEntry = new Entry(janDateOne, 1, 10, 140);

        janEntryDuplicate = new Entry(janDateOne, 1, 10, 140);
        janEntryDupYear = new Entry(janDateOneDupYear, 1, 10, 140);
        janEntryDupDay = new Entry(janDateOneDupDay, 1, 10, 140);

        janEntrySecond = new Entry(janDateTwo, 10, 20, 150);
        janEntryThird = new Entry(janDateThird, 20, 20, 150);
        janEntryFourth = new Entry(janDateFourth, 20, 60, 150);
        febEntryFirst = new Entry(febDateFirst, 10, 60, 150);

        monthList = new ArrayList<>();
        monthList.add(Month.JAN);
        monthList.add(Month.FEB);
        monthList.add(Month.MARCH);
        monthList.add(Month.APRIL);
        monthList.add(Month.MAY);
        monthList.add(Month.JUNE);
        monthList.add(Month.JULY);
        monthList.add(Month.AUGUST);
        monthList.add(Month.SEPT);
        monthList.add(Month.OCT);
        monthList.add(Month.NOV);
        monthList.add(Month.DEC);


        dayList = new ArrayList<>();

        entryList = new ArrayList<>();
    }

    @Test
    public void testConstructor() {
        assertEquals(12, testLog.getRunningLog().size());

        assertEquals(testLog.getJan(), testLog.selectMonth(Month.JAN));
        assertEquals(testLog.getFeb(), testLog.selectMonth(Month.FEB));
        assertEquals(testLog.getMarch(), testLog.selectMonth(Month.MARCH));
        assertEquals(testLog.getApril(), testLog.selectMonth(Month.APRIL));
        assertEquals(testLog.getMay(), testLog.selectMonth(Month.MAY));
        assertEquals(testLog.getJune(), testLog.selectMonth(Month.JUNE));
        assertEquals(testLog.getJuly(), testLog.selectMonth(Month.JULY));
        assertEquals(testLog.getAugust(), testLog.selectMonth(Month.AUGUST));
        assertEquals(testLog.getSeptember(), testLog.selectMonth(Month.SEPT));
        assertEquals(testLog.getOctober(), testLog.selectMonth(Month.OCT));
        assertEquals(testLog.getNovember(), testLog.selectMonth(Month.NOV));
        assertEquals(testLog.getDecember(), testLog.selectMonth(Month.DEC));
    }

    @Test
    void testAddEntry() {
        assertEquals(0, testLog.getJan().size());
        addEntries();
        assertEquals(4, testLog.getJan().size());
    }

    @Test
    void testAddDuplicateEntry() {
        addEntries();

        // exact match
        try {
            testLog.addEntry(janEntryDuplicate);
        } catch (DuplicateEntryException dee) {
            System.out.println("duplicate entry found");
        }

        // same year different day
        try {
            testLog.addEntry(janEntryDupYear);
        } catch (DuplicateEntryException dee) {
            fail();
        }

        // same day different year
        try {
            testLog.addEntry(janEntryDupDay);
        } catch (DuplicateEntryException dee) {
            fail();
        }


    }

    @Test
    void testTotalMonthlyDistance() {
        addEntries();
        assertEquals(51, testLog.totalMonthlyDistance(Month.JAN));

        //no entry added for feb
        assertEquals(0, testLog.totalMonthlyDistance(Month.FEB));

        //add one entry to feb
        try {
            testLog.addEntry(febEntryFirst);
            assertEquals(10, testLog.totalMonthlyDistance(Month.FEB));
        } catch (DuplicateEntryException dee) {
            fail();
        }

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
        createDates();
        createEntries();
        for (Entry e : entryList) {
            try {
                testLog.addEntry(e);
            } catch (DuplicateEntryException dee) {
                fail();
            }
        }

        assertEquals(testLog.getJan(), testLog.selectMonth(Month.JAN));
        assertEquals(testLog.getFeb(), testLog.selectMonth(Month.FEB));
        assertEquals(testLog.getMarch(), testLog.selectMonth(Month.MARCH));
        assertEquals(testLog.getApril(), testLog.selectMonth(Month.APRIL));
        assertEquals(testLog.getMay(), testLog.selectMonth(Month.MAY));
        assertEquals(testLog.getJune(), testLog.selectMonth(Month.JUNE));
        assertEquals(testLog.getJuly(), testLog.selectMonth(Month.JULY));
        assertEquals(testLog.getAugust(), testLog.selectMonth(Month.AUGUST));
        assertEquals(testLog.getSeptember(), testLog.selectMonth(Month.SEPT));
        assertEquals(testLog.getOctober(), testLog.selectMonth(Month.OCT));
        assertEquals(testLog.getNovember(), testLog.selectMonth(Month.NOV));
        assertEquals(testLog.getDecember(), testLog.selectMonth(Month.DEC));
    }


    @Test
    public void addEntries() {
        try {
            testLog.addEntry(janEntry);
            testLog.addEntry(janEntrySecond);
            testLog.addEntry(janEntryThird);
            testLog.addEntry(janEntryFourth);
        } catch (DuplicateEntryException dee) {
            fail();
        }

    }

    public void createDates() {
        for (Month m : monthList) {
            Date day = new Date(1, m, 2024);
            dayList.add(day);
        }
    }

    public void createEntries() {
        for (Date d : dayList) {
            Entry entry = new Entry(d, 10, 60, 150);
            entryList.add(entry);
        }
    }

    @Test
    public void testDatesAndEntries() {
        createDates();
        assertEquals(12, dayList.size());
        createEntries();
        assertEquals(12, entryList.size());
    }

}