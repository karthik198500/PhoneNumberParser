package com.doordash.interview.phoneNumberParser.persistence.repository;

import com.doordash.interview.phoneNumberParser.persistence.model.PhoneEntity;
import com.doordash.interview.phoneNumberParser.persistence.model.PhoneNumberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PhoneNumberRepository extends CrudRepository<PhoneEntity, PhoneNumberId> {
}
