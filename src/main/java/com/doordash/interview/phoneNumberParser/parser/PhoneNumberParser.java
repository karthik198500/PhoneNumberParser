package com.doordash.interview.phoneNumberParser.parser;

import com.doordash.interview.phoneNumberParser.model.PhoneInfo;
import com.doordash.interview.phoneNumberParser.model.PhoneInfoResult;
import com.doordash.interview.phoneNumberParser.request.RawPhoneData;

import java.util.List;

public interface PhoneNumberParser {
    PhoneInfoResult parseRawInfo(RawPhoneData rawPhoneNumberInfo);
}
