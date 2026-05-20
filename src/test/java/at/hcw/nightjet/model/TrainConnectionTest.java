package at.hcw.nightjet.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link TrainConnection} class.
 */
class TrainConnectionTest {

    @Test
    void constructorShouldStoreConnectionData() {
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");

        TrainConnection connection = new TrainConnection(vienna, salzburg, 180);

        assertEquals(vienna, connection.getFrom());
        assertEquals(salzburg, connection.getTo());
        assertEquals(180, connection.getTravelTimeMinutes());
    }

    @Test
    void constructorShouldThrowExceptionIfFromCityIsNull() {
        City salzburg = new City("Salzburg");

        assertThrows(
                IllegalArgumentException.class,
                () -> new TrainConnection(null, salzburg, 180)
        );
    }

    @Test
    void constructorShouldThrowExceptionIfToCityIsNull() {
        City vienna = new City("Vienna");

        assertThrows(
                IllegalArgumentException.class,
                () -> new TrainConnection(vienna, null, 180)
        );
    }

    @Test
    void constructorShouldThrowExceptionIfBothCitiesAreEqual() {
        City vienna = new City("Vienna");

        assertThrows(
                IllegalArgumentException.class,
                () -> new TrainConnection(vienna, new City("vienna"), 180)
        );
    }

    @Test
    void constructorShouldThrowExceptionIfTravelTimeIsZero() {
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");

        assertThrows(
                IllegalArgumentException.class,
                () -> new TrainConnection(vienna, salzburg, 0)
        );
    }

    @Test
    void constructorShouldThrowExceptionIfTravelTimeIsNegative() {
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");

        assertThrows(
                IllegalArgumentException.class,
                () -> new TrainConnection(vienna, salzburg, -10)
        );
    }

    @Test
    void getOtherCityShouldReturnToCityWhenFromCityIsGiven() {
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");
        TrainConnection connection = new TrainConnection(vienna, salzburg, 180);

        assertEquals(salzburg, connection.getOtherCity(vienna));
    }

    @Test
    void getOtherCityShouldReturnFromCityWhenToCityIsGiven() {
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");
        TrainConnection connection = new TrainConnection(vienna, salzburg, 180);

        assertEquals(vienna, connection.getOtherCity(salzburg));
    }

    @Test
    void getOtherCityShouldThrowExceptionForNullCity() {
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");
        TrainConnection connection = new TrainConnection(vienna, salzburg, 180);

        assertThrows(
                IllegalArgumentException.class,
                () -> connection.getOtherCity(null)
        );
    }

    @Test
    void getOtherCityShouldThrowExceptionForCityNotInConnection() {
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");
        City hamburg = new City("Hamburg");
        TrainConnection connection = new TrainConnection(vienna, salzburg, 180);

        assertThrows(
                IllegalArgumentException.class,
                () -> connection.getOtherCity(hamburg)
        );
    }

    @Test
    void toStringShouldReturnReadableConnectionText() {
        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");
        TrainConnection connection = new TrainConnection(vienna, salzburg, 180);

        assertEquals("Vienna <-> Salzburg | 180 min", connection.toString());
    }
}