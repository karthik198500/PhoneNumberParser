package com.doordash.interview.phoneNumberParser.parser;

import com.doordash.interview.phoneNumberParser.dto.PhoneInfoResult;
import com.doordash.interview.phoneNumberParser.persistence.model.PhoneEntity;
import com.doordash.interview.phoneNumberParser.request.RawPhoneData;

import java.util.List;

public interface PhoneNumberParser {
    List<PhoneEntity> parseRawInfo(RawPhoneData rawPhoneNumberInfo);
}
