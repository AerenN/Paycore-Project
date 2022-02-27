package com.creditcalculator.controller;

import com.creditcalculator.services.CreditLimitCalculationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Disabled
@ExtendWith(MockitoExtension.class)
public class CreditLimitControllerTest {

    private MockMvc mockMvc;
    @Mock
    private CreditLimitCalculationService creditLimitCalculationService;
    @InjectMocks
    private CreditLimitController creditLimitController;


    private static final String USERNAME = "12345678912";

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mockMvc = MockMvcBuilders.standaloneSetup(creditLimitController)
                .build();
    }

//    @WithMockUser
//

    @Test
    void testRequestCredit() throws Exception {
        // init
//        when(creditLimitCalculationService.requestCreditLimit(USERNAME)).thenReturn(true);
        // when
        MockHttpServletResponse response = mockMvc.perform(get("/api/credit/request-credit")
                        .param("username",USERNAME)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString().equals("true"));
    }
}