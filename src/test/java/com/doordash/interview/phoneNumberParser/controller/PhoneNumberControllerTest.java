package com.doordash.interview.phoneNumberParser.controller;

import com.doordash.interview.phoneNumberParser.dto.PhoneNumberDTO;
import com.doordash.interview.phoneNumberParser.dto.PhoneNumberDTOMapper;
import com.doordash.interview.phoneNumberParser.persistence.model.PhoneEntity;
import com.doordash.interview.phoneNumberParser.persistence.repository.PhoneNumberRepository;
import com.doordash.interview.phoneNumberParser.request.RawPhoneData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PhoneNumberController.class)
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application-test.properties")
class PhoneNumberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhoneNumberDTOMapper phoneNumberDTOMapper;

    @MockBean
    private PhoneNumberRepository phoneNumberRepository;

    @Mock
    private PhoneEntity phoneEntity;

    @Mock
    private PhoneNumberDTO phoneNumberDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        phoneEntity = new PhoneEntity();
        phoneEntity.setPhoneNumber("234234234");
        phoneEntity.setPhoneNumberType("Cell");
        phoneEntity.setOccurrences(3);


        phoneNumberDTO = new PhoneNumberDTO();
        phoneNumberDTO.setPhoneNumber("234234234");
        phoneNumberDTO.setPhoneNumberType("Cell");
    }

    @Test
    @WithMockUser(username = "admin", password = "password")
    void homePage() throws Exception {
        this.mockMvc.perform(get("/api/v1/phone-number-parser"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome to Phone Number Parser"));
    }


    /**
     * No Data throws an exception
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "admin", password = "password")
    void addPhoneFailure() throws Exception {

        PhoneNumberDTO phoneNumberDTO = new PhoneNumberDTO();
        phoneNumberDTO.setPhoneNumber("234234234");
        phoneNumberDTO.setPhoneNumberType("Cell");

        when(phoneNumberDTOMapper.convertPhoneEntityToPhoneNumberDTO(Mockito.any())).thenReturn(phoneNumberDTO);
        when(phoneNumberRepository.findById(Mockito.any())).thenReturn(Optional.of(phoneEntity));
        when(phoneNumberRepository.save(Mockito.any())).thenReturn(phoneEntity);
        RawPhoneData rawPhoneData = new RawPhoneData();
        rawPhoneData.setRawPhoneNumbers("(Home)415-415-4155 (Cell) 415-123-4561 (Business) 115-514-5145");
        this.mockMvc.perform(post("/api/v1/phone-number-parser"))
                .andDo(print())
                .andExpect(status().isUnsupportedMediaType());

    }

    /**
     *
     * Tests a succesful scenario of API invocation.
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "admin", password = "password")
    void addPhoneSuccess() throws Exception {

        PhoneNumberDTO phoneNumberDTO = new PhoneNumberDTO();
        phoneNumberDTO.setPhoneNumber("234234234");
        phoneNumberDTO.setPhoneNumberType("Cell");

        when(phoneNumberDTOMapper.convertPhoneEntityToPhoneNumberDTO(Mockito.any())).thenReturn(phoneNumberDTO);
        when(phoneNumberRepository.findById(Mockito.any())).thenReturn(Optional.of(phoneEntity));
        when(phoneNumberRepository.save(Mockito.any())).thenReturn(phoneEntity);
        RawPhoneData rawPhoneData = new RawPhoneData();
        rawPhoneData.setRawPhoneNumbers("(Home)415-415-4155 (Cell) 415-123-4561 (Business) 115-514-5145");
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(get("/api/v1/phone-number-parser")

                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"results\":[{\"occurrences\":null,\"phone_number\":\"234234234\",\"phone_type\":\"Cell\"},{\"occurrences\":null,\"phone_number\":\"234234234\",\"phone_type\":\"Cell\"},{\"occurrences\":null,\"phone_number\":\"234234234\",\"phone_type\":\"Cell\"}]}"));

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}