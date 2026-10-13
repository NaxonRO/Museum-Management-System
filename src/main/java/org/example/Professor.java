package org.example;

import java.io.PrintWriter;

public class Professor extends Person{
    private int experience;
    private String school;

    // Constructor profesor
    public Professor(String surname, String name, String role) {
        super(surname, name, role);
    }

    // Metodele clasei profesor
    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getExperience() {
        return experience;
    }

    public String getSchool() {
        return school;
    }

    // Metoda toString specifica clasei profesor
    public String toString() {
        return super.toString() +
                ", school=" + this.school +
                ", experience=" + this.experience;
    }
}
