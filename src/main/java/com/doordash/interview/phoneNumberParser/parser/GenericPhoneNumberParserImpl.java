package com.doordash.interview.phoneNumberParser.parser;

import com.doordash.interview.phoneNumberParser.model.PhoneInfo;
import com.doordash.interview.phoneNumberParser.model.PhoneInfoResult;
import com.doordash.interview.phoneNumberParser.request.RawPhoneData;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GenericPhoneNumberParserImpl implements PhoneNumberParser {
    @SneakyThrows
    @Override
    public PhoneInfoResult parseRawInfo(RawPhoneData rawPhoneNumberInfo) {
        String rawPhoneNumbers = rawPhoneNumberInfo.getRawPhoneNumbers();
        System.out.println("result");
        return parse(rawPhoneNumbers);

    }

    private PhoneInfoResult parse(String rawPhoneNumbers) {
        Stack<Character> stack = new Stack();
        PhoneInfoResult phoneInfoResult = new PhoneInfoResult();
        List<PhoneInfo> results = new ArrayList<>();
        PhoneInfo phoneInfo = null;
        for (int i = 0; i < rawPhoneNumbers.length(); ++i) {
            char c = rawPhoneNumbers.charAt(i);
            if (c == '(') {
                StringBuilder str = new StringBuilder();
                while (!stack.isEmpty() &&
                        stack.peek() != '(') {
                    str.append(stack.pop());
                }
                if (null != str  && str.toString().length() >0) {
                    phoneInfo.setPhoneNumber(str.reverse().toString());
                    System.out.println("Adding phone number "+str.toString());
                    results.add(phoneInfo);
                    phoneInfo = null;
                }
                //System.out.println("Push "+ c);
                stack.push(c);
            } else if (c == ')') {
                phoneInfo = new PhoneInfo();
                //  If the scanned character is an ')', pop and output from the stack  until an '(' is encountered.
                StringBuilder str = new StringBuilder();
                while (!stack.isEmpty() &&
                        stack.peek() != '(') {
                    str.append(stack.pop());
                }
                System.out.println("Adding phone type "+str.reverse().toString());
                phoneInfo.setPhoneNumberType(str.toString());
                stack.pop();
            } else if (c == ' ') {
                continue;
            } else {
                //System.out.println("Push "+ c);
                stack.push(c);
            }
        }
        StringBuilder str = new StringBuilder();
        while (!stack.isEmpty()){
            str.append(stack.pop());
        }
        phoneInfo.setPhoneNumber(str.reverse().toString());
        System.out.println("Adding phone number "+str.toString());
        results.add(phoneInfo);
        phoneInfoResult.setResults(results);
        return phoneInfoResult;
    }
}
