package cz.cvut.fit.niam1.wsserver;

import cz.cvut.fit.niam1.webservices.hw04.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class WebServiceEndpoint {

    @Autowired
    private WebServiceRepository repository;

    @Autowired
    private ValidationClient client;

    //Returns all payments saved in repository
    @PayloadRoot(namespace = "https://courses.fit.cvut.cz/NI-AM1/hw/04/", localPart = "getPaymentsRequest")
    @ResponsePayload
    public GetPaymentsResponse getPayments(@RequestPayload GetPaymentsRequest request) {
        GetPaymentsResponse response = new GetPaymentsResponse();
        response.getPayments().addAll(repository.getPayments());
        return response;
    }

}
