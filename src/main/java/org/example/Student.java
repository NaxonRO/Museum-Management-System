package org.example;

public class Student extends Person {
    private String school;
    private int studyYear;

    // Constructor student
    public Student(String surname, String name, String role) {
        super(surname, name, role);
    }

    // Metodele clasei student
    public void setSchool(String school) {
        this.school = school;
    }

    public void setStudyYear(int studyYear) {
        this.studyYear = studyYear;
    }

    public String getSchool() {
        return school;
    }

    public int getStudyYear() {
        return studyYear;
    }

    // Metoda toString specifica clasei student
    public String toString() {
        return super.toString() +
                ", school=" + this.school +
                ", studyYear=" + this.studyYear;
    }
}
