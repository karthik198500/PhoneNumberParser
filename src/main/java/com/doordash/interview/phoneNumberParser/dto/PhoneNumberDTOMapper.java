package com.doordash.interview.phoneNumberParser.dto;

import com.doordash.interview.phoneNumberParser.persistence.model.PhoneEntity;
import org.mapstruct.*;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
public interface PhoneNumberDTOMapper {

   @Mappings({
            @Mapping(source = "phoneNumber",  target = "phoneNumber", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS),
            @Mapping(source = "phoneNumberType", target = "phoneNumberType", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS),
            @Mapping(source = "occurrences", target = "occurrences")
    })
    PhoneNumberDTO convertPhoneEntityToPhoneNumberDTO(PhoneEntity source);
    PhoneEntity convertPhoneNumberDTOToPhoneEntity(PhoneNumberDTO destination);
}
