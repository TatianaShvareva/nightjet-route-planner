package at.hcw.nightjet.app;

import at.hcw.nightjet.algorithm.BfsAlgorithm;
import at.hcw.nightjet.algorithm.DijkstraAlgorithm;
import at.hcw.nightjet.algorithm.InsertionSort;
import at.hcw.nightjet.data.SampleData;
import at.hcw.nightjet.graph.NightjetGraph;
import at.hcw.nightjet.model.City;
import at.hcw.nightjet.model.RouteResult;
import at.hcw.nightjet.model.TrainConnection;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Console application for the Nightjet Route Planner.
 *
 * <p>The application allows the user to display cities and train connections,
 * find the fastest route, find a route with the fewest transfers, and sort
 * connections by travel time.</p>
 */
public class NightjetPlannerApp {
    private final NightjetGraph graph;
    private final DijkstraAlgorithm dijkstraAlgorithm;
    private final BfsAlgorithm bfsAlgorithm;
    private final InsertionSort insertionSort;
    private final Scanner scanner;

    /**
     * Creates the application with sample Nightjet data.
     */
    public NightjetPlannerApp() {
        this.graph = new SampleData().createSampleGraph();
        this.dijkstraAlgorithm = new DijkstraAlgorithm();
        this.bfsAlgorithm = new BfsAlgorithm();
        this.insertionSort = new InsertionSort();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts the console menu.
     */
    public void run() {
        boolean running = true;

        printWelcomeMessage();

        while (running) {
            printMenu();
            int choice = readMenuChoice();

            switch (choice) {
                case 1 -> showAllCities();
                case 2 -> showAllConnections();
                case 3 -> findFastestRoute();
                case 4 -> findRouteWithFewestTransfers();
                case 5 -> showConnectionsSortedByTravelTime();
                case 6 -> {
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please choose a number from 1 to 6.");
            }
        }
    }

    /**
     * Main method of the application.
     *
     * @param args command line arguments, not used in this application
     */
    public static void main(String[] args) {
        NightjetPlannerApp app = new NightjetPlannerApp();
        app.run();
    }

    /**
     * Prints the welcome message.
     */
    private void printWelcomeMessage() {
        System.out.println("======================================");
        System.out.println("Welcome to the Nightjet Route Planner");
        System.out.println("======================================");
        System.out.println("Note: This is a simplified Nightjet-inspired project.");
        System.out.println("It does not use real timetables or booking data.");
    }

    /**
     * Prints the main menu.
     */
    private void printMenu() {
        System.out.println();
        System.out.println("Please choose an option:");
        System.out.println("1. Show all cities");
        System.out.println("2. Show all train connections");
        System.out.println("3. Find fastest route");
        System.out.println("4. Find route with fewest transfers");
        System.out.println("5. Show connections sorted by travel time");
        System.out.println("6. Exit");
        System.out.print("Your choice: ");
    }

    /**
     * Reads the user's menu choice.
     *
     * @return the selected menu option, or -1 if the input is not a number
     */
    private int readMenuChoice() {
        String input = scanner.nextLine();

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            return -1;
        }
    }

    /**
     * Shows all cities in the graph.
     */
    private void showAllCities() {
        System.out.println();
        System.out.println("Available cities:");

        for (City city : graph.getCities()) {
            System.out.println("- " + city.getName());
        }
    }

    /**
     * Shows all train connections in the graph.
     */
    private void showAllConnections() {
        System.out.println();
        System.out.println("Available train connections:");

        for (TrainConnection connection : graph.getConnections()) {
            System.out.println("- " + connection);
        }
    }

    /**
     * Finds and prints the fastest route using Dijkstra's algorithm.
     */
    private void findFastestRoute() {
        System.out.println();
        System.out.println("Find fastest route");

        Optional<City> startCity = readCity("Start city: ");
        Optional<City> destinationCity = readCity("Destination city: ");

        if (startCity.isEmpty() || destinationCity.isEmpty()) {
            return;
        }

        try {
            RouteResult result = dijkstraAlgorithm.findFastestRoute(
                    graph,
                    startCity.get(),
                    destinationCity.get()
            );

            printRouteResult("Fastest route", result);
        } catch (IllegalStateException exception) {
            System.out.println("No route found between these cities.");
        }
    }

    /**
     * Finds and prints a route with the fewest transfers using BFS.
     */
    private void findRouteWithFewestTransfers() {
        System.out.println();
        System.out.println("Find route with fewest transfers");

        Optional<City> startCity = readCity("Start city: ");
        Optional<City> destinationCity = readCity("Destination city: ");

        if (startCity.isEmpty() || destinationCity.isEmpty()) {
            return;
        }

        try {
            RouteResult result = bfsAlgorithm.findRouteWithFewestTransfers(
                    graph,
                    startCity.get(),
                    destinationCity.get()
            );

            printRouteResult("Route with fewest transfers", result);
        } catch (IllegalStateException exception) {
            System.out.println("No route found between these cities.");
        }
    }

    /**
     * Shows all train connections sorted by travel time.
     */
    private void showConnectionsSortedByTravelTime() {
        System.out.println();
        System.out.println("Train connections sorted by travel time:");

        List<TrainConnection> sortedConnections =
                insertionSort.sortByTravelTime(graph.getConnections());

        for (TrainConnection connection : sortedConnections) {
            System.out.println("- " + connection);
        }
    }

    /**
     * Reads a city name from the user and searches it in the graph.
     *
     * @param prompt the text shown before user input
     * @return an Optional containing the city if found, otherwise an empty Optional
     */
    private Optional<City> readCity(String prompt) {
        System.out.print(prompt);
        String cityName = scanner.nextLine();

        Optional<City> city = graph.findCityByName(cityName);

        if (city.isEmpty()) {
            System.out.println("City not found: " + cityName);
            System.out.println("Use option 1 to see all available cities.");
        }

        return city;
    }

    /**
     * Prints a route result in a readable format.
     *
     * @param title the result title
     * @param result the route result
     */
    private void printRouteResult(String title, RouteResult result) {
        System.out.println();
        System.out.println(title + ":");
        System.out.println(result.formatPath());
        System.out.println("Total travel time: " + result.getTotalTravelTimeMinutes() + " minutes");
        System.out.println("Transfers: " + result.getTransfers());
    }
}