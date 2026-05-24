package at.hcw.nightjet.data;

import at.hcw.nightjet.graph.NightjetGraph;
import at.hcw.nightjet.model.City;

/**
 * Creates sample data for the Nightjet Route Planner.
 *
 * <p>The data is simplified and inspired by real Nightjet destinations.
 * It is not an official timetable.</p>
 */
public class SampleData {

    /**
     * Creates a sample Nightjet train network.
     *
     * @return a NightjetGraph with predefined cities and train connections
     */
    public NightjetGraph createSampleGraph() {
        NightjetGraph graph = new NightjetGraph();

        City vienna = new City("Vienna");
        City salzburg = new City("Salzburg");
        City linz = new City("Linz");
        City graz = new City("Graz");
        City innsbruck = new City("Innsbruck");
        City munich = new City("Munich");
        City hamburg = new City("Hamburg");
        City berlin = new City("Berlin");
        City zurich = new City("Zurich");
        City amsterdam = new City("Amsterdam");
        City brussels = new City("Brussels");
        City venice = new City("Venice");
        City rome = new City("Rome");

        graph.addConnection(vienna, salzburg, 180);
        graph.addConnection(vienna, linz, 100);
        graph.addConnection(linz, salzburg, 80);
        graph.addConnection(salzburg, munich, 120);
        graph.addConnection(munich, hamburg, 390);
        graph.addConnection(munich, berlin, 300);
        graph.addConnection(berlin, hamburg, 120);

        graph.addConnection(vienna, zurich, 600);
        graph.addConnection(zurich, amsterdam, 520);
        graph.addConnection(amsterdam, hamburg, 300);
        graph.addConnection(amsterdam, brussels, 180);

        graph.addConnection(vienna, venice, 480);
        graph.addConnection(venice, rome, 390);

        graph.addConnection(graz, vienna, 150);
        graph.addConnection(innsbruck, zurich, 220);
        graph.addConnection(innsbruck, munich, 160);

        return graph;
    }
}