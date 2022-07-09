package com.doordash.interview.phoneNumberParser.repository;

import com.doordash.interview.phoneNumberParser.model.PhoneInfo;
import com.doordash.interview.phoneNumberParser.model.PhoneNumberId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneNumberRepository extends JpaRepository<PhoneInfo, PhoneNumberId> {
}
