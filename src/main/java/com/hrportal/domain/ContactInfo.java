package com.hrportal.domain;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by james on 2/29/2016.
 */
public class ContactInfo {
    @Field("phone_number")
    PhoneNumber phoneNumber;
    @Field("address")
    Address address;
    @Field("email")
    Email email;

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactInfo that = (ContactInfo) o;

        if (!phoneNumber.equals(that.phoneNumber)) return false;
        if (!address.equals(that.address)) return false;
        return email.equals(that.email);

    }

    @Override
    public int hashCode() {
        int result = phoneNumber.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ContactInfo{" +
            "phoneNumber=" + phoneNumber +
            ", address=" + address +
            ", email=" + email +
            '}';
    }
}
