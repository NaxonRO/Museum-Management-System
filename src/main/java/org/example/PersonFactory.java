package org.example;

// Design pattern Factory Method pentru crearea obiectelor de tip Person
public class PersonFactory {
    public Person createPerson(String tip, String surname, String name, String role, int age, String email, int studyYear, String school, String command) {
        // Verificare daca tipul este "profesor" sau "student"
        tip = tip.toLowerCase();
        switch (tip) {
            case "profesor":
                // Creare obiect de tip Professor
                Professor professor = new Professor(surname, name, role);
                // Setare atributele obiectului Professor
                professor.setAge(age);
                professor.setEmail(email);
                professor.setExperience(studyYear);
                professor.setSchool(school);
                return professor;
            case "student":
                // Creare obiect de tip Student
                Student student = new Student(surname, name, "vizitator");
                // Setare atributele obiectului Student
                student.setAge(age);
                student.setEmail(email);
                student.setStudyYear(studyYear);
                student.setSchool(school);
                return student;
            default:
                // Creare obiect de tip Person
                return new Person(surname, name, role);
        }
    }
}
