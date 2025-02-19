package RobotPathPlanning;

public class Main {
    public static void main(String[] args) {
        System.out.println("========================================================");
        System.out.println("           WELCOME TO ROBOT NAVIGATION SYSTEM");
        System.out.println("========================================================");
        System.out.println("KEY:");
        System.out.println();
        System.out.println("The EMPTY space is denoted by •");
        System.out.println("The OBSTACLE is denoted by X");
        System.out.println("The ROBOT START position is denoted by R");
        System.out.println("The ROBOT END position is denoted by E");
        System.out.println("The PATH taken by the ROBOT is denoted by \u25A0");
        System.out.println("========================================================");
        System.out.println();

        Grid input = new Grid();
        input.simulation();
    }
}
