package com.hrportal.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by james on 3/2/2016.
 */
public class Phone {
    @NotNull
    private PhoneNumber phoneNumber;

    @NotNull
    private String macAddress;

    @NotNull
    @Pattern(regexp = "\\d{4}")
    private String extension;

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Phone phone = (Phone) o;

        if (!phoneNumber.equals(phone.phoneNumber)) return false;
        if (!macAddress.equals(phone.macAddress)) return false;
        return extension != null ? extension.equals(phone.extension) : phone.extension == null;

    }

    @Override
    public int hashCode() {
        int result = phoneNumber.hashCode();
        result = 31 * result + macAddress.hashCode();
        result = 31 * result + (extension != null ? extension.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Phone{" +
            "phoneNumber=" + phoneNumber +
            ", macAddress='" + macAddress + '\'' +
            ", extension='" + extension + '\'' +
            '}';
    }
}
