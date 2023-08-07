package cz.cvut.fit.niam1.rest;


import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class RestApplicationRepository {

    private static final List<Tour> tours = new ArrayList<>();
    long timer;

    @PostConstruct
    public void initRepo() {
        List<String> list = new ArrayList<String>();
        list.add("David Mikulka");
        tours.add(new Tour("1", "Taipei", list));
        tours.add(new Tour("2", "Prague", new ArrayList<String>()));
        updateTimer();
    }

    public void updateTimer() {
        timer = System.currentTimeMillis();
    }

    public void addTour(Tour c) {
        int max = tours.stream().map(t -> Integer.valueOf(t.getId())).max(Integer::compare).get();
        c.setId(String.valueOf(max+1));
        tours.add(c);
        updateTimer();
    }

    public List<Tour> getTours() {
        return tours;
    }

    public Tour getTourById(String id) {
        return tours.stream().filter(c -> c.getId().equals(id)).findAny().orElse(null);
    }

    public void deleteTour(String id) {
        tours.removeIf(c -> c.getId().equals(id));
    }

    public void updateTour(String id, Tour tour){
        tours.stream().filter(c -> c.getId().equals(id)).findAny().map(
                t -> {
                    t.setName(tour.getName());
                    return t;
                })
                .orElseGet(() -> {
                    tour.setId(id);
                    tours.add(tour);
                    return tour;
                });
    }

    public void addCustomer(String id, String customer) {
        getTourById(id).addCustomer(customer);
        updateTimer();
    }
}
