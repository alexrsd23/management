package com.rosendo.company.Domain.General.Adress;

public class UpdateAdressDTO {

    private String street;
    private String neighborhood;
    private Integer number;
    private String postalCode;
    private String state;
    private String country;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public UpdateAdressDTO(String street, String neighborhood, Integer number, String postalCode, String state, String country) {
        this.street = street;
        this.neighborhood = neighborhood;
        this.number = number;
        this.postalCode = postalCode;
        this.state = state;
        this.country = country;
    }

    public UpdateAdressDTO() {
    }
}
