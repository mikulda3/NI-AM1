package cz.cvut.fit.niam1.wsserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class ValidationClientConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("cz.cvut.fit.niam1.webservices.hw04");
        return marshaller;
    }

    @Bean
    public ValidationClient validationClient(Jaxb2Marshaller marshaller) {
        ValidationClient client = new ValidationClient();
        client.setDefaultUri("http://147.32.233.18:8888/NI-AM1-CardValidation/ws/card.wsdl");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}
