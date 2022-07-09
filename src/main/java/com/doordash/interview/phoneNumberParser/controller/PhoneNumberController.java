package com.doordash.interview.phoneNumberParser.controller;

import com.doordash.interview.phoneNumberParser.model.PhoneInfo;
import com.doordash.interview.phoneNumberParser.model.PhoneInfoResult;
import com.doordash.interview.phoneNumberParser.model.PhoneNumberId;
import com.doordash.interview.phoneNumberParser.parser.GenericPhoneNumberParserImpl;
import com.doordash.interview.phoneNumberParser.parser.PhoneNumberParser;
import com.doordash.interview.phoneNumberParser.repository.PhoneNumberRepository;
import com.doordash.interview.phoneNumberParser.request.RawPhoneData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.doordash.interview.phoneNumberParser.util.Constants.*;

@RestController
@Validated
@Api(tags = SWAGGER_TAGS, value = SWAGGER_PHONE_PARSER_INFO)
@RequestMapping(API_VERSION_PHONE_NUMBER_PARSER)
@Log4j2
public class PhoneNumberController {

    private final PhoneNumberRepository phoneNumberRepository;

    public PhoneNumberController(PhoneNumberRepository phoneNumberRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
    }

    @ApiOperation(value = "Phone Number Parser", response = String.class)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PhoneInfoResult> addPhoneNumbers(
            @RequestBody
            @Validated RawPhoneData rawPhoneNumberInfo) {
        log.info("This is a post request");
        PhoneNumberParser phoneNumberParser = new GenericPhoneNumberParserImpl();
        PhoneInfoResult phoneInfoResult = phoneNumberParser.parseRawInfo(rawPhoneNumberInfo);
        if(null!= phoneInfoResult && null!= phoneInfoResult.getResults()){
            List<PhoneInfo> results = phoneInfoResult.getResults();
            for (PhoneInfo phoneInfo: results) {
                Optional<PhoneInfo> optionalPhoneInfo = phoneNumberRepository.findById(new PhoneNumberId(phoneInfo.getPhoneNumber(), phoneInfo.getPhoneNumberType()));
                if(optionalPhoneInfo.isPresent()){
                    phoneInfo.setOccurrences(optionalPhoneInfo.get().getOccurrences()+1);
                    phoneNumberRepository.save(phoneInfo);
                    phoneNumberRepository.save(phoneInfo);
                }else{
                    phoneInfo.setOccurrences(1);
                    phoneNumberRepository.save(phoneInfo);
                }
            }
        }
        return  ResponseEntity.ok(phoneInfoResult);
    }

    @ApiOperation(value = "Phone Number Parser", response = String.class)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> homePage() {
        log.info("This is sample message");
        return  ResponseEntity.ok("response");
    }
}