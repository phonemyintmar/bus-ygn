package mm.gov.rtad.busygn.controller;

import mm.gov.rtad.busygn.logic.IFindRouteLogic;
import mm.gov.rtad.busygn.logic.implementations.FindRouteLogic;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bus-ygn")
public class BusController {

    private final IFindRouteLogic findRouteLogic;

    public BusController(IFindRouteLogic findRouteLogic) {
        this.findRouteLogic = findRouteLogic;
    }

    @GetMapping("nearestBusStop")
    public ResponseEntity<?> getNearestBusStop(double lat, double lon) {
        return null;
    }

    @GetMapping("findRoute")
    public ResponseEntity<?> getNearestBusStop(String startStopId, String endStopId) {
        findRouteLogic.findRoute(startStopId, endStopId);
        return null;
    }

}

/*
*     public List<BusRoute> findBestRoute(BusStop startingBusStop, BusStop endingBusStop) {
        List<BusRoute> bestRoutes = new ArrayList<>();

        // Step 2: Find all bus routes that pass through the starting bus stop
        List<BusRoute> startingBusRoutes = busRouteRepository.findByBusStop(startingBusStop);

        // Step 3: Find all bus routes that pass through the ending bus stop
        List<BusRoute> endingBusRoutes = busRouteRepository.findByBusStop(endingBusStop);

        // Step 4: Find the intersection of the starting and ending bus routes
        List<BusRoute> commonBusRoutes = startingBusRoutes.stream()
                .filter(endingBusRoutes::contains)
                .collect(Collectors.toList());

        // Step 5: Find the list of bus stops along each common bus route
        List<List<BusStop>> busStopsAlongRoutes = commonBusRoutes.stream()
                .map(busRoute -> busStopRepository.findByBusRoute(busRoute))
                .collect(Collectors.toList());

        // Step 6: Find the intersection of the starting bus stop and the bus stops along each common bus route
        List<BusStop> commonBusStops = startingBusStop.getBusRoutes().stream()
                .flatMap(busRoute -> busStopRepository.findByBusRoute(busRoute).stream())
                .distinct()
                .filter(busStopsAlongRoutes.stream().flatMap(Collection::stream).collect(Collectors.toSet())::contains)
                .collect(Collectors.toList());

        // Step 7: Find the list of bus routes that pass through each common bus stop
        List<List<BusRoute>> busRoutesAtStops = commonBusStops.stream()
                .map(busStop -> busStop.getBusRoutes())
                .collect(Collectors.toList());

        // Step 8: Find the intersection of the bus routes at each common bus stop and the common bus routes
        List<BusRoute> intermediateRoutes = busRoutesAtStops.stream()
                .flatMap(Collection::stream)
                .distinct()
                .filter(commonBusRoutes::contains)
                .collect(Collectors.toList());

        // Step 9: Calculate the total distance or time it takes to travel from the starting bus stop to the ending bus stop using each intermediate route
        Map<BusRoute, Double> routeDistances = new HashMap<>();
        for (BusRoute route : intermediateRoutes) {
            List<BusStop> stops = busStopRepository.findByBusRoute(route);
            int startingIndex = stops.indexOf(startingBusStop);
            int endingIndex = stops.indexOf(endingBusStop);
            if (startingIndex < endingIndex) {
                List<BusStop> relevantStops = stops.subList(startingIndex, endingIndex + 1);
                double distance = calculateDistanceBetweenStops(relevantStops);
                routeDistances.put(route, distance);
            }
        }

        // Step 10: Choose the route with the shortest distance as the best route
        Optional<Map.Entry<BusRoute, Double>> shortestDistance = routeDistances.entrySet().stream()
                .min(Comparator.comparing(Map.Entry::getValue));
        if (shortestDistance.isPresent()) {
            bestRoutes.add(shortestDistance.get().getKey());
        }

        // Step 11: If there is no direct route, repeat the process for each common bus stop
        if (bestRoutes.isEmpty()) {
            for (BusStop stop : commonBusStops) {
                List<BusRoute> routesAtStop = stop.getBusRoutes();
                List<BusRoute> intermediateRoutesAtStop = routesAtStop.stream()
                        .distinct()
                        .filter(commonBusRoutes::contains)
                        .collect(Collectors.toList());
                Map<BusRoute, Double> routeDistancesAtStop = new HashMap<>();
                for (BusRoute route : intermediateRoutesAtStop) {
                    List<BusStop> stops = busStopRepository.findByBusRoute(route);
                    int startingIndex = stops.indexOf(startingBusStop);
                    int endingIndex = stops.indexOf(stop);
                    if (startingIndex < endingIndex) {
                        List<BusStop> relevantStops = stops.subList(startingIndex, endingIndex + 1);
                        double distance = calculateDistanceBetweenStops(relevantStops);
                        routeDistancesAtStop.put(route, distance);
                    }
                    startingIndex = stops.indexOf(stop);
                    endingIndex = stops.indexOf(endingBusStop);
                    if (startingIndex < endingIndex) {
                        List<BusStop> relevantStops = stops.subList(startingIndex, endingIndex + 1);
                        double distance = calculateDistanceBetweenStops(relevantStops);
                        routeDistancesAtStop.merge(route, distance, Double::sum);
                    }
                }
                Optional<Map.Entry<BusRoute, Double>> shortestDistanceAtStop = routeDistancesAtStop.entrySet().stream()
                        .min(Comparator.comparing(Map.Entry::getValue));
                if (shortestDistanceAtStop.isPresent()) {
                    bestRoutes.addAll(intermediateRoutes);
                    bestRoutes.add(shortestDistanceAtStop.get().getKey());
                    bestRoutes.addAll(intermediateRoutes);
                    break;
                }
            }
        }
        return bestRoutes;
        * }*/
