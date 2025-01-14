package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DateTest {
    private Date testDate;
    private Date testDateTwo;

    @BeforeEach
    void setUp() {
        testDate = new Date(14, Month.JUN, 1999);
        testDateTwo = new Date(4, Month.MAR, 2000);
    }

    @Test
    void testConstructor() {
        assertEquals(14, testDate.getDay());
        assertEquals(Month.JUN, testDate.getMonth());
        assertEquals(1999, testDate.getYear());
    }

    @Test
    void testToString() {
        assertEquals("14/JUN/1999", testDate.toString());
        assertEquals("04/MAR/2000", testDateTwo.toString());

    }


}
