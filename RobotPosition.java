package RobotPathPlanning;

public class RobotPosition {
    // variables
    private int startRow;
    private int startColumn;
    private int endRow;
    private int endColumn;
    private String Orientation;

    // getter
    public int getStartRow() {
        return startRow;
    }

    // setter
    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getStartColumn() {
        return startColumn;
    }

    public void setStartColumn(int startColumn) {
        this.startColumn = startColumn;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getEndColumn() {
        return endColumn;
    }

    public void setEndColumn(int endColumn) {
        this.endColumn = endColumn;
    }

    public String getOrientation(){return Orientation;}

    public void setOrientation(String Orientation){this.Orientation = Orientation;}
}
