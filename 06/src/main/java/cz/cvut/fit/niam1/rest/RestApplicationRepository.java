package cz.cvut.fit.niam1.rest;


import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class RestApplicationRepository {

    private static final List<Tour> tours = new ArrayList<>();
    private static final List<Tour> deleteQueue = new ArrayList<>();


    @PostConstruct
    public void initRepo() {
        tours.add(new Tour("1", "Taipei"));
        tours.add(new Tour("2", "Prague"));
    }

    public void addTour(Tour c) {
        int max = tours.stream().map(t -> Integer.valueOf(t.getId())).max(Integer::compare).get();
        c.setId(String.valueOf(max+1));
        tours.add(c);
    }

    public List<Tour> getTours() {
        return tours;
    }

    public Tour getTourById(String id) {
        return tours.stream().filter(c -> c.getId().equals(id)).findAny().orElse(null);
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

    public void deleteTour(String id) {
        deleteQueue.add(getTourById(id));
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        tours.removeIf(c -> c.getId().equals(id));
                        deleteQueue.removeIf(c -> c.getId().equals(id));
                    }
                },
                10 * 1000 // 10s
        );
    }

    public List<Tour> deleteTourStatus() {
        return deleteQueue;
    }
}
