package mn.edu.must.sqat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GradeCalculatorTest {

    @Test
    @DisplayName("90 оноо яг A дүн байх ёстой (хязгаарын тохиолдол)")
    void ninetyIsExactlyA() {
        GradeCalculator calc = new GradeCalculator();      // Arrange
        String grade = calc.letterGrade(90.0);             // Act
        assertEquals("A", grade);                          // Assert
    }

    @Test
    @DisplayName("100 оноо A дүн байх ёстой (дээд хязгаар)")
    void hundredIsA() {
        GradeCalculator calc = new GradeCalculator();
        String grade = calc.letterGrade(100.0);
        assertEquals("A", grade);
    }

    @Test
    @DisplayName("0 оноо F дүн байх ёстой (доод хязгаар)")
    void zeroIsF() {
        GradeCalculator calc = new GradeCalculator();
        String grade = calc.letterGrade(0.0);
        assertEquals("F", grade);
    }

    @ParameterizedTest(name = "{0} оноо -> {1}")
    @DisplayName("Ердийн утгууд зөв үсгэн дүнтэй таарна")
    @CsvSource({"95,A", "85,B", "75,C", "65,D", "30,F"})
    void typicalGrades(double score, String expected) {
        GradeCalculator calc = new GradeCalculator();
        String grade = calc.letterGrade(score);
        assertEquals(expected, grade);
    }

    @ParameterizedTest(name = "{0} оноо -> {1}")
    @DisplayName("Дүнгийн хязгаарын утгууд")
    @CsvSource({"100,A", "95,A", "90,A", "89.99,B", "80,B", "79.99,C",
                "70,C", "69.99,D", "60,D", "59.99,F", "0,F"})
    void letterGradeBoundaries(double score, String expected) {
        GradeCalculator calc = new GradeCalculator();
        String grade = calc.letterGrade(score);
        assertEquals(expected, grade);
    }

    @ParameterizedTest(name = "{0} оноо буруу оролт")
    @DisplayName("letterGrade: 0-100-аас гарсан эсвэл NaN оноонд exception шидэх ёстой")
    @ValueSource(doubles = {-1, 101, -0.01, 100.01, Double.NaN})
    void letterGradeRejectsInvalid(double score) {
        GradeCalculator calc = new GradeCalculator();
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(score));
    }

    @Test
    @DisplayName("totalScore: дээд хязгаарын оноонуудаас яг 100 гарна")
    void totalScoreMaxIsHundred() {
        GradeCalculator calc = new GradeCalculator();
        double total = calc.totalScore(10, 40, 10, 10, 30);
        assertEquals(100.0, total, 1e-9);
    }

    @ParameterizedTest(name = "{0},{1},{2},{3},{4} -> {5}")
    @DisplayName("totalScore: зөв нийлбэр")
    @CsvSource({"10,40,10,10,30,100", "0,0,0,0,0,0", "8,35,7,9,25,84"})
    void totalScoreSums(double att, double lab, double q1, double q2, double exam, double expected) {
        GradeCalculator calc = new GradeCalculator();
        double total = calc.totalScore(att, lab, q1, q2, exam);
        assertEquals(expected, total, 1e-9);
    }

    @Test
    @DisplayName("totalScore: сөрөг ирцэд exception шидэх ёстой")
    void totalScoreRejectsNegativeAttendance() {
        GradeCalculator calc = new GradeCalculator();
        assertThrows(IllegalArgumentException.class, () -> calc.totalScore(-5, 20, 5, 5, 15));
    }

    @Test
    @DisplayName("totalScore: лаб 41 (дээд хязгаараас хэтэрсэн) үед exception шидэх ёстой")
    void totalScoreRejectsLabOverMax() {
        GradeCalculator calc = new GradeCalculator();
        assertThrows(IllegalArgumentException.class, () -> calc.totalScore(5, 41, 5, 5, 15));
    }

    @ParameterizedTest(name = "{0},{1},{2},{3},{4} буруу")
    @DisplayName("totalScore: аль ч бүрэлдэхүүн хязгаараас гарвал exception шидэх ёстой")
    @CsvSource({"-5,0,0,0,0", "11,0,0,0,0", "0,-1,0,0,0", "0,41,0,0,0",
                "0,0,11,0,0", "0,0,0,-1,0", "0,0,0,11,0", "0,0,0,0,31"})
    void totalScoreRejectsInvalidComponents(double att, double lab, double q1, double q2, double exam) {
        GradeCalculator calc = new GradeCalculator();
        assertThrows(IllegalArgumentException.class, () -> calc.totalScore(att, lab, q1, q2, exam));
    }
}