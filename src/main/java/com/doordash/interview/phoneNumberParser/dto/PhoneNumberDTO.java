package com.doordash.interview.phoneNumberParser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public class PhoneNumberDTO {

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("phone_type")
    private String phoneNumberType;

    private Integer occurrences;


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumberType() {
        return phoneNumberType;
    }

    public void setPhoneNumberType(String phoneNumberType) {
        this.phoneNumberType = phoneNumberType;
    }

    public Integer getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(Integer occurrences) {
        this.occurrences = occurrences;
    }


    @Override
    public String toString() {
        return "PhoneNumberDTO{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", phoneNumberType='" + phoneNumberType + '\'' +
                ", occurrences=" + occurrences +
                '}';
    }
}
