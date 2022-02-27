package com.creditcalculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

@ExtendWith(MockitoExtension.class)
class TestControllerTest {

    private MockMvc mockMvc;
    private TestController testController;

    @BeforeEach
    void setup() {
        testController = new TestController();
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mockMvc = MockMvcBuilders.standaloneSetup(testController)
                .build();
    }
    @Test
    void testAllAccess() throws Exception {
        // init
        String expectedMessage = "Public Content.";
        // when
        MockHttpServletResponse response = mockMvc.perform(get("/api/test/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString().equals(expectedMessage));
    }

}