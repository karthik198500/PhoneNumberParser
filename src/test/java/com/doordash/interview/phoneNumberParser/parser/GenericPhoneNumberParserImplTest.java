package com.doordash.interview.phoneNumberParser.parser;

import com.doordash.interview.phoneNumberParser.persistence.model.PhoneEntity;
import com.doordash.interview.phoneNumberParser.request.RawPhoneData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenericPhoneNumberParserImplTest {

    @Test
    void parseRawInfo() throws Exception{
        RawPhoneData rawPhoneData = new RawPhoneData();
        rawPhoneData.setRawPhoneNumbers("(Home)415-415-4155 (Cell) 415-123-4561 (Business) 115-514-5145");
        GenericPhoneNumberParserImpl genericPhoneNumberParser = new GenericPhoneNumberParserImpl();
        List<PhoneEntity> phoneEntities = genericPhoneNumberParser.parseRawInfo(rawPhoneData);
        Assertions.assertEquals(3,phoneEntities.size());
    }

    @Test
    void parseRawInfoInvalidData() throws Exception{
        RawPhoneData rawPhoneData = new RawPhoneData();
        rawPhoneData.setRawPhoneNumbers("asdfasd (Home)415-415-4155 (Cell) 415-123-4561 (Business) 115-514-5145");
        GenericPhoneNumberParserImpl genericPhoneNumberParser = new GenericPhoneNumberParserImpl();
        Assertions.assertThrows(RuntimeException.class,()->genericPhoneNumberParser.parseRawInfo(rawPhoneData));
    }

    @Test
    void parseRawInfoInvalidDataNoPhoneType() throws Exception{
        /*As discussed in comments and readme, no validation is being handled for the input data. I haven't covered this
        as part of the scope of this exercise.
        * */
        RawPhoneData rawPhoneData = new RawPhoneData();
        rawPhoneData.setRawPhoneNumbers("(Home)415-415-4155 (Cell) 415-123-4561 115-514-5145");
        GenericPhoneNumberParserImpl genericPhoneNumberParser = new GenericPhoneNumberParserImpl();
        List<PhoneEntity> phoneEntities = genericPhoneNumberParser.parseRawInfo(rawPhoneData);
        Assertions.assertEquals(2,phoneEntities.size());

    }
}