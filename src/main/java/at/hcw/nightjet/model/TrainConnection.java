package at.hcw.nightjet.model;

/**
 * Represents a direct train connection between two cities.
 *
 * <p>In the weighted graph model, a train connection is used as an edge.
 * The travel time is the weight of this edge.</p>
 */
public class TrainConnection {
    private final City from;
    private final City to;
    private final int travelTimeMinutes;

    /**
     * Creates a new train connection between two cities.
     *
     * @param from the start city of the connection
     * @param to the destination city of the connection
     * @param travelTimeMinutes the travel time in minutes
     * @throws IllegalArgumentException if one city is null, both cities are equal,
     *                                  or the travel time is not positive
     */
    public TrainConnection(City from, City to, int travelTimeMinutes) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Cities must not be null.");
        }

        if (from.equals(to)) {
            throw new IllegalArgumentException("A connection must link two different cities.");
        }

        if (travelTimeMinutes <= 0) {
            throw new IllegalArgumentException("Travel time must be positive.");
        }

        this.from = from;
        this.to = to;
        this.travelTimeMinutes = travelTimeMinutes;
    }

    /**
     * Returns the start city of this connection.
     *
     * @return the start city
     */
    public City getFrom() {
        return from;
    }

    /**
     * Returns the destination city of this connection.
     *
     * @return the destination city
     */
    public City getTo() {
        return to;
    }

    /**
     * Returns the travel time of this connection in minutes.
     *
     * @return the travel time in minutes
     */
    public int getTravelTimeMinutes() {
        return travelTimeMinutes;
    }

    /**
     * Returns the other city of this connection.
     *
     * <p>This method is useful for undirected graph traversal. If the given city
     * is the start city, the destination city is returned. If the given city is
     * the destination city, the start city is returned.</p>
     *
     * @param city one city of this connection
     * @return the other city of the connection
     * @throws IllegalArgumentException if the given city is null or not part of this connection
     */
    public City getOtherCity(City city) {
        if (city == null) {
            throw new IllegalArgumentException("City must not be null.");
        }

        if (city.equals(from)) {
            return to;
        }

        if (city.equals(to)) {
            return from;
        }

        throw new IllegalArgumentException("City is not part of this connection.");
    }

    /**
     * Returns this connection as readable text.
     *
     * @return a text representation of the connection
     */
    @Override
    public String toString() {
        return from + " <-> " + to + " | " + travelTimeMinutes + " min";
    }
}