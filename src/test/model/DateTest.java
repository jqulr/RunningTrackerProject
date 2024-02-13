package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DateTest {
    private Date testDate;
    private Date testDateTwo;

    @BeforeEach
    void setUp() {
        testDate = new Date(14, Month.JUNE, 1999);
        testDateTwo = new Date(4, Month.MARCH, 2000);
    }

    @Test
    void testConstructor() {
        assertEquals(14, testDate.getDay());
        assertEquals(Month.JUNE, testDate.getMonth());
        assertEquals(1999, testDate.getYear());
    }

    @Test
    void testToString() {
        assertEquals("14/JUNE/1999", testDate.toString());
        assertEquals("04/MARCH/2000", testDateTwo.toString());

    }

}
