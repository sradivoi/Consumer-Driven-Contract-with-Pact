package com.softvision.consumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "character_provider", port = "1234")
public class CharacterTest {

    private final Logger logger = LoggerFactory.getLogger(CharacterTest.class);
    private final Map<String, String> HEADERS = MapUtils.putAll(new HashMap<>(), new String[]{
            "Content-Type", "application/json"
    });
    private final DslPart characterTemplate = new PactDslJsonBody()
            .stringType("name")
            .stringMatcher("side", "^[a-zA-Z0-9]{0,10}$", "Light");

    @BeforeEach
    public void setUp(MockServer mockServer) {
        assertThat(mockServer, is(notNullValue()));
    }

    @Pact(consumer = "get_character")
    public RequestResponsePact getCharacter(PactDslWithProvider builder) {
        return builder
                .given("no-state")
                .uponReceiving("Get character - SUCCESS")
                    .path("/characters/1")
                    .method("GET")
                .willRespondWith()
                    .headers(HEADERS)
                    .status(200)
                    .body(characterTemplate)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getCharacter")
    void getCharacter(MockServer mockServer) throws IOException {
        HttpResponse httpResponse = Request.Get(mockServer.getUrl() + "/characters/1").execute().returnResponse();
        logger.info("Response body: {}", EntityUtils.toString(httpResponse.getEntity()));
        assertThat(httpResponse.getStatusLine().getStatusCode(), is(equalTo(200)));
    }

    @Pact(consumer = "get_characters")
    public RequestResponsePact getCharacters(PactDslWithProvider builder) {
        final DslPart arrayOfCharactersBody = new PactDslJsonArray()
                .template(characterTemplate, 3);
        return builder
                .given("3item-state")
                .uponReceiving("Get all characters - SUCCESS")
                    .path("/characters")
                    .method("GET")
                .willRespondWith()
                    .headers(HEADERS)
                    .status(200)
                    .body(arrayOfCharactersBody)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getCharacters")
    void getAllCharacters(MockServer mockServer) throws IOException {
        HttpResponse httpResponse = Request.Get(mockServer.getUrl() + "/characters").execute().returnResponse();
        logger.info("Response body: {}", EntityUtils.toString(httpResponse.getEntity()));
        assertThat(httpResponse.getStatusLine().getStatusCode(), is(equalTo(200)));
    }

}
