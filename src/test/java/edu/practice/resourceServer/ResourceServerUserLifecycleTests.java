package edu.practice.resourceServer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import edu.practice.resourceServer.model.entity.ApplicationRole;
import edu.practice.resourceServer.model.entity.ApplicationUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = ResourceServerApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ResourceServerUserLifecycleTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${AUTHORIZATION_CLIENT_ID}")
    private String authorizationClientId;

    @Value("${AUTHORIZATION_CLIENT_SECRET}")
    private String authorizationClientSecret;

    private ApplicationUser administratorUser;
    private String administratorOauthAuthorizationToken;

    private ApplicationUser testUser1;
    private String testUser1OauthAuthorizationToken;

    private ApplicationUser testUser2;
    private String testUser2OauthAuthorizationToken;

    @BeforeAll
    static void beforeAll() {
        System.setProperty("AUTHORIZATION_CLIENT_ID", "test_authorization_client_id");
        System.setProperty("AUTHORIZATION_CLIENT_SECRET", "test_authorization_client_secret");
    }

    @BeforeEach
    void beforeEach() {
        administratorUser = ApplicationUser.builder()
                .username("administrator")
                .password("administrator_password")
                .role(ApplicationRole.ADMINISTRATOR)
                .enabled(true)
                .build();
        testUser1 = ApplicationUser.builder()
                .username("test_user_1")
                .password("test_password_1")
                .role(ApplicationRole.USER)
                .enabled(true)
                .build();
        testUser2 = ApplicationUser.builder()
                .username("test_user_2")
                .password("test_password_2")
                .role(ApplicationRole.USER)
                .enabled(true)
                .build();
    }

    //@Order(1)
    //@Test
    void userCreationTest() throws Exception {
        final MultiValueMap<String, String> user_1_parameters = new LinkedMultiValueMap<>();
        user_1_parameters.add("client_id", authorizationClientId);
        user_1_parameters.add("grant_type", "client_credentials");
        user_1_parameters.add("username", testUser1.getUsername());
        user_1_parameters.add("password", testUser1.getPassword());
        createUser(user_1_parameters, testUser1);
        testUser1OauthAuthorizationToken = getAccessToken(user_1_parameters);
        checkAccessToken(testUser1OauthAuthorizationToken);

        final MultiValueMap<String, String> user_2_parameters = new LinkedMultiValueMap<>();
        user_2_parameters.add("client_id", authorizationClientId);
        user_2_parameters.add("grant_type", "client_credentials");
        user_2_parameters.add("username", testUser2.getUsername());
        user_2_parameters.add("password", testUser2.getPassword());
        createUser(user_2_parameters, testUser2);
        testUser2OauthAuthorizationToken = getAccessToken(user_2_parameters);
        checkAccessToken(testUser2OauthAuthorizationToken);
        
        final MultiValueMap<String, String> administrator_parameters = new LinkedMultiValueMap<>();
        administrator_parameters.add("client_id", authorizationClientId);
        administrator_parameters.add("grant_type", "client_credentials");
        administrator_parameters.add("username", administratorUser.getUsername());
        administrator_parameters.add("password", administratorUser.getPassword());
        mockMvc
                .perform(
                        post("/users")
                        .params(administrator_parameters)
                        .content(objectMapper.writeValueAsString(administratorUser))
                        .with(httpBasic(authorizationClientId, authorizationClientSecret))                        
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isConflict());
        administratorOauthAuthorizationToken = getAccessToken(administrator_parameters);
        checkAccessToken(administratorOauthAuthorizationToken);
    }

    //@Order(2)
    //@Test
    void userGetTest() throws Exception {
        mockMvc
                .perform(
                    get("/users"))
                .andExpect(status().isForbidden());
    }

    //@Order(3)
    //@Test
    void userUpdateTest() {

    }

    //@Order(4)
    //@Test
    void userDeleteTest() {
        
    }

    private void createUser(MultiValueMap<String, String> parameters, ApplicationUser user) throws Exception {
        mockMvc
                .perform(
                        post("/users")
                        .params(parameters)
                        .content(objectMapper.writeValueAsString(user))
                        .with(httpBasic(authorizationClientId, authorizationClientSecret))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());
    }

    private String getAccessToken(MultiValueMap<String, String> parameters) throws Exception {
        final MvcResult mvcResult = mockMvc.perform(
                        post("/oauth/token")
                        .params(parameters)
                        .with(httpBasic(authorizationClientId, authorizationClientSecret))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        return mvcResult.getResponse().getContentAsString();
    }

    private void checkAccessToken(String fullAccessToken) throws Exception {
        final JsonNode fullAccessTokenJson = objectMapper.readValue(fullAccessToken, JsonNode.class);
        final String accessToken = fullAccessTokenJson.get("access_token").asText();
        mockMvc.perform(
                    post("/oauth/check_token")
                    .param("token", accessToken)
                    .with(httpBasic(authorizationClientId, authorizationClientSecret))
                    .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));        
    }

}
