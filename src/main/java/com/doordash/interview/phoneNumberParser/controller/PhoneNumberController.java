package com.doordash.interview.phoneNumberParser.controller;

import com.doordash.interview.phoneNumberParser.dto.PhoneInfoResult;
import com.doordash.interview.phoneNumberParser.dto.PhoneNumberDTO;
import com.doordash.interview.phoneNumberParser.dto.PhoneNumberDTOMapper;
import com.doordash.interview.phoneNumberParser.parser.GenericPhoneNumberParserImpl;
import com.doordash.interview.phoneNumberParser.parser.PhoneNumberParser;
import com.doordash.interview.phoneNumberParser.persistence.model.PhoneEntity;
import com.doordash.interview.phoneNumberParser.persistence.model.PhoneNumberId;
import com.doordash.interview.phoneNumberParser.persistence.repository.PhoneNumberRepository;
import com.doordash.interview.phoneNumberParser.request.RawPhoneData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    private static final Logger logger = LoggerFactory.getLogger(PhoneNumberController.class);

    private final PhoneNumberRepository phoneNumberRepository;
    private final PhoneNumberDTOMapper phoneNumberDTOMapper;

    public PhoneNumberController(PhoneNumberRepository phoneNumberRepository, PhoneNumberDTOMapper phoneNumberDTOMapper) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.phoneNumberDTOMapper = phoneNumberDTOMapper;
    }


    @ApiOperation(value = "Add Phone Number", response = String.class)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PhoneInfoResult> addPhoneNumbers(
            @RequestBody
            @Validated RawPhoneData rawPhoneNumberInfo) {
        logger.info("Phone Parser API Invoked "+rawPhoneNumberInfo);
        PhoneNumberParser phoneNumberParser = new GenericPhoneNumberParserImpl();
        List<PhoneEntity> phoneEntities = phoneNumberParser.parseRawInfo(rawPhoneNumberInfo);
        if(null!= phoneEntities && !phoneEntities.isEmpty()){
            for (PhoneEntity phoneEntity : phoneEntities) {
                    Optional<PhoneEntity> optionalPhoneInfo = phoneNumberRepository.findById(new PhoneNumberId(phoneEntity.getPhoneNumber(), phoneEntity.getPhoneNumberType()));
                if(optionalPhoneInfo.isPresent()){
                    phoneEntity.setOccurrences(optionalPhoneInfo.get().getOccurrences()+1);
                    phoneNumberRepository.save(phoneEntity);
                }else{
                    phoneEntity.setOccurrences(1);
                    phoneNumberRepository.save(phoneEntity);
                }
            }
        }
        List<PhoneNumberDTO> phoneNumberDTOList = new ArrayList<>();
        for(PhoneEntity phoneEntity:phoneEntities){
            phoneNumberDTOList.add(phoneNumberDTOMapper.convertPhoneEntityToPhoneNumberDTO(phoneEntity));
        }
        PhoneInfoResult phoneInfoResult = new PhoneInfoResult();
        phoneInfoResult.setResults(phoneNumberDTOList);
        logger.info(phoneNumberDTOList.toString());

        return  ResponseEntity.ok(phoneInfoResult);
    }



    @ApiOperation(value = "Phone Number Parser", response = String.class)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> welcomePage() {
        logger.info("Phone Parser Welcome page  ");
        return  ResponseEntity.ok("Welcome to Phone Number Parser");

    }
}