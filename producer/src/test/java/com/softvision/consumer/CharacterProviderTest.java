package com.softvision.consumer;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.junitsupport.loader.PactUrl;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import com.softvision.consumer.controller.CharacterService;
import com.softvision.consumer.domain.Character;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Provider("character_provider")
@PactFolder("path/to/target/pacts")
public class CharacterProviderTest {

    private final Logger log = LoggerFactory.getLogger(CharacterProviderTest.class);

    @MockBean
    CharacterService characterService;

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State({"no-state"})
    public void toDefaultState() {
        log.info("No state for this use case");
    }

    @State({"3item-state"})
    public void getCharsState() {
        log.info("Mock service");
        Mockito.when(
                characterService.getCharters())
                .thenReturn(
                        List.of(
                                new Character("Test1", "Light"),
//                                new Character("Test2", "Dark"),
                                new Character("Test3", "Light")
                        ));
    }
}
