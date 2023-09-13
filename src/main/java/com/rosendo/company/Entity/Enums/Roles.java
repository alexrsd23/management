package com.rosendo.company.Entity.Enums;

public enum Roles {

    ADMIN("Admin"),
    SALES("Sales"),
    MANAGER("Manager");

    private final String displayValue;

    public String getDisplayValue() {
        return displayValue;
    }

    Roles(String displayValue) {
        this.displayValue = displayValue;
    }


}