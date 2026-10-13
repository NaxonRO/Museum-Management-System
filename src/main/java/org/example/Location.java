package org.example;

public class Location {
    private String county;
    private Integer sirutaCode;

    private String localCity;
    private String adminUnit;
    private String address;
    private Integer latitude;
    private Integer longitude;

    // Constructor locatie
    private Location(Builder builder) {
        this.county = builder.county;
        this.sirutaCode = builder.sirutaCode;
        this.localCity = builder.localCity;
        this.adminUnit = builder.adminUnit;
        this.address = builder.address;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
    }

    // Desing pattern folosit pentru a construi obiectul Location (Builder)
    public static class Builder {
        private String county;
        private Integer sirutaCode;

        private String localCity;
        private String adminUnit;
        private String address;
        private Integer latitude;
        private Integer longitude;

        // Constructorul din Builder
        public Builder(String county, Integer sirutaCode) {
            this.county = county;
            this.sirutaCode = sirutaCode;
        }

        // Metodele din Builder
        public Builder localCity(String localCity) {
            this.localCity = localCity;
            return this;
        }

        public Builder adminUnit(String adminUnit) {
            this.adminUnit = adminUnit;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder latitude(Integer latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder longitude(Integer longitude) {
            this.longitude = longitude;
            return this;
        }

        public Location build() {
            return new Location(this);
        }
    }

    // Metodele clasei Location
    public String getCounty() {
        return county;
    }

    public Integer getSirutaCode() {
        return sirutaCode;
    }

    public String getLocalCity() {
        return localCity;
    }

    public String getAdminUnit() {
        return adminUnit;
    }

    public String getAddress() {
        return address;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public Integer getLongitude() {
        return longitude;
    }
}
