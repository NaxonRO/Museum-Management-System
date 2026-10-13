package org.example;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Database {
    private static Database database;
    private Set<Museum> museums;
    private Set<Group> groups;

    // Constructorul clasei Database
    private Database() {
        this.museums = new HashSet<>();
        this.groups = new HashSet<>();
    }

    // Aceasta metoda este folosita pentru a obtine instanta clasei Database (singleton)
    public static Database getInstance() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    // Metoda care returneaza toate muzeele
    public Set<Museum> getMuseums() {
        return this.museums;
    }

    // Metoda care returneaza toate grupurile
    public Set<Group> getGroups() {
        return this.groups;
    }

    // Metoda care adauga un muzeu in baza de date
    public void addMuseum(Museum museum) {
        this.museums.add(museum);
    }

    // Metoda care adauga mai multe muzee in baza de date
    public void addMuseums(Scanner scanner, PrintWriter printWriter) {
        // Citim fiecare linie din fisier
        while (scanner.hasNextLine()) {
            // Citim linia si eliminam spatiile de la inceput si de la sfarsit
            String line = scanner.nextLine().trim();
            // Daca linia este goala, continuam
            if (line.isEmpty()) {
                continue;
            }

            // Impartim linia in functie de caracterul '|'
            String[] parts = line.split("\\|");

            try {
                // Luam comanda din prima parte a liniei
                String command = parts[0].trim();
                // Daca comanda nu este "ADD MUSEUM", afisam un mesaj de eroare
                if (!"ADD MUSEUM".equals(command)) {
                    printWriter.println("Comandă necunoscută: " + line);
                    continue;
                }

                // Luam toate datele despre muzeu si locatie
                // In caz ca avem date care nu sunt strict necesare, le citim sau lasam valoarea null daca nu exista
                long code = Long.parseLong(parts[1].trim());
                String name = parts[2].trim();
                String county = parts[3].trim();
                String localCity = parts.length > 4 && !parts[4].isEmpty() ? parts[4].trim() : null;
                String adminUnit = parts.length > 5 && !parts[5].isEmpty() ? parts[5].trim() : null;
                String address = parts.length > 6 && !parts[6].isEmpty() ? parts[6].trim() : null;
                long postalCode = parts.length > 7 && !parts[7].isEmpty() ? Long.parseLong(parts[7].trim()) : 0;
                String phoneNumber = parts.length > 8 && !parts[8].isEmpty() ? parts[8].trim() : null;
                String fax = parts.length > 9 && !parts[9].isEmpty() ? parts[9].trim() : null;
                int foundingYear = parts.length > 10 && !parts[10].isEmpty() ? Integer.parseInt(parts[10].trim()) : 0;
                String url = parts.length > 11 && !parts[11].isEmpty() ? parts[11].trim() : null;
                String email = parts.length > 12 && !parts[12].isEmpty() ? parts[12].trim() : null;
                String managerName = parts.length > 13 && !parts[13].isEmpty() ? parts[13].trim() : null;
                long supervisorCode = Long.parseLong(parts[14].trim());
                String profile = parts.length > 15 && !parts[15].isEmpty() ? parts[15].trim() : null;
                int sirutaCode = Integer.parseInt(parts[16].trim());
                String category = parts.length > 17 && !parts[17].isEmpty() ? parts[17].trim() : null;

                // La latitudine si longitudine, daca avem, eliminam virgula si spatiile si convertim in double
                double latitude = parts.length > 18 && !parts[18].isEmpty()
                        ? Double.parseDouble(parts[18].replace(",", "").trim()) : 0.0;
                double longitude = parts.length > 19 && !parts[19].isEmpty()
                        ? Double.parseDouble(parts[19].replace(",", "").trim()) : 0.0;

                // Cream un obiect de tip Person pentru manager si il initializam cu datele existente
                Person manager = null;
                if (managerName != null && !managerName.isEmpty()) {
                    String[] managerParts = managerName.split(" ");
                    String surname = managerParts[0];
                    String nickname = managerParts.length > 1 ? managerParts[1] : "";
                    manager = new Person(surname, nickname, "manager");
                }

                // Apelam builder-ul pentru a crea un obiect de tip Location
                Location location = new Location.Builder(county, sirutaCode)
                        .localCity(localCity)
                        .adminUnit(adminUnit)
                        .address(address)
                        .latitude((int) latitude)
                        .longitude((int) longitude)
                        .build();

                // Apelam builder-ul pentru a crea un obiect de tip Museum
                Museum museum = new Museum.Builder(name, code, supervisorCode, location)
                        .manager(manager)
                        .foundingYear(foundingYear)
                        .phoneNumber(phoneNumber)
                        .fax(fax)
                        .email(email)
                        .url(url)
                        .profile(profile)
                        .build();

                // Adaugam muzeul in baza de date si afisam datele despre muzeu
                this.addMuseum(museum);
                printWriter.println(museum.toString());

            } catch (Exception e) {
                // In caz ca apar erori, afisam un mesaj de eroare
                printWriter.println("Exception: Data is broken. ## (" + line + ")");
            }
        }
    }


    // Metoda care adauga un grup in baza de date
    public void addGroup(Group group) {
        this.groups.add(group);
    }

    // Metoda care adauga mai multe grupuri in baza de date
    public void addGroups(Scanner scanner, PrintWriter printWriter) throws GuideExistsException, GuideTypeException, GroupNotExistsException {
        // Cream un obiect de tip PersonFactory
        PersonFactory factory = new PersonFactory();
        // Stergem toate grupurile existente
        this.groups.clear();
        // Citim fiecare linie din fisier
        while (scanner.hasNextLine()) {
            // Citim linia si eliminam spatiile de la inceput si de la sfarsit
            String line = scanner.nextLine().trim();

            // Impartim linia in functie de caracterul '|'
            String[] parts = line.split("\\|");

            // Citim comanda si luam toate datele despre persoana si grup
            String command = parts[0].trim();
            String surname = parts[1].trim();
            String name = parts[2].trim();
            String role = parts[3].trim().toLowerCase();
            int age = Integer.parseInt(parts[4].trim());
            String email = parts[5].trim().isEmpty() ? null : parts[5].trim();
            String school = parts[6].trim();
            int experience = Integer.parseInt(parts[7].trim());
            String codRole = parts[8].trim().toLowerCase();
            int museumCode = Integer.parseInt(parts[9].trim());
            String timetable = parts[10].trim();

            // Cream persoana folosind factory-ul
            Person person = factory.createPerson(role, surname, name, codRole, age, email, experience, school, command);

            // Cautam grupul in baza de date
            Group group = null;
            for (Group g : this.groups) {
                if (g.getMuseumCode() == museumCode && g.getTimetable().equals(timetable)) {
                    group = g;
                    break;
                }
            }

            // Cautam muzeul in baza de date
            Museum museum = null;
            for (Museum m : this.museums) {
                if (m.getCode() == museumCode) {
                    museum = m;
                    break;
                }
            }

            // In functie de comanda, adaugam, cautam sau stergem vizitatorii/ghizii din grup
            switch (command) {
                case "ADD GUIDE":
                    // Daca grupul nu exista, il cream si adaugam ghidul
                    if (group == null) {
                        group = new Group(new ArrayList<>(), null, timetable, museumCode, museum);
                        this.addGroup(group);
                    }
                    // Daca grupul exista, adaugam ghidul
                    // Daca ghidul exista deja sau nu este de tip profesor, afisam un mesaj de eroare
                    try {
                        group.addGuide(person, printWriter, museum);
                    } catch (GuideExistsException | GuideTypeException e) {
                        printWriter.println(e.getMessage());
                    }
                    break;
                case "FIND GUIDE":
                    // Daca grupul nu exista, afisam un mesaj de eroare
                    if (group == null) {
                        printWriter.println(museumCode + " ## " + timetable +
                                " ## GroupNotExistsException: Group does not exist. ## (new guide: " + person + ")");
                        break;
                    }
                    try {
                        // Cautam ghidul in grup
                        group.findGuide(person, printWriter);
                    } catch (GuideTypeException e) {
                        printWriter.println(e.getMessage());
                    }
                    break;
                case "REMOVE GUIDE":
                    // Daca grupul nu exista, afisam un mesaj de eroare
                    if (group == null) {
                        printWriter.println(museumCode + " ## " + timetable +
                                " ## GroupNotExistsException: Group does not exist. ## (removed guide: " + person + ")");
                        break;
                        // Daca ghidul exista, il stergem
                    } else if (group.getGuide() != null && group.getGuide().getName().equals(person.getName())) {
                        // Stergem ghidul din grup
                        group.resetGuide();
                        printWriter.println(museumCode + " ## " + timetable + " ## removed guide: " + person);
                    }
                    break;
                case "ADD MEMBER":
                    // Daca grupul nu exista, afisam un mesaj de eroare
                    if (group == null) {
                        printWriter.println(museumCode + " ## " + timetable +
                                " ## GroupNotExistsException: Group does not exist. ## (new member: " + person + ")");
                        break;
                    }
                    try {
                        // Daca grupul exista, adaugam vizitatorul
                        group.addMember(person, printWriter);
                    } catch (GroupThresholdException e) {
                        printWriter.println(e.getMessage());
                    }
                    break;
                case "FIND MEMBER":
                    // Daca grupul nu exista, afisam un mesaj de eroare
                    if (group == null) {
                        printWriter.println(museumCode + " ## " + timetable +
                                " ## GroupNotExistsException: Group does not exist. ## (new member: " + person + ")");
                        break;
                    } else {
                        // Cautam vizitatorul in grup
                        group.findMember(person, printWriter);
                    }
                    break;
                case "REMOVE MEMBER":
                    // Daca grupul nu exista, afisam un mesaj de eroare
                    if (group == null) {
                        printWriter.println(museumCode + " ## " + timetable +
                                " ## GroupNotExistsException: Group does not exist. ## (removed member: " + person + ")");
                        break;
                    }
                    try {
                        // Daca grupul exista si vizitatorul este in grup, il stergem
                        group.removeMember(person, printWriter);
                    } catch (PersonNotExistsException e) {
                        printWriter.println(e.getMessage());
                    }
                    break;
            }
        }
        scanner.close();
    }

    // Metoda care adauga evenimente in baza de date
    public void eventsAdd(Scanner scanner, PrintWriter printWriter) {
        // Citim fiecare linie din fisier
        while (scanner.hasNextLine()) {
            // Citim linia si eliminam spatiile de la inceput si de la sfarsit
            String line = scanner.nextLine().trim();

            // Impartim linia in functie de caracterul '|'
            String[] parts = line.split("\\|");

            // Luam comanda din prima parte a liniei si datele despre muzeu si eveniment
            String command = parts[0].trim();
            int museumCode = Integer.parseInt(parts[1].trim());
            String messages = parts[2].trim();

            // Cautam muzeul in baza de date
            Museum museum = null;
            for (Museum m : this.museums) {
                if (m.getCode() == museumCode) {
                    museum = m;
                    break;
                }
            }

            // Notificam observatorii despre eveniment
            if(museum != null) {
                museum.addEvent(messages, printWriter);
            }
        }
    }
}

