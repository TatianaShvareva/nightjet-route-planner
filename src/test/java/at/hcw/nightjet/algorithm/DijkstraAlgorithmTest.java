package at.hcw.nightjet.algorithm;

import at.hcw.nightjet.graph.NightjetGraph;
import at.hcw.nightjet.model.City;
import at.hcw.nightjet.model.RouteResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link DijkstraAlgorithm} class.
 */
class DijkstraAlgorithmTest {

    @Test
    void findFastestRouteShouldReturnDirectRouteIfItIsFastest() {
        NightjetGraph graph = new NightjetGraph();
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");

        graph.addConnection(vienna, salzburg, 180);

        DijkstraAlgorithm algorithm = new DijkstraAlgorithm();
        RouteResult result = algorithm.findFastestRoute(graph, vienna, salzburg);

        assertEquals("Vienna -> Salzburg", result.formatPath());
        assertEquals(180, result.getTotalTravelTimeMinutes());
        assertEquals(0, result.getTransfers());
    }

    @Test
    void findFastestRouteShouldReturnRouteWithSmallestTotalTravelTime() {
        NightjetGraph graph = new NightjetGraph();
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");
        City munich = new City("Munich");
        City hamburg = new City("Hamburg");

        graph.addConnection(vienna, hamburg, 800);
        graph.addConnection(vienna, salzburg, 180);
        graph.addConnection(salzburg, munich, 120);
        graph.addConnection(munich, hamburg, 390);

        DijkstraAlgorithm algorithm = new DijkstraAlgorithm();
        RouteResult result = algorithm.findFastestRoute(graph, vienna, hamburg);

        assertEquals("Vienna -> Salzburg -> Munich -> Hamburg", result.formatPath());
        assertEquals(690, result.getTotalTravelTimeMinutes());
        assertEquals(2, result.getTransfers());
    }

    @Test
    void findFastestRouteShouldReturnZeroTimeForSameStartAndDestination() {
        NightjetGraph graph = new NightjetGraph();
        City vienna = new City("Vienna");

        graph.addCity(vienna);

        DijkstraAlgorithm algorithm = new DijkstraAlgorithm();
        RouteResult result = algorithm.findFastestRoute(graph, vienna, vienna);

        assertEquals("Vienna", result.formatPath());
        assertEquals(0, result.getTotalTravelTimeMinutes());
        assertEquals(0, result.getTransfers());
        assertTrue(result.isSingleCityRoute());
    }

    @Test
    void findFastestRouteShouldThrowExceptionIfGraphIsNull() {
        DijkstraAlgorithm algorithm = new DijkstraAlgorithm();
        City vienna = new City("Vienna");
        City hamburg = new City("Hamburg");

        assertThrows(
                IllegalArgumentException.class,
                () -> algorithm.findFastestRoute(null, vienna, hamburg)
        );
    }

    @Test
    void findFastestRouteShouldThrowExceptionIfStartCityIsNull() {
        NightjetGraph graph = new NightjetGraph();
        City hamburg = new City("Hamburg");

        graph.addCity(hamburg);

        DijkstraAlgorithm algorithm = new DijkstraAlgorithm();

        assertThrows(
                IllegalArgumentException.class,
                () -> algorithm.findFastestRoute(graph, null, hamburg)
        );
    }

    @Test
    void findFastestRouteShouldThrowExceptionIfDestinationCityIsNull() {
        NightjetGraph graph = new NightjetGraph();
        City vienna = new City("Vienna");

        graph.addCity(vienna);

        DijkstraAlgorithm algorithm = new DijkstraAlgorithm();

        assertThrows(
                IllegalArgumentException.class,
                () -> algorithm.findFastestRoute(graph, vienna, null)
        );
    }

    @Test
    void findFastestRouteShouldThrowExceptionIfStartCityIsNotInGraph() {
        NightjetGraph graph = new NightjetGraph();
        City vienna = new City("Vienna");
        City hamburg = new City("Hamburg");

        graph.addCity(hamburg);

        DijkstraAlgorithm algorithm = new DijkstraAlgorithm();

        assertThrows(
                IllegalArgumentException.class,
                () -> algorithm.findFastestRoute(graph, vienna, hamburg)
        );
    }

    @Test
    void findFastestRouteShouldThrowExceptionIfDestinationCityIsNotInGraph() {
        NightjetGraph graph = new NightjetGraph();
        City vienna = new City("Vienna");
        City hamburg = new City("Hamburg");

        graph.addCity(vienna);

        DijkstraAlgorithm algorithm = new DijkstraAlgorithm();

        assertThrows(
                IllegalArgumentException.class,
                () -> algorithm.findFastestRoute(graph, vienna, hamburg)
        );
    }

    @Test
    void findFastestRouteShouldThrowExceptionIfNoRouteExists() {
        NightjetGraph graph = new NightjetGraph();
        City vienna = new City("Vienna");
        City hamburg = new City("Hamburg");

        graph.addCity(vienna);
        graph.addCity(hamburg);

        DijkstraAlgorithm algorithm = new DijkstraAlgorithm();

        assertThrows(
                IllegalStateException.class,
                () -> algorithm.findFastestRoute(graph, vienna, hamburg)
        );
    }
}