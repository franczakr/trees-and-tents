import java.util.Arrays;

public class Board {

    public int size;

    public int[] tentsH;
    public int[] tentsV;

    public CellType[][] cells;

    public Board(int[] tentsH, int[] tentsV, CellType[][] cells) {
        this.tentsH = tentsH;
        this.tentsV = tentsV;
        this.cells = cells;
        this.size = tentsH.length;

        if (tentsV.length != size || cells.length != size)
            throw new IllegalArgumentException("Incorrect input");
        for (int x = 0; x < size; x++) {
            if (cells[x].length != size)
                throw new IllegalArgumentException("Incorrect input");
        }
    }

    public Board copy() {
        CellType[][] clonedCells = new CellType[cells.length][];
        for(int i = 0; i<cells.length; i++) {
            clonedCells[i] = cells[i].clone();
        }
        return new Board(tentsH.clone(), tentsV.clone(), clonedCells);
    }
}
