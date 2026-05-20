package at.hcw.nightjet.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link City} class.
 */
class CityTest {

    @Test
    void constructorShouldStoreCityName() {
        City city = new City("Vienna");

        assertEquals("Vienna", city.getName());
    }

    @Test
    void constructorShouldThrowExceptionForNullName() {
        assertThrows(IllegalArgumentException.class, () -> new City(null));
    }

    @Test
    void constructorShouldThrowExceptionForEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> new City(""));
    }

    @Test
    void constructorShouldThrowExceptionForBlankName() {
        assertThrows(IllegalArgumentException.class, () -> new City("   "));
    }

    @Test
    void toStringShouldReturnCityName() {
        City city = new City("Salzburg");

        assertEquals("Salzburg", city.toString());
    }

    @Test
    void citiesWithSameNameShouldBeEqual() {
        City firstCity = new City("Vienna");
        City secondCity = new City("Vienna");

        assertEquals(firstCity, secondCity);
    }

    @Test
    void citiesWithSameNameButDifferentCaseShouldBeEqual() {
        City firstCity = new City("Vienna");
        City secondCity = new City("vienna");

        assertEquals(firstCity, secondCity);
    }

    @Test
    void citiesWithDifferentNamesShouldNotBeEqual() {
        City firstCity = new City("Vienna");
        City secondCity = new City("Hamburg");

        assertNotEquals(firstCity, secondCity);
    }

    @Test
    void equalCitiesShouldHaveSameHashCode() {
        City firstCity = new City("Vienna");
        City secondCity = new City("vienna");

        assertEquals(firstCity.hashCode(), secondCity.hashCode());
    }

    @Test
    void cityShouldNotBeEqualToNull() {
        City city = new City("Vienna");

        assertNotEquals(null, city);
    }

    @Test
    void cityShouldNotBeEqualToDifferentObjectType() {
        City city = new City("Vienna");

        assertNotEquals("Vienna", city);
    }
}