package at.hcw.nightjet.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Stores the result of a route search.
 *
 * <p>A route result contains the ordered list of cities in the route,
 * the total travel time and the number of transfers.</p>
 */
public class RouteResult {
    private final List<City> path;
    private final int totalTravelTimeMinutes;
    private final int transfers;

    /**
     * Creates a new route result.
     *
     * @param path the ordered list of cities in the route
     * @param totalTravelTimeMinutes the total travel time in minutes
     * @throws IllegalArgumentException if the path is null or empty,
     *                                  if the path contains null cities,
     *                                  or if the total travel time is negative
     */
    public RouteResult(List<City> path, int totalTravelTimeMinutes) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Path must not be null or empty.");
        }

        for (City city : path) {
            if (city == null) {
                throw new IllegalArgumentException("Path must not contain null cities.");
            }
        }

        if (totalTravelTimeMinutes < 0) {
            throw new IllegalArgumentException("Total travel time must not be negative.");
        }

        this.path = new ArrayList<>(path);
        this.totalTravelTimeMinutes = totalTravelTimeMinutes;
        this.transfers = calculateTransfers(path);
    }

    /**
     * Returns the ordered list of cities in the route.
     *
     * <p>The returned list cannot be changed from outside the class.</p>
     *
     * @return the route path
     */
    public List<City> getPath() {
        return Collections.unmodifiableList(path);
    }

    /**
     * Returns the total travel time of the route in minutes.
     *
     * @return the total travel time in minutes
     */
    public int getTotalTravelTimeMinutes() {
        return totalTravelTimeMinutes;
    }

    /**
     * Returns the number of transfers in the route.
     *
     * <p>A direct connection has zero transfers. A route with two train
     * connections has one transfer.</p>
     *
     * @return the number of transfers
     */
    public int getTransfers() {
        return transfers;
    }

    /**
     * Checks whether the route contains only one city.
     *
     * @return true if the route contains exactly one city, otherwise false
     */
    public boolean isSingleCityRoute() {
        return path.size() == 1;
    }

    /**
     * Formats the path as readable text.
     *
     * @return the route path as text
     */
    public String formatPath() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < path.size(); i++) {
            builder.append(path.get(i).getName());

            if (i < path.size() - 1) {
                builder.append(" -> ");
            }
        }

        return builder.toString();
    }

    /**
     * Calculates the number of transfers from the path length.
     *
     * @param path the route path
     * @return the number of transfers
     */
    private int calculateTransfers(List<City> path) {
        if (path.size() <= 2) {
            return 0;
        }

        return path.size() - 2;
    }

    /**
     * Returns the route result as readable text.
     *
     * @return a text representation of the route result
     */
    @Override
    public String toString() {
        return formatPath()
                + " | total time: "
                + totalTravelTimeMinutes
                + " min | transfers: "
                + transfers;
    }
}