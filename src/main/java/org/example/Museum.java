package org.example;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Museum {
    private String name;
    private long code;
    private long supervisorCode;
    private Location location;
    private List<Group> groups;

    private Person manager;
    private Integer foundingYear;
    private String phoneNumber;
    private String fax;
    private String email;
    private String url;
    private String profile;

    // Constructor muzeu
    private Museum(Builder builder) {
        this.name = builder.name;
        this.code = builder.code;
        this.supervisorCode = builder.supervisorCode;
        this.location = builder.location;
        this.manager = builder.manager;
        this.foundingYear = builder.foundingYear;
        this.phoneNumber = builder.phoneNumber;
        this.fax = builder.fax;
        this.email = builder.email;
        this.url = builder.url;
        this.profile = builder.profile;
        this.groups = new ArrayList<>();
    }

    // Desing pattern folosit pentru a construi obiectul Museum (Builder)
    public static class Builder {
        private String name;
        private long code;
        private long supervisorCode;
        private Location location;

        private Person manager;
        private Integer foundingYear;
        private String phoneNumber;
        private String fax;
        private String email;
        private String url;
        private String profile;

        // Constructorul din Builder
        public Builder(String name, long code, long supervisorCode, Location location) {
            this.name = name;
            this.code = code;
            this.supervisorCode = supervisorCode;
            this.location = location;
        }

        // Metodele din Builder
        public Builder manager(Person manager) {
            this.manager = manager;
            return this;
        }

        public Builder foundingYear(Integer foundingYear) {
            this.foundingYear = foundingYear;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder fax(String fax) {
            this.fax = fax;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder profile(String profile) {
            this.profile = profile;
            return this;
        }

        public Museum build() {
            return new Museum(this);
        }
    }

    // Metodele clasei Museum
    public String getName() {
        return name;
    }

    public long getCode() {
        return code;
    }

    public long getSupervisorCode() {
        return supervisorCode;
    }

    public Location getLocation() {
        return location;
    }

    public Person getManager() {
        return manager;
    }

    public Integer getFoundingYear() {
        return foundingYear;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFax() {
        return fax;
    }

    public String getEmail() {
        return email;
    }

    // Metoda de atasare a unui grup la muzeu
    public void attachGroup(Group group) {
        this.groups.add(group);
    }

    // Metoda de notificare a grupurilor
    public void notifyGroups(String message, PrintWriter printWriter) {
        for (int i = this.groups.size() - 1; i >= 0; i--) {
            this.groups.get(i).update(message, printWriter);
        }
    }

    // Metoda de adaugare a unui eveniment
    public void addEvent(String message, PrintWriter printWriter) {
        notifyGroups(message, printWriter);
    }

    // Metoda de afisare a datelor muzeului
    public String toString() {
        return this.code + ": " + this.name;
    }

}
