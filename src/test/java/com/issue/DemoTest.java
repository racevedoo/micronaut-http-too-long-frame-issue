package com.issue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.token.jwt.generator.JwtTokenGenerator;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import jakarta.inject.Inject;

@MicronautTest
class DemoTest {

    @Inject
    EmbeddedApplication<?> application;

    @Inject
    @Client("/api/v2")
    private HttpClient client;

    @Inject private JwtTokenGenerator generator;

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }

    @Test
    void testLargeInitialLine() {
        String token = generator
            .generateToken(Authentication.build("myuser"), 1000)
            .get();
        var request =
            HttpRequest.GET("/user/" + RandomStringUtils.randomAlphanumeric(1025)).bearerAuth(token);
        var error =
            assertThrows(
                HttpClientResponseException.class, () -> client.toBlocking().exchange(request));

        assertEquals(413, error.getStatus().getCode());
    }
}
