package com.doordash.interview.phoneNumberParser.parser;

import com.doordash.interview.phoneNumberParser.controller.PhoneNumberController;
import com.doordash.interview.phoneNumberParser.dto.PhoneInfoResult;
import com.doordash.interview.phoneNumberParser.persistence.model.PhoneEntity;
import com.doordash.interview.phoneNumberParser.request.RawPhoneData;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GenericPhoneNumberParserImpl implements PhoneNumberParser {

    private static final Logger logger = LoggerFactory.getLogger(GenericPhoneNumberParserImpl.class);

    @SneakyThrows
    @Override
    public List<PhoneEntity> parseRawInfo(RawPhoneData rawPhoneNumberInfo) {
        String rawPhoneNumbers = rawPhoneNumberInfo.getRawPhoneNumbers();
        return parse(rawPhoneNumbers);
    }

    private List<PhoneEntity> parse(String rawPhoneNumbers) {
        Stack<Character> stack = new Stack();
        List<PhoneEntity> results = new ArrayList<>();
        PhoneEntity phoneEntity = null;
        try {
            for (int i = 0; i < rawPhoneNumbers.length(); ++i) {
                char c = rawPhoneNumbers.charAt(i);
                if (c == '(') {
                    StringBuilder str = new StringBuilder();
                    while (!stack.isEmpty() &&
                            stack.peek() != '(') {
                        str.append(stack.pop());
                    }
                    if (null != str  && str.toString().length() >0) {
                        phoneEntity.setPhoneNumber(str.reverse().toString());
                        logger.debug("Adding phone number "+str.toString());
                        results.add(phoneEntity);
                        phoneEntity = null;
                    }
                    //logger.debug("Push "+ c);
                    stack.push(c);
                } else if (c == ')') {
                    phoneEntity = new PhoneEntity();
                    //  If the scanned character is an ')', pop and output from the stack  until an '(' is encountered.
                    StringBuilder str = new StringBuilder();
                    while (!stack.isEmpty() &&
                            stack.peek() != '(') {
                        str.append(stack.pop());
                    }
                    logger.debug("Adding phone type "+str.reverse().toString());
                    phoneEntity.setPhoneNumberType(str.toString());
                    stack.pop();
                } else if (c == ' ') {
                    continue;
                } else {
                    //logger.debug("Push "+ c);
                    stack.push(c);
                }
            }
            StringBuilder str = new StringBuilder();
            while (!stack.isEmpty()){
                str.append(stack.pop());
            }
            phoneEntity.setPhoneNumber(str.reverse().toString());
            logger.debug("Adding phone number "+str.toString());
            results.add(phoneEntity);
        } catch (Exception e) {
            throw new RuntimeException("Error while parsing the Input. Check the input "+e.getMessage(),e);
        }
        return results;
    }
}
