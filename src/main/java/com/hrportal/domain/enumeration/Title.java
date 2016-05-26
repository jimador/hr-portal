package com.hrportal.domain.enumeration;

/**
 * The Title enumeration.
 */
public enum Title {
    Blank(""),
    Dr("Dr");

    Title(String value) {
        this.value = value;
    }

    private String value;

    public String value(){
        return this.value;
    }
}
