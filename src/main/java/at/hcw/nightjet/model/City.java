package at.hcw.nightjet.model;

import java.util.Objects;

/**
 * Represents one city in the Nightjet route network.
 *
 * <p>In the graph model, a city is used as a vertex. Train connections
 * between cities are represented as edges in the graph.</p>
 */
public class City {
    private final String name;

    /**
     * Creates a new city with the given name.
     *
     * @param name the name of the city
     * @throws IllegalArgumentException if the name is null or empty
     */
    public City(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("City name must not be empty.");
        }
        this.name = name;
    }

    /**
     * Returns the name of the city.
     *
     * @return the city name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the city name as text.
     *
     * @return the city name
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Compares two cities by their names.
     *
     * <p>The comparison is case-insensitive, so "Vienna" and "vienna"
     * are treated as the same city.</p>
     *
     * @param obj the object to compare with this city
     * @return true if both objects represent the same city name, otherwise false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof City other)) {
            return false;
        }

        return name.equalsIgnoreCase(other.name);
    }

    /**
     * Returns a hash code based on the city name.
     *
     * <p>The name is converted to lowercase to match the case-insensitive
     * behavior of equals.</p>
     *
     * @return the hash code of the city
     */
    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }
}