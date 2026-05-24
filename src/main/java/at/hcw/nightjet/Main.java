package at.hcw.nightjet;

import at.hcw.nightjet.app.NightjetPlannerApp;

/**
 * Entry point of the Nightjet Route Planner application.
 */
public class Main {

    /**
     * Starts the console application.
     *
     * @param args command line arguments, not used in this application
     */
    public static void main(String[] args) {
        NightjetPlannerApp app = new NightjetPlannerApp();
        app.run();
    }
}