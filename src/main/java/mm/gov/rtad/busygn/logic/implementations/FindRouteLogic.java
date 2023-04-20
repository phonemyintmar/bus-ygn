package mm.gov.rtad.busygn.logic.implementations;

import mm.gov.rtad.busygn.database.model.Bus;
import mm.gov.rtad.busygn.logic.IFindRouteLogic;
import mm.gov.rtad.busygn.payload.PossibleRoute;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FindRouteLogic implements IFindRouteLogic {

    private final MongoTemplate mongoTemplate;

    public FindRouteLogic(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public ResponseEntity<?> findRoute(String startStopId, String endStopId) {
        Query query = Query.query(new Criteria().andOperator(
                Criteria.where("busRoute").is(startStopId),
                Criteria.where("busRoute").is(endStopId)
        ));
//        should sort
        List<Bus> busList = mongoTemplate.find(query, Bus.class, "bus");
        return null;
    }

    public List<PossibleRoute> findRoutesBetweenStops(String startStopId, String endStopId) {
        // Step 1: Find all the routes that pass through the start bus stop
        List<Bus> startRoutes = mongoTemplate.find(Query.query(Criteria.where("busRoute").is(startStopId)), Bus.class);

        // Step 2: Find all the routes that pass through the end bus stop
        List<Bus> endRoutes = mongoTemplate.find(Query.query(Criteria.where("busRoute").is(endStopId)), Bus.class);

        List<PossibleRoute> possibleRoutes = new ArrayList<>();
        // Step 3 and 4: Find routes that connect the start and end bus stops
        for (Bus startRoute : startRoutes) {
            List<String> startStops = startRoute.getBusRoute();
            for (String startStop : startStops) {
                for (Bus endRoute : endRoutes) {
                    List<String> endStops = endRoute.getBusRoute();
                    if (endStops.contains(startStop)) {
                        PossibleRoute possibleRoute = new PossibleRoute();
                        possibleRoute.setFirstBusId(startRoute.getId());
                        possibleRoute.setSecondBusId(endRoute.getId());
                        possibleRoutes.add(possibleRoute);
                    }
                }
            }
        }

        // Step 5: Repeat step 3 and 4 for each end route if no connection is found
        if (possibleRoutes.isEmpty()) {
            for (Bus endRoute : endRoutes) {
                List<String> endStops = endRoute.getBusRoute();
                for (String stop : endStops) {
                    for (Bus startRoute : startRoutes) {
                        List<String> startStops = startRoute.getBusRoute();
                        if (startStops.contains(stop)) {
                            // Found a possible route
                            PossibleRoute possibleRoute = new PossibleRoute();
                            possibleRoute.setFirstBusId(startRoute.getId());
                            possibleRoute.setSecondBusId(endRoute.getId());
                            possibleRoutes.add(possibleRoute);
                        }
                    }
                }
            }
        }
        possibleRoutes.sort(Comparator.comparingInt(x -> (x.getFirstBusRoute().size() + x.getSecondBusRoute().size())));

/*        // Step 6: Create a graph where each bus stop is a node and each bus route is an edge
        Graph<BusStop, Bus> graph = new DefaultDirectedWeightedGraph<>(Bus.class);

        // Step 7: Assign weights to the edges based on distance, travel time, and frequency of buses
        List<BusStop> allStops = mongoTemplate.findAll(BusStop.class);
        for (BusStop stop : allStops) {
            graph.addVertex(stop);
        }
        for (Bus route : possibleRoutes) {
            List<String> stops = route.getBusRoute();
            double totalDistance = 0.0;
            // Calculate total distance of the route
            for (int i = 0; i < stops.size() - 1; i++) {
                BusStop fromStop = mongoTemplate.findById(stops.get(i), BusStop.class);
                BusStop toStop = mongoTemplate.findById(stops.get(i + 1), BusStop.class);
                totalDistance += calculateDistance(fromStop, toStop); // Calculate distance between two stops
            }
            // Calculate weight of the route
            double weight = calculateWeight(route, totalDistance); // Use custom method to calculate weight
            for (int i = 0; i < stops.size() - 1; i++) {
                BusStop fromStop = mongoTemplate.findById(stops.get(i), BusStop.class);
                BusStop toStop = mongoTemplate.findById(stops.get(i + 1), BusStop.class);
                graph.addEdge(fromStop, toStop, route); // Add edge with weight to the graph
                graph.setEdgeWeight(route, weight);
            }
        }

        // Step 8: Find the shortest path between start and end bus stops
        DijkstraShortestPath<BusStop, Bus> shortestPath = new DijkstraShortestPath<>(graph);
        List<Bus> routeList = shortestPath.getPath(mongoTemplate.findById(startStopId, BusStop.class),
                mongoTemplate.findById(endStopId, BusStop.class)).getEdgeList();
                */

        return possibleRoutes;
    }

    // Custom methods to calculate distance and weight

}
