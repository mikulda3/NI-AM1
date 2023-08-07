package cz.cvut.fit.niam1.wsserver;

import https.courses_fit_cvut_cz.ni_am1.tutorials.web_services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class WebServiceEndpoint {

    @Autowired
    private WebServiceRepository repository;

    //Returns all bookings saved in repository
    @PayloadRoot(namespace = "https://courses.fit.cvut.cz/NI-AM1/tutorials/web-services/", localPart = "getBookingsRequest")
    @ResponsePayload
    public GetBookingsResponse getBookings(@RequestPayload GetBookingsRequest request) {
        GetBookingsResponse response = new GetBookingsResponse();
        response.getBooking().addAll(repository.getBookings());
        return response;
    }

    //Adds booking into repository, return empty request
    @PayloadRoot(namespace = "https://courses.fit.cvut.cz/NI-AM1/tutorials/web-services/", localPart = "addBookingRequest")
    @ResponsePayload
    public AddBookingResponse addBooking(@RequestPayload AddBookingRequest request) {
        AddBookingResponse response = new AddBookingResponse();
        repository.addBooking(request.getBooking());
        return response;
    }

    //Deletes booking from repository, return empty request
    @PayloadRoot(namespace = "https://courses.fit.cvut.cz/NI-AM1/tutorials/web-services/", localPart = "deleteBookingRequest")
    @ResponsePayload
    public DeleteBookingResponse deleteBooking(@RequestPayload DeleteBookingRequest request) {
        DeleteBookingResponse response = new DeleteBookingResponse();
        repository.deleteBooking(request.getBooking().getId());
        return response;
    }

    //Deletes old repository, uploads new based on id, returns empty request
    @PayloadRoot(namespace = "https://courses.fit.cvut.cz/NI-AM1/tutorials/web-services/", localPart = "updateBookingRequest")
    @ResponsePayload
    public UpdateBookingResponse updateBooking(@RequestPayload UpdateBookingRequest request) {
        UpdateBookingResponse response = new UpdateBookingResponse();
        repository.updateBooking(request.getBooking());
        return response;
    }

}
