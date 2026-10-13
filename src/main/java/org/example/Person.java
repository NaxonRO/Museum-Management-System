package org.example;

public class Person {
    private String surname;
    private String name;
    private String role;
    private int age;
    private String email;

    // Constructorul clasei
    public Person(String surname, String name, String role) {
        this.surname = surname;
        this.name = name;
        this.role = role;
    }

    // Metodele clasei Person
    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    // Metoda toString
    public String toString() {
        return "surname=" + this.surname +
                ", name=" + this.name +
                ", role=" + this.role +
                ", age=" + this.age +
                ", email=" + this.email;
    }
}
