package at.hcw.nightjet.algorithm;

import at.hcw.nightjet.graph.NightjetGraph;
import at.hcw.nightjet.model.City;
import at.hcw.nightjet.model.RouteResult;
import at.hcw.nightjet.model.TrainConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implements Dijkstra's algorithm for the Nightjet train graph.
 *
 * <p>The algorithm finds the fastest route between two cities in a weighted graph.
 * In this project, the edge weight is the travel time of a train connection.</p>
 */
public class DijkstraAlgorithm {

    /**
     * Finds the fastest route between a start city and a destination city.
     *
     * @param graph the Nightjet graph
     * @param start the start city
     * @param destination the destination city
     * @return the fastest route as a RouteResult
     * @throws IllegalArgumentException if graph, start or destination is null,
     *                                  or if one of the cities is not part of the graph
     * @throws IllegalStateException if no route exists between start and destination
     */
    public RouteResult findFastestRoute(NightjetGraph graph, City start, City destination) {
        validateInput(graph, start, destination);

        Map<City, Integer> distances = new HashMap<>();
        Map<City, City> previousCities = new HashMap<>();
        List<City> unvisitedCities = new ArrayList<>(graph.getCities());

        initializeDistances(graph, start, distances);

        while (!unvisitedCities.isEmpty()) {
            City currentCity = findCityWithSmallestDistance(unvisitedCities, distances);

            if (currentCity == null) {
                break;
            }

            if (currentCity.equals(destination)) {
                break;
            }

            unvisitedCities.remove(currentCity);
            updateNeighborDistances(graph, currentCity, distances, previousCities);
        }

        if (distances.get(destination) == Integer.MAX_VALUE) {
            throw new IllegalStateException("No route exists between the selected cities.");
        }

        List<City> path = buildPath(start, destination, previousCities);
        int totalTravelTime = distances.get(destination);

        return new RouteResult(path, totalTravelTime);
    }

    /**
     * Validates the input for the algorithm.
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
     * Initializes all distances with infinity, except the start city.
     *
     * @param graph the graph
     * @param start the start city
     * @param distances the distance map
     */
    private void initializeDistances(NightjetGraph graph, City start, Map<City, Integer> distances) {
        for (City city : graph.getCities()) {
            distances.put(city, Integer.MAX_VALUE);
        }

        distances.put(start, 0);
    }

    /**
     * Finds the unvisited city with the smallest currently known distance.
     *
     * @param unvisitedCities the list of unvisited cities
     * @param distances the current distance values
     * @return the city with the smallest distance, or null if no reachable city exists
     */
    private City findCityWithSmallestDistance(List<City> unvisitedCities, Map<City, Integer> distances) {
        City cityWithSmallestDistance = null;
        int smallestDistance = Integer.MAX_VALUE;

        for (City city : unvisitedCities) {
            int distance = distances.get(city);

            if (distance < smallestDistance) {
                smallestDistance = distance;
                cityWithSmallestDistance = city;
            }
        }

        return cityWithSmallestDistance;
    }

    /**
     * Updates the distances of all neighboring cities.
     *
     * @param graph the graph
     * @param currentCity the city currently processed
     * @param distances the current distance values
     * @param previousCities the previous city for each best known path
     */
    private void updateNeighborDistances(
            NightjetGraph graph,
            City currentCity,
            Map<City, Integer> distances,
            Map<City, City> previousCities
    ) {
        int currentDistance = distances.get(currentCity);

        if (currentDistance == Integer.MAX_VALUE) {
            return;
        }

        for (TrainConnection connection : graph.getConnectionsFrom(currentCity)) {
            City neighbor = connection.getOtherCity(currentCity);
            int newDistance = currentDistance + connection.getTravelTimeMinutes();

            if (newDistance < distances.get(neighbor)) {
                distances.put(neighbor, newDistance);
                previousCities.put(neighbor, currentCity);
            }
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
}