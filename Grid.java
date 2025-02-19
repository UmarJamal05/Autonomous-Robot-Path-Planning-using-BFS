package RobotPathPlanning;

import java.util.Random;
import java.util.Scanner;

public class Grid {
    private static final char OBSTACLE = 'X';
    private static final char EMPTY = '•';
    private static final char PATH = '\u25A0';
    private char[][] grid;
    private int rows;
    private int cols;

    // Method to run the simulation
    public void simulation() {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false; // for validation checks

        System.out.println("Enter grid dimensions (first rows, next line columns): ");
        int rows = 0;
        int cols = 0;

        boolean condition =  true;

        // Take input for grid dimensions
        do {
            try {
                rows = Integer.parseInt(scanner.next());
                cols = Integer.parseInt(scanner.next());
                validInput = true;  // if all good -> validInput is set to true
                if (rows < 3 || cols < 3){
                    System.out.println("You should enter 3 rows and 3 columns or more than that.");
                } else {
                    condition = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter integer values for rows and columns.");
            }
        } while (!validInput || condition);

        System.out.println("--------------------------------------------------------");

        Grid planner = new Grid(rows, cols);
        planner.initializeGrid();
        planner.printGrid();

        System.out.println("--------------------------------------------------------");
        System.out.println();

        RobotPosition input = new RobotPosition();

        int startRow=2;
        int startColumn=2;
        int endRow=2;
        int endColumn=2;

        // Input start row
        System.out.println("Enter the start row of the robot:");
        validInput = false;
        do {
            try {
                startRow = Integer.parseInt(scanner.next());
                if (startRow < 1 || startRow > rows) {  // check if within the bounds
                    System.out.println("Start row must be within the range 1 to " + rows + ".");
                } else {
                    validInput = true;

                    if (isRowFullOfObstacles(planner.grid, startRow - 1)) {
                        System.out.println("The start row cannot be placed on a row full of obstacles!");
                        validInput = false;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter an integer value.");
            }
        } while (!validInput);

        input.setStartRow(startRow);

        // Input start column
        System.out.println("Enter the start column of the robot:");

        validInput = false; // Reset validInput
        do {
            try {
                startColumn = Integer.parseInt(scanner.next());
                if (startColumn < 1 || startColumn > cols) {  // check if within the bounds
                    System.out.println("Start column must be within the range 1 to " + cols + ".");
                } else {
                    validInput = true; // if all good -> validInput is set to true

                    // validation to check if the position is on an obstacle
                    if(planner.grid[startRow - 1][startColumn - 1] == OBSTACLE) {
                        System.out.println("The start column can not be placed on an obstacle!");
                        validInput = false;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter an integer value.");
            }
        } while (!validInput);

        input.setStartColumn(startColumn);

        // Input end row
        System.out.println("Enter the end row of the robot:");
        validInput = false; // Reset validInput
        do {
            try {
                endRow = Integer.parseInt(scanner.next());
                if (endRow < 1 || endRow > rows) {
                    System.out.println("End row must be within the range 1 to " + rows + ".");
                } else {
                    validInput = true;

                    if (isRowFullOfObstacles(planner.grid, endRow - 1)) {
                        System.out.println("The end row cannot be placed on a row full of obstacles!");
                        validInput = false;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter an integer value.");
            }
        } while (!validInput);

        input.setEndRow(endRow);

        // Input end column
        System.out.println("Enter the end column of the robot:");
        validInput = false;
        do {
            try {
                endColumn = Integer.parseInt(scanner.next());
                if (endColumn < 1 || endColumn > cols) {
                    System.out.println("End column must be within the range 1 to " + cols + ".");
                } else {
                    validInput = true;

                    // validation to check if the position is on an obstacle
                    if(planner.grid[endRow - 1][endColumn - 1] == OBSTACLE) {
                        System.out.println("The end column can not be placed on an obstacle!");
                        validInput = false;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter an integer value.");
            }
        } while (!validInput);

        input.setEndColumn(endColumn);

        // Mark start and end positions on the grid
        planner.grid[startRow - 1][startColumn - 1] = 'R';
        planner.grid[endRow - 1][endColumn - 1] = 'E';

        System.out.println();
        planner.printGrid();

        System.out.println("--------------------------------------------------------");
        System.out.println();

        // Find the shortest path and mark it on the grid
        LinkedList path = ShortestPath(planner.grid, startRow - 1, startColumn - 1, endRow - 1, endColumn - 1);
        if (path != null) {
            for (int i = 0; i < path.size(); i++) {
                int[] p = path.get(i);
                int x = p[0];
                int y = p[1];
                // Skip updating the grid cells if they correspond to the start or end position of the robot
                if (!(x == startRow - 1 && y == startColumn - 1) && !(x == endRow - 1 && y == endColumn - 1)) {
                    planner.grid[x][y] = PATH;
                }
            }
            System.out.println();
            System.out.println("The number of steps for shortest Path: " + (path.size()-1));
            System.out.println();
            planner.printGrid();
        } else {
            System.out.println("Sorry! There is no path to be found.");
        }

    }

    // Constructor to initialize a grid with given dimensions
    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new char[rows][cols];
    }

    // Default constructor
    public Grid() {}

    // Method to randomly initialize the grid with obstacles
    public void initializeGrid() {
        Random random = new Random();
        int totalCells = rows * cols;
        int obstacles = (int) (totalCells * 0.25); // 25% of cells are obstacles

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = EMPTY;
            }
        }

        while (obstacles > 0) {
            int r = random.nextInt(rows);
            int c = random.nextInt(cols);

            if (grid[r][c] != OBSTACLE) {
                grid[r][c] = OBSTACLE;
                obstacles--;
            }
        }
    }

    // Method to print the grid
    public void printGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Method to find the shortest path using BFS
    public LinkedList ShortestPath(char[][] grid, int startX, int startY, int endX, int endY) {
        RobotPosition robot = new RobotPosition();
        int[][] directions = {{-1, 0, 1}, {1, 0, 2}, {0, -1, 3}, {0, 1, 4}};
        int rows = grid.length;
        int cols = grid[0].length;

        boolean[][] visited = new boolean[rows][cols];
        int[][] parent = new int[rows][cols];

        Queue queue = new Queue();
        queue.offer(startX, startY);
        visited[startX][startY] = true;

        String[] Position = new String[rows*cols];
        int a = 0;
        boolean value = true;

        while (!queue.isEmpty()) {
            Queue.Node current = queue.poll();
            int x = current.row;
            int y = current.column;

            if (x == endX && y == endY) {
                for (int i = 0;i<a;i++){
                    while (value == true) {
                        System.out.println("Robot Orientation is:");
                        value = false;
                    }
                    System.out.print("(" + Position[i] + ")");
                    System.out.print(" ");
                }
                System.out.println();
                return reconstructPath(parent, startX, startY, endX, endY);
            }

            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];

                if (dir[2]==1){
                    robot.setOrientation("up");
                }else if(dir[2]==2){
                    robot.setOrientation("down");
                }else if(dir[2]==3){
                    robot.setOrientation("left");
                }else if(dir[2]==4) {
                    robot.setOrientation("right");
                }

                if (isValid(newX, newY, grid) && !visited[newX][newY]) {
                    queue.offer(newX, newY);
                    visited[newX][newY] = true;
                    parent[newX][newY] = x * cols + y;
                    Position[a++] = robot.getOrientation();
                }
            }
        }
        return null; // If no path is found
    }

    // Method to check if a row is full of obstacles
    public boolean isRowFullOfObstacles(char[][] grid, int row) {
        for (int col = 0; col < grid[row].length; col++) {
            if (grid[row][col] != OBSTACLE) {
                return false;
            }
        }
        return true;
    }

    // Method to check if a position is valid
    public boolean isValid(int x, int y, char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        return x >= 0 && x < rows && y >= 0 && y < cols && grid[x][y] != OBSTACLE;
    }

    // Method to reconstruct the shortest path from the parent array
    public LinkedList reconstructPath(int[][] parent, int startX, int startY, int endX, int endY) {
        LinkedList path = new LinkedList();
        int rows = parent.length;
        int cols = parent[0].length;

        int currentX = endX;
        int currentY = endY;
        while (currentX != startX || currentY != startY) {
            path.add(new int[]{currentX, currentY});
            int index = parent[currentX][currentY];
            currentX = index / cols;
            currentY = index % cols;
        }
        path.add(new int[]{startX, startY});
        return path;
    }
}
