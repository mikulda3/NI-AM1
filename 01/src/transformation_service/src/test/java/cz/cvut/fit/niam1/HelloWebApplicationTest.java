package cz.cvut.fit.niam1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

// test spousti nasi aplikaci HelloWebApplication.class, na nahodnem portu (kvuli kolizi)
@SpringBootTest(classes = HelloWebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloWebApplicationTest {

    // abychom vedeli, jaky je ten random port
    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    // priprava adresy, na ktere se dela test
    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
    }

    // nas test
    @Test
    public void getHello() throws Exception {
        String name = "world";
        ResponseEntity<String> response = template.getForEntity(base.toString() + "hello/" + name, String.class);
        assertThat(response.getBody()).isEqualTo("Hello " + name);
    }
}