package com.doordash.interview.phoneNumberParser.persistence.model;

import java.io.Serializable;
import java.util.Objects;

public class PhoneNumberId implements Serializable {

    private String phoneNumber;
    private String phoneNumberType;

    public PhoneNumberId() {
    }

    public PhoneNumberId(String phoneNumber, String phoneNumberType) {
        this.phoneNumber = phoneNumber;
        this.phoneNumberType = phoneNumberType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumberId phoneNumberId = (PhoneNumberId) o;
        return phoneNumber.equals(phoneNumberId.phoneNumber) &&
                phoneNumberType.equals(phoneNumberId.phoneNumberType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber, phoneNumberType);
    }
}