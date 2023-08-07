package cz.cvut.fit.niam1.wsserver;

import cz.cvut.fit.niam1.webservices.hw04.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class WebServiceRepository {

    private static final List<Payment> payments = new ArrayList<>();

    public void addPayment(Payment p) {
        payments.add(p);
    }
    public List<Payment> getPayments() {
        return payments;
    }

}
