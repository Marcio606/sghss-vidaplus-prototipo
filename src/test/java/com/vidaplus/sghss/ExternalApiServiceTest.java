package com.vidaplus.sghss;

import com.vidaplus.sghss.service.ExternalApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class ExternalApiServiceTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ExternalApiService externalApiService;

    @Test
    public void getHealth_returnsMockedResponse() {
        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
        server.expect(requestTo("https://httpbin.org/get"))
                .andRespond(withSuccess("{\"status\":\"ok\"}", MediaType.APPLICATION_JSON));

        String resp = externalApiService.getHealth();
        assertThat(resp).contains("status");

        server.verify();
    }
}
