package edu.practice.resourceServer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {ResourceServerApplication.class})
class ResourceServerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void actuatorResponds() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(
                get("/actuator/health")
                .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andReturn();
        assertEquals("UP",
            objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                ObjectNode.class)
            .get("status").asText());
    }

    @Test
    void unsecuredHelloResponds() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(
                get("/test/hello")
                .param("name", "world")
                .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andReturn();
        assertEquals("hello world", mvcResult.getResponse().getContentAsString());
    }
}
