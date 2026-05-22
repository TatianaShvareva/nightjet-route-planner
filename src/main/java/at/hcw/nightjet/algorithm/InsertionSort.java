package at.hcw.nightjet.algorithm;

import at.hcw.nightjet.model.TrainConnection;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements Insertion Sort for train connections.
 *
 * <p>In this project, Insertion Sort is used to sort direct train connections
 * by their travel time. The algorithm is simple and suitable for small data sets.</p>
 */
public class InsertionSort {

    /**
     * Sorts train connections by travel time in ascending order.
     *
     * <p>The original list is not changed. A new sorted list is returned.</p>
     *
     * @param connections the train connections to sort
     * @return a new list sorted by travel time
     * @throws IllegalArgumentException if the list is null or contains null values
     */
    public List<TrainConnection> sortByTravelTime(List<TrainConnection> connections) {
        validateInput(connections);

        List<TrainConnection> sortedConnections = new ArrayList<>(connections);

        for (int i = 1; i < sortedConnections.size(); i++) {
            TrainConnection currentConnection = sortedConnections.get(i);
            int j = i - 1;

            while (j >= 0
                    && sortedConnections.get(j).getTravelTimeMinutes()
                    > currentConnection.getTravelTimeMinutes()) {
                sortedConnections.set(j + 1, sortedConnections.get(j));
                j--;
            }

            sortedConnections.set(j + 1, currentConnection);
        }

        return sortedConnections;
    }

    /**
     * Validates the input list.
     *
     * @param connections the list to validate
     */
    private void validateInput(List<TrainConnection> connections) {
        if (connections == null) {
            throw new IllegalArgumentException("Connection list must not be null.");
        }

        for (TrainConnection connection : connections) {
            if (connection == null) {
                throw new IllegalArgumentException("Connection list must not contain null values.");
            }
        }
    }
}