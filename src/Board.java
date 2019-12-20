public class Board {

    public int size;

    public int[] tentsLimitVertical;
    public int[] tentsLimitHorizontal;

    public CellType[][] cells;

    public Board(int[] tentsLimitVertical, int[] tentsLimitHorizontal, CellType[][] cells) {
        this.tentsLimitVertical = tentsLimitVertical;
        this.tentsLimitHorizontal = tentsLimitHorizontal;
        this.cells = cells;
        this.size = tentsLimitVertical.length;

        if (tentsLimitHorizontal.length != size || cells.length != size)
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
        return new Board(tentsLimitVertical.clone(), tentsLimitHorizontal.clone(), clonedCells);
    }
}
