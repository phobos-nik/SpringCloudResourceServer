package edu.practice.resourceServer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = ResourceServerApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ResourceServerNoteLifecycleTests {

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void beforeAll() {

    }

    @Order(1)
    @Test
    void noteCreationTest() {
        
    }

    @Order(2)
    @Test
    void noteGetTest() {

    }

    @Order(3)
    @Test
    void noteUpdateTest() {

    }

    @Order(4)
    @Test
    void noteDeleteTest() {
        
    }

}