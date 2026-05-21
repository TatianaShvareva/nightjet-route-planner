package at.hcw.nightjet.graph;

import at.hcw.nightjet.model.City;
import at.hcw.nightjet.model.TrainConnection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Represents the Nightjet train network as a weighted undirected graph.
 *
 * <p>In this graph, cities are vertices and direct train connections are edges.
 * The travel time of a connection is used as the edge weight.</p>
 */
public class NightjetGraph {
    private final List<City> cities;
    private final List<TrainConnection> connections;

    /**
     * Creates an empty Nightjet graph.
     */
    public NightjetGraph() {
        this.cities = new ArrayList<>();
        this.connections = new ArrayList<>();
    }

    /**
     * Adds a city to the graph.
     *
     * <p>If a city with the same name already exists, it is not added again.
     * City comparison is case-insensitive because the City class defines it this way.</p>
     *
     * @param city the city to add
     * @throws IllegalArgumentException if the city is null
     */
    public void addCity(City city) {
        if (city == null) {
            throw new IllegalArgumentException("City must not be null.");
        }

        if (!cities.contains(city)) {
            cities.add(city);
        }
    }

    /**
     * Adds a direct train connection between two cities.
     *
     * <p>The graph is undirected, so a connection from Vienna to Salzburg can also
     * be used as a connection from Salzburg to Vienna.</p>
     *
     * @param from the start city
     * @param to the destination city
     * @param travelTimeMinutes the travel time in minutes
     * @throws IllegalArgumentException if one city is null, both cities are equal,
     *                                  or the travel time is not positive
     */
    public void addConnection(City from, City to, int travelTimeMinutes) {
        TrainConnection connection = new TrainConnection(from, to, travelTimeMinutes);

        addCity(from);
        addCity(to);

        if (!hasConnection(from, to)) {
            connections.add(connection);
        }
    }

    /**
     * Returns all cities in the graph.
     *
     * <p>The returned list cannot be changed from outside the class.</p>
     *
     * @return all cities
     */
    public List<City> getCities() {
        return Collections.unmodifiableList(cities);
    }

    /**
     * Returns all train connections in the graph.
     *
     * <p>The returned list cannot be changed from outside the class.</p>
     *
     * @return all train connections
     */
    public List<TrainConnection> getConnections() {
        return Collections.unmodifiableList(connections);
    }

    /**
     * Returns all direct train connections of a city.
     *
     * @param city the city whose connections should be returned
     * @return all neighboring connections of the city
     * @throws IllegalArgumentException if the city is null
     */
    public List<TrainConnection> getConnectionsFrom(City city) {
        if (city == null) {
            throw new IllegalArgumentException("City must not be null.");
        }

        List<TrainConnection> result = new ArrayList<>();

        for (TrainConnection connection : connections) {
            if (connection.getFrom().equals(city) || connection.getTo().equals(city)) {
                result.add(connection);
            }
        }

        return result;
    }

    /**
     * Returns all neighboring cities of a city.
     *
     * @param city the city whose neighbors should be returned
     * @return all neighboring cities
     * @throws IllegalArgumentException if the city is null
     */
    public List<City> getNeighbors(City city) {
        if (city == null) {
            throw new IllegalArgumentException("City must not be null.");
        }

        List<City> neighbors = new ArrayList<>();

        for (TrainConnection connection : getConnectionsFrom(city)) {
            neighbors.add(connection.getOtherCity(city));
        }

        return neighbors;
    }

    /**
     * Finds a city by its name.
     *
     * <p>The search is case-insensitive.</p>
     *
     * @param name the city name
     * @return an Optional containing the city if it exists, otherwise an empty Optional
     * @throws IllegalArgumentException if the name is null or empty
     */
    public Optional<City> findCityByName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("City name must not be empty.");
        }

        for (City city : cities) {
            if (city.getName().equalsIgnoreCase(name)) {
                return Optional.of(city);
            }
        }

        return Optional.empty();
    }

    /**
     * Checks whether the graph contains a city.
     *
     * @param city the city to check
     * @return true if the city exists in the graph, otherwise false
     */
    public boolean containsCity(City city) {
        return cities.contains(city);
    }

    /**
     * Checks whether a direct connection between two cities exists.
     *
     * <p>The direction does not matter because the graph is undirected.</p>
     *
     * @param firstCity the first city
     * @param secondCity the second city
     * @return true if a direct connection exists, otherwise false
     */
    public boolean hasConnection(City firstCity, City secondCity) {
        for (TrainConnection connection : connections) {
            boolean sameDirection = connection.getFrom().equals(firstCity)
                    && connection.getTo().equals(secondCity);

            boolean oppositeDirection = connection.getFrom().equals(secondCity)
                    && connection.getTo().equals(firstCity);

            if (sameDirection || oppositeDirection) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks whether the graph has no cities and no connections.
     *
     * @return true if the graph is empty, otherwise false
     */
    public boolean isEmpty() {
        return cities.isEmpty() && connections.isEmpty();
    }
}