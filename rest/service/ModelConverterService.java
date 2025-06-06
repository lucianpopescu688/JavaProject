package service;

import model.*;
import model.*;
import repository.*;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModelConverterService {

    @Autowired
    private RestArtistRepository restArtistRepository;

    @Autowired
    private RestPerformanceRepository restPerformanceRepository;

    @Autowired
    private RestOfficeWorkerRepository restOfficeWorkerRepository;

    // Convert original Performance to REST Performance
    public RestPerformance convertToRestPerformance(Performance original) {
        RestPerformance restPerformance = new RestPerformance();
        restPerformance.setPerformanceID(original.getPerformanceID());
        restPerformance.setDate(original.getDate());
        restPerformance.setPlace(original.getPlace());
        restPerformance.setAvailableTickets(original.getAvailableTickets());
        restPerformance.setSoldTickets(original.getSoldTickets());

        // Convert and save artist if needed
        if (original.getArtist() != null) {
            RestArtist restArtist = new RestArtist(original.getArtist());
            restPerformance.setArtist(restArtist);
        }

        return restPerformance;
    }

    // Convert REST Performance to original Performance
    public Performance convertToOriginalPerformance(RestPerformance restPerformance) {
        return restPerformance.toOriginalPerformance();
    }

    // Batch convert lists
    public List<RestPerformance> convertToRestPerformances(List<Performance> originals) {
        return originals.stream()
                .map(this::convertToRestPerformance)
                .collect(Collectors.toList());
    }

    public List<Performance> convertToOriginalPerformances(List<RestPerformance> restPerformances) {
        return restPerformances.stream()
                .map(this::convertToOriginalPerformance)
                .collect(Collectors.toList());
    }
}