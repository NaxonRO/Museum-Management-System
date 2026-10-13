package org.example;

import java.io.PrintWriter;
import java.util.List;

public class Group {
    private List<Person> members;
    private Professor guide;
    private Integer museumCode;
    private String timetable;
    private Museum museum;

    // Constructor grupului
    public Group(List<Person> members, Professor guide, String timetable, Integer museumCode, Museum museum) {
        this.members = members;
        this.timetable = timetable;
        this.museumCode = museumCode;
        this.guide = guide;
        if(museum != null) {
            this.museum = museum;
            museum.attachGroup(this);
        }
    }

    // Adaugarea unui ghid in grup
    public void addGuide(Person person, PrintWriter printWriter, Museum museum) throws GuideExistsException, GuideTypeException {
        // Verificam daca persoana este de tip profesor, altfel aruncam exceptie
        if (!(person instanceof Professor)) {
            throw new GuideTypeException(String.format(
                    "%d ## %s ## GuideTypeException: Guide must be a professor. ## (new guide: %s)",
                    this.museumCode, this.timetable, person.toString()
            ));
        }

        // Verificam daca ghidul exista deja in grup si daca exista, aruncam exceptie
        if (this.guide != null) {
            throw new GuideExistsException(String.format(
                    "%d ## %s ## GuideExistsException: Guide already exists. ## (new guide: %s)",
                    this.museumCode, this.timetable, this.guide.toString()
            ));
        }

        // Setam ghidul grupului
        this.guide = (Professor) person;
        printWriter.printf("%d ## %s ## new guide: %s%n", this.museumCode, this.timetable, person.toString());
    }

    // Adaugarea unui membru in grup
    public void addMember(Person member, PrintWriter printWriter) throws GroupThresholdException {
        // Verificam daca grupul are deja 10 membri si daca da, aruncam exceptie
        if(this.members.size() >= 10) {
            throw new GroupThresholdException(String.format(
                    "%d ## %s ## GroupThresholdException: Group cannot have more than 10 members. ## (new member: %s)",
                    this.museumCode, this.timetable, member.toString()
            ));
        }

        // Adaugam membrul in grup
        this.members.add(member);
        printWriter.printf("%d ## %s ## new member: %s%n", this.museumCode, this.timetable, member.toString());
    }

    // Cautarea unui membru in grup
    public void findMember(Person member, PrintWriter printWriter) {
        // Cautam membrul in grup si afisam, daca a fost gasit, datele acestuia
        for(Person person : this.members) {
            if(person.getName().equals(member.getName()) && person.getSurname().equals(member.getSurname())) {
                printWriter.printf("%d ## %s ## member found: %s%n", this.museumCode, this.timetable, member.toString());
                return;
            }
        }
        // Daca membrul nu a fost gasit, afisam un mesaj corespunzator
        printWriter.printf("%d ## %s ## member not exists: %s%n", this.museumCode, this.timetable, member.toString());
    }

    // Stergerea unui membru din grup
    public void removeMember(Person member, PrintWriter printWriter) throws PersonNotExistsException {
        // Cautam membrul in grup si daca il gasim, il stergem
        for (Person person : this.members) {
            if (person.getName().equals(member.getName()) && person.getSurname().equals(member.getSurname())) {
                this.members.remove(person);
                printWriter.printf("%d ## %s ## removed member: %s%n", this.museumCode, this.timetable, member.toString());
                return;
            }
        }
        // Daca membrul nu a fost gasit, aruncam exceptie
        throw new PersonNotExistsException(String.format(
                "%d ## %s ## PersonNotExistsException: Person was not found in the group. ## (%s)",
                this.museumCode, this.timetable, member.toString()
        ));
    }

    // Cautarea unui ghid in grup
    public void findGuide(Person guide, PrintWriter printWriter) throws GuideTypeException {
        // Verificam daca ghidul este de tip profesor, altfel aruncam exceptie
        if (guide instanceof Professor) {
            if (this.getGuide() != null && this.getGuide().getName().equals(guide.getName())) {
                printWriter.println(museumCode + " ## " + timetable + " ## guide found: " + guide);
            } else {
                // Cautam ghidul in grup si afisam, daca a fost gasit, datele acestuia
                for(Person person : this.members) {
                    if(person.getName().equals(guide.getName()) && person.getSurname().equals(guide.getSurname())) {
                        printWriter.println(museumCode + " ## " + timetable + " ## guide not exists: " + person);
                        return;
                    }
                }
                // Daca ghidul nu a fost gasit, afisam un mesaj de eroare
                printWriter.println(museumCode + " ## " + timetable + " ## guide not exists: " + guide);
            }
        } else {
            // Daca ghidul nu este de tip profesor, aruncam exceptie
            throw new GuideTypeException(museumCode + " ## " + timetable +
                    " ## Guide must be a professor. ## (new guide: " + guide.toString());
        }
    }

    // Stergerea ghidului din grup
    public void resetGuide() {
        this.guide = null;
    }

    // Returnarea datelor despre grup
    public Integer getMuseumCode() {
        return this.museumCode;
    }

    public String getTimetable() {
        return this.timetable;
    }

    public Professor getGuide() {
        return this.guide;
    }

    // Metoda de afisare pentru ca s-a notificat un grup
    public void update(String message, PrintWriter printWriter) {
        printWriter.printf("To: %s ## Message: %s (%d) %s%n", this.getGuide().getEmail(), museum.getName(), museum.getCode(), message);
    }
}

// Clasele de exceptii
class GroupNotExistsException extends Exception {
    public GroupNotExistsException(String message) {
        super(message);
    }
}

class GroupThresholdException extends Exception {
    public GroupThresholdException(String message) {
        super(message);
    }
}

class GuideExistsException extends Exception {
    public GuideExistsException(String message) {
        super(message);
    }
}

class GuideTypeException extends Exception {
    public GuideTypeException(String message) {
        super(message);
    }
}

class PersonNotExistsException extends Exception {
    public PersonNotExistsException(String message) {
        super(message);
    }
}
