package com.doordash.interview.phoneNumberParser.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RawPhoneData {

    @JsonProperty("raw_phone_numbers")
    @NotNull
    @NotBlank
    private String rawPhoneNumbers;


    public String getRawPhoneNumbers() {
        return rawPhoneNumbers;
    }

    public void setRawPhoneNumbers(String rawPhoneNumbers) {
        this.rawPhoneNumbers = rawPhoneNumbers;
    }
}
