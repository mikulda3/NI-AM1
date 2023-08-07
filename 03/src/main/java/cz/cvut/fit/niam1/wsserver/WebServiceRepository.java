package cz.cvut.fit.niam1.wsserver;

import https.courses_fit_cvut_cz.ni_am1.tutorials.web_services.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class WebServiceRepository {

    private static final List<Booking> bookings = new ArrayList<>();

    //Testing, add 2 examples of bookings
    @PostConstruct
    public void initRepo() {
        Booking b1 = new Booking();
        b1.setId(1);
        b1.setPassengerName("David");
        b1.setPassengerSurname("Mikulka");
        b1.setDepartureDate("30-01-2023");
        b1.setDepartureAirport("Prague");
        b1.setArrivalDate("01-02-2023");
        b1.setArrivalAirport("Taipei");
        bookings.add(b1);
    }

    public void addBooking(Booking b) {
        bookings.add(b);
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void deleteBooking(Integer id) {
        bookings.removeIf(b -> b.getId() == id);
    }

    public void updateBooking(Booking b) {
        deleteBooking(b.getId());
        addBooking(b);
    }

}
