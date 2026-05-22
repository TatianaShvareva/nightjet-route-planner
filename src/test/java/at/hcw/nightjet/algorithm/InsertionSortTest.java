package at.hcw.nightjet.algorithm;

import at.hcw.nightjet.model.City;
import at.hcw.nightjet.model.TrainConnection;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link InsertionSort} class.
 */
class InsertionSortTest {

    @Test
    void sortByTravelTimeShouldSortConnectionsInAscendingOrder() {
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");
        City munich = new City("Munich");
        City hamburg = new City("Hamburg");

        TrainConnection longConnection = new TrainConnection(vienna, hamburg, 800);
        TrainConnection shortConnection = new TrainConnection(salzburg, munich, 120);
        TrainConnection middleConnection = new TrainConnection(vienna, salzburg, 180);

        List<TrainConnection> connections = List.of(
                longConnection,
                shortConnection,
                middleConnection
        );

        InsertionSort insertionSort = new InsertionSort();
        List<TrainConnection> sortedConnections = insertionSort.sortByTravelTime(connections);

        assertEquals(shortConnection, sortedConnections.get(0));
        assertEquals(middleConnection, sortedConnections.get(1));
        assertEquals(longConnection, sortedConnections.get(2));
    }

    @Test
    void sortByTravelTimeShouldKeepAlreadySortedList() {
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");
        City munich = new City("Munich");

        TrainConnection firstConnection = new TrainConnection(vienna, salzburg, 100);
        TrainConnection secondConnection = new TrainConnection(salzburg, munich, 200);

        List<TrainConnection> connections = List.of(firstConnection, secondConnection);

        InsertionSort insertionSort = new InsertionSort();
        List<TrainConnection> sortedConnections = insertionSort.sortByTravelTime(connections);

        assertEquals(firstConnection, sortedConnections.get(0));
        assertEquals(secondConnection, sortedConnections.get(1));
    }

    @Test
    void sortByTravelTimeShouldReturnEmptyListForEmptyInput() {
        InsertionSort insertionSort = new InsertionSort();

        List<TrainConnection> sortedConnections = insertionSort.sortByTravelTime(List.of());

        assertTrue(sortedConnections.isEmpty());
    }

    @Test
    void sortByTravelTimeShouldReturnOneElementList() {
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");
        TrainConnection connection = new TrainConnection(vienna, salzburg, 180);

        InsertionSort insertionSort = new InsertionSort();
        List<TrainConnection> sortedConnections = insertionSort.sortByTravelTime(List.of(connection));

        assertEquals(1, sortedConnections.size());
        assertEquals(connection, sortedConnections.get(0));
    }

    @Test
    void sortByTravelTimeShouldNotChangeOriginalList() {
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");
        City munich = new City("Munich");

        TrainConnection longConnection = new TrainConnection(vienna, munich, 300);
        TrainConnection shortConnection = new TrainConnection(vienna, salzburg, 100);

        List<TrainConnection> originalConnections = new java.util.ArrayList<>();
        originalConnections.add(longConnection);
        originalConnections.add(shortConnection);

        InsertionSort insertionSort = new InsertionSort();
        List<TrainConnection> sortedConnections = insertionSort.sortByTravelTime(originalConnections);

        assertEquals(longConnection, originalConnections.get(0));
        assertEquals(shortConnection, originalConnections.get(1));

        assertEquals(shortConnection, sortedConnections.get(0));
        assertEquals(longConnection, sortedConnections.get(1));
    }

    @Test
    void sortByTravelTimeShouldThrowExceptionIfListIsNull() {
        InsertionSort insertionSort = new InsertionSort();

        assertThrows(
                IllegalArgumentException.class,
                () -> insertionSort.sortByTravelTime(null)
        );
    }

    @Test
    void sortByTravelTimeShouldThrowExceptionIfListContainsNull() {
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");
        TrainConnection connection = new TrainConnection(vienna, salzburg, 180);

        List<TrainConnection> connections = java.util.Arrays.asList(connection, null);

        InsertionSort insertionSort = new InsertionSort();

        assertThrows(
                IllegalArgumentException.class,
                () -> insertionSort.sortByTravelTime(connections)
        );
    }
}