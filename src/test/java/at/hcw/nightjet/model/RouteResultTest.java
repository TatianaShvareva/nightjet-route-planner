package at.hcw.nightjet.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link RouteResult} class.
 */
class RouteResultTest {

    @Test
    void constructorShouldStoreRouteData() {
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");

        RouteResult result = new RouteResult(List.of(vienna, salzburg), 180);

        assertEquals(List.of(vienna, salzburg), result.getPath());
        assertEquals(180, result.getTotalTravelTimeMinutes());
        assertEquals(0, result.getTransfers());
    }

    @Test
    void constructorShouldThrowExceptionForNullPath() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new RouteResult(null, 0)
        );
    }

    @Test
    void constructorShouldThrowExceptionForEmptyPath() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new RouteResult(List.of(), 0)
        );
    }

    @Test
    void constructorShouldThrowExceptionIfPathContainsNull() {
        City vienna = new City("Vienna");

        assertThrows(
                IllegalArgumentException.class,
                () -> new RouteResult(java.util.Arrays.asList(vienna, null), 100)
        );
    }

    @Test
    void constructorShouldThrowExceptionForNegativeTravelTime() {
        City vienna = new City("Vienna");

        assertThrows(
                IllegalArgumentException.class,
                () -> new RouteResult(List.of(vienna), -1)
        );
    }

    @Test
    void getPathShouldReturnUnmodifiableList() {
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");
        RouteResult result = new RouteResult(List.of(vienna, salzburg), 180);

        assertThrows(
                UnsupportedOperationException.class,
                () -> result.getPath().add(new City("Hamburg"))
        );
    }

    @Test
    void directRouteShouldHaveZeroTransfers() {
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");

        RouteResult result = new RouteResult(List.of(vienna, salzburg), 180);

        assertEquals(0, result.getTransfers());
    }

    @Test
    void routeWithThreeCitiesShouldHaveOneTransfer() {
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");
        City munich = new City("Munich");

        RouteResult result = new RouteResult(List.of(vienna, salzburg, munich), 300);

        assertEquals(1, result.getTransfers());
    }

    @Test
    void routeWithFourCitiesShouldHaveTwoTransfers() {
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");
        City munich = new City("Munich");
        City hamburg = new City("Hamburg");

        RouteResult result = new RouteResult(
                List.of(vienna, salzburg, munich, hamburg),
                690
        );

        assertEquals(2, result.getTransfers());
    }

    @Test
    void singleCityRouteShouldBeDetected() {
        City vienna = new City("Vienna");

        RouteResult result = new RouteResult(List.of(vienna), 0);

        assertTrue(result.isSingleCityRoute());
    }

    @Test
    void routeWithMoreThanOneCityShouldNotBeSingleCityRoute() {
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");

        RouteResult result = new RouteResult(List.of(vienna, salzburg), 180);

        assertFalse(result.isSingleCityRoute());
    }

    @Test
    void formatPathShouldReturnReadableRoute() {
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");
        City munich = new City("Munich");

        RouteResult result = new RouteResult(List.of(vienna, salzburg, munich), 300);

        assertEquals("Vienna -> Salzburg -> Munich", result.formatPath());
    }

    @Test
    void toStringShouldReturnReadableRouteResult() {
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");

        RouteResult result = new RouteResult(List.of(vienna, salzburg), 180);

        assertEquals(
                "Vienna -> Salzburg | total time: 180 min | transfers: 0",
                result.toString()
        );
    }
}