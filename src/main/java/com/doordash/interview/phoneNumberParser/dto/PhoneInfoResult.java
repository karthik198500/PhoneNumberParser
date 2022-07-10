package com.doordash.interview.phoneNumberParser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

public class PhoneInfoResult {

    @JsonProperty("results")
    private List<PhoneNumberDTO> results;


    public List<PhoneNumberDTO> getResults() {
        return results;
    }

    public void setResults(List<PhoneNumberDTO> results) {
        this.results = results;
    }


    @Override
    public String toString() {
        return "PhoneInfoResult{" +
                "results=" + Arrays.toString(results.toArray()) +
                '}';
    }
}
