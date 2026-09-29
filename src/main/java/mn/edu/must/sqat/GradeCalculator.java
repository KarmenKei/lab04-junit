package mn.edu.must.sqat;

public class GradeCalculator {

    public String letterGrade(double score) {
        if (Double.isNaN(score) || score < 0 || score > 100) {
            throw new IllegalArgumentException("Оноо 0-100 хооронд байх ёстой: " + score);
        }
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }

    public double totalScore(double att, double lab, double quiz1, double quiz2, double exam) {
        check("Ирц", att, 10);
        check("Лаб+бие даалт", lab, 40);
        check("Сорил 1", quiz1, 10);
        check("Сорил 2", quiz2, 10);
        check("Шалгалт", exam, 30);
        return att + lab + quiz1 + quiz2 + exam;
    }

    private static void check(String name, double value, double max) {
        if (Double.isNaN(value) || value < 0 || value > max) {
            throw new IllegalArgumentException(name + " 0-" + max + " хооронд байх ёстой: " + value);
        }
    }
}