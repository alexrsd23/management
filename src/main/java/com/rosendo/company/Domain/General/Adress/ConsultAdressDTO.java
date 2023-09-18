package com.rosendo.company.Domain.General.Adress;

public class ConsultAdressDTO {
    private Long id;
    private String street; //RUA
    private String neighborhood;//BAIRRO
    private Integer number;//NUMERO
    private String postalCode;//CEP
    private String complement;//COMPLEMENTO
    private String state;//ESTADO
    private String country;//PAIS
    private String city;//PAIS

    private String observation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
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

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ConsultAdressDTO() {
    }

    public ConsultAdressDTO(Long id, String street, String neighborhood, Integer number, String postalCode, String complement, String state, String country, String city, String observation) {
        this.id = id;
        this.street = street;
        this.neighborhood = neighborhood;
        this.number = number;
        this.postalCode = postalCode;
        this.complement = complement;
        this.state = state;
        this.country = country;
        this.city = city;
        this.observation = observation;
    }
}
