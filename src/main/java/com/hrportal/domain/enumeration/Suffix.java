package com.hrportal.domain.enumeration;

/**
 * The Suffix enumeration.
 */
public enum Suffix {
    Jr("Jr"),
    Sr("Sr");

    private String value;

    Suffix(String value) {
        this.value = value;
    }

    public String value(){
        return this.value;
    }
}
