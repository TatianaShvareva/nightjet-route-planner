package at.hcw.nightjet.algorithm;

import at.hcw.nightjet.graph.NightjetGraph;
import at.hcw.nightjet.model.City;
import at.hcw.nightjet.model.RouteResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link BfsAlgorithm} class.
 */
class BfsAlgorithmTest {

    @Test
    void findRouteWithFewestTransfersShouldReturnDirectRoute() {
        NightjetGraph graph = new NightjetGraph();
        City vienna = new City("Vienna");
        City hamburg = new City("Hamburg");

        graph.addConnection(vienna, hamburg, 800);

        BfsAlgorithm algorithm = new BfsAlgorithm();
        RouteResult result = algorithm.findRouteWithFewestTransfers(graph, vienna, hamburg);

        assertEquals("Vienna -> Hamburg", result.formatPath());
        assertEquals(800, result.getTotalTravelTimeMinutes());
        assertEquals(0, result.getTransfers());
    }

    @Test
    void findRouteWithFewestTransfersShouldPreferFewerConnectionsOverShorterTravelTime() {
        NightjetGraph graph = new NightjetGraph();
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");
        City munich = new City("Munich");
        City hamburg = new City("Hamburg");

        graph.addConnection(vienna, hamburg, 800);
        graph.addConnection(vienna, salzburg, 180);
        graph.addConnection(salzburg, munich, 120);
        graph.addConnection(munich, hamburg, 390);

        BfsAlgorithm algorithm = new BfsAlgorithm();
        RouteResult result = algorithm.findRouteWithFewestTransfers(graph, vienna, hamburg);

        assertEquals("Vienna -> Hamburg", result.formatPath());
        assertEquals(800, result.getTotalTravelTimeMinutes());
        assertEquals(0, result.getTransfers());
    }

    @Test
    void findRouteWithFewestTransfersShouldReturnZeroTimeForSameStartAndDestination() {
        NightjetGraph graph = new NightjetGraph();
        City vienna = new City("Vienna");

        graph.addCity(vienna);

        BfsAlgorithm algorithm = new BfsAlgorithm();
        RouteResult result = algorithm.findRouteWithFewestTransfers(graph, vienna, vienna);

        assertEquals("Vienna", result.formatPath());
        assertEquals(0, result.getTotalTravelTimeMinutes());
        assertEquals(0, result.getTransfers());
        assertTrue(result.isSingleCityRoute());
    }

    @Test
    void findRouteWithFewestTransfersShouldThrowExceptionIfGraphIsNull() {
        BfsAlgorithm algorithm = new BfsAlgorithm();
        City vienna = new City("Vienna");
        City hamburg = new City("Hamburg");

        assertThrows(
                IllegalArgumentException.class,
                () -> algorithm.findRouteWithFewestTransfers(null, vienna, hamburg)
        );
    }

    @Test
    void findRouteWithFewestTransfersShouldThrowExceptionIfStartCityIsNull() {
        NightjetGraph graph = new NightjetGraph();
        City hamburg = new City("Hamburg");

        graph.addCity(hamburg);

        BfsAlgorithm algorithm = new BfsAlgorithm();

        assertThrows(
                IllegalArgumentException.class,
                () -> algorithm.findRouteWithFewestTransfers(graph, null, hamburg)
        );
    }

    @Test
    void findRouteWithFewestTransfersShouldThrowExceptionIfDestinationCityIsNull() {
        NightjetGraph graph = new NightjetGraph();
        City vienna = new City("Vienna");

        graph.addCity(vienna);

        BfsAlgorithm algorithm = new BfsAlgorithm();

        assertThrows(
                IllegalArgumentException.class,
                () -> algorithm.findRouteWithFewestTransfers(graph, vienna, null)
        );
    }

    @Test
    void findRouteWithFewestTransfersShouldThrowExceptionIfStartCityIsNotInGraph() {
        NightjetGraph graph = new NightjetGraph();
        City vienna = new City("Vienna");
        City hamburg = new City("Hamburg");

        graph.addCity(hamburg);

        BfsAlgorithm algorithm = new BfsAlgorithm();

        assertThrows(
                IllegalArgumentException.class,
                () -> algorithm.findRouteWithFewestTransfers(graph, vienna, hamburg)
        );
    }

    @Test
    void findRouteWithFewestTransfersShouldThrowExceptionIfDestinationCityIsNotInGraph() {
        NightjetGraph graph = new NightjetGraph();
        City vienna = new City("Vienna");
        City hamburg = new City("Hamburg");

        graph.addCity(vienna);

        BfsAlgorithm algorithm = new BfsAlgorithm();

        assertThrows(
                IllegalArgumentException.class,
                () -> algorithm.findRouteWithFewestTransfers(graph, vienna, hamburg)
        );
    }

    @Test
    void findRouteWithFewestTransfersShouldThrowExceptionIfNoRouteExists() {
        NightjetGraph graph = new NightjetGraph();
        City vienna = new City("Vienna");
        City hamburg = new City("Hamburg");

        graph.addCity(vienna);
        graph.addCity(hamburg);

        BfsAlgorithm algorithm = new BfsAlgorithm();

        assertThrows(
                IllegalStateException.class,
                () -> algorithm.findRouteWithFewestTransfers(graph, vienna, hamburg)
        );
    }
}