package kz.toleukhan.studentapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class StudentNameTest {
    @Test
    void studentNameIsCorrect() {
        String author = "Toleukhan Aidos";
        assertEquals("Wrong Student", author, "Controlled failure by Toleukhan Aidos");
    }
}
