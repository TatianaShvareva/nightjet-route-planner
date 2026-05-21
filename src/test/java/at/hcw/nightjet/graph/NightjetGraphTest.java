package at.hcw.nightjet.graph;

import at.hcw.nightjet.model.City;
import at.hcw.nightjet.model.TrainConnection;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link NightjetGraph} class.
 */
class NightjetGraphTest {

    @Test
    void newGraphShouldBeEmpty() {
        NightjetGraph graph = new NightjetGraph();

        assertTrue(graph.isEmpty());
        assertTrue(graph.getCities().isEmpty());
        assertTrue(graph.getConnections().isEmpty());
    }

    @Test
    void addCityShouldAddCityToGraph() {
        NightjetGraph graph = new NightjetGraph();
        City vienna = new City("Vienna");

        graph.addCity(vienna);

        assertTrue(graph.containsCity(vienna));
        assertEquals(1, graph.getCities().size());
    }

    @Test
    void addCityShouldNotAddDuplicateCity() {
        NightjetGraph graph = new NightjetGraph();

        graph.addCity(new City("Vienna"));
        graph.addCity(new City("vienna"));

        assertEquals(1, graph.getCities().size());
    }

    @Test
    void addCityShouldThrowExceptionForNullCity() {
        NightjetGraph graph = new NightjetGraph();

        assertThrows(
                IllegalArgumentException.class,
                () -> graph.addCity(null)
        );
    }

    @Test
    void addConnectionShouldAddBothCitiesAndConnection() {
        NightjetGraph graph = new NightjetGraph();
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");

        graph.addConnection(vienna, salzburg, 180);

        assertTrue(graph.containsCity(vienna));
        assertTrue(graph.containsCity(salzburg));
        assertEquals(2, graph.getCities().size());
        assertEquals(1, graph.getConnections().size());
    }

    @Test
    void addConnectionShouldNotAddDuplicateConnection() {
        NightjetGraph graph = new NightjetGraph();
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");

        graph.addConnection(vienna, salzburg, 180);
        graph.addConnection(salzburg, vienna, 180);

        assertEquals(1, graph.getConnections().size());
    }

    @Test
    void addConnectionShouldThrowExceptionForInvalidConnection() {
        NightjetGraph graph = new NightjetGraph();
        City vienna = new City("Vienna");

        assertThrows(
                IllegalArgumentException.class,
                () -> graph.addConnection(vienna, vienna, 100)
        );
    }

    @Test
    void getConnectionsFromShouldReturnConnectionsOfCity() {
        NightjetGraph graph = new NightjetGraph();
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");
        City munich = new City("Munich");

        graph.addConnection(vienna, salzburg, 180);
        graph.addConnection(salzburg, munich, 120);

        List<TrainConnection> connectionsFromSalzburg = graph.getConnectionsFrom(salzburg);

        assertEquals(2, connectionsFromSalzburg.size());
    }

    @Test
    void getConnectionsFromShouldReturnEmptyListForCityWithoutConnections() {
        NightjetGraph graph = new NightjetGraph();
        City vienna = new City("Vienna");

        graph.addCity(vienna);

        assertTrue(graph.getConnectionsFrom(vienna).isEmpty());
    }

    @Test
    void getConnectionsFromShouldThrowExceptionForNullCity() {
        NightjetGraph graph = new NightjetGraph();

        assertThrows(
                IllegalArgumentException.class,
                () -> graph.getConnectionsFrom(null)
        );
    }

    @Test
    void getNeighborsShouldReturnNeighboringCities() {
        NightjetGraph graph = new NightjetGraph();
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");
        City munich = new City("Munich");

        graph.addConnection(vienna, salzburg, 180);
        graph.addConnection(salzburg, munich, 120);

        List<City> neighbors = graph.getNeighbors(salzburg);

        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains(vienna));
        assertTrue(neighbors.contains(munich));
    }

    @Test
    void getNeighborsShouldThrowExceptionForNullCity() {
        NightjetGraph graph = new NightjetGraph();

        assertThrows(
                IllegalArgumentException.class,
                () -> graph.getNeighbors(null)
        );
    }

    @Test
    void findCityByNameShouldReturnCityIfItExists() {
        NightjetGraph graph = new NightjetGraph();
        City vienna = new City("Vienna");

        graph.addCity(vienna);

        Optional<City> result = graph.findCityByName("Vienna");

        assertTrue(result.isPresent());
        assertEquals(vienna, result.get());
    }

    @Test
    void findCityByNameShouldIgnoreCase() {
        NightjetGraph graph = new NightjetGraph();
        City vienna = new City("Vienna");

        graph.addCity(vienna);

        Optional<City> result = graph.findCityByName("vienna");

        assertTrue(result.isPresent());
        assertEquals(vienna, result.get());
    }

    @Test
    void findCityByNameShouldReturnEmptyOptionalIfCityDoesNotExist() {
        NightjetGraph graph = new NightjetGraph();

        Optional<City> result = graph.findCityByName("Hamburg");

        assertTrue(result.isEmpty());
    }

    @Test
    void findCityByNameShouldThrowExceptionForBlankName() {
        NightjetGraph graph = new NightjetGraph();

        assertThrows(
                IllegalArgumentException.class,
                () -> graph.findCityByName("   ")
        );
    }

    @Test
    void hasConnectionShouldReturnTrueForExistingConnection() {
        NightjetGraph graph = new NightjetGraph();
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");

        graph.addConnection(vienna, salzburg, 180);

        assertTrue(graph.hasConnection(vienna, salzburg));
    }

    @Test
    void hasConnectionShouldReturnTrueForOppositeDirection() {
        NightjetGraph graph = new NightjetGraph();
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");

        graph.addConnection(vienna, salzburg, 180);

        assertTrue(graph.hasConnection(salzburg, vienna));
    }

    @Test
    void hasConnectionShouldReturnFalseForMissingConnection() {
        NightjetGraph graph = new NightjetGraph();
        City vienna = new City("Vienna");
        City hamburg = new City("Hamburg");

        graph.addCity(vienna);
        graph.addCity(hamburg);

        assertFalse(graph.hasConnection(vienna, hamburg));
    }

    @Test
    void returnedCitiesListShouldBeUnmodifiable() {
        NightjetGraph graph = new NightjetGraph();
        graph.addCity(new City("Vienna"));

        assertThrows(
                UnsupportedOperationException.class,
                () -> graph.getCities().add(new City("Hamburg"))
        );
    }

    @Test
    void returnedConnectionsListShouldBeUnmodifiable() {
        NightjetGraph graph = new NightjetGraph();
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");

        graph.addConnection(vienna, salzburg, 180);

        assertThrows(
                UnsupportedOperationException.class,
                () -> graph.getConnections().clear()
        );
    }
}