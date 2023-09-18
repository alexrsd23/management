package com.rosendo.company.Entity.General;

import jakarta.persistence.*;

@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phoneOne;
    private String phoneTwo;
    private String CNPJ;
    private String stateRegistration;
    private String email;
    private String comments;
    private String representative;
    @OneToOne
    @JoinColumn(name = "adress_id")
    private Adress adress;

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneOne() {
        return phoneOne;
    }

    public void setPhoneOne(String phoneOne) {
        this.phoneOne = phoneOne;
    }

    public String getPhoneTwo() {
        return phoneTwo;
    }

    public void setPhoneTwo(String phoneTwo) {
        this.phoneTwo = phoneTwo;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getStateRegistration() {
        return stateRegistration;
    }

    public void setStateRegistration(String stateRegistration) {
        this.stateRegistration = stateRegistration;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRepresentative() {
        return representative;
    }

    public void setRepresentative(String representative) {
        this.representative = representative;
    }

    public Supplier() {
    }

    public Supplier(Long id, String name, String phoneOne, String phoneTwo, String CNPJ, String stateRegistration, String email, String comments, String representative, Adress adress) {
        this.id = id;
        this.name = name;
        this.phoneOne = phoneOne;
        this.phoneTwo = phoneTwo;
        this.CNPJ = CNPJ;
        this.stateRegistration = stateRegistration;
        this.email = email;
        this.comments = comments;
        this.representative = representative;
        this.adress = adress;
    }
}
