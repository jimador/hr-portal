package com.hrportal.domain;

import com.hrportal.domain.enumeration.LaptopType;

/**
 * Created by james on 3/2/2016.
 */
public class Laptop {
    private LaptopType type;
    private boolean isLoner = false;
    private int loanTime = 0;

    public LaptopType getType() {
        return type;
    }

    public void setType(LaptopType type) {
        this.type = type;
    }

    public boolean isLoner() {
        return isLoner;
    }

    public void setLoner(boolean loner) {
        isLoner = loner;
    }

    public int getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(int loanTime) {
        this.loanTime = loanTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Laptop laptop = (Laptop) o;

        if (isLoner != laptop.isLoner) return false;
        if (loanTime != laptop.loanTime) return false;
        return type == laptop.type;

    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + (isLoner ? 1 : 0);
        result = 31 * result + loanTime;
        return result;
    }

    @Override
    public String toString() {
        return "Laptop{" +
            "type=" + type +
            ", isLoner=" + isLoner +
            ", loanTime=" + loanTime +
            '}';
    }
}

