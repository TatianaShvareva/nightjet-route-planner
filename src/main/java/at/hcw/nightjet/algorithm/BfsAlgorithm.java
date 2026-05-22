package at.hcw.nightjet.algorithm;

import at.hcw.nightjet.graph.NightjetGraph;
import at.hcw.nightjet.model.City;
import at.hcw.nightjet.model.RouteResult;
import at.hcw.nightjet.model.TrainConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Implements Breadth-First Search for the Nightjet train graph.
 *
 * <p>BFS is used to find a route with the fewest train connections.
 * In this case, the travel time is ignored and every connection has the same cost.</p>
 */
public class BfsAlgorithm {

    /**
     * Finds a route with the fewest connections between two cities.
     *
     * @param graph the Nightjet graph
     * @param start the start city
     * @param destination the destination city
     * @return the route with the fewest connections as a RouteResult
     * @throws IllegalArgumentException if graph, start or destination is null,
     *                                  or if one of the cities is not part of the graph
     * @throws IllegalStateException if no route exists between start and destination
     */
    public RouteResult findRouteWithFewestTransfers(NightjetGraph graph, City start, City destination) {
        validateInput(graph, start, destination);

        if (start.equals(destination)) {
            return new RouteResult(List.of(start), 0);
        }

        Queue<City> queue = new LinkedList<>();
        List<City> visitedCities = new ArrayList<>();
        Map<City, City> previousCities = new HashMap<>();

        queue.add(start);
        visitedCities.add(start);

        while (!queue.isEmpty()) {
            City currentCity = queue.poll();

            if (currentCity.equals(destination)) {
                break;
            }

            for (City neighbor : graph.getNeighbors(currentCity)) {
                if (!visitedCities.contains(neighbor)) {
                    visitedCities.add(neighbor);
                    previousCities.put(neighbor, currentCity);
                    queue.add(neighbor);
                }
            }
        }

        if (!visitedCities.contains(destination)) {
            throw new IllegalStateException("No route exists between the selected cities.");
        }

        List<City> path = buildPath(start, destination, previousCities);
        int totalTravelTime = calculateTotalTravelTime(graph, path);

        return new RouteResult(path, totalTravelTime);
    }

    /**
     * Validates the input for the BFS algorithm.
     *
     * @param graph the graph to check
     * @param start the start city
     * @param destination the destination city
     */
    private void validateInput(NightjetGraph graph, City start, City destination) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph must not be null.");
        }

        if (start == null || destination == null) {
            throw new IllegalArgumentException("Start and destination city must not be null.");
        }

        if (!graph.containsCity(start)) {
            throw new IllegalArgumentException("Start city is not part of the graph.");
        }

        if (!graph.containsCity(destination)) {
            throw new IllegalArgumentException("Destination city is not part of the graph.");
        }
    }

    /**
     * Builds the final path from start city to destination city.
     *
     * @param start the start city
     * @param destination the destination city
     * @param previousCities the previous city map
     * @return the ordered route path
     */
    private List<City> buildPath(City start, City destination, Map<City, City> previousCities) {
        List<City> path = new ArrayList<>();
        City currentCity = destination;

        while (currentCity != null) {
            path.add(0, currentCity);

            if (currentCity.equals(start)) {
                break;
            }

            currentCity = previousCities.get(currentCity);
        }

        return path;
    }

    /**
     * Calculates the total travel time of a found route.
     *
     * <p>BFS does not use travel time for choosing the route, but the application
     * still displays the total travel time to the user.</p>
     *
     * @param graph the Nightjet graph
     * @param path the route path
     * @return the total travel time in minutes
     */
    private int calculateTotalTravelTime(NightjetGraph graph, List<City> path) {
        int totalTravelTime = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            City currentCity = path.get(i);
            City nextCity = path.get(i + 1);

            for (TrainConnection connection : graph.getConnectionsFrom(currentCity)) {
                if (connection.getOtherCity(currentCity).equals(nextCity)) {
                    totalTravelTime += connection.getTravelTimeMinutes();
                    break;
                }
            }
        }

        return totalTravelTime;
    }
}